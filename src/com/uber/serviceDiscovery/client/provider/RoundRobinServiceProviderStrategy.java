package com.uber.serviceDiscovery.client.provider;

import com.uber.serviceDiscovery.api.ServiceInstance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinServiceProviderStrategy implements ServiceProviderStrategy {

    private final AtomicInteger index = new AtomicInteger(0);

    @Override
    public ServiceInstance getServiceInstance(List<ServiceInstance> services) {
        if (services.size() == 0)
            return null;
        int val = index.getAndIncrement();
        return services.get(val % services.size());
    }
}
