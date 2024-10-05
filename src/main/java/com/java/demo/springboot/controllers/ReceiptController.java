package com.java.demo.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.demo.springboot.security.services.PaiementService;
import com.java.demo.springboot.security.services.ReceiptService;



import com.java.demo.springboot.models.Paiement;
import com.java.demo.springboot.models.StatutPaiement;

import java.io.ByteArrayInputStream;
import java.util.Optional;
import java.util.List;
@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    @Autowired
    private PaiementService paiementService;

    @Autowired
    private ReceiptService receiptService;

    @GetMapping("/download/{paiementId}")
public ResponseEntity<InputStreamResource> downloadReceipt(@PathVariable Long paiementId) {
    // Récupérer le paiement à partir de l'ID, qui retourne un Optional
    Optional<Paiement> paiementOptional = paiementService.getPaiementById(paiementId);

    // Vérifier si le paiement existe et s'il est PAYÉ
    if (!paiementOptional.isPresent() || paiementOptional.get().getStatutPaiement() != StatutPaiement.PAYE) {
        return ResponseEntity.badRequest().body(null);
    }

    // Si le paiement est trouvé et est PAYÉ, récupérer l'objet Paiement
    Paiement paiement = paiementOptional.get();

    // Générer le PDF du reçu
    ByteArrayInputStream bis = receiptService.generateReceipt(paiement);

    // Créer l'en-tête pour le téléchargement
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "inline; filename=receipt.pdf");

    return ResponseEntity.ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(bis));
}


@GetMapping("/student/{etudiantId}/receipts")
public ResponseEntity<List<Paiement>> getStudentReceipts(@PathVariable Long etudiantId) {
    List<Paiement> paiements = paiementService.getPaiementsByEtudiantId(etudiantId);
    return ResponseEntity.ok(paiements);
}
}

