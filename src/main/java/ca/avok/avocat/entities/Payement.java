package ca.avok.avocat.entities;

import ca.avok.avocat.enumeration.ModePay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Payement")
public class Payement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPayement;
    private LocalDateTime datePayement;
    private String montant;
    private ModePay modePayement;
    @ManyToOne
    @JoinColumn(name = "facture_id")
    private Facture facture; // Facture associée à cette dépense
    /*@OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private List<Facture> facture;*/


}
