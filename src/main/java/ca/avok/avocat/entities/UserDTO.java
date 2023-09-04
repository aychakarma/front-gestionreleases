package ca.avok.avocat.entities;

import ca.avok.avocat.enumeration.ROLE;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    private String email;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private ROLE role;
    private int statusCode;
    private String status;
    private Boolean enabled;
    private Boolean emailverified;

}