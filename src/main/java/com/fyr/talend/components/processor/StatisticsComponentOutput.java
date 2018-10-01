package com.fyr.talend.components.processor;

import java.io.Serializable;

public class StatisticsComponentOutput implements Serializable {

    private String clusterName;
    private int num;
    private double mean;
    private double median;
    private double min;
    private double max;
    private double stddev;
    private double iqr;
    private double cov;

    public double getCov() {
        return cov;
    }

    public StatisticsComponentOutput setCov(double cov) {
        this.cov = cov;
        return this;
    }

    public double getIqr() {
        return iqr;
    }

    public StatisticsComponentOutput setIqr(double iqr) {
        this.iqr = iqr;
        return this;
    }

    public int getNum() {
        return num;
    }

    public StatisticsComponentOutput setNum(int num) {
        this.num = num;
        return this;
    }

    public double getMin() {
        return min;
    }

    public StatisticsComponentOutput setMin(double min) {
        this.min = min;
        return this;
    }

    public double getMax() {
        return max;
    }

    public StatisticsComponentOutput setMax(double max) {
        this.max = max;
        return this;
    }

    public double getStddev() {
        return stddev;
    }

    public StatisticsComponentOutput setStddev(double stddev) {
        this.stddev = stddev;
        return this;
    }

    public String getClusterName() {
        return clusterName;
    }

    public StatisticsComponentOutput setClusterName(final String clusterName) {
        this.clusterName = clusterName;
        return this;
    }

    public double getMean() {
        return mean;
    }

    public StatisticsComponentOutput setMean(double mean) {
        this.mean = mean;
        return this;
    }

    public double getMedian() {
        return median;
    }

    public StatisticsComponentOutput setMedian(double median) {
        this.median = median;
        return this;
    }
}