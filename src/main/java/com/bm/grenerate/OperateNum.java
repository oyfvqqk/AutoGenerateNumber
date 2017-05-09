package com.bm.grenerate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 
 * @author Administrator
 * @Description: 初始化和获得序号
 *
 */
public class OperateNum {

	private static ConcurrentMap<String, Integer> numMap;
	private static boolean noInit = true;
	static {
		numMap = new ConcurrentHashMap<>();
	}

	private OperateNum() {
	}

	protected static ConcurrentMap<String, Integer> getNumMap() {
		return numMap;
	}

	/**
	 * @Description: 初始化序号（只能一次）
	 * @param map
	 */
	public static void initNumMap(ConcurrentMap<String, Integer> map) {
		synchronized (numMap) {
			if (noInit) {
				numMap = map;
				noInit = false;
			}
		}
	}

	/**
	 * @Description: 获得规则对应的序号
	 * @param rule
	 * @return
	 */
	public static Integer getNum(String rule) {
		return numMap.get(rule);
	}
}
