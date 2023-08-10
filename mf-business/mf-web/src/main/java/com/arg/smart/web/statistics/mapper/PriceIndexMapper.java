package com.arg.smart.web.statistics.mapper;

import com.arg.smart.web.statistics.entity.PriceIndex;
import com.arg.smart.web.statistics.vo.BuyerPurchaseVO;
import com.arg.smart.web.statistics.vo.DateVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: 采购商价格指数
 * @author cgli
 * @date: 2023-08-09
 * @version: V1.0.0
 */
public interface PriceIndexMapper extends BaseMapper<PriceIndex> {

	/**
	 * 从 sh_product_price 中获取产品的平均价格, 如果没有记录这个产品, 则使用模块所有产品的平均价格
	 * @param flag 模块编号
	 * @param productName 产品名称
	 * @param year 年份
	 * @param month 月份
	 * @return BigDecimal
	 */
	BigDecimal selectProductMonthPrice(@Param("flag") Integer flag,
	                                   @Param("productName") String productName,
	                                   @Param("year") Integer year,
	                                   @Param("month") Integer month);

	/**
	 * 从 sh_market_num 中查询某年某月某模块所有采购商名称
	 * @param flag 模块编号
	 * @param year 年份
	 * @param month 月份
	 * @return List<String>
	 */
	List<String> selectBuyerName(@Param("flag") Integer flag,
	                             @Param("year") Integer year,
	                             @Param("month") Integer month);

	/**
	 * 从 sh_product_market_num 中查询一个采购商某年某月的采购数据
	 * @param flag 模块编号
	 * @param name 采购商名称
	 * @param year 年份
	 * @param month 月份
	 * @return List<BuyerPurchaseVO>
	 */
	List<BuyerPurchaseVO> selectBuyerPurchaseVo(@Param("flag") Integer flag,
												@Param("publisher") String name,
	                                            @Param("year") Integer year,
	                                            @Param("month") Integer month);

	/**
	 * 获取 sh_product_price 有记录的年月
	 * @return List<DateVO>
	 */
	List<DateVO> selectProductPriceDate();

	/**
	 * 获取 sh_product_market_num 有记录的年月
	 * @return List<DateVO>
	 */
	List<DateVO> selectMarketNumDate();

}
