package com.java.demo.springboot.security.services;

import com.java.demo.springboot.models.Cours;
import com.java.demo.springboot.models.Paiement;
import com.java.demo.springboot.models.StatutPaiement;
import com.java.demo.springboot.models.User;
import com.java.demo.springboot.repository.CoursRepository;
import com.java.demo.springboot.repository.InscriptionRepository;
import com.java.demo.springboot.repository.PaiementRepository;
import com.java.demo.springboot.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PaiementServiceImpl implements PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;
     @Autowired
    private CoursRepository coursRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Override
    public Paiement savePaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public Optional<Paiement> getPaiementById(Long id) {
        return paiementRepository.findById(id);
    }

    @Override
    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    @Override
    public void deletePaiement(Long id) {
        paiementRepository.deleteById(id);
    }


    public boolean hasPaid(Long etudiantId, Long coursId) {
        // Rechercher les paiements existants pour cet étudiant et ce cours
        return paiementRepository.existsByEtudiantIdAndCoursId(etudiantId, coursId);
    }
    
    
    public boolean isStudentEnrolled(Long etudiantId, Long coursId) {
        // Vérifiez si l'étudiant est inscrit à ce cours
        return inscriptionRepository.existsByEtudiantIdAndCoursId(etudiantId, coursId);
    }
    

    public Paiement processPayment(Long etudiantId, Long coursId) {
        // Vérifiez si un paiement existe déjà pour cet étudiant et ce cours
        Optional<Paiement> existingPaiement = paiementRepository
            .findByEtudiantIdAndCoursId(etudiantId, coursId);

        if (existingPaiement.isPresent() && existingPaiement.get().getStatutPaiement() == StatutPaiement.PAYE) {
            throw new RuntimeException("Le paiement a déjà été effectué pour ce cours.");
        }
         // Récupérer le cours et son montant
         Cours cours = coursRepository.findById(coursId)
         .orElseThrow(() -> new RuntimeException("Cours non trouvé"));

        // Créer un nouveau paiement
        Paiement paiement = new Paiement();
        paiement.setEtudiant(new User(etudiantId)); // Assurez-vous d'utiliser le bon utilisateur
        paiement.setCours(new Cours(coursId));      // Assurez-vous d'utiliser le bon cours
        paiement.setMontant(cours.getMontant());
        paiement.setDatePaiement(LocalDate.now());
        paiement.setStatutPaiement(StatutPaiement.PAYE); // Le paiement est maintenant marqué comme payé

        return paiementRepository.save(paiement); // Sauvegarder le paiement
    }
    

    @Override
    public List<Paiement> getPaiementsByEtudiantId(Long etudiantId) {
        return paiementRepository.findByEtudiantId(etudiantId);
    }


    

    @Override
    public List<Paiement> getPaiementsByCourseId(Long coursId) {
        return paiementRepository.findByCoursId(coursId);
    }

    
}
