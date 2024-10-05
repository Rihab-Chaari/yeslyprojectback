package com.java.demo.springboot.controllers;


import com.java.demo.springboot.models.Paiement;
import com.java.demo.springboot.repository.PaiementRepository;
import com.java.demo.springboot.security.services.PaiementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController {

    @Autowired
    private PaiementService paiementService;

    @Autowired
    private PaiementRepository paiementRepository;

    @PostMapping
    public ResponseEntity<Paiement> createPaiement(@RequestBody Paiement paiement) {
        Paiement createdPaiement = paiementService.savePaiement(paiement);
        return ResponseEntity.ok(createdPaiement);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paiement> getPaiementById(@PathVariable Long id) {
        return paiementService.getPaiementById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Paiement> getAllPaiements() {
        return paiementService.getAllPaiements();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaiement(@PathVariable Long id) {
        paiementService.deletePaiement(id);
        return ResponseEntity.noContent().build();
    }



    @PostMapping("/pay")
public ResponseEntity<String> processPayment(@RequestParam Long etudiantId, @RequestParam Long coursId) {
    try {
        Paiement paiement = paiementService.processPayment(etudiantId, coursId);
        return ResponseEntity.ok("Paiement de " + paiement.getMontant() + " DT effectué avec succès !");
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors du paiement : " + e.getMessage());
    }
}


    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Paiement>> getPaymentsByStudent(@PathVariable Long studentId) {
        List<Paiement> paiements = paiementRepository.findByEtudiantId(studentId);
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Paiement>> getPaymentsByCourse(@PathVariable Long courseId) {
        List<Paiement> paiements = paiementRepository.findByCoursId(courseId);
        return ResponseEntity.ok(paiements);
    }



   
}

