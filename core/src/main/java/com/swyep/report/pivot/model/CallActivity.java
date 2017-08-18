package com.swyep.report.pivot.model;

import java.util.Date;

/**
 * Created by ysw on 8/18/2017.
 */
public class CallActivity {

    private String callerMsisdn;
    private String[] callerIsTargets = new String[] {};
    private String calledMsisdn;
    private String[] calledIsTargets = new String[] {};
    private String source;
    private Long duration;
    private Date callDate = new Date();

    public CallActivity() {
    }

    public CallActivity(String callerMsisdn, String[] callerIsTargets, String calledMsisdn, String[] calledIsTargets, String source, Long duration, Date callDate) {
        this.callerMsisdn = callerMsisdn;
        this.callerIsTargets = callerIsTargets;
        this.calledMsisdn = calledMsisdn;
        this.calledIsTargets = calledIsTargets;
        this.source = source;
        this.duration = duration;
        this.callDate = callDate;
    }

    public String getCallerMsisdn() {
        return callerMsisdn;
    }

    public void setCallerMsisdn(String callerMsisdn) {
        this.callerMsisdn = callerMsisdn;
    }

    public String getCalledMsisdn() {
        return calledMsisdn;
    }

    public void setCalledMsisdn(String calledMsisdn) {
        this.calledMsisdn = calledMsisdn;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String[] getCallerIsTargets() {
        return callerIsTargets;
    }

    public void setCallerIsTargets(String[] callerIsTargets) {
        this.callerIsTargets = callerIsTargets;
    }

    public String[] getCalledIsTargets() {
        return calledIsTargets;
    }

    public void setCalledIsTargets(String[] calledIsTargets) {
        this.calledIsTargets = calledIsTargets;
    }

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CallActivity that = (CallActivity) o;

        if (getCallerMsisdn() != null ? !getCallerMsisdn().equals(that.getCallerMsisdn()) : that.getCallerMsisdn() != null)
            return false;
        if (getCalledMsisdn() != null ? !getCalledMsisdn().equals(that.getCalledMsisdn()) : that.getCalledMsisdn() != null)
            return false;
        if (getDuration() != null ? !getDuration().equals(that.getDuration()) : that.getDuration() != null)
            return false;
        return getCallDate() != null ? getCallDate().equals(that.getCallDate()) : that.getCallDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getCallerMsisdn() != null ? getCallerMsisdn().hashCode() : 0;
        result = 31 * result + (getCalledMsisdn() != null ? getCalledMsisdn().hashCode() : 0);
        result = 31 * result + (getDuration() != null ? getDuration().hashCode() : 0);
        result = 31 * result + (getCallDate() != null ? getCallDate().hashCode() : 0);
        return result;
    }
}
