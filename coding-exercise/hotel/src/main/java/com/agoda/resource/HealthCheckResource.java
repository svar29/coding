package com.agoda.resource;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/healthcheck")
@Produces(MediaType.APPLICATION_JSON)
public class HealthCheckResource {

    @GET
    @Timed(name = "healthcheck")
    public String sayHello() {
        return "hello";
    }

}
