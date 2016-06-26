package com.uber.serviceDiscovery.client;

import com.uber.serviceDiscovery.api.Callback;
import com.uber.serviceDiscovery.api.ServiceDiscoveryApi;
import com.uber.serviceDiscovery.api.ServiceInstance;
import com.uber.serviceDiscovery.server.ServiceDiscoveryServer;

import java.util.List;
import java.util.Set;
/*
    A simple proxy client, which can talk the server. This implementation directly calls the server instance.
    Ideally, this communication could be over http/tcp.
 */
public class ProxyServiceDiscoveryClient implements ServiceDiscoveryApi {

    private final ServiceDiscoveryServer serviceDiscoveryServer;

    public ProxyServiceDiscoveryClient(ServiceDiscoveryServer serviceDiscoveryServer) {
        this.serviceDiscoveryServer = serviceDiscoveryServer;
    }

    @Override
    public Set<String> getAllServiceNames() {
        return serviceDiscoveryServer.getAllServiceNames();
    }

    @Override
    public void registerServiceInstance(ServiceInstance service) {
        serviceDiscoveryServer.registerServiceInstance(service);
    }

    @Override
    public void deregisterServiceInstance(ServiceInstance service) {
        serviceDiscoveryServer.deregisterServiceInstance(service);
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceName) {
        return serviceDiscoveryServer.getInstances(serviceName);
    }

    @Override
    public ServiceInstance getInstance(String serviceName, String id) {
        return serviceDiscoveryServer.getInstance(serviceName, id);
    }

    @Override
    public void watch(String serviceName, String nodeId, Callback callback) {
        serviceDiscoveryServer.watch(serviceName, nodeId, callback);
    }

    @Override
    public void unwatch(String serviceName, String nodeId) {
        serviceDiscoveryServer.unwatch(serviceName, nodeId);
    }
}
