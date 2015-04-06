package src.jp.co.yh123.tank.game;

import src.jp.co.yh123.swing.Game;
import src.jp.co.yh123.tank.logic.LogicScene;

public class Main {

	private static Game game = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		game = new Game();
		try {
			game.startGame(30l, new LogicScene(),
					GameConstants.SIZE_DISPLAY_WIDTH,
					GameConstants.SIZE_DISPLAY_HEIGHT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void keyPressCheck() {

	}

}
