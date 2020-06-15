package io.github.mariazevedo88.financialjavaapi.util;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Class that implements an event logger to show cache creation and expiration
 * in the API.
 *  
 * @author Mariana Azevedo
 * @since 11/06/2020
 */
@Component
public class CacheEventLogger implements CacheEventListener<Object, Object> {
 
	private static final Logger logger = LoggerFactory.getLogger(CacheEventLogger.class);
	
	/**
	 * @see CacheEventListener#onEvent(CacheEvent)
	 */
    @Override
    public void onEvent(CacheEvent<? extends Object, ? extends Object> cacheEvent) {
    	logger.info("Cache event {} for item with key {}. Old value = {}, New value = {}", cacheEvent.getType(), 
    			cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
    }

}
