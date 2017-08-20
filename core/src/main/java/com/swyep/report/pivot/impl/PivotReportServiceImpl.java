package com.swyep.report.pivot.impl;

import com.swyep.report.pivot.PivotReportService;
import com.swyep.report.pivot.model.CallActivity;
import com.swyep.report.pivot.model.PivotCountBySource;
import com.swyep.report.pivot.model.PivotReportResultRow;

import java.util.*;

import static java.util.stream.Collectors.*;

public class PivotReportServiceImpl implements PivotReportService {

    @Override
    public Set<PivotReportResultRow> getReport(Iterator<CallActivity> iterator) {
        List<CallActivity> callActivities = new ArrayList<>();
        iterator.forEachRemaining(callActivities::add);
        return getReport(callActivities);
    }

    public Set<PivotReportResultRow> getReport(Collection<CallActivity> inputList) {
        List<CallActivity> filtered = inputList.stream()
                .filter(c -> c.getCalledIsTargets().length > 0 || c.getCallerIsTargets().length > 0)
                .collect(toList());
//        List<CallActivity> distincted = filtered.stream().distinct().collect(toList());
        List<CallActivity> distincted = filtered.stream()
                .collect(groupingBy(c -> new CallerCalledCallDateKey(c.getCallerMsisdn(), c.getCalledMsisdn(), c.getCallDate()), toSet()))
                .entrySet().stream().map(e -> e.getValue().stream().findFirst().get()).collect(toList());
        Map<CallerCalledCallDateKey, Long> masterCountingGroup = distincted.stream()
                .collect(groupingBy(c -> new CallerCalledCallDateKey(c.getCallerMsisdn(), c.getCalledMsisdn(), c.getCallDate()), counting()));
        Set<PivotReportResultRow> masterPivotTable = getMasterPivotTable(filtered, masterCountingGroup);
        Map<CallerCalledSourceKey, Long> pivotCountingGroup = filtered.stream()
                .collect(groupingBy(c -> new CallerCalledSourceKey(c.getCallerMsisdn(), c.getCalledMsisdn(), c.getSource()), counting()));
//        Set<String> sources = pivotCountingGroup.keySet().stream().map(k -> k.getSource()).distinct().collect(toSet());
        mergeResults(masterPivotTable, pivotCountingGroup);
        return masterPivotTable.stream().sorted(Comparator.comparing(PivotReportResultRow::getDistinctCount).reversed()).collect(toSet());
    }

    private Set<PivotReportResultRow> getMasterPivotTable(Collection<CallActivity> callActivities, Map<CallerCalledCallDateKey, Long> masterCountingGroup) {
        Map<CallerCalledCallDateKey, Set<CallActivity>> masterPivotTable = callActivities.stream()
                .collect(groupingBy(c -> new CallerCalledCallDateKey(c.getCallerMsisdn(), c.getCalledMsisdn(), c.getCallDate()), toSet()));
        return masterPivotTable.entrySet().stream().map(e -> {
            CallerCalledCallDateKey key = e.getKey();
            Optional<CallActivity> callActivity = e.getValue().stream().findFirst();
            Optional<Map.Entry<CallerCalledCallDateKey, Long>> countEntry = masterCountingGroup.entrySet().stream().filter(e2 -> {
                CallerCalledCallDateKey matchingKey = new CallerCalledCallDateKey(e2.getKey().getCaller(), e2.getKey().getCalled(), e2.getKey().getCallDate());
                return matchingKey.equals(key);
            }).findFirst();
            // Transformer
            String caller = key.getCaller();
            String called = key.getCalled();
            PivotReportResultRow pivotReportResultRow = new PivotReportResultRow();
            pivotReportResultRow.setCallerMsisdn(caller);
            pivotReportResultRow.setCalledMsisdn(called);
            callActivity.ifPresent(c -> {
                pivotReportResultRow.setSource(c.getSource());
                pivotReportResultRow.setCallerIsTargets(c.getCallerIsTargets());
                pivotReportResultRow.setCalledIsTargets(c.getCalledIsTargets());
            });
            countEntry.ifPresent(e3 -> pivotReportResultRow.setDistinctCount(e3.getValue()));
            return pivotReportResultRow;
        }).collect(toSet());
    }

    private void mergeResults(Set<PivotReportResultRow> masterPivotTable, Map<CallerCalledSourceKey, Long> pivotCountingGroup) {
        pivotCountingGroup.entrySet().stream().forEach(e -> {
            CallerCalledSourceKey callerCalledSourceKey = e.getKey();
            Optional<PivotReportResultRow> matchingRow = masterPivotTable.stream()
                    .filter(r -> r.getCallerMsisdn() != null && r.getCalledMsisdn() != null
                            && r.getCalledMsisdn().equals(callerCalledSourceKey.getCalled())
                            && r.getCallerMsisdn().equals(callerCalledSourceKey.getCaller()))
                    .findFirst();
            matchingRow.ifPresent(m -> {
                PivotCountBySource pivotCountBySource = new PivotCountBySource();
                pivotCountBySource.setSource(callerCalledSourceKey.getSource());
                pivotCountBySource.setCount(e.getValue());
                m.getPivotCountBySources().add(pivotCountBySource);
            });
        });
    }

    private static final class CallerCalledSourceKey {
        private String caller;
        private String called;
        private String source;

        public CallerCalledSourceKey(String caller, String called, String source) {
            this.caller = caller;
            this.called = called;
            this.source = source;
        }

        public String getCaller() {
            return caller;
        }

        public void setCaller(String caller) {
            this.caller = caller;
        }

        public String getCalled() {
            return called;
        }

        public void setCalled(String called) {
            this.called = called;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CallerCalledSourceKey that = (CallerCalledSourceKey) o;

            if (getCaller() != null ? !getCaller().equals(that.getCaller()) : that.getCaller() != null) return false;
            if (getCalled() != null ? !getCalled().equals(that.getCalled()) : that.getCalled() != null) return false;
            return getSource() != null ? getSource().equals(that.getSource()) : that.getSource() == null;
        }

        @Override
        public int hashCode() {
            int result = getCaller() != null ? getCaller().hashCode() : 0;
            result = 31 * result + (getCalled() != null ? getCalled().hashCode() : 0);
            result = 31 * result + (getSource() != null ? getSource().hashCode() : 0);
            return result;
        }
    }

    private static final class CallerCalledCallDateKey {
        private String caller;
        private String called;
        private Date callDate;

        public CallerCalledCallDateKey() {
        }

        public CallerCalledCallDateKey(String caller, String called, Date callDate) {
            this.caller = caller;
            this.called = called;
            this.callDate = callDate;
        }

        public String getCaller() {
            return caller;
        }

        public String getCalled() {
            return called;
        }

        public Date getCallDate() {
            return callDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CallerCalledCallDateKey that = (CallerCalledCallDateKey) o;

            if (getCaller() != null ? !getCaller().equals(that.getCaller()) : that.getCaller() != null) return false;
            if (getCalled() != null ? !getCalled().equals(that.getCalled()) : that.getCalled() != null) return false;
            return callDate != null ? callDate.equals(that.callDate) : that.callDate == null;
        }

        @Override
        public int hashCode() {
            int result = getCaller() != null ? getCaller().hashCode() : 0;
            result = 31 * result + (getCalled() != null ? getCalled().hashCode() : 0);
            result = 31 * result + (callDate != null ? callDate.hashCode() : 0);
            return result;
        }
    }

}
