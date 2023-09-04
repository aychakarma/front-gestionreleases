package ca.avok.avocat.services;

import ca.avok.avocat.InterfaceService.IFideicommisService;
import ca.avok.avocat.entities.Fideicommis;
import ca.avok.avocat.repositories.FideicommisRepos;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FideicommisServiceImpl implements IFideicommisService {

    FideicommisRepos fideicommisRepos;

    //-----------------------------------CRUD begins-----------------------------------//
    @Override
    public Fideicommis createFideicommis(Fideicommis fideicommis) {
        return fideicommisRepos.save(fideicommis);
    }

    @Override
    public List<Fideicommis> readAllFideicommis() {
        return fideicommisRepos.findAll();
    }

    @Override
    public Fideicommis readFideicommis(int idFideicommis) {
        return fideicommisRepos.findById(idFideicommis).get();
    }

    @Override
    public Fideicommis updateFideicommis(Fideicommis fideicommis) {
        return fideicommisRepos.save(fideicommis);
    }

    @Override
    public void deleteFideicommis(int idFideicommis) {
        fideicommisRepos.deleteById(idFideicommis);
    }

    // Méthode utilitaire pour vérifier si un fideicommis correspond aux critères de recherche
    @Override
    public boolean isMatchingFideicommis(Fideicommis fideicommis, String searchCriteria) {
        // Formatter pour spécifier le format de la chaîne de sortie (par exemple, "yyyy-MM-dd HH:mm:ss")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Vérifier si la date correspond aux critères de recherche
        if (fideicommis.getDate() != null) {
            String dateString = fideicommis.getDate().format(formatter);
            if (dateString.contains(searchCriteria)) {
                return true;
            }
        }

        String NaturePay = String.valueOf(fideicommis.getNature_payment());
        // Vérifier si la nature de paiement correspond aux critères de recherche
        if(NaturePay.toUpperCase().contains(searchCriteria.toUpperCase())){
            return true;
        }

        // Convertir le montant en chaîne de caractères et vérifier si elle correspond aux critères de recherche
        String stringValue = Float.toString(fideicommis.getMontant());
        if(stringValue.contains(searchCriteria)){
            return true;
        }

        String modePay = String.valueOf(fideicommis.getMode_payment());
        // Vérifier si le mode de paiement correspond aux critères de recherche
        if(modePay.toUpperCase().contains(searchCriteria.toUpperCase())){
            return true;
        }

        if(fideicommis.getCommentaire().toUpperCase().contains(searchCriteria.toUpperCase())){
            return true;
        }

        // Aucun des critères de recherche n'a été satisfait, retourner false
        return false;
    }

    // Méthode de recherche à partir de n'importe quel attribut du fideicommis
    @Override
    public List<Fideicommis> searchFideicommis(String searchCriteria) {
        List<Fideicommis> allFideicommis = readAllFideicommis();
        List<Fideicommis> matchingFideicommis = new ArrayList<>();

        for (Fideicommis fideicommis : allFideicommis) {
            // Vérifier si le Fideicommis correspond aux critères de recherche
            if (isMatchingFideicommis(fideicommis, searchCriteria)) {
                matchingFideicommis.add(fideicommis);
            }
        }

        return matchingFideicommis;
    }


}
