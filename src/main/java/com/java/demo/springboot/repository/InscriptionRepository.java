package com.java.demo.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.demo.springboot.models.Cours;
import com.java.demo.springboot.models.Inscription;
import com.java.demo.springboot.models.User;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

    @Query("SELECT i.etudiant FROM Inscription i JOIN i.etudiant.roles r WHERE i.cours.id = :coursId AND r.name = 'ROLE_ETUDIANT'")
    List<User> findStudentsByCourseId(@Param("coursId") Long coursId);
   
    List<Inscription> findByCoursId(Long coursId);
    boolean existsByEtudiantAndCours(User etudiant, Cours cours);
    
    @Query("SELECT i.cours.id FROM Inscription i WHERE i.etudiant.id = :etudiantId")
    List<Long> findRegisteredCourseIdsByEtudiant(@Param("etudiantId") Long etudiantId);

    Optional<Inscription> findByEtudiantAndCours(User etudiant, Cours cours);
    List<Inscription> findByEtudiantId(Long etudiantId);

    boolean existsByEtudiantIdAndCoursId(Long etudiantId, Long coursId);
    void deleteByEtudiantId(Long etudiantId);
    List<Inscription> findByCours(Cours cours);
    
  
    

}
