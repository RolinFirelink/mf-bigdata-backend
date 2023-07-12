package com.arg.smart.web.product.service;

import com.arg.smart.web.product.entity.ProductPriceMonth;
import com.arg.smart.web.product.entity.vo.AvgPriceVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;


public interface ProductPriceMonthService extends IService<ProductPriceMonth> {
    List<AvgPriceVO> selectAvgPriceOfDate(LocalDate startTime, LocalDate endTime);

    List<AvgPriceVO> selectAvgPriceOfMonth(LocalDate startTime, LocalDate endTime);

    List<AvgPriceVO> selectAvgPriceOfHalfYear(LocalDate startTime, LocalDate endTime);

    List<AvgPriceVO> selectAvgPriceOfYear(LocalDate startTime, LocalDate endTime);
}
