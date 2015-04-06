package src.jp.co.yh123.zlibrary.util;

public class IDArray {

	private int[] dataArray = null;

	private RandomValues random = null;

	public IDArray(String data, RandomValues random) throws Exception {
		this.random = random;
		StringTokenizer st = new StringTokenizer(data, "/");
		int size = st.countTokens();
		if (size <= 0) {
			return;
		}
		dataArray = new int[st.countTokens()];
		int index = 0;
		while (st.hasMoreElements()) {
			dataArray[index] = Integer.parseInt(st.nextToken());
			index++;
		}
		return;
	}

	public IDArray(String data) throws Exception {
		StringTokenizer st = new StringTokenizer(data, "/");
		int size = st.countTokens();
		if (size <= 0) {
			return;
		}
		dataArray = new int[st.countTokens()];
		int index = 0;
		while (st.hasMoreElements()) {
			dataArray[index] = Integer.parseInt(st.nextToken());
			index++;
		}
		return;
	}

	public void set(String data) throws Exception {
		StringTokenizer st = new StringTokenizer(data, "/");
		int size = st.countTokens();
		if (size <= 0) {
			return;
		}
		dataArray = new int[st.countTokens()];
		int index = 0;
		while (st.hasMoreElements()) {
			dataArray[index] = Integer.parseInt(st.nextToken());
			index++;
		}
		return;
	}

	public void dump() {
		for (int i = 0; i < dataArray.length; i++) {
			System.out.println(dataArray[i]);
		}
	}

	public int size() {
		if (dataArray == null) {
			return 0;
		}
		return dataArray.length;
	}

	public int get(int index) {
		return dataArray[index];
	}

	public int getRandomID() {
		return dataArray[random.getRandomInt(0, dataArray.length)];
	}
}
