package io.github.mariazevedo88.financialjavaapi.test.controller.transaction;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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

/**
 * Class that implements tests of the TransactionController funcionalities
 * 
 * @author Mariana Azevedo
 * @since 05/04/2020
 */
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class TransactionControllerTest {
	
	private static final Long ID = 1L;
	private static final String NSU = "654789";
	private static final String AUTH = "010203";
	private static final Date TRANSACTION_DATE = new Date();
	private static final TransactionTypeEnum TYPE = TransactionTypeEnum.CARD;
	private static final BigDecimal VALUE = BigDecimal.valueOf(288);
	private static final String URL = "/financial/v1/transactions";
	
	private HttpHeaders headers;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransactionService service;
	
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
	public void testSave() throws Exception {
		
		BDDMockito.given(service.save(Mockito.any(Transaction.class))).willReturn(getMockTransaction());
		
		mockMvc.perform(MockMvcRequestBuilders.post(URL)
			.content(getJsonPayload(ID, NSU, AUTH, TRANSACTION_DATE, VALUE, TYPE))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.headers(headers))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.nsu").value(NSU))
		.andExpect(jsonPath("$.data.authorizationNumber").value(AUTH))
		.andExpect(jsonPath("$.data.transactionDate").value(getDateTimeFormater(TRANSACTION_DATE)))
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
	public void testSaveInvalidTransaction() throws Exception {
		
		BDDMockito.given(service.save(Mockito.any(Transaction.class))).willReturn(getMockTransaction());
		
		mockMvc.perform(MockMvcRequestBuilders.post(URL)
				.content(getJsonPayload(ID, null, AUTH, TRANSACTION_DATE, VALUE, TYPE))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
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
	 */
	private Transaction getMockTransaction() {
		
		Transaction transaction = new Transaction(ID, NSU, AUTH,
				TRANSACTION_DATE, VALUE, TYPE);
		
		return transaction;
	}
	
	/**
	 * Method that fill a mock TransactionDTO to use as return in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 05/04/2020
	 * 
	 * @param id
	 * @param name
	 * @param email
	 * @param password
	 * @return <code>String</code> with the TrnsactionDTO payload
	 * 
	 * @throws JsonProcessingException
	 */
	private String getJsonPayload(Long id, String nsu, String authorization, Date transactionDate,
			BigDecimal amount, TransactionTypeEnum type) throws JsonProcessingException {
		
		TransactionDTO dto = new TransactionDTO();
		dto.setId(id);
		dto.setNsu(nsu);
		dto.setAuthorizationNumber(authorization);
		dto.setTransactionDate(transactionDate);
        dto.setAmount(amount);
        dto.setType(type);
	        
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return mapper.writeValueAsString(dto);
	}
	
	/**
	 * Method that format a date as yyyy-MM-ddTHH:mm:ss.SSSZ.
	 * 
	 * @author Mariana Azevedo
	 * @since 05/04/2020
	 * 
	 * @return <code>String</code> object
	 */
	private String getDateTimeFormater(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		return formatter.format(date);
	}

}
