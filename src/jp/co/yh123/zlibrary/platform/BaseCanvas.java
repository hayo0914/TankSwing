package src.jp.co.yh123.zlibrary.platform;
//package src.jp.co.yh123.zcore.graphic;
//
//import javax.microedition.lcdui.Canvas;
//import javax.microedition.lcdui.game.GameCanvas;
//
//import src.jp.co.yh123.zcore.userinteraction.ActionEventAdapter;
//
///**
// * �L�����o�X���ʂ̃��C���N���X�ł��B<br>
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
//	 * ������L�[��\���܂��B
//	 */
//	public static final int UP = Canvas.UP;
//
//	/**
//	 * �������L�[��\���܂��B
//	 */
//	public static final int DOWN = Canvas.DOWN;
//
//	/**
//	 * �������L�[��\���܂��B
//	 */
//	public static final int LEFT = Canvas.LEFT;
//
//	/**
//	 * �E�����L�[��\���܂��B
//	 */
//	public static final int RIGHT = Canvas.RIGHT;
//
//	/**
//	 * ����L�[��\���܂��B
//	 */
//	public static final int SELECT = FIRE;
//
//	/**
//	 * 0�L�[��\���܂��B
//	 */
//	public static final int KEY_0 = KEY_NUM0;
//
//	/**
//	 * 1�L�[��\���܂��B
//	 */
//	public static final int KEY_1 = KEY_NUM1;
//
//	/**
//	 * 2�L�[��\���܂��B
//	 */
//	public static final int KEY_2 = KEY_NUM2;
//
//	/**
//	 * 3�L�[��\���܂��B
//	 */
//	public static final int KEY_3 = KEY_NUM3;
//
//	/**
//	 * 4�L�[��\���܂��B
//	 */
//	public static final int KEY_4 = KEY_NUM4;
//
//	/**
//	 * 5�L�[��\���܂��B
//	 */
//	public static final int KEY_5 = KEY_NUM5;
//
//	/**
//	 * 6�L�[��\���܂��B
//	 */
//	public static final int KEY_6 = KEY_NUM6;
//
//	/**
//	 * 7�L�[��\���܂��B
//	 */
//	public static final int KEY_7 = KEY_NUM7;
//
//	/**
//	 * 8�L�[��\���܂��B
//	 */
//	public static final int KEY_8 = KEY_NUM8;
//
//	/**
//	 * 9�L�[��\���܂��B
//	 */
//	public static final int KEY_9 = KEY_NUM9;
//
//	/**
//	 * *�L�[��\���܂��B
//	 */
//	public static final int KEY_AST = KEY_STAR;
//
//	/**
//	 * #�L�[��\���܂��B
//	 */
//	public static final int KEY_PND = KEY_POUND;
//
//	/**
//	 * �N���A�L�[��\���܂��B
//	 */
//	public static final int KEY_CLR = 0;
//
//	/**
//	 * �\�t�g�L�[1��\���܂��B
//	 */
//	public static final int KEY_SOFT1 = -21;
//
//	/**
//	 * �\�t�g�L�[2��\���܂��B
//	 */
//	public static final int KEY_SOFT2 = -22;
//
//	/**
//	 * �����L�[��������Ă��Ȃ���Ԃ�\���܂��B
//	 */
//	public static final int KEY_NONE = -999;
//
//	/**
//	 * �L�[�v���X�C�x���g���������܂��B
//	 */
//	public void keyPressed(int keyCode) {
//		ActionEventAdapter.keyPressed(keyCode);
//	}
//
//	/**
//	 * �L�[�����[�X�C�x���g���������܂��B
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
