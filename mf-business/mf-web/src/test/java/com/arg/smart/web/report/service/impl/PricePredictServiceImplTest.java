package com.arg.smart.web.report.service.impl;

import com.arg.smart.web.MfWebApplication;
import com.arg.smart.web.order.model.ModuleFlag;
import com.arg.smart.web.report.entity.vo.PricePredictVO;
import com.arg.smart.web.report.mapper.PricePredictMapper;
import com.arg.smart.web.report.service.PricePredictService;
import com.arg.smart.web.statistics.vo.BuyerPurchaseVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = MfWebApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class PricePredictServiceImplTest {

    @Resource
    private PricePredictService service;
    @Resource
    private PricePredictMapper mapper;

    @Test
    public void testGetPriceDescription() {
        String description = service.getPriceDescription(ModuleFlag.CHICKEN);
        log.info("Description: {}", description);
    }

    @Test
    public void testGetProductWeekPrice() {
        try {
            // 查询本日前两个周期的数据, 便于移动平均法计算，否则移动平均法无法获取第 1 天的前一个周期数据，无法准确预测
            int period = 5, window = 2 * period, flag = ModuleFlag.ORANGE;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // market_price 的最新日期
            Date date = mapper.getProductMaxDate(flag);
            // market_price 的上周日期
            Date lastWeekDate = new Date(date.getTime() - (long) 1000 * 60 * 60 * 24 * period);

            String flagName = ModuleFlag.FLAG_NAME.get(flag);
            StringBuilder sb = new StringBuilder();

            // 获取模块所有产品名称, 根据名称查询本周均价与预测均价
            List<String> names = mapper.getProductNames(flag, date, window);
            ArrayList<List<PricePredictVO>> weekPrices = new ArrayList<>();
            ArrayList<List<PricePredictVO>> nextWeekPrices = new ArrayList<>();
            names.forEach(n -> {
                List<PricePredictVO> productWeekPrices = mapper.getProductWeekAveragePrice(flag, n, date, window);
                weekPrices.add(productWeekPrices);
                nextWeekPrices.add(service.getNextWeekPredictPrice(productWeekPrices));
            });

            // 清除因需要预测数据而多查询的前第 2 个周期, 否则计算本周数据斜率时会误将上周的数据也带入计算, 导致斜率计算出错
            for (List<PricePredictVO> p : weekPrices) {
                Date last = p.get(p.size() - 1).getDate();
                Date start = new Date(last.getTime() - (long) 1000 * 60 * 60 * 24 * period);
                p.removeIf(vo -> vo.getDate().before(start));
            }

            // 进行每种产品本周价格走势分析
            double scope;
            int len = weekPrices.size(), up = 0, down = 0, steady = 0;
            for (List<PricePredictVO> w : weekPrices) {
                scope = service.getListK(w);
                if (Double.compare(scope, PricePredictServiceImpl.UP_K) > 0)
                    up++;
                else if (Double.compare(scope, PricePredictServiceImpl.DOWN_K) < 0)
                    down++;
                else
                    steady++;
            }

            // 产区供应
            // 本周价格评估
            double upAndDownPercent = (double) (up + down) / len;
            // 如果上涨和下跌的数量超过总数的 50% 则为震荡调整
            if (Double.compare(upAndDownPercent, 0.50) >= 0)
                sb.append(String.format("本周广东省%s价格震荡调整，", flagName));
            else
                sb.append(String.format("本周广东省%s价格平稳，", flagName));

            if (up > 0)
                sb.append(String.format("个别%s品种有所上涨，", flagName));
            else if (down > 0)
                sb.append(String.format("个别%s品种有所下降，", flagName));

            double upPercent = (double) up / len,
                downPercent = (double) down / len;
            if (Double.compare(upPercent, 0.50) >= 0)
                sb.append("总体保持上涨态势。");
            else if (Double.compare(downPercent, 0.50) >= 0)
                sb.append("总体保持下跌态势。");
            else
                sb.append("总体保持平稳。");

            // 降序排列每种产品的 k 值
            weekPrices.sort((x, y) -> {
                double k1 = service.getListK(x);
                double k2 = service.getListK(y);
                return Double.compare(k2, k1);
            });

            up = down = steady = -1;
            int slowUp = -1, slowDown = -1;
            // 获得持续上升, 小幅度上升, 稳定, 小幅度下降, 持续下降的品种在 list 中的索引, 方便生成每一种形式的报告
            for (int i = 0; i < weekPrices.size(); i++) {
                double k = service.getListK(weekPrices.get(i)) * 100.0;
                if (Double.compare(k, 10.0) > 0)
                    up = i;
                else if (Double.compare(k, 5.0) > 0)
                    slowUp = i;
                else if (Double.compare(k, -5.0) > 0)
                    steady = i;
                else if (Double.compare(k, -10.0) > 0)
                    slowDown = i;
                else if (Double.compare(k, -10.0) < 0)
                    down = i;
                // 不计算斜率无效的情况
            }
            // 每个品种的价格分析
            for (int i = 0; i < weekPrices.size(); i++) {
                if (i <= up) {
                    sb.append(String.format("%s", weekPrices.get(i).get(0).getProduct()));
                    if (i <= up - 1)
                        sb.append("、");
                    else if (i == up)
                        sb.append("本周内价格持续上涨；");
                } else if (i <= slowUp) {
                    sb.append(String.format("%s", weekPrices.get(i).get(0).getProduct()));
                    if (i <= slowUp - 1)
                        sb.append("、");
                    else if (i == slowUp)
                        sb.append("本周内价格小幅度上涨；");
                } else if (i <= steady) {
                    sb.append(String.format("%s", weekPrices.get(i).get(0).getProduct()));
                    if (i <= steady - 1)
                        sb.append("、");
                    else if (i == steady)
                        sb.append("本周内价格无明显变化；");
                } else if (i <= slowDown) {
                    sb.append(String.format("%s", weekPrices.get(i).get(0).getProduct()));
                    if (i <= slowDown - 1)
                        sb.append("、");
                    else if (i == slowDown)
                        sb.append("本周内价格出现小幅度下降；");
                } else {
                    // 环比对照, 本周和上一周的价格 (本周价格 - 上周价格) / 上周价格 * 100.0
                    List<PricePredictVO> lastWeekPrice = mapper.getWeekAveragePrice(flag, lastWeekDate, period);
                    double lastWeekAvg = 0.0, thisWeekAvg = 0.0;
                    for (PricePredictVO p : lastWeekPrice) {
                        lastWeekAvg += p.getAvgPrice().doubleValue();
                    }

                    List<PricePredictVO> thisWeekPrice = weekPrices.get(i);
                    for (PricePredictVO p : thisWeekPrice) {
                        thisWeekAvg += p.getAvgPrice().doubleValue();
                    }
                    double percent = (thisWeekAvg - lastWeekAvg) / lastWeekAvg * 100.0;

                    sb.append(String.format("%s本周内价格持续下降，均价降至%.2f%s，环比下降%.2f%%"
                                    , thisWeekPrice.get(0).getProduct()
                                    , thisWeekPrice.get(thisWeekPrice.size() - 1).getAvgPrice().doubleValue()
                                    , thisWeekPrice.get(0).getUnit()
                                    , Math.abs(percent))
                            );
                    if (i <= down - 1)
                        sb.append("、");
                    else if (i == down)
                        sb.append("；");
                }
            }

            // 销售流通
            // 查询本周的采购销售情况
            Date marketNumsMaxDate = mapper.getProductMarketPriceMaxDate(flag);
            Date lastWeekMarketNumsDate = new Date(marketNumsMaxDate.getTime() - 1000 * 60 * 60 * 24 * period);

            SimpleDateFormat year = new SimpleDateFormat("yyyy");
            SimpleDateFormat month = new SimpleDateFormat("MM");
            // 重新格式化日期为 yyyy-MM-dd 格式, 否则在 market_price 表无法查出数据, market_price 表的 record_time 是 date 类型
            marketNumsMaxDate = sdf.parse(sdf.format(marketNumsMaxDate));
            lastWeekMarketNumsDate = sdf.parse(sdf.format(lastWeekMarketNumsDate));

            // 获取所有采购商名称, 接着计算本周和上周的总采购量
            double total = 0.0, lastWeekTotal = 0.0;
            names = mapper.getBuyerName(flag, marketNumsMaxDate, period);
            for (String n : names) {
                // 查询采购商本周采购情况
                List<BuyerPurchaseVO> purchaseVOS = mapper.getBuyerPurchase(flag,
                        marketNumsMaxDate, Integer.valueOf(year.format(marketNumsMaxDate)),
                        Integer.valueOf(month.format(marketNumsMaxDate)), n, period);
                for (BuyerPurchaseVO p : purchaseVOS) {
                    if (p == null || p.getNum() == null)
                        continue;
                    total += p.getNum();
                }
                // 查询采购商上一周的采购情况
                List<BuyerPurchaseVO> p1 = mapper.getBuyerPurchase(flag, lastWeekMarketNumsDate,
                        Integer.valueOf(year.format(lastWeekMarketNumsDate)),
                        Integer.valueOf(month.format(lastWeekMarketNumsDate)), n, period);
                for (BuyerPurchaseVO p : p1) {
                    if (p == null || p.getNum() == null)
                        continue;
                    lastWeekTotal += p.getNum();
                }
            }
            // 计算销量环比：(本周销量 - 上周销量) / 上周销量 * 100%
            log.info("This Week Total Price: {}, Last Week Total Price: {}", total, lastWeekTotal);
            double pricePercent = (total - lastWeekTotal) / lastWeekTotal * 100.0;
            log.info("Sales Percent: {}", pricePercent);
            if (Double.compare(pricePercent, 30.0) > 0)
                sb.append(String.format("广东%s市场%s销量环比持续上升。", flagName, flagName));
            else if (Double.compare(pricePercent, 0.0) > 0)
                sb.append(String.format("广东%s市场%s销量环比小幅度上升。", flagName, flagName));
            else if (Double.compare(pricePercent, -30.0) > 0)
                sb.append(String.format("广东%s市场%s销量环比小幅下降。", flagName, flagName));
            else if (Double.compare(pricePercent, -30.0) < 0)
                sb.append(String.format("广东%s市场%s销量环比持续下降。", flagName, flagName));

            // 行情研判
            // 下周模块价格趋势, 计算下周的预测数据的斜率, 统计上涨, 下跌, 平稳的数量
            len = nextWeekPrices.size();
            up = down = steady = 0;
            for (List<PricePredictVO> p : nextWeekPrices) {
                double k = service.getListK(p) * 100.0;
                if (Double.compare(k, 10.0) > 0)
                    up++;
                else if (Double.compare(k, -10.0) > 0)
                    steady++;
                else if (Double.compare(k, -10.0) < 0)
                    down++;
            }

            upPercent = (double) up / len;
            downPercent = (double) down / len;
            // 上涨数量大于总数的 30% 即可认定为处于活跃态势, 否则处于平稳态势 或 下跌态势? 且是持续上升, 而 0 - 30% 区间属于略有上升
            if (Double.compare(upPercent, 0.0) > 0 && Double.compare(upPercent, 30.0) < 0)
                sb.append(String.format("预计下周%s市场价格略有上升", flagName));
            else if (Double.compare(upPercent, 30.0) > 0)
                sb.append(String.format("预计下周%s市场价格持续上升", flagName));
            else if (Double.compare(downPercent, 0.0) > 0 && Double.compare(downPercent, 30.0) < 0)
                sb.append(String.format("预计下周%s市场价格略有下跌", flagName));
            else if (Double.compare(downPercent, 30.0) > 0)
                sb.append(String.format("预计下周%s市场价格持续下跌", flagName));
            else
                sb.append(String.format("预计下周%s市场价格保持平稳", flagName));

            // 态势评定
            if (Double.compare(upPercent, 30.0) > 0)
                sb.append("，总体处于活跃态势");
            else
                sb.append("，总体处于平稳态势");

            log.info("String: {}", sb.toString());

        } catch (ParseException e) {
            e.printStackTrace();
            log.error("Error On Get Production And Sales Situation, Error: {}", e.getMessage());
        }
    }

}