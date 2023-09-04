package ca.avok.avocat.controllers;

import ca.avok.avocat.entities.LoginRequest;
import ca.avok.avocat.entities.UserDTO;
import ca.avok.avocat.enumeration.ROLE;
import ca.avok.avocat.services.AuthService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.authorization.client.util.HttpResponseException;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200/","http://localhost:4300/"})
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthentificationController {

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.auth-server-url}")
    private String authserverurl;

    @Value("${keycloak.resource}")
    private String clientname;

    @Value("${keycloak.credentials.secret}")
    private String clientsecret;

    @Value("${curiam.realm-master}")
    private String realmaster;

    @Value("${curiam.client-master}")
    private String clientmaster;

    @Value("${curiam.masterusername}")
    private String masterusername;

    @Value("${curiam.masterpw}")
    private String masterpw;

    @Value("${curiam.masteradminclisecret}")
    private String masteradminclisecret;

    @Autowired
    private AuthService authService;
    @PostMapping(path = "/getinfo")
    public ResponseEntity<?> getiinfo() {

        return ResponseEntity.ok(realm +"\n"+ clientname +"\n"+ authserverurl+"\n"+clientsecret);
    }


    /**
     * Logs out a user by revoking the provided refresh token.
     *
     * @param refresh_token The refresh token of the user to be logged out.
     * @return ResponseEntity with a success message or an error message.
     */
    @GetMapping(value = "/logout/{refresh_token}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> logout(@PathVariable String refresh_token) {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            String url = String.format("%s/realms/%s/protocol/openid-connect/logout", authserverurl, realm);
            HttpPost httpPost = new HttpPost(url);

            String requestBody = "client_id=" + clientname +
                    "&client_secret=" + clientsecret +
                    "&refresh_token=" + refresh_token;

            StringEntity stringEntity = new StringEntity(requestBody, ContentType.APPLICATION_FORM_URLENCODED);
            httpPost.setEntity(stringEntity);

            HttpResponse response = httpClient.execute(httpPost);

            // Check for a successful logout response (HTTP status 204)
            if (response.getStatusLine().getStatusCode() == 204) {
                return ResponseEntity.ok("User Logged out");
            } else {
                // Return an error message if the logout request fails
                return ResponseEntity.ok("Failed to perform logout");
            }

        } catch (IOException e) {
            // Handle I/O exceptions
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An I/O error occurred");
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during logout");
        }
    }


    /**
     * Registers a new user in the Keycloak realm.
     *
     * @param userDTO The UserDTO object containing user details for registration.
     * @return ResponseEntity with a success message or an error message.
     */
    @CrossOrigin(origins = "http://localhost:4300")
    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        try {
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(authserverurl)
                    .clientSecret(masteradminclisecret)
                    .realm(realmaster)
                    .clientId(clientmaster)
                    .username(masterusername)
                    .password(masterpw)
                    .grantType(OAuth2Constants.PASSWORD)
                    .build();

            // Create a new UserRepresentation object with user details
            UserRepresentation user = new UserRepresentation();
            user.setEnabled(true);
            user.setUsername(userDTO.getUsername());
            user.setFirstName(userDTO.getFirstname());
            user.setLastName(userDTO.getLastname());
            user.setEmail(userDTO.getEmail());
            user.setEmailVerified(false);

            RealmResource realmResource = keycloak.realm("curiam");
            UsersResource usersResource = realmResource.users();

            // Check if the username already exists
            List<UserRepresentation> existingUsersbyyusername = usersResource.search(user.getUsername(), true);
            List<UserRepresentation> existingUsersByEmail = usersResource.search(null, null, null, user.getEmail(), null, null, null, true);
            if (!existingUsersbyyusername.isEmpty()) {
                // Return a conflict response if the username already exists
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"A user with the same Username already exists\"}");
            }

            if (!existingUsersByEmail.isEmpty()) {
                // Return a conflict response if the username already exists
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"A user with the same Email  already exists\"}");
            }



            // Create the user in Keycloak
            Response response = usersResource.create(user);

            UserResource u = realmResource.users().get(CreatedResponseUtil.getCreatedId(response));
            u.sendVerifyEmail();


            if (response.getStatus() == 201) {
                String userId = CreatedResponseUtil.getCreatedId(response);
                UserResource userResource = usersResource.get(userId);

                // Set the user's password credential
                CredentialRepresentation passwordCred = new CredentialRepresentation();
                passwordCred.setTemporary(false);
                passwordCred.setType(CredentialRepresentation.PASSWORD);
                passwordCred.setValue(userDTO.getPassword());
                userResource.resetPassword(passwordCred);

                // Get the realm role AvocatSenior
                RoleRepresentation realmRoleUser = realmResource.roles().get(ROLE.AVOCAT_SENIOR.toString()).toRepresentation();

                // Assign AvocatSenior role to the user
                userResource.roles().realmLevel().add(Arrays.asList(realmRoleUser));

                // Return success message
                return ResponseEntity.ok("{\"message\": \"User Created Successfully\"}");
            } else {
                // Return an error message if user creation fails
                return ResponseEntity.status(response.getStatus()).body("{\"error\": \"Failed to create user\"}");
            }
        } catch (Exception e) {
            // Handle exceptions and return error message
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"An error occurred during user registration\"}");
        }
    }




    /** Sign-in function that handles authentication using Keycloak */
    @PostMapping(path = "/signin")
    public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest) {

try {
    /*****Connecting to keyclaok Serve *************/
    Keycloak keycloak = KeycloakBuilder.builder()
            .serverUrl(authserverurl)
            .clientSecret(masteradminclisecret)
            .realm(realmaster)
            .clientId(clientmaster)
            .username(masterusername)
            .password(masterpw)
            .grantType(OAuth2Constants.PASSWORD)
            .build();

    Map<String, Object> clientCredentials = new HashMap<>();
    clientCredentials.put("secret", clientsecret);
    clientCredentials.put("grant_type", "password");
    clientCredentials.put("username", loginRequest.getUsernameoremail());
    clientCredentials.put("password", loginRequest.getPassword());

    Configuration configuration = new Configuration(authserverurl, realm, clientname, clientCredentials, null);
    AuthzClient authzClient = AuthzClient.create(configuration);

    AccessTokenResponse response;

    /*******************************************************************/
    /*Find users by username*/
    RealmResource realmResource = keycloak.realm(realm);
    List<UserRepresentation> allUsers = realmResource.users().list();
    List<UserRepresentation> users = keycloak.realm(realm).users().search(loginRequest.getUsernameoremail());
    /*******************************************************************/
    /*Find users by email*/
//    List<UserRepresentation> usersByEmail = allUsers.stream()
//            .filter(user -> loginRequest.getUsernameoremail().equalsIgnoreCase(user.getEmail()))
//            .collect(Collectors.toList());
    /*******************************************************************/
    // Find users by username
    List<UserRepresentation> usersByUsername = allUsers.stream()
            .filter(user -> loginRequest.getUsernameoremail().equalsIgnoreCase(user.getUsername()))
            .collect(Collectors.toList());

    // Find users by email
    List<UserRepresentation> usersByEmail = allUsers.stream()
            .filter(user -> loginRequest.getUsernameoremail().equalsIgnoreCase(user.getEmail()))
            .collect(Collectors.toList());

    if (usersByUsername.isEmpty() && usersByEmail.isEmpty()) {
        return ResponseEntity.ok("{\"error\": \"Username or email doesn't exist\"}");
    } else if (!usersByUsername.isEmpty() && !usersByUsername.get(0).isEmailVerified()) {
        return ResponseEntity.ok("{\"error\": \"Please verify email before continuing\"}");
    } else if (!usersByEmail.isEmpty() && !usersByEmail.get(0).isEmailVerified()) {
        return ResponseEntity.ok("{\"error\": \"Please verify email before continuing\"}");
    } else if (!usersByUsername.isEmpty() && !usersByUsername.get(0).isEnabled()) {
        return ResponseEntity.ok("{\"error\": \"User is disabled\"}");
    } else if (!usersByEmail.isEmpty() && !usersByEmail.get(0).isEnabled()) {
        return ResponseEntity.ok("{\"error\": \"User is disabled\"}");
    }

    else {
      try{

        // Obtain the access token if the user is verified and exists
        response = authzClient.obtainAccessToken(loginRequest.getUsernameoremail(), loginRequest.getPassword());
        DecodedJWT jwt = JWT.decode(response.getToken());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("access_token", response.getToken());
        responseBody.put("expires_in", response.getExpiresIn());
        responseBody.put("token_type", response.getTokenType());
        responseBody.put("scope", response.getScope());
        responseBody.put("refresh_token", response.getRefreshToken());

        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("message", "Authentication successful");
        successResponse.put("data", responseBody);

        return ResponseEntity.ok(successResponse);
      }
      finally
      {
          keycloak.close();
      }
    }
        } catch (HttpResponseException e)   {
                    return ResponseEntity.ok("{\"error\": \"Invalid password\"}");

            }
            catch (Exception e) {
                return ResponseEntity.ok("{\"error\": \"Failed to sign in. Please try again later\"}");
                }

    }




 /**Function to reset the password of a user in the Keycloak realm BY USERID  */
    @PutMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@QueryParam("userId") String userId) {
        int resetResult = authService.resetPasswordByUserId(userId);

        if (resetResult == 404) {
            return ResponseEntity.ok("User not found");
        } else if (resetResult == 105) {
            return ResponseEntity.ok("User is Disabled");
        } else {
            return ResponseEntity.ok("A Password reset email was sent, please reset your password before continuing");
        }
    }


































}