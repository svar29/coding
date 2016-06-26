package com.uber.serviceDiscovery.api;

public abstract class ServiceInstance {

    private final String id;

    private String host;

    private int port;

    public ServiceInstance(String id, String host, int port) {
        this.id = id;
        this.host = host;
        this.port = port;
    }

    public String getServiceName() {
        return getClass().getName();
    }

    public String getServiceId() {
        return id;
    }

    public abstract HealthCheck getHealthCheck();

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceInstance)) return false;

        ServiceInstance that = (ServiceInstance) o;

        if (port != that.port) return false;
        if (host != null ? !host.equals(that.host) : that.host != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (host != null ? host.hashCode() : 0);
        result = 31 * result + port;
        return result;
    }

    @Override
    public String toString() {
        return "ServiceInstance{" +
                "id='" + id + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
