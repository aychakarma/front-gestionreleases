package ca.avok.avocat.services;

import ca.avok.avocat.InterfaceService.IPayementService;
import ca.avok.avocat.entities.Payement;
import ca.avok.avocat.enumeration.ModePay;
import ca.avok.avocat.repositories.PayementRepos;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PayementServiceImpl implements IPayementService {
    PayementRepos payementRepos;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Payement> afficherAllPayements() {
        return payementRepos.findAll();
    }

    @Override
    public Payement afficherPayement(Integer idPayement) {
        Optional<Payement> payementOptional = payementRepos.findById(idPayement);
        return payementOptional.orElse(null);
    }

    @Override
    public Payement ajouterPayement(Payement p) {
        p.setDatePayement(LocalDateTime.now());
        return payementRepos.save(p);
    }

    @Override
    public Payement modifierPayement(Payement p) {
        Optional<Payement> payementOptional = payementRepos.findById(p.getIdPayement());
        if (payementOptional.isPresent()) {
            Payement payementExistante = payementOptional.get();
            payementExistante.setDatePayement(p.getDatePayement());
            payementExistante.setMontant(p.getMontant());
            payementExistante.setModePayement(p.getModePayement());
            return payementRepos.save(payementExistante);
        } else {
            return null;
        }
    }

    @Override
    public void supprimerPayement(Integer idPayement) {
         payementRepos.deleteById(idPayement);

    }

    @Override
    public List<Payement> searchPayementByAttributes(Integer idPayement, LocalDateTime datePayement, String montant,
                                                     ModePay modePayement) {
        StringBuilder queryString = new StringBuilder("SELECT p FROM Payement p WHERE 1=1");
        List<String> parameters = new ArrayList<>();

        if (idPayement != null) {
            queryString.append(" AND p.id = :idPayment");
            parameters.add("idPayment");
        }

        if (datePayement != null) {
            queryString.append(" AND p.datePayment = :datePayment");
            parameters.add("datePayment");
        }

        if (montant != null && !montant.isEmpty()) {
            queryString.append(" AND p.montant = :montant");
            parameters.add("montant");
        }

        if (modePayement != null) {
            queryString.append(" AND p.modePayment = :modePayment");
            parameters.add("modePayment");
        }

        TypedQuery<Payement> query = entityManager.createQuery(queryString.toString(), Payement.class);

        for (String parameter : parameters) {
            switch (parameter) {
                case "idPayment":
                    query.setParameter("idPayment", idPayement);
                    break;
                case "datePayment":
                    query.setParameter("datePayment", datePayement);
                    break;
                case "amount":
                    query.setParameter("montant", montant);
                    break;
                case "modePayment":
                    query.setParameter("modePayment", modePayement);
                    break;
            }
        }

        return query.getResultList();
    }

}
