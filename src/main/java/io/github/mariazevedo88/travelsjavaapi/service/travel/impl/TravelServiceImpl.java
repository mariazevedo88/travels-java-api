package io.github.mariazevedo88.travelsjavaapi.service.travel.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.zero_x_baadf00d.partialize.Partialize;

import io.github.mariazevedo88.travelsjavaapi.dto.model.travel.TravelDTO;
import io.github.mariazevedo88.travelsjavaapi.exception.TravelNotFoundException;
import io.github.mariazevedo88.travelsjavaapi.model.travel.Travel;
import io.github.mariazevedo88.travelsjavaapi.repository.travel.TravelRepository;
import io.github.mariazevedo88.travelsjavaapi.service.travel.TravelService;

/**
 * Class that implements the travel's service methods
 * 
 * @author Mariana Azevedo
 * @since 28/03/2020
 */
@Service
public class TravelServiceImpl implements TravelService {
	
	TravelRepository travelRepository;
	
	@Autowired
	public TravelServiceImpl(TravelRepository travelRepository) {
		this.travelRepository = travelRepository;
	}

	/**
	 * @see TravelService#save(Travel)
	 */
	@Override
	public Travel save(Travel travel) {
		return travelRepository.save(travel);
	}

	/**
	 * @see TravelService#findByOrderNumber(String)
	 */
	@Override
	@Cacheable(value="travelOrderNumberCache", key="#orderNumber", unless="#result==null")
	public Optional<Travel> findByOrderNumber(String orderNumber) {
		return travelRepository.findByOrderNumber(orderNumber);
	}

	/**
	 * @see TravelService#deleteById(Long)
	 */
	@Override
	public void deleteById(Long travelId) {
		travelRepository.deleteById(travelId);		
	}

	/**
	 * @see TravelService#findById(Long)
	 * @throws TravelNotFoundException 
	 */
	@Override
	@Cacheable(value="travelIdCache", key="#id")
	public Travel findById(Long id) throws TravelNotFoundException {
		return travelRepository.findById(id).orElseThrow(() -> 
			new TravelNotFoundException("Travel id=" + id + " not found"));
	}

	/**
	 * @see TravelService#findBetweenDates(LocalDateTime, LocalDateTime, Pageable)
	 */
	@Override
	public Page<Travel> findBetweenDates(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
		return travelRepository.
				findAllByStartDateGreaterThanEqualAndStartDateLessThanEqual(startDate, 
					endDate, pageable);
	}

	/**
	 * @see TravelService#findAll()
	 */
	@Override
	public List<Travel> findAll() {
		return travelRepository.findAll();
	}

	/**
	 * @see TravelService#getPartialJsonResponse(String, TravelDTO)
	 */
	@Override
	public TravelDTO getPartialJsonResponse(String fields, TravelDTO dto) {
		
		final Partialize partialize = new Partialize();
		final ContainerNode<?> node = partialize.buildPartialObject(fields, TravelDTO.class, dto);
		return new ObjectMapper().convertValue(node, TravelDTO.class);
	}

}
