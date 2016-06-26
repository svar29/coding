package com.uber.serviceDiscovery.api;

public interface Callback {

    public void serviceRegistered(ServiceInstance serviceInstance);

    public void serviceDeregistered(ServiceInstance serviceInstance);
}
