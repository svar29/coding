package com.uber.serviceDiscovery;

import com.uber.serviceDiscovery.api.Node;
import com.uber.serviceDiscovery.api.ServiceInstance;
import com.uber.serviceDiscovery.client.CachedServiceDiscoveryClient;
import com.uber.serviceDiscovery.client.ProxyServiceDiscoveryClient;
import com.uber.serviceDiscovery.client.cache.InMemoryCache;
import com.uber.serviceDiscovery.healthchecks.OORHealthCheck;
import com.uber.serviceDiscovery.server.InMemoryServiceDiscoveryServer;
import com.uber.serviceDiscovery.server.ServiceDiscoveryServer;
import com.uber.serviceDiscovery.services.ServiceA;
import com.uber.serviceDiscovery.services.ServiceB;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String args[]) throws InterruptedException {

        ServiceDiscoveryServer server = new InMemoryServiceDiscoveryServer();
        OORHealthCheck serviceAHost1OORHealthCheck = new OORHealthCheck();
        serviceAHost1OORHealthCheck.makeBIR();

        OORHealthCheck serviceBHost1OORHealthCheck = new OORHealthCheck();
        serviceBHost1OORHealthCheck.makeBIR();

        ServiceInstance serviceAHost1Instance1 = new ServiceA("id1", "host1", 5555, serviceAHost1OORHealthCheck);
        ServiceInstance serviceBHost1Instance1 = new ServiceB("id2", "host1", 5556, serviceBHost1OORHealthCheck);
        List<ServiceInstance> host1ServiceInstances = Arrays.asList(
                serviceAHost1Instance1, serviceBHost1Instance1
        );

        OORHealthCheck serviceAHost2OORHealthCheck = new OORHealthCheck();
        serviceAHost2OORHealthCheck.makeBIR();

        OORHealthCheck serviceBHost2OORHealthCheck = new OORHealthCheck();
        serviceBHost2OORHealthCheck.makeBIR();

        ServiceInstance serviceAHost2Instance1 = new ServiceA("id3", "host2", 5555, serviceAHost2OORHealthCheck);
        ServiceInstance serviceBHost2Instance1 = new ServiceB("id4", "host2", 5556, serviceBHost2OORHealthCheck);
        List<ServiceInstance> host2ServiceInstances = Arrays.asList(
                serviceAHost2Instance1, serviceBHost2Instance1
        );

        Node host1 = new Node("host1", new CachedServiceDiscoveryClient(new ProxyServiceDiscoveryClient(server), new InMemoryCache<String, Object>(), "host1"), host1ServiceInstances);
        Node host2 = new Node("host2", new CachedServiceDiscoveryClient(new ProxyServiceDiscoveryClient(server), new InMemoryCache<String, Object>(), "host2"), host2ServiceInstances);

        host1.start();
        host2.start();

        for (int i=0;i<10;i++){
            Thread.sleep(1000);
            ((ServiceA) serviceAHost1Instance1).getServiceBHost();
            ((ServiceA) serviceAHost2Instance1).getServiceBHost();
            ((ServiceB) serviceBHost1Instance1).getServiceAHost();
            ((ServiceB) serviceBHost2Instance1).getServiceAHost();
            System.out.println();
        }

        serviceAHost1OORHealthCheck.makeOOR();
        serviceBHost2OORHealthCheck.makeOOR();

        System.out.println("\n\nServiceA at host1 is unhealthy, ServiceB at host2 is unhealthy.\n\n");
        for (int i=0;i<10;i++){
            Thread.sleep(1000);
            ((ServiceA) serviceAHost1Instance1).getServiceBHost();
            ((ServiceA) serviceAHost2Instance1).getServiceBHost();
            ((ServiceB) serviceBHost1Instance1).getServiceAHost();
            ((ServiceB) serviceBHost2Instance1).getServiceAHost();
            System.out.println();
        }

        serviceAHost1OORHealthCheck.makeBIR();
        serviceBHost2OORHealthCheck.makeBIR();

        System.out.println("\n\nServiceA at host1 is healthy, ServiceB at host2 is healthy.\n\n");
        for (int i=0;i<10;i++){
            Thread.sleep(1000);
            ((ServiceA) serviceAHost1Instance1).getServiceBHost();
            ((ServiceA) serviceAHost2Instance1).getServiceBHost();
            ((ServiceB) serviceBHost1Instance1).getServiceAHost();
            ((ServiceB) serviceBHost2Instance1).getServiceAHost();
            System.out.println();
        }

        host1.stop();
        host2.stop();
    }
}
