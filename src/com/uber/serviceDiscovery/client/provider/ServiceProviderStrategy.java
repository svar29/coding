package com.uber.serviceDiscovery.client.provider;

import com.uber.serviceDiscovery.api.ServiceInstance;

import java.util.List;

/*
    Load balancing strategy for a set of service instances. Could be round-robin/random, etc.
 */
public interface ServiceProviderStrategy {

    public ServiceInstance getServiceInstance(List<ServiceInstance> services);
}
