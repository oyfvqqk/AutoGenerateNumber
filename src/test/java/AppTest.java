
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

import com.bm.grenerate.Grenerate;
import com.bm.grenerate.GrenerateImpl;
import com.bm.grenerate.OperateNum;
import com.bm.help.ThreadPool;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	public void testOne() throws InterruptedException {
		String rule = "'sdfs-';year c;month c;day c;'-';num{step:1,size:3,name:'test1'}";
		Integer number = 100; // 线程数
		CountDownLatch countDownLatch = new CountDownLatch(number);
		ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();
		map.put("test1", 11);
		OperateNum.initNumMap(map);
		for (int i = 0; i < number; i++) {
			ThreadPool.runThread(new Runnable() {
				String rule;
				CountDownLatch countDownLatch;

				public Runnable get(String s, CountDownLatch countDownLatch) {
					rule = s;
					this.countDownLatch = countDownLatch;
					return this;
				}

				@Override
				public void run() {
					Grenerate grenerate = new GrenerateImpl();
					System.out.println(grenerate.getNumberByRule(rule));
					countDownLatch.countDown();
				}
			}.get(rule, countDownLatch));
		}
		while (countDownLatch.getCount() != 0) {
			Thread.sleep(1);
		}
		ThreadPool.close();
	}
}
