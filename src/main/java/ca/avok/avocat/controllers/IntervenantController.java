package ca.avok.avocat.controllers;

import ca.avok.avocat.entities.Intervenant;
import ca.avok.avocat.services.IntervenantServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/intervenant")
@AllArgsConstructor
public class IntervenantController {

    IntervenantServiceImpl intervenantService;

    // Créer un intervenant
    @PostMapping("/createIntervenant")
    public ResponseEntity<String> createIntervenant(@RequestBody Intervenant intervenant) {
        try {
            try{
                intervenantService.readIntervenant(intervenant.getIdIntervenant());
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"ce intervenant existe déjà\"}");
            } catch (Exception ex) {
                Intervenant createdIntervenant = intervenantService.createIntervenant(intervenant);
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"intervenant ajouté avec succés\"}");
            }
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu\"}");
        }
    }

    // Lire tous les intervenants
    @GetMapping("/readAllIntervenant")
    public ResponseEntity<?> readAllIntervenant() {
        try {
            List<Intervenant> allIntervenants = intervenantService.readAllIntervenant();
            return ResponseEntity.ok(allIntervenants);
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu\"}");
        }
    }

    // Lire un intervenant par son ID
    @GetMapping("/readIntervenant/{idIntervenant}")
    public ResponseEntity<?> readIntervenant(@PathVariable String idIntervenant) {
        try {
            Intervenant intervenant = intervenantService.readIntervenant(idIntervenant);
            if (intervenant != null) {
                return ResponseEntity.ok(intervenant);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"No intervenant found.\"}");
            }
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu\"}");
        }
    }

    // Mettre à jour un intervenant
    @PutMapping("/updateIntervenant")
    public ResponseEntity<String> updateIntervenant(@RequestBody Intervenant intervenant) {
        try {
            Intervenant updatedIntervenant = intervenantService.updateIntervenant(intervenant);
            if (updatedIntervenant != null) {
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"intervenant mis à jour avec succés\"}");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"No intervenant found.\"}");
            }
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu\"}");
        }
    }

    // Supprimer un intervenant par son ID
    @DeleteMapping("/deleteIntervenant/{idIntervenant}")
    public ResponseEntity<String> deleteIntervenant(@PathVariable String idIntervenant) {
        try {
            intervenantService.deleteIntervenant(idIntervenant);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"intervenant supprimé avec succés\"}");
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu.\"}");
        }
    }

    // Rechercher un intervenant à partir de n'importe quel attribut
    @GetMapping("/searchIntervenant")
    public ResponseEntity<?> searchIntervenant(@RequestParam("criteria") String searchCriteria) {
        try {
            List<Intervenant> matchingIntervenants = intervenantService.searchIntervenant(searchCriteria);
            if (!matchingIntervenants.isEmpty()) {
                return ResponseEntity.ok(matchingIntervenants);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"No intervenant found.\"}");
            }
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu.\"}");
        }
    }
}
