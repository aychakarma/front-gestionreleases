package ca.avok.avocat.services;

import ca.avok.avocat.InterfaceService.IDepenseService;
import ca.avok.avocat.entities.Depense;
import ca.avok.avocat.enumeration.StatusPay;
import ca.avok.avocat.repositories.DepenseRepos;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DepenseServiceImpl implements IDepenseService {
    DepenseRepos depenseRepos;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Depense> afficherAllDepenses() {
        return depenseRepos.findAll();
    }

    @Override
    public Depense afficherDepense(Integer idDepense) {
        Optional<Depense> depenseOptional = depenseRepos.findById(idDepense);
        return depenseOptional.orElse(null);
    }

    @Override
    public Depense ajouterDepense(Depense d) {
        d.setDateDepense(LocalDateTime.now());
        d.setStatus(StatusPay.NON_PAYE);
        return depenseRepos.save(d);
    }

    @Override
    public Depense modifierDepense(Depense d) {
        Optional<Depense> depenseOptional = depenseRepos.findById(d.getIdDepense());
        if (depenseOptional.isPresent()) {
            Depense depenseExistante = depenseOptional.get();
            depenseExistante.setObjet(d.getObjet());
            depenseExistante.setDateDepense(d.getDateDepense());
            depenseExistante.setFrais(d.getFrais());
            depenseExistante.setStatus(d.getStatus());
            depenseExistante.setTps(d.getTps());
            depenseExistante.setTpq(d.getTpq());
            depenseExistante.setNumFacture(d.getNumFacture());
            depenseExistante.setMontant(d.getMontant());
            depenseExistante.setCommentaire(d.getCommentaire());
            return depenseRepos.save(depenseExistante);
        } else {
            return null;
        }
    }

    @Override
    public void supprimerDepense(Integer idDepense) {
        depenseRepos.deleteById(idDepense);

    }

    @Override
    public void depensePaye(Depense d) {
        d.setStatus(StatusPay.PAYE);
    }

    @Override
    public void depenseNonPaye(Depense d) {
        d.setStatus(StatusPay.NON_PAYE);

    }

    @Override
    public List<Depense> searchsearchDepensesByAttributes(String objet, LocalDateTime dateDepense, String numFacture,
                                                          Float montant, Float tps, Float tpq, Float frais, String commentaire, StatusPay status) {

        StringBuilder queryString = new StringBuilder("SELECT d FROM Depense d WHERE 1=1");
        if (objet != null && !objet.isEmpty()) {
            queryString.append(" AND UPPER (d.objet) = :objet");
        }

        if (dateDepense != null) {
            queryString.append(" AND d.dateDepense = :dateDepense");
        }

        if (numFacture != null && !numFacture.isEmpty()) {
            queryString.append(" AND d.numFacture = :numFacture");
        }

        if (montant != null) {
            queryString.append(" AND d.montant = :montant");
        }

        if (tps != null) {
            queryString.append(" AND d.tps = :tps");
        }

        if (tpq != null) {
            queryString.append(" AND d.tpq = :tpq");
        }

        if (frais != null) {
            queryString.append(" AND d.frais = :frais");
        }

        if (commentaire != null && !commentaire.isEmpty()) {
            queryString.append(" AND UPPER (d.commentaire) = :commentaire");
        }

        if (status != null) {
            queryString.append(" AND UPPER (d.status) = :status");
        }

        TypedQuery<Depense> query = entityManager.createQuery(queryString.toString(), Depense.class);

        if (objet != null && !objet.isEmpty()) {
            query.setParameter("objet", objet.toUpperCase(Locale.ROOT));
        }

        if (dateDepense != null) {
            query.setParameter("dateDepense", dateDepense);
        }

        if (numFacture != null && !numFacture.isEmpty()) {
            query.setParameter("numFacture", numFacture);
        }

        if (montant != null) {
            query.setParameter("montant", montant);
        }

        if (tps != null) {
            query.setParameter("tps", tps);
        }

        if (tpq != null) {
            query.setParameter("tpq", tpq);
        }

        if (frais != null) {
            query.setParameter("frais", frais);
        }

        if (commentaire != null && !commentaire.isEmpty()) {
            query.setParameter("commentaire", commentaire.toUpperCase());
        }

        if (status != null) {
            query.setParameter("status", status);
        }

        return query.getResultList();
    }
}
