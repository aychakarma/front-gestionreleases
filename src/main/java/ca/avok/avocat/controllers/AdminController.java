package ca.avok.avocat.controllers;


import ca.avok.avocat.InterfaceService.IUserService;
import ca.avok.avocat.entities.AppUser;
import ca.avok.avocat.enumeration.ROLE;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200/","http://localhost:4300/"})
@RestController
@RequestMapping("/api/admin/")
@AllArgsConstructor
@Slf4j
public class AdminController {

private final IUserService appuserService;


    @PostMapping("/createuser")
    public ResponseEntity<?> addUser(@RequestBody AppUser user) {
        try {
            AppUser usercreated = appuserService.createUser(user);
            return ResponseEntity.ok(usercreated);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création de l'utilisateur.");
        }
    }

    @PutMapping("/edituser")
    public ResponseEntity<?> edituser(@RequestBody AppUser user) {
        try {
            AppUser userupdated = appuserService.updateuser(user);
            return ResponseEntity.ok(userupdated);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour de l'utilisateur.");
        }
    }

    @DeleteMapping("/deleteuser/{iduser}")
    public ResponseEntity<?> deleteuser(@PathVariable("iduser") String idUser) {
        AppUser user = appuserService.finduserbyid(idUser);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
        } else {
            try {
                appuserService.removeUser(idUser);
                return ResponseEntity.ok("Utilisateur Supprimé avec succès");
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression de l'utilisateur.");
            }
        }
    }



    @GetMapping("/getallusers")
    public ResponseEntity<?> getAllusers()
    {
        List<AppUser> users = appuserService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/finduserbyid")
    public ResponseEntity<?> finduserbyid(@RequestParam("iduser") String iduser) {
        AppUser user = appuserService.finduserbyid(iduser);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
        } else {
            return ResponseEntity.ok(user);
        }
    }














}
