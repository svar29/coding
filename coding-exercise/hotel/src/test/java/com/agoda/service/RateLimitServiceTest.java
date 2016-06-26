package com.agoda.service;

import com.agoda.model.Rate;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RateLimitServiceTest {
    private Rate globalRate;
    private Map<String, Rate> rateMap;

    @Before
    public void setUp() throws Exception {
        this.globalRate = new Rate(10.0, 10.0);
        this.rateMap = new HashMap<>();
        this.rateMap.put("agoda", new Rate(5.0, 10.0));
    }

    @Test
    public void validate() throws Exception {
        Long currentTimeInMillis = new Date().getTime();

        RateLimitService rateLimitService = new RateLimitService(this.globalRate, this.rateMap, 5);
        Boolean response = false;
        response = rateLimitService.validate("agoda", currentTimeInMillis);
        assertEquals(true, response);
        response = rateLimitService.validate("agoda", currentTimeInMillis);
        assertEquals(true, response);
        response = rateLimitService.validate("agoda", currentTimeInMillis);
        assertEquals(true, response);
        response = rateLimitService.validate("agoda", currentTimeInMillis);
        assertEquals(true, response);
        response = rateLimitService.validate("agoda", currentTimeInMillis);
        assertEquals(true, response);
        // quota must have exceeded by now
        response = rateLimitService.validate("agoda", currentTimeInMillis);
        assertEquals(false, response);
        // even after 10 seconds it will fail for this api key as it is suspended for 5 minutes
        response = rateLimitService.validate("agoda", currentTimeInMillis + 10000);
        assertEquals(false, response);
        // after 5 minutes this will again start accepting new requests
        response = rateLimitService.validate("agoda", currentTimeInMillis + rateLimitService.getSuspendFor());
        assertEquals(true, response);

    }

}