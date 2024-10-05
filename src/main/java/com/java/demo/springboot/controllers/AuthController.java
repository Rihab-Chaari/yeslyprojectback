package com.java.demo.springboot.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.demo.springboot.models.Cours;
import com.java.demo.springboot.models.ERole;
import com.java.demo.springboot.models.Inscription;
import com.java.demo.springboot.models.Paiement;
import com.java.demo.springboot.models.Role;
import com.java.demo.springboot.models.User;
import com.java.demo.springboot.payload.request.LoginRequest;
import com.java.demo.springboot.payload.request.SignupRequest;
import com.java.demo.springboot.payload.response.JwtResponse;
import com.java.demo.springboot.payload.response.MessageResponse;
import com.java.demo.springboot.repository.CoursRepository;
import com.java.demo.springboot.repository.InscriptionRepository;
import com.java.demo.springboot.repository.PaiementRepository;
import com.java.demo.springboot.repository.RoleRepository;
import com.java.demo.springboot.repository.UserRepository;
import com.java.demo.springboot.security.jwt.JwtUtils;
import com.java.demo.springboot.security.services.UserDetailsImpl;
import com.java.demo.springboot.security.services.UserService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
    UserService userService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
    private PaiementRepository paiementRepository;

	@Autowired
	private CoursRepository coursRepository;

	@Autowired
	private InscriptionRepository inscriptionRepository;

	


	 @PostMapping("/signin")
	  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

	    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    String jwt = jwtUtils.generateJwtToken(authentication);
	    
	    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
	    List<String> roles = userDetails.getAuthorities().stream()
	        .map(item -> item.getAuthority())
	        .collect(Collectors.toList());

	    return ResponseEntity.ok(new JwtResponse(jwt, 
	                         userDetails.getId(), 
	                         userDetails.getUsername(), 
	                         userDetails.getEmail(), 
	                         roles));
	  }
	  @PostMapping("/signup")
	  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		  // Check if the username is already taken
		  if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			  return ResponseEntity
					  .badRequest()
					  .body(new MessageResponse("Error: Username is already taken!"));
		  }
	  
		  // Check if the email is already in use
		  if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			  return ResponseEntity
					  .badRequest()
					  .body(new MessageResponse("Error: Email is already in use!"));
		  }
	  
		  // Create a new user account
		  User user = new User(
			  signUpRequest.getUsername(),
			  signUpRequest.getEmail(),
			  encoder.encode(signUpRequest.getPassword()),
			  signUpRequest.getNom(),
			  signUpRequest.getPrenom(),
			  signUpRequest.getTelephone(),
			  signUpRequest.getAge(),
			  signUpRequest.getSpecialite()
		  );
	  
		  Set<String> strRoles = signUpRequest.getRole(); // This contains the role passed from the frontend
		  Set<Role> roles = new HashSet<>();
	  
		  // If no roles are provided, default to ROLE_ETUDIANT
		  if (strRoles == null) {
			  Role userRole = roleRepository.findByName(ERole.ROLE_ETUDIANT)
					  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			  roles.add(userRole);
		  } else {
			  strRoles.forEach(role -> {
				  switch (role) {
					  case "responsable":
						  Role responsableRole = roleRepository.findByName(ERole.ROLE_RESPONSABLE)
								  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						  roles.add(responsableRole);
						  break;
					  case "formateur":
						  Role formateurRole = roleRepository.findByName(ERole.ROLE_FORMATEUR)
								  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						  roles.add(formateurRole);
						  break;
					  default:
						  Role etudiantRole = roleRepository.findByName(ERole.ROLE_ETUDIANT)
								  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						  roles.add(etudiantRole);
						  break;
				  }
			  });
		  }
	  
		  user.setRoles(roles);
		  userRepository.save(user);
	  
		  return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	  }
	  
	@GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

	@GetMapping("/students")
    public ResponseEntity<List<User>> getStudents() {
        List<User> students = userService.getAllStudents();
        return ResponseEntity.ok(students);
    }
	@DeleteMapping("/students/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
		boolean isDeleted = userService.deleteStudentById(id);
		System.out.println("Deleting student with ID: " + id + ", Deleted: " + isDeleted);
		
		if (isDeleted) {
			return ResponseEntity.noContent().build(); 
		} else {
			return ResponseEntity.notFound().build(); 
		}
	}
	
	@GetMapping("/trainers")
    public ResponseEntity<List<User>> getTrainings() {
        List<User> students = userService.getAllTrainings();
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }



	

	

}