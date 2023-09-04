package ca.avok.avocat.controllers;

import ca.avok.avocat.entities.Fideicommis;
import ca.avok.avocat.services.FideicommisServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Fideicommis")
@AllArgsConstructor
public class FideicommisController {

    FideicommisServiceImpl fideicommisService;

    // Créer un fideicommis
    @PostMapping("/createFideicommis")
    public ResponseEntity<String> createFideicommis(@RequestBody Fideicommis fideicommis) {
        try {
            Fideicommis createdFideicommis = fideicommisService.createFideicommis(fideicommis);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"fideicommis ajouté avec succés\"}");
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu\"}");
        }
    }

    // Lire tous les fideicommis
    @GetMapping("/readAllFideicommis")
    public ResponseEntity<?> readAllFideicommis() {
        try {
            List<Fideicommis> allFideicommis = fideicommisService.readAllFideicommis();
            return ResponseEntity.ok(allFideicommis);
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu\"}");
        }
    }

    // Lire un fideicommis par son ID
    @GetMapping("/readFideicommis/{idFideicommis}")
    public ResponseEntity<?> readFideicommis(@PathVariable int idFideicommis) {
        try {
            Fideicommis fideicommis = fideicommisService.readFideicommis(idFideicommis);
            if (fideicommis != null) {
                return ResponseEntity.ok(fideicommis);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"No fideicommis found.\"}");
            }
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu.\"}");
        }
    }

    // Mettre à jour un fideicommis
    @PutMapping("/updateFideicommis")
    public ResponseEntity<String> updateFideicommis(@RequestBody Fideicommis fideicommis) {
        try {
            Fideicommis updatedFideicommis = fideicommisService.updateFideicommis(fideicommis);
            if (updatedFideicommis != null) {
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"fideicommis mis à jour avec succés\"}");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"No fideicommis found.\"}");
            }
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu.\"}");
        }
    }

    // Supprimer un fideicommis par son ID
    @DeleteMapping("/deleteFideicommis/{idFideicommis}")
    public ResponseEntity<String> deleteFideicommis(@PathVariable int idFideicommis) {
        try {
            fideicommisService.deleteFideicommis(idFideicommis);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"fideicommis supprimé avec succés\"}");
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu.\"}");
        }
    }

    // Recherche des Fideicommis correspondants aux critères de recherche
    @GetMapping("/searchFideicommis")
    public ResponseEntity<?> searchFideicommis(@RequestParam("criteria") String searchCriteria) {
        try {
            List<Fideicommis> matchingFideicommis = fideicommisService.searchFideicommis(searchCriteria);

            if (matchingFideicommis.isEmpty()) {
                // Si la liste est vide, retourner un statut "NO_CONTENT"
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"No fideicommis found.\"}");
            } else {
                // Si la liste contient des Fideicommis correspondants, retourner les Fideicommis avec un statut "OK"
                return ResponseEntity.ok(matchingFideicommis);
            }
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée en cas d'erreur interne du serveur
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu.\"}");
        }
    }
}
