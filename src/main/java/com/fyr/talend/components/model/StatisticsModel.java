package com.fyr.talend.components.model;

import com.fyr.talend.components.util.SetUtil;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class StatisticsModel {

    private Map<String, SortedMultiset<Double>> model = new HashMap<>();

    /**
     * Default CTor
     */
    public StatisticsModel() {

    }

    /**
     * Build statistics model for cluster using a separated list of values
     *
     * @param clusterName identifies the group of entries
     * @param clusterValues values in the group
     * @param separator for the values
     * @return StatisticsModel
     */
    public StatisticsModel build(final String clusterName, final String clusterValues, final String separator) throws ParseException {
        // Clear model
        if (model.size()>0) {
            model = new HashMap<>();
        }
        // Build
        for (String clusterValue : clusterValues.split(separator)) {
            addModelEntryValue(clusterName, NumberFormat.getInstance(Locale.US).parse(clusterValue).doubleValue());
        }
        return this;
    }

    /**
     * Build statistics model for cluster using a separated list of values
     *
     * @param clusterName   identifies the group of entries
     * @param clusterValues values in the group
     * @return StatisticsModel
     */
    public StatisticsModel build(final String clusterName, final double[] clusterValues) {
        // Clear model
        if (model.size() > 0) {
            model = new HashMap<>();
        }
        // Build
        for (Double clusterValue : clusterValues) {
            addModelEntryValue(clusterName, clusterValue);
        }
        return this;
    }


    /**
     * Add entry to model
     *
     * @param clusterName identifies the group of entries
     * @param modelEntry model entry
     */
    public void addModelEntry(final String clusterName, final SortedMultiset<Double> modelEntry) {
        model.put(clusterName, modelEntry);
    }

    /**
     * Add entry value to model
     *
     * @param clusterName  identifies the group of entries
     * @param clusterValue value indicates one value in the group
     */
    public void addModelEntryValue(final String clusterName, final Double clusterValue) {
        if (!model.containsKey(clusterName)) {
            model.put(clusterName, TreeMultiset.create());
        }
        model.get(clusterName).add(clusterValue);
    }

    /**
     * Returns the statistics model
     *
     * @return Map<String ,   SortedMultiset < Double>>
     */
    public Map<String, SortedMultiset<Double>> getModel() {
        return model;
    }


    /**
     * Returns an model entry
     */
    public SortedMultiset<Double> getModelEntry(final String clusterName) {
        return model.get(clusterName);
    }

    /**
     * Returns the min value for a cluster
     *
     * @param clusterName identifies the group of entries
     * @return Double
     */
    public Double getMinForCluster(final String clusterName) {
        Double retValue = null;
        if (model.containsKey(clusterName)) {
            retValue = model.get(clusterName).firstEntry().getElement();
        }
        return retValue;
    }

    /**
     * Returns the max value for a cluster
     *
     * @param clusterName identifies the group of entries
     * @return Double
     */
    public Double getMaxForCluster(final String clusterName) {
        Double retValue = null;
        if (model.containsKey(clusterName)) {
            retValue = model.get(clusterName).lastEntry().getElement();
        }
        return retValue;
    }

    /**
     * Calculates the median for a cluster of values
     *
     * @param clusterName identifies the group of entries
     * @return Double
     */
    public Double getMedian(final String clusterName) {
        Double retValue = null;
        if (model.get(clusterName) != null && model.get(clusterName).size() % 2 == 0) {
            int n = model.get(clusterName).size() / 2;
            retValue = (SetUtil.nthElement(model.get(clusterName), n - 1) + SetUtil.nthElement(model.get(clusterName), n)) / 2;
        } else {
            retValue = calcQuartile(clusterName, 50);
        }
        return retValue;
    }

    /**
     * Calculates the lower quartile (Q1)
     *
     * @param clusterName identifies the group of entries
     * @return Double
     */
    public Double getLowerQuartile(final String clusterName) {
        return calcQuartile(clusterName, 25);
    }

    /**
     * Calculates the upper quartile (Q3)
     *
     * @param clusterName identifies the group of entries
     * @return Double
     */
    public Double getUpperQuartile(final String clusterName) {
        return calcQuartile(clusterName, 75);
    }

    /**
     * Calculates the value of the quartile
     *
     * @param clusterName identifies the group of entries
     * @param percent percentage of the quartile usually either 25,50,75
     * @return Double
     */
    private Double calcQuartile(final String clusterName, int percent) {
        Double retValue = null;
        if (model.containsKey(clusterName)) {
            int n = (int) Math.round(model.get(clusterName).size() * percent / 100);
            retValue = SetUtil.nthElement(model.get(clusterName), n);
        }
        return retValue;
    }

    /**
     * Gets the average value opf the series
     *
     * @param clusterName identifies the group of entries
     * @return Double
     */
    public Double getAverage(final String clusterName) {
        Double retValue = null;
        if (model.containsKey(clusterName)) {
            Iterator<Double> it = model.get(clusterName).iterator();
            int count = 0;
            double sum = 0;
            while(it.hasNext()) {
                count++;
                sum += it.next();
            }
            retValue = sum/count;
        }
        return retValue;
    }

    /**
     * Returns the standard deviation
     *
     * @param clusterName identifies the group of entries
     * @return Double
     */
    public Double getStandardDeviation(final String clusterName) {
        Double retValue = getVariance(clusterName);
        if (retValue != null) {
            retValue = Math.sqrt(retValue);
        }
        return retValue;
    }

    /**
     * Returns the variance
     *
     * @param clusterName identifies the group of entries
     * @return Double
     */
    private Double getVariance(final String clusterName) {
        Double retValue = null;
        Double average = getAverage(clusterName);
        if (average != null) {
            Iterator<Double> it = model.get(clusterName).iterator();
            int count = 0;
            double sum = 0;
            while(it.hasNext()) {
                count++;
                sum += Math.pow(it.next() - average, 2);
            }
            retValue = sum/count;
        }
        return retValue;
    }

    /**
     * Returns the amount of values associated with a cluster
     *
     * @param clusterName identifies the group of entries
     * @return Integer
     */
    public Integer getNumberOfEntries(final String clusterName) {
        Integer num = null;
        if (model.containsKey(clusterName)) {
            num = model.get(clusterName).size();
        }
        return num;
    }

    /**
     * Calculate the interquartile range between the lower and the upper quartile
     *
     * @param clusterName identifies the group of entries
     * @return Double
     */
    public Double calcIQR(final String clusterName) {
        Double iqr = null;
        if (model.containsKey(clusterName)) {
            iqr = getUpperQuartile(clusterName) - getLowerQuartile(clusterName);
        }
        return iqr;
    }

    /**
     * Calculate Coefficient of Variance (As the term is not clearly defined we use the formula IQR/MED)
     *
     * @param clusterName identifies the group of entries
     * @return Double
     */
    public Double calcCoV(final String clusterName) {
        Double cov = null;
        if (model.containsKey(clusterName)) {
            cov = this.calcIQR(clusterName) / getMedian(clusterName);
        }
        return cov;
    }
}
