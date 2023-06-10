package com.arg.smart.report.service.impl;

import com.arg.smart.report.entity.Report;
import com.arg.smart.report.entity.vo.ReportList;
import com.arg.smart.report.mapper.ReportMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.arg.smart.report.service.ReportService;

import java.util.*;

/**
 * @description: 报表页面
 * @author cgli
 * @date: 2023-05-31
 * @version: V1.0.0
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {


    @Override
    public List<ReportList> getReportList() {
        List<Report> list = this.list();
        Map<Integer, ReportList> map = new HashMap<>();
        for(int i = 1; i <= 6; i++){
            ReportList reportList = new ReportList();
            reportList.setCharts(new ArrayList<>());
            map.put(i,reportList);
        }
        list.forEach(item->{
            ReportList reportList = map.get(item.getFlag());
            reportList.getCharts().add(item);
            map.put(item.getFlag(),reportList);
        });
        map.get(1).setTitle("肉鸡云图");
        map.get(2).setTitle("柑橘云图");
        map.get(3).setTitle("兰花云图");
        map.get(4).setTitle("对虾云图");
        map.get(5).setTitle("菜心云图");
        map.get(6).setTitle("预制菜云图");
        return new ArrayList<>(map.values());
    }
}
