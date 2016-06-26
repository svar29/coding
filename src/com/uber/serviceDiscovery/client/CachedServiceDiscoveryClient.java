package com.uber.serviceDiscovery.client;

import com.uber.serviceDiscovery.api.Callback;
import com.uber.serviceDiscovery.api.ServiceDiscoveryApi;
import com.uber.serviceDiscovery.api.ServiceInstance;
import com.uber.serviceDiscovery.client.cache.Cache;
import com.uber.serviceDiscovery.client.cache.CacheKeys;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/*
    A cached implementation of the service discovery client, the get calls for service instances and names are cached.
 */
public class CachedServiceDiscoveryClient implements ServiceDiscoveryClient {

    private final ServiceDiscoveryApi serviceDiscoveryApi;
    private final Cache<String, Object> cache;
    private final Map<String, Callback> callbacks;
    private final String nodeId;

    public CachedServiceDiscoveryClient(ServiceDiscoveryApi serviceDiscoveryApi, Cache<String, Object> cache, String nodeId) {
        this.serviceDiscoveryApi = serviceDiscoveryApi;
        this.cache = cache;
        callbacks = new ConcurrentHashMap<String, Callback>();
        this.nodeId = nodeId;
    }

    @Override
    public Set<String> getAllServiceNames() {
        String key = CacheKeys.ALL_SERVICE_NAMES;
        Set<String> value = (Set<String>) cache.get(key);
        if (value == null) {
            value = serviceDiscoveryApi.getAllServiceNames();
            cache.put(key, value);
        }
        return value;
    }

    @Override
    public void registerServiceInstance(ServiceInstance service) {
        serviceDiscoveryApi.registerServiceInstance(service);
    }

    @Override
    public void deregisterServiceInstance(ServiceInstance service) {
        serviceDiscoveryApi.deregisterServiceInstance(service);
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceName) {
        String key = CacheKeys.getInstancesByServiceName(serviceName);
        List<ServiceInstance> value = (List<ServiceInstance>) cache.get(key);
        if (value == null) {
            value = serviceDiscoveryApi.getInstances(serviceName);
            cache.put(key, value);
        }
        registerCallback(serviceName);
        return value;
    }

    private void registerCallback(final String serviceName) {
        if (callbacks.containsKey(serviceName))
            return;

        final Callback callback = new Callback() {
            @Override
            public void serviceRegistered(ServiceInstance serviceInstance) {
                cache.remove(CacheKeys.getInstancesByServiceName(serviceInstance.getServiceName()));
                cache.remove(CacheKeys.getInstanceById(serviceInstance.getServiceName(), serviceInstance.getServiceId()));
                cache.remove(CacheKeys.ALL_SERVICE_NAMES);
            }

            @Override
            public void serviceDeregistered(ServiceInstance serviceInstance) {
                cache.remove(CacheKeys.getInstancesByServiceName(serviceInstance.getServiceName()));
                cache.remove(CacheKeys.getInstanceById(serviceInstance.getServiceName(), serviceInstance.getServiceId()));
                cache.remove(CacheKeys.ALL_SERVICE_NAMES);
            }
        };

        callbacks.put(serviceName, callback);
        watch(serviceName, nodeId, callback);
    }

    @Override
    public ServiceInstance getInstance(String serviceName, String id) {
        String key = CacheKeys.getInstanceById(serviceName, id);
        ServiceInstance value = (ServiceInstance) cache.get(key);
        if (value == null) {
            value = serviceDiscoveryApi.getInstance(serviceName, id);
            cache.put(key, value);
        }
        return value;
    }

    @Override
    public void watch(String serviceName, String nodeId, Callback callback) {
        serviceDiscoveryApi.watch(serviceName, nodeId, callback);
    }

    @Override
    public void unwatch(String serviceName, String nodeId) {
        serviceDiscoveryApi.unwatch(serviceName, nodeId);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        for(String service: callbacks.keySet())
            unwatch(service, nodeId);
        callbacks.clear();
    }
}
