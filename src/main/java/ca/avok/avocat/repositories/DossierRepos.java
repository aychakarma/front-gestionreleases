package ca.avok.avocat.repositories;

import ca.avok.avocat.entities.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DossierRepos extends JpaRepository<Dossier,String> {
    Optional<Dossier> findByNumDossier(String numDossier);
    void deleteByNumDossier(String numDossier);
}
