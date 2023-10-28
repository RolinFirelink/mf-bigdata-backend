package com.arg.smart.web.report.report_handler.good_analyse;

import com.arg.smart.common.core.utils.StringUtils;
import com.arg.smart.web.report.entity.vo.CustomerEvaluationVO;
import com.arg.smart.web.report.entity.vo.GoodAnalyseVO;
import com.arg.smart.web.report.entity.vo.SupplyAndDemandVO;
import com.arg.smart.web.report.mapper.GoodAnalyseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author GlowingTree
 * @Description 农产品竞品分析报告处理类
 * @Date 8/25/2023 1:54 AM
 * @Version 1.0
 */
@Component
@Slf4j
public class GoodAnalyseHandler {

	@Resource
	private GoodAnalyseMapper mapper;
	@Resource
	private Environment env;

	/**
	 * 查询某个产品是否属于某个模块
	 *
	 * @param firstGood 第一个产品名称
	 * @param secondGood 第二个产品名称
	 * @return Integer
	 */
	public Integer checkGoodInModule(String firstGood, String secondGood) {
		Integer firstFlag = mapper.checkGoodInModule(firstGood);
		Integer secondFlag = mapper.checkGoodInModule(secondGood);
		if (firstFlag == null || secondFlag == null || !Objects.equals(firstFlag, secondFlag)) {
			return -1;
		}
		return firstFlag;
	}

