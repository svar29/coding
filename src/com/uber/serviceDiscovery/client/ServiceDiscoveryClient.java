package com.uber.serviceDiscovery.client;

import com.uber.serviceDiscovery.api.ServiceDiscoveryApi;

public interface ServiceDiscoveryClient extends ServiceDiscoveryApi {

    public void start();

    public void stop();
}
