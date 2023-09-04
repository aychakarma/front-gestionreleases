package ca.avok.avocat.services;

import ca.avok.avocat.InterfaceService.IDossierService;
import ca.avok.avocat.InterfaceService.IIntervenantService;
import ca.avok.avocat.entities.Audition;
import ca.avok.avocat.entities.Dossier;
import ca.avok.avocat.entities.Intervenant;
import ca.avok.avocat.enumeration.Status;
import ca.avok.avocat.repositories.DossierRepos;
import ca.avok.avocat.repositories.IntervenantRepos;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class DossierServiceImpl implements IDossierService {
    private final IntervenantRepos intervenantRepos;

    DossierRepos dossierRepos;
    IntervenantServiceImpl intervenantService;

    //-----------------------------------CRUD begins-----------------------------------//
    //Creation
    @Override
    public Dossier createDossier(Dossier dossier) {
        return dossierRepos.save(dossier);
    }

    //read / readAll
    @Override
    public Dossier readDossier(String numDossier) {
        Dossier dossier = dossierRepos.findByNumDossier(numDossier).get();
        dossier.setDerniereConsultation(LocalDateTime.now());
        dossierRepos.save(dossier);
        return dossier;
    }

    @Override
    public List<Dossier> readAllDossier() {
        return dossierRepos.findAll();
    }

    //update
    @Override
    public Dossier updateDossier(Dossier dossier) {
        return dossierRepos.save(dossier);
    }

    //delete
    @Override
    @Transactional
    public void deleteDossier(String numDossier) {
        dossierRepos.deleteByNumDossier(numDossier);
    }

    //-----------------------------------CRUD ends-----------------------------------//

    // Méthode pour afficher les 5 derniers dossiers ouverts
    @Override
    public List<Dossier> getLatest5OpenedDossiers() {
        List<Dossier> allDossiers = readAllDossier();

        // Trier les dossiers par date d'ouverture
        Collections.sort(allDossiers, Comparator.comparing(Dossier::getDerniereConsultation).reversed());

        // Sélectionner les 5 derniers dossiers ouverts
        int numberOfDossiersToShow = Math.min(5, allDossiers.size());
        List<Dossier> latest5Dossiers = new ArrayList<>();

        for (int i = 0; i < numberOfDossiersToShow; i++) {
            latest5Dossiers.add(allDossiers.get(i));
        }
        return latest5Dossiers;
    }

    // Méthode pour fermer un dossier(changer la dateFermeture à la date de l'appel de la méthode)
    @Override
    public void fermerDossier(String numDossier) {
        Dossier dossier = dossierRepos.findByNumDossier(numDossier).get();
        dossier.setDateFermeture(LocalDateTime.now());
        dossier.setStatus(Status.FERMEE);
        dossierRepos.save(dossier);
    }

    // Méthode utilitaire pour vérifier si un dossier correspond aux critères de recherche
    public boolean isMatchingDossier(Dossier dossier, String searchCriteria) {
        // Formatter pour spécifier le format de la chaîne de sortie (par exemple, "yyyy-MM-dd HH:mm:ss")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Vérifier si la date d'ouverture correspond aux critères de recherche
        if (dossier.getDateOuverture() != null) {
            String dateOuvertureString = dossier.getDateOuverture().format(formatter);
            if (dateOuvertureString.toUpperCase().contains(searchCriteria.toUpperCase())) {
                return true;
            }
        }

        // Vérifier si la date de fermeture correspond aux critères de recherche
        if (dossier.getDateFermeture() != null) {
            String dateFermetureString = dossier.getDateFermeture().format(formatter);
            if (dateFermetureString.toUpperCase().contains(searchCriteria.toUpperCase())) {
                return true;
            }
        }

        // Vérifier si la date d'inscription correspond aux critères de recherche
        if (dossier.getDateInscription() != null) {
            String dateInscriptionString = dossier.getDateInscription().format(formatter);
            if (dateInscriptionString.toUpperCase().contains(searchCriteria.toUpperCase())) {
                return true;
            }
        }

        // Vérifier si le numéro de dossier correspond aux critères de recherche
        if (dossier.getNumDossier().toUpperCase().contains(searchCriteria.toUpperCase())) {
            return true;
        }

        // Si aucune correspondance n'est trouvée, retourner false
        return false;
    }




    // Méthode de recherche à partir de n'importe quel attribut du dossier
    public List<Dossier> searchDossier(String searchCriteria) {
        List<Dossier> allDossiers = readAllDossier();
        List<Dossier> matchingDossiers = new ArrayList<>();

        for (Dossier dossier : allDossiers) {
            // Vérifier si le dossier correspond aux critères de recherche
            if (isMatchingDossier(dossier, searchCriteria)) {
                matchingDossiers.add(dossier);
            }
        }

        return matchingDossiers;
    }

    //Afficher seulement les dossiers ouvertes
    @Override
    public List<Dossier> afficherDossiersOuvertes() {
        List<Dossier> allDossiers = readAllDossier();
        List<Dossier> dossiersOuvertes = new ArrayList<>();

        for(Dossier dossier: allDossiers){
            // Vérifier si le dossier est ouvert
            if (dossier.getStatus()==Status.OUVERT){
                    dossiersOuvertes.add(dossier);
            }
        }
        return dossiersOuvertes;
    }

    //Afficher seulement les dossiers fermees
    @Override
    public List<Dossier> afficherDossiersfermees() {
        List<Dossier> allDossiers = readAllDossier();
        List<Dossier> dossiersFermees = new ArrayList<>();

        for(Dossier dossier: allDossiers){
            // Vérifier si le dossier est fermees
            if (dossier.getStatus()==Status.FERMEE){
                dossiersFermees.add(dossier);
            }
        }
        return dossiersFermees;
    }

    // Afficher les intervenants associés à un dossier spécifique
    @Override
    public List<Intervenant> afficherIntervenants(String numDossier) {
        Dossier dossier = dossierRepos.findById(numDossier).get();
        return dossier.getIntervenants();
    }

    // Afficher les auditions associés à un dossier spécifique
    @Override
    public List<Audition> afficherAuditions(String numDossier) {
        Dossier dossier = dossierRepos.findById(numDossier).get();
        return dossier.getAuditions();
    }

    // Méthode pour affecter un intervenant à un dossier en utilisant les numéros de dossier et d'intervenant
    @Override
    public void affecterIntervenant(String numString, String idIntervenant) {
        Dossier dossier = dossierRepos.findById(numString).get();
        List<Intervenant> intervenants = dossier.getIntervenants();
        intervenants.add(intervenantService.readIntervenant(idIntervenant));
        dossier.setIntervenants(intervenants);
        dossierRepos.save(dossier);
    }

}
