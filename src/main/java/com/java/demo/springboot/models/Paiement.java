package com.java.demo.springboot.models;
import java.time.LocalDate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
@Entity
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double montant;
    private LocalDate datePaiement;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    @JsonBackReference // Éviter la boucle
    private User etudiant;

    @ManyToOne
    @JoinColumn(name = "cours_id")
  //  @JsonManagedReference // Gérer la relation cours
  @JsonBackReference
    private Cours cours;

    @Enumerated(EnumType.STRING)
    private StatutPaiement statutPaiement;

   

    
    public Paiement() {
    }

    public Paiement(Long id, Double montant, LocalDate datePaiement) {
        this.id = id;
        this.montant = montant;
        this.datePaiement = datePaiement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public User getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(User etudiant) {
        this.etudiant = etudiant;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public StatutPaiement getStatutPaiement() {
        return statutPaiement;
    }

    public void setStatutPaiement(StatutPaiement statutPaiement) {
        this.statutPaiement = statutPaiement;
    }

  
    
}

