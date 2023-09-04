package ca.avok.avocat.controllers;

import ca.avok.avocat.entities.Facture;
import ca.avok.avocat.services.FactureServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/Facture")
@AllArgsConstructor
public class FactureController {
    FactureServiceImpl factureService;



    @GetMapping("/afficherAllFactures")
    public ResponseEntity<Object> afficherToutesLesFactures() {
        try {
            List<Facture> factures = factureService.afficherAllFacture();

            if (factures.isEmpty()) {
                // Si la liste des factures est vide, renvoyer une réponse avec le statut NOT_FOUND (404) et un message d'erreur
                String successMessage = "Aucune facture trouvée.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            } else {
                // Si la liste des factures n'est pas vide, renvoyer une réponse avec les tâches et le statut OK (200)
                return ResponseEntity.ok(factures);
            }
        } catch (Exception e) {
            // En cas d'exception, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
            String errorMessage = "Une erreur est survenue lors de la récupération des factures.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }



    @GetMapping("/{idFacture}")
    public ResponseEntity<?> afficherFactureParId(@PathVariable Integer idFacture) {
        try {
            Facture facture = factureService.afficherFacture(idFacture);

            if (facture != null) {
                // Si la facture est trouvée, renvoyer une réponse avec la facture et le statut OK (200)
                return ResponseEntity.ok(facture);
            } else {
                // Si la facture n'est pas trouvée, renvoyer une réponse avec le statut NOT_FOUND (404) et un message d'erreur
                String successMessage = "La facture avec l'ID " + idFacture + " n'existe pas.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            }
        } catch (Exception e) {
            // En cas d'exception, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
            String errorMessage = "Une erreur est survenue lors de la récupération de la facture.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }




    @PostMapping("/add")
    public ResponseEntity<Object> ajouterFacture(@RequestBody Facture facture) {
        try {
            Facture nouvelleFacture = factureService.ajouterFacture(facture);
            if (nouvelleFacture != null) {
                // Si la facture est ajoutée avec succès, renvoyer une réponse avec la nouvelle tâche et le statut CREATED (201)
                String successMessage = "Facture ajoutée avec succès.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            } else {
                // Si l'ajout de la facture a échoué, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
                String errorMessage = "Erreur lors de l'ajout de la facture.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
            }
        } catch (Exception e) {
            // En cas d'exception, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
            String errorMessage = "Une erreur est survenue lors de l'ajout de la facture.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }

    @PutMapping("/{idFacture}")
    public ResponseEntity<Object> modifierFacture(@PathVariable Integer idFacture, @RequestBody Facture facture) {
        try {
            facture.setIdFacture(idFacture);
            Facture factureModifiee = factureService.modifierFacture(facture);

            if (factureModifiee != null) {
                // Si la facture est modifiée avec succès, renvoyer une réponse avec la tâche modifiée et le statut OK (200)
                return ResponseEntity.ok(factureModifiee);
            } else {
                // Si la facture n'est pas trouvée (ou s'il y a eu une erreur de modification), renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
                String successMessage = "facture non trouvé.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            }
        } catch (Exception e) {
            // En cas d'exception, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
            String errorMessage = "Une erreur est survenue lors de la modification de la facture.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }


    @DeleteMapping("/{idFacture}")
    public ResponseEntity<String> supprimerFacture(@PathVariable Integer idFacture) {
        try {
            Facture facture = factureService.afficherFacture(idFacture);
            if (facture != null) {
                // Si la facture est trouvée, la supprimer
                factureService.supprimerFacture(idFacture);
                // Renvoyer une réponse avec le statut OK (200) et un message de succès
                String successMessage = "Facture supprimée avec succès.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            } else {
                // Si la facture n'est pas trouvée, renvoyer une réponse avec le statut NOT_FOUND (404) et un message d'erreur
                String successMessage = "La facture avec l'ID " + idFacture + " n'existe pas.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            }
        } catch (Exception e) {
            // En cas d'exception, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
            String errorMessage = "Une erreur est survenue lors de la suppression de la facture.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }
    @GetMapping("/search-factures")
    public ResponseEntity<Object> searchFactures(@RequestParam(required = false) LocalDateTime dateFacture,
                                                 @RequestParam(required = false) String commentaire) {
        try {
            List<Facture> factures = factureService.searchFacturesByAttributes(dateFacture, commentaire);

            if (factures.isEmpty()) {
                String successMessage = "Aucune facture trouvée.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            } else {
                return ResponseEntity.ok(factures);
            }
        } catch (Exception e) {
            String errorMessage = "Une erreur s'est produite lors de la recherche des factures.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }

}
