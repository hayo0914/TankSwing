package src.jp.co.yh123.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;

import src.jp.co.yh123.tank.game.Main;
import src.jp.co.yh123.zlibrary.game.IGame;
import src.jp.co.yh123.zlibrary.platform.ActionEventAdapter;
import src.jp.co.yh123.zlibrary.platform.GameThread;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.scene.ISceneInterface;
import src.jp.co.yh123.zlibrary.scene.SceneManager;
import src.jp.co.yh123.zlibrary.scene.SceneManagerFactory;
import src.jp.co.yh123.zlibrary.util.IGameThread;

public class Game implements IGame {

	private int _viewWidth = 0;
	private int _viewHeight = 0;
	private SceneManager _sceneManager = null;
	private SwingFrame _frame = null;
	private static GameGraphic _g = null;
	private GameThread gameThread = null;

	public void startGame(long sleepTime, ISceneInterface firstScene, int viewWidth,
			int viewHeight) throws Exception {
		_viewWidth = viewWidth;
		_viewHeight = viewHeight;
		_frame = new SwingFrame(this, viewWidth, viewHeight);
		Graphics2D g2 = (Graphics2D) _frame.getPanelGraphic();
		_g = new GameGraphic();
		_g.setGraphics(g2);
		SceneManagerFactory.createInstance();
		_sceneManager = SceneManagerFactory.getSceneManager();
		_sceneManager.add(firstScene);
		gameThread = new GameThread(30l, new IGameThread.IRunnable() {
			public void update() throws Exception {
				try {
					// キーチェック＆キーイベント（MIDPのGameCanvasの場合）
					Main.keyPressCheck();
					// Sceneの更新
					_sceneManager.update();
					// キーのプッシュカウントをクリア
					ActionEventAdapter.clearKeyPushCount();
					// 描画
					_frame.repaintAll();
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
		});
		gameThread.start();
	}

	void draw() {
		try {
			if (_sceneManager != null) {
				_g.setColor(0x000000);
				_g.fillRect(0, 0, _viewWidth, _viewHeight);
				_sceneManager.draw(_g);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	void setGraphic(Graphics graphic) {
		_g.setGraphics((Graphics2D) graphic);
	}

	public void destroyGame() throws Exception {
		gameThread.destroy();
	}

	public void pauseGame() throws Exception {
		gameThread.pause();
	}

	public void resumeGame() throws Exception {
		gameThread.resume();

	}

}