package fr.stb.stats.impl;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;

import java.io.Closeable;

public class CouchbaseConnector implements Closeable {

    private Cluster cluster;

    public CouchbaseConnector(String url, String login, String pwd) {
        Cluster cluster = CouchbaseCluster.create(url);
        cluster.authenticate(login, pwd);
        this.cluster = cluster;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public Bucket getBucket(String bucketName) {
        return this.cluster.openBucket(bucketName);
    }

    public void close() {
        this.cluster.disconnect();
    }
}
