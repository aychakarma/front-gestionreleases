package ca.avok.avocat.InterfaceService;

import ca.avok.avocat.entities.AppUser;

import java.util.List;

public interface IUserService {
    public AppUser createUser(AppUser user) ;

    public AppUser updateuser(AppUser user);

    public void removeUser(String idAppuser);

    public List<AppUser> getAllUsers();

    public AppUser finduserbyid(String id);

}
