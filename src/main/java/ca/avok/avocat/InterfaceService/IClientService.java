package ca.avok.avocat.InterfaceService;

import ca.avok.avocat.entities.Client;

import java.time.LocalDateTime;
import java.util.List;

public interface IClientService {
    List<Client> afficherAllClients();
    Client afficherClient(Integer idClient);
    Client ajouterClient(Client c);
    Client modifierClient(Client c);
    void supprimerClient(Integer idClient);
    List<Client> searchClientsByAttributes(String nomPrenom, String email1, String email2,
                                           String telephone, LocalDateTime dateDeNaissance,
                                           String civilite, String poste, String faxe,
                                           String cellulaire, String adresse1, String adresse2,
                                           String pays, String province, String commentaire);
}
