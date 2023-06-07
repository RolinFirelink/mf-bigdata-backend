package com.arg.smart.report.common.base;

import com.arg.smart.report.common.domain.AjaxResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * web层通用数据处理
 * 
 * @ClassName: BaseController
 * @author fuce
 * @date 2018年8月18日
 *
 */

public class BaseController {

	/**
	 * 将前台传递过来的日期格式的字符串，自动转化为Date类型
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new MyDateEditor());
	}

	private class MyDateEditor extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			// 通过两次异常的处理可以，绑定两次日期的格式
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = null;
			try {
				date = format.parse(text);
			} catch (ParseException e) {
				format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					date = format.parse(text);
				} catch (ParseException e1) {
					format = new SimpleDateFormat("yyyy/MM/dd H:mm");
					try {
						date = format.parse(text);
					} catch (ParseException e2) {
						e2.printStackTrace();
					}
				}
			}
			setValue(date);
		}
	}

	/**
	 * 响应返回结果
	 * 
	 * @param rows 影响行数
	 * @return 操作结果
	 */
	protected AjaxResult toAjax(int rows) {
		return rows > 0 ? success() : error();
	}

	/**
	 * 返回成功
	 */
	public AjaxResult success() {
		return AjaxResult.success();
	}

	/**
	 * 返回失败消息
	 */
	public AjaxResult error() {
		return AjaxResult.error();
	}

	public AjaxResult successData(int code, Object value) {
		AjaxResult json = new AjaxResult();
		json.put("code", code);
		json.put("data", value);
		return json;
	}

	/**
	 * 返回成功消息
	 */
	public AjaxResult success(String message) {
		return AjaxResult.success(message);
	}

	/**
	 * 返回失败消息
	 */
	public AjaxResult error(String message) {
		return AjaxResult.error(message);
	}

	/**
	 * 返回错误码消息
	 */
	public AjaxResult error(int code, String message) {
		return AjaxResult.error(code, message);
	}

	/**
	 * 返回object数据
	 */
	public AjaxResult retobject(int code, Object data) {
		return AjaxResult.successData(code, data);
	}

}
