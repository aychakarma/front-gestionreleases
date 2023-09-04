package ca.avok.avocat.services;

import ca.avok.avocat.InterfaceService.IFactureService;
import ca.avok.avocat.entities.Facture;
import ca.avok.avocat.repositories.FactureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FactureServiceImpl implements IFactureService {

    FactureRepository factureRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Facture> afficherAllFacture()
    {
        return  factureRepository.findAll();
    }

    @Override
    public Facture afficherFacture(Integer idFacture) {
        Optional<Facture> factureOptional = factureRepository.findById(idFacture);
        return factureOptional.orElse(null);
    }

    @Override
    public Facture ajouterFacture(Facture f) {
        f.setDateFacture(LocalDateTime.now());
        return factureRepository.save(f);
    }

    @Override
    public Facture modifierFacture(Facture f) {

        Optional<Facture> factureOptional = factureRepository.findById(f.getIdFacture());
        if (factureOptional.isPresent()) {
            Facture factureExistante = factureOptional.get();
            factureExistante.setDateFacture(f.getDateFacture());
            factureExistante.setCommentaire(f.getCommentaire());

            return factureRepository.save(factureExistante);
        } else {
            return null;
        }
    }

    @Override
    public void supprimerFacture(Integer idFacture) {
        factureRepository.deleteById(idFacture);

    }

    @Override
    public List<Facture> searchFacturesByAttributes(LocalDateTime dateFacture, String commentaire) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Facture> query = builder.createQuery(Facture.class);
        Root<Facture> root = query.from(Facture.class);
        List<Predicate> predicates = new ArrayList<>();

        if (dateFacture != null) {
            predicates.add(builder.equal(root.get("dateFacture"), dateFacture));
        }

        if (commentaire != null && !commentaire.isEmpty()) {
            predicates.add(builder.equal(builder.upper(root.get("commentaire")), commentaire.toUpperCase()));
        }

        query.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Facture> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

}
