package com.springapp.mvc.services.Mongo;

import com.springapp.mvc.core.ConfigLoader;

import java.io.IOException;

/**
 * Created by Max on 2017/5/14.
 */
public class MongoConfig {
    public String host;
    public int port;
    public int connectionsPerHost;
    public int threadsAllowedToBlockForConnectionMultiplier;
    public int connectTimeout;
    public int maxWaitTime;
    public boolean autoConnectRetry;
    public boolean socketKeepAlive;
    public int socketTimeout;
    public boolean slaveOk;
    public int writeNumber;

    public static synchronized MongoConfig load() throws IOException {
        return ConfigLoader.loadYamlAs("mongo.yaml",MongoConfig.class);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getConnectionsPerHost() {
        return connectionsPerHost;
    }

    public void setConnectionsPerHost(int connectionsPerHost) {
        this.connectionsPerHost = connectionsPerHost;
    }

    public int getThreadsAllowedToBlockForConnectionMultiplier() {
        return threadsAllowedToBlockForConnectionMultiplier;
    }

    public void setThreadsAllowedToBlockForConnectionMultiplier(int threadsAllowedToBlockForConnectionMultiplier) {
        this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public boolean isAutoConnectRetry() {
        return autoConnectRetry;
    }

    public void setAutoConnectRetry(boolean autoConnectRetry) {
        this.autoConnectRetry = autoConnectRetry;
    }

    public boolean isSocketKeepAlive() {
        return socketKeepAlive;
    }

    public void setSocketKeepAlive(boolean socketKeepAlive) {
        this.socketKeepAlive = socketKeepAlive;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public boolean isSlaveOk() {
        return slaveOk;
    }

    public void setSlaveOk(boolean slaveOk) {
        this.slaveOk = slaveOk;
    }

    public int getWriteNumber() {
        return writeNumber;
    }

    public void setWriteNumber(int writeNumber) {
        this.writeNumber = writeNumber;
    }

    public int getRiteTimeout() {
        return riteTimeout;
    }

    public void setRiteTimeout(int riteTimeout) {
        this.riteTimeout = riteTimeout;
    }

    public boolean isWriteFsync() {
        return writeFsync;
    }

    public void setWriteFsync(boolean writeFsync) {
        this.writeFsync = writeFsync;
    }

    public int riteTimeout;
    public boolean writeFsync;

}
