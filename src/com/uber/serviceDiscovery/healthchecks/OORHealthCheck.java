package com.uber.serviceDiscovery.healthchecks;

import com.uber.serviceDiscovery.api.HealthCheck;

public class OORHealthCheck implements HealthCheck {

    private boolean oorStatus;

    public OORHealthCheck() {
        oorStatus = true;
    }

    @Override
    public boolean isHealthy() {
        return !oorStatus;
    }

    public void makeOOR() {
        oorStatus = true;
    }

    public void makeBIR() {
        oorStatus = false;
    }
}
