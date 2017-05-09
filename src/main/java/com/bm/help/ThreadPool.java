package com.bm.help;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * @author Administrator
 * @Description: 线程池辅助类
 *
 */
public class ThreadPool {

	private static ExecutorService threadPool;

	static {
		threadPool = Executors.newCachedThreadPool();
	}

	public static void runThread(Runnable thread) {
		if (thread != null) {
			threadPool.execute(thread);
		}
	}

	public static Future<?> callThread(Callable<?> thread) {
		if (thread != null) {
			return threadPool.submit(thread);
		}
		return null;
	}
	
	public static void close(){
		threadPool.shutdown();
	}
}
