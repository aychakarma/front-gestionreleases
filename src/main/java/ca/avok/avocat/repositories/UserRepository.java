package ca.avok.avocat.repositories;

import ca.avok.avocat.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser,String> {
}
