package io.github.mariazevedo88.financialjavaapi.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.mariazevedo88.financialjavaapi.model.Transaction;
import io.github.mariazevedo88.financialjavaapi.model.Transaction.TransactionTypeEnum;
import io.github.mariazevedo88.financialjavaapi.service.TransactionService;

/**
 * Class that implements API integration tests
 * 
 * @author Mariana Azevedo
 * @since 10/09/2019
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties
@AutoConfigureMockMvc
public class FinancialJavaApiIT {
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
    private MockMvc mockMvc;

	@Before
	public void getContext() {
		
		transactionService.createFactory();
		transactionService.createTransactionList();
		assertNotNull(transactionService);
		
		assertNotNull(mockMvc);
	}
	
	@Test
	public void shouldReturnCreateATransaction() throws Exception {

		JSONObject map = setObjectToCreate();
		
		this.mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(map))).andExpect(status().isCreated());
	}
	
	@Test
	public void shouldReturnUpdateATransaction() throws Exception {
		
		Transaction transaction = transactionService.findById(1);
		JSONObject map = setObjectToUpdate();
		
		this.mockMvc.perform(put("/transactions/" + transaction.getId()).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(map))).andExpect(status().isOk());
	}

	@Test
    public void shouldReturnGetAllTransactions() throws Exception {
		this.mockMvc.perform(get("/transactions")).andExpect(status().isOk());
    }
	
	@Test
    public void shouldReturnRemoveAllTransactions() throws Exception {
		this.mockMvc.perform(delete("/transactions")).andExpect(status().isNoContent());
    }
	
	@SuppressWarnings("unchecked")
	private JSONObject setObjectToCreate() {
		
		String localDate = LocalDateTime.now().toString().concat("Z");
		
		JSONObject map = new JSONObject();
		map.put("id", 1L);
		map.put("nsu", "220788");
		map.put("autorizationNumber", "010203");
		map.put("amount", "22.88");
		map.put("transactionDate", localDate);
		map.put("type", TransactionTypeEnum.CARD);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject setObjectToUpdate() {
		
		String localDate = LocalDateTime.now().toString().concat("Z");
		
		JSONObject map = new JSONObject();
		map.put("id", 1L);
		map.put("nsu", "220788");
		map.put("autorizationNumber", "000000");
		map.put("amount", "22.88");
		map.put("transactionDate", localDate);
		map.put("type", TransactionTypeEnum.CARD);
		
		return map;
	}

}
