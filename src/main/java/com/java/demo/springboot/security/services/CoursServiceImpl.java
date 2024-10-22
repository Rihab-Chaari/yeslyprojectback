package com.java.demo.springboot.security.services;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.demo.springboot.models.Cours;
import com.java.demo.springboot.models.CoursStatsDTO;
import com.java.demo.springboot.models.ERole;
import com.java.demo.springboot.models.Inscription;
import com.java.demo.springboot.models.Paiement;
import com.java.demo.springboot.models.StatutPaiement;
import com.java.demo.springboot.models.User;
import com.java.demo.springboot.repository.CoursRepository;
import com.java.demo.springboot.repository.InscriptionRepository;
import com.java.demo.springboot.repository.PaiementRepository;
import com.java.demo.springboot.repository.UserRepository;
@Service
public class CoursServiceImpl implements CoursService {

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InscriptionRepository inscriptionRepository;
    @Autowired
    private PaiementRepository paiementRepository;
    
    public Cours saveCours(Cours cours) {
        // Vérifiez si le formateur est null avant de récupérer son ID
        if (cours.getFormateur() == null || cours.getFormateur().getId() == null) {
            throw new IllegalArgumentException("Formateur must be provided and should not be null");
        }
    
        // Récupérer le formateur depuis l'id
        Long formateurId = cours.getFormateur().getId();
        User formateur = userRepository.findById(formateurId)
            .orElseThrow(() -> new RuntimeException("Formateur not found"));
    
        // Assigner le formateur au cours
        cours.setFormateur(formateur);
    
        return coursRepository.save(cours);
    }
    

    @Override
    public Long countCourses() {
        return coursRepository.count();
    }

    @Override
    public Optional<Cours> getCoursById(Long id) {
        return coursRepository.findById(id);
    }

    @Override
    public List<Cours> getAllCours() {
        return coursRepository.findAll();
    }

    public Cours updateCours(Long id, Cours updatedCours) {
        Optional<Cours> existingCours = coursRepository.findById(id);
        if(existingCours.isPresent()) {
            Cours cours = existingCours.get();
            cours.setTitre(updatedCours.getTitre());
            cours.setDescription(updatedCours.getDescription());
            cours.setDateDebut(updatedCours.getDateDebut());
            cours.setDateFin(updatedCours.getDateFin());
            cours.setDuree(updatedCours.getDuree());
            cours.setMontant(updatedCours.getMontant());
    
            System.out.println("Saving updated course: " + cours.toString()); // Debug log
            return coursRepository.save(cours);
        }
        return null;
    }
    

    @Override
    public void deleteCours(Long id) {
        coursRepository.deleteById(id);
    }


    @Override
    public List<Cours> getCoursesWithTrainersAndStudents() {
        return coursRepository.findAllWithTrainersAndStudents();
    }


    @Override
    public List<User> getStudentsByCourseId(Long courseId) {
        return inscriptionRepository.findStudentsByCourseId(courseId);
    }


    @Override
    public boolean isFormateurAssignedToCours(Long formateurId, Long coursId) {
        Cours cours = coursRepository.findById(coursId).orElse(null);
        return cours != null && cours.getFormateur().getId().equals(formateurId);
    }


    public List<Cours> getCoursByFormateur(Long formateurId) {
        return coursRepository.findByFormateurId(formateurId);
    }

    public List<User> getEtudiantsByCours(Long coursId) {
        // Utilisez Optional pour vérifier si le Cours existe
        Optional<Cours> optionalCours = coursRepository.findById(coursId);
        if (optionalCours.isPresent()) {
            // Retourner les étudiants inscrits au cours
            return new ArrayList<>(optionalCours.get().getEtudiants());
        } else {
            // Retourner une liste vide si le Cours n'existe pas
            return new ArrayList<>();
        }
    }
   


    public CoursStatsDTO getCoursStats(Long coursId) {
        Cours cours = coursRepository.findById(coursId).orElseThrow(() -> new RuntimeException("Cours non trouvé"));
        
        Long totalEtudiantsInscrits = inscriptionRepository.countByCours(cours);
        Long totalEtudiantsPayants = paiementRepository.countByCoursAndStatutPaiement(cours, StatutPaiement.PAYE);

        return new CoursStatsDTO(
            cours.getId(),
            cours.getTitre(),
            totalEtudiantsInscrits,
            totalEtudiantsPayants
        );
    }


  

}
