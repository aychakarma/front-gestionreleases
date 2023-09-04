package ca.avok.avocat.InterfaceService;

import ca.avok.avocat.entities.Audition;

import java.util.List;

public interface IAuditionService {

    //-----------------------------------CRUD begins-----------------------------------//
    public Audition createAudition(Audition audition,String numDossier);

    public List<Audition> readAllAudition();

    public Audition readAudition(int idAudition);

    public Audition updateAudition(Audition audition);

    public void  deleteAudition(int idAudition);
    //-----------------------------------CRUD ends-----------------------------------//

    // Terminer une audition par son ID
    public void terminerAudition(int idAudition);

    // Activer une audition par son ID
    public void activerAudition(int idAudition);
}
