package com.fyr.talend.components.util;

/**
 * #%L
 * ActivityInfo Server
 * %%
 * Copyright (C) 2009 - 2013 UNICEF
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 *
 *
 * Modified & extended by Fyrz
 **/

import com.google.common.collect.Lists;
import javafx.util.Pair;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * The Jenks optimization method, also called the Jenks natural breaks
 * classification method, is a data classification method designed to determine
 * the best arrangement of values into different classes. This is done by
 * seeking to minimize each class’s average deviation from the class mean, while
 * maximizing each class’s deviation from the means of the other groups. In
 * other words, the method seeks to reduce the variance within classes and
 * maximize the variance between classes.
 */
public class Jenks {

    private LinkedList<Pair<Double, Double>> list = Lists.newLinkedList();

    public void addValue(Pair<Double, Double> pair) {
        list.add(pair);
    }

    public void addValues(Pair<Double, Double>... values) {
        for (Pair<Double, Double> pair : values) {
            addValue(pair);
        }
    }

    /**
     * @return
     */
    public Breaks computeBreaks() {
        double[] list = toSortedArray();

        int uniqueValues = countUnique(list);
        if (uniqueValues <= 3) {
            return computeBreaks(list, uniqueValues);
        }

        Breaks lastBreaks = computeBreaks(list, 3);
        double lastGvf = lastBreaks.gvf();
        double lastImprovement = lastGvf - computeBreaks(list, 2).gvf();

        for (int i = 3; i <= Math.min(6, uniqueValues); ++i) {
            Breaks breaks = computeBreaks(list, 3);
            double gvf = breaks.gvf();
            double marginalImprovement = gvf - lastGvf;
            if (marginalImprovement < lastImprovement) {
                return lastBreaks;
            }
            lastBreaks = breaks;
            lastGvf = gvf;
            lastImprovement = marginalImprovement;
        }

        return lastBreaks;
    }

    private double[] toSortedArray() {
        double[] values = new double[this.list.size()];
        for (int i = 0; i != values.length; ++i) {
            values[i] = this.list.get(i).getKey();
        }
        Arrays.sort(values);
        return values;
    }

    private int countUnique(double[] sortedList) {
        int count = 1;
        for (int i = 1; i < sortedList.length; ++i) {
            if (sortedList[i] != sortedList[i - 1]) {
                count++;
            }
        }
        return count;
    }

    /**
     * @param numclass int number of classes
     * @return int[] breaks (upper indices of class)
     */
    public Breaks computeBreaks(int numclass) {
        return computeBreaks(toSortedArray(), numclass, new Identity());
    }

    private Breaks computeBreaks(double[] list, int numclass) {
        return computeBreaks(list, numclass, new Identity());
    }

    private Breaks computeBreaks(double[] list, int numclass, DoubleFunction transform) {

        int numdata = list.length;

        if (numdata == 0) {
            return new Breaks(this.list, new double[0], new int[0]);
        }

        double[][] mat1 = new double[numdata + 1][numclass + 1];
        double[][] mat2 = new double[numdata + 1][numclass + 1];

        for (int i = 1; i <= numclass; i++) {
            mat1[1][i] = 1;
            mat2[1][i] = 0;
            for (int j = 2; j <= numdata; j++) {
                mat2[j][i] = Double.MAX_VALUE;
            }
        }
        double v = 0;
        for (int l = 2; l <= numdata; l++) {
            double s1 = 0;
            double s2 = 0;
            double w = 0;
            for (int m = 1; m <= l; m++) {
                int i3 = l - m + 1;

                double val = transform.apply(list[i3 - 1]);

                s2 += val * val;
                s1 += val;

                w++;
                v = s2 - (s1 * s1) / w;
                int i4 = i3 - 1;
                if (i4 != 0) {
                    for (int j = 2; j <= numclass; j++) {
                        if (mat2[l][j] >= (v + mat2[i4][j - 1])) {
                            mat1[l][j] = i3;
                            mat2[l][j] = v + mat2[i4][j - 1];
                        }
                    }
                }
            }
            mat1[l][1] = 1;
            mat2[l][1] = v;
        }
        int k = numdata;

        int[] kclass = new int[numclass];

        kclass[numclass - 1] = list.length - 1;

        for (int j = numclass; j >= 2; j--) {
            int id = (int) (mat1[k][j]) - 2;

            kclass[j - 2] = id;

            k = (int) mat1[k][j] - 1;
        }
        return new Breaks(this.list, list, kclass);
    }

    private interface DoubleFunction {
        double apply(double x);
    }

    private static class Log10 implements DoubleFunction {

        @Override
        public double apply(double x) {
            return Math.log10(x);
        }
    }

    public static class Identity implements DoubleFunction {

        @Override
        public double apply(double x) {
            return x;
        }

    }

    public static class Breaks implements Serializable {

        private LinkedList<Pair<Double, Double>> list;
        private double[] sortedValues;
        private int[] breaks;

