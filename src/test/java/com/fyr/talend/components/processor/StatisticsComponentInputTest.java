package com.fyr.talend.components.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StatisticsComponentInputTest {

    @Test
    public void testInput() {
        StatisticsComponentInput statisticsComponentInput = new StatisticsComponentInput();

        statisticsComponentInput.setClusterName("test");
        Assertions.assertEquals("test", statisticsComponentInput.getClusterName());

        statisticsComponentInput.setClusterValues("1.0;2.0;3.0");
        Assertions.assertEquals("1.0;2.0;3.0", statisticsComponentInput.getClusterValues());
    }
}
