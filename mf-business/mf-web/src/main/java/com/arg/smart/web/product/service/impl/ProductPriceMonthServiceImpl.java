package com.arg.smart.web.product.service.impl;

import com.arg.smart.web.product.entity.ProductPriceMonth;
import com.arg.smart.web.product.entity.vo.AvgPriceVO;
import com.arg.smart.web.product.mapper.ProductPriceMonthMapper;
import com.arg.smart.web.product.service.ProductPriceMonthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductPriceMonthServiceImpl extends ServiceImpl<ProductPriceMonthMapper, ProductPriceMonth> implements ProductPriceMonthService {
    @Override
    public List<AvgPriceVO> selectAvgPriceOfDate(LocalDate startTime, LocalDate endTime, String region,String product) {
        return baseMapper.selectAvgPriceOfDate(startTime.toString(), endTime.toString(),region,product);
    }

    @Override
    public List<AvgPriceVO> selectAvgPriceOfMonth(LocalDate startTime, LocalDate endTime,String region,String product) {
        return baseMapper.selectAvgPriceOfMonth(startTime.toString(), endTime.toString(),region,product);
    }

    @Override
    public List<AvgPriceVO> selectAvgPriceOfHalfYear(LocalDate startTime, LocalDate endTime,String region,String product) {
        return baseMapper.selectAvgPriceOfHalfYear(startTime.toString(), endTime.toString(),region,product);
    }

    @Override
    public List<AvgPriceVO> selectAvgPriceOfYear(LocalDate startTime, LocalDate endTime,String region,String product) {
        return baseMapper.selectAvgPriceOfYear(startTime.toString(), endTime.toString(),region,product);
    }
}
