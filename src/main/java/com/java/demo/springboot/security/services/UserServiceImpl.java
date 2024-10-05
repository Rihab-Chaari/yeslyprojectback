package com.java.demo.springboot.security.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.demo.springboot.models.ERole;
import com.java.demo.springboot.models.Role;
import com.java.demo.springboot.models.User;
import com.java.demo.springboot.repository.InscriptionRepository;
import com.java.demo.springboot.repository.UserRepository;
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long userId) {
        // First, delete inscriptions related to the user
        inscriptionRepository.deleteByEtudiantId(userId);
        
        // Then, delete the user
        userRepository.deleteById(userId);
    }
    

    @Override
    public List<User> getAllStudents() {
        List<User> users = userRepository.findAll();
        
        // Ajouter un log pour voir les utilisateurs et leurs rôles
        users.forEach(user -> {
            System.out.println("User: " + user.getUsername() + ", Roles: " + user.getRoles());
        });
        
        return users.stream()
            .filter(user -> user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(ERole.ROLE_ETUDIANT)))
            .collect(Collectors.toList());
    }

    @Override
    public Long countStudents() {
        return userRepository.countByRole(ERole.ROLE_ETUDIANT);
    }

    @Override
    public Long countTrainers() {
        return userRepository.countByRole(ERole.ROLE_FORMATEUR);
    }
    @Override
    public Long countRegistrations() {
        return userRepository.count();
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if(existingUser.isPresent()){
            User user = existingUser.get();
            user.setNom(updatedUser.getNom());
            user.setPrenom(updatedUser.getPrenom());
            user.setTelephone(updatedUser.getTelephone());
            user.setAge(updatedUser.getAge());
            user.setSpecialite(updatedUser.getSpecialite());
            return userRepository.save(user);

        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
         userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllTrainings() {
        
        List<User> users = userRepository.findAll();
        
        // Ajouter un log pour voir les utilisateurs et leurs rôles
        users.forEach(user -> {
            System.out.println("User: " + user.getUsername() + ", Roles: " + user.getRoles());
        });
        
        return users.stream()
            .filter(user -> user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(ERole.ROLE_FORMATEUR)))
            .collect(Collectors.toList());
    }

    

    @Override
    public boolean deleteStudentById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
       
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Vérifiez que l'utilisateur a le rôle étudiant
            for (Role role : user.getRoles()) {
                if (role.getName().equals(ERole.ROLE_ETUDIANT)) {
                    userRepository.deleteById(id);
                    return true; // Suppression réussie
                }
            }
            
            System.out.println("User is not a student.");
        }
        
        return false; // L'utilisateur n'existe pas ou n'est pas un étudiant
    }


    
    


}
