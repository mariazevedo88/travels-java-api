package io.github.mariazevedo88.travelsjavaapi.it;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.mariazevedo88.travelsjavaapi.dto.model.account.AccountDTO;
import io.github.mariazevedo88.travelsjavaapi.dto.model.security.JwtUserDTO;
import io.github.mariazevedo88.travelsjavaapi.dto.model.travel.TravelDTO;
import io.github.mariazevedo88.travelsjavaapi.dto.model.user.UserDTO;
import io.github.mariazevedo88.travelsjavaapi.enumeration.APIUsagePlansEnum;
import io.github.mariazevedo88.travelsjavaapi.enumeration.AccountTypeEnum;
import io.github.mariazevedo88.travelsjavaapi.enumeration.RoleEnum;
import io.github.mariazevedo88.travelsjavaapi.enumeration.TravelTypeEnum;
import io.github.mariazevedo88.travelsjavaapi.util.TravelsApiUtil;

/**
 * Class that implements API integration tests.
 * 
 * @author Mariana Azevedo
 * @since 10/09/2019
 */
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TravelsJavaApiIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	private String token;
	 
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void testCreateUser() {
    	
    	UserDTO userDto = new UserDTO(99L, "Admin", "123456", "admin@financial.com", 
    			RoleEnum.ROLE_ADMIN.getValue());
    	
    	final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
        
        //Create a new HttpEntity
        final HttpEntity<UserDTO> entity = new HttpEntity<>(userDto, headers);
        
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" 
        		+ port + "/api-travels/v1/users", HttpMethod.POST, entity, String.class);
        
        assertEquals(201, responseEntity.getStatusCodeValue());
    }
    
    @Test
    @Order(2)
    public void testAuthentication() throws JsonMappingException, JsonProcessingException {
    	
    	JwtUserDTO userDto = new JwtUserDTO("admin@financial.com", "123456");
    	
    	final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
        
        //Create a new HttpEntity
        final HttpEntity<JwtUserDTO> entity = new HttpEntity<>(userDto, headers);
        
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" 
        		+ port + "/api-travels/v1/auth", HttpMethod.POST, entity, String.class);
        
        String body = responseEntity.getBody();
        JsonNode json = new ObjectMapper().readTree(body);
        token = json.get("data").get("token").textValue();
        
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(token);
    }
    
    @Test
    @Order(3)
    public void testCreateAccount() throws ParseException {
    	
    	//id=1
        AccountDTO accountDto = new AccountDTO(1L, "000012", AccountTypeEnum.BASIC.name()); 
        
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
        
        //Create a new HttpEntity
        final HttpEntity<AccountDTO> entity = new HttpEntity<>(accountDto, headers);
        
		ResponseEntity<AccountDTO> responseEntity = this.restTemplate.exchange("http://localhost:" 
        		+ port + "/api-travels/v1/accounts", HttpMethod.POST, entity, new ParameterizedTypeReference<AccountDTO>(){});
        
        assertEquals(201, responseEntity.getStatusCodeValue());
    }
    
    @Test
    @Order(4)
    public void testCreateTravelOrderNumber123456() throws ParseException {
    	
    	//id=1
        TravelDTO dtoNsu123456 = new TravelDTO(null, "123456", 
        		TravelsApiUtil.getLocalDateTimeFromString("2020-08-15T18:32:04.150Z"), 
        		TravelsApiUtil.getLocalDateTimeFromString("2020-08-21T18:32:04.150Z"), 
        		new BigDecimal(100d), TravelTypeEnum.RETURN.getValue(), 1L); 
        
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
        
        //Create a new HttpEntity
        final HttpEntity<TravelDTO> entity = new HttpEntity<>(dtoNsu123456, headers);
        
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + port 
        		+ "/api-travels/v1/travels", HttpMethod.POST, entity, String.class);
        
        assertEquals(201, responseEntity.getStatusCodeValue());
    }
    
    @Test
    @Order(5)
    public void testCreateTravelOrderNumber258963() throws ParseException {
    	
    	//id=2
    	TravelDTO dtoNsu258963 = new TravelDTO(null, "258963", 
    			TravelsApiUtil.getLocalDateTimeFromString("2020-08-21T18:32:04.150Z"),
    			null, new BigDecimal(2546.93), TravelTypeEnum.ONE_WAY.getValue(), 1L); 
        
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
        
        //Create a new HttpEntity
        final HttpEntity<TravelDTO> entity = new HttpEntity<>(dtoNsu258963, headers);
        
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" 
        		+ port + "/api-travels/v1/travels", HttpMethod.POST, entity, String.class);
        
        assertEquals(201, responseEntity.getStatusCodeValue());
    }
    
    @Test
    @Order(6)
    public void testFindAllTravels() throws ParseException {
    	
    	final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
        
        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<>(headers);
        
        String startDate = LocalDate.of(2020, 8, 20).toString();
		String endDate = LocalDate.of(2020, 8, 30).toString();

		ResponseEntity<String> responseEntity = this.restTemplate
        		.exchange("http://localhost:" + port + "/api-travels/v1/travels?startDate=" + startDate 
        				+ "&endDate=" + endDate + "&page=" + 1 + "&size=" + 2 + "&order=ASC", HttpMethod.GET, 
        				entity, String.class);
    	
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
    
    @Test
    @Order(7)
    public void testFindTravelById() {
    	
    	final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
        
        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<String> responseEntity = this.restTemplate
        		.exchange("http://localhost:" + port + "/api-travels/v1/travels/1", 
        				HttpMethod.GET, entity, String.class);
    	
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
    
    @Test
    @Order(8)
    public void testFindTravelByIdThatNotExists() {
    	
    	final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
        
        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<String> responseEntity = this.restTemplate
        		.exchange("http://localhost:" + port + "/api-travels/v1/travels/3", 
        				HttpMethod.GET, entity, String.class);
    	
    	//Transaction not found
        assertEquals(404, responseEntity.getStatusCodeValue()); 
    }
    
    @Test
    @Order(9)
    public void testFindTravelByOrderNumber() {
    	
    	final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
        
        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<String> responseEntity = this.restTemplate
        		.exchange("http://localhost:" + port 
        		+ "/api-travels/v1/travels/byOrderNumber/258963", 
        		HttpMethod.GET, entity, String.class);
    	
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
    
    @Test
    @Order(10)
    public void testCreateStatistics() {
    	
    	final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
        
        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<>(headers);
    	
    	ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + port 
        		+ "/api-travels/v1/statistics", HttpMethod.GET, entity, String.class);
        
        assertEquals(201, responseEntity.getStatusCodeValue());
    }
    
    @Test
    @Order(11)
	public void testRequestExceedingRateLimitCapacity() throws Exception {
	    
	    final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");

        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<>(headers);

        for (int i = 1; i <= APIUsagePlansEnum.FREE.getBucketCapacity(); i++) {
        	this.restTemplate.exchange("http://localhost:" + port + "/api-travels/v1/travels/1", 
        			HttpMethod.GET, entity, String.class);
        }
        
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + port 
        		+ "/api-travels/v1/travels/1", HttpMethod.GET, entity, String.class);
        
    	//Too many requests
        assertEquals(429, responseEntity.getStatusCodeValue());
	}

}
