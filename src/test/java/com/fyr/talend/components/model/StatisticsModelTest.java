package com.fyr.talend.components.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

public class StatisticsModelTest {

    private final String clusterA = "cluster a";
    private final String clusterB = "cluster b";
    
    @Test
    public void testMinMaxValue() {
        StatisticsModel statisticsModel = new StatisticsModel();
        statisticsModel.addModelEntry(clusterA, 1.0);
        statisticsModel.addModelEntry(clusterA, 2.0);
        statisticsModel.addModelEntry(clusterA, 3.0);
        statisticsModel.addModelEntry(clusterB, 1.0);

        Assertions.assertEquals((Double)1.0,statisticsModel.getMinForCluster(clusterA));
        Assertions.assertEquals((Double)3.0,statisticsModel.getMaxForCluster(clusterA));
        Assertions.assertEquals((Double)1.0,statisticsModel.getMinForCluster(clusterB));
        Assertions.assertEquals((Double)1.0,statisticsModel.getMaxForCluster(clusterB));
    }

    @Test
    public void testOddQuartiles() {
        //5, 7, 4, 4, 6, 2, 8
        StatisticsModel statisticsModel = new StatisticsModel();
        statisticsModel.addModelEntry(clusterA, 5.0);
        statisticsModel.addModelEntry(clusterA, 7.0);
        statisticsModel.addModelEntry(clusterA, 4.0);
        statisticsModel.addModelEntry(clusterA, 4.0);
        statisticsModel.addModelEntry(clusterA, 6.0);
        statisticsModel.addModelEntry(clusterA, 2.0);
        statisticsModel.addModelEntry(clusterA, 8.0);

        Assertions.assertEquals((Double)5.0,statisticsModel.getMedian(clusterA));
        Assertions.assertEquals((Double)4.0,statisticsModel.getLowerQuartile(clusterA));
        Assertions.assertEquals((Double)7.0,statisticsModel.getUpperQuartile(clusterA));
    }

    @Test
    public void testEvenQuartiles() {
        //1, 3, 3, 4, 5, 6, 6, 7, 8, 8
        StatisticsModel statisticsModel = new StatisticsModel();
        statisticsModel.addModelEntry(clusterA, 1.0);
        statisticsModel.addModelEntry(clusterA, 3.0);
        statisticsModel.addModelEntry(clusterA, 3.0);
        statisticsModel.addModelEntry(clusterA, 4.0);
        statisticsModel.addModelEntry(clusterA, 6.0);
        statisticsModel.addModelEntry(clusterA, 6.0);
        statisticsModel.addModelEntry(clusterA, 7.0);
        statisticsModel.addModelEntry(clusterA, 8.0);
        statisticsModel.addModelEntry(clusterA, 8.0);
        statisticsModel.addModelEntry(clusterA, 5.0);

        Assertions.assertEquals((Double) 5.5, statisticsModel.getMedian(clusterA));
        Assertions.assertEquals((Double) 3.0, statisticsModel.getLowerQuartile(clusterA));
        Assertions.assertEquals((Double) 7.0, statisticsModel.getUpperQuartile(clusterA));
    }

    @Test
    public void testExceptionalCaseQuartileOneEntry() {
        StatisticsModel statisticsModel = new StatisticsModel();
        statisticsModel.addModelEntry(clusterA, 1.0);

        Assertions.assertEquals((Double)1.0,statisticsModel.getMedian(clusterA));
        Assertions.assertEquals((Double)1.0,statisticsModel.getLowerQuartile(clusterA));
        Assertions.assertEquals((Double)1.0,statisticsModel.getUpperQuartile(clusterA));
    }

    @Test
    public void testExceptionalCaseQuartileTwoEntry() {
        StatisticsModel statisticsModel = new StatisticsModel();
        statisticsModel.addModelEntry(clusterA, 1.0);
        statisticsModel.addModelEntry(clusterA, 2.0);

        Assertions.assertEquals((Double)1.5,statisticsModel.getMedian(clusterA));
        Assertions.assertEquals((Double)1.0,statisticsModel.getLowerQuartile(clusterA));
        Assertions.assertEquals((Double)2.0,statisticsModel.getUpperQuartile(clusterA));
    }

    @Test
    public void testAverage() {
        StatisticsModel statisticsModel = new StatisticsModel();
        statisticsModel.addModelEntry(clusterA, 11.0);
        statisticsModel.addModelEntry(clusterA, 23.0);
        statisticsModel.addModelEntry(clusterA, 30.0);
        statisticsModel.addModelEntry(clusterA, 47.0);
        statisticsModel.addModelEntry(clusterA, 56.0);


        Assertions.assertEquals((Double)33.4,statisticsModel.getAverage(clusterA));
        Assertions.assertEquals((Double)30.0,statisticsModel.getMedian(clusterA));
    }

    @Test
    public void testStandardDeviation() {
        StatisticsModel statisticsModel = new StatisticsModel();
        statisticsModel.addModelEntry(clusterA, 2.0);
        statisticsModel.addModelEntry(clusterA, 4.0);
        statisticsModel.addModelEntry(clusterA, 4.0);
        statisticsModel.addModelEntry(clusterA, 4.0);
        statisticsModel.addModelEntry(clusterA, 5.0);
        statisticsModel.addModelEntry(clusterA, 5.0);
        statisticsModel.addModelEntry(clusterA, 7.0);
        statisticsModel.addModelEntry(clusterA, 9.0);

        Assertions.assertEquals((Integer)8, statisticsModel.getNumberOfEntries(clusterA));
        Assertions.assertEquals((Double)5.0,statisticsModel.getAverage(clusterA));
        Assertions.assertEquals((Double)2.0,statisticsModel.getStandardDeviation(clusterA));
    }

    @Test
    public void testBuildModel() throws ParseException {
        StatisticsModel statisticsModel = new StatisticsModel();
        statisticsModel.build(clusterA, "9.0;132.50;3", ";");
        Assertions.assertEquals(3, statisticsModel.getModel().get(clusterA).size());
    }
}
