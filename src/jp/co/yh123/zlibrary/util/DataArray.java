package src.jp.co.yh123.zlibrary.util;

import src.jp.co.yh123.zlibrary.platform.StringUtil;

public class DataArray {

	// データ形式(key=value)
	// A=25/B=35/

	private String[] key = null;
	private int[] value = null;

	public DataArray(String data) throws Exception {
		StringTokenizer st1 = new StringTokenizer(data, "/");
		int size = st1.countTokens();
		if (size <= 0) {
			return;
		}
		key = new String[size];
		value = new int[size];

		int index = -1;
		while (st1.hasMoreElements()) {
			index++;
			StringTokenizer st2 = new StringTokenizer(st1.nextToken(), "=");
			int count = -1;
			lp: while (st2.hasMoreElements()) {
				count++;
				switch (count) {
				case 0:
					key[index] = st2.nextToken();
					break;
				case 1:
					value[index] = StringUtil.toInt(st2.nextToken());
					break;
				case 2:
					break lp;
				}
			}
		}
		return;
	}

	public String getKey(int index) {
		return key[index];
	}

	public int getKeyInt(int index) throws Exception {
		return StringUtil.toInt(key[index]);
	}

	public int getValue(int index) {
		return value[index];
	}

	public int size() {
		if (key == null) {
			return 0;
		}
		return key.length;
	}

}
