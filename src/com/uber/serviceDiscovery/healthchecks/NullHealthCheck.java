package com.uber.serviceDiscovery.healthchecks;

import com.uber.serviceDiscovery.api.HealthCheck;

public class NullHealthCheck implements HealthCheck {

    @Override
    public boolean isHealthy() {
        return true;
    }
}
