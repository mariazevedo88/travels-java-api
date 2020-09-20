package io.github.mariazevedo88.financialjavaapi.test.controller.transaction;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
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
import com.fasterxml.jackson.databind.SerializationFeature;

import io.github.mariazevedo88.financialjavaapi.dto.model.transaction.TransactionDTO;
import io.github.mariazevedo88.financialjavaapi.model.enumeration.TransactionTypeEnum;
import io.github.mariazevedo88.financialjavaapi.model.transaction.Transaction;
import io.github.mariazevedo88.financialjavaapi.service.transaction.TransactionService;
import io.github.mariazevedo88.financialjavaapi.util.FinancialApiUtil;

/**
 * Class that implements tests of the TransactionController features
 * 
 * @author Mariana Azevedo
 * @since 05/04/2020
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class TransactionControllerTest {
	
	private static final Long ID = 1L;
	private static final String NSU = "654789";
	private static final String AUTH = "010203";
	private static final String TRANSACTION_DATE = "2020-08-21T18:32:04.150";
	private static final TransactionTypeEnum TYPE = TransactionTypeEnum.CARD;
	private static final BigDecimal VALUE = BigDecimal.valueOf(288);
	private static final String URL = "/financial/v1/transactions";
	
	private HttpHeaders headers;

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	TransactionService transactionService;
	
	@BeforeAll
	private void setUp() {
		headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
	}	
	/**
	 * Method that tests to save an Transaction in the API
	 * 
	 * @author Mariana Azevedo
	 * @since 05/04/2020
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(1)
	public void testSave() throws Exception {
		
		BDDMockito.given(transactionService.save(Mockito.any(Transaction.class))).willReturn(getMockTransaction());
		
		mockMvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, NSU, AUTH, 
			FinancialApiUtil.getLocalDateTimeFromString(TRANSACTION_DATE.concat("Z")), VALUE, TYPE))
			.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
			.headers(headers))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.nsu").value(NSU))
		.andExpect(jsonPath("$.data.authorizationNumber").value(AUTH))
		.andExpect(jsonPath("$.data.transactionDate").value(TRANSACTION_DATE))
		.andExpect(jsonPath("$.data.amount").value(VALUE))
		.andExpect(jsonPath("$.data.type").value(TYPE.toString()));
	}
	
	/**
	 * Method that try to save an invalid Transaction in the API
	 * 
	 * @author Mariana Azevedo
	 * @since 05/04/2020
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(2)
	public void testSaveInvalidTransaction() throws Exception {
		
		BDDMockito.given(transactionService.save(Mockito.any(Transaction.class))).willReturn(getMockTransaction());
		
		mockMvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, null, AUTH, FinancialApiUtil.
				 getLocalDateTimeFromString(TRANSACTION_DATE.concat("Z")), VALUE, TYPE))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.headers(headers))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.details").value("Nsu cannot be null"));
	}
	
	/**
	 * Method that fill a mock Transaction to use as return in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 05/04/2020
	 * 
	 * @return <code>Transaction</code> object
	 * @throws ParseException 
	 */
	private Transaction getMockTransaction() throws ParseException {
		
		Transaction transaction = new Transaction(ID, NSU, AUTH, FinancialApiUtil.getLocalDateTimeFromString
				(TRANSACTION_DATE.concat("Z")), VALUE, TYPE);
		return transaction;
	}
	
	/**
	 * Method that fill a mock TransactionDTO to use as return in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 05/04/2020
	 * 
	 * @param id
	 * @param nsu
	 * @param authorization
	 * @param transactionDate
	 * @param amount
	 * @param type
	 * @return <code>String</code> with the TransactionDTO payload
	 * 
	 * @throws JsonProcessingException
	 */
	private String getJsonPayload(Long id, String nsu, String authorization, LocalDateTime transactionDate,
			BigDecimal amount, TransactionTypeEnum type) throws JsonProcessingException {
		
		TransactionDTO dto = new TransactionDTO(id, nsu, authorization, transactionDate, amount, type);
	        
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return mapper.writeValueAsString(dto);
	}

}
