package com.uber.serviceDiscovery;

import org.junit.Test;
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

import static org.junit.Assert.assertTrue;

public class ServiceDiscoveryTest {

    @Test
    public void testServiceDiscovery() throws InterruptedException {

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

        List<ServiceInstance> serviceAInstances = Arrays.asList(serviceAHost1Instance1, serviceAHost2Instance1);
        List<ServiceInstance> serviceBInstances = Arrays.asList(serviceBHost1Instance1, serviceBHost2Instance1);
        for (int i=0;i<10;i++) {
            Thread.sleep(500);
            ServiceInstance serviceInstanceBQueriedByHost1ForA = ((ServiceA) serviceAHost1Instance1).getServiceBHost();
            ServiceInstance serviceInstanceBQueriedByHost2ForA = ((ServiceA) serviceAHost2Instance1).getServiceBHost();

            assertTrue(serviceBInstances.contains(serviceInstanceBQueriedByHost1ForA));
            assertTrue(serviceBInstances.contains(serviceInstanceBQueriedByHost2ForA));

            ServiceInstance serviceInstanceAQueriedByHost1ForB = ((ServiceB) serviceBHost1Instance1).getServiceAHost();
            ServiceInstance serviceInstanceAQueriedByHost2ForB = ((ServiceB) serviceBHost2Instance1).getServiceAHost();

            assertTrue(serviceAInstances.contains(serviceInstanceAQueriedByHost1ForB));
            assertTrue(serviceAInstances.contains(serviceInstanceAQueriedByHost2ForB));
            System.out.println();
        }

        serviceAHost1OORHealthCheck.makeOOR();
        serviceBHost2OORHealthCheck.makeOOR();

        for (int i=0;i<10;i++) {
            Thread.sleep(500);
            ServiceInstance serviceInstanceBQueriedByHost1ForA = ((ServiceA) serviceAHost1Instance1).getServiceBHost();
            ServiceInstance serviceInstanceBQueriedByHost2ForA = ((ServiceA) serviceAHost2Instance1).getServiceBHost();

            assertTrue(serviceInstanceBQueriedByHost1ForA.equals(serviceBHost1Instance1));
            assertTrue(serviceInstanceBQueriedByHost2ForA.equals(serviceBHost1Instance1));

            ServiceInstance serviceInstanceAQueriedByHost1ForB = ((ServiceB) serviceBHost1Instance1).getServiceAHost();
            ServiceInstance serviceInstanceAQueriedByHost2ForB = ((ServiceB) serviceBHost2Instance1).getServiceAHost();

            assertTrue(serviceInstanceAQueriedByHost1ForB.equals(serviceAHost2Instance1));
            assertTrue(serviceInstanceAQueriedByHost2ForB.equals(serviceAHost2Instance1));
            System.out.println();
        }

        serviceAHost1OORHealthCheck.makeBIR();
        serviceBHost2OORHealthCheck.makeBIR();

        for (int i=0;i<10;i++) {
            Thread.sleep(500);
            ServiceInstance serviceInstanceBQueriedByHost1ForA = ((ServiceA) serviceAHost1Instance1).getServiceBHost();
            ServiceInstance serviceInstanceBQueriedByHost2ForA = ((ServiceA) serviceAHost2Instance1).getServiceBHost();

            assertTrue(serviceBInstances.contains(serviceInstanceBQueriedByHost1ForA));
            assertTrue(serviceBInstances.contains(serviceInstanceBQueriedByHost2ForA));

            ServiceInstance serviceInstanceAQueriedByHost1ForB = ((ServiceB) serviceBHost1Instance1).getServiceAHost();
            ServiceInstance serviceInstanceAQueriedByHost2ForB = ((ServiceB) serviceBHost2Instance1).getServiceAHost();

            assertTrue(serviceAInstances.contains(serviceInstanceAQueriedByHost1ForB));
            assertTrue(serviceAInstances.contains(serviceInstanceAQueriedByHost2ForB));
            System.out.println();
        }

    }
}
