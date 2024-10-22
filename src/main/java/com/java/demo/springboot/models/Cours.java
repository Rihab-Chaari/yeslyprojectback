package com.java.demo.springboot.models;
import java.time.LocalDate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.*;
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") 
    private LocalDate dateDebut;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") 
    private LocalDate dateFin;
    private Integer duree; // en heures
    private Double montant;
    @ManyToOne
    @JoinColumn(name = "formateur_id")
   // @JsonIgnore 
    private User formateur;

    @ManyToMany
    @JoinTable(name = "cours_etudiants",
            joinColumns = @JoinColumn(name = "cours_id"),
            inverseJoinColumns = @JoinColumn(name = "etudiant_id"))
    @JsonIgnore 
    private Set<User> etudiants = new HashSet<>();

    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL)
    @JsonIgnore 
    private Set<Paiement> paiements = new HashSet<>();

    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL)
    @JsonIgnore 
    private Set<Inscription> inscriptions = new HashSet<>();


    

    public Cours(Long id) {
        this.id = id;
    }

    public Cours() {
    }

    public Cours(Long id, String titre, String description, LocalDate dateDebut, LocalDate dateFin, Integer duree) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.duree = duree;
    }

    public Cours(Long id, String titre, String description, LocalDate dateDebut, LocalDate dateFin, Integer duree,double montant) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.duree = duree;
        this.montant=montant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public User getFormateur() {
        return formateur;
    }

    public void setFormateur(User formateur) {
        this.formateur = formateur;
    }

     public Set<User> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(Set<User> etudiants) {
        this.etudiants = etudiants;
    }

     public Set<Inscription> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Set<Paiement> getPaiements() {
        return paiements;
    }

    public void setPaiements(Set<Paiement> paiements) {
        this.paiements = paiements;
    } 

    
    // Getters et Setters
    
    
}

