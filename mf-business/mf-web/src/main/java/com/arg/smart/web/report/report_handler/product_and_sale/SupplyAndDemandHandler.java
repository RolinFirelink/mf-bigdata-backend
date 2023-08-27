package com.arg.smart.web.report.report_handler.product_and_sale;

import com.arg.smart.web.report.entity.vo.SupplyAndDemandVO;
import com.arg.smart.web.report.mapper.ProductAndSaleMapper;
import com.arg.smart.web.report.service.ReportKeyHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author GlowingTree
 * @Description 农产品产销形势供需环节部分
 * @Date 8/24/2023 7:20 PM
 * @Version 1.0
 */
@Component
public class SupplyAndDemandHandler implements ReportKeyHandler {

	@Resource
	private ProductAndSaleMapper mapper;

	@Override
	public String getKey() {
		return "\\{supply_and_demand}";
	}

	@Override
	public String handle(Integer flag, Integer year, Integer month, Object ... params) {
		StringBuilder sb = new StringBuilder();

		SupplyAndDemandVO supplyAndDemandInfo = mapper.getSupplyAndDemandInfo(flag, year, month);

		int supply = supplyAndDemandInfo == null ? 0 : supplyAndDemandInfo.getSupply().intValue(),
			demand = supplyAndDemandInfo == null ? 0 : supplyAndDemandInfo.getDemand().intValue();
		String unit = supplyAndDemandInfo == null ? "吨" : supplyAndDemandInfo.getUnit();

		sb.append("{year}年{month}月，{flag}产品供应量为")
				.append(supply).append(unit);
		sb.append("，需求量为")
				.append(demand).append(unit);
		int result = Integer.compare(supply, demand);
		if (result > 0) {
			sb.append("，{flag}产品处于供过于求状态");
		} else if (result < 0) {
			sb.append("，{flag}产品处于供不应求状态");
		} else {
			sb.append("，{flag}产品处于供需匹配状态");
		}

		double percent = (Double.compare(supply, 0.0) == 0 || Double.compare(demand, 0.0) == 0) ? 0.0 : (double) supply / demand;
		sb.append("，供需比为").append(String.format("%.2f%%", percent * 100.0));

		return sb.toString();
	}
}