        /**
         * @param sortedValues the complete array of sorted data values
         * @param breaks       the indexes of the values within the sorted array that begin new classes
         */
        private Breaks(LinkedList<Pair<Double, Double>> list, double[] sortedValues, int[] breaks) {
            this.list = list;
            this.sortedValues = sortedValues;
            this.breaks = breaks;
        }

        /**
         * The Goodness of Variance Fit (GVF) is found by taking the difference
         * between the squared deviations from the array mean (SDAM) and the
         * squared deviations from the class means (SDCM), and dividing by the
         * SDAM
         *
         * @return
         */
        public double gvf() {
            double sdam = sumOfSquareDeviations(sortedValues);
            double sdcm = 0.0;
            for (int i = 0; i != numClassses(); ++i) {
                sdcm += sumOfSquareDeviations(classList(i));
            }
            return (sdam - sdcm) / sdam;
        }

        private double sumOfSquareDeviations(double[] values) {
            double mean = mean(values);
            double sum = 0.0;
            for (int i = 0; i != values.length; ++i) {
                double sqDev = Math.pow(values[i] - mean, 2);
                sum += sqDev;
            }
            return sum;
        }

        public double[] getValues() {
            return sortedValues;
        }

        public double[] classList(int i) {
            int classStart = (i == 0) ? 0 : breaks[i - 1] + 1;
            int classEnd = breaks[i];
            double list[] = new double[classEnd - classStart + 1];
            for (int j = classStart; j <= classEnd; ++j) {
                list[j - classStart] = sortedValues[j];
            }
            return list;
        }

        /**
         * @param classIndex
         * @return the minimum value (inclusive) of the given class
         */
        public double getClassMin(int classIndex) {
            if (classIndex == 0) {
                return sortedValues[0];
            } else {
                return sortedValues[breaks[classIndex - 1] + 1];
            }
        }

        /**
         * @param classIndex
         * @return the maximum value (inclusive) of the given class
         */
        public double getClassMax(int classIndex) {
            return sortedValues[breaks[classIndex]];
        }

        public int getClassCount(int classIndex) {
            if (classIndex == 0) {
                return breaks[0] + 1;
            } else {
                return breaks[classIndex] - breaks[classIndex - 1];
            }
        }

        private double mean(double[] values) {
            double sum = 0;
            for (int i = 0; i != values.length; ++i) {
                sum += values[i];
            }
            return sum / values.length;
        }

        public int numClassses() {
            return breaks.length;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i != numClassses(); ++i) {
                if (getClassMin(i) == getClassMax(i)) {
                    sb.append(getClassMin(i));
                } else {
                    sb.append(getClassMin(i)).append(" - ").append(getClassMax(i));
                }
                sb.append(" (" + getClassCount(i) + ")");
                sb.append(" = ").append(Arrays.toString(classList(i)));
                sb.append("\n");
            }
            return sb.toString();
        }

        /**
         * Return summary of classes
         *
         * @return String
         */
        public String toSummaryString() {
            DecimalFormat df = new DecimalFormat("0.00##");
            int currentClass;
            double totalAmount = 0;

            double[] values = new double[numClassses()];
            for (Pair<Double, Double> pair : this.list) {
                currentClass = classOf(pair.getKey());
                totalAmount += pair.getKey() * pair.getValue();
                values[currentClass] += pair.getKey() * pair.getValue();
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i != numClassses(); ++i) {
                if (getClassMin(i) == getClassMax(i)) {
                    sb.append(getClassMin(i));
                } else {
                    sb.append(getClassMin(i)).append(" - ").append(getClassMax(i));
                }

                // Check for division by zero
                String percentageStr = "N/A";
                if (totalAmount > 0) {
                    percentageStr = df.format((values[i] / totalAmount) * 100);
                }

                // print amount of cluster values in class and percentage in relation to total amount
                sb.append(" (" + getClassCount(i) + ", " + percentageStr + "%)");
                sb.append("\n");
            }
            return sb.toString();
        }


        /**
         * Return Class of double value
         *
         * @param value number of class
         * @return
         */
        public int classOf(double value) {
            for (int i = 0; i != numClassses(); ++i) {
                if (value <= getClassMax(i)) {
                    return i;
                }
            }
            return numClassses() - 1;
        }

        public static void serialize(String absolutePathToModel, Breaks model) {
            FileOutputStream fos = null;
            ObjectOutputStream out = null;
            try {
                fos = new FileOutputStream(absolutePathToModel);
                out = new ObjectOutputStream(fos);
                out.writeObject(model);

                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public static Breaks deserialize(String absolutePathToModel) {
            Breaks model = null;
            FileInputStream fis = null;
            ObjectInputStream in = null;
            try {
                fis = new FileInputStream(absolutePathToModel);
                in = new ObjectInputStream(fis);
                model = (Breaks) in.readObject();
                in.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return model;
        }
    }
}
