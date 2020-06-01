package io.github.mariazevedo88.financialjavaapi.service.ratelimiting;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

public interface APIUsagePlansService {
	
	public Bucket resolveBucket(String apiKey);
	
	public Bucket newBucket(String apiKey);
	
	public Bucket bucket(Bandwidth limit);

}
