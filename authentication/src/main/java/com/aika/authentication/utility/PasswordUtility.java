package com.aika.authentication.utility;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;
 
@Component
public class PasswordUtility {

	public String generatePasswordHash(String userPassword) {
		return BCrypt.hashpw(userPassword, BCrypt.gensalt());
	}
 
	public boolean isPasswordSame(String userPassword, String encryptedPassword) {
		if (userPassword == null) {
			return false;
		}
		if (encryptedPassword == null) {
			return false;
		}
		return BCrypt.checkpw(userPassword, encryptedPassword);
	}
}