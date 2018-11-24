package com.fyr.talend.components.processor;

import org.talend.sdk.component.api.meta.Documentation;

import java.io.Serializable;

/**
 * Output definition for the Statistics Model
 */
@Documentation("Output definition for the Statistics Model")
public class StatisticsModelOutput implements Serializable {

    private String clusterName;
    private int numberOfClusters;
    private String clusterSummary;

    public String getClusterSummary() {
        return clusterSummary;
    }

    public StatisticsModelOutput setClusterSummary(String clusterSummary) {
        this.clusterSummary = clusterSummary;
        return this;
    }

    public int getNumberOfClusters() {
        return numberOfClusters;
    }

    public StatisticsModelOutput setNumberOfClusters(int numberOfClusters) {
        this.numberOfClusters = numberOfClusters;
        return this;
    }

    public String getClusterName() {
        return clusterName;
    }

    public StatisticsModelOutput setClusterName(final String clusterName) {
        this.clusterName = clusterName;
        return this;
    }

}