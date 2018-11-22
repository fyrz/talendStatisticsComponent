package com.fyr.talend.components.processor;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

import java.io.File;
import java.io.Serializable;

@GridLayout({
        @GridLayout.Row({"separator"}),
        @GridLayout.Row({"covThreshold"}),
        @GridLayout.Row({"maxClusters"}),
        @GridLayout.Row({"modelFolder"})

})
@Documentation("Statistics Component configuration")
public class StatisticsModelProcessorConfiguration implements Serializable {

    @Option
    @Documentation("Separator for given cluster values.")
    private String separator = ";";

    @Option
    @Documentation("CoV Optimization Threshold")
    private String covThreshold = "0.15";

    @Option
    @Documentation("Max Clusters")
    private int maxClusters = 10;

    @Option
    @Documentation("Model Folder")
    private File modelFolder;

    public String getSeparator() {
        return separator;
    }

    public StatisticsModelProcessorConfiguration setSeparator(String separator) {
        this.separator = separator;
        return this;
    }

    public int getMaxClusters() {
        return maxClusters;
    }

    public StatisticsModelProcessorConfiguration setMaxClusters(int maxClusters) {
        this.maxClusters = maxClusters;
        return this;
    }

    public File getModelFolder() {
        return modelFolder;
    }

    public StatisticsModelProcessorConfiguration setModelFolder(File modelFolder) {
        this.modelFolder = modelFolder;
        return this;
    }

    public String getCovThreshold() {
        return covThreshold;
    }

    public StatisticsModelProcessorConfiguration setCovThreshold(String covThreshold) {
        this.covThreshold = covThreshold;
        return this;
    }
}