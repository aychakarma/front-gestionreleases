package ca.avok.avocat.controllers;

import ca.avok.avocat.entities.Depense;
import ca.avok.avocat.enumeration.StatusPay;
import ca.avok.avocat.services.DepenseServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/Depense")
@AllArgsConstructor
public class DepenseController {
    DepenseServiceImpl depenseService;
    @GetMapping("/all")
    public ResponseEntity<Object> getAllDepenses() {
        try {
            List<Depense> depenses = depenseService.afficherAllDepenses();

            if (depenses.isEmpty()) {
                // Si la liste des dépenses est vide, renvoyer une réponse avec le statut NOT_FOUND (404) et un message d'erreur
                String successMessage = "Aucune dépense trouvée.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            } else {
                // Si la liste des dépenses n'est pas vide, renvoyer une réponse avec les dépenses et le statut OK (200)
                return ResponseEntity.ok(depenses);
            }
        } catch (Exception e) {
            // En cas d'erreur, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur personnalisé
            String errorMessage = "Une erreur s'est produite lors de la récupération des dépenses.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }

    @GetMapping("/{idDepense}")
    public ResponseEntity<Object> getDepense(@PathVariable Integer idDepense) {
        try {
            Depense depense = depenseService.afficherDepense(idDepense);

            if (depense != null) {
                // Si la dépense est trouvée, renvoyer une réponse avec la dépense et le statut OK (200)
                return ResponseEntity.ok(depense);
            } else {
                // Si la dépense n'est pas trouvée, renvoyer une réponse avec le statut OK (200) et un message d'erreur
                String successMessage = "Dépense non trouvée.";
                return ResponseEntity.ok("{\"message\": \"" + successMessage + "\"}");
            }
        } catch (Exception e) {
            // En cas d'erreur, renvoyer une réponse avec le statut OK (200) et un message d'erreur personnalisé
            String errorMessage = "Une erreur s'est produite lors de la récupération de la dépense.";
            return ResponseEntity.ok("{\"error\": \"" + errorMessage + "\"}");
        }
    }


    @PostMapping("/ajouter")
    public ResponseEntity<Object> addDepense(@RequestBody Depense depense) {
        try {
            Depense nouvelleDepense = depenseService.ajouterDepense(depense);

            if (nouvelleDepense != null) {
                // Si la dépense est ajoutée avec succès, renvoyer une réponse avec la nouvelle dépense et le statut CREATED (201)
                String successMessage = "Dépense ajoutée avec succès.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            } else {
                // Si l'ajout de la dépense a échoué, renvoyer une réponse avec le statut OK (200) et un message d'erreur
                String errorMessage = "Erreur lors de l'ajout de la dépense.";
                return ResponseEntity.ok("{\"error\": \"" + errorMessage + "\"}");
            }
        } catch (Exception e) {
            // En cas d'erreur, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
            String errorMessage = "Une erreur s'est produite lors de l'ajout de la dépense.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }


    @PutMapping("/modifier")
    public ResponseEntity<Object> updateDepense(@RequestBody Depense depense) {
        try {
            Depense depenseModifiee = depenseService.modifierDepense(depense);

            if (depenseModifiee != null) {
                // Si la dépense est modifiée avec succès, renvoyer une réponse avec la dépense modifiée et le statut OK (200)
                String successMessage = "Dépense modifiée avec succès.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            } else {
                // Si la dépense n'est pas trouvée (ou s'il y a eu une erreur de modification), renvoyer une réponse avec le statut NOT_FOUND (404) et un message d'erreur
                String successMessage = "dépense non trouvé.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            }
        } catch (Exception e) {
            // En cas d'erreur, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
            String errorMessage = "Une erreur s'est produite lors de la modification de la dépense.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }

    @DeleteMapping("/supprimer/{idDepense}")
    public ResponseEntity<Object> deleteDepense(@PathVariable Integer idDepense) {
        try {
            Depense depense = depenseService.afficherDepense(idDepense);

            if (depense != null) {
                // Si la dépense est trouvée, la supprimer
                depenseService.supprimerDepense(idDepense);
                // Renvoyer une réponse avec le statut OK (200) et un message de succès
                String successMessage = "Dépense supprimée avec succès.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            } else {
                // Si la dépense n'est pas trouvée, renvoyer une réponse avec le statut NOT_FOUND (404) et un message d'erreur
                String successMessage = "La dépense avec l'ID " + idDepense + " n'existe pas.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            }
        } catch (Exception e) {
            // En cas d'erreur, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
            String errorMessage = "Une erreur s'est produite lors de la suppression de la dépense.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }

    @PutMapping("/paye")
    public ResponseEntity<Object> depensePaye(@RequestBody Depense depense) {
        try {
            depenseService.depensePaye(depense);

            // Renvoyer une réponse avec le statut OK (200) et un message de succès
            String successMessage = "La dépense a été marquée comme payée avec succès.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
        } catch (Exception e) {
            // En cas d'erreur, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
            String errorMessage = "Une erreur s'est produite lors du marquage de la dépense comme payée.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }

    @PutMapping("/non-paye")
    public ResponseEntity<Object> depenseNonPaye(@RequestBody Depense depense) {
        try {
            depenseService.depenseNonPaye(depense);

            // Renvoyer une réponse avec le statut OK (200) et un message de succès
            String successMessage = "La dépense a été marquée comme non payée avec succès.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
        } catch (Exception e) {
            // En cas d'erreur, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
            String errorMessage = "Une erreur s'est produite lors du marquage de la dépense comme non payée.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchDepensesByAttributes(@RequestParam(required = false) String objet,
                                                             @RequestParam(required = false) LocalDateTime dateDepense,
                                                             @RequestParam(required = false) String numFacture,
                                                             @RequestParam(required = false) Float montant,
                                                             @RequestParam(required = false) Float tps,
                                                             @RequestParam(required = false) Float tpq,
                                                             @RequestParam(required = false) Float frais,
                                                             @RequestParam(required = false) String commentaire,
                                                             @RequestParam(required = false) StatusPay status) {
        try {
            List<Depense> depenses = depenseService.searchsearchDepensesByAttributes(objet, dateDepense, numFacture, montant, tps, tpq, frais, commentaire, status);

            if (!depenses.isEmpty()) {
                // Si des dépenses sont trouvées, renvoyer une réponse avec la liste des dépenses et le statut OK (200)
                return ResponseEntity.ok(depenses);
            } else {
                // Si aucune dépense n'est trouvée, renvoyer une réponse avec le statut NOT_FOUND (404) et un message d'erreur
                String successMessage = "Aucune dépense trouvée pour les critères spécifiés.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            }
        } catch (Exception e) {
            // En cas d'erreur, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
            String errorMessage = "Une erreur s'est produite lors de la recherche des dépenses.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }
}
