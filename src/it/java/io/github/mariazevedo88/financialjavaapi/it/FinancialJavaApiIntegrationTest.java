package io.github.mariazevedo88.financialjavaapi.it;

import static com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.support.RateLimitConstants.HEADER_LIMIT;
import static com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.support.RateLimitConstants.HEADER_REMAINING;
import static com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.support.RateLimitConstants.HEADER_RESET;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import io.github.mariazevedo88.financialjavaapi.dto.model.transaction.TransactionDTO;
import io.github.mariazevedo88.financialjavaapi.model.enumeration.TransactionTypeEnum;

/**
 * Class that implements API integration tests.
 * 
 * @author Mariana Azevedo
 * @since 10/09/2019
 */
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FinancialJavaApiIntegrationTest {
	
	@LocalServerPort
	private int port;
	 
    @Autowired
    private TestRestTemplate restTemplate;
	 
    @Test
    @Order(1)
    public void testCreateTransactionNSU123456() {
    	
    	//id=1
        TransactionDTO dtoNsu123456 = new TransactionDTO(); 
        dtoNsu123456.setNsu("123456");
        dtoNsu123456.setAuthorizationNumber("014785");
        dtoNsu123456.setTransactionDate(new Date());
        dtoNsu123456.setAmount(new BigDecimal(100d));
        dtoNsu123456.setType(TransactionTypeEnum.CARD);
        
        ResponseEntity<String> responseEntity = this.restTemplate
            .postForEntity("http://localhost:" + port + "/financial/v1/transactions", dtoNsu123456, String.class);
        
        assertEquals(201, responseEntity.getStatusCodeValue());
    }
    
    @Test
    @Order(2)
    public void testCreateTransactionNSU258963() {
    	
    	//id=2
    	TransactionDTO dtoNsu258963 = new TransactionDTO(); 
        dtoNsu258963.setNsu("258963");
        dtoNsu258963.setTransactionDate(new Date());
        dtoNsu258963.setAmount(new BigDecimal(2546.93));
        dtoNsu258963.setType(TransactionTypeEnum.MONEY);
        
        ResponseEntity<String> responseEntity = this.restTemplate
            .postForEntity("http://localhost:" + port + "/financial/v1/transactions", dtoNsu258963, String.class);
            
        assertEquals(201, responseEntity.getStatusCodeValue());
    }
    
    @Test
    @Order(3)
    public void testFindAllTransactions() {
    	
    	ResponseEntity<String> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/financial/v1/transactions", String.class);
                
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
    
    @Test
    @Order(4)
    public void testFindTransactionById() {
    	
    	ResponseEntity<String> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/financial/v1/transactions/1", String.class);
                
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
    
    @Test
    @Order(5)
    public void testFindTransactionByIdThatNotExists() {
    	
    	ResponseEntity<String> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/financial/v1/transactions/3", String.class);
        
    	//Transaction not found
        assertEquals(404, responseEntity.getStatusCodeValue()); 
    }
    
    @Test
    @Order(6)
    public void testFindTransactionByNsu() {
    	
    	ResponseEntity<String> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/financial/v1/transactions/byNsu/258963", String.class);
        
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
    
    @Test
    @Order(7)
    public void testCreateStatistics() {
    	
    	ResponseEntity<String> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/financial/v1/statistics", String.class);
        
        assertEquals(201, responseEntity.getStatusCodeValue());
    }
    
    @Test
    @Order(8)
	public void testRequestNotExceedingRateLimitCapacity() {
	    ResponseEntity<String> response = this.restTemplate
	    		.getForEntity("http://localhost:" + port + "/financial/v1/transactions/1", String.class);
	    assertEquals(200, response.getStatusCodeValue());
	 
	    HttpHeaders headers = response.getHeaders();
	    String key = "financial-java-api__transactions__127.0.0.1";
	 
	    assertEquals("10", headers.getFirst(HEADER_LIMIT + key));
	    assertEquals("9", headers.getFirst(HEADER_REMAINING + key));
	    assertEquals("60000", headers.getFirst(HEADER_RESET + key));    
	}

}