	/**
	 * 从网上爬取相关产品的数据
	 *
	 * @param name 产品名称
	 * @return GoodAnalyseVO
	 */
	public GoodAnalyseVO getGoodInfoFromWeb(String name) {
		// 拼接 URL
		String url = env.getProperty("reports.good-analyse.search-url") + name;

		StringBuilder sb = new StringBuilder();
		BufferedReader bf = null;

		try {
			// 获取网页内容
			URLConnection conn = new URL(url).openConnection();
			bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line;
			while ((line = bf.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			return null;
		} finally {
			try {
				if (bf != null) {
					bf.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				log.error("Error during obtain product {} good analyse report, Error message {}", name, e.getMessage());
			}
		}

		// 爬取数据, 封装 Bean
		if (sb.length() <= 0) {
			return null;
		}
		return getGoodAnalyseVo(sb.toString());
	}

	/**
	 * 从 HTML 文本中获取产品相关信息 (简介, 特性)
	 * @param htmlText HTML 文本
	 * @return GoodAnalyseVO
	 */
	public GoodAnalyseVO getGoodAnalyseVo(String htmlText) {
		if (StringUtils.isEmpty(htmlText)) {
			return null;
		}
		// 获取 Summary 和第一段二级标题的内容
		String avatarUrl = null, group;
		StringBuilder summary = new StringBuilder(),
				firstSecondTitleContent = new StringBuilder();

		Pattern summaryPattern = Pattern.compile(Objects.requireNonNull(env.getProperty("reports.good-analyse.web-crawl.summary-regex"))),
				paramPattern = Pattern.compile(Objects.requireNonNull(env.getProperty("reports.good-analyse.web-crawl.param-regex"))),
				htmlPattern = Pattern.compile(Objects.requireNonNull(env.getProperty("reports.good-analyse.web-crawl.html-regex"))),
				firstSecondTitlePattern = Pattern.compile(Objects.requireNonNull(env.getProperty("reports.good-analyse.web-crawl.first-second-title-regex")));

		Matcher summaryMatcher = summaryPattern.matcher(htmlText),
				paramMatcher, htmlMatcher;
		// 获取图片 URL
		Pattern avatarPattern = Pattern.compile("<div class=\"summary-pic\">.*?</div>");
		Matcher avatarMatcher = avatarPattern.matcher(htmlText);
		while (avatarMatcher.find()) {
			group = avatarMatcher.group();
			avatarPattern = Pattern.compile("<img src=\".*?\"");
			avatarMatcher = avatarPattern.matcher(group);
			if (avatarMatcher.find()) {
				avatarUrl = avatarMatcher.group().substring(10, avatarMatcher.group().length() - 1);
			}
		}

		// 获取 Summary
		if (summaryMatcher.find()) {
			// 获取 Summary 中的每一段
			paramMatcher = paramPattern.matcher(summaryMatcher.group());
			while (paramMatcher.find()) {
				// 去除所有 HTML 标记
				htmlMatcher = htmlPattern.matcher(paramMatcher.group());
				summary.append(htmlMatcher.replaceAll(""));
			}
		}
		// 获取第一段 Second Title
		Matcher firstSecondTitleMatcher = firstSecondTitlePattern.matcher(htmlText);
		if (firstSecondTitleMatcher.find()) {
			// 获取 Second Title 中的每一段
			paramMatcher = paramPattern.matcher(firstSecondTitleMatcher.group());
			while (paramMatcher.find()) {
				// 去除所有 HTML 标记
				htmlMatcher = htmlPattern.matcher(paramMatcher.group());
				firstSecondTitleContent.append(htmlMatcher.replaceAll(""));
			}
		}

		return GoodAnalyseVO.builder().picUrl(avatarUrl).summary(summary.toString()).feature(firstSecondTitleContent.toString()).build();
	}

	/**
	 * 获取某年某月某模块某产品供需关系
	 * @param flag 模块编号
	 * @param name 产品名称
	 * @param year 年份
	 * @param month 月份
	 * @return SupplyAndDemandVO
	 */
	public String getGoodAnalyseSupplyAndDemandInfo(Integer flag,
													 String name,
													 Integer year,
													 Integer month) {
		if (StringUtils.isEmpty(name)) {
			return String.format("无法获取%d年%d月产品供需情况", year, month);
		}

		StringBuilder sb = new StringBuilder();
		SupplyAndDemandVO supplyAndDemandInfo = mapper.getSupplyAndDemandInfo(flag, name, year, month);

		int supply = supplyAndDemandInfo == null ? 0 : supplyAndDemandInfo.getSupply().intValue(),
				demand = supplyAndDemandInfo == null ? 0 : supplyAndDemandInfo.getDemand().intValue();
		String unit = supplyAndDemandInfo == null ? "吨" : supplyAndDemandInfo.getUnit();

		int result = Integer.compare(supply, demand);
		double percent = (Double.compare(supply, 0.0) == 0 || Double.compare(demand, 0.0) == 0) ? 0.0 : (double) supply / demand * 100.0;
		sb.append(String.format("%d年%d月产品：%s的供应量为%d%s，需求量为%d%s，%s，产品供需比为%.2f%%", year, month,
				name, supply, unit, demand, unit,
				result > 0 ? "处于供过于求状态" : result == 0 ? "处于供需匹配状态" : "处于供不应求状态", percent));

		return sb.toString();
	}

	/**
	 * 获取某年某月某模块某产品的消费者评价
	 * @param flag 模块编号
	 * @param name 产品名称
	 * @param year 年份
	 * @param month 月份
	 * @return String
	 */
	public String getProductCustomerEvaluation(Integer flag, String name, Integer year, Integer month) {
		if (StringUtils.isEmpty(name)) {
			return String.format("无法获取 %d 年 %d 月的 %s 产品消费者评价", year, month, name);
		}

		List<CustomerEvaluationVO> evaluations = mapper.getProductCustomerEvaluation(flag, name, year, month);
		int bad = 0, middle = 0, good = 0, total = 0;
		for (CustomerEvaluationVO e : evaluations) {
			total++;
			switch (e.getEvaluate()) {
				case 0: {
					bad++;
					break;
				}
				case 1: {
					middle++;
					break;
				}
				case 2: {
					good++;
					break;
				}
			}
		}
		return String.format("产品：%s%d年%d月共有%d条消费者购买评价，其中好评%d条，" +
				"占比%.2f%%，中评%d条，占比%.2f%%，差评%d条，占比%.2f%%", name, year, month, total, good,
				(total == 0 ? 0.0 : ((double) good / total) * 100.0),
				middle, (total == 0 ? 0.0 : ((double) middle / total) * 100.0),
				bad, (total == 0 ? 0.0 : ((double) bad / total) * 100.0));
	}

}
