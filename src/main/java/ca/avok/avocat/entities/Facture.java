package ca.avok.avocat.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Facture")
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFacture;
    private LocalDateTime dateFacture;
    private String commentaire;
    //private Dossier dossier;
    /*@ManyToOne
    @JoinColumn(name = "dossier_id") // Le nom de la colonne qui représente la clé étrangère
    private Dossier dossier;*/
    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL)
    private List<Payement> paiements; // Liste des paiements associés à cette facture


}
