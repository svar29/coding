package com.uber.serviceDiscovery.server;

import com.uber.serviceDiscovery.api.Callback;
import com.uber.serviceDiscovery.api.ServiceInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/*
    An in-memory implementation of the ServiceDiscoveryServer
 */
public class InMemoryServiceDiscoveryServer implements ServiceDiscoveryServer {

    private static Map<String, Map<String, ServiceInstance>> SERVICES
                                            = new ConcurrentHashMap<String, Map<String, ServiceInstance>>();


    private static Map<String, Map<String, Callback>> callbacks = new ConcurrentHashMap<String, Map<String, Callback>>();

    @Override
    public Set<String> getAllServiceNames() {
        return SERVICES.keySet();
    }

    @Override
    public void registerServiceInstance(ServiceInstance service) {
        String serviceName = service.getServiceName();
        if (!SERVICES.containsKey(serviceName))
            SERVICES.put(serviceName, new ConcurrentHashMap<String, ServiceInstance>());

        SERVICES.get(serviceName).put(service.getServiceId(), service);
        triggerServiceAdditionCallbacks(service);
    }

    @Override
    public void deregisterServiceInstance(ServiceInstance service) {
        String serviceName = service.getServiceName();
        if (!SERVICES.containsKey(serviceName))
            return;

        SERVICES.get(serviceName).remove(service.getServiceId());
        triggerServiceRemovalCallbacks(service);
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceName) {
        if (!SERVICES.containsKey(serviceName))
            return new ArrayList<ServiceInstance>();

        return new ArrayList<ServiceInstance>(SERVICES.get(serviceName).values());
    }

    @Override
    public ServiceInstance getInstance(String serviceName, String id) {
        if (!SERVICES.containsKey(serviceName))
            return null;

        return SERVICES.get(serviceName).get(id);
    }

    private void triggerServiceAdditionCallbacks(ServiceInstance serviceInstance) {
        if (!callbacks.containsKey(serviceInstance.getServiceName()))
            return;

        for (Callback callback: callbacks.get(serviceInstance.getServiceName()).values())
            callback.serviceRegistered(serviceInstance);
    }

    private void triggerServiceRemovalCallbacks(ServiceInstance serviceInstance) {
        if (!callbacks.containsKey(serviceInstance.getServiceName()))
            return;

        for (Callback callback: callbacks.get(serviceInstance.getServiceName()).values())
            callback.serviceDeregistered(serviceInstance);
    }

    @Override
    public void watch(String serviceName, String nodeId, Callback callback) {
        if (!callbacks.containsKey(serviceName))
            callbacks.put(serviceName, new ConcurrentHashMap<String, Callback>());

        callbacks.get(serviceName).put(nodeId, callback);
    }

    @Override
    public void unwatch(String serviceName, String nodeId) {
        if (!callbacks.containsKey(serviceName))
            return;

        callbacks.get(serviceName).remove(nodeId);
    }
}
