package com.fyr.talend.components.model;

import com.fyr.talend.components.util.Jenks;
import javafx.util.Pair;

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
        return optimize(covRatioLimit, 10);
    }

    public Jenks.Breaks optimize(double covRatioLimit, int maxClusters) throws ParseException {
        StatisticsModel statisticsModel = new StatisticsModel();
        Jenks jenks = new Jenks();
        Jenks.Breaks breaks = null;
        Pair<Double, Double> pair;
        boolean testFailed;

        for (String clusterValue : clusterValues.split(separator)) {
            if (clusterValue.contains("/")) {
                // Split value and amount of occurences
                String[] values = clusterValue.split("/");
                pair = new Pair<Double, Double>(Double.valueOf(values[0]), Double.valueOf(values[1]));
            } else {
                // If occurences are not given default to 1
                pair = new Pair<Double, Double>(Double.valueOf(clusterValue), 1d);
            }


            jenks.addValue(pair);
        }

        for (int numClusters = 1; numClusters < maxClusters; numClusters++) {
            testFailed = false;
            breaks = jenks.computeBreaks(numClusters);

            for (int j = 0; j < numClusters; j++) {
                statisticsModel.build(clusterName, breaks.classList(0));
                if (statisticsModel.calcCoV(clusterName) > covRatioLimit) {
                    testFailed = true;
                    break;
                }
            }

            if (!testFailed) {
                break;
            }
        }
        return breaks;
    }


}
