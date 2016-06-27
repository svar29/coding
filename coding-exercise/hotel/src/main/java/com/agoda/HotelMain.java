package com.agoda;

import com.agoda.resource.HealthCheckResource;
import com.agoda.resource.HotelResource;
import com.agoda.service.HotelService;
import com.agoda.service.InMemoryRateLimitService;
import com.agoda.service.RateLimitService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HotelMain extends Application<HotelConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelMain.class);

    public static void main(String[] args) throws Exception {
        new HotelMain().run(args);
    }

    @Override
    public String getName() {
        return "Hotel App";
    }

    @Override
    public void initialize(Bootstrap<HotelConfiguration> bootstrap) {
    }

    @Override
    public void run(HotelConfiguration configuration, Environment environment) throws NoSuchFieldException, IllegalAccessException, IOException {

        environment.jersey().register(new HealthCheckResource());
        LOGGER.info("Loading data from: " + configuration.getHoteldb());
        HotelService hotelService = new HotelService(configuration.getHoteldb());
        RateLimitService inMemoryRateLimitService = new InMemoryRateLimitService(configuration.getGlobalRateLimit(), configuration.getRateLimitMap(), configuration.getSuspendTimeInMinutes());
        environment.jersey().register(new HotelResource(hotelService, inMemoryRateLimitService));

    }
}
