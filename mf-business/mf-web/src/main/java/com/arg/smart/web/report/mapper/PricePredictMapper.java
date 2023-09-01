package com.arg.smart.web.report.mapper;

import com.arg.smart.web.report.entity.vo.PricePredictVO;
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
     * 根据输入时间获取某模块在输入时间这一周每天所有产品的平均价格
     * @param flag 模块编号
     * @param date 指定日期
     * @return List<PricePredictVO>
     */
    List<PricePredictVO> getWeekAveragePrice(@Param("flag") Integer flag,
                                             @Param("date") Date date);

}
