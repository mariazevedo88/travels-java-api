package io.github.mariazevedo88.travelsjavaapi.repository.travel;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.mariazevedo88.travelsjavaapi.model.travel.Travel;

/**
 * Interface that implements the Travel Repository, with JPA CRUD methods
 * and other customized searches.
 *  
 * @author Mariana Azevedo
 * @since 28/03/2020
 */
@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {

	/**
	 * Method to search for all the travels in the API in a period of time.
	 * 
	 * @author Mariana Azevedo
	 * @since 21/08/2020
	 * 
	 * @return <code>Page<Travel></code> object
	 */
	Page<Travel> findAllByStartDateGreaterThanEqualAndStartDateLessThanEqual
		(LocalDateTime startDate, LocalDateTime endDate, Pageable pg);
	
	/**
	 * Method to search for all the travel in the same order number (unique number).
	 * 
	 * @author Mariana Azevedo
	 * @since 28/03/2020
	 * 
	 * @return <code>Optional<Travel></code> object
	 */
	Optional<Travel> findByOrderNumber(String orderNumber);
	
}
