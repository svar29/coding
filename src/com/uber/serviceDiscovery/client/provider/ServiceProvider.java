package com.uber.serviceDiscovery.client.provider;

import com.uber.serviceDiscovery.api.ServiceInstance;
import com.uber.serviceDiscovery.client.CachedServiceDiscoveryClient;
import com.uber.serviceDiscovery.client.ProxyServiceDiscoveryClient;
import com.uber.serviceDiscovery.client.ServiceDiscoveryClient;
import com.uber.serviceDiscovery.client.cache.InMemoryCache;
import com.uber.serviceDiscovery.server.InMemoryServiceDiscoveryServer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceProvider {

    private final ServiceDiscoveryClient serviceDiscoveryClient;
    private final Map<String, ServiceProviderStrategy> serviceProviders;

    private static Map<String, ServiceProvider> providers = new ConcurrentHashMap<String, ServiceProvider>();

    public static synchronized ServiceProvider getInstance(String nodeId) {
        if (!providers.containsKey(nodeId)) {
            ServiceProvider provider = new ServiceProvider(
                            new CachedServiceDiscoveryClient(
                                new ProxyServiceDiscoveryClient(new InMemoryServiceDiscoveryServer()),
                                    new InMemoryCache<String, Object>(), nodeId
                            ));
            providers.put(nodeId, provider);
        }
        return providers.get(nodeId);
    }

    private ServiceProvider(ServiceDiscoveryClient serviceDiscoveryClient) {
        this.serviceDiscoveryClient = serviceDiscoveryClient;
        serviceProviders = new ConcurrentHashMap<String, ServiceProviderStrategy>();
    }

    public ServiceInstance getService(String serviceName) {
        if (!serviceProviders.containsKey(serviceName)) {
            serviceProviders.put(serviceName, getDefaultServiceProviderStrategy());
        }

        return serviceProviders.get(serviceName).getServiceInstance(serviceDiscoveryClient.getInstances(serviceName));
    }

    private ServiceProviderStrategy getDefaultServiceProviderStrategy() {
        return new RoundRobinServiceProviderStrategy();
    }
}
