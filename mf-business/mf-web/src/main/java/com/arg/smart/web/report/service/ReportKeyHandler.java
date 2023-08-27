package com.arg.smart.web.report.service;

/**
 * @Author GlowingTree
 * @Description 农产品分析报告占位符替换接口
 * @Date 8/24/2023 2:07 PM
 * @Version 1.0
 */
public interface ReportKeyHandler {

	/**
	 * 获取实现类中处理的占位符
	 * @return String
	 */
	String getKey();
	
	/**
	 * 处理报告内容, 将指定占位符替换为新的字符串
	 * @param flag 模块编号
	 * @param year 年份
	 * @param month 月份
	 * @param params 任意参数
	 */
	String handle(Integer flag, Integer year, Integer month, Object ... params);
}
