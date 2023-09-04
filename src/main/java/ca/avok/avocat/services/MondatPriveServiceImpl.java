package ca.avok.avocat.services;

import ca.avok.avocat.InterfaceService.IMondatPriveService;
import ca.avok.avocat.entities.Dossier;
import ca.avok.avocat.entities.MondatPrive;
import ca.avok.avocat.repositories.DossierRepos;
import ca.avok.avocat.repositories.MondatPriveRepos;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MondatPriveServiceImpl implements IMondatPriveService {

    MondatPriveRepos mondatPriveRepos;
    DossierRepos dossierRepos;

    //-----------------------------------CRUD begins-----------------------------------//
    @Override
    public MondatPrive createMondatPrive(MondatPrive mondatPrive,String numDossier) {
        mondatPriveRepos.save(mondatPrive);
        Dossier dossier = dossierRepos.findById(numDossier).get();
        dossier.setMondat(mondatPrive);
        dossierRepos.save(dossier);
        return mondatPrive;
    }

    @Override
    public List<MondatPrive> readAllMondatPrive() {
        return mondatPriveRepos.findAll();
    }

    @Override
    public MondatPrive readMondatPrive(int idMondat) {
        return mondatPriveRepos.findById(idMondat).get();
    }

    @Override
    public MondatPrive updateMondatPrive(MondatPrive mondatPrive) {
        return mondatPriveRepos.save(mondatPrive);
    }

    @Override
    public void deleteMondatPrive(int idMondat) {
        mondatPriveRepos.deleteById(idMondat);
    }
    //-----------------------------------CRUD ends-----------------------------------//
}
