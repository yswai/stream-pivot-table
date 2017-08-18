package com.swyep.report.pivot.model;

import java.util.SortedSet;

/**
 * Created by ysw on 8/18/2017.
 */
public class PivotReportResultRow extends CallActivity {
    private SortedSet<PivotCountBySource> pivotCountBySources;
    private Long distinctCount;

    public PivotReportResultRow() {
    }

    public PivotReportResultRow(String callerMsisdn, String[] callerIsTargets, String calledMsisdn, String[] calledIsTargets, String source, Long duration, SortedSet<PivotCountBySource> pivotCountBySources, Long distinctCount) {
        super(callerMsisdn, callerIsTargets, calledMsisdn, calledIsTargets, source, duration);
        this.pivotCountBySources = pivotCountBySources;
        this.distinctCount = distinctCount;
    }

    public SortedSet<PivotCountBySource> getPivotCountBySources() {
        return pivotCountBySources;
    }

    public void setPivotCountBySources(SortedSet<PivotCountBySource> pivotCountBySources) {
        this.pivotCountBySources = pivotCountBySources;
    }

    public Long getDistinctCount() {
        return distinctCount;
    }

    public void setDistinctCount(Long distinctCount) {
        this.distinctCount = distinctCount;
    }
}
