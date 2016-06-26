package com.uber.serviceDiscovery.api;

import java.util.List;
import java.util.Set;

/*
    This is the ServiceDiscoveryApi contract defined for a client
 */
public interface ServiceDiscoveryApi {

    public Set<String> getAllServiceNames();

    public void registerServiceInstance(ServiceInstance service);

    public void deregisterServiceInstance(ServiceInstance service);

    public List<ServiceInstance> getInstances(String serviceName);

    public ServiceInstance getInstance(String serviceName, String id);

    public void watch(String serviceName, String nodeId, Callback callback);

    public void unwatch(String serviceName, String nodeId);
}
