package com.java.demo.springboot.security.services;

import com.java.demo.springboot.models.Paiement;
import java.util.List;
import java.util.Optional;

public interface PaiementService {

    Paiement savePaiement(Paiement paiement);
    Optional<Paiement> getPaiementById(Long id);
    List<Paiement> getAllPaiements();
    void deletePaiement(Long id);
    Paiement processPayment(Long etudiantId, Long coursId) ;
    boolean hasPaid(Long etudiantId, Long coursId);
    List<Paiement> getPaiementsByEtudiantId(Long etudiantId);
    List<Paiement>  getPaiementsByCourseId(Long coursId) ;
}
