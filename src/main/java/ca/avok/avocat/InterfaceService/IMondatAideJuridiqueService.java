package ca.avok.avocat.InterfaceService;

import ca.avok.avocat.entities.MondatAideJuridique;

import java.util.List;

public interface IMondatAideJuridiqueService {

    //-----------------------------------CRUD begins-----------------------------------//
    //Création avec l'affectation au dossier concerné
    public MondatAideJuridique createMondatAideJuridique(MondatAideJuridique mondatAideJuridique,String numDossier);

    public List<MondatAideJuridique> readAllMondatAideJuridique();

    public MondatAideJuridique readMondatAideJuridique(int idMondat);

    public MondatAideJuridique updateMondatAideJuridique(MondatAideJuridique mondatAideJuridique);

    public void  deleteMondatAideJuridique(int idMondat);
    //-----------------------------------CRUD ends-----------------------------------//
}
