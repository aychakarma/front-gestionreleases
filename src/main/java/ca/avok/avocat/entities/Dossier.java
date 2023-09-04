package ca.avok.avocat.entities;

import ca.avok.avocat.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dossier implements Serializable {
    @Id
    @Column(name = "id")
    private String numDossier;

    @Enumerated(EnumType.STRING)
    private Status status = Status.OUVERT;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateOuverture = LocalDateTime.now();

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateFermeture = null;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateInscription;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime derniereConsultation;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "dossier")
    private List<Audition> auditions = new ArrayList<>();

    @OneToOne
    private Mondat mondat;

    @ManyToMany
    private List<Intervenant> intervenants;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "dossier")
    private List<Fideicommis> fideicommis = new ArrayList<>();

}
