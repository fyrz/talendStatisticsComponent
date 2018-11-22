package com.fyr.talend.components.model;


import com.fyr.talend.components.util.Jenks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

public class ClusterOptimizerTest {

    private String testSeriesA = "xyz|18.3;18.6;20.3;15.9;15.9;49.5;20.0;24.4;14.4;18.7;12.9;7.1;23.0;21.2;6.2;11.8;26.7;18.6;22.3;23.9;11.5;15.2;10.5;14.5;19.5;64.0;37.5;20.2;11.3;27.4;21.3;11.9;9.7;33.5;16.2;14.0;14.4;38.3;21.7;9.4;13.8;13.8;13.9;13.9;44.7;49.5;33.0;16.2;25.2;25.0;15.0;11.6;13.4;21.0;21.0;14.8;9.2;9.0;9.0;9.0;17.1;18.9;46.0;36.5;36.5;38.8;12.0;12.0;12.0;12.0;20.8;20.8;20.8;35.5;22.3;14.0;77.0;13.7;13.9;22.9;44.2;15.6;24.5;65.0;27.1;12.6;52.0;24.4;11.0;12.0;11.0;26.3;26.2;28.2;28.2;26.0;18.8;33.0;29.5;24.9;37.0;75.8;13.9;12.0;12.2;49.0;57.0;25.8;24.5;40.5;40.5;12.2;12.5;33.9;30.0;17.8;51.0;19.2;15.4;14.4;10.5;14.2;6.8;17.9;18.5;64.0;27.5;25.5;50.0;35.0;32.5;15.9;7.6;17.0;39.0;10.0;12.5;12.0;200.0;200.0;7.5;20.5;20.5;20.5;20.5;15.4;38.5;14.5;18.5;19.0;14.5;19.0;10.2;23.9;25.0;24.0;16.5;12.7;10.4;4.2;11.4;12.2;8.8;6.4;10.5;15.0;11.3;34.9;49.5;17.0;4.3;23.0;31.0;18.0;11.2;21.6;32.0;12.2;10.9;25.0;62.0;17.2;18.9;17.3;11.5;24.3;10.9;19.0;15.7;15.3;35.0;16.7;48.0;9.2;12.3;8.6;30.0;19.8;16.5;17.0;10.1;7.1;46.5;12.6;21.6;16.0;8.2;17.5;25.5;17.0;15.9;14.5;12.8;14.5;15.0;16.0;13.4;24.8;32.0;17.8;6.1;2.7;12.2;32.0;11.6;32.5;27.3;22.3;23.9;15.5;14.5;16.7;11.5;13.6;24.5;24.5;27.8;9.0;14.3;15.9;15.3;79.5;21.3;64.0;24.5;17.8;94.0;9.1;43.0;23.0;9.75;50.5;20.8;12.5;20.5;12.5;7.5;10.9;8.7;8.5;11.7;11.7;11.7;14.8;14.8;54.5;54.5;22.2;15.0;15.5;19.5;20.0;17.3;9.1;7.6;14.5;12.6;14.2;10.1;13.5;10.0;16.8;15.3;18.3;18.0;12.5;8.6;12.4;9.75;27.4;20.7;29.7;14.9;9.9;9.7;11.0;11.6;33.8;34.0;32.0;13.5;12.0;13.8;13.8;16.2;14.0;22.8;13.6;9.1;9.0;9.0;23.4;29.5;32.0;14.5;12.3;11.5;20.6;37.5;37.5;33.7;48.3;27.3;24.8;73.0;37.5;25.0;24.2;60.0;9.4;12.8;11.2;9.3;9.7;31.5;29.4;39.0;21.3;20.6;38.8;14.3;10.3;29.5;13.4;39.5;13.8;14.0;14.0;27.5;9.1;29.8;8.5;12.3;37.8;8.3;13.3;10.2;13.3;14.2;8.8;36.0;27.0;12.4;35.4;9.9;40.0;20.2;14.3;20.6;9.6;19.2;10.3;12.4;12.1;19.2;33.0;41.5;9.6;32.0;52.0;17.1;13.1;32.7;32.7;45.3;49.5;49.5;9.5;15.7;15.7;16.0;16.0;37.5;42.0;32.0;16.5;24.0;10.8;37.5;18.8;12.4;15.6;34.5;24.5;27.0;13.4;11.5;25.5;9.5;35.9;11.0;53.0;15.8;15.8;47.5;14.1;11.8;11.8;9.5;10.6;11.7;11.8;20.4;16.3;19.9;48.0;47.2;26.8;26.8;31.0;6.8;27.0;6.5;7.7;5.8;5.8;17.1;6.6;8.0;8.4;9.6;24.5;24.5;40.0;9.2;9.8;17.1;15.7;16.8;15.8;21.5;21.2;32.5;9.5;17.0;24.3;24.3;14.9;12.7;13.8;18.8;48.0;11.0;49.0;49.0;10.7;10.8;11.6;24.9;16.2;13.8;13.5;14.2;18.9;12.5;19.9;54.0;54.0;67.5;18.0;13.7;19.7;16.8;12.7;26.7;26.9;36.5;16.9;14.6;10.9;42.6;22.7;38.0;38.0;32.6;37.0;21.1;21.1;12.7;11.7;17.5;12.9;15.1;20.4;50.0;41.5;40.0;21.7;32.0;32.0;11.8;16.5";

