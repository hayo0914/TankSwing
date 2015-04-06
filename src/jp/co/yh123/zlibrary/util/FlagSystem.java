package src.jp.co.yh123.zlibrary.util;


public class FlagSystem {

	public static FlagSystem instance = null;;
	public static final int FLAG_NUM = 256;
	private static byte[] flag = null;

	private FlagSystem() {
		initFlag();
	}

	private FlagSystem(byte[] data) {
		flag = data;
	}

	public FlagSystem getInstance() {
		if (instance == null) {
			instance = new FlagSystem();
		}
		return instance;
	}

	public FlagSystem getInstance(byte[] data) {
		if (instance == null) {
			instance = new FlagSystem(data);
		}
		return instance;
	}

	public void initFlag() {
		flag = new byte[32];
		for (int i = 0; i < flag.length; i++) {
			flag[i] = 0;
		}
	}

	public void flagOn(int number) {
		ByteUtil.bitOn(flag, number);
	}

	public void flagOff(int number) {
		ByteUtil.bitOff(flag, number);
	}

	public boolean checkFlug(int number) {
		return ByteUtil.checkBit(flag, number);
	}

}
