package ca.avok.avocat.controllers;

import ca.avok.avocat.entities.Audition;
import ca.avok.avocat.entities.Dossier;
import ca.avok.avocat.entities.Intervenant;
import ca.avok.avocat.repositories.DossierRepos;
import ca.avok.avocat.services.DossierServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/Dossier")
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:4200/","http://localhost:4300/"})
public class DossierController {

    DossierServiceImpl dossierService;
    DossierRepos dossierRepos;

    //-----------------------------------CRUD begins-----------------------------------//
    @GetMapping("/getAllDossier")
    public ResponseEntity<?> readAllDossier() {
        List<Dossier> dossierList = dossierService.readAllDossier();

        if (!dossierList.isEmpty()) {
            // If the list of dossiers is not empty, return it as part of the response body with an HTTP 200 OK status code.
            return ResponseEntity.ok(dossierList);
        } else {
            // If the list of dossiers is empty, return an HTTP 404 NOT FOUND response with an appropriate error message.
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"No dossiers found.\"}");
        }
    }

    @PostMapping("/addDossier")
    public ResponseEntity<?> createDossier(@RequestBody Dossier dossier) {
        try {
            try{
                dossierService.readDossier(dossier.getNumDossier());
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"ce dossier existe déjà\"}");
            } catch (Exception exception){
                dossierService.createDossier(dossier);
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"dossier ajouté avec succés\"}");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervienu\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervienu\"}");
        }
    }

    @PutMapping("/updateDossier")
    public ResponseEntity<?> updateDossier(@RequestBody Dossier dossier) {
        // Check if the dossier with the given ID exists in the database
        Optional<Dossier> optionalDossier = dossierRepos.findByNumDossier(dossier.getNumDossier());

        if (optionalDossier.isPresent()) {
            // If the dossier with the given ID exists, update it and return an HTTP 200 OK response.
            Dossier updatedDossier = dossierService.updateDossier(dossier);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"dossier mis à jour avec succés\"}");
        } else {
            // If the dossier with the given ID does not exist, return an HTTP 404 NOT FOUND response with an appropriate error message.
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"dossier not found\"}");
        }
    }

    @GetMapping("/getDossier/{idDossier}")
    public ResponseEntity<?> readDossier(@PathVariable("idDossier") String numDossier) {
        Optional<Dossier> optionalDossier = dossierRepos.findByNumDossier(numDossier);

        if (optionalDossier.isPresent()) {
            return ResponseEntity.ok(dossierService.readDossier(numDossier));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"dossier not found\"}");
        }
    }

    @DeleteMapping("/removeDossier/{idDossier}")
    public ResponseEntity<?> deleteDossier(@PathVariable("idDossier") String numDossier) {
        // Check if the dossier with the given number exists in the database
        Optional<Dossier> optionalDossier = dossierRepos.findByNumDossier(numDossier);

        if (optionalDossier.isPresent()) {
            // If the dossier with the given number exists, delete it and return an HTTP 204 NO CONTENT response.
            dossierService.deleteDossier(numDossier);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"dossier supprimé avec succés\"}");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"dossier not found\"}");
        }
    }

    //-----------------------------------CRUD ends-----------------------------------//

    // Méthode pour afficher les 5 derniers dossiers ouverts
    @GetMapping("/getLatest5OpenedDossiers")
    public ResponseEntity<List<Dossier>> getLatest5OpenedDossiers() {
            List<Dossier> latest5Dossiers = dossierService.getLatest5OpenedDossiers();

            if (latest5Dossiers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(latest5Dossiers, HttpStatus.OK);
    }

    // Méthode pour fermer un dossier(changer la dateFermeture à la date de l'appel de la méthode)
    @PostMapping("/fermerDossier")
    public ResponseEntity<?> fermerDossier(@RequestParam("numDossier") String numDossier) {
        try {
            dossierService.fermerDossier(numDossier);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Dossier fermé avec succès.\"}");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"dossier not found\"}");
        }
    }

    // Méthode de recherche à partir de n'importe quel attribut du dossier
    @GetMapping("/search")
    public ResponseEntity<List<Dossier>> searchDossier(@RequestParam("criteria") String searchCriteria) {
        List<Dossier> matchingDossiers = dossierService.searchDossier(searchCriteria);

        if (matchingDossiers.isEmpty()) {
            // Si la liste est vide, retourner un statut "NO_CONTENT"
            return ResponseEntity.noContent().build();
        } else {
            // Si la liste contient des dossiers correspondants, retourner les dossiers avec un statut "OK"
            return ResponseEntity.ok(matchingDossiers);
        }
    }

    //Afficher seulement les dossiers ouvertes
    @GetMapping("/afficherDossiersOuvertes")
    public ResponseEntity<?> afficherDossiersOuvertes() {
        try {
            List<Dossier> dossiersOuvertes = dossierService.afficherDossiersOuvertes();
                return ResponseEntity.ok(dossiersOuvertes);
            } catch (Exception ex) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"Erreur lors de l'afficahge.\"}");
        }
    }

    //Afficher seulement les dossiers fermees
    @GetMapping("/afficherDossiersFermees")
    public ResponseEntity<?> afficherDossiersFermees() {
        try {
            List<Dossier> dossiersFermees = dossierService.afficherDossiersfermees();
            return ResponseEntity.ok(dossiersFermees);
        } catch (Exception ex) {
            // Gérer l'exception et renvoyer une réponse appropriée
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"Erreur lors de l'afficahge.\"}");
        }
    }

    // Afficher les intervenants associés à un dossier spécifique
    @GetMapping("/afficherIntervenants")
    public ResponseEntity<?> afficherIntervenants(@RequestParam("numDossier") String numDossier) {
        try {
            List<Intervenant> intervenants = dossierService.afficherIntervenants(numDossier);

            if (intervenants.isEmpty()) {
                // Si la liste est vide, retourner un statut "NO_CONTENT"
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"no intervenants found in this dossier\"}");
            } else {
                // Si la liste contient des intervenants, retourner les intervenants avec un statut "OK"
                return ResponseEntity.ok(intervenants);
            }
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée en cas d'erreur interne du serveur
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu.\"}");
        }
    }

    // Afficher les auditions associés à un dossier spécifique
    @GetMapping("/afficherAuditions")
    public ResponseEntity<?> afficherAuditions(@RequestParam("numDossier") String numDossier) {
        try {
            List<Audition> auditions = dossierService.afficherAuditions(numDossier);

            if (auditions.isEmpty()) {
                // Si la liste est vide, retourner un statut "NO_CONTENT"
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"no auditions found in this dossier\"}");
            } else {
                // Si la liste contient des intervenants, retourner les intervenants avec un statut "OK"
                return ResponseEntity.ok(auditions);
            }
        } catch (Exception e) {
            // Gérer l'exception et renvoyer une réponse appropriée en cas d'erreur interne du serveur
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"une erreur s'est intervenu.\"}");
        }
    }

    // Méthode pour affecter un intervenant à un dossier
    @PostMapping("/affecterIntervenant/{numString}/{idIntervenant}")
    public ResponseEntity<String> affecterIntervenant(@PathVariable String numString, @PathVariable String idIntervenant) {
        try {
            dossierService.affecterIntervenant(numString, idIntervenant);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Intervenant affecté avec succès au dossier\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"Erreur lors de l'affectation de l'intervenant au dossier\"}");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Dossier ou intervenant non trouvé\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Une erreur s'est produite\"}");
        }
    }

}
