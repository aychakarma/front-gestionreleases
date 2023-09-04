package ca.avok.avocat.InterfaceService;

import ca.avok.avocat.entities.Tache;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

public interface ITacheService {
    List<Tache> afficherAllTaches();
    Tache afiicherTache(Integer idTache);
    Tache ajouterTache(Tache t);
    Tache modifierTache(Tache t);
    void supprimerTache(Integer idTache);
    void terminerTache(int idTache);
    void activerTache(int idTache);
    List<Tache> getTachesDuJour();
    List<Tache> searchTachesByAttributes(String title, LocalDateTime dateDebut, LocalDateTime dateFin, String statut,
                                         String description, String duree);



}
