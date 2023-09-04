package ca.avok.avocat.controllers;

import ca.avok.avocat.entities.Audition;
import ca.avok.avocat.services.AuditionServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Audition")
@AllArgsConstructor
public class AuditionController {

    @Autowired
    AuditionServiceImpl auditionService;


    //-----------------------------------CRUD begins-----------------------------------//
    // Créer une audition
    @PostMapping("/addAudition/{numDossier}")
    public ResponseEntity<String> createAudition(@RequestBody Audition audition,@PathVariable String numDossier) {
        try {
            auditionService.createAudition(audition,numDossier);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"audition ajouté avec succés\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu\"}");
        }
    }

    // Lire toutes les auditions
    @GetMapping("/getAllAudition")
    public ResponseEntity<?> readAllAudition() {
        try {
            List<Audition> allAuditions = auditionService.readAllAudition();
            return ResponseEntity.ok(allAuditions);
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu.\"}");
        }
    }

    // Lire une audition par son ID
    @GetMapping("/getAudition/{id}")
    public ResponseEntity<?> readAudition(@PathVariable int id) {
        try {
            Audition audition = auditionService.readAudition(id);
            if (audition != null) {
                return ResponseEntity.ok(audition);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"No audition found.\"}");
            }
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu.\"}");
        }
    }

    // Mettre à jour une audition
    @PutMapping("/updateAudition")
    public ResponseEntity<String> updateAudition(@RequestBody Audition audition) {
        try {
            Audition updatedAudition = auditionService.updateAudition(audition);
            if (updatedAudition != null) {
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"audition mis à jour avec succés\"}");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"No audition found.\"}");
            }
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu.\"}");
        }
    }

    // Supprimer une audition par son ID
    @DeleteMapping("removeAudition/{id}")
    public ResponseEntity<String> deleteAudition(@PathVariable int id) {
        try {
            auditionService.deleteAudition(id);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"audition supprimé avec succés\"}");
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu.\"}");
        }
    }
    //-----------------------------------CRUD ends-----------------------------------//

    // Terminer une audition par son ID
    @PutMapping("/terminerAudition/{id}")
    public ResponseEntity<String> terminerAudition(@PathVariable int id) {
        try {
            auditionService.terminerAudition(id);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"audition s'est terminé avec succés\"}");
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu.\"}");
        }
    }


    // Activer une audition par son ID
    @PutMapping("/activerAudition/{idAudition}")
    public ResponseEntity<String> activerAudition(@PathVariable int idAudition) {
        try {
            auditionService.activerAudition(idAudition);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"audition s'est activé avec succés\"}");
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu.\"}");
        }
    }

}
