package ca.avok.avocat.entities;

import ca.avok.avocat.enumeration.StatusPay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Depense")
public class Depense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDepense ;
    private String objet;
    private String numFacture;
    private LocalDateTime dateDepense;
    private Float montant;
    private Float tps;
    private Float tpq;
    private Float frais;
    private String commentaire;
    @Enumerated(EnumType.STRING)
    private StatusPay status;

    /*@ManyToOne
    @JoinColumn(name = "dossier_id") // Le nom de la colonne qui représente la clé étrangère
    private Dossier dossier;*/
}
