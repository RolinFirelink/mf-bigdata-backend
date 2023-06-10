package com.arg.smart.report.entity.vo;

import com.arg.smart.report.entity.Report;
import lombok.Data;

import java.util.List;

@Data
public class ReportList {

    private String title;
    private List<Report> charts;
}
