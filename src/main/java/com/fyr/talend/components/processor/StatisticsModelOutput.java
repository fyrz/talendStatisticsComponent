package com.fyr.talend.components.processor;

import org.talend.sdk.component.api.meta.Documentation;

import java.io.Serializable;

/**
 * Output definition for the Statistics Model
 */
@Documentation("Output definition for the Statistics Model")
public class StatisticsModelOutput implements Serializable {

    private String clusterName;
    private int num;

    public int getNum() {
        return num;
    }

    public StatisticsModelOutput setNum(int num) {
        this.num = num;
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