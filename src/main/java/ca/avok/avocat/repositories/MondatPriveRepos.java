package ca.avok.avocat.repositories;

import ca.avok.avocat.entities.MondatPrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MondatPriveRepos extends JpaRepository<MondatPrive,Integer> {

}
