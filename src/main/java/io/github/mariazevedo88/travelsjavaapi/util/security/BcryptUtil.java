package io.github.mariazevedo88.travelsjavaapi.util.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * BCrypt implements OpenBSD-style Blowfish password hashing using the scheme described in 
 * "A Future-Adaptable Password Scheme" by Niels Provos and David Mazieres. This class 
 * implements some utility methods to generate a password hashing based on Bcrypt algorithm.
 * 
 * @author Mariana Azevedo
 * @since 11/10/2020
 */
public class BcryptUtil {
	
	private BcryptUtil() {}
	
	/**
	 * Method that encode the raw password.
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @param password
	 * @return String password encoded
	 */
	public static String getHash(String password) {
		
		if(password == null) {
			return null;
		}
		
		if(BcryptUtil.isEncrypted(password)) {
			return password;
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
	
	/**
	 * Method that check if the password is already encoding.
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @param password
	 * @return boolean
	 */
	public static boolean isEncrypted(String password) {
		return password.startsWith("$2a$");
	}
	
	/**
	 * Method that decode the raw password.
	 * 
	 * @author Mariana Azevedo
	 * @since 16/12/2020
	 * 
	 * @param password
	 * @param encrypted
	 * @return String password decoded
	 */
	public static String decode(String password, String encrypted) throws Exception {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		boolean isPasswordMatches = bcrypt.matches(password, encrypted);
		
		if(!isPasswordMatches)
			throw new Exception("Password does not match.");
		
		return password;
	}

}
