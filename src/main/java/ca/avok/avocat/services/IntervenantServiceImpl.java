package ca.avok.avocat.services;

import ca.avok.avocat.InterfaceService.IIntervenantService;
import ca.avok.avocat.entities.Intervenant;
import ca.avok.avocat.repositories.IntervenantRepos;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class IntervenantServiceImpl implements IIntervenantService {

    IntervenantRepos intervenantRepos;

    //-----------------------------------CRUD begins-----------------------------------//
    @Override
    public Intervenant createIntervenant(Intervenant intervenant) {
        return intervenantRepos.save(intervenant);
    }

    @Override
    public List<Intervenant> readAllIntervenant() {
        return intervenantRepos.findAll();
    }

    @Override
    public Intervenant readIntervenant(String idIntervenant) {
        return intervenantRepos.findById(idIntervenant).get();
    }

    @Override
    public Intervenant updateIntervenant(Intervenant intervenant) {
        return intervenantRepos.save(intervenant);
    }

    @Override
    public void deleteIntervenant(String idIntervenant) {
        intervenantRepos.deleteById(idIntervenant);
    }
    //-----------------------------------CRUD ends-----------------------------------//

    // Méthode utilitaire pour vérifier si un intervenant correspond aux critères de recherche
    @Override
    public boolean isMatchingIntervenant(Intervenant intervenant, String searchCriteria) {
        // Formatter pour spécifier le format de la chaîne de sortie (par exemple, "yyyy-MM-dd HH:mm:ss")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if(intervenant.getDateDeNaissance() != null){
            String dateDeNaissanceString =intervenant.getDateDeNaissance().format(formatter);
            if(dateDeNaissanceString.contains(searchCriteria)){
                return true;
            }
        }

        if (intervenant.getCivilite().toUpperCase().contains(searchCriteria.toUpperCase())){
            return true;
        }

        if (intervenant.getEmail().toUpperCase().contains(searchCriteria.toUpperCase())){
            return true;
        }
        if (intervenant.getCellulaire().toUpperCase().contains(searchCriteria.toUpperCase())){
            return true;
        }
        if (intervenant.getCommentaire().toUpperCase().contains(searchCriteria.toUpperCase())){
            return true;
        }
        if (intervenant.getFax().toUpperCase().contains(searchCriteria.toUpperCase())){
            return true;
        }
        if (intervenant.getAdresse1().toUpperCase().contains(searchCriteria.toUpperCase())){
            return true;
        }
        if (intervenant.getPays().toUpperCase().contains(searchCriteria.toUpperCase())){
            return true;
        }
        if (intervenant.getPoste().toUpperCase().contains(searchCriteria.toUpperCase())){
            return true;
        }
        if (intervenant.getAdresse2().toUpperCase().contains(searchCriteria.toUpperCase())){
            return true;
        }
        if (intervenant.getProvince().toUpperCase().contains(searchCriteria.toUpperCase())){
            return true;
        }
        if (intervenant.getEmail2().toUpperCase().contains(searchCriteria.toUpperCase())){
            return true;
        }
        if (intervenant.getNomEtPrenom().toUpperCase().contains(searchCriteria.toUpperCase())){
            return true;
        }
        if (intervenant.getTelephone().contains(searchCriteria)){
            return true;
        }
        return false;
    }

    // Méthode de recherche à partir de n'importe quel attribut de l'intervenant
    @Override
    public List<Intervenant> searchIntervenant(String searchCriteria) {
        List<Intervenant> allIntervenant = readAllIntervenant();
        List<Intervenant> matchingIntervenants = new ArrayList<>();

        for (Intervenant intervenant : allIntervenant) {
            // Vérifier si le Intervenant correspond aux critères de recherche
            if (isMatchingIntervenant(intervenant, searchCriteria)) {
                matchingIntervenants.add(intervenant);
            }
        }
        return matchingIntervenants;
    }

}
