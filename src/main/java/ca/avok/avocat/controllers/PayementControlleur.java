package ca.avok.avocat.controllers;

import ca.avok.avocat.entities.Payement;
import ca.avok.avocat.enumeration.ModePay;
import ca.avok.avocat.services.PayementServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/Payement")
@AllArgsConstructor
public class PayementControlleur {
    PayementServiceImpl payementService;

    @GetMapping("/afficherAllPayements")
    public ResponseEntity<Object> afficherToutesLesPayements() {
        try {
            List<Payement> payements = payementService.afficherAllPayements();

            if (payements.isEmpty()) {
                // Si la liste des Payements est vide, renvoyer une réponse avec le statut NOT_FOUND (404) et un message d'erreur
                String successMessage = "Aucune Payement trouvée.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            } else {
                // Si la liste des payements n'est pas vide, renvoyer une réponse avec les Payements et le statut OK (200)
                return ResponseEntity.ok(payements);
            }
        } catch (Exception e) {
            // En cas d'exception, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
            String errorMessage = "Une erreur est survenue lors de la récupération des payements.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }
    @GetMapping("/{idPayement}")
    public ResponseEntity<?> afficherPayementParId(@PathVariable Integer idPayement) {
        try {
            Payement payement = payementService.afficherPayement(idPayement);

            if (payement != null) {
                // Si la payement est trouvée, renvoyer une réponse avec la payement et le statut OK (200)
                return ResponseEntity.ok(payement);
            } else {
                // Si la payement n'est pas trouvée, renvoyer une réponse avec le statut NOT_FOUND (404) et un message d'erreur
                String successMessage = "Le payement avec l'ID " + idPayement + " n'existe pas.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            }
        } catch (Exception e) {
            // En cas d'exception, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
            String errorMessage = "Une erreur est survenue lors de la récupération du payement.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }
    @PostMapping("/add")
    public ResponseEntity<Object> ajouterPayement(@RequestBody Payement payement) {
        try {
            Payement nouvellePayement = payementService.ajouterPayement(payement);
            if (nouvellePayement != null) {
                // Si le payement est ajoutée avec succès, renvoyer une réponse avec la nouvelle tâche et le statut CREATED (201)
                String successMessage = "payement ajoutée avec succès.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            } else {
                // Si l'ajout du payement a échoué, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
                String errorMessage = "Erreur lors de l'ajout du payement.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
            }
        } catch (Exception e) {
            // En cas d'exception, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
            String errorMessage = "Une erreur est survenue lors de l'ajout du payement.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }
    @PutMapping("/{idPayemnt}")
    public ResponseEntity<Object> modifierPayement(@PathVariable Integer idPayemnt, @RequestBody Payement payement) {
        try {
            payement.setIdPayement(idPayemnt);
            Payement payementModifiee = payementService.modifierPayement(payement);

            if (payementModifiee != null) {
                // Si le payement est modifiée avec succès, renvoyer une réponse avec la tâche modifiée et le statut OK (200)
                return ResponseEntity.ok(payementModifiee);
            } else {
                // Si le payement n'est pas trouvée (ou s'il y a eu une erreur de modification), renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
                String successMessage = "payement non trouvé.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            }
        } catch (Exception e) {
            // En cas d'exception, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
            String errorMessage = "Une erreur est survenue lors de la modification du payement.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }
        @DeleteMapping("/{idPayement}")
        public ResponseEntity<String> supprimerPayement(@PathVariable Integer idPayement) {
            try {
                Payement payement = payementService.afficherPayement(idPayement);
                if (payement != null) {
                    // Si le paiement est trouvé, le supprimer
                    payementService.supprimerPayement(idPayement);
                    // Renvoyer une réponse avec le statut OK (200) et un message de succès
                    String successMessage = "Paiement supprimé avec succès.";
                    return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
                } else {
                    // Si le paiement n'est pas trouvé, renvoyer une réponse avec le statut NOT_FOUND (404) et un message d'erreur
                    String successMessage = "Le paiement avec l'ID " + idPayement + " n'existe pas.";
                    return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
                }
            } catch (Exception e) {
                // En cas d'exception, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
                String errorMessage = "Une erreur est survenue lors de la suppression du paiement.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
            }
        }

    @GetMapping("/search-payements")
    public ResponseEntity<Object> searchPayements(
            @RequestParam(required = false) Integer idPayement,
            @RequestParam(required = false) LocalDateTime datePayement,
            @RequestParam(required = false) String montant,
            @RequestParam(required = false) ModePay modePayement
    ) {
        try {
            List<Payement> payements = payementService.searchPayementByAttributes(idPayement, datePayement, montant, modePayement);

            if (payements.isEmpty()) {
                // Si la liste des paiements est vide, renvoyer une réponse avec le statut NOT_FOUND (404) et un message d'erreur
                String successMessage = "Aucun paiement trouvé.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            } else {
                // Si la liste des paiements n'est pas vide, renvoyer une réponse avec les paiements et le statut OK (200)
                return ResponseEntity.ok(payements);
            }
        } catch (Exception e) {
            // En cas d'erreur, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
            String errorMessage = "Une erreur s'est produite lors de la recherche des paiements.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
        }
    }



