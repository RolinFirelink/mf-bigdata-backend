package com.arg.smart.report.service;

import com.arg.smart.report.entity.Report;
import com.arg.smart.report.entity.vo.ReportList;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 报表页面
 * @author cgli
 * @date: 2023-05-31
 * @version: V1.0.0
 */
public interface ReportService extends IService<Report> {

    List<ReportList> getReportList();
}
