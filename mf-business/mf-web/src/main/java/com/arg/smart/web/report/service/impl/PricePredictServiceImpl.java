package com.arg.smart.web.report.service.impl;

import com.arg.smart.web.order.model.ModuleFlag;
import com.arg.smart.web.report.entity.vo.PricePredictVO;
import com.arg.smart.web.report.mapper.PricePredictMapper;
import com.arg.smart.web.report.service.PricePredictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Author GlowingTree
 * @Description 价格预测实现类
 * @Date 09/01/2023 1:44 PM
 * @Version 1.0
 */
@Service
@Slf4j
public class PricePredictServiceImpl extends ServiceImpl<PricePredictMapper, PricePredictVO> implements PricePredictService {

    // 序列上升的斜率 k 值
        private static final double UP_K = 0.1763;
    // 序列下降的斜率 k 值
    private static final double DOWN_K = -0.1763;
    // 默认窗口大小
    private static final int DEFAULT_WINDOW_SIZE = 5;

    @PostConstruct
    private void postConstructMethod() {
        ModuleFlag.initFlagNameMap();
    }

    @Resource
    private PricePredictMapper mapper;

    @Override
    public List<PricePredictVO> getWeekAveragePrice(Integer flag, Date date) {
        return mapper.getWeekAveragePrice(flag, date);
    }

    @Override
    public List<PricePredictVO> getNextWeekPredictPrice(List<PricePredictVO> weekPrice) {
        if (weekPrice.isEmpty())
            return Collections.emptyList();
        int len = weekPrice.size();

        // 移动平均法预测某模块后一周每日的平均价格
        ArrayList<PricePredictVO> ret = new ArrayList<>();
        // 获取记录的最后一天的日期
        Date lastDate = weekPrice.get(len - 1).getDate();

        // 使用过去 5 天数据来计算移动平均, 如果窗口大小大于给定数据长度, 则缩小窗口大小
        int count = 1, i, j, windowSize = DEFAULT_WINDOW_SIZE;
        while (windowSize > len)
            windowSize /= 2;

        for (i = weekPrice.size() - windowSize; i < weekPrice.size(); i++) {
            double sum = 0.0, newAvg = 0.0;
            for (j = i - windowSize; j < i; j++) {
                if (j < 0)
                    continue;
                sum += weekPrice.get(j).getAvgPrice().doubleValue();
            }
            newAvg = sum / windowSize;
            ret.add(PricePredictVO.builder()
                    .date(new Date(lastDate.getTime() + 1000L * 60 * 60 * count))
                    .avgPrice(new BigDecimal(newAvg))
                    .build());
            count++;
        }
        return ret;
    }

    @Override
    public String getPriceDescription(Integer flag) {
        // 获取数据库中记录的某模块最大日期
        Date maxDate = mapper.getProductMaxDate(flag);
        // 上一周的价格

        List<PricePredictVO> prevWeekPrice = this.getWeekAveragePrice(flag, maxDate);
        if (prevWeekPrice.isEmpty())
            return String.format("%s暂无价格与价格涨势预测数据", ModuleFlag.FLAG_NAME.get(flag));

        PricePredictVO weekMaxDate = prevWeekPrice.get(prevWeekPrice.size() - 1);
        PricePredictVO weekSecondMaxDate = null;
        if (prevWeekPrice.size() >= 2)
            weekSecondMaxDate = prevWeekPrice.get(prevWeekPrice.size() - 2);

        int compareResult;
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月dd日");

        sb.append(String.format("%s：%s价格%.2f元",
                sdf.format(weekMaxDate.getDate()),
                ModuleFlag.FLAG_NAME.get(flag),
                weekMaxDate.getAvgPrice().doubleValue()));
        if (weekSecondMaxDate != null) {
            double weekMaxDatePrice = weekMaxDate.getAvgPrice().doubleValue(),
                weekSecondMaxDatePrice = weekSecondMaxDate.getAvgPrice().doubleValue(),
                percent;
            compareResult = Double.compare(weekMaxDatePrice, weekSecondMaxDatePrice);
            if (compareResult > 0) {
                percent = (weekMaxDatePrice - weekSecondMaxDatePrice) / weekSecondMaxDatePrice;
                sb.append(String.format("，对比昨日上涨%.2f%%", percent * 100.0));
            } else if (compareResult < 0) {
                percent = (weekSecondMaxDatePrice - weekMaxDatePrice) / weekSecondMaxDatePrice;
                sb.append(String.format("，对比昨日下跌%.2f%%", percent * 100.0));
            } else {
                sb.append("，与昨日价格持平");
            }
        }

        // 后续一周的价格
        List<PricePredictVO> nextWeekPrice = this.getNextWeekPredictPrice(prevWeekPrice);
        log.info("Next Week Price VO: {}", nextWeekPrice);
        // 计算后续一周的斜率
        double scope = 0.0;
        PricePredictVO nowPrice, prevPrice;
        for (int i = 1; i < nextWeekPrice.size(); i++) {
            nowPrice = nextWeekPrice.get(i);
            prevPrice = nextWeekPrice.get(i - 1);
            scope += nowPrice.getAvgPrice().doubleValue() - prevPrice.getAvgPrice().doubleValue();
        }
        scope /= nextWeekPrice.size();
        log.info("Next Week Scope: {}, Up Scope Threshold: {}, Down Scope Threshold: {}", scope, UP_K, DOWN_K);
        if (scope > UP_K)
            sb.append("，预计价格涨势上涨");
        else if (scope < DOWN_K)
            sb.append("，预计价格涨势下跌");
        else
            sb.append("，预计价格涨势平稳");

        return sb.toString();
    }

}
