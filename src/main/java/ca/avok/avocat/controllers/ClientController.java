package ca.avok.avocat.controllers;

import ca.avok.avocat.entities.Client;
import ca.avok.avocat.services.ClientServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/Client")
@AllArgsConstructor
public class ClientController {
    ClientServiceImpl clientService;
    @GetMapping("/api/afficherAllClients")
    public ResponseEntity<Object> afficherToutesLesClients() {
        try {
            List<Client> clients = clientService.afficherAllClients();

            if (clients.isEmpty()) {
                // Si la liste des clients est vide, renvoyer une réponse avec le statut NOT_FOUND (404) et un message d'erreur
                String successMessage = "Aucun client trouvé.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            } else {
                // Si la liste des clients n'est pas vide, renvoyer une réponse avec les clients et le statut OK (200)
                return ResponseEntity.ok(clients);
            }
        } catch (Exception ex) {
            // En cas d'exception, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
            String errorMessage = "Une erreur est survenue lors de la récupération des clients.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }

    @GetMapping("/{idClient}")
    public ResponseEntity<Object> afficherClientParId(@PathVariable Integer idClient) {
        try {
            Client client = clientService.afficherClient(idClient);

            if (client != null) {
                return ResponseEntity.ok(client);
            } else {
                String successMessage = "Client non trouvé.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            }
        } catch (Exception ex) {
            String errorMessage = "Une erreur est survenue lors de la récupération du client.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> ajouterClient(@RequestBody Client client) {
        try {
            Client nouvelleClient = clientService.ajouterClient(client);

            if (nouvelleClient != null) {
                String successMessage = "Client ajouté avec succès.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            } else {
                String successMessage = "Erreur lors de l'ajout du client.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            }
        } catch (Exception ex) {
            String errorMessage = "Une erreur est survenue lors de l'ajout du client.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }

    @PutMapping("/{idClient}")
    public ResponseEntity<Object> modifierTache(@PathVariable Integer idClient, @RequestBody Client client) {
        try {
            client.setIdClient(idClient);
            Client clientModifiee = clientService.modifierClient(client);

            if (clientModifiee != null) {
                return ResponseEntity.ok(clientModifiee);
            } else {
                String successMessage = "client non trouvé.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            }
        } catch (Exception ex) {
            String errorMessage = "Une erreur est survenue lors de la modification du client.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }

    @DeleteMapping("/{idClient}")
    public ResponseEntity<String> supprimerClient(@PathVariable Integer idClient) {
        try {
            Client client = clientService.afficherClient(idClient);

            if (client != null) {
                // Si le client est trouvé, le supprimer
                clientService.supprimerClient(idClient);
                // Renvoyer une réponse avec le statut OK (200) et un message de succès
                String successMessage = "Client ajouté avec succès.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            } else {
                // Si le client n'est pas trouvé, renvoyer une réponse avec le statut NOT_FOUND (404) et un message d'erreur
                String successMessage = "Le client avec l'ID " + idClient + " n'existe pas.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            }
        } catch (Exception ex) {
            // En cas d'exception, renvoyer une réponse avec le statut INTERNAL_SERVER_ERROR (500) et un message d'erreur
            String errorMessage = "Une erreur est survenue lors de la suppression du client.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }
    @GetMapping("/search-clients")
    public ResponseEntity<Object> searchClients(@RequestParam(required = false) String nomPrenom,
                                                @RequestParam(required = false) String email1,
                                                @RequestParam(required = false) String email2,
                                                @RequestParam(required = false) String telephone,
                                                @RequestParam(required = false) String dateDeNaissance,
                                                @RequestParam(required = false) String civilite,
                                                @RequestParam(required = false) String poste,
                                                @RequestParam(required = false) String faxe,
                                                @RequestParam(required = false) String cellulaire,
                                                @RequestParam(required = false) String adresse1,
                                                @RequestParam(required = false) String adresse2,
                                                @RequestParam(required = false) String pays,
                                                @RequestParam(required = false) String province,
                                                @RequestParam(required = false) String commentaire) {
        try {
            LocalDateTime parsedDateDeNaissance = null;
            if (dateDeNaissance != null) {
                parsedDateDeNaissance = LocalDateTime.parse(dateDeNaissance);
            }

            List<Client> clients = clientService.searchClientsByAttributes(nomPrenom, email1, email2, telephone, parsedDateDeNaissance,
                    civilite, poste, faxe, cellulaire, adresse1, adresse2,
                    pays, province, commentaire);

            if (clients.isEmpty()) {
                String successMessage = "Aucun client trouvé.";
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + successMessage + "\"}");
            } else {
                return ResponseEntity.ok(clients);
            }
        } catch (Exception e) {
            String errorMessage = "Une erreur s'est produite lors de la recherche des clients.";
            return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"" + errorMessage + "\"}");
        }
    }
}
