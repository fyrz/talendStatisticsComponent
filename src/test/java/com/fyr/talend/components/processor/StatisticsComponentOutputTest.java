package com.fyr.talend.components.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StatisticsComponentOutputTest {

    @Test
    public void testOutput() {
        StatisticsComponentOutput statisticsComponentOutput = new StatisticsComponentOutput();

        statisticsComponentOutput.setMedian(1.0);
        Assertions.assertEquals(new Double(1.0), (Double)statisticsComponentOutput.getMedian());

        statisticsComponentOutput.setClusterName("testX");
        Assertions.assertEquals("testX", statisticsComponentOutput.getClusterName());

        statisticsComponentOutput.setMean(2.0);
        Assertions.assertEquals(new Double(2.0), (Double)statisticsComponentOutput.getMean());

        statisticsComponentOutput.setCov(1.5);
        Assertions.assertEquals(new Double(1.5), (Double)statisticsComponentOutput.getCov());

        statisticsComponentOutput.setIqr(4.0);
        Assertions.assertEquals(new Double(4.0), (Double)statisticsComponentOutput.getIqr());

        statisticsComponentOutput.setMax(11.0);
        Assertions.assertEquals(new Double(11.0), (Double)statisticsComponentOutput.getMax());

        statisticsComponentOutput.setMin(0.0);
        Assertions.assertEquals(new Double(0.0), (Double)statisticsComponentOutput.getMin());

        statisticsComponentOutput.setNum(20);
        Assertions.assertEquals(20, statisticsComponentOutput.getNum());

        statisticsComponentOutput.setStddev(31.0);
        Assertions.assertEquals(new Double(31.0), (Double)statisticsComponentOutput.getStddev());
    }
}
