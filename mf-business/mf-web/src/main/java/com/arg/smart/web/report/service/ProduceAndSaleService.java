package com.arg.smart.web.report.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.report.entity.ProduceAndSale;
import com.arg.smart.web.report.req.ReqProduceAndSale;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author GlowingTree
 * @Description 农产品产销形势分析报告接口
 * @Date 8/24/2023 2:10 PM
 * @Version 1.0
 */
public interface ProduceAndSaleService extends IService<ProduceAndSale> {

	/**
	 * 获取农产品产销形势报告
	 * @param flag 模块编号
	 * @param year 年份
	 * @param month 月份
	 * @return String
	 */
	String getProductAndSaleReport(Integer flag, Integer year, Integer month);

	/**
	 * 农产品产销形势分析报告分页列表查询
	 * @param reqProduceAndSale 农产品产销形势分析报告条件查询类
	 * @return PageResult<ProduceAndSale>
	 */
	PageResult<ProduceAndSale> list(ReqProduceAndSale reqProduceAndSale);

}
