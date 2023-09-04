package ca.avok.avocat.entities;

import ca.avok.avocat.enumeration.ModePay;
import ca.avok.avocat.enumeration.NaturePay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Fideicommis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFideicommis;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private NaturePay nature_payment;

    private float montant;

    @Enumerated(EnumType.STRING)
    private ModePay mode_payment;

    private String commentaire;

    @ManyToOne
    private Dossier dossier;
}
