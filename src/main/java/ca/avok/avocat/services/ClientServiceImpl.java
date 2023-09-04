package ca.avok.avocat.services;

import ca.avok.avocat.InterfaceService.IClientService;
import ca.avok.avocat.entities.Client;
import ca.avok.avocat.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements IClientService {
    ClientRepository clientRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Client> afficherAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client afficherClient(Integer idClient) {
        Optional<Client> clientOptional = clientRepository.findById(idClient);
        return clientOptional.orElse(null);
    }

    @Override
    public Client ajouterClient(Client c) {
        return clientRepository.save(c);
    }

    @Override
    public Client modifierClient(Client c) {
        Optional<Client> clientOptional = clientRepository.findById(c.getIdClient());
        if (clientOptional.isPresent()) {
            Client clientExistante = clientOptional.get();
            clientExistante.setNom_prenom(c.getNom_prenom());
            clientExistante.setEmail1(c.getEmail1());
            clientExistante.setEmail2(c.getEmail1());
            clientExistante.setTelephone(c.getTelephone());
            clientExistante.setDateDeNaissance(c.getDateDeNaissance());
            clientExistante.setCivilite(c.getCivilite());
            clientExistante.setPoste(c.getPoste());
            clientExistante.setFaxe(c.getFaxe());
            clientExistante.setCellulaire(c.getCellulaire());
            clientExistante.setAdresse1(c.getAdresse1());
            clientExistante.setAdresse2(c.getAdresse2());
            clientExistante.setPays(c.getPays());
            clientExistante.setProvince(c.getProvince());
            clientExistante.setCommentaire(c.getCommentaire());
            return clientRepository.save(clientExistante);
        } else {
            return null;
        }

    }

    @Override
    public void supprimerClient(Integer idClient) {

    }

    @Override
    public List<Client> searchClientsByAttributes(String nomPrenom, String email1, String email2, String telephone,
                                                  LocalDateTime dateDeNaissance, String civilite, String poste,
                                                  String faxe, String cellulaire, String adresse1, String adresse2,
                                                  String pays, String province, String commentaire) {
        StringBuilder queryString = new StringBuilder("SELECT c FROM Client c WHERE 1=1");
        List<String> parameters = new ArrayList<>();

        if (nomPrenom != null && !nomPrenom.isEmpty()) {
            queryString.append(" AND UPPER(c.nomPrenom) = :nomPrenom");
            parameters.add("nomPrenom");
        }

        if (email1 != null && !email1.isEmpty()) {
            queryString.append(" AND UPPER(c.email1) = :email1");
            parameters.add("email1");
        }

        if (email2 != null && !email2.isEmpty()) {
            queryString.append(" AND UPPER(c.email2) = :email2");
            parameters.add("email2");
        }

        if (telephone != null && !telephone.isEmpty()) {
            queryString.append(" AND c.telephone = :telephone");
            parameters.add("telephone");
        }

        if (dateDeNaissance != null) {
            queryString.append(" AND c.dateDeNaissance = :dateDeNaissance");
            parameters.add("dateDeNaissance");
        }

        if (civilite != null && !civilite.isEmpty()) {
            queryString.append(" AND UPPER(c.civilite) = :civilite");
            parameters.add("civilite");
        }

        if (poste != null && !poste.isEmpty()) {
            queryString.append(" AND UPPER(c.poste) = :poste");
            parameters.add("poste");
        }

        if (faxe != null && !faxe.isEmpty()) {
            queryString.append(" AND UPPER(c.faxe) = :faxe");
            parameters.add("faxe");
        }

        if (cellulaire != null && !cellulaire.isEmpty()) {
            queryString.append(" AND UPPER(c.cellulaire) = :cellulaire");
            parameters.add("cellulaire");
        }

        if (adresse1 != null && !adresse1.isEmpty()) {
            queryString.append(" AND UPPER(c.adresse1) = :adresse1");
            parameters.add("adresse1");
        }

        if (adresse2 != null && !adresse2.isEmpty()) {
            queryString.append(" AND UPPER(c.adresse2) = :adresse2");
            parameters.add("adresse2");
        }

        if (pays != null && !pays.isEmpty()) {
            queryString.append(" AND UPPER(c.pays) = :pays");
            parameters.add("pays");
        }

        if (province != null && !province.isEmpty()) {
            queryString.append(" AND UPPER(c.province) = :province");
            parameters.add("province");
        }

        if (commentaire != null && !commentaire.isEmpty()) {
            queryString.append(" AND UPPER(c.commentaire) = :commentaire");
            parameters.add("commentaire");
        }

        TypedQuery<Client> query = entityManager.createQuery(queryString.toString(), Client.class);

        for (String parameter : parameters) {
            switch (parameter) {
                case "nomPrenom":
                    query.setParameter("nomPrenom", nomPrenom.toUpperCase());
                    break;
                case "email1":
                    query.setParameter("email1", email1.toUpperCase());
                    break;
                case "email2":
                    query.setParameter("email2", email2.toUpperCase());
                    break;
                case "telephone":
                    query.setParameter("telephone", telephone);
                    break;
                case "dateDeNaissance":
                    query.setParameter("dateDeNaissance", dateDeNaissance);
                    break;
                case "civilite":
                    query.setParameter("civilite", civilite.toUpperCase());
                    break;
                case "poste":
                    query.setParameter("poste", poste.toUpperCase());
                    break;
                case "faxe":
                    query.setParameter("faxe", faxe.toUpperCase());
                    break;
                case "cellulaire":
                    query.setParameter("cellulaire", cellulaire.toUpperCase());
                    break;
                case "adresse1":
                    query.setParameter("adresse1", adresse1.toUpperCase());
                    break;
                case "adresse2":
                    query.setParameter("adresse2", adresse2.toUpperCase());
                    break;
                case "pays":
                    query.setParameter("pays", pays.toUpperCase());
                    break;
                case "province":
                    query.setParameter("province", province.toUpperCase());
                    break;
                case "commentaire":
                    query.setParameter("commentaire", commentaire.toUpperCase());
                    break;
            }
        }

        return query.getResultList();
    }
}
