
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
		String rule = "'sdfs-';year u;month c;day c;'(';hour;minute;seconds;')-';num{step:2,size:3,unit:'c',name:'test1'}";
		Integer number = 1000; // 线程数
		CountDownLatch countDownLatch = new CountDownLatch(number);
		ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();
		map.put("test1", 11);
		OperateNum.initNumMap(map);
		ExecutorService threadPool = Executors.newCachedThreadPool();
		for (int i = 0; i < number; i++) {
			threadPool.execute(new Runnable() {
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
