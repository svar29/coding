package com.uber.serviceDiscovery.services;

import com.uber.serviceDiscovery.api.HealthCheck;
import com.uber.serviceDiscovery.api.ServiceInstance;
import com.uber.serviceDiscovery.client.provider.ServiceProvider;

public class ServiceA extends ServiceInstance implements HealthCheck {

    private final HealthCheck healthCheck;

    public ServiceA(String id, String host, int port, HealthCheck healthCheck) {
        super(id, host, port);
        this.healthCheck = healthCheck;
    }

    @Override
    public boolean isHealthy() {
        return healthCheck.isHealthy();
    }

    public ServiceInstance getServiceBHost() {
        ServiceInstance service = ServiceProvider.getInstance(getHost()).getService(ServiceB.class.getName());
        System.out.printf("Node %s at %s:%d identifies ServiceB at %s\n", this.getServiceId(), getHost(), getPort(), service);
        return service;
    }

    @Override
    public HealthCheck getHealthCheck() {
        return this;
    }
}
