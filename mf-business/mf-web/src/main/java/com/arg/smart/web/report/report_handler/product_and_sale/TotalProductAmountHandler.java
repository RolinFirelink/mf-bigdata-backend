package com.arg.smart.web.report.report_handler.product_and_sale;

import com.arg.smart.web.report.mapper.ProductAndSaleMapper;
import com.arg.smart.web.report.service.ReportKeyHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @Author GlowingTree
 * @Description 获取某模块某年某月总产量
 * @Date 8/24/2023 3:50 PM
 * @Version 1.0
 */
@Component
public class TotalProductAmountHandler implements ReportKeyHandler {

	@Resource
	private ProductAndSaleMapper mapper;

	@Override
	public String getKey() {
		return "\\{total_produce_amount}";
	}

	@Override
	public String handle(Integer flag, Integer year, Integer month, Object ... params) {
		int preYear = year - 1,
			preMonth = month == 1 ? 12 : month - 1;

		BigDecimal thisMonth = mapper.getProduceAmount(flag, year, month);
		BigDecimal lastYear = mapper.getProduceAmount(flag, preYear, month);
		BigDecimal lastMonth = mapper.getProduceAmount(flag, month == 1 ? year - 1 : year, preMonth);

		double thisMonthAmount = thisMonth == null ? 0.0 : thisMonth.doubleValue(),
				lastYearAmount = lastYear == null ? 0.0 : lastYear.doubleValue(),
				lastMonthAmount = lastMonth == null ? 0.0 : lastMonth.doubleValue();

		StringBuilder sb = new StringBuilder();
		sb.append(year).append("年").append(month).append("月{flag}总产量为")
				.append(String.format("%.2f", thisMonthAmount)).append("吨，对比去年同期");

		int compareLastYear = Double.compare(thisMonthAmount, lastYearAmount);
		if (compareLastYear > 0) {
			sb.append("增长").append(String.format("%.2f", thisMonthAmount - lastYearAmount)).append("吨");
		} else if (compareLastYear < 0) {
			sb.append("减少").append(String.format("%.2f", thisMonthAmount - lastYearAmount)).append("吨");
		} else {
			sb.append("持平");
		}

		sb.append("，对比上月（").append(month == 1 ? year - 1 : year).append("年").append(preMonth).append("月）");
		sb.append(String.format("%.2f", lastMonthAmount)).append("吨，");
		int compareLastMonth = Double.compare(thisMonthAmount, lastMonthAmount);
		if (compareLastMonth > 0) {
			sb.append("增长").append(String.format("%.2f", thisMonthAmount - lastMonthAmount)).append("吨");
		} else if (compareLastMonth < 0) {
			sb.append("减少").append(String.format("%.2f", thisMonthAmount - lastMonthAmount)).append("吨");
		} else {
			sb.append("持平");
		}

		return sb.toString();
	}
}
