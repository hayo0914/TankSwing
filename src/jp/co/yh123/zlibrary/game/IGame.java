package src.jp.co.yh123.zlibrary.game;

import src.jp.co.yh123.zlibrary.scene.ISceneInterface;

public interface IGame {

	public void startGame(long sleepTime, ISceneInterface firstScene, int viewWidth,
			int viewHeight) throws Exception;

	public void pauseGame() throws Exception;

	public void resumeGame() throws Exception;

	public void destroyGame() throws Exception;

}
