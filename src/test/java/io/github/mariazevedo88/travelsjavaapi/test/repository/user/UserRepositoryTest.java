package io.github.mariazevedo88.travelsjavaapi.test.repository.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.mariazevedo88.travelsjavaapi.enumeration.RoleEnum;
import io.github.mariazevedo88.travelsjavaapi.model.user.User;
import io.github.mariazevedo88.travelsjavaapi.repository.user.UserRepository;

/**
 * Class that implements tests of the UserRepository functionalities
 * 
 * @author Mariana Azevedo
 * @since 09/11/2020
 */
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class UserRepositoryTest {
	
	static final String EMAIL = "email@test.com";

	@Autowired
	UserRepository repository;
	
	User user;
	
	/**
	 * Method that test the repository that save an User in the API.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@Test
	public void testSave() {
		
		User user = new User(null, "Setup User", "123", EMAIL, 
				RoleEnum.ROLE_ADMIN);
		User response = repository.save(user);
		
		assertNotNull(response);
	}
	
	/**
	 * Method that test the repository that search for an User by the email.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@Test
	public void testFindByEmail(){
		Optional<User> response = repository.findByEmail(EMAIL);
		
		assertTrue(response.isPresent());
		assertEquals(EMAIL, response.get().getEmail());
	}
	
	/**
	 * Method to remove all User test data.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@AfterAll
	private void tearDown() {
		repository.deleteAll();
	}

}
