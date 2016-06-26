package com.uber.serviceDiscovery.client.cache;

public class CacheKeys {
    public static final String ALL_SERVICE_NAMES = "ALL_SERVICES";

    public static String getInstancesByServiceName(String serviceName) {
        return String.format("service:%s:instances", serviceName);
    }

    public static String getInstanceById(String serviceName, String id) {
        return String.format("service:%s:instances:id", serviceName, id);
    }
}
