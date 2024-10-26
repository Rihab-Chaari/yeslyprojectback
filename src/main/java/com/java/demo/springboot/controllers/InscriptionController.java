package com.java.demo.springboot.controllers;



import com.java.demo.springboot.models.Cours;
import com.java.demo.springboot.models.Inscription;
import com.java.demo.springboot.models.InscriptionRequest;
import com.java.demo.springboot.models.User;
import com.java.demo.springboot.payload.response.MessageResponse;
import com.java.demo.springboot.repository.InscriptionRepository;
import com.java.demo.springboot.security.services.CoursService;
import com.java.demo.springboot.security.services.InscriptionService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.ArrayList;
@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionController {

    @Autowired
    private InscriptionService inscriptionService;
    @Autowired
    private CoursService coursService;
    @Autowired
    private InscriptionRepository inscriptionRepository;
    
    @PostMapping
    public ResponseEntity<Inscription> createInscription(@RequestBody Inscription inscription) {
        try {
            Inscription newInscription = inscriptionService.saveInscription(inscription);
            return new ResponseEntity<>(newInscription, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Inscription> getInscriptionById(@PathVariable Long userId) {
        return inscriptionService.getInscriptionById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Inscription>> getAllInscriptions() {
        List<Inscription> inscriptions = inscriptionService.getAllInscriptions();
        return ResponseEntity.ok(inscriptions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInscription(@PathVariable Long id) {
        inscriptionService.deleteInscription(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("inscri/{etudiantId}")
public ResponseEntity<List<Long>> getRegisteredCourses(@PathVariable Long etudiantId) {
    List<Long> registeredCourseIds = inscriptionRepository.findRegisteredCourseIdsByEtudiant(etudiantId);
    return ResponseEntity.ok(registeredCourseIds);
}

@PostMapping("/inscri")
    public ResponseEntity<String> inscrireEtudiant(@RequestBody InscriptionRequest inscriptionRequest) {
        return inscriptionService.inscrireEtudiant(inscriptionRequest);
    }




    @GetMapping("/formateur/{formateurId}/cours/{coursId}/etudiants")
    public ResponseEntity<List<User>> getEtudiantsByCours(@PathVariable Long formateurId, @PathVariable Long coursId) {
        // Vérification que le cours appartient bien au formateur
        if (coursService.isFormateurAssignedToCours(formateurId, coursId)) {
            List<Inscription> inscriptions = inscriptionService.getInscriptionsByCoursId(coursId);
            List<User> etudiants = inscriptions.stream()
                    .map(Inscription::getEtudiant)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(etudiants);
        } else {
            return ResponseEntity.status(403).body(null); // Si le formateur n'a pas accès à ce cours
        }
    }



    @GetMapping("/student/{etudiantId}/cours-registrations")
    public ResponseEntity<List<Cours>> getCoursesByStudentId(@PathVariable Long etudiantId) {
        List<Cours> courses = inscriptionService.findCoursesByStudentId(etudiantId);
        
        if (courses == null || courses.isEmpty()) {
            // Return a 404 if no courses found
            return ResponseEntity.notFound().build(); 
        }
        
        return ResponseEntity.ok(courses);
    }
    


    @GetMapping("/recommendations/{etudiantId}")
    public List<HashMap<String, Object>> getRecommendations(@PathVariable int etudiantId) {
        List<HashMap<String, Object>> recommendations = new ArrayList<>();
        try {
            // Exécute le script Python avec l'ID de l'étudiant comme argument
            ProcessBuilder pb = new ProcessBuilder("python", "path/to/your_script.py", String.valueOf(etudiantId));
            Process process = pb.start();

            // Lire la sortie du script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                // Parse la ligne et ajoute aux recommandations
                String[] parts = line.split(",");
                HashMap<String, Object> entry = new HashMap<>();
                entry.put("cours_id", Integer.parseInt(parts[0]));
                entry.put("rating", Double.parseDouble(parts[1]));
                recommendations.add(entry);
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recommendations;
    }
  
}