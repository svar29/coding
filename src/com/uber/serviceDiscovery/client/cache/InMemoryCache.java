package com.uber.serviceDiscovery.client.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCache<K, V> implements Cache<K, V> {

    private Map<K, V> map;

    public InMemoryCache() {
        map = new ConcurrentHashMap<K, V>();
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public void remove(K key) {
        if (map.containsKey(key))
            map.remove(key);
    }
}
