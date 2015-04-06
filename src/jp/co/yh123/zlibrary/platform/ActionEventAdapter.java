package src.jp.co.yh123.zlibrary.platform;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ActionEventAdapter {

	private static KeyState key = KeyState.get();

	private static TouchState touch = TouchState.get();

	public static boolean checkTouchEvent() {
		// TODO:実装
		return false;
	}

	public static KeyState getKeyState() {
		return key;
	}

	public static TouchState getTouchState() {
		return touch;
	}

	public static void clearKeyPushCount() {
		key.clearKeyPushCount();
	}

	public static class PojoKeyListner implements KeyListener {

		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				// テンキー操作
				key.pushed(KeyState.KEY_UP);
				key.pushed(KeyState.KEY_UP);
				break;
			case KeyEvent.VK_DOWN:
				// テンキー操作
				key.pushed(KeyState.KEY_DOWN);
				break;
			case KeyEvent.VK_LEFT:
				// テンキー操作
				key.pushed(KeyState.KEY_LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				// テンキー操作
				key.pushed(KeyState.KEY_RIGHT);
				break;
			case KeyEvent.VK_ENTER:
				// メニュー表示
				key.pushed(KeyState.KEY_ENTER);
				break;
			case KeyEvent.VK_SPACE:
				// テンキー操作
				key.pushed(KeyState.KEY_ENTER);
				break;
			case KeyEvent.VK_NUMPAD0:
				key.pushed(KeyState.KEY_0);
				break;
			case KeyEvent.VK_NUMPAD1:
				// テンキー操作
				key.pushed(KeyState.KEY_1);
				break;
			case KeyEvent.VK_NUMPAD2:
				// テンキー操作
				key.pushed(KeyState.KEY_2);
				key.pushed(KeyState.KEY_DOWN);
				break;
			case KeyEvent.VK_NUMPAD3:
				// テンキー操作
				key.pushed(KeyState.KEY_3);
				break;
			case KeyEvent.VK_NUMPAD4:
				// テンキー操作
				key.pushed(KeyState.KEY_4);
				key.pushed(KeyState.KEY_LEFT);
				break;
			case KeyEvent.VK_NUMPAD5:
				// テンキー操作
				key.pushed(KeyState.KEY_5);
				key.pushed(KeyState.KEY_ENTER);
				break;
			case KeyEvent.VK_NUMPAD6:
				// テンキー操作
				key.pushed(KeyState.KEY_6);
				key.pushed(KeyState.KEY_RIGHT);
				break;
			case KeyEvent.VK_NUMPAD7:
				// テンキー操作
				key.pushed(KeyState.KEY_7);
				break;
			case KeyEvent.VK_NUMPAD8:
				// テンキー操作
				key.pushed(KeyState.KEY_8);
				key.pushed(KeyState.KEY_UP);
				break;
			case KeyEvent.VK_NUMPAD9:
				// テンキー操作
				key.pushed(KeyState.KEY_9);
				break;
			case KeyEvent.VK_W:
				// テンキー操作
				key.pushed(KeyState.KEY_UP);
				key.pushed(KeyState.KEY_8);
				break;
			case KeyEvent.VK_S:
				// テンキー操作
				key.pushed(KeyState.KEY_DOWN);
				key.pushed(KeyState.KEY_2);
				break;
			case KeyEvent.VK_A:
				// テンキー操作
				key.pushed(KeyState.KEY_LEFT);
				key.pushed(KeyState.KEY_4);
				break;
			case KeyEvent.VK_D:
				// テンキー操作
				key.pushed(KeyState.KEY_RIGHT);
				key.pushed(KeyState.KEY_6);
				break;
			case KeyEvent.VK_Q:
				// テンキー操作
				key.pushed(KeyState.KEY_7);
				break;
			case KeyEvent.VK_E:
				// テンキー操作
				key.pushed(KeyState.KEY_9);
				break;
			case KeyEvent.VK_Z:
				// テンキー操作
				key.pushed(KeyState.KEY_1);
				break;
			case KeyEvent.VK_X:
				// テンキー操作
				key.pushed(KeyState.KEY_3);
				break;
			case KeyEvent.VK_F:
				// 足元メニュー
				key.pushed(KeyState.KEY_AST);
				break;
			case KeyEvent.VK_R:
				// 足踏
				key.pushed(KeyState.KEY_SHP);
				break;
			case KeyEvent.VK_1:
				// 方向選択
				key.pushed(KeyState.KEY_SFT1);
				break;
			case KeyEvent.VK_2:
				// 発射
				key.pushed(KeyState.KEY_SFT2);
				break;
			default:
				break;
			}

		}

		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				key.released(KeyState.KEY_UP);
				break;
			case KeyEvent.VK_DOWN:
				key.released(KeyState.KEY_DOWN);
				break;
			case KeyEvent.VK_LEFT:
				key.released(KeyState.KEY_LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				key.released(KeyState.KEY_RIGHT);
				break;
			case KeyEvent.VK_ENTER:
				key.released(KeyState.KEY_ENTER);
				break;
			case KeyEvent.VK_SPACE:
				key.released(KeyState.KEY_ENTER);
				break;
			case KeyEvent.VK_NUMPAD0:
				key.released(KeyState.KEY_0);
				break;
			case KeyEvent.VK_NUMPAD1:
				key.released(KeyState.KEY_1);
				break;
			case KeyEvent.VK_NUMPAD2:
				key.released(KeyState.KEY_2);
				key.released(KeyState.KEY_DOWN);
				break;
			case KeyEvent.VK_NUMPAD3:
				key.released(KeyState.KEY_3);
				break;
			case KeyEvent.VK_NUMPAD4:
				key.released(KeyState.KEY_4);
				key.released(KeyState.KEY_LEFT);
				break;
			case KeyEvent.VK_NUMPAD5:
				key.released(KeyState.KEY_5);
				key.released(KeyState.KEY_ENTER);
				break;
			case KeyEvent.VK_NUMPAD6:
				key.released(KeyState.KEY_6);
				key.released(KeyState.KEY_RIGHT);
				break;
			case KeyEvent.VK_NUMPAD7:
				key.released(KeyState.KEY_7);
				break;
			case KeyEvent.VK_NUMPAD8:
				key.released(KeyState.KEY_8);
				key.released(KeyState.KEY_UP);
				break;
			case KeyEvent.VK_NUMPAD9:
				key.released(KeyState.KEY_9);
				break;
			case KeyEvent.VK_W:
				key.released(KeyState.KEY_UP);
				key.released(KeyState.KEY_8);
				break;
			case KeyEvent.VK_S:
				key.released(KeyState.KEY_DOWN);
				key.released(KeyState.KEY_2);
				break;
			case KeyEvent.VK_A:
				key.released(KeyState.KEY_LEFT);
				key.released(KeyState.KEY_4);
				break;
			case KeyEvent.VK_D:
				key.released(KeyState.KEY_RIGHT);
				key.released(KeyState.KEY_6);
				break;
			case KeyEvent.VK_Q:
				key.released(KeyState.KEY_7);
				break;
			case KeyEvent.VK_E:
				key.released(KeyState.KEY_9);
				break;
			case KeyEvent.VK_Z:
				key.released(KeyState.KEY_1);
				break;
			case KeyEvent.VK_X:
				key.released(KeyState.KEY_3);
				break;
			case KeyEvent.VK_F:
				key.released(KeyState.KEY_AST);
				break;
			case KeyEvent.VK_R:
				key.released(KeyState.KEY_SHP);
				break;
			case KeyEvent.VK_1:
				key.released(KeyState.KEY_SFT1);
				break;
			case KeyEvent.VK_2:
				key.released(KeyState.KEY_SFT2);
				break;
			default:
				break;
			}

		}

		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}
	//
	// /**
	// * キープレスイベントを処理します。
	// */
	// public static void keyPressed(int keyCode) {
	// if (Canvas.KEY_NUM0 == keyCode) {
	// KeyState.get().pushed(KeyState.KEY_0);
	// } else if (Canvas.KEY_NUM1 == keyCode) {
	// KeyState.get().pushed(KeyState.KEY_1);
	// } else if (Canvas.KEY_NUM2 == keyCode) {
	// KeyState.get().pushed(KeyState.KEY_2);
	// } else if (Canvas.KEY_NUM3 == keyCode) {
	// KeyState.get().pushed(KeyState.KEY_3);
	// } else if (Canvas.KEY_NUM4 == keyCode) {
	// KeyState.get().pushed(KeyState.KEY_4);
	// } else if (Canvas.KEY_NUM5 == keyCode) {
	// KeyState.get().pushed(KeyState.KEY_5);
	// } else if (Canvas.KEY_NUM6 == keyCode) {
	// KeyState.get().pushed(KeyState.KEY_6);
	// } else if (Canvas.KEY_NUM7 == keyCode) {
	// KeyState.get().pushed(KeyState.KEY_7);
	// } else if (Canvas.KEY_NUM8 == keyCode) {
	// KeyState.get().pushed(KeyState.KEY_8);
	// } else if (Canvas.KEY_NUM9 == keyCode) {
	// KeyState.get().pushed(KeyState.KEY_9);
	// } else if (Canvas.KEY_STAR == keyCode) {
	// KeyState.get().pushed(KeyState.KEY_AST);
	// } else if (Canvas.KEY_POUND == keyCode) {
	// KeyState.get().pushed(KeyState.KEY_SHP);
	// }
	// }
	//
	// /**
	// * キーリリースイベントを処理します。
	// */
	// public static void keyReleased(int keyCode) {
	// if (Canvas.KEY_NUM0 == keyCode) {
	// KeyState.get().released(KeyState.KEY_0);
	// } else if (Canvas.KEY_NUM1 == keyCode) {
	// KeyState.get().released(KeyState.KEY_1);
	// } else if (Canvas.KEY_NUM2 == keyCode) {
	// KeyState.get().released(KeyState.KEY_2);
	// } else if (Canvas.KEY_NUM3 == keyCode) {
	// KeyState.get().released(KeyState.KEY_3);
	// } else if (Canvas.KEY_NUM4 == keyCode) {
	// KeyState.get().released(KeyState.KEY_4);
	// } else if (Canvas.KEY_NUM5 == keyCode) {
	// KeyState.get().released(KeyState.KEY_5);
	// } else if (Canvas.KEY_NUM6 == keyCode) {
	// KeyState.get().released(KeyState.KEY_6);
	// } else if (Canvas.KEY_NUM7 == keyCode) {
	// KeyState.get().released(KeyState.KEY_7);
	// } else if (Canvas.KEY_NUM8 == keyCode) {
	// KeyState.get().released(KeyState.KEY_8);
	// } else if (Canvas.KEY_NUM9 == keyCode) {
	// KeyState.get().released(KeyState.KEY_9);
	// } else if (Canvas.KEY_STAR == keyCode) {
	// KeyState.get().released(KeyState.KEY_AST);
	// } else if (Canvas.KEY_POUND == keyCode) {
	// KeyState.get().released(KeyState.KEY_SHP);
	// }
	// }
	//
	// public static void keyPressedCheck(int keycode) {
	// /* 上キー */
	// if ((GameCanvas.UP_PRESSED & keycode) != 0) {
	// KeyState.get().pushed(KeyState.KEY_UP);
	// } else {
	// KeyState.get().released(KeyState.KEY_UP);
	// }
	// /* 下キー */
	// if ((GameCanvas.DOWN_PRESSED & keycode) != 0) {
	// KeyState.get().pushed(KeyState.KEY_DOWN);
	// } else {
	// KeyState.get().released(KeyState.KEY_DOWN);
	// }
	// /* 右キー */
	// if ((GameCanvas.RIGHT_PRESSED & keycode) != 0) {
	// KeyState.get().pushed(KeyState.KEY_RIGHT);
	// } else {
	// KeyState.get().released(KeyState.KEY_RIGHT);
	// }
	// /* 左キー */
	// if ((GameCanvas.LEFT_PRESSED & keycode) != 0) {
	// KeyState.get().pushed(KeyState.KEY_LEFT);
	// } else {
	// KeyState.get().released(KeyState.KEY_LEFT);
	// }
	// /* ファイアキー */
	// if ((GameCanvas.FIRE_PRESSED & keycode) != 0) {
	// KeyState.get().pushed(KeyState.KEY_ENTER);
	// } else {
	// KeyState.get().released(KeyState.KEY_ENTER);
	// }
	// /* A */
	// if ((GameCanvas.GAME_A_PRESSED & keycode) != 0) {
	// KeyState.get().pushed(KeyState.KEY_1);
	// } else {
	// KeyState.get().released(KeyState.KEY_1);
	// }
	// /* B */
	// if ((GameCanvas.GAME_B_PRESSED & keycode) != 0) {
	// KeyState.get().pushed(KeyState.KEY_3);
	// } else {
	// KeyState.get().released(KeyState.KEY_3);
	// }
	// /* C */
	// if ((GameCanvas.GAME_C_PRESSED & keycode) != 0) {
	// KeyState.get().pushed(KeyState.KEY_7);
	// } else {
	// KeyState.get().released(KeyState.KEY_7);
	// }
	// /* D */
	// if ((GameCanvas.GAME_D_PRESSED & keycode) != 0) {
	// KeyState.get().pushed(KeyState.KEY_9);
	// } else {
	// KeyState.get().released(KeyState.KEY_9);
	// }
	// }
}
