package ca.avok.avocat.repositories;

import ca.avok.avocat.entities.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TacheReposiory extends JpaRepository<Tache,Integer> {
    @Query("SELECT t FROM Tache t WHERE t.dateDebut <= :dateDuJour AND t.dateFin >= :dateDuJour")
    List<Tache> findTachesDuJour(LocalDateTime dateDuJour);
}
