package com.swyep.report.pivot.model;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by ysw on 8/18/2017.
 */
public class PivotReportResultRow extends CallActivity {
    private Set<PivotCountBySource> pivotCountBySources = new LinkedHashSet<>();
    private Long distinctCount;

    public PivotReportResultRow() {
    }

    public Set<PivotCountBySource> getPivotCountBySources() {
        return pivotCountBySources;
    }

    public void setPivotCountBySources(Set<PivotCountBySource> pivotCountBySources) {
        this.pivotCountBySources = pivotCountBySources;
    }

    public Long getDistinctCount() {
        return distinctCount;
    }

    public void setDistinctCount(Long distinctCount) {
        this.distinctCount = distinctCount;
    }
}
