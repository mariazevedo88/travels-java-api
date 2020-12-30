package io.github.mariazevedo88.travelsjavaapi.model.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.github.mariazevedo88.travelsjavaapi.enumeration.RoleEnum;
import io.github.mariazevedo88.travelsjavaapi.model.user.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Class that implements a factory to create a JwtUser.
 * 
 * @author Mariana Azevedo
 * @since 11/10/2020
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUserFactory {
	
	/**
	 * Method to create a JwtUser.
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @param user
	 * @return JwtUser object
	 */
	public static JwtUser create(User user) {
		return new JwtUser(user.getId(), user.getEmail(), user.getPassword(), createGrantedAuthorities(user.getRole()));
	}
	
	/**
	 * Method to grant authorities to a JwtUser.
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @param role
	 * @return List<GrantedAuthority>
	 */
	private static List<GrantedAuthority> createGrantedAuthorities(RoleEnum role) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		return authorities;
	}

}
