package ca.avok.avocat.repositories;

import ca.avok.avocat.entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactureRepository extends JpaRepository<Facture,Integer> {
}
