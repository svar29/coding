package com.uber.serviceDiscovery.api;

import com.uber.serviceDiscovery.client.ServiceDiscoveryClient;
import com.uber.serviceDiscovery.client.healthchecker.HealthChecker;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
/*
    A node is basically any node in a data-centre.
    Multiple services/service-instances can be installed on a node.
    A node, by default, also has a ServiceDiscoveryClient, which contains a healthChecker, which periodically monitors
    the health of the installed services on the node, and accordingly registers/deregisters the service from the centralized
    ServiceDiscoveryServer.
 */
public class Node {

    private final ServiceDiscoveryClient serviceDiscoveryClient;
    private final String ip;
    private Map<ServiceInstance, Boolean> isHealthy;
    private Timer healthCheckTimer;

    public Node(String ip, ServiceDiscoveryClient serviceDiscoveryClient, List<ServiceInstance> serviceInstances) {
        this.ip = ip;
        this.serviceDiscoveryClient = serviceDiscoveryClient;
        isHealthy = new ConcurrentHashMap<ServiceInstance, Boolean>();
        for (ServiceInstance serviceInstance: serviceInstances)
            this.addService(serviceInstance);
    }

    private void addService(ServiceInstance service) {
        isHealthy.put(service, false);
        serviceDiscoveryClient.registerServiceInstance(service);
    }

    private void removeService(ServiceInstance service) {
        isHealthy.remove(service);
        serviceDiscoveryClient.deregisterServiceInstance(service);
    }

    public void markAsHealthy(ServiceInstance serviceInstance) {
        if (!isHealthy.containsKey(serviceInstance))
            return;
        Boolean previousValue = isHealthy.get(serviceInstance);
        if (previousValue == null || !previousValue)
            serviceDiscoveryClient.registerServiceInstance(serviceInstance);
        isHealthy.put(serviceInstance, true);
    }

    public void markAsUnhealthy(ServiceInstance serviceInstance) {
        if (!isHealthy.containsKey(serviceInstance))
            return;

        Boolean previousValue = isHealthy.get(serviceInstance);
        if (previousValue == null || !previousValue)
            serviceDiscoveryClient.deregisterServiceInstance(serviceInstance);
        isHealthy.put(serviceInstance, false);
    }

    public List<ServiceInstance> getServices() {
        return new ArrayList<ServiceInstance>(isHealthy.keySet());
    }

    public void start() {
        serviceDiscoveryClient.start();
        if (healthCheckTimer != null)
            healthCheckTimer.cancel();
        healthCheckTimer = new Timer();
        healthCheckTimer.schedule(new HealthChecker(this), 100, 100);
    }

    public void stop() {
        serviceDiscoveryClient.stop();
        if (healthCheckTimer != null)
            healthCheckTimer.cancel();
    }
}
