package com.fyr.talend.components.processor;

import com.fyr.talend.components.model.StatisticsModel;
import com.fyr.talend.components.service.Talend_statistics_componentService;
import org.talend.sdk.component.api.component.Icon;
import org.talend.sdk.component.api.component.Version;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.processor.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Serializable;
import java.text.ParseException;

@Version(1)
// default version is 1, if some configuration changes happen between 2 versions you can add a migrationHandler
@Icon(Icon.IconType.STAR)
// you can use a custom one using @Icon(value=CUSTOM, custom="filename") and adding icons/filename_icon32.png in resources
@Processor(name = "StatisticsComponent")
@Documentation("TODO fill the documentation for this processor")
public class StatisticsComponentProcessor implements Serializable {
    private final StatisticsComponentProcessorConfiguration configuration;
    private final Talend_statistics_componentService service;

    public StatisticsComponentProcessor(@Option("configuration") final StatisticsComponentProcessorConfiguration configuration,
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
            @Input final StatisticsComponentInput defaultInput, @Output final OutputEmitter<StatisticsComponentOutput> main) {
        final String clusterName = defaultInput.getClusterName();
        StatisticsModel statisticsModel = null;
        try {
            statisticsModel = new StatisticsModel().build(clusterName, defaultInput.getClusterValues(), configuration.getSeparator());

            StatisticsComponentOutput statisticsComponentOutput = new StatisticsComponentOutput()
                    .setClusterName(clusterName)
                    .setMean(statisticsModel.getAverage(clusterName))
                    .setMedian(statisticsModel.getMedian(clusterName))
                    .setMin(statisticsModel.getMinForCluster(clusterName))
                    .setMax(statisticsModel.getMaxForCluster(clusterName))
                    .setStddev(statisticsModel.getStandardDeviation(clusterName))
                    .setNum(statisticsModel.getNumberOfEntries(clusterName))
                    .setIqr(statisticsModel.calcIQR(clusterName))
                    .setCov(statisticsModel.calcCoV(clusterName));

            main.emit(statisticsComponentOutput);
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