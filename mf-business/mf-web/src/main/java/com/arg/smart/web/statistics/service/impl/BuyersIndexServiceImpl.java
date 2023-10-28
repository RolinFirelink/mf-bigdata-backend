package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.docking.entity.InquiryData;
import com.arg.smart.web.docking.entity.InquiryDataGuangdong;
import com.arg.smart.web.docking.service.InquiryDataGuangdongService;
import com.arg.smart.web.docking.service.InquiryDataService;
import com.arg.smart.web.product.entity.ProductPrice;
import com.arg.smart.web.product.service.ProductPriceService;
import com.arg.smart.web.statistics.entity.BuyersIndex;
import com.arg.smart.web.statistics.mapper.BuyersIndexMapper;
import com.arg.smart.web.statistics.req.ReqBuyersIndex;
import com.arg.smart.web.statistics.service.BuyersIndexService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 采购商指数
 * @date: 2023-07-16
 * @version: V1.0.0
 */
@Service
@Slf4j
public class BuyersIndexServiceImpl extends ServiceImpl<BuyersIndexMapper, BuyersIndex> implements BuyersIndexService {

    @Resource
    private BuyersIndexMapper buyersIndexMapper;

    @Resource
    private InquiryDataGuangdongService inquiryDataGuangdongService;

    @Resource
    private ProductPriceService productPriceService;

    @Override
    public List<BuyersIndex> list(ReqBuyersIndex reqBuyersIndex) {
        QueryWrapper<BuyersIndex> queryWrapper = new QueryWrapper<>();
        Date startTime = reqBuyersIndex.getStartTime();
        Date endTime = reqBuyersIndex.getEndTime();
        queryWrapper.ge(startTime != null, "statistical_time", startTime)
                .le(endTime != null, "statistical_time", endTime);
        Integer flag = reqBuyersIndex.getFlag();
        queryWrapper.eq("flag", flag);
        queryWrapper.orderByDesc("year").orderByDesc("month");
        return this.list(queryWrapper);
    }

    @Override
    public PageResult<BuyersIndex> listPage(ReqBuyersIndex reqBuyersIndex) {
        LambdaQueryWrapper<BuyersIndex> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(reqBuyersIndex.getFlag() != null, BuyersIndex::getFlag, reqBuyersIndex.getFlag())
                .eq(reqBuyersIndex.getYear() != null, BuyersIndex::getYear, reqBuyersIndex.getYear())
                .eq(reqBuyersIndex.getMonth() != null, BuyersIndex::getMonth, reqBuyersIndex.getMonth());
        return new PageResult<>(this.list(queryWrapper));
    }

    @Transactional
    @Override
    public List<BuyersIndex> updateData(ReqBuyersIndex reqBuyersIndex) {
        Integer flag = reqBuyersIndex.getFlag();
        //查询采购量
        QueryWrapper<InquiryDataGuangdong> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("flag", flag).groupBy("date").select("date", "sum(cnt_ratio) as cnt_ratio");
        //采购量占比
        List<InquiryDataGuangdong> list = inquiryDataGuangdongService.list(queryWrapper);
        //转为map
        Map<String, InquiryDataGuangdong> resultMap = list.stream()
                .collect(Collectors.toMap(
                        InquiryDataGuangdong::getDate, // 以 date 字段作为键
                        data -> data // 数据对象作为值
                ));
        //获取每月价格
        QueryWrapper<ProductPrice> productPriceQueryWrapper = new QueryWrapper<>();
        productPriceQueryWrapper.eq("flag", flag).select("DATE_FORMAT(time, '%Y-%m') as month", "avg(price) as price").groupBy("month");
        List<Map<String, Object>> priceList = productPriceService.listMaps(productPriceQueryWrapper);
        // 使用Java Stream API将列表转换为Map
        Map<String, BigDecimal> priceMap = priceList.stream()
                .collect(Collectors.toMap(
                        entry -> (String) entry.get("month"),
                        entry -> (BigDecimal) entry.get("price")
                ));
        //计算
        List<BuyersIndex> buyIndexList = new ArrayList<>();

        for (Map.Entry<String, InquiryDataGuangdong> entry : resultMap.entrySet()) {
            String month = entry.getKey();
            InquiryDataGuangdong data = entry.getValue();
            // 获取价格
            BigDecimal price = priceMap.get(month);
            // 获取上个月的月份
            String previousMonth = YearMonth.parse(month, DateTimeFormatter.ofPattern("yyyy-MM"))
                    .minusMonths(1)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM"));
            //获取上月的采购量和价格
            BigDecimal bigDecimal = priceMap.get(previousMonth);
            InquiryDataGuangdong previousBuyRatio = resultMap.get(previousMonth);
            //计算
            BuyersIndex buyersIndex = new BuyersIndex();

            // 计算占比标准化
            Double buyRatioStandardized = 0.0;
            if (data != null && previousBuyRatio != null) {  // 修正这一行，应该检查previousInquiryData而不是previousBuyRatio
                Double cntRatio1 = data.getCntRatio();
                Double cntRatio = previousBuyRatio.getCntRatio();
                buyRatioStandardized = (cntRatio1 - cntRatio) / cntRatio;
            }

            // 计算价格标准化
            BigDecimal priceStandardized = BigDecimal.ZERO;
            if (price != null && previousBuyRatio != null) {
                BigDecimal previousPrice = priceMap.get(previousMonth);
                if (previousPrice != null) {
                    priceStandardized = price.subtract(previousPrice)
                            .divide(previousPrice, 2, RoundingMode.HALF_UP);
                }
            }

            // 计算指数
            BigDecimal index = BigDecimal.valueOf(100.0)
                    .add(BigDecimal.valueOf(buyRatioStandardized * 10))
                    .add((priceStandardized.multiply(new BigDecimal(10))));

            buyersIndex.setTemp(index);

            // 使用日期格式化解析字符串
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            YearMonth yearMonth = YearMonth.parse(month, formatter);

            // 现在你可以将 year 和 month 设置到相应的字段中
            buyersIndex.setYear(yearMonth.getYear());
            buyersIndex.setMonth(yearMonth.getMonthValue());
            buyersIndex.setFlag(flag);
            buyersIndex.setStatisticalTime(new Date());
            buyIndexList.add(buyersIndex);
        }
        this.saveBatch(buyIndexList);
        return buyIndexList;
    }

}
