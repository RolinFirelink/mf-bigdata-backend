package com.arg.smart.web.report.report_handler.product_and_sale;

import com.arg.smart.web.report.mapper.ProductAndSaleMapper;
import com.arg.smart.web.report.service.ReportKeyHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DecimalFormat;

/**
 * @Author GlowingTree
 * @Description 农产品产销形势分析采购商形势
 * @Date 8/24/2023 5:23 PM
 * @Version 1.0
 */
@Component
public class BuyerInfoHandler implements ReportKeyHandler {

	@Resource
	private ProductAndSaleMapper mapper;

	@Override
	public String getKey() {
		return "\\{buyer_info}";
	}

	@Override
	public String handle(Integer flag, Integer year, Integer month, Object ... params) {
		StringBuilder sb = new StringBuilder();

		DecimalFormat df = new DecimalFormat("0.00");

		int preYear = month == 1 ? year - 1 : year,
				preMonth = month == 1 ? 12 : month - 1;

		int thisMonthCount = mapper.getBuyerNum(flag, year, month) == null ? 0 : mapper.getBuyerNum(flag, year, month);
		int lastMonthCount = mapper.getBuyerNum(flag, preYear, preMonth) == null ? 0 : mapper.getBuyerNum(flag, preYear, preMonth);
		int result = Integer.compare(thisMonthCount, lastMonthCount);

		sb.append("{year}年{month}月共有{flag}采购商").append(thisMonthCount).append("家");

		if (result > 0) {
			sb.append("，相较上月（").append(preYear).append("年").append(preMonth).append("月）")
					.append(lastMonthCount).append("家，")
					.append("增加").append(thisMonthCount - lastMonthCount).append("家采购商");
		} else if (result < 0) {
			sb.append("，相较上月（").append(preYear).append("年").append(preMonth).append("月）")
					.append(lastMonthCount).append("家，")
					.append("减少").append(lastMonthCount - thisMonthCount).append("家采购商");
		} else {
			sb.append("本月采购商数量与上月（").append(preYear).append("年").append(preMonth).append("月持平");
		}

		double thisMonthTotal = mapper.getBuyerPurchaseTotal(flag, year, month) == null ? 0.0 : mapper.getBuyerPurchaseTotal(flag, year, month);
		double lastMonthTotal = mapper.getBuyerPurchaseTotal(flag, preYear, preMonth) == null ? 0.0 : mapper.getBuyerPurchaseTotal(flag, preYear, preMonth);

		sb.append("，本月总采购量为").append(df.format(thisMonthTotal)).append("斤");
		result = Double.compare(thisMonthTotal, lastMonthTotal);
		if (result > 0) {
			sb.append("，相较上月（").append(preYear).append("年").append(preMonth).append("月）")
					.append("增加").append(df.format(thisMonthTotal - lastMonthTotal)).append("斤")
					.append("，增幅为").append(df.format((thisMonthTotal - lastMonthTotal) / lastMonthTotal * 100.0)).append("%");
		} else if (result < 0) {
			sb.append("，相较上月（").append(preYear).append("年").append(preMonth).append("月）")
					.append("减少").append(df.format(lastMonthTotal - thisMonthTotal)).append("斤")
					.append("，增幅为").append(df.format((lastMonthTotal - thisMonthTotal) / lastMonthTotal * 100.0 * -1.0)).append("%");
		} else {
			sb.append("本月采购商数量与上月（").append(preYear).append("年").append(preMonth).append("月持平");
		}

		return sb.toString();
	}
}
