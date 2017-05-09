package com.bm.grenerate;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.bm.util.DateUtils;
import com.bm.util.StringUtils;

import net.sf.json.JSONObject;

/**
 * 
 * @author Administrator
 * @Description: 规则解析类
 *
 */
class rulesAnalysisThread implements Runnable {

	private String rule;
	private int no;
	private ConcurrentMap<Integer, String> map;

	protected rulesAnalysisThread(String rule, int no, ConcurrentMap<Integer, String> map) {
		this.rule = rule;
		this.no = no;
		this.map = map;
	}

	public void run() {
		if (rule == null || map == null) {
			return;
		}
		try {
			map.put(no, classify(rule));
		} catch (Exception e) {
			new Exception("规则参数错误：" + rule).printStackTrace();
		}
	}

	private String classify(String rule) throws Exception {
		if (rule.indexOf("'") == 0 && rule.lastIndexOf("'") == rule.length() - 1) {
			return fixed(rule);
		} else if (rule.indexOf("NUM") == 0 || rule.indexOf("num") == 0) {
			return num(rule);
		} else {
			return date(rule);
		}
	}

	/**
	 * @Description: 固定值
	 * @param rule
	 * @return
	 */
	private String fixed(String rule) {
		return rule.substring(1, rule.length() - 1);
	}

	/**
	 * @Description: 序号
	 * @param rule
	 * @return
	 */
	private String num(String rule) {
		Integer num, step, size = null;
		String returnString, name, unit = null, addStr = null,
				jsonStr = rule.substring(rule.indexOf("{"), rule.indexOf("}") + 1);
		StringBuilder add = new StringBuilder();
		Map<String, Integer> numMap = OperateNum.getNumMap();
		// 解析序号的json字符串
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		if (jsonObject.containsKey("unit")) {
			unit = jsonObject.getString("unit");
		}
		if (jsonObject.containsKey("name")) {
			name = jsonObject.getString("name");
		} else {
			name = rule.substring(rule.indexOf(jsonStr) + jsonStr.length());
		}
		if (jsonObject.containsKey("step")) {
			step = jsonObject.getInt("step");
		} else {
			step = 1;
		}
		if (jsonObject.containsKey("size")) {
			size = jsonObject.getInt("size");
		}
		// 每次增加步长的数字
		synchronized (numMap) {
			num = numMap.get(name);
			if (num != null) {
				num += step;
			} else {
				num = 1;
			}
			numMap.put(name, num);
		}
		// 中文化
		if ("U".equalsIgnoreCase(unit)) {
			returnString = StringUtils.unitIntegerNum(num);
		} else if ("C".equalsIgnoreCase(unit)) {
			returnString = StringUtils.chineseNum(num);
			addStr = "零";
		} else {
			returnString = num.toString();
			addStr = "0";
		}
		// 更改长度
		if (size != null && StringUtils.isNotEmpty(addStr)) {
			int length = returnString.length();
			if (size > length) {
				for (int i = 0; i < size - length; i++) {
					add.append(addStr);
				}
			} else {
				returnString = returnString.substring(length - size, length);
			}
			returnString = add.append(returnString).toString();
		}
		return returnString;
	}

	/**
	 * @Description: 时间相关
	 * @param rule
	 * @return
	 * @throws Exception
	 */
	private String date(String rule) throws Exception {
		String returnString = null;
		String[] rules = rule.split(" ");
		if ("YEAR".equalsIgnoreCase(rules[0])) {
			returnString = DateUtils.formatToday(DateUtils.YEAR);
		} else if ("MONTH".equalsIgnoreCase(rules[0])) {
			returnString = DateUtils.formatToday(DateUtils.MONTH);
		} else if ("DAY".equalsIgnoreCase(rules[0])) {
			returnString = DateUtils.formatToday(DateUtils.DAY);
		} else if ("HOUR".equalsIgnoreCase(rules[0])) {
			returnString = DateUtils.formatToday(DateUtils.HOUR);
		} else if ("MINUTE".equalsIgnoreCase(rules[0])) {
			returnString = DateUtils.formatToday(DateUtils.MINUTE);
		} else if ("SECONDS".equalsIgnoreCase(rules[0])) {
			returnString = DateUtils.formatToday(DateUtils.SECONDS);
		}
		if (StringUtils.isNotEmpty(returnString)) {
			String lastString = rules[rules.length - 1];
			if ("U".equalsIgnoreCase(lastString)) {
				returnString = StringUtils.unitNum(returnString);
			} else if ("C".equalsIgnoreCase(lastString)) {
				returnString = StringUtils.chineseNum(returnString);
			}
			return returnString;
		}
		throw new Exception();
	}
}
