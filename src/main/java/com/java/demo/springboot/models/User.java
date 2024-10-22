package com.java.demo.springboot.models;

import java.util.HashSet;
import java.util.Set;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;



@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@NotBlank
	@Size(max = 20)
	private String username;



	@NotBlank
	@Size(max = 50)
	@Email
	private String email;



	@NotBlank
	@Size(max = 120)
	private String password;


	@NotBlank
	@Size(max=20)
	private String nom;
	@NotBlank
	@Size(max=20)
    private String prenom;
	@NotBlank
	@Size(max=12)
    private String telephone;
	@NotNull 
	private Integer age;
	@NotBlank @Size(max = 120) 
	private String specialite;

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
	@JsonIgnore 
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "formateur", cascade = CascadeType.ALL)
	@JsonIgnore 
    private Set<Cours> coursEnseignes = new HashSet<>();

    @ManyToMany(mappedBy = "etudiants")
	@JsonIgnore 
    private Set<Cours> coursInscrits = new HashSet<>();

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore 
    private Set<Inscription> inscriptions = new HashSet<>();

	

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@PreRemove
private void removeInscriptions() {
    for (Inscription inscription : inscriptions) {
        inscription.setEtudiant(null); // Detach the user from inscription
    }
}



	

	public User(Long id) {
		this.id = id;
	}




	public User(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 50) @Email String email, @NotBlank @Size(max = 120) String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		
	}

	public User(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 50) @Email String email, @NotBlank @Size(max = 120) String password, @NotBlank @Size(max = 20) String nom, @NotBlank @Size(max = 20) String prenom, @NotBlank @Size(max = 12) String telephone, @NotNull  Integer age, @NotBlank @Size(max = 120) String specialite) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.age=age;
		this.specialite=specialite;
	}

	public User(@NotBlank @Size(max = 20) String nom, @NotBlank @Size(max = 20) String prenom, @NotBlank @Size(max = 12) String telephone, @NotNull  Integer age, @NotBlank @Size(max = 120) String specialite) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.age= age;
		this.specialite=specialite;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}



	public String getNom() {
		return nom;
	}



	public void setNom(String nom) {
		this.nom = nom;
	}



	public String getPrenom() {
		return prenom;
	}



	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}



	public String getTelephone() {
		return telephone;
	}



	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}



	public Integer getAge() {
		return age;
	}



	public void setAge(Integer age) {
		this.age = age;
	}



	public String getSpecialite() {
		return specialite;
	}



	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}



	



	public Set<Cours> getCoursEnseignes() {
		return coursEnseignes;
	}



	public void setCoursEnseignes(Set<Cours> coursEnseignes) {
		this.coursEnseignes = coursEnseignes;
	}



	public Set<Cours> getCoursInscrits() {
		return coursInscrits;
	}



	public void setCoursInscrits(Set<Cours> coursInscrits) {
		this.coursInscrits = coursInscrits;
	}




	public Set<Inscription> getInscriptions() {
		return inscriptions;
	}




	public void setInscriptions(Set<Inscription> inscriptions) {
		this.inscriptions = inscriptions;
	}



	



	



	

	
	
}
