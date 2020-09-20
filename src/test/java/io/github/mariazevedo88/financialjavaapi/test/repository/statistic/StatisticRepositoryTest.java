package io.github.mariazevedo88.financialjavaapi.test.repository.statistic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.mariazevedo88.financialjavaapi.model.statistic.Statistic;
import io.github.mariazevedo88.financialjavaapi.repository.statistic.StatisticRepository;

/**
 * Class that implements tests of the StatisticRepository features
 * 
 * @author Mariana Azevedo
 * @since 24/03/2020
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class StatisticRepositoryTest {
	
	@Autowired
	private StatisticRepository repository;
	
	/**
	 * Method that test the repository that save a Statistic in the API.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@Test
	public void testSave() {
		
		Statistic statistic = new Statistic(null, new BigDecimal(200d), new BigDecimal(100d),
				new BigDecimal(100d), new BigDecimal(100d), 2);
		
		Statistic response = repository.save(statistic);
		
		assertNotNull(response);
		
		assertEquals(statistic.getSum(), response.getSum());
		assertEquals(statistic.getMin(), response.getMin());
		assertEquals(statistic.getMax(), response.getMax());
		assertEquals(statistic.getAvg(), response.getAvg());
		assertEquals(statistic.getCount(), response.getCount());
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
