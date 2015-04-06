package src.jp.co.yh123.zlibrary.platform;

import src.jp.co.yh123.zlibrary.util.IGameThread;

public class GameThread implements IGameThread, Runnable {

	private Thread th = null;

	private long _frameTime = 0;

	private long _afterTime = 0;

	private long _frameCnt = 0;

	private long _calcInterval = 0;

	private long _beforeTime = System.currentTimeMillis();

	private long _prevCalcTime = _beforeTime;

	private long _culcSleepTime = 0;

	private int fps = 0;

	private boolean isPaused = false;

	private boolean isDestroyed = false;

	private IRunnable _target;

	public GameThread(long sleepTime, IRunnable target) {
		this._frameTime = sleepTime;
		th = new Thread(this);
		_target = target;
	}

	public void start() throws Exception {
		th.start();
	}

	public void run() {

		_afterTime = 0;
		_frameCnt = 0;
		_calcInterval = 0;
		_beforeTime = System.currentTimeMillis();
		_prevCalcTime = _beforeTime;
		_culcSleepTime = 0;
		loop: while (true) {

			_culcSleepTime = _frameTime;
			if (!isPaused) {
				try {
					_target.update();
				} catch (Exception e) {
					e.printStackTrace();
				}
				_afterTime = System.currentTimeMillis();
				_culcSleepTime = _frameTime - (_afterTime - _beforeTime);
			}

			if (isDestroyed) {
				break loop;
			}

			if (_culcSleepTime > 0) {
				try {
					Thread.sleep(_culcSleepTime);
				} catch (Exception e) {
				}
			}
			_beforeTime = System.currentTimeMillis();

			calcFPS();
		}
	}

	public void pause() {
		isPaused = true;
	}

	public void resume() {
		isPaused = false;
	}

	public void destroy() {
		isDestroyed = true;
	}

	public int getFPS() {
		return fps;
	}

	private void calcFPS() {

		_frameCnt++;
		_calcInterval += _frameTime;

		if (_calcInterval >= 1000) {
			long timeNow = System.currentTimeMillis();

			fps = (int) (_frameCnt * 1000 / (timeNow - _prevCalcTime));

			_frameCnt = 0;
			_calcInterval = 0;
			_prevCalcTime = timeNow;
		}
	}

}
