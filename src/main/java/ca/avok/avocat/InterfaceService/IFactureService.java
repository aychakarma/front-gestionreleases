package ca.avok.avocat.InterfaceService;

import ca.avok.avocat.entities.Facture;

import java.time.LocalDateTime;
import java.util.List;

public interface IFactureService {
    List<Facture> afficherAllFacture();
    Facture afficherFacture(Integer idFacture);
    Facture ajouterFacture(Facture f);
    Facture modifierFacture(Facture f);
    void supprimerFacture(Integer idFacture);
    List<Facture> searchFacturesByAttributes(LocalDateTime dateFacture, String commentaire);

}
