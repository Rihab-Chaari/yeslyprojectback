package com.java.demo.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.java.demo.springboot.models.ERole;
import com.java.demo.springboot.models.Role;
import com.java.demo.springboot.models.User;
import com.java.demo.springboot.repository.RoleRepository;
import com.java.demo.springboot.repository.UserRepository;
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private  RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        //vérifie si les roles existent déja
        if(roleRepository.findByName(ERole.ROLE_RESPONSABLE).isEmpty()){
            roleRepository.save(new Role(ERole.ROLE_RESPONSABLE));
        }
        if(roleRepository.findByName(ERole.ROLE_FORMATEUR).isEmpty()){
            roleRepository.save(new Role(ERole.ROLE_FORMATEUR));
        }
        if(roleRepository.findByName(ERole.ROLE_ETUDIANT).isEmpty()){
            roleRepository.save(new Role(ERole.ROLE_ETUDIANT));
        }

        // initialisation de l'utilisateur responsable
        if(userRepository.findByUsername("Ibtissem").isEmpty()){
            User responsable = new User();
            responsable.setUsername("Ibtissem");
            responsable.setEmail("myowntrainingyesly@gmail.com");
            responsable.setPassword(passwordEncoder.encode("yeslytraining123*"));
            responsable.setNom("karoui");
            responsable.setPrenom("ibtissem");
            responsable.setAge(35);
            responsable.setTelephone("22221264");
            responsable.setSpecialite("directrice de centre de formation yesly");

            //ajouter le role responsable a l'utilisateur
            Role roleresponsable = roleRepository.findByName(ERole.ROLE_RESPONSABLE).get();
            responsable.getRoles().add(roleresponsable);

            //sauvgarde de l'utilisateur
            userRepository.save(responsable);
        }
    }

}
