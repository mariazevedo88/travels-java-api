package io.github.mariazevedo88.financialjavaapi.service.ratelimiting.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.mariazevedo88.financialjavaapi.model.enumeration.APIUsagePlansEnum;
import io.github.mariazevedo88.financialjavaapi.service.ratelimiting.APIUsagePlansService;

@Service
public class APIUsagePlansServiceImpl implements APIUsagePlansService {
	
	private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

	@Override
	public Bucket resolveBucket(String apiKey) {
		return cache.computeIfAbsent(apiKey, this::newBucket);
	}

	@Override
	public Bucket newBucket(String apiKey) {
		APIUsagePlansEnum pricingPlan = APIUsagePlansEnum.resolvePlanFromApiKey(apiKey);
		return bucket(pricingPlan.getLimit());
	}

	@Override
	public Bucket bucket(Bandwidth limit) {
		return Bucket4j.builder().addLimit(limit).build();
	}

}
