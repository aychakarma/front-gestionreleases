package ca.avok.avocat.controllers;

import ca.avok.avocat.entities.Tache;
import ca.avok.avocat.services.TacheServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/Tache")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200/","http://localhost:4300/"})

public class TacheController {
     TacheServiceImpl tacheService;

     @GetMapping("/afficherAllTaches")
     public ResponseEntity<Object> afficherToutesLesTaches() {
          try {
               List<Tache> taches = tacheService.afficherAllTaches();

               if (taches.isEmpty()) {
                    // Si la liste des tâches est vide, renvoyer une réponse avec le statut NOT_FOUND (404) et un message d'erreur
                    String successMessage = "Aucune tâche trouvée.";
                    return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
               } else {
                    // Si la liste des tâches n'est pas vide, renvoyer une réponse avec les tâches et le statut OK (200)
                    return ResponseEntity.ok(taches);
               }
          } catch (Exception e) {
               // En cas d'erreur, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
               String errorMessage = "Une erreur s'est produite lors de la récupération des tâches.";
               return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
          }
     }

     @GetMapping("/{idTache}")
     public ResponseEntity<Object> afficherTacheParId(@PathVariable Integer idTache) {
          try {
               Tache tache = tacheService.afiicherTache(idTache);

               if (tache != null) {
                    // Si la tâche est trouvée, renvoyer une réponse avec la tâche et le statut OK (200)
                    return ResponseEntity.ok(tache);
               } else {
                    // Si la tâche n'est pas trouvée, renvoyer une réponse avec le statut NOT_FOUND (404) et un message d'erreur
                    String successMessage = "Tâche non trouvée.";
                    return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
               }
          } catch (Exception e) {
               // En cas d'erreur, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
               String errorMessage = "Une erreur s'est produite lors de la récupération de la tâche.";
               return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
          }
     }

     @PostMapping("/add")
     public ResponseEntity<Object> ajouterTache(@RequestBody Tache tache) {
          try {
               Tache nouvelleTache = tacheService.ajouterTache(tache);

               if (nouvelleTache != null) {
                    // Si la tâche est ajoutée avec succès, renvoyer une réponse avec la nouvelle tâche et le statut CREATED (201)
                    String successMessage = "Tâche ajoutée avec succès.";
                    return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
               } else {
                    // Si l'ajout de la tâche a échoué, renvoyer une réponse avec le statut BAD_REQUEST (400) et un message d'erreur
                    String errorMessage = "Erreur lors de l'ajout de la tâche.";
                    return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
               }
          } catch (Exception e) {
               // En cas d'erreur, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
               String errorMessage = "Une erreur s'est produite lors de l'ajout de la tâche.";
               return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
          }
     }

     @PutMapping("/{idTache}")
     public ResponseEntity<Object> modifierTache(@PathVariable Integer idTache, @RequestBody Tache tache) {
          try {
               tache.setIdTache(idTache);
               Tache tacheModifiee = tacheService.modifierTache(tache);

               if (tacheModifiee != null) {
                    // Si la tâche est modifiée avec succès, renvoyer une réponse avec la tâche modifiée et le statut OK (200)
                    return ResponseEntity.ok(tacheModifiee);
               } else {
                    // Si la tâche n'est pas trouvée (ou s'il y a eu une erreur de modification), renvoyer une réponse avec le statut NOT_FOUND (404) et un message d'erreur
                    String successMessage = " tâche non trouvé .";
                    return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
               }
          } catch (Exception e) {
               // En cas d'erreur, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
               String errorMessage = "Une erreur s'est produite lors de la modification de la tâche.";
               return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
          }
     }
     @GetMapping("/taches-du-jour")
     public ResponseEntity<?> getTachesDuJour() {
          try {
               List<Tache> tachesDuJour = tacheService.getTachesDuJour();

               if (tachesDuJour.isEmpty()) {
                    // Aucune tâche aujourd'hui, renvoyer une réponse 404 Not Found avec un message d'erreur
                    String successMessage = "Aucune tâche trouvée pour aujourd'hui.";
                    return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
               } else {
                    // Renvoyer la liste des tâches du jour
                    return ResponseEntity.ok(tachesDuJour);
               }
          } catch (Exception e) {
               // En cas d'erreur, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
               String errorMessage = "Une erreur s'est produite lors de la récupération des tâches du jour.";
               return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
          }
     }

     @PutMapping("/terminerTache/{idTache}")
     public ResponseEntity<String> terminerTache(@PathVariable int idTache) {
          try {
               tacheService.terminerTache(idTache);
               return ResponseEntity.ok("Tâche terminée avec succès.");
          } catch (NoSuchElementException ex) {
               String successMessage = "La tâche avec l'ID spécifié n'existe pas.";
               return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
          } catch (Exception ex) {
               String errorMessage = "Une erreur est survenue lors de la terminaison de la tâche.";
               return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
          }
     }
     @GetMapping("/search-taches")
     public ResponseEntity<Object> searchTaches(
             @RequestParam(required = false) String title,
             @RequestParam(required = false) LocalDateTime dateDebut,
             @RequestParam(required = false) LocalDateTime dateFin,
             @RequestParam(required = false) String statut,
             @RequestParam(required = false) String description,
             @RequestParam(required = false) String duree
     ) {
          try {
               List<Tache> taches = tacheService.searchTachesByAttributes(title, dateDebut, dateFin, statut, description, duree);

               if (taches.isEmpty()) {
                    // Si la liste des tâches est vide, renvoyer une réponse avec le statut NOT_FOUND (404) et un message d'erreur
                    String successMessage = "Aucune tâche trouvée.";
                    return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
               } else {
                    // Si la liste des tâches n'est pas vide, renvoyer une réponse avec les tâches et le statut OK (200)
                    return ResponseEntity.ok(taches);
               }
          } catch (Exception e) {
               // En cas d'erreur, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
               String errorMessage = "Une erreur s'est produite lors de la recherche des tâches.";
               return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
          }
     }

     @DeleteMapping("/{idTache}")
     public ResponseEntity<String> supprimerTache(@PathVariable Integer idTache) {
          try {
               Tache tache = tacheService.afiicherTache(idTache);

               if (tache != null) {
                    // Si la tâche est trouvée, la supprimer
                    tacheService.supprimerTache(idTache);
                    // Renvoyer une réponse avec le statut OK (200) et un message de succès
                    String successMessage = "Tache supprimee avec succes.";
                    return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
               } else {
                    // Si la tâche n'est pas trouvée, renvoyer une réponse avec le statut NOT_FOUND (404) et un message d'erreur
                    String successMessage = "La tache avec l'ID " + idTache + " n'existe pas.";
                    return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
               }
          } catch (Exception e) {
               // En cas d'erreur, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
               String errorMessage = "Une erreur s'est produite lors de la suppression de la tâche.";
               return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
          }
     }
     }




