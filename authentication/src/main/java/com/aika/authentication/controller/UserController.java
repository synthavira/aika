package com.aika.authentication.controller;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.aika.authentication.entity.*;
import com.aika.authentication.repository.*;
import com.aika.authentication.utility.*;

@RestController

public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordUtility passwordUtility;
	
	@Autowired
	private LoginForm form;
	
	@Autowired
	private JwtToken jwtToken;
	
	@Autowired
	private UpdatePasswordRequest request;
	
    private Set<String> tokenBlacklist = new HashSet<>();

		
	@GetMapping("/api/v1/users")
	public List<User> getAllUser() {
		return userRepo.findAll();
	}
	
	@PostMapping("/api/v1/user/save")
	public  ResponseEntity<String> createUser(@RequestBody User user) {
	    String encodedPassword = passwordUtility.generatePasswordHash(user.getPassword());
	    user.setPassword(encodedPassword);
	    userRepo.save(user);
        return ResponseEntity.ok("User added"); 	
	}

	@PutMapping("/api/v1/update/{user_id}")
	public ResponseEntity<String> updateUser(@PathVariable Long user_id, @RequestBody User user) {
		User existingUser = userRepo.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));

		 existingUser.setFirst_name(user.getFirst_name());
		 existingUser.setLast_name(user.getLast_name());
		 existingUser.setContact(user.getContact());

		user.setUser_id(user_id);
		userRepo.save(user);
		return ResponseEntity.ok("User updated"); 
	}
	
	@PostMapping("/api/v1/login")
	public ResponseEntity<String> login(@RequestBody LoginForm form) {
	    User user = userRepo.findByEmail(form.getEmail());
	    if( passwordUtility.isPasswordSame(form.getPassword(),user.getPassword()) ){
	        String token = jwtToken.generateToken(user);
	        
	        UserSession session = new UserSession();
            session.setToken(token);
            session.setCreated_At(LocalDateTime.now());
            session.setUser_id(user.getUser_id());
            user.setSession(session);
	        
            return ResponseEntity.ok("logged in");
	    }
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	}

	@PostMapping("api/v1/logout")
	public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {

		tokenBlacklist.add(token);
	    return ResponseEntity.ok().build();
	}
	
	@PutMapping("/api/v1/updatepassword")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequest request) {
		
		String email = request.getEmail();
		String currentPassword = request.getCurrentPassword();
		String newPassword = request.getNewPassword();
		String confirmPassword = request.getConfirmPassword();

        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("New password and confirm password do not match");
        }

        if (!authenticateUser(email,currentPassword)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }

        boolean passwordUpdated = updatePasswordInDatabase(email,newPassword);
        if (passwordUpdated) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update password");
        }
    }

    private boolean authenticateUser(String email,String currentPassword) {
        User user = userRepo.findByEmail(email);

        if (user == null || user.getPassword() == null) {
            return false;
        }
        String storedPasswordHash = user.getPassword();
        String currentPasswordHash = passwordUtility.generatePasswordHash(currentPassword);

        return passwordUtility.isPasswordSame(currentPasswordHash, storedPasswordHash);
    }

    private boolean updatePasswordInDatabase(String email,String newPassword) {
    	User user = userRepo.findByEmail(email);
    	if(user == null) {
    		return false;
    	}
    	String hashedPassword = passwordUtility.generatePasswordHash(request.getNewPassword());
    	user.setPassword(hashedPassword);
    	userRepo.save(user);
    	
        return true; 
    }

}
