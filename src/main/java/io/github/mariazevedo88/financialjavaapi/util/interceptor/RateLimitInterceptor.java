package io.github.mariazevedo88.financialjavaapi.util.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.mariazevedo88.financialjavaapi.service.ratelimiting.APIUsagePlansService;
import io.github.mariazevedo88.financialjavaapi.util.FinancialApiUtil;
import lombok.extern.log4j.Log4j2;

/**
 * Class that implements a interceptor of rate limiting in the API
 * 
 * @author Mariana Azevedo
 * @since 11/06/2020
 */
@Log4j2
@Component
public class RateLimitInterceptor implements HandlerInterceptor {
	
    private APIUsagePlansService pricingPlanService;
    
    @Autowired
    public RateLimitInterceptor(APIUsagePlansService pricingPlanService) {
    	this.pricingPlanService = pricingPlanService;
	}
 
    /**
     * @see HandlerInterceptor#preHandle(HttpServletRequest, HttpServletResponse, Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
    	String apiKey = request.getHeader(FinancialApiUtil.HEADER_API_KEY);
    	
        if (apiKey == null || apiKey.isEmpty()) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Missing Header: " + FinancialApiUtil.HEADER_API_KEY);

            log.error("Missing Header: " + FinancialApiUtil.HEADER_API_KEY);
            return false;
        }

        Bucket tokenBucket = pricingPlanService.resolveBucket(apiKey);
        ConsumptionProbe probe = tokenBucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {

            response.addHeader(FinancialApiUtil.HEADER_LIMIT_REMAINING, String.valueOf(probe.getRemainingTokens()));
            return true;

        } else {

            long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.addHeader(FinancialApiUtil.HEADER_RETRY_AFTER, String.valueOf(waitForRefill));
            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "You have exhausted your API Request Quota"); // 429
            
            log.info("You have exhausted your API Request Quota");

            return false;
        }
    }
    
}