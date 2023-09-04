package ca.avok.avocat.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MondatAideJuridique extends Mondat{

    private String numMondat;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateMondat;
}
