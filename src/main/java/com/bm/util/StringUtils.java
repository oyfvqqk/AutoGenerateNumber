package com.bm.util;

/**
 * 
 * @author Administrator
 * @Description: 字符串处理类
 * @date 2017年5月6日 下午3:15:53
 *
 */
public class StringUtils {
	private static final String[] UNITS = { "", "十", "百", "千", "万", "亿" };
	private static final String[] CHINESENUMS = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };

	public static boolean isEmpty(String string) {
		if (string == null || "".equals(string)) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(String string) {
		return !isEmpty(string);
	}

	/**
	 * @Description: 数字中文化
	 * @param num
	 * @return
	 */
	public static String chineseNum(String num) {
		StringBuilder sb = new StringBuilder();
		if (num != null) {
			String[] nums = num.split("");
			for (int i = 0; i < nums.length; i++) {
				sb.append(CHINESENUMS[Integer.valueOf(nums[i])]);
			}
		}
		return sb.toString();
	}

	/**
	 * @Description: 数字中文化
	 * @param num
	 * @return
	 */
	public static String chineseNum(int num) {
		return chineseNum(String.valueOf(num));
	}

	public static String unitNum(String num) throws Exception {
		try {
			return unitIntegerNum(Integer.valueOf(num));
		} catch (Exception e) {
			throw new Exception("目前只支持int转换！");
		}
	}
	
	/**
	 * @Description: 带进制的数字中文化
	 * @param num
	 * @return
	 */
	public static String unitIntegerNum(int num) {
		// 个位数快速处理
		if (num < 10) {
			return CHINESENUMS[num];
		}
		// 定义变量
		boolean lastZero = false, specialUnit = false;
		StringBuilder sb = new StringBuilder();
		String unit = null;
		String[] nums = String.valueOf(num).split("");
		int n, m, len = nums.length;
		// 逐位转换
		for (int i = 0; i < len; i++) {
			n = Integer.valueOf(nums[i]);
			m = len - i;
			// 万和亿的处理
			if (specialUnit && m % 4 == 0) {
				unit = UNITS[m / 4 + 3];
				specialUnit = false;
				// 万和亿最后一位是零时，将单位插入到零前
				if (lastZero) {
					sb.insert(sb.length() - 1, unit);
					lastZero = false;
				} else {
					sb.append(unit);
				}
			}
			// 零的处理
			if (n == 0) {
				// 连续的零只需要一个
				if ("0".equals(nums[i - 1])) {
					continue;
				} else {
					sb.append(CHINESENUMS[n]);
				}
				lastZero = true;
			} else {
				if (m > 4) {
					specialUnit = true;
				}
				sb.append(CHINESENUMS[n]);
				sb.append(UNITS[(m - 1) % 4]);
				lastZero = false;
			}
		}
		// 最后一位是零就删除
		if (lastZero) {
			sb.delete(sb.length() - 1, sb.length());
		}
		// 去掉十前面的一
		if ("1".equals(nums[0]) && len % 4 == 2) {
			sb.delete(0, 1);
		}
		return sb.toString();
	}
}