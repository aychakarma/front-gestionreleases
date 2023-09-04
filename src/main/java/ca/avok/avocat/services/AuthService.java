package ca.avok.avocat.services;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {


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






    public int resetPasswordByUserId(String userId)
    {
        try (Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(authserverurl)
                .clientSecret(masteradminclisecret)
                .realm(realmaster)
                .clientId(clientmaster)
                .username(masterusername)
                .password(masterpw)
                .grantType(OAuth2Constants.PASSWORD)
                .build()) {

            if (!keycloak.realm(realm).users().get(userId).toRepresentation().isEnabled()) {
                return 105;
            } else {
                keycloak.realm(realm).users().get(userId).resetPasswordEmail();
                return 200;
            }
        } catch (javax.ws.rs.NotFoundException e) {
            // Handle the case where the user is not found in the Keycloak realm
            return 404;
        }
        catch (Exception e) {
            // Handle other unexpected exceptions
            e.printStackTrace(); // Print the stack trace for debugging purposes
            return 500; // Return an appropriate error code or message
        }
    }













     /***LOGOUT USER METHODE****/

//     public String logout(String token) {
//
//         String logoutUrl = "http://localhost:8180/auth/realms/"+realm+"/protocol/openid-connect/logout";
//
//         try {
//             HttpClient httpClient = HttpClients.createDefault();
//
//             HttpPost httpPost = new HttpPost(logoutUrl);
//             String requestBody = "client_id="+realm+
//                     "&client_secret="+clientsecret+
//                     "&refresh_token=" + token;
//
//             StringEntity stringEntity = new StringEntity(requestBody, ContentType.APPLICATION_FORM_URLENCODED);
//
//             httpPost.setEntity(stringEntity);
//
//             HttpResponse response = httpClient.execute(httpPost);
//
//             int statusCode = response.getStatusLine().getStatusCode();
//
//             String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
//
//             return responseBody;
//
//         } catch (Exception e) {
//                    e.printStackTrace();
//             return e.getMessage();
//         }
//
//
//     }


//     public void logout(@PathVariable String refresh_token) {
//         MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
//         requestParams.add("client_id", "codeholicsclient");
//         requestParams.add("client_secret","ac47426e-9872-4531-9614-4d3acad9dcbd");
//         requestParams.add("refresh_token", refresh_token);
//
//         logoutUserSession(requestParams);
//
//     }



}
