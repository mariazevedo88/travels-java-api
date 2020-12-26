package io.github.mariazevedo88.financialjavaapi.test.controller.account;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.mariazevedo88.financialjavaapi.dto.model.account.AccountDTO;
import io.github.mariazevedo88.financialjavaapi.enumeration.AccountTypeEnum;
import io.github.mariazevedo88.financialjavaapi.model.account.Account;
import io.github.mariazevedo88.financialjavaapi.service.account.AccountService;

/**
 * Class that implements tests of the AccountController features
 * 
 * @author Mariana Azevedo
 * @since 10/12/2020
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class AccountControllerTest {
	
	static final String URL = "/financial/v1/accounts";
	static final String ACCOUNT_NUMBER = "985552";
	static final Long ACCOUNT_ID = 1L;

	HttpHeaders headers;

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	AccountService accountService;
	
	@BeforeAll
	private void setUp() {
		headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
	}
	
	/**
	 * Method that tests to save an Account in the API
	 * 
	 * @author Mariana Azevedo
	 * @since 10/12/2020
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(1)
	public void testSave() throws Exception {
		
		BDDMockito.given(accountService.save(Mockito.any(Account.class))).willReturn(getMockAccount());
		
		mockMvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ACCOUNT_ID, ACCOUNT_NUMBER,
				AccountTypeEnum.CHECKING_ACCOUNT.getValue()))
			.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
			.headers(headers))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.id").value(ACCOUNT_ID))
		.andExpect(jsonPath("$.data.accountNumber").value(ACCOUNT_NUMBER))
		.andExpect(jsonPath("$.data.accountType").value(AccountTypeEnum.CHECKING_ACCOUNT.getValue()));
	}
	
	/**
	 * Method that fill a mock Account to use as return in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 10/12/2020
	 * 
	 * @return <code>Account</code> object
	 * @throws ParseException 
	 */
	private Account getMockAccount() throws ParseException {
		return new Account(ACCOUNT_ID, ACCOUNT_NUMBER, AccountTypeEnum.CHECKING_ACCOUNT);
	}
	
	/**
	 * Method that fill a mock AccountDTO to use as return in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 10/12/2020
	 * 
	 * @param id
	 * @param accountNumber
	 * @param accountType
	 * @return <code>String</code> with the AccountDTO payload
	 * 
	 * @throws JsonProcessingException
	 */
	private String getJsonPayload(Long id, String accountNumber, String accountType) 
			throws JsonProcessingException {
		
		AccountDTO dto = new AccountDTO(id, accountNumber, accountType);
		return new ObjectMapper().writeValueAsString(dto);
	}
	
}
