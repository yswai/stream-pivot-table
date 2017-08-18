package com.swyep.report.pivot.model;

/**
 * Created by ysw on 8/18/2017.
 */
public class PivotCountBySource {
    private String source;
    private Long count;

    public PivotCountBySource(String source, Long count) {
        this.source = source;
        this.count = count;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
