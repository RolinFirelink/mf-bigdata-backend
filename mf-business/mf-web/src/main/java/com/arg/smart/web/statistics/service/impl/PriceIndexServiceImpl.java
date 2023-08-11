package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.order.model.ModuleFlag;
import com.arg.smart.web.statistics.entity.PriceIndex;
import com.arg.smart.web.statistics.mapper.PriceIndexMapper;
import com.arg.smart.web.statistics.req.ReqPriceIndex;
import com.arg.smart.web.statistics.service.PriceIndexService;
import com.arg.smart.web.statistics.vo.BuyerPurchaseVO;
import com.arg.smart.web.statistics.vo.DateVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author GlowingTree
 * @Description 采购商价格指数实现类
 * @Date 8/9/2023 12:23 PM
 * @Version 1.0
 */
@Service
public class PriceIndexServiceImpl extends ServiceImpl<PriceIndexMapper, PriceIndex> implements PriceIndexService {

	private static final double BASE_PERIOD_PRICE_INDEX = 100.0;

	@Resource
	private PriceIndexMapper mapper;

	@Override
	public List<PriceIndex> list(ReqPriceIndex reqPriceIndex) {
		LambdaQueryWrapper<PriceIndex> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(reqPriceIndex.getFlag() != null, PriceIndex::getFlag, reqPriceIndex.getFlag())
				.eq(reqPriceIndex.getYear() != null, PriceIndex::getYear, reqPriceIndex.getYear())
				.eq(reqPriceIndex.getMonth() != null, PriceIndex::getMonth, reqPriceIndex.getMonth())
				.ge(reqPriceIndex.getStartTime() != null, PriceIndex::getRecordTime, reqPriceIndex.getStartTime())
				.le(reqPriceIndex.getEndTime() != null, PriceIndex::getRecordTime, reqPriceIndex.getEndTime());
		return this.list(wrapper);
	}

	@Override
	public PageResult<PriceIndex> listPage(ReqPriceIndex reqPriceIndex) {
		LambdaQueryWrapper<PriceIndex> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(reqPriceIndex.getFlag() != null, PriceIndex::getFlag, reqPriceIndex.getFlag())
				.eq(reqPriceIndex.getYear() != null, PriceIndex::getYear, reqPriceIndex.getYear())
				.eq(reqPriceIndex.getMonth() != null, PriceIndex::getMonth, reqPriceIndex.getMonth())
				.ge(reqPriceIndex.getStartTime() != null, PriceIndex::getRecordTime, reqPriceIndex.getStartTime())
				.le(reqPriceIndex.getEndTime() != null, PriceIndex::getRecordTime, reqPriceIndex.getEndTime());
		return new PageResult<>(this.list(wrapper));
	}

	@Override
	public void updatePriceIndex() {

		// 获取 sh_product_price 中有记录的年月
		List<DateVO> productPriceDate = mapper.selectProductPriceDate();
		// 获取 sh_product_market_num 中有记录的年月
		List<DateVO> marketNumDate = mapper.selectMarketNumDate();

		productPriceDate.forEach(p -> {
			// 如果在 product_market_num 中不存在这个年月, 跳过
			if (marketNumDate.stream().noneMatch(dateVO ->
					Objects.equals(p.getYear(), dateVO.getYear()) && Objects.equals(p.getMonth(), dateVO.getMonth()))) {
				return;
			}
			// 存在这个年月, 计算这个年月的采购商价格指数
			int flag, year = p.getYear(), month = p.getMonth(),
				preYear = month == 1 ? year - 1 : year, preMonth = month == 1 ? 12 : month - 1;
			// 如果在 product_market_num 中不存在相对于本年本月的上一个月, 则将本年本月认定为基期, 价格指数为 100
			if (marketNumDate.stream().noneMatch(dateVO ->
					Objects.equals(preYear, dateVO.getYear()) && Objects.equals(preMonth, dateVO.getMonth()))) {
				// 查询每个模块的采购商, 将采购商价格指数赋值为 100
				for (flag = ModuleFlag.CHICKEN; flag <= ModuleFlag.PREFABRICATED_DISHES; flag++) {
					List<String> buyerNames = mapper.selectBuyerName(flag, year, month);
					for (String name : buyerNames) {
						this.save(PriceIndex.builder()
										.flag(flag)
										.name(name)
										.priceIndex(BASE_PERIOD_PRICE_INDEX)
										.year(year)
										.month(month)
										.recordTime(new Date())
								.build());
					}
				}
				return;
			}
			// 存在相对于本年本月的上一个月, 以上一个月为基期, 计算本月的采购商价格指数
			for (flag = ModuleFlag.CHICKEN; flag <= ModuleFlag.PREFABRICATED_DISHES; flag++) {
				// 获取本年本月的采购商名称列表
				List<String> buyerNames = mapper.selectBuyerName(flag, year, month);
				for (String name : buyerNames) {
					// 获取本年本月采购商采购发布记录
					List<BuyerPurchaseVO> purchases = mapper.selectBuyerPurchaseVo(flag, name, year, month);
					// 获取相对于本年本月的上一月采购商发布记录
					List<BuyerPurchaseVO> prePurchases = mapper.selectBuyerPurchaseVo(flag, name, preYear, preMonth);
					// 上一月没有购买产品, 则将本月认定为基期, 价格指数为 100
					if (prePurchases.isEmpty()) {
						this.save(PriceIndex.builder()
								.flag(flag)
								.name(name)
								.priceIndex(BASE_PERIOD_PRICE_INDEX)
								.year(year)
								.month(month)
								.recordTime(new Date())
								.build());
					}
					// 上一月购买了产品, 计算价格指数
					double totalPrice = 0.0, preTotalPrice = 0.0;
					for (BuyerPurchaseVO purchase : purchases) {
						totalPrice += purchase.getTotal() * mapper.selectProductMonthPrice(flag, purchase.getName(), year, month).doubleValue();
					}
					for (BuyerPurchaseVO prePurchase : prePurchases) {
						preTotalPrice += prePurchase.getTotal() * mapper.selectProductMonthPrice(flag, prePurchase.getName(), year, month).doubleValue();
					}
					this.save(PriceIndex.builder()
									.flag(flag)
									.name(name)
									.priceIndex(totalPrice / preTotalPrice)
									.year(year)
									.month(month)
									.recordTime(new Date())
							.build());
				}
			}
		});
	}
}
