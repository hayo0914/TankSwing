package src.jp.co.yh123.zlibrary.platform;

public class KeyState {
	public static final int KEY_1 = 0;

	public static final int KEY_2 = 1;

	public static final int KEY_3 = 2;

	public static final int KEY_4 = 3;

	public static final int KEY_5 = 4;

	public static final int KEY_6 = 5;

	public static final int KEY_7 = 6;

	public static final int KEY_8 = 7;

	public static final int KEY_9 = 8;

	public static final int KEY_0 = 9;

	public static final int KEY_AST = 10;

	public static final int KEY_SHP = 11;

	public static final int KEY_CLR = 12;

	public static final int KEY_SFT1 = 13;

	public static final int KEY_SFT2 = 14;

	public static final int KEY_UP = 15;

	public static final int KEY_DOWN = 16;

	public static final int KEY_LEFT = 17;

	public static final int KEY_RIGHT = 18;

	public static final int KEY_ENTER = 19;

	public static final int COUNT_KEYS = 20;

	private static KeyState instance = null;

	private int[] keyPushCount = null;

	private boolean[] keyPushState = null;

	private boolean pushed = false;

	private boolean released = false;

	private static final long longPushJudgeTime = 2000l;

	private int lastPushKey = -1;

	private long pushCount = 0;

	private long lastClearTime = 0;

	static KeyState get() {
		if (instance == null) {
			createInstance();
		}
		return instance;
	}

	private static void createInstance() {
		instance = new KeyState();
	}

	private KeyState() {
		keyPushCount = new int[COUNT_KEYS];
		for (int i = 0; i < keyPushCount.length; i++) {
			keyPushCount[i] = 0;
		}
		keyPushState = new boolean[COUNT_KEYS];
		for (int i = 0; i < keyPushState.length; i++) {
			keyPushState[i] = false;
		}
	}

	void pushed(int key) {
		keyPushState[key] = true;
		pushed = true;
		lastPushKey = key;
		pushCount = 0;
	}

	void released(int key) {
		keyPushState[key] = true;
		if (keyPushState[key] == true) {
			keyPushCount[key]++;
		}
		released = true;
		keyPushState[key] = false;
		lastPushKey = -1;
		pushCount = 0;
	}

	public int chkKeyPushCount(int key) {
		return keyPushCount[key];
	}

	public boolean chkKeyPushState(int key) {
		return keyPushState[key];
	}

	public boolean pushed() {
		return pushed;
	}

	public boolean released() {
		return released;
	}

	void clearKeyPushCount() {
		for (int i = 0; i < keyPushCount.length; i++) {
			keyPushCount[i] = 0;
		}
		pushed = false;
		released = false;
	}

	// void clearKeyPushState() {
	// for (int i = 0; i < keyPushState.length; i++) {
	// keyPushState[i] = false;
	// }
	// if (lastPushKey != -1) {
	// pushCount += System.currentTimeMillis() - lastClearTime;
	// }
	// }

	public boolean chkLongPushed(int key) {
		if (lastPushKey == -1) {
			return false;
		}
		if (key == lastPushKey && pushCount >= longPushJudgeTime) {
			lastPushKey = -1;
			pushCount = 0;
			return true;
		} else {
			return false;
		}
	}

	public void dump() {
		for (int i = 0; i < keyPushCount.length; i++) {
			System.out.println(keyPushCount[i]);
		}
	}

}
