package com.arg.smart.web.report.entity;

import org.springframework.core.env.Environment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @Author GlowingTree
 * @Description 农产品产销形式分析及竞品分析占位符映射
 * @Date 8/24/2023 1:50 PM
 * @Version 1.0
 */
public class ReportMapper {

	private static final Map<String, Supplier<String>> MAPPER = new HashMap<>();

	private Integer year = Calendar.getInstance().get(Calendar.YEAR);
	private Integer month = Calendar.getInstance().get(Calendar.MONTH) + 1;
	public Map<String, Supplier<String>> getMapper() {
		return MAPPER;
	}

	private final Environment env;

	public ReportMapper(Environment env) {
		this.env = env;
	}

	public ReportMapper(Environment env, Integer year, Integer month) {
		this.env = env;
		this.year = year;
		this.month = month;
	}

	public void initMapper() {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		MAPPER.put("\\{year}", () -> String.valueOf(this.year));
		MAPPER.put("\\{month}", () -> String.valueOf(this.month));
		MAPPER.put("\\{date}", () -> sdf.format(new Date()));
		MAPPER.put("\\{source}", () -> env.getProperty("reports.from"));
		MAPPER.put("\\{author}", () -> env.getProperty("reports.author"));
	}

}
