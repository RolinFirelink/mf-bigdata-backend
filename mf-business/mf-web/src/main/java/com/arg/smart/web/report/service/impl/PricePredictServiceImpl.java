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
    public static final double UP_K = 0.1763;
    // 序列下降的斜率 k 值
    public static final double DOWN_K = -0.1763;
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
        return mapper.getWeekAveragePrice(flag, date, 2 * DEFAULT_WINDOW_SIZE);
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
            if (i < 0 || weekPrice.get(i) == null)
                continue;

            double sum = 0.0, newAvg = 0.0;
            int exactCount = 0;
            for (j = i - windowSize; j <= i; j++) {
                if (j < 0 || weekPrice.get(j) == null)
                    continue;
                sum += weekPrice.get(j).getAvgPrice().doubleValue();
                // 实际有值的数据个数, j 小于 0 的情况跳过, 直接除以 WindowSize 会导致预期数据变小
                exactCount++;
            }
            newAvg = sum / exactCount;
            ret.add(PricePredictVO.builder()
                    .product(weekPrice.get(0).getProduct())
                    .date(new Date(lastDate.getTime() + 1000L * 60 * 60 * count))
                    .avgPrice(new BigDecimal(newAvg))
                    .build());
            count++;
        }
        return ret;
    }

    @Override
    public Double getListK(List<PricePredictVO> list) {
        if (list.isEmpty() || list.size() < 2)
            return 0.0;

        double sum = 0.0;
        PricePredictVO now, prev;
        for (int i = 1; i < list.size(); i++) {
            now = list.get(i);
            prev = list.get(i - 1);
            sum += now.getAvgPrice().doubleValue() - prev.getAvgPrice().doubleValue();
        }
        return sum / list.size();
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
        // 计算后续一周的斜率
        double scope = this.getListK(nextWeekPrice);

        if (scope > UP_K)
            sb.append("，预计价格涨势上涨");
        else if (scope < DOWN_K)
            sb.append("，预计价格涨势下跌");
        else
            sb.append("，预计价格涨势平稳");

        return sb.toString();
    }

}
