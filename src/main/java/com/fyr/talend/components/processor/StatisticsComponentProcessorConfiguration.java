package com.fyr.talend.components.processor;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

@GridLayout({
        // the generated layout put one configuration entry per line,
        // customize it as much as needed
        @GridLayout.Row({"maxElements"}),
        @GridLayout.Row({"separator"})
})
@Documentation("TODO fill the documentation for this configuration")
public class StatisticsComponentProcessorConfiguration implements Serializable {
    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private int maxElements = 10000;

    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private String separator = ";";

    public int getMaxElements() {
        return maxElements;
    }

    public StatisticsComponentProcessorConfiguration setMaxElements(int maxElements) {
        this.maxElements = maxElements;
        return this;
    }

    public String getSeparator() {
        return separator;
    }

    public StatisticsComponentProcessorConfiguration setSeparator(String separator) {
        this.separator = separator;
        return this;
    }
}