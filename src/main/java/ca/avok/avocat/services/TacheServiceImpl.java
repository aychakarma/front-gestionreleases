package ca.avok.avocat.services;

import ca.avok.avocat.InterfaceService.ITacheService;
import ca.avok.avocat.entities.Tache;
import ca.avok.avocat.enumeration.StatusTache;
import ca.avok.avocat.repositories.TacheReposiory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TacheServiceImpl implements ITacheService {
    TacheReposiory tacheReposiory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tache> afficherAllTaches() {
        return tacheReposiory.findAll();
    }

    @Override
    public Tache afiicherTache(Integer idTache) {
        Optional<Tache> tacheOptional = tacheReposiory.findById(idTache);
        return tacheOptional.orElse(null);
    }

    @Override
    public Tache ajouterTache(Tache t) {
        t.setStatus(StatusTache.EN_COURS);
//        Duration duree = Duration.between(t.getDateDebut(), t.getDateFin());
//        long heures = duree.toHours();
//        long minutesTotal = duree.toMinutes();
//        long minutes = minutesTotal % 60;
//        long secondes = duree.getSeconds() % 60;
//
//        String dureeFormattee = String.format("%02dh:%02dmin:%02ds", heures, minutes, secondes);
//        t.setDuree(dureeFormattee); // Enregistrez la durée formatée dans la base de données
        return tacheReposiory.save(t);
    }

    @Override
    public Tache modifierTache(Tache t) {
        Optional<Tache> tacheOptional = tacheReposiory.findById(t.getIdTache());
        if (tacheOptional.isPresent()) {
            Tache tacheExistante = tacheOptional.get();
            tacheExistante.setTitle(t.getTitle());
            tacheExistante.setDateDebut(t.getDateDebut());
            tacheExistante.setDateFin(t.getDateFin());
            tacheExistante.setDuree(t.getDuree());
            tacheExistante.setDescription(t.getDescription());
            tacheExistante.setStatus(t.getStatus());

            return tacheReposiory.save(tacheExistante);
        } else {
            return null;
        }
    }

    @Override
    public void supprimerTache(Integer idTache) {
        tacheReposiory.deleteById(idTache);

    }

    @Override
    public void terminerTache(int idTache) {
        Tache t = tacheReposiory.findById(idTache).get();
        t.setStatus(StatusTache.TERMINE);
        t.setDateFin(LocalDateTime.now());

        Duration duree = Duration.between(t.getDateDebut(), t.getDateFin());
        long heures = duree.toHours();
        long minutesTotal = duree.toMinutes();
        long minutes = minutesTotal % 60;
        long secondes = duree.getSeconds() % 60;

        String dureeFormattee = String.format("%02dh:%02dmin:%02ds", heures, minutes, secondes);
        t.setDuree(dureeFormattee); // Enregistrez la durée formatée dans la base de données


        tacheReposiory.save(t);
    }

    @Override
    public void activerTache(int idTache) {
        Tache t = tacheReposiory.findById(idTache).get();
        t.setStatus(StatusTache.EN_COURS);
        tacheReposiory.save(t);
    }

    @Override
    public List<Tache> getTachesDuJour() {
        LocalDateTime dateDuJour = LocalDateTime.now();
        return tacheReposiory.findTachesDuJour(dateDuJour);


    }

    @Override
    public List<Tache> searchTachesByAttributes(String title, LocalDateTime dateDebut, LocalDateTime dateFin,
                                                String statut, String description, String duree) {
        StringBuilder queryString = new StringBuilder("SELECT t FROM Tache t WHERE 1=1");
        List<String> parameters = new ArrayList<>();

        if (title != null && !title.isEmpty()) {
            queryString.append(" AND UPPER(t.title) = :title");
            parameters.add("title");
        }

        if (dateDebut != null) {
            queryString.append(" AND t.dateDebut = :dateDebut");
            parameters.add("dateDebut");
        }

        if (dateFin != null) {
            queryString.append(" AND t.dateFin = :dateFin");
            parameters.add("dateFin");
        }

        if (statut != null && !statut.isEmpty()) {
            queryString.append(" AND UPPER(t.statut) = :statut");
            parameters.add("statut");
        }

        if (description != null && !description.isEmpty()) {
            queryString.append(" AND UPPER(t.description) = :description");
            parameters.add("description");
        }

        if (duree != null) {
            queryString.append(" AND t.duree = :duree");
            parameters.add("duree");
        }

        TypedQuery<Tache> query = entityManager.createQuery(queryString.toString(), Tache.class);

        for (String parameter : parameters) {
            switch (parameter) {
                case "title":
                    query.setParameter("title", title.toUpperCase());
                    break;
                case "dateDebut":
                    query.setParameter("dateDebut", dateDebut);
                    break;
                case "dateFin":
                    query.setParameter("dateFin", dateFin);
                    break;
                case "statut":
                    query.setParameter("statut", statut.toUpperCase());
                    break;
                case "description":
                    query.setParameter("description", description.toUpperCase());
                    break;
                case "duree":
                    query.setParameter("duree", duree);
                    break;
            }
        }

        return query.getResultList();
    }



}
