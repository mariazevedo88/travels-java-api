package io.github.mariazevedo88.financialjavaapi.model.enumeration;

import java.time.Duration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;

/**
 * Enum that classifies the usage plans in the API.
 * 
 * @author Mariana Azevedo
 * @since 14/06/2020
 */
public enum APIUsagePlansEnum {
	
	FREE(20),
    BASIC(40),
    PROFESSIONAL(100);

    private int bucketCapacity;
    
    private APIUsagePlansEnum (int bucketCapacity) {
        this.bucketCapacity = bucketCapacity;
    }
    
    /**
     * Method to get the Bandwidth, that is composed by the bucket capacity and the refill interval.
     * 
     * @author Mariana Azevedo
     * @since 11/06/2020
     * 
     * @return <code>Bandwidth</code> object
     */
    public Bandwidth getLimit() {
        return Bandwidth.classic(bucketCapacity, Refill.intervally(bucketCapacity, Duration.ofMinutes(20)));
    }
    
    /**
     * Method to get the bucket capacity.
     * 
     * @author Mariana Azevedo
     * @since 11/06/2020
     * 
     * @return int
     */
    public int getBucketCapacity() {
        return bucketCapacity;
    }
    
    /**
     * Method to get the right plan by looking the apiKey prefix.
     * 
     * @author Mariana Azevedo
     * @since 14/06/2020
     * 
     * @param apiKey
     * @return <code>APIUsagePlansEnum</code>
     */
    public static APIUsagePlansEnum resolvePlanFromApiKey(String apiKey) {
        
    	if (apiKey == null || apiKey.isEmpty()) {
            return FREE;
        
        } else if (apiKey.startsWith("PX001-")) {
            return PROFESSIONAL;
            
        } else if (apiKey.startsWith("BX001-")) {
            return BASIC;
        }
        return FREE;
    }

}
