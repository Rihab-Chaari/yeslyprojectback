package com.java.demo.springboot.security.services;

import com.java.demo.springboot.models.Cours;
import com.java.demo.springboot.models.Inscription;
import com.java.demo.springboot.models.InscriptionRequest;
import com.java.demo.springboot.models.User;

import java.util.*;

import org.springframework.http.ResponseEntity;
public interface InscriptionService {
    
    Inscription saveInscription(Inscription inscription);
    Optional<Inscription> getInscriptionById(Long id);
    List<Inscription> getAllInscriptions();
    void deleteInscription(Long id);
     //ResponseEntity<?> inscrireEtudiant(InscriptionRequest inscriptionRequest) throws Exception;
     ResponseEntity<String> inscrireEtudiant(InscriptionRequest inscriptionRequest);
     List<Inscription> getInscriptionsByCoursId(Long coursId);
     List<Inscription> getInscriptionsByEtudiantId(Long etudiantId);
     List<Cours> findCoursesByStudentId(Long etudiantId);
    
}

