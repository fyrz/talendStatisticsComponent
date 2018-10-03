package com.fyr.talend.components.processor;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

import java.io.Serializable;

@GridLayout({
        @GridLayout.Row({"separator"})
})
@Documentation("Statistics Component configuration")
public class StatisticsComponentProcessorConfiguration implements Serializable {

    @Option
    @Documentation("Separator for given cluster values.")
    private String separator = ";";

    public String getSeparator() {
        return separator;
    }

    public StatisticsComponentProcessorConfiguration setSeparator(String separator) {
        this.separator = separator;
        return this;
    }
}