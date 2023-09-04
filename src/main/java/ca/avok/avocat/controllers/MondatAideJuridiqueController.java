package ca.avok.avocat.controllers;

import ca.avok.avocat.entities.MondatAideJuridique;
import ca.avok.avocat.services.MondatAideJuridiqueServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/MondatAideJuridique")
@AllArgsConstructor
public class MondatAideJuridiqueController {

    MondatAideJuridiqueServiceImpl mondatAideJuridiqueService;

    //-----------------------------------CRUD begins-----------------------------------//
    //Création avec l'affectation au dossier concerné
    @PostMapping("/addAideJuridique/{numDossier}")
    public ResponseEntity<String> createMondatAideJuridique(@RequestBody MondatAideJuridique mondatAideJuridique,@PathVariable String numDossier) {
        try {
            mondatAideJuridiqueService.createMondatAideJuridique(mondatAideJuridique,numDossier);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"mondat ajouté avec succés\"}");
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu\"}");
        }
    }

    // Lire tous les mandats d'aide juridique
    @GetMapping("/getAll")
    public ResponseEntity<?> readAllMondatAideJuridique() {
        try {
            List<MondatAideJuridique> allMondats = mondatAideJuridiqueService.readAllMondatAideJuridique();
            return ResponseEntity.ok(allMondats);
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu\"}");
        }
    }

    // Lire un mandat d'aide juridique par son ID
    @GetMapping("/get/{id}")
    public ResponseEntity<?> readMondatAideJuridique(@PathVariable int id) {
        try {
            MondatAideJuridique mondatAideJuridique = mondatAideJuridiqueService.readMondatAideJuridique(id);
            if (mondatAideJuridique != null) {
                return ResponseEntity.ok(mondatAideJuridique);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"No mondat found.\"}");
            }
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu\"}");
        }
    }

    // Mettre à jour un mandat d'aide juridique
    @PutMapping("/update")
    public ResponseEntity<String> updateMondatAideJuridique(@RequestBody MondatAideJuridique mondatAideJuridique) {
        try {
            MondatAideJuridique updatedMondat = mondatAideJuridiqueService.updateMondatAideJuridique(mondatAideJuridique);
            if (updatedMondat != null) {
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"mondat mis à jour avec succés\"}");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"No mondat found.\"}");
            }
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu\"}");
        }
    }

    // Supprimer un mandat d'aide juridique par son ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMondatAideJuridique(@PathVariable int id) {
        try {
            mondatAideJuridiqueService.deleteMondatAideJuridique(id);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"mondat supprimé avec succés\"}");
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu\"}");
        }
    }
    //-----------------------------------CRUD ends-----------------------------------//

}
