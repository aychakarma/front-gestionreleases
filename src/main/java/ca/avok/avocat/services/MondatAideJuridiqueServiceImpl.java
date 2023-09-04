package ca.avok.avocat.services;

import ca.avok.avocat.InterfaceService.IMondatAideJuridiqueService;
import ca.avok.avocat.entities.Dossier;
import ca.avok.avocat.entities.Mondat;
import ca.avok.avocat.entities.MondatAideJuridique;
import ca.avok.avocat.repositories.DossierRepos;
import ca.avok.avocat.repositories.MondatAideJuridiqueRepos;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MondatAideJuridiqueServiceImpl implements IMondatAideJuridiqueService {

    MondatAideJuridiqueRepos mondatAideJuridiqueRepos;
    DossierRepos dossierRepos;

    //-----------------------------------CRUD begins-----------------------------------//

    //Création avec l'affectation au dossier concerné
    @Override
    public MondatAideJuridique createMondatAideJuridique(MondatAideJuridique mondatAideJuridique,String numDossier) {
        mondatAideJuridiqueRepos.save(mondatAideJuridique);
        Dossier dossier = dossierRepos.findById(numDossier).get();
        dossier.setMondat(mondatAideJuridique);
        dossierRepos.save(dossier);
        return mondatAideJuridique;
    }

    @Override
    public List<MondatAideJuridique> readAllMondatAideJuridique() {
        return mondatAideJuridiqueRepos.findAll();
    }

    @Override
    public MondatAideJuridique readMondatAideJuridique(int idMondat) {
        return mondatAideJuridiqueRepos.findById(idMondat).get();
    }

    @Override
    public MondatAideJuridique updateMondatAideJuridique(MondatAideJuridique mondatAideJuridique) {
        return mondatAideJuridiqueRepos.save(mondatAideJuridique);
    }

    @Override
    public void deleteMondatAideJuridique(int idMondat) {
        mondatAideJuridiqueRepos.deleteById(idMondat);
    }
    //-----------------------------------CRUD ends-----------------------------------//
}
