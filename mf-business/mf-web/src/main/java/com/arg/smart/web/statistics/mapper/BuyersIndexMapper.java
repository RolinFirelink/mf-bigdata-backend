package com.arg.smart.web.statistics.mapper;

import com.arg.smart.web.statistics.entity.BuyersIndex;
import com.arg.smart.web.statistics.vo.BuyerPurchaseVO;
import com.arg.smart.web.statistics.vo.BuyersIndexVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 采购商指数
 * @author cgli
 * @date: 2023-07-16
 * @version: V1.0.0
 */
public interface BuyersIndexMapper extends BaseMapper<BuyersIndex> {

	/**
	 * 获取采购商名称
	 * @param flag 模块名称
	 * @param year 年
	 * @param month 月
	 * @return List<String>
	 */
	List<String> selectBuyersName(@Param("flag") Integer flag, @Param("year") Integer year, @Param("month") Integer month);

	/**
	 * 获取某模块下一个采购商的采购商指数
	 * @param flag 模块名称
	 * @param year 年
	 * @param month 月
	 * @return BuyersIndexVo
	 */
	BuyersIndexVO selectOneBuyerIndex(@Param("flag") Integer flag,
	                                  @Param("name") String name,
	                                  @Param("year") Integer year,
	                                  @Param("month") Integer month);


	BuyerPurchaseVO selectOneBuyerPurchaseInfo(@Param("flag") Integer flag,
	                                           @Param("name") String name,
	                                           @Param("year") Integer year,
	                                           @Param("month") Integer month);

}
