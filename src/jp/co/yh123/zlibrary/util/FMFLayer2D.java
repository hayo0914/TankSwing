package src.jp.co.yh123.zlibrary.util;

public class FMFLayer2D {
	/**
	 * データテーブル
	 */
	int[] data;
	/**
	 * 幅
	 */
	int width;
	/**
	 * 高さ
	 */
	int height;

	int wkPtr = 0;

	public FMFLayer2D(int width, int height, byte[] data, int startIndex,
			int endIndex) {
		this.width = width;
		this.height = height;
		wkPtr = 0;
		this.data = new int[endIndex - startIndex];
		System.out.println(width + "*" + height + ":" + data.length + ":str:"
				+ startIndex + ":end:" + endIndex);
		for (int i = startIndex; i < endIndex; i++) {
			this.data[wkPtr] = 0xff & data[i];
			wkPtr++;
		}
		System.out.println("layerReadEnd:" + ":" + this.data.length);
	}

	int wk = 0;

	/**
	 * 座標を指定して値を取得
	 * 
	 * @param x,
	 *            y 座標
	 */
	public int get(int x, int y) {
		if (x < 0 || width <= x || y < 0 || height <= y)
			return -1;
		// 場合によっては例外吐いてもいいかも
		wk = y * width + x;
		if (wk < data.length) {
			return data[wk];
		}
		return 0;
	}

	/**
	 * デバッグ出力
	 */
	public void dump() {
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				System.out.print(get(i, j));
				System.out.print(",");
			}
			System.out.println("\n");
		}
	}
}
