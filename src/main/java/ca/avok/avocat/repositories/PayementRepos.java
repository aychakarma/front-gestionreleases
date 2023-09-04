package ca.avok.avocat.repositories;

import ca.avok.avocat.entities.Payement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayementRepos extends JpaRepository<Payement,Integer> {
}
