package com.java.demo.springboot.controllers;


import com.java.demo.springboot.models.Cours;
import com.java.demo.springboot.models.CoursStatsDTO;
import com.java.demo.springboot.models.CourseResponse;
import com.java.demo.springboot.models.User;
import com.java.demo.springboot.repository.CoursRepository;
import com.java.demo.springboot.repository.InscriptionRepository;
import com.java.demo.springboot.security.services.CoursService;
import com.java.demo.springboot.security.services.InscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cours")
public class CoursController {

    @Autowired
    private CoursService coursService;


    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private InscriptionService inscriptionService;
   

    @PreAuthorize("hasRole('ROLE_FORMATEUR')")
  //  @PostMapping( consumes = "application/json")
  @PostMapping
    public ResponseEntity<Cours> createCours(@RequestBody Cours cours) {
        Cours createdCours = coursService.saveCours(cours);
        return ResponseEntity.ok(createdCours);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cours> getCoursById(@PathVariable Long id) {
        return coursService.getCoursById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Cours> getAllCours() {
        return coursService.getAllCours();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cours> updateCours(@PathVariable Long id, @RequestBody Cours cours) {
        Cours updatedCours = coursService.updateCours(id, cours);
        return ResponseEntity.ok(updatedCours);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCours(@PathVariable Long id) {
        coursService.deleteCours(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/with-trainers-and-students")
    public ResponseEntity<List<Cours>> getCoursesWithTrainersAndStudents() {
        List<Cours> courses = coursService.getCoursesWithTrainersAndStudents();
        return ResponseEntity.ok(courses);
    }


    @GetMapping("/{coursId}/students")
    public ResponseEntity<List<User>> getStudentsByCourse(@PathVariable Long coursId) {
        List<User> students = coursService.getStudentsByCourseId(coursId);
        return ResponseEntity.ok(students);
    }


    @GetMapping("/withtrainersandstudents")
    public List<CourseResponse> getAllCoursesWithTrainersAndStudents() {
        List<Cours> courses = coursRepository.findAll();
        List<CourseResponse> courseResponses = new ArrayList<>();
    
        for (Cours course : courses) {
            // Retrieve the list of students registered in the course
            List<User> students = inscriptionRepository.findStudentsByCourseId(course.getId());
    
            // Add each course with its associated students to the CourseResponse list
            courseResponses.add(new CourseResponse(course, students));
        }
    
        return courseResponses;
    }
    

   

    


    @GetMapping("/formateur/{formateurId}")
    public ResponseEntity<List<Cours>> getCoursByFormateur(@PathVariable Long formateurId) {
        List<Cours> cours = coursService.getCoursByFormateur(formateurId);
        if (cours.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retourne 404 Not Found
        }
        return ResponseEntity.ok(cours);
    }

    @GetMapping("/{coursId}/etudiants")
    public ResponseEntity<List<User>> getEtudiantsByCours(@PathVariable Long coursId) {
        List<User> etudiants = coursService.getEtudiantsByCours(coursId);
        if (etudiants.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retourne 404 Not Found
        }
        return ResponseEntity.ok(etudiants);
    }


     @GetMapping("/{id}/stats")
    public ResponseEntity<CoursStatsDTO> getCoursStats(@PathVariable Long id) {
        CoursStatsDTO stats = coursService.getCoursStats(id);
        return ResponseEntity.ok(stats);
    }
    
}
