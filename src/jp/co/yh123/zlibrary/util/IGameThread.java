package src.jp.co.yh123.zlibrary.util;

public interface IGameThread {

	public static interface IRunnable {
		public void update() throws Exception;
	}

	public void start() throws Exception;

	public void pause() throws Exception;

	public void resume() throws Exception;

	public void destroy() throws Exception;

	public int getFPS();

}
