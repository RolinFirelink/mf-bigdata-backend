package com.arg.smart.web.report.mapper;

import com.arg.smart.web.report.entity.vo.PricePredictVO;
import com.arg.smart.web.statistics.vo.BuyerPurchaseVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Author GlowingTree
 * @Description 价格预测 Mapper
 * @Date 8/24/2023 9:02 PM
 * @Version 1.0
 */
public interface PricePredictMapper extends BaseMapper<PricePredictVO> {

    /**
     * 获取某模块的最大记录日期
     * @param flag 模块编号
     * @return Date
     */
    Date getProductMaxDate(@Param("flag") Integer flag);

    /**
     * 根据输入时间获取某模块所有产品在距离输入时间前 n 天每天的平均价格
     * @param flag 模块编号
     * @param date 指定日期
     * @param interval 时间间隔 (单位: 天)
     * @return List<PricePredictVO>
     */
    List<PricePredictVO> getWeekAveragePrice(@Param("flag") Integer flag,
                                             @Param("date") Date date,
                                             @Param("interval") Integer interval);


    /**
     * 根据输入的时间获取某模块某产品在距离输入时间前 n 天每天的平均价格
     * @param flag 模块编号
     * @param name 产品名称
     * @param date 指定日期
     * @param interval 时间间隔 (单位: 天)
     * @return List<ProductWeekPriceVO>
     */
    List<PricePredictVO> getProductWeekAveragePrice(@Param("flag") Integer flag,
                                                    @Param("name") String name,
                                                    @Param("date") Date date,
                                                    @Param("interval") Integer interval);

    /**
     * 获取某模块所有产品名称
     * @param flag 模块编号
     * @param date 指定日期
     * @param interval 时间间隔 (单位: 天)
     * @return List<String>
     */
    List<String> getProductNames(@Param("flag") Integer flag,
                                 @Param("date") Date date,
                                 @Param("interval") Integer interval);

    /**
     * 获取 sh_product_market_nums 表的最大日期
     * @param flag 模块编号
     * @return Date
     */
    Date getProductMarketPriceMaxDate(@Param("flag") Integer flag);

    /**
     * 获取某模块某天前 n 天的所有采购商名称
     * @param flag 模块编号
     * @param date 指定日期
     * @param interval 时间间隔 (单位: 天)
     * @return List<String>
     */
    List<String> getBuyerName(@Param("flag") Integer flag,
                              @Param("date") Date date,
                              @Param("interval") Integer interval);

    /**
     * 从 sh_product_market_nums 表中查询某模块某采购商距离给定时间前 n 天的采购记录
     * @param flag 模块编号
     * @param date 指定日期
     * @param year 年份
     * @param month 月份
     * @param name 采购商名称
     * @param interval 时间间隔 (单位: 天)
     * @return List<PurchaseVO>
     */
    List<BuyerPurchaseVO> getBuyerPurchase(@Param("flag") Integer flag,
                                           @Param("date") Date date,
                                           @Param("year") Integer year,
                                           @Param("month") Integer month,
                                           @Param("name") String name,
                                           @Param("interval") Integer interval);

}
