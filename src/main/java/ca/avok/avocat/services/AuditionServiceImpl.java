package ca.avok.avocat.services;

import ca.avok.avocat.InterfaceService.IAuditionService;
import ca.avok.avocat.entities.Audition;
import ca.avok.avocat.entities.Dossier;
import ca.avok.avocat.enumeration.StatusTache;
import ca.avok.avocat.repositories.AuditionRepos;
import ca.avok.avocat.repositories.DossierRepos;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AuditionServiceImpl implements IAuditionService {

    AuditionRepos auditionRepos;
    DossierRepos dossierRepos;

    //-----------------------------------CRUD begins-----------------------------------//
    @Override
    public Audition createAudition(Audition audition, String numDossier) {
        Dossier dossier = dossierRepos.findById(numDossier).get();
        audition.setDossier(dossier);
        auditionRepos.save(audition);
        return audition;
    }

    @Override
    public List<Audition> readAllAudition() {
        return auditionRepos.findAll();
    }

    @Override
    public Audition readAudition(int idAudition) {
        Audition audition = auditionRepos.findById(idAudition).get();
        return audition;
    }

    @Override
    public Audition updateAudition(Audition audition) {
        return auditionRepos.save(audition);
    }

    @Override
    public void deleteAudition(int idAudition) {
        auditionRepos.deleteById(idAudition);
    }
    //-----------------------------------CRUD ends-----------------------------------//

    // Terminer une audition par son ID
    @Override
    public void terminerAudition(int idAudition) {
        Audition audition = auditionRepos.findById(idAudition).get();
        audition.setStatus(StatusTache.TERMINE);
        audition.setDateFin(LocalDateTime.now());
        audition.setDuree(Duration.between(audition.getDateDebut(), audition.getDateFin()));
        auditionRepos.save(audition);
    }

    // Activer une audition par son ID
    @Override
    public void activerAudition(int idAudition) {
        Audition audition = auditionRepos.findById(idAudition).get();
        audition.setStatus(StatusTache.EN_COURS);
        auditionRepos.save(audition);
    }

}
