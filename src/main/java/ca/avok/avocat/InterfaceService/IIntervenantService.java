package ca.avok.avocat.InterfaceService;

import ca.avok.avocat.entities.Intervenant;

import java.util.List;

public interface IIntervenantService {

    //-----------------------------------CRUD begins-----------------------------------//
    public Intervenant createIntervenant(Intervenant intervenant);

    public List<Intervenant> readAllIntervenant();

    public Intervenant readIntervenant(String idIntervenant);

    public Intervenant updateIntervenant(Intervenant intervenant);

    public void  deleteIntervenant(String idIntervenant);
    //-----------------------------------CRUD ends-----------------------------------//

    // Méthode utilitaire pour vérifier si un intervenant correspond aux critères de recherche
    public boolean isMatchingIntervenant(Intervenant intervenant , String searchCriteria);

    // Méthode de recherche à partir de n'importe quel attribut de l'intervenant
    public List<Intervenant> searchIntervenant(String searchCriteria);

}
