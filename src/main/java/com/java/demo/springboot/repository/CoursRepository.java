package com.java.demo.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.demo.springboot.models.Cours;
import com.java.demo.springboot.models.User;

import java.util.*;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {

     @Query("SELECT c FROM Cours c JOIN FETCH c.formateur f")
    List<Cours> findAllWithTrainers();
    @Query("SELECT c FROM Cours c JOIN FETCH c.formateur f LEFT JOIN FETCH c.etudiants e")
    List<Cours> findAllWithTrainersAndStudents();
    @Query("SELECT c FROM Cours c WHERE c.formateur.id = :formateurId")
    List<Cours> findByFormateurId(Long formateurId);
    List<Cours> findByFormateur(User user);

}
