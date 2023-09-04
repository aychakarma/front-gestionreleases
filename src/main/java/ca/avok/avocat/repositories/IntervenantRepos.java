package ca.avok.avocat.repositories;

import ca.avok.avocat.entities.Intervenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IntervenantRepos extends JpaRepository<Intervenant,String> {

}
