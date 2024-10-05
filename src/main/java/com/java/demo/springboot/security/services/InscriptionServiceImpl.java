package com.java.demo.springboot.security.services;

import com.java.demo.springboot.models.Cours;
import com.java.demo.springboot.models.ERole;
import com.java.demo.springboot.models.Inscription;
import com.java.demo.springboot.models.InscriptionRequest;
import com.java.demo.springboot.models.User;
import com.java.demo.springboot.repository.CoursRepository;
import com.java.demo.springboot.repository.InscriptionRepository;
import com.java.demo.springboot.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;

@Service
public class InscriptionServiceImpl implements InscriptionService {

    
        @Autowired
        private InscriptionRepository inscriptionRepository;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private CoursRepository coursRepository;
    
        public Inscription saveInscription(Inscription inscription) {
            // Assurez-vous que les entités associées (étudiant et cours) existent
            return inscriptionRepository.save(inscription);
        }
    
        public Optional<Inscription> getInscriptionById(Long id) {
            return inscriptionRepository.findById(id);
        }
    
        public List<Inscription> getAllInscriptions() {
            return inscriptionRepository.findAll();
        }
    
        public void deleteInscription(Long id) {
            inscriptionRepository.deleteById(id);
        }

        public ResponseEntity<String> inscrireEtudiant(InscriptionRequest inscriptionRequest) {
            User etudiant = userRepository.findById(inscriptionRequest.getEtudiantId())
                    .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
            
            Cours cours = coursRepository.findById(inscriptionRequest.getCoursId())
                    .orElseThrow(() -> new RuntimeException("Cours non trouvé"));
        
            // Vérifiez si l'étudiant est déjà inscrit à ce cours avec une inscription active
            Optional<Inscription> existingInscriptionOpt = inscriptionRepository.findByEtudiantAndCours(etudiant, cours);
            
            if (existingInscriptionOpt.isPresent()) {
                Inscription existingInscription = existingInscriptionOpt.get();
                if (existingInscription.getEtat() == 1) {
                    return ResponseEntity.badRequest().body("vous êtes déjà inscrit à ce cours.");
                } else {
                    // Réactiver l'inscription si elle est inactive
                    existingInscription.setEtat(1);
                    inscriptionRepository.save(existingInscription);
                    return ResponseEntity.ok("Inscription réactivée avec succès.");
                }
            }
        
            // Si pas d'inscription existante, on en crée une nouvelle
            Inscription inscription = new Inscription(LocalDate.now(), etudiant, cours);
            inscriptionRepository.save(inscription);
            
            return ResponseEntity.ok("Inscription réussie");
        }
        

        
    
    public List<Inscription> getInscriptionsByCoursId(Long coursId) {
        return inscriptionRepository.findByCoursId(coursId);
    } 

    public List<Inscription> getInscriptionsByEtudiantId(Long etudiantId) {
        return inscriptionRepository.findByEtudiantId(etudiantId);
    }




    public List<Cours> findCoursesByStudentId(Long etudiantId) {
        // Find inscriptions for the student
        List<Inscription> inscriptions = inscriptionRepository.findByEtudiantId(etudiantId);
        
        // Collect course IDs from inscriptions
        List<Long> courseIds = inscriptions.stream()
                                            .map(inscription -> inscription.getCours().getId()) // Get the course ID
                                            .collect(Collectors.toList());
    
        // Fetch courses based on collected IDs
        return coursRepository.findAllById(courseIds);
    }
    


    
    


}