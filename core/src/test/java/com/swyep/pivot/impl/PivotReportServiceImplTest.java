package com.swyep.pivot.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.swyep.report.pivot.PivotReportService;
import com.swyep.report.pivot.impl.PivotReportServiceImpl;
import com.swyep.report.pivot.model.CallActivity;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by ysw on 8/18/2017.
 */
public class PivotReportServiceImplTest {

    private PivotReportService pivotReportService;
    private ObjectMapper mapper = (new ObjectMapper()).enable(SerializationFeature.INDENT_OUTPUT);
    private List<CallActivity> mockData;

    public PivotReportServiceImplTest() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("data.json");
        pivotReportService = new PivotReportServiceImpl();
        mockData = mapper.readValue(is, new TypeReference<List<CallActivity>>(){});
    }

    @Test
    public void generateReportTest() throws IOException {
        mapper.writeValue(System.out, pivotReportService.getReport(mockData));
    }

}
