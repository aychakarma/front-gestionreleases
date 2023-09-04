package ca.avok.avocat.controllers;

import ca.avok.avocat.entities.MondatAideJuridique;
import ca.avok.avocat.entities.MondatPrive;
import ca.avok.avocat.services.MondatPriveServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/MondatPrive")
@AllArgsConstructor
public class MondatPriveController {

    MondatPriveServiceImpl mondatPriveService;

    //-----------------------------------CRUD begins-----------------------------------//
    // Créer un nouveau mandat privé et lui affecter au dossier spècifique
    @PostMapping("/add/{numDossier}")
    public ResponseEntity<String> createMondatAideJuridique(@RequestBody MondatPrive mondatPrive, @PathVariable String numDossier) {
        try {
            mondatPriveService.createMondatPrive(mondatPrive,numDossier);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"mondat ajouté avec succés\"}");
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu\"}");
        }
    }

    // Lire tous les mandats privés
    @GetMapping("/getAll")
    public ResponseEntity<?> readAllMondatPrive() {
        try {
            List<MondatPrive> allMondats = mondatPriveService.readAllMondatPrive();
            return ResponseEntity.ok(allMondats);
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu\"}");
        }
    }

    // Lire un mandat privé par son ID
    @GetMapping("/get/{id}")
    public ResponseEntity<?> readMondatPrive(@PathVariable int id) {
        try {
            MondatPrive mondatPrive = mondatPriveService.readMondatPrive(id);
            if (mondatPrive != null) {
                return ResponseEntity.ok(mondatPrive);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"No mondat found.\"}");
            }
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu\"}");
        }
    }

    // Mettre à jour un mandat privé
    @PutMapping("/update")
    public ResponseEntity<String> updateMondatPrive(@RequestBody MondatPrive mondatPrive) {
        try {
            MondatPrive updatedMondat = mondatPriveService.updateMondatPrive(mondatPrive);
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

    // Supprimer un mandat privé par son ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMondatPrive(@PathVariable int id) {
        try {
            mondatPriveService.deleteMondatPrive(id);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"mondat supprimé avec succés\"}");
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu\"}");
        }
    }
    //-----------------------------------CRUD ends-----------------------------------//
}
