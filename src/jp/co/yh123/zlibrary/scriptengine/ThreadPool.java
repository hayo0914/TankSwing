package src.jp.co.yh123.zlibrary.scriptengine;

import src.jp.co.yh123.zlibrary.util.List;

public class ThreadPool {

	private List pool = null;

	public ThreadPool(int maxThread) {
		pool = new List(maxThread);
		for (int i = 0; i < maxThread; i++) {
			pool.add(new Thread());
		}
	}

	public Thread getThead() {
		Thread th = (Thread) pool.elementAt(0);
		th.init();
		return th;
	}

	public int availableTheads() {
		return pool.size();
	}

	public void returnThread(Thread th) {
		pool.add(th);
	}

}
