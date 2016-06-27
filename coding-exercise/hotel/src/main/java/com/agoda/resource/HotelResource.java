package com.agoda.resource;

import com.agoda.model.ORDER;
import com.agoda.response.RateLimitExceededResponse;
import com.agoda.service.HotelService;
import com.agoda.service.InMemoryRateLimitService;
import com.agoda.service.RateLimitService;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/")
public class HotelResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelResource.class);

    private HotelService hotelService;
    private RateLimitService inMemoryRateLimitService;
    public HotelResource(HotelService hotelService, RateLimitService inMemoryRateLimitService) {
        this.hotelService = hotelService;
        this.inMemoryRateLimitService = inMemoryRateLimitService;
    }

    /*
    GET /hotels?city=Bangkok&key=agoda
    GET /hotels?city=Bangkok&order=DESC&key=agoda
    GET /hotels?city=Bangkok&order=ASC&key=agoda
    GET /hotels?city=Bangkok&key=varun
     */

    @GET
    @Timed(name = "getHotelsByCity")
    @Produces("application/json")
    @Path("/hotels")
    public Response getShipmentsView(@QueryParam("city") String city,
                                     @QueryParam("order") @Valid ORDER ORDER,
                                     @QueryParam("key") @NotNull String key) throws IOException {
        /*
        //If we want to block unauthorised access
        if(!rateLimitService.isRateDefined(key)){
            LOGGER.warn("Unauthorised access from key: " + key);
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        */
        if (!inMemoryRateLimitService.validate(key)) {
            LOGGER.info("RateLimit crossed for key: " + key);
            return Response.status(429).entity(new RateLimitExceededResponse()).build();
        }
        return Response.ok(hotelService.getHotelsByCity(city, ORDER)).build();
    }

}
