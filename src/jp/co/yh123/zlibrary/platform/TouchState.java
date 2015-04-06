package src.jp.co.yh123.zlibrary.platform;

public class TouchState {
	public static final int ACTION_CANCEL = 0;

	public static final int ACTION_DOWN = 1;

	public static final int ACTION_MASK = 2;

	public static final int ACTION_MOVE = 3;

	public static final int ACTION_OUTSIDE = 4;

	public static final int ACTION_POINTER_1_DOWN = 5;

	public static final int ACTION_POINTER_1_UP = 6;

	public static final int ACTION_POINTER_2_DOWN = 7;

	public static final int ACTION_POINTER_2_UP = 8;

	public static final int ACTION_POINTER_3_DOWN = 9;

	public static final int ACTION_POINTER_3_UP = 10;

	public static final int ACTION_POINTER_DOWN = 11;

	public static final int ACTION_POINTER_ID_MASK = 12;

	public static final int ACTION_POINTER_ID_SHIFT = 13;

	public static final int ACTION_POINTER_INDEX_MASK = 14;

	public static final int ACTION_POINTER_INDEX_SHIFT = 15;

	public static final int ACTION_POINTER_UP = 16;

	public static final int ACTION_UP = 17;

	public static final int EDGE_BOTTOM = 18;

	public static final int EDGE_LEFT = 19;

	public static final int EDGE_RIGHT = 20;

	public static final int EDGE_TOP = 21;

	private static TouchState instance = null;

	static TouchState get() {
		if (instance == null) {
			createInstance();
		}
		return instance;
	}

	static void createInstance() {
		instance = new TouchState();
	}

	public boolean isTouched() {
		return false;
	}

	public boolean isReleased() {
		return false;
	}

	private int x = 0;

	private int y = 0;

	public int getX() {
		return x;
	}

	void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	void setY(int y) {
		this.y = y;
	}

}
