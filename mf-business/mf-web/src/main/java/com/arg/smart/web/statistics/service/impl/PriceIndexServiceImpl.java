package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.order.model.ModuleFlag;
import com.arg.smart.web.statistics.entity.PriceIndex;
import com.arg.smart.web.statistics.mapper.PriceIndexMapper;
import com.arg.smart.web.statistics.req.ReqBuyersIndex;
import com.arg.smart.web.statistics.req.ReqPriceIndex;
import com.arg.smart.web.statistics.service.PriceIndexService;
import com.arg.smart.web.statistics.vo.BuyerPurchaseVO;
import com.arg.smart.web.statistics.vo.BuyersIndexVO;
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
		List<PriceIndex> priceIndexes = this.list(reqPriceIndex);
		return new PageResult<>(priceIndexes);
	}

	@Override
	public BuyersIndexVO list(ReqBuyersIndex reqBuyersIndex) {
		ReqPriceIndex reqPriceIndex = new ReqPriceIndex()
				.setFlag(reqBuyersIndex.getFlag())
				.setYear(reqBuyersIndex.getYear())
				.setMonth(reqBuyersIndex.getMonth())
				.setStartTime(reqBuyersIndex.getStartTime())
				.setEndTime(reqBuyersIndex.getEndTime());
		List<PriceIndex> priceIndexes = this.list(reqPriceIndex);

		double ret = 0.0;
		for (PriceIndex index : priceIndexes) {
			ret += index.getPriceIndex();
		}
		ret = priceIndexes.isEmpty() ? 0.0 : Double.compare(ret, 0.0) == 0 ? 0.0 : ret / priceIndexes.size();

		return BuyersIndexVO.builder()
				.flag(reqBuyersIndex.getFlag())
				.year(reqBuyersIndex.getYear())
				.month(reqBuyersIndex.getMonth())
				.index(ret)
				.build();
	}

	@Override
	public void updatePriceIndex(int year, int month) {
		// 获取 sh_product_price（产品采购价格表）中有记录的年月, 若 sh_product_price 中没有本月, 则不计算本月价格指数
		List<DateVO> productPriceDate = mapper.selectProductPriceDate();
		// 获取 sh_product_market_num（采购商采购信息表）中有记录的年月
		List<DateVO> marketNumDate = mapper.selectMarketNumDate();

		// 在 sh_product_price 中存在需要更新的年月
		if (productPriceDate.stream().anyMatch(dateVO -> Objects.equals(dateVO.getYear(), year) &&
				Objects.equals(dateVO.getMonth(), month))) {
			int flag;
			int preYear = month == 1 ? year - 1 : year, preMonth = month == 1 ? 12 : month - 1;
			// 在 sh_product_price 中存在相对需要更新的年月的上一月
			if (productPriceDate.stream().anyMatch(dateVO -> Objects.equals(dateVO.getYear(), preYear) &&
					Objects.equals(dateVO.getMonth(), preMonth))) {
				for (flag = ModuleFlag.CHICKEN; flag <= ModuleFlag.PREFABRICATED_DISHES; flag++) {
					// 本月本模块所有采购商名称, 无采购商采购则不更新
					List<String> names = mapper.selectBuyerName(flag, year, month);
					for (String name : names) {
						// 采购商本月采购记录
						List<BuyerPurchaseVO> purchases = mapper.selectBuyerPurchaseVo(flag, name, year, month);
						// 采购商上月采购记录
						List<BuyerPurchaseVO> prePurchases = mapper.selectBuyerPurchaseVo(flag, name, preYear, preMonth);
						// 不存在上月采购记录, 则将本月认定为基期, 价格指数设置为 100
						if (prePurchases == null || prePurchases.isEmpty()) {
							this.updateOrSave(flag, name, year, month, BASE_PERIOD_PRICE_INDEX);
						} else {
							// 存在上月采购记录, 计算本月采购商价格指数
							double totalPrice = 0.0, preTotalPrice = 0.0;
							for (BuyerPurchaseVO purchase : purchases) {
								totalPrice += purchase.getTotal() * mapper.selectProductMonthPrice(flag, purchase.getName(), year, month).doubleValue();
							}
							for (BuyerPurchaseVO prePurchase : prePurchases) {
								preTotalPrice += prePurchase.getTotal() * mapper.selectProductMonthPrice(flag, prePurchase.getName(), year, month).doubleValue();
							}
							this.updateOrSave(flag, name, year, month,
									Double.compare(preTotalPrice, 0.0) == 0 ?
											BASE_PERIOD_PRICE_INDEX : totalPrice / preTotalPrice * 100.0);
						}
					}
				}
			} else {
				// 在 sh_product_price 中不存在相对需要更新的年月的上一月, 直接查询本月所有采购商, 将价格指数赋值为 100
				for (flag = ModuleFlag.CHICKEN; flag <= ModuleFlag.PREFABRICATED_DISHES; flag++) {
					List<String> names = mapper.selectBuyerName(flag, year, month);
					for (String name : names) {
						this.updateOrSave(flag, name, year, month, BASE_PERIOD_PRICE_INDEX);
					}
				}
			}
		}
	}

	@Override
	public void updatePriceIndex() {

		// 获取 sh_product_price（产品采购价格表）中有记录的年月, 若 sh_product_price 中没有本月, 则不计算本月价格指数
		List<DateVO> productPriceDate = mapper.selectProductPriceDate();
		// 获取 sh_product_market_num（采购商采购信息表）中有记录的年月
		List<DateVO> marketNumDate = mapper.selectMarketNumDate();

		productPriceDate.forEach(p -> {
			// 如果在 product_market_num 中不存在这个年月, 则说明本月没有采购商发布采购信息, 跳过
			if (marketNumDate.stream().noneMatch(dateVO ->
					Objects.equals(p.getYear(), dateVO.getYear()) &&
							Objects.equals(p.getMonth(), dateVO.getMonth()))) {
				return;
			}
			// 存在这个年月, 计算这个年月的采购商价格指数
			int flag, year = p.getYear(), month = p.getMonth(),
					preYear = month == 1 ? year - 1 : year, preMonth = month == 1 ? 12 : month - 1;
			// 如果在 product_market_num 中不存在相对于本年本月的上一个月, 则将本年本月认定为基期
			// 查询本月所有采购商名称, 并将其价格指数定为 100
			if (marketNumDate.stream().noneMatch(dateVO ->
					Objects.equals(preYear, dateVO.getYear()) &&
							Objects.equals(preMonth, dateVO.getMonth()))) {
				// 查询每个模块的采购商, 将采购商价格指数赋值为 100
				for (flag = ModuleFlag.CHICKEN; flag <= ModuleFlag.PREFABRICATED_DISHES; flag++) {
					// 获取本模块下所有采购商名称
					List<String> buyerNames = mapper.selectBuyerName(flag, year, month);
					for (String name : buyerNames) {
						this.updateOrSave(flag, name, year, month, BASE_PERIOD_PRICE_INDEX);
					}
				}
				return;
			}
			// 否则, 存在相对于本年本月的上一个月, 以上一个月为基期, 计算本月的采购商价格指数
			for (flag = ModuleFlag.CHICKEN; flag <= ModuleFlag.PREFABRICATED_DISHES; flag++) {
				// 获取本年本月的采购商名称列表
				List<String> buyerNames = mapper.selectBuyerName(flag, year, month);
				for (String name : buyerNames) {
					// 获取本年本月采购商采购发布记录
					List<BuyerPurchaseVO> purchases = mapper.selectBuyerPurchaseVo(flag, name, year, month);
					// 获取相对于本年本月的上一月采购商发布记录
					List<BuyerPurchaseVO> prePurchases = mapper.selectBuyerPurchaseVo(flag, name, preYear, preMonth);
					// 上一月没有购买产品, 则将本月认定为基期, 本月的价格指数为 100
					if (prePurchases.isEmpty()) {
						this.updateOrSave(flag, name, year, month, BASE_PERIOD_PRICE_INDEX);
						continue;
					}
					// 上一月购买了产品, 计算本月的价格指数
					double totalPrice = 0.0, preTotalPrice = 0.0;
					for (BuyerPurchaseVO purchase : purchases) {
						totalPrice += purchase.getTotal() * mapper.selectProductMonthPrice(flag, purchase.getName(), year, month).doubleValue();
					}
					for (BuyerPurchaseVO prePurchase : prePurchases) {
						preTotalPrice += prePurchase.getTotal() * mapper.selectProductMonthPrice(flag, prePurchase.getName(), year, month).doubleValue();
					}
					this.updateOrSave(flag, name, year, month,
							Double.compare(preTotalPrice, 0.0) == 0 ?
									BASE_PERIOD_PRICE_INDEX : totalPrice / preTotalPrice * 100.0);
				}
			}
		});
	}

	/**
	 * 更新某年某月某采购商的价格指数（先查询是否存在相应记录, 存在则更新, 不存在则新增条目）
	 * @param flag 模块编号
	 * @param name 采购商名称
	 * @param year 年份
	 * @param month 月份
	 * @param index 采购商价格指数
	 */
	private void updateOrSave(int flag, String name, int year, int month, double index) {
		PriceIndex originIndex;
		// 已经存在本采购商本月价格指数则更新
		if ((originIndex = this.getOne(new LambdaQueryWrapper<PriceIndex>()
				.eq(PriceIndex::getFlag, flag)
				.eq(PriceIndex::getName, name)
				.eq(PriceIndex::getYear, year)
				.eq(PriceIndex::getMonth, month))) != null) {
			originIndex.setPriceIndex(index);
			originIndex.setRecordTime(new Date());
			this.updateById(originIndex);
		} else {
			// 否则增加一条新的记录
			this.save(PriceIndex.builder()
					.flag(flag)
					.name(name)
					.priceIndex(index)
					.year(year)
					.month(month)
					.recordTime(new Date())
					.build());
		}
	}
}