    private String testSeriesB = "zyx|307.8;313.6;228.0;154.0;241.4;127.4;285.0;326.8;300.2;176.8;173.0;150.2;285.0;150.2;264.2;250.8;222.4;478.8;174.8;188.2;188.2;165.4;228.0;152.0;247.0;218.6;205.2;228.0;207.2;163.4;211.0;207.2;366.8;247.0;247.0;241.4;182.4;155.8;201.4;167.2;184.4;226.2;226.2;226.2;254.6;254.6;306.0;281.2;155.8;180.6;182.4;169.2;173.0;182.4;188.2;171.0;211.0;148.2;156.2;155.8;133.0;135.0;157.8;201.4;148.2;195.8;291.0;237.6;203.4;154.8;195.8;196.2;220.4;334.4;176.8;332.6;135.0;332.6;212.8;560.6;233.8;250.8;258.4;241.4;302.2;167.2;199.6;171.0;161.6;192.0;171.0;203.4;241.4;233.8;211.0;222.4;330.6;174.8;216.6;241.4;264.2;357.2;266.0;283.2;269.8;256.6;228.0;186.2;239.4;237.6;247.0;380.0;259.4;241.4;228.0;334.4;211.0;334.4;173.0;266.0;186.2;190.0;317.4;190.0;317.4;201.4;317.4;211.0;317.4;317.4;281.2;218.6;252.8;186.2;235.6;207.2;176.8;224.2;323.0;148.2;287.0;186.2;325.0;226.2;182.4;235.6;207.2;195.8;313.6;249.0;274.2;252.8;389.6;321.2;376.2;165.4;165.4;344.0;264.2;397.2;328.8;182.4;167.2;212.8;186.2;180.6;275.6;220.4;228.0;220.4;494.0;214.8;540.4;465.6;224.2;189.2;249.0;177.8;218.6;222.4;427.6;222.4;192.0;406.6;224.2;193.8;220.4;271.8;155.8;241.4;155.8;187.6;294.6;237.6;309.8;287.0;252.8;254.6;239.4;239.4;254.6;252.8;222.4;287.0;157.8;178.6;306.0;273.6;186.2;260.4;250.8;264.2;264.2;427.6;294.6;296.4;342.0;218.6;218.6;218.6;193.8;226.2;260.4;182.4;216.6;237.6;182.4;199.6;250.8;313.6;252.8;220.4;279.4;238.4;252.8;178.8;264.2;302.2;224.2;323.0;218.6;247.0;220.4;304.0;307.8;235.6;245.2;330.6;282.6;292.6;231.8;341.0;275.6;319.2;241.4;418.0;472.0;243.2;279.4;256.6;150.2;180.6;211.0;376.2;266.0;180.6;218.6;268.0;249.0;311.6;277.4;334.4;188.2;180.6;189.2;237.6;150.2;247.0;190.0;180.6;277.4;277.4;184.4;190.0;218.6;202.4;182.4;218.6;254.6;239.4;220.4;326.8;182.4;209.0;215.6;228.0;182.4;334.4;332.6;323.0;342.0;176.8;203.4;285.0;262.2;323.0;323.0;199.6;211.8;211.0;186.9;184.4;171.0;228.0;262.2;217.6;182.4;171.0;190.0;248.0;389.6;275.6;328.8;243.2;211.8;296.4;157.8;429.4;429.4;182.4;182.4;184.4;176.8;211.0;228.0;247.0;247.0;250.8;243.2;247.0;146.4;296.4;300.2;188.2;207.2;207.2;319.2;213.8;346.6;326.8;294.6;294.6;183.6;222.4;237.6;222.4;184.4;268.0;211.0;247.0;214.8;214.8;215.6;182.4;182.4;186.2;241.4;152.0;157.8;169.2;199.6;281.2;171.0;250.8;241.4;231.8;321.2;184.4;231.8;195.8;225.2;202.4;178.6;184.4;182.4;190.0;250.8;228.0;370.6;216.6;216.6;157.8;230.0;157.8;252.8;161.6;157.8;184.4;201.4;199.6;306.0;199.6;243.2;256.6;208.0;190.0;174.8;243.2;186.2;146.4;190.0;199.6;212.8;254.6;237.6;252.8;222.4;256.6;220.4;230.0;213.8;157.8;213.8;218.6;188.2;186.2;176.8;245.2;275.6;220.4;235.6;207.2;188.2;219.4;254.6;192.0;152.0;184.4;180.6;216.6;207.2;207.2;208.0;208.0;208.0;218.6;188.2;188.2;178.6;188.2;214.8;199.6;351.6;351.6;345.8;345.8;345.8;178.6;157.8;380.0;182.4;190.0;294.6;207.2;256.6;197.6;195.8;195.8;247.0;207.2;218.6;218.6;161.6;161.6;290.8;171.0;171.0;212.8;226.2;203.4;323.0;273.6;233.8;361.0;218.6;136.8;182.4;169.2;176.8;254.6;176.8;300.2;245.2;152.0;353.4;201.4;285.0;241.4;192.0;256.6;247.0;247.0;247.0;262.2;173.0;290.8;142.6;197.6;247.0;186.2;207.2;193.8;250.8;178.8;266.0;349.6;347.4;389.6;338.8;176.8;159.6;184.4;218.6;173.0;125.4;203.4;206.2;318.2;283.2;250.8;203.4;192.0;187.2;148.2;154.0;235.6;201.4;212.8;412.4;218.6;154.0;180.6;130.0;174.8;150.2;144.4;180.6;178.6;344.0;233.8;237.6;271.8;199.6;192.0;266.0;260.4;173.0;235.6;148.2;201.4;146.6;148.2;250.8;178.6;182.4;215.6;218.6;224.2;357.2;203.4;180.6;125.4;216.6;209.0;212.8;178.6;212.8;152.0;186.2;218.6;243.2;231.8;241.4;203.4;250.8;279.4;180.6;239.4;218.6;226.2;209.0;188.2;246.0;235.6;117.8;135.0;123.6;148.2;207.2;110.2;114.0;203.4;203.4;222.4;184.4;205.2;173.0;192.0;184.4;157.8;176.8;176.8;163.4;117.0;203.4;150.2;235.6;136.8;131.2;190.0;288.8;144.4;173.0;121.6;288.8;142.6;197.6;144.4;150.2;161.6;192.0;192.0;241.4;155.8;212.8;209.0;142.6;142.6;148.2;146.4;152.0;176.8;135.0;171.6;228.0;174.8;154.0;157.8;176.8;152.0;171.0;150.2;171.0;190.0;190.0;190.0;176.8;174.8;169.2;163.4;212.8;212.8;212.8;239.4;146.4;154.0;154.0;171.0;155.8;203.4;207.2;189.0;264.2;150.2;186.2;174.8;271.8;136.8;171.0;201.4;97.0;197.6;125.4;129.2;161.6;186.8;163.4;226.0;228.0;127.4;378.2;146.4;180.6;169.2;125.4;218.6;171.0;266.0;228.0;387.0;199.6;359.2;285.0;205.2;171.0;278.2;303.8;188.6;188.6;199.6;212.4;278.2;144.4;154.0;146.4;327.8;245.2;301.4;212.8;204.2;169.2;133.0;144.4;127.4;160.0;241.2;114.0;148.2;121.6;150.2;119.8;211.8;184.4;173.0;117.8;125.8;174.8;125.4;178.6;167.2;167.2;173.0;159.6;167.2;146.4;262.2;142.6;212.8;148.2;142.6;142.6;114.8;150.2;174.8;212.8;304.0;152.0;165.2;129.2;178.6;174.8;135.0;150.2;178.6;106.4;190.0;129.2;157.8;152.0;131.2;129.2;206.2;140.6;159.6;178.6;144.4;135.0;152.0;142.6;163.4;138.8;142.6;178.6;178.6;161.6;152.0;167.2;146.4;121.6;114.0;155.2;159.6;184.4;182.4;351.6;173.0;188.2;171.0;138.8;163.4;228.0;133.0;182.4;220.4;142.6;188.2;216.6;131.2;157.8;188.2;180.6;224.2;163.4;174.6;180.6;148.2;199.6;173.8;218.6;138.8;125.4;209.0;157.8;150.2;167.2;243.2;355.4;140.6;279.4;279.4;269.8;309.8;237.6;171.0;241.4;127.4;169.2;155.8;182.4;347.8;216.6;129.2;209.0;167.2;215.6;152.0;197.6;184.4;178.6;155.8;178.6;186.2;212.8;186.2;186.2;155.8;157.8;142.6;152.0;144.4;117.8;129.2;190.0;173.0;148.2;150.2;146.4;207.2;136.8;180.6;157.8;190.0;190.0;199.6;150.2;136.8;144.4;121.6;148.2;207.2;182.4;143.4;186.2;178.6;157.8;129.2;226.2;142.6;209.0;228.0;144.4;171.0;271.8;150.2;142.6;142.6;201.4;197.6;211.0;230.0;211.0;171.0;142.6;142.6;154.0;154.0;180.2;190.0;176.8;165.4;140.6;136.8;146.4;190.0;190.0;190.0;169.2;140.6;241.4;138.8;319.2;167.2;235.6;218.6;163.4;180.6;186.2;157.8;123.6;176.8;205.2;186.2;243.2;222.4;235.6;192.0;176.8;175.4;216.6;174.8;218.6;203.4;114.0;134.4;174.8;193.8;165.4;179.2;264.2;193.8;171.0;169.2;182.4;228.0;212.8;193.8;218.6;228.0;191.4;185.2;237.6;167.2;133.0;273.6;273.6;211.0;218.6;174.8;148.2;138.8;281.2;349.6;190.0;241.4;237.6;169.2;241.4;209.4;231.8;184.4;344.0;169.2;317.4;192.0;212.8;242.6;217.0;188.2;193.8;180.6;182.4;201.4;161.6;173.0;188.2;224.2;182.4;215.6;220.4;173.0;363.0;399.0;192.0;211.0;176.8;193.8;180.6;146.4;237.6;186.2;152.0;184.4;184.4;228.0;300.2;222.4;161.6;182.4;209.0;190.0;171.0;190.0;183.0;188.2;176.8;171.0;178.6;188.2;224.2;414.2;190.0;178.6;209.0;178.6;195.8;178.6;178.6;190.9;205.6;144.4;159.6;119.8;133.0;152.0;190.0;138.8;135.0;123.6;199.6;220.4;157.8;191.6;188.2;150.2;148.2;240.1;125.4;140.6;175.8;121.6;136.8;127.4;131.2;131.2;116.0;110.2;114.0;121.6;116.0;131.2;142.6;157.8;117.8;169.6;152.0;150.2;114.0;180.6;201.4;144.4;138.8;110.2;125.4;167.2;178.6;150.2;157.8;157.8;106.4;100.8;129.2;159.6;212.8;152.0;199.6;184.4;165.4;212.8;135.0;140.6;123.6;144.4;180.6;157.8;140.6;190.0;133.2;131.2;190.0;196.6;196.6;275.6;237.6;216.2;125.4;275.6;170.7;156.5;168.7;156.5";

    @Test
    public void testSeriesABreaks() throws ParseException {

        String[] splits = testSeriesA.split("\\|");

        ClusterOptimizer clusterOptimizer = new ClusterOptimizer(splits[0], splits[1], ";");

        Jenks.Breaks breaks = clusterOptimizer.optimize(0.15);
        Assertions.assertEquals(breaks.numClassses(), 9);
    }

    @Test
    public void setTestSeriesBBreaks() throws ParseException {
        String[] splits = testSeriesB.split("\\|");
        ClusterOptimizer clusterOptimizer = new ClusterOptimizer(splits[0], splits[1], ";");
        Jenks.Breaks breaks = clusterOptimizer.optimize(0.15);
        Assertions.assertEquals(breaks.numClassses(), 4);
    }
}
