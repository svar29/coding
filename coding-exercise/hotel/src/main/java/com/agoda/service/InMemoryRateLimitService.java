package com.agoda.service;

import com.agoda.core.RateLimit;
import com.agoda.model.Rate;
import com.agoda.core.TokenBasedRateLimit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InMemoryRateLimitService implements RateLimitService{
    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryRateLimitService.class);

    private Rate globalRate;
    static final Long ONE_MINUTE_IN_MILLIS = 60000L;
    private Long suspendFor;
    private Map<String, RateLimit> tokenMap;
    private Map<String, Rate> rateMap;
    public InMemoryRateLimitService(Rate globalRate , Map<String, Rate> rateMap, Integer suspendFor) {
        this.globalRate = globalRate;
        this.tokenMap = new HashMap<>();
        this.rateMap = rateMap;
        this.suspendFor = suspendFor * ONE_MINUTE_IN_MILLIS;
    }

    private boolean isRateDefined(String apiKey){
        return rateMap.containsKey(apiKey);
    }

    private boolean isAlreadyRegistered(String apiKey){
        return tokenMap.containsKey(apiKey);
    }

    @Override
    synchronized public boolean validate(String apiKey) {
        Long currentTimeInMillis = new Date().getTime();
        return this.validate(apiKey, currentTimeInMillis);
    }
    // thread safety for multiple api hits with same key
    @Override
    synchronized public boolean validate(String apiKey, Long currentTimeInMillis){
        checkForRegistration(apiKey);
        //check if this api key is blocked for excess usage
        if (currentTimeInMillis >= tokenMap.get(apiKey).getBlockedTill()){
            LOGGER.info("Api Key: "+ apiKey + " is being processed..");
            if(tokenMap.get(apiKey).processMessage(currentTimeInMillis/1000)){
                return true;
            } else{
                Long blockedTill = currentTimeInMillis + suspendFor;
                LOGGER.info("Api Key limit exceeded: "+ apiKey + " is blocked till: " +blockedTill);
                tokenMap.get(apiKey).setBlockedTill(blockedTill);
                return false;
            }
        } else {
            LOGGER.info("Api Key: "+ apiKey + " is blocked till: " + tokenMap.get(apiKey).getBlockedTill());
            // Assumption: not setting BlockedTill to current time on hitting while it is already blocked
            return false;
        }
    }

    // this method checks if there is already RateLimit defined for this api key
    synchronized private void checkForRegistration(String apiKey) {
        //First time registration of api
        if (!isAlreadyRegistered(apiKey)){
            LOGGER.info("Registering api Key: "+ apiKey + " for first time");
            // if rate is not defined for this key, default to global
            RateLimit tokenBasedRateLimit;
            if (isRateDefined(apiKey)) {
                tokenBasedRateLimit = new TokenBasedRateLimit(rateMap.get(apiKey));
            } else {
                tokenBasedRateLimit = new TokenBasedRateLimit(globalRate);
            }
            tokenMap.put(apiKey, tokenBasedRateLimit);
        }
    }
}
