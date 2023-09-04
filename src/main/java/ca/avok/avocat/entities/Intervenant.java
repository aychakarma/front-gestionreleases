package ca.avok.avocat.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Intervenant {

    @Id
    private String idIntervenant;

    private String nomEtPrenom;

    private String email;

    private String telephone;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateDeNaissance;

    private String civilite;

    private String poste;

    private String fax;

    private String cellulaire;

    private String adresse1;

    private String adresse2;

    private String email2;

    private String pays;

    private String province;

    private String commentaire;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "intervenants")
    private List<Dossier> dossiers = new ArrayList<>();

}
