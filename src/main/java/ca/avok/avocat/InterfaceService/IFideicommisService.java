package ca.avok.avocat.InterfaceService;

import ca.avok.avocat.entities.Fideicommis;

import java.util.List;

public interface IFideicommisService {


    //-----------------------------------CRUD begins-----------------------------------//
    public Fideicommis createFideicommis(Fideicommis fideicommis);

    public List<Fideicommis> readAllFideicommis();

    public Fideicommis readFideicommis(int idFideicommis);

    public Fideicommis updateFideicommis(Fideicommis fideicommis);

    public void  deleteFideicommis(int idFideicommis);
    //-----------------------------------CRUD ends-----------------------------------//

    // Méthode utilitaire pour vérifier si un fideicommis correspond aux critères de recherche
    public boolean isMatchingFideicommis(Fideicommis fideicommis , String searchCriteria);

    // Méthode de recherche à partir de n'importe quel attribut du fideicommis
    public List<Fideicommis> searchFideicommis(String searchCriteria);

}
