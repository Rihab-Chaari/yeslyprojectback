package com.java.demo.springboot.models;
import java.time.LocalDate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
@Entity
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateInscription;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    @JsonBackReference // Utilisez BackReference pour éviter la boucle
    private User etudiant;

    @ManyToOne
    @JoinColumn(name = "cours_id")
    @JsonManagedReference // Utilisez ManagedReference pour sérialiser
    private Cours cours;

    private int etat;

    
    public Inscription(LocalDate dateInscription, User etudiant, Cours cours) {
        this.dateInscription = dateInscription;
        this.etudiant = etudiant;
        this.cours = cours;
        this.etat = 1;
    }

    public Inscription() {
    }

    public Inscription(Long id, LocalDate dateInscription) {
        this.id = id;
        this.dateInscription = dateInscription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
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

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    // Getters et Setters
    
    
}

