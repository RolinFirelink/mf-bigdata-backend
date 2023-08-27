package com.arg.smart.web.report.report_handler.product_and_sale;

import com.arg.smart.web.report.entity.vo.BaseProductVO;
import com.arg.smart.web.report.mapper.ProductAndSaleMapper;
import com.arg.smart.web.report.service.ReportKeyHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

/**
 * @Author GlowingTree
 * @Description 农产品产销形式分析基地数据分析
 * @Date 8/24/2023 4:25 PM
 * @Version 1.0
 */
@Component
public class BaseProductInfoHandler implements ReportKeyHandler {

	@Resource
	private ProductAndSaleMapper mapper;

	@Override
	public String getKey() {
		return "\\{base_produce_info}";
	}

	@Override
	public String handle(Integer flag, Integer year, Integer month, Object ... params) {
		StringBuilder sb = new StringBuilder();

		DecimalFormat df = new DecimalFormat("0.00");

		List<BaseProductVO> productAmount = mapper.getBaseProductAmount(flag, year, month);
		List<BaseProductVO> lastMonthAmount = mapper.getBaseProductAmount(flag, month == 1 ? year - 1 : year, month == 1 ? 12 : month - 1);

		int baseCount = mapper.getBaseNum(flag, year, month);
		sb.append("其中，{year}年{month}月一共有{flag}生产基地").append(baseCount).append("个，");
		if (!productAmount.isEmpty()) {
			sb.append("其中：");
			sb.append(productAmount.get(0).getName()).append("基地产量位居第 1，产量为").append(productAmount.get(0).getAmount()).append("吨。");
			for (int i = 1; i <= 2; i++) {
				if (i < productAmount.size()) {
					sb.append(productAmount.get(i).getName()).append("基地产量紧随其后，位居第 ").append(i + 1)
							.append("，产量为").append(productAmount.get(i).getAmount()).append("吨");
					if (i < productAmount.size() - 1) {
						sb.append("，");
					} else {
						sb.append("。");
					}
				}
			}
		}
		sb.append("各基地{year}年{month}月产量如下表（单位：吨）");
		sb.append("<table style=\"margin: 0 auto; text-align: center; line-height: 2; border: 2px black solid; border-collapse: collapse;\">\n" +
				"    <th style=\"border: 2px #000 solid; padding: 0 100px 0 100px;\">基地名称</th>\n" +
				"    <th style=\"border: 2px #000 solid; padding: 0 100px 0 100px;\">本月</th>\n" +
				"    <th style=\"border: 2px #000 solid; padding: 0 100px 0 100px;\">上月</th>\n" +
				"    <th style=\"border: 2px #000 solid; padding: 0 100px 0 100px;\">增减数</th>\n" +
				"    <th style=\"border: 2px #000 solid; padding: 0 100px 0 100px;\">增减百分比</th>\n");
		for (BaseProductVO productVO : productAmount) {
			sb.append("<tr>\n" +
					"<td style=\"border: 2px #000 solid;\">" + productVO.getName() + "</td>\n" +
					"<td style=\"border: 2px #000 solid;\">" + productVO.getAmount() + "</td>\n");
					lastMonthAmount.forEach(p -> {
						if (Objects.equals(p.getId(), productVO.getId())) {
							sb.append("<td style=\"border: 2px #000 solid;\">" + p.getAmount() + "</td>\n");

							double thisMonth = productVO.getAmount() == null ? 0.0 : productVO.getAmount().doubleValue(),
									lastMonth = p.getAmount() == null ? 0.0 : p.getAmount().doubleValue();

							sb.append("<td style=\"border: 2px #000 solid;\">" + df.format(thisMonth - lastMonth) + "</td>\n");

							double percent = Double.compare(thisMonth, lastMonth) > 0 ?
									(thisMonth - lastMonth) / lastMonth : (lastMonth - thisMonth) / lastMonth * -1.0;

							sb.append("<td style=\"border: 2px #000 solid;\">" + df.format(percent * 100.0) + "%" + "</td>\n");
						}
					});
			sb.append("</tr>\n");
		}
		sb.append("</table>");

		return sb.toString();
	}
}
