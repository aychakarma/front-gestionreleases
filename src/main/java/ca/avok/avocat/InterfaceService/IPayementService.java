package ca.avok.avocat.InterfaceService;

import ca.avok.avocat.entities.Payement;
import ca.avok.avocat.enumeration.ModePay;

import java.time.LocalDateTime;
import java.util.List;

public interface IPayementService {
    List<Payement> afficherAllPayements();
    Payement afficherPayement(Integer idPayement);
    Payement ajouterPayement(Payement p);
    Payement modifierPayement(Payement p);
    void supprimerPayement(Integer idPayement);
    List<Payement> searchPayementByAttributes(Integer idPayement, LocalDateTime datePayement, String montant,
                                              ModePay modePayement);
}
