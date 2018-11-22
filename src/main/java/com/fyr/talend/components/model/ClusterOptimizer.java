package com.fyr.talend.components.model;

import com.fyr.talend.components.util.Jenks;

import java.text.ParseException;

public class ClusterOptimizer {

    private String clusterName;
    private String clusterValues;
    private String separator;

    public ClusterOptimizer(String clusterName, String clusterValues, String separator) {
        this.clusterName = clusterName;
        this.clusterValues = clusterValues;
        this.separator = separator;
    }

    public Jenks.Breaks optimize(double covRatioLimit) throws ParseException {
        StatisticsModel statisticsModel = new StatisticsModel();
        Jenks jenks = new Jenks();
        Jenks.Breaks breaks = null;
        boolean testFailed;

        for (String clusterValue : clusterValues.split(separator)) {
            jenks.addValue(Double.valueOf(clusterValue));
        }

        for (int numClusters = 1; numClusters < 10; numClusters++) {
            testFailed = false;
            breaks = jenks.computeBreaks(numClusters);

            for (int j = 0; j < numClusters; j++) {
                statisticsModel.build(clusterName, breaks.classList(0));
                if (statisticsModel.calcCoV(clusterName) > covRatioLimit) {
                    testFailed = true;
                }
            }

            if (!testFailed) {
                break;
            }
        }

        return breaks;
    }


}