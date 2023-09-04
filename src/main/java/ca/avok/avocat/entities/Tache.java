
package ca.avok.avocat.entities;

import ca.avok.avocat.enumeration.StatusTache;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Tache")
public class Tache implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTache ;
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateDebut;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateFin;
    @Enumerated(EnumType.STRING)
    private StatusTache status;
    //private Dossier dossier;
    private String description;
    private String duree;

    /*@ManyToOne
    @JoinColumn(name = "dossier_id") // Le nom de la colonne qui représente la clé étrangère
    private Dossier dossier;*/
}
