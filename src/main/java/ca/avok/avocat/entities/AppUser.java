package ca.avok.avocat.entities;

import ca.avok.avocat.enumeration.ROLE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "app_users")
@ToString
public class AppUser implements Serializable {


    @Id
    private String idUser;

    @Column(name = "nom")
    @NotEmpty(message = "veuillez saisr votre nom")
    private String nom;

    @Column(name = "prenom")
    @NotEmpty(message = "veuillez saisr votre prenom")
    private  String prenom;

    @Column(name = "username")
    @NotEmpty(message = "veuillez saisr votre nom d'utilisateur")
    private  String username;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "birthdate")
    @NotBlank(message ="veuillez saisr votre DDN")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:SS")
    private LocalDateTime birthdate;

    @Column(name = "numerotelephone")
    @NotBlank(message ="veuillez saisr votre Numero de telephone")
    private String phone;

    @Column(name = "civilite")
    @NotBlank(message ="veuillez saisr votre civilite")
    private String civilite;

    @Column(name = "poste")
    @NotBlank(message ="veuillez saisr votre poste")
    private String poste;

    @Column(name = "fax")
    @NotBlank(message ="veuillez saisr votre fax")
    private String fax;

    @Column(name = "cellulaire")
    @NotBlank(message ="veuillez saisr votre cellulaire")
    private String cellulaire;

    @Column(name = "adresse")
    @NotBlank(message ="veuillez saisr votre adresse\"")
    private String adresse;

    @NotBlank(message = "Password is required")
    @Column(name = "password")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Password must have at least 8 characters, one lowercase letter, one uppercase letter, and one digit.")
    @JsonIgnore
    private String password;

    @NotBlank(message = "Confirm Password is required")
    @JsonIgnore
    private String confirmPassword;

    @Column(name = "Role")
    @Enumerated(EnumType.STRING)
    private ROLE role;

    private String userimage;


    @Column(name = "Numero_assurance_sociale")
    @NotBlank(message ="veuillez saisr votre NAS")
    private String nas;


    @Column(name = "pays")
    @NotBlank(message ="veuillez saisr votre Pays")
    private String pays;


    @Column(name = "province")
    @NotBlank(message ="veuillez saisr votre province")
    private String province;









    //    Password need to match Confirm Password
    public boolean isPasswordMatch() {
        return password != null && password.equals(confirmPassword);
    }


}
