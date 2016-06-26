package com.uber.serviceDiscovery.client.healthchecker;

import com.uber.serviceDiscovery.api.Node;
import com.uber.serviceDiscovery.api.ServiceInstance;

import java.util.List;
import java.util.TimerTask;

public class HealthChecker extends TimerTask {

    private final Node node;

    public HealthChecker(Node node) {
        this.node = node;
    }

    @Override
    public void run() {
        List<ServiceInstance> serviceInstances = node.getServices();
        for (ServiceInstance serviceInstance: serviceInstances) {
            boolean isHealthy = serviceInstance.getHealthCheck().isHealthy();
            if (!isHealthy)
                node.markAsUnhealthy(serviceInstance);
            else
                node.markAsHealthy(serviceInstance);
        }
    }
}
