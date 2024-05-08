package com.aika.authentication.utility;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import com.aika.authentication.entity.*;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class JwtToken {

	private static final String SECRET_KEY = keyGenerator();
	private static String keyGenerator() {
		 
		 byte[] secretKeyBytes = new byte[32];
	     new SecureRandom().nextBytes(secretKeyBytes);
	     return Base64.getEncoder().encodeToString(secretKeyBytes);
	     
	 }
	
	 @SuppressWarnings("deprecation")
	public String generateToken(User user) {
	        // Extract user details from the user object
	        String userId = Long.toString(user.getUser_id());
	        String username = user.getFirst_name();

	        // Generate JWT token
	        return Jwts.builder()
	                .claim("userId", userId)
	                .claim("username", username)
	                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
	                .compact();
	    }
	
}
