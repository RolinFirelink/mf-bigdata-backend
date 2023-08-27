package com.arg.smart.web.report.report_handler.product_and_sale;

import com.arg.smart.common.core.utils.StringUtils;
import com.arg.smart.web.report.entity.vo.PurchaseVO;
import com.arg.smart.web.report.mapper.ProductAndSaleMapper;
import com.arg.smart.web.report.service.ReportKeyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author GlowingTree
 * @Description 农产品产销形势分析采购价格部分
 * @Date 8/24/2023 9:05 PM
 * @Version 1.0
 */
@Component
@Slf4j
public class PurchaseInfoHandler implements ReportKeyHandler {

	@Resource
	private ProductAndSaleMapper mapper;

	@Override
	public String getKey() {
		return "\\{sale_info}";
	}

	@Override
	public String handle(Integer flag, Integer year, Integer month, Object ... params) {
		StringBuilder sb = new StringBuilder();

		int preYear = month == 1 ? year - 1 : year,
				preMonth = month == 1 ? 12 : month - 1;

		List<PurchaseVO> thisMonthPrice = mapper.getPurchasePrice(flag, year, month);
		BigDecimal thisMonthAvg = mapper.getAveragePrice(flag, year, month);
		List<PurchaseVO> lastMonthPrice = mapper.getPurchasePrice(flag, preYear, preMonth);
		BigDecimal lastMonthAvg = mapper.getAveragePrice(flag, preYear, preMonth);

		if (!thisMonthPrice.isEmpty()) {
			thisMonthPrice.forEach(p -> {
				if (p.getAvgPrice() == null) {
					p.setAvgPrice(thisMonthAvg == null ? new BigDecimal("0.0") : thisMonthAvg);
				}
			});

			if (!lastMonthPrice.isEmpty()) {
				lastMonthPrice.forEach(p -> {
					if (p.getAvgPrice() == null) {
						p.setAvgPrice(lastMonthAvg == null ? new BigDecimal("0.0") : lastMonthAvg);
					}
				});
			}

			sb.append("{year}年{month}月{flag}类各产品采购价格如下表（单位：元/斤，%）");
			sb.append("<div style=\"margin: 0 auto; width: 1400px; box-sizing: border-box; overflow-x: scroll;\">\n" +
					"<table style=\"margin: 0 auto; text-align: center; line-height: 2; border: 2px black solid; border-collapse: collapse;\">\n");

			sb.append("<tr>\n");
			sb.append("<td style=\"border: 2px #000 solid; padding: 0 30px 0 30px;\">").append("类别").append("</td>\n");

			thisMonthPrice.forEach(p -> {
				sb.append("<td style=\"border: 2px #000 solid; padding: 0 30px 0 30px;\">").append(p.getName()).append("</td>\n");
			});
			sb.append("</tr>\n");
			sb.append("<tr>\n");
			sb.append("<td style=\"border: 2px #000 solid;\">").append("采购均价").append("</td>\n");

			thisMonthPrice.forEach(p -> {
				sb.append("<td style=\"border: 2px #000 solid; padding: 0 30px 0 30px;\">")
						.append(String.format("%.2f", p.getAvgPrice())).append("</td>\n");
			});
			sb.append("</tr>\n");

			// 同比涨幅
			sb.append("<tr>\n");
			sb.append("<td style=\"border: 2px #000 solid;\">").append("同比涨幅").append("</td>\n");

			boolean havePercent = false;
			for (PurchaseVO p : thisMonthPrice) {
				for (PurchaseVO p1 : lastMonthPrice) {
					if (StringUtils.isNotEmpty(p.getName()) && p.getName().equals(p1.getName())) {
						havePercent = true;

						int result = Double.compare(p.getAvgPrice().doubleValue(), p1.getAvgPrice().doubleValue());
						double percent;
						if (result > 0) {
							percent = (p.getAvgPrice().doubleValue() - p1.getAvgPrice().doubleValue()) / p1.getAvgPrice().doubleValue() * 100.0;
						} else if (result < 0) {
							percent = (p1.getAvgPrice().doubleValue() - p.getAvgPrice().doubleValue()) / p1.getAvgPrice().doubleValue() * 100.0 * -1.0;
						} else {
							percent = 100.0;
						}
						sb.append("<td style=\"border: 2px #000 solid; padding: 0 20px 0 20px;\">").append(String.format("%.2f", percent)).append("</td>\n");
						break;
					}
				}
				if (!havePercent) {
					sb.append("<td style=\"border: 2px #000 solid; padding: 0 20px 0 20px;\">").append(String.format("%.2f", 100.0)).append("</td>\n");
				}
				havePercent = false;
			}
			sb.append("</tr>\n");

			sb.append("</table>\n" +
					"</div>");
		} else {
			sb.append("{year}年{month}月{flag}类产品暂无采购价格数据");
		}

		return sb.toString();
	}
}
