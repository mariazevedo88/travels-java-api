package io.github.mariazevedo88.financialjavaapi.util.config;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class that implements the necessary settings for the cache in the API.
 *  
 * @author Mariana Azevedo
 * @since 11/06/2020
 */
@EnableCaching
@Configuration
public class CacheConfiguration {

	/**
	 * Method that instantiates a cache manager based on ConcurrentMapCacheManager
	 * 
	 * @author Mariana Azevedo
	 * @since 11/06/2020
	 * 
	 * @return <code>CacheManagerCustomizer<ConcurrentMapCacheManager></code> object
	 */
	@Bean
	public CacheManagerCustomizer<ConcurrentMapCacheManager> cacheManagerCustomizer() {
		return new CacheManagerCustomizer<ConcurrentMapCacheManager>() {
			@Override
			public void customize(ConcurrentMapCacheManager cacheManager) {
				cacheManager.setAllowNullValues(true);
			}
		};
	}
}
