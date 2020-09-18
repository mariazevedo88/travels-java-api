package io.github.mariazevedo88.financialjavaapi.test.controller.statistic;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

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

import io.github.mariazevedo88.financialjavaapi.model.statistic.Statistic;
import io.github.mariazevedo88.financialjavaapi.service.statistic.StatisticService;

/**
 * Class that implements tests of the StatisticController features
 * 
 * @author Mariana Azevedo
 * @since 05/04/2020
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class StatisticControllerTest {
	
	private static final Long ID = 1L;
	private static final BigDecimal SUM = BigDecimal.valueOf(500);
	private static final BigDecimal AVG = BigDecimal.valueOf(125);
	private static final BigDecimal MIN = BigDecimal.valueOf(50);
	private static final BigDecimal MAX = BigDecimal.valueOf(200);
	private static final long COUNT = 4L;
	private static final String URL = "/financial/v1/statistics";
	
	private HttpHeaders headers;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StatisticService service;
	
	@BeforeAll
	private void setUp() {
		headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
	}
	
	/**
	 * Method that tests to save a Statistic in the API.
	 * 
	 * @author Mariana Azevedo
	 * @since 05/04/2020
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSave() throws Exception {
		
		BDDMockito.given(service.save(Mockito.any(Statistic.class))).willReturn(getMockStatistic());
		
		final HttpHeaders headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
		
		mockMvc.perform(MockMvcRequestBuilders.get(URL).contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON).headers(headers)).andDo(MockMvcResultHandlers.print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.sum").value(SUM))
		.andExpect(jsonPath("$.data.avg").value(AVG))
		.andExpect(jsonPath("$.data.max").value(MAX))
		.andExpect(jsonPath("$.data.min").value(MIN))
		.andExpect(jsonPath("$.data.count").value(COUNT));
	}
	
	/**
	 * Method that fill a mock Statistic to use as return in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 05/04/2020
	 * 
	 * @return <code>Statistic</code> object
	 */
	private Statistic getMockStatistic() {
		return new Statistic(ID, SUM, AVG, MAX, MIN, COUNT);
	}

}
