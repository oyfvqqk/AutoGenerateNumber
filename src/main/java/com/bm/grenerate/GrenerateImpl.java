package com.bm.grenerate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.bm.help.ThreadPool;
import com.bm.util.StringUtils;

/**
 * 
 * @author Administrator
 * @Description: 编号生成接口实现类
 * @date 2017年5月6日 下午3:15:23
 *
 */
public class GrenerateImpl implements Grenerate {

	public String getNumberByRule(String rule) {

		if (StringUtils.isNotEmpty(rule)) {
			try {
				String[] rules = rule.split(";");
				StringBuilder sb = new StringBuilder();
				ConcurrentMap<Integer, String> map = new ConcurrentHashMap<Integer, String>();
//				synchronized (rule) {	// 强调顺序时开启，高并发时会降低效率
					for (int i = 0; i < rules.length; i++) {
						// 设置序号默认别名
						if (rules[i].indexOf("NUM") == 0 || rule.indexOf("num") == 0) {
							rules[i] += rule;
						}
						ThreadPool.runThread(new rulesAnalysisThread(rules[i], i, map));
						// new rulesAnalysisThread(rules[i], i, map).run();
					}
					while (map.size() != rules.length) {
						Thread.sleep(1);
					}
//				}
				for (int i = 0; i < rules.length; i++) {
					sb.append(map.get(i));
				}

				return sb.toString();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

}
