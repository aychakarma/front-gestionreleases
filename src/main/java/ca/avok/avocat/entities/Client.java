package ca.avok.avocat.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idClient;
    private String nom_prenom;
    private String email1;
    private String email2;
    private String telephone;
    private LocalDateTime dateDeNaissance;
    private String civilite;
    private String poste;
    private String faxe;
    private String cellulaire;
    private String adresse1;
    private String adresse2;
    private String pays;
    private String province;
    private String commentaire;
 /*  @OneToMany
   (mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Dossier> dossiers = new ArrayList<>(); */


}
