package com.arg.smart.web.report.mapper;

import com.arg.smart.web.report.entity.GoodAnalyse;
import com.arg.smart.web.report.entity.vo.CustomerEvaluationVO;
import com.arg.smart.web.report.entity.vo.SupplyAndDemandVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author GlowingTree
 * @Description 农产品竞品分析 Mapper
 * @Date 8/24/2023 3:43 PM
 * @Version 1.0
 */
public interface GoodAnalyseMapper extends BaseMapper<GoodAnalyse> {

	/**
	 * 查询某种产品是否在某个某块内
	 * @param name 产品名称
	 * @return Long
	 */
	Integer checkGoodInModule(@Param("name") String name);

	/**
	 * 获取某年某月某模块某产品供需关系
	 * @param flag 模块编号
	 * @param name 产品名称
	 * @param year 年份
	 * @param month 月份
	 * @return SupplyAndDemandVO
	 */
	SupplyAndDemandVO getSupplyAndDemandInfo(@Param("flag") Integer flag,
											 @Param("name") String name,
											 @Param("year") Integer year,
											 @Param("month") Integer month);

	/**
	 * 获取某年某月某模块某产品的消费者评价
	 * @param flag 模块编号
	 * @param name 产品名称
	 * @param year 年份
	 * @param month 月份
	 * @return List<CustomerEvaluationVO>
	 */
	List<CustomerEvaluationVO> getProductCustomerEvaluation(@Param("flag") Integer flag,
															@Param("name") String name,
															@Param("year") Integer year,
															@Param("month") Integer month);

}
