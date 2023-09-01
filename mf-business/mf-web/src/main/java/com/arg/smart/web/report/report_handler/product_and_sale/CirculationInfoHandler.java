package com.arg.smart.web.report.report_handler.product_and_sale;

import com.arg.smart.web.report.entity.vo.CirculationVO;
import com.arg.smart.web.report.mapper.ProductAndSaleMapper;
import com.arg.smart.web.report.service.ReportKeyHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Author GlowingTree
 * @Description 农产品产销形势销售渠道部分
 * @Date 8/24/2023 6:16 PM
 * @Version 1.0
 */
@Component
public class CirculationInfoHandler implements ReportKeyHandler {

	@Resource
	private ProductAndSaleMapper mapper;

	@Override
	public String getKey() {
		return "\\{company_info_and_circulation_amount}";
	}

	@Override
	public String handle(Integer flag, Integer year, Integer month, Object ... params) {
		StringBuilder sb = new StringBuilder();

		int preYear = month == 1 ? year - 1 : year,
				preMonth = month == 1 ? 12 : month - 1;

		List<CirculationVO> circulationAmount = mapper.getCirculationAmount(flag, year, month);
		List<CirculationVO> lastMonthAmount = mapper.getCirculationAmount(flag, preYear, preMonth);

		sb.append("{year}年{month}月，{flag}产品物流，承运物流企业共").append(circulationAmount.size()).append("家");

		int result = Integer.compare(circulationAmount.size(), lastMonthAmount.size());
		if (result > 0) {
			sb.append("，相较上月（").append(preYear).append("年").append(preMonth).append("月）").append(lastMonthAmount.size()).append("家")
					.append("增长").append(circulationAmount.size() - lastMonthAmount.size())
					.append("家").append("涨幅为")
					.append(String.format("%.2f", (circulationAmount.size() - lastMonthAmount.size()) / lastMonthAmount.size() * 100.0));
		} else if (result < 0) {
			sb.append("，相较上月（").append(preYear).append("年").append(preMonth).append("月）").append(lastMonthAmount.size()).append("家")
					.append("减少").append(lastMonthAmount.size() - circulationAmount.size())
					.append("家").append("家").append("涨幅为")
					.append(String.format("%.2f", (lastMonthAmount.size() - circulationAmount.size()) / lastMonthAmount.size() * 100.0 * -1.0));
		} else {
			sb.append("，与上月（").append(preYear).append("年").append(preMonth).append("月）持平");
		}

		if (!circulationAmount.isEmpty()) {
			sb.append("，其中").append(circulationAmount.get(0).getCompanyName())
					.append("承运商承运总量").append(circulationAmount.get(0).getAmount()).append("吨")
					.append("，位居第 1");
			sb.append("，各承运商{year}年{month}月承运量如下表（单位：吨）");

			sb.append("<table style=\"margin: 0 auto; text-align: center; line-height: 2; border: 2px black solid; border-collapse: collapse;\">\n" +
					"    <th style=\"border: 2px #000 solid; padding: 0 100px 0 100px;\">承运商名称</th>\n" +
					"    <th style=\"border: 2px #000 solid; padding: 0 100px 0 100px;\">本月运量</th>\n" +
					"    <th style=\"border: 2px #000 solid; padding: 0 100px 0 100px;\">上月运量</th>\n" +
					"    <th style=\"border: 2px #000 solid; padding: 0 100px 0 100px;\">增减数</th>\n" +
					"    <th style=\"border: 2px #000 solid; padding: 0 100px 0 100px;\">增减百分比</th>\n");
			for (CirculationVO circulationVO : circulationAmount) {
				sb.append("<tr>\n");
				sb.append("<td style=\"border: 2px #000 solid;\">").append(circulationVO.getCompanyName()).append("</td>\n");
				sb.append("<td style=\"border: 2px #000 solid;\">").append(circulationVO.getAmount()).append("</td>\n");

				lastMonthAmount.forEach(c -> {
					if (Objects.equals(c.getCompanyId(), circulationVO.getCompanyId())) {
						sb.append("<td style=\"border: 2px #000 solid;\">").append(c.getAmount()).append("</td>\n");

						double amount = circulationVO.getAmount() == null ? 0.0 : circulationVO.getAmount().doubleValue(),
							preAmount = c.getAmount() == null ? 0.0 : c.getAmount().doubleValue();
						sb.append("<td style=\"border: 2px #000 solid;\">").append(String.format("%.2f", amount - preAmount)).append("</td>\n");

						double percent = 0.0;
						int compare = Double.compare(amount, preAmount);
						if (compare > 0) {
							percent = (amount - preAmount) / preAmount * 100.0;
						} else if (compare < 0) {
							percent = (preAmount - amount) / preAmount * 100.0 * -1.0;
						}
						sb.append("<td style=\"border: 2px #000 solid;\">").append(String.format("%.2f%%", percent)).append("</td>\n");
					}
				});

				sb.append("</tr>\n");
			}
		}
		sb.append("</table>\n");

		return sb.toString();
	}
}
