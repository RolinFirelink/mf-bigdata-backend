package com.arg.smart.web.report.mapper;

import com.arg.smart.web.report.entity.ProduceAndSale;
import com.arg.smart.web.report.entity.vo.BaseProductVO;
import com.arg.smart.web.report.entity.vo.CirculationVO;
import com.arg.smart.web.report.entity.vo.PurchaseVO;
import com.arg.smart.web.report.entity.vo.SupplyAndDemandVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author GlowingTree
 * @Description 农产品产销形式分析 Mapper
 * @Date 8/24/2023 3:43 PM
 * @Version 1.0
 */
public interface ProductAndSaleMapper extends BaseMapper<ProduceAndSale> {

	/**
	 * 获取今年某模块的总产量
	 * @param flag 模块编号
	 * @param year 年份
	 * @param month 月份
	 * @return Double
	 */
	BigDecimal getProduceAmount(@Param("flag") Integer flag, @Param("year") Integer year, @Param("month") Integer month);

	/**
	 * 获取今年某模块基地数量
	 * @param flag 模块编号
	 * @param year 年份
	 * @param month 月份
	 * @return Integer
	 */
	Integer getBaseNum(@Param("flag") Integer flag, @Param("year") Integer year, @Param("month") Integer month);

	/**
	 * 获取某年某月某模块所有基地的生产量
	 * @param flag 模块编号
	 * @param year 年份
	 * @param month 月份
	 * @return List<BaseProductVO>
	 */
	List<BaseProductVO> getBaseProductAmount(@Param("flag") Integer flag, @Param("year") Integer year, @Param("month") Integer month);

	/**
	 * 获取某年某月某模块所有采购商数量
	 * @param flag 模块编号
	 * @param year 年份
	 * @param month 月份
	 * @return Integer
	 */
	Integer getBuyerNum(@Param("flag") Integer flag, @Param("year") Integer year, @Param("month") Integer month);

	/**
	 * 获取某年某月某模块所有采购商发布的采购总数
	 * @param flag 模块编号
	 * @param year 年份
	 * @param month 月份
	 * @return Double
	 */
	Double getBuyerPurchaseTotal(@Param("flag") Integer flag, @Param("year") Integer year, @Param("month") Integer month);

	/**
	 * 获取某年某月某模块货物运送情况
	 * @param flag 模块编号
	 * @param year 年份
	 * @param month 月份
	 * @return List<CirculationVO>
	 */
	List<CirculationVO> getCirculationAmount(@Param("flag") Integer flag, @Param("year") Integer year, @Param("month") Integer month);

	/**
	 * 获取某年某月某模块产品供需关系
	 * @param flag 模块编号
	 * @param year 年份
	 * @param month 月份
	 * @return SupplyAndDemandVO
	 */
	SupplyAndDemandVO getSupplyAndDemandInfo(@Param("flag") Integer flag, @Param("year") Integer year, @Param("month") Integer month);

	/**
	 * 获取某年某月某模块产品的采购平均价格
	 * @param flag 模块编号
	 * @param year 年份
	 * @param month 月份
	 * @return List<PurchaseVO>
	 */
	List<PurchaseVO> getPurchasePrice(@Param("flag") Integer flag, @Param("year") Integer year, @Param("month") Integer month);

	/**
	 * 获取某年某月某模块所有产品平均采购价格
	 * @param flag 模块编号
	 * @param year 年份
	 * @param month 月份
	 * @return BigDecimal
	 */
	BigDecimal getAveragePrice(@Param("flag") Integer flag, @Param("year") Integer year, @Param("month") Integer month);

}
