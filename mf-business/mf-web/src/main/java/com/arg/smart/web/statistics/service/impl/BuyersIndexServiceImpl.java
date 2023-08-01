package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.order.model.ModuleFlag;
import com.arg.smart.web.statistics.entity.BuyersIndex;
import com.arg.smart.web.statistics.mapper.BuyersIndexMapper;
import com.arg.smart.web.statistics.req.ReqBuyersIndex;
import com.arg.smart.web.statistics.service.BuyersIndexService;
import com.arg.smart.web.statistics.vo.BuyerPurchaseVO;
import com.arg.smart.web.statistics.vo.BuyersIndexVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cgli
 * @description: 采购商指数
 * @date: 2023-07-16
 * @version: V1.0.0
 */
@Service
@Slf4j
public class BuyersIndexServiceImpl extends ServiceImpl<BuyersIndexMapper, BuyersIndex> implements BuyersIndexService {

	@Resource
	private BuyersIndexMapper buyersIndexMapper;

	@Override
	public List<BuyersIndex> list(ReqBuyersIndex reqBuyersIndex) {
		QueryWrapper<BuyersIndex> queryWrapper = new QueryWrapper<>();
		Date startTime = reqBuyersIndex.getStartTime();
		Date endTime = reqBuyersIndex.getEndTime();
		queryWrapper.ge(startTime != null, "statistical_time", startTime)
				.le(endTime != null, "statistical_time", endTime);
		Integer flag = reqBuyersIndex.getFlag();
		queryWrapper.eq("flag", flag);
		queryWrapper.orderByDesc("year").orderByDesc("month");
		return this.list(queryWrapper);
	}

	@Override
	public PageResult<BuyersIndex> listPage(ReqBuyersIndex reqBuyersIndex) {
		LambdaQueryWrapper<BuyersIndex> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(reqBuyersIndex.getFlag() != null, BuyersIndex::getFlag, reqBuyersIndex.getFlag())
				.eq(reqBuyersIndex.getYear() != null, BuyersIndex::getYear, reqBuyersIndex.getYear())
				.eq(reqBuyersIndex.getMonth() != null, BuyersIndex::getMonth, reqBuyersIndex.getMonth());
		return new PageResult<>(this.list(queryWrapper));
	}

	@Override
	public List<BuyersIndexVO> getBuyersIndex(ReqBuyersIndex reqBuyersIndex) {
		List<BuyersIndexVO> returnList = new ArrayList<>();
		List<String> buyersName;
		BuyersIndexVO tempBuyersVO;
		double index = 0;
		// 循环遍历每个模块
		for (int i = ModuleFlag.CHICKEN; i <= ModuleFlag.PREFABRICATED_DISHES; i++) {
			buyersName = buyersIndexMapper.selectBuyersName(i, reqBuyersIndex.getYear(), reqBuyersIndex.getMonth());
			if (buyersName.isEmpty()) {
				returnList.add(BuyersIndexVO.builder()
						.flag(i)
						.year(reqBuyersIndex.getYear())
						.month(reqBuyersIndex.getMonth())
						.index(0.0)
						.build());
				continue;
			}
			for (String name : buyersName) {
				tempBuyersVO = this.getBuyerIndex(i,
						name,
						reqBuyersIndex.getYear(),
						reqBuyersIndex.getMonth());
				index += tempBuyersVO.getIndex();
			}
			returnList.add(BuyersIndexVO.builder()
					.flag(i)
					.year(reqBuyersIndex.getYear())
					.month(reqBuyersIndex.getMonth())
					.index(index / buyersName.size())
					.build());
			index = 0;
		}
		return returnList;
	}

	private BuyersIndexVO getBuyerIndex(Integer flag, String name, Integer year, Integer month) {
		// 查询本月这个采购商的采购总价
		BuyerPurchaseVO thisMonth = this.getBuyerPurchaseVo(flag, name, year, month);
		// 查询上月这个采购商的采购总价
		year = month == 1 ? year - 1 : year;
		month = month == 1 ? 12 : month - 1;
		BuyerPurchaseVO previousMonth = this.getBuyerPurchaseVo(flag, name, year, month);
		log.info("previous: {}", previousMonth);

		return BuyersIndexVO.builder()
				// 上一个月没有数据的情况，设为 100 点
				.index((previousMonth == null || previousMonth.getTotalPrice() == null)
						? 100.0
						: thisMonth.getTotalPrice() / previousMonth.getTotalPrice())
				.build();
	}

	private BuyerPurchaseVO getBuyerPurchaseVo(Integer flag,
	                                           String name,
	                                           Integer year,
	                                           Integer month) {
		return buyersIndexMapper.selectOneBuyerPurchaseInfo(flag, name, year, month);
	}
}
