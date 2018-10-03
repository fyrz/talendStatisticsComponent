package com.fyr.talend.components.processor;

import org.talend.sdk.component.api.meta.Documentation;

import java.io.Serializable;

/**
 * Input definition for the Statistics Component
 */
@Documentation("Input definition for the Statistics Component")
public class StatisticsComponentInput implements Serializable {

    private String clusterName;
    private String clusterValues;

    public String getClusterValues() {
        return clusterValues;
    }

    public void setClusterValues(String clusterValues) {
        this.clusterValues = clusterValues;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(final String clusterName) {
        this.clusterName = clusterName;
    }

}