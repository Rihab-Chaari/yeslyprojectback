package com.java.demo.springboot.security.services;

import java.util.List;
import java.util.Optional;

import com.java.demo.springboot.models.User;

public interface UserService {

    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User updatedUser);
    void deleteUser(Long id);
    Long countStudents();
    Long countTrainers();
    Long countRegistrations();
    List<User> getAllStudents();
    List<User> getAllTrainings();
    boolean deleteStudentById(Long id);
    void deleteUserById(Long userId);

}
