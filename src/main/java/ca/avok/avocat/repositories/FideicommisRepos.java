package ca.avok.avocat.repositories;

import ca.avok.avocat.entities.Fideicommis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FideicommisRepos extends JpaRepository<Fideicommis,Integer> {
}
