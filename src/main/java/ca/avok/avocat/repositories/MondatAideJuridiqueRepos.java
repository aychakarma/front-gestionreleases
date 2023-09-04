package ca.avok.avocat.repositories;

import ca.avok.avocat.entities.MondatAideJuridique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MondatAideJuridiqueRepos extends JpaRepository<MondatAideJuridique,Integer> {
}
