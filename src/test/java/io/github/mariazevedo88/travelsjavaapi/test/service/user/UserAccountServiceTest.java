package io.github.mariazevedo88.travelsjavaapi.test.service.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.mariazevedo88.travelsjavaapi.enumeration.AccountTypeEnum;
import io.github.mariazevedo88.travelsjavaapi.enumeration.RoleEnum;
import io.github.mariazevedo88.travelsjavaapi.model.account.Account;
import io.github.mariazevedo88.travelsjavaapi.model.user.User;
import io.github.mariazevedo88.travelsjavaapi.model.user.UserAccount;
import io.github.mariazevedo88.travelsjavaapi.repository.user.UserAccountRepository;
import io.github.mariazevedo88.travelsjavaapi.service.user.UserAccountService;

/**
 * Class that implements tests of the UserAccountService features.
 * 
 * @author Mariana Azevedo
 * @since 08/12/2020
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class UserAccountServiceTest {
	
	static final String EMAIL = "email@test.com";
	static final String ACCOUNT_NUMBER = "1234560";
	
	@Autowired
	private UserAccountService userAccountService;
	
	@MockBean
	private UserAccountRepository userAccRepository;
	
	/**
	 * Method to test the creation of an UserAccount.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/12/2020
	 */
	@Test
	@Order(1)
	public void testSave() {
		
		BDDMockito.given(userAccRepository.save(Mockito.any(UserAccount.class)))
			.willReturn(getMockUserAccount());
		UserAccount userAccount = userAccountService.save(new UserAccount());
		
		assertNotNull(userAccount);
	}
	
	/**
	 * Method that fill a mock of an Account to use as return in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/12/2020
	 * 
	 * @return <code>Account</code> object
	 */
	private Account getMockAccount() {
		return new Account(1L, ACCOUNT_NUMBER, AccountTypeEnum.BASIC);
	}
	
	/**
	 * Method that fill a mock of an User to use as return in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/12/2020
	 * 
	 * @return <code>User</code> object
	 */
	private User getMockUser() {
		return new User(1L, "Test User", "123", EMAIL, RoleEnum.ROLE_ADMIN);
	}
	
	/**
	 * Method that fill a mock of an UserAccount to use as return in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/12/2020
	 * 
	 * @return <code>UserAccount</code> object
	 */
	private UserAccount getMockUserAccount() {
		return new UserAccount(null, getMockUser(), getMockAccount());
	}

}
