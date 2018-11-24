package com.fyr.talend.components.processor;

import com.fyr.talend.components.model.ClusterOptimizer;
import com.fyr.talend.components.service.Talend_statistics_componentService;
import com.fyr.talend.components.util.Jenks;
import org.talend.sdk.component.api.component.Icon;
import org.talend.sdk.component.api.component.Version;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.processor.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.Serializable;
import java.text.ParseException;

/**
 * Talend-Component to compute a classification model.
 */
@Version(1)
@Icon(Icon.IconType.STAR)
@Processor(name = "StatisticsModel")
@Documentation("Component to compute a classification model.")
public class StatisticsModelProcessor implements Serializable {
    private final StatisticsModelProcessorConfiguration configuration;
    private final Talend_statistics_componentService service;

    public StatisticsModelProcessor(@Option("configuration") final StatisticsModelProcessorConfiguration configuration,
                                    final Talend_statistics_componentService service) {
        this.configuration = configuration;
        this.service = service;
    }

    @PostConstruct
    public void init() {
        // this method will be executed once for the whole component execution,
        // this is where you can establish a connection for instance
        // Note: if you don't need it you can delete it
    }

    @BeforeGroup
    public void beforeGroup() {

    }

    @ElementListener
    public void onNext(
            @Input final StatisticsComponentInput defaultInput, @Output final OutputEmitter<StatisticsModelOutput> main) {
        final String clusterName = defaultInput.getClusterName();
        try {
            ClusterOptimizer clusterOptimizer = new ClusterOptimizer(clusterName, defaultInput.getClusterValues(), configuration.getSeparator());
            Jenks.Breaks model = clusterOptimizer.optimize(Double.valueOf(configuration.getCovThreshold()), configuration.getMaxClusters());

            File modelFile = new File(configuration.getModelFolder(), clusterName + ".ser");
            Jenks.Breaks.serialize(modelFile.getAbsolutePath(), model);

            main.emit(new StatisticsModelOutput().setClusterName(clusterName).setNumberOfClusters(model.numClassses()).setClusterSummary(model.toSummaryString()));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @AfterGroup
    public void afterGroup() {

    }

    @PreDestroy
    public void release() {

    }
}