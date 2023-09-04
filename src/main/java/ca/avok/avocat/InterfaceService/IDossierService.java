package ca.avok.avocat.InterfaceService;

import ca.avok.avocat.entities.Audition;
import ca.avok.avocat.entities.Dossier;
import ca.avok.avocat.entities.Intervenant;
import ca.avok.avocat.entities.Mondat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface IDossierService {

    //-----------------------------------CRUD begins-----------------------------------//
    public Dossier createDossier(Dossier dossier);

    public List<Dossier> readAllDossier();

    public Dossier readDossier(String numDossier);

    public Dossier updateDossier(Dossier dossier);

    public void  deleteDossier(String numDossier);
    //-----------------------------------CRUD ends-----------------------------------//

    // Méthode pour afficher les 5 derniers dossiers ouverts
    public List<Dossier> getLatest5OpenedDossiers();

    // Méthode pour fermer un dossier(changer la dateFermeture à la date de l'appel de la méthode)
    public void fermerDossier(String numDossier);

    // Méthode utilitaire pour vérifier si un dossier correspond aux critères de recherche
    public boolean isMatchingDossier(Dossier dossier, String searchCriteria);

    // Méthode de recherche à partir de n'importe quel attribut du dossier
    public List<Dossier> searchDossier(String searchCriteria);

    //Afficher seulement les dossiers ouvertes
    public List<Dossier> afficherDossiersOuvertes();

    //Afficher seulement les dossiers fermees
    public List<Dossier> afficherDossiersfermees();

    // Afficher les intervenants associés à un dossier spécifique
    public List<Intervenant> afficherIntervenants(String numDossier);

    // Afficher les auditions associés à un dossier spécifique
    public List<Audition> afficherAuditions(String numDossier);

    //Affecter un intervenant à un dossier
    public void affecterIntervenant(String numString,String idIntervenant);

}
