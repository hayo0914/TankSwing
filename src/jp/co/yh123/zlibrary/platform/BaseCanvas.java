package src.jp.co.yh123.zlibrary.platform;
//package src.jp.co.yh123.zcore.graphic;
//
//import javax.microedition.lcdui.Canvas;
//import javax.microedition.lcdui.game.GameCanvas;
//
//import src.jp.co.yh123.zcore.userinteraction.ActionEventAdapter;
//
///**
// * キャンバス共通のメインクラスです。<br>
// * 
// * @author Takuto Nishioka
// */
//public class BaseCanvas extends GameCanvas {
//
//	public BaseCanvas(boolean suppressKeyEvents) {
//		super(suppressKeyEvents);
//	}
//
//	/**
//	 * 上方向キーを表します。
//	 */
//	public static final int UP = Canvas.UP;
//
//	/**
//	 * 下方向キーを表します。
//	 */
//	public static final int DOWN = Canvas.DOWN;
//
//	/**
//	 * 左方向キーを表します。
//	 */
//	public static final int LEFT = Canvas.LEFT;
//
//	/**
//	 * 右方向キーを表します。
//	 */
//	public static final int RIGHT = Canvas.RIGHT;
//
//	/**
//	 * 決定キーを表します。
//	 */
//	public static final int SELECT = FIRE;
//
//	/**
//	 * 0キーを表します。
//	 */
//	public static final int KEY_0 = KEY_NUM0;
//
//	/**
//	 * 1キーを表します。
//	 */
//	public static final int KEY_1 = KEY_NUM1;
//
//	/**
//	 * 2キーを表します。
//	 */
//	public static final int KEY_2 = KEY_NUM2;
//
//	/**
//	 * 3キーを表します。
//	 */
//	public static final int KEY_3 = KEY_NUM3;
//
//	/**
//	 * 4キーを表します。
//	 */
//	public static final int KEY_4 = KEY_NUM4;
//
//	/**
//	 * 5キーを表します。
//	 */
//	public static final int KEY_5 = KEY_NUM5;
//
//	/**
//	 * 6キーを表します。
//	 */
//	public static final int KEY_6 = KEY_NUM6;
//
//	/**
//	 * 7キーを表します。
//	 */
//	public static final int KEY_7 = KEY_NUM7;
//
//	/**
//	 * 8キーを表します。
//	 */
//	public static final int KEY_8 = KEY_NUM8;
//
//	/**
//	 * 9キーを表します。
//	 */
//	public static final int KEY_9 = KEY_NUM9;
//
//	/**
//	 * *キーを表します。
//	 */
//	public static final int KEY_AST = KEY_STAR;
//
//	/**
//	 * #キーを表します。
//	 */
//	public static final int KEY_PND = KEY_POUND;
//
//	/**
//	 * クリアキーを表します。
//	 */
//	public static final int KEY_CLR = 0;
//
//	/**
//	 * ソフトキー1を表します。
//	 */
//	public static final int KEY_SOFT1 = -21;
//
//	/**
//	 * ソフトキー2を表します。
//	 */
//	public static final int KEY_SOFT2 = -22;
//
//	/**
//	 * 何もキーが押されていない状態を表します。
//	 */
//	public static final int KEY_NONE = -999;
//
//	/**
//	 * キープレスイベントを処理します。
//	 */
//	public void keyPressed(int keyCode) {
//		ActionEventAdapter.keyPressed(keyCode);
//	}
//
//	/**
//	 * キーリリースイベントを処理します。
//	 */
//	public void keyReleased(int keyCode) {
//		ActionEventAdapter.keyReleased(keyCode);
//	}
//
//	public G getGraphic() {
//		G g = new G();
//		g.setGraphics(super.getGraphics());
//		return g;
//	}
//
//	public void keyPressedCheck() {
//		ActionEventAdapter.keyPressedCheck(getKeyStates());
//	}
//
//}
