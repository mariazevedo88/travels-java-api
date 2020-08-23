package io.github.mariazevedo88.financialjavaapi.service.ratelimiting;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

/**
 * Interface that provides methods for manipulating API Usage Plans.
 * 
 * @author Mariana Azevedo
 * @since 11/06/2020
 */
public interface APIUsagePlansService {
	
	/**
	 * Method that links a bucket in an apiKey.
	 * 
	 * @author Mariana Azevedo
	 * @since 14/06/2020
	 * 
	 * @param apiKey
	 * @return <code>Bucket</code> object
	 */
	public Bucket resolveBucket(String apiKey);
	
	/**
	 * Method that creates a bucket for a specific apiKey.
	 * 
	 * @author Mariana Azevedo
	 * @since 14/06/2020
	 * 
	 * @param apiKey
	 * @return <code>Bucket</code> object
	 */
	public Bucket newBucket(String apiKey);
	
	/**
	 * Method that creates the new builder of in-memory buckets, 
	 * adding a limited bandwidth.
	 * 
	 * @author Mariana Azevedo
	 * @since 14/06/2020
	 * 
	 * @param limit
	 * @return <code>Bucket</code> object
	 */
	public Bucket bucket(Bandwidth limit);

}
