package com.arg.smart.web.report.service;

import com.arg.smart.web.report.entity.vo.PricePredictVO;

import java.util.Date;
import java.util.List;

/**
 * @Author GlowingTree
 * @Description 价格预测接口
 * @Date 09/01/2023 1:43 PM
 * @Version 1.0
 */
public interface PricePredictService {

    /**
     * 根据输入时间获取某模块在输入时间这一周每天所有产品的平均价格
     * @param flag 模块编号
     * @param date 指定日期
     * @return List<PricePredictVO>
     */
    List<PricePredictVO> getWeekAveragePrice(Integer flag, Date date);

    /**
     * 获取序列斜率
     * @param list 序列
     * @return Double
     */
    Double getListK(List<PricePredictVO> list);

    /**
     * 获取价格描述
     * @param flag 模块编号
     * @return String
     */
    String getPriceDescription(Integer flag);

    /**
     * 使用移动平均法获取给定的价格序列下一周的预测序列
     * @param weekPrice 给定的价格序列
     * @return List<PricePredictVO>
     */
    List<PricePredictVO> getNextWeekPredictPrice(List<PricePredictVO> weekPrice);

}
