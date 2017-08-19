package com.swyep.report.pivot;

import com.swyep.report.pivot.model.CallActivity;
import com.swyep.report.pivot.model.PivotReportResultRow;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by ysw on 8/18/2017.
 */
public interface PivotReportService {

    Set<PivotReportResultRow> getReport(Iterator<CallActivity> iterator);
    Set<PivotReportResultRow> getReport(Collection<CallActivity> inputList);

}
