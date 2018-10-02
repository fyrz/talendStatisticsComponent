package com.fyr.talend.components.processor;

import org.junit.jupiter.api.Test;
import org.talend.sdk.component.junit.ComponentsHandler;
import org.talend.sdk.component.junit5.Injected;
import org.talend.sdk.component.junit5.WithComponents;
import org.talend.sdk.component.runtime.output.Processor;

@WithComponents("com.fyr.talend.components")
public class StatisticsComponentProcessorTest {

    @Injected
    private ComponentsHandler handler;

    @Test
    public void testBeforeAndAfterGroup() {
        StatisticsComponentProcessorConfiguration statisticsComponentProcessorConfiguration = new StatisticsComponentProcessorConfiguration().setMaxElements(10000).setSeparator(";");
        Processor processor = handler.createProcessor(StatisticsComponentProcessor.class,
                statisticsComponentProcessorConfiguration);
        processor.start();
        processor.beforeGroup();
        processor.afterGroup(null);
        processor.stop();
    }
}
