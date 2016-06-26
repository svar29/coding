package com.uber.serviceDiscovery.server;

import com.uber.serviceDiscovery.api.Callback;
import com.uber.serviceDiscovery.api.ServiceInstance;

import java.util.List;
import java.util.Set;

/*
    Api exposed by the central service discovery server, which knows the state of all services in the system
 */
public interface ServiceDiscoveryServer {

    public Set<String> getAllServiceNames();

    public void registerServiceInstance(ServiceInstance service);

    public void deregisterServiceInstance(ServiceInstance service);

    public List<ServiceInstance> getInstances(String serviceName);

    public ServiceInstance getInstance(String serviceName, String id);

    public void watch(String serviceName, String nodeId, Callback callback);

    public void unwatch(String serviceName, String nodeId);
}
