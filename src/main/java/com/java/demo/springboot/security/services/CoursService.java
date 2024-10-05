package com.java.demo.springboot.security.services;

import java.util.*;

import com.java.demo.springboot.models.Cours;
import com.java.demo.springboot.models.User;

public interface CoursService {

    Cours saveCours(Cours cours);
    Optional<Cours> getCoursById(Long id);
    List<Cours> getAllCours();
    Cours updateCours(Long id, Cours updatedCours);
    void deleteCours(Long id);
    Long countCourses();
    List<Cours> getCoursesWithTrainersAndStudents();
    List<User> getStudentsByCourseId(Long courseId);
    boolean isFormateurAssignedToCours(Long formateurId, Long coursId);
    List<Cours> getCoursByFormateur(Long formateurId);
    List<User> getEtudiantsByCours(Long coursId);
   

}
