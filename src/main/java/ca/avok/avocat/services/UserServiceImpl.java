package ca.avok.avocat.services;

import ca.avok.avocat.InterfaceService.IUserService;
import ca.avok.avocat.entities.AppUser;
import ca.avok.avocat.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository appUserRepository;

    public AppUser createUser(AppUser user) {

        return appUserRepository.save(user);
    }

    public AppUser updateuser(AppUser user)
    {

        return appUserRepository.save(user);
    }

    public void removeUser(String idAppuser)
    {
        appUserRepository.deleteById(idAppuser);

    }

    public List<AppUser> getAllUsers()
    {
        return appUserRepository.findAll();
    }



    public AppUser finduserbyid(String id)
    {
        Optional<AppUser> appUser = appUserRepository.findById(id);
        return appUser.orElse(null);
    }
}

