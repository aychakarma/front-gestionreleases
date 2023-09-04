package ca.avok.avocat.InterfaceService;

import ca.avok.avocat.entities.Depense;
import ca.avok.avocat.enumeration.StatusPay;

import java.time.LocalDateTime;
import java.util.List;

public interface IDepenseService {
    List<Depense> afficherAllDepenses ();
    Depense afficherDepense(Integer idDepense);
    Depense ajouterDepense (Depense d);
    Depense modifierDepense (Depense d);
    void  supprimerDepense (Integer idDepense);
    void depensePaye(Depense d);
    void depenseNonPaye(Depense d);
    List<Depense> searchsearchDepensesByAttributes(String objet, LocalDateTime dateDepense,
                                                   String numFacture, Float montant,
                                                   Float tps, Float tpq, Float frais,
                                                   String commentaire, StatusPay status);

}
