package ca.avok.avocat.repositories;

import ca.avok.avocat.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Integer> {
}
