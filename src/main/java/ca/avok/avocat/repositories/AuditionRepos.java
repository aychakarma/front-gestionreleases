package ca.avok.avocat.repositories;

import ca.avok.avocat.entities.Audition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditionRepos extends JpaRepository<Audition,Integer> {
}
