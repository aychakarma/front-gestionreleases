package ca.avok.avocat.InterfaceService;

import ca.avok.avocat.entities.MondatAideJuridique;
import ca.avok.avocat.entities.MondatPrive;

import java.util.List;

public interface IMondatPriveService {

    //-----------------------------------CRUD begins-----------------------------------//
    //Création avec l'affectation au dossier concerné
    public MondatPrive createMondatPrive(MondatPrive mondatPrive, String numDossier);

    public List<MondatPrive> readAllMondatPrive();

    public MondatPrive readMondatPrive(int idMondat);

    public MondatPrive updateMondatPrive(MondatPrive mondatPrive);

    public void  deleteMondatPrive(int idMondat);

    //-----------------------------------CRUD ends-----------------------------------//
}
