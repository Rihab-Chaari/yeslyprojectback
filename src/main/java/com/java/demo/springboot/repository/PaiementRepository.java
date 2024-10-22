package com.java.demo.springboot.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.demo.springboot.models.Cours;
import com.java.demo.springboot.models.Paiement;
import com.java.demo.springboot.models.StatutPaiement;
import com.java.demo.springboot.models.User;

import java.util.Optional;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long>{

    List<Paiement> findByEtudiantId(Long etudiantId);
    List<Paiement> findByCoursId(Long coursId);
    boolean existsByEtudiantIdAndCoursId(Long etudiantId, Long coursId);
    Optional<Paiement> findByEtudiantIdAndCoursId(Long etudiantId, Long coursId);
     List<Paiement> findByEtudiantIdAndStatutPaiement(Long etudiantId, StatutPaiement statutPaiement);
     void deleteByCours(Cours cours);
    List<Paiement> findByEtudiant(User user);
    Long countByCoursAndStatutPaiement(Cours cours, StatutPaiement statutPaiement);
   

}
