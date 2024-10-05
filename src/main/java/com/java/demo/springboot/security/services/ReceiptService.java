package com.java.demo.springboot.security.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.java.demo.springboot.models.Paiement;
import com.java.demo.springboot.models.StatutPaiement;
import com.java.demo.springboot.repository.PaiementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.ArrayList;
@Service
public class ReceiptService {

    @Autowired
    PaiementRepository paiementRepository;

    public ByteArrayInputStream generateReceipt(Paiement paiement) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Ajouter le contenu du reçu
            document.add(new Paragraph("Reçu de Paiement"));
            document.add(new Paragraph("Nom du cours : " + paiement.getCours().getTitre()));
            document.add(new Paragraph("Nom du formateur : " + paiement.getCours().getFormateur().getNom() + " " +
                    paiement.getCours().getFormateur().getPrenom()));
            document.add(new Paragraph("Montant payé : " + paiement.getMontant() + " DT"));
            document.add(new Paragraph("Date de paiement : " + paiement.getDatePaiement()));

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }


    public List<Paiement> getPaiementsByEtudiantId(Long etudiantId) {
    List<Paiement> paiements = paiementRepository.findByEtudiantIdAndStatutPaiement(etudiantId, StatutPaiement.PAYE);
    List<Paiement> paiementDTOs = new ArrayList<>();

    for (Paiement paiement : paiements) {
        Paiement dto = new Paiement();
        dto.setId(paiement.getId());
        dto.setMontant(paiement.getMontant());
        dto.setStatutPaiement(paiement.getStatutPaiement());
        // Ajoutez d'autres champs si nécessaire
        paiementDTOs.add(dto);
    }

    return paiementDTOs;
}

}

