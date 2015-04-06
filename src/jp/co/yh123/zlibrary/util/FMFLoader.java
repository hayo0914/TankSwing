package src.jp.co.yh123.zlibrary.util;

import src.jp.co.yh123.zlibrary.io.ResourceFileReader;

public class FMFLoader {
	/**
	 * 識別文字「FMF_」
	 */
	public char[] identifier;
	/**
	 * ヘッダを除いたデータサイズ
	 */
	public int datasize;
	/**
	 * マップの幅
	 */
	public int width;
	/**
	 * マップの高さ
	 */
	public int height;
	/**
	 * チップの幅
	 */
	public int chipwidth;
	/**
	 * チップの高さ
	 */
	public int chipheight;
	/**
	 * レイヤー数
	 */
	public int layercount;
	/**
	 * データのbit数(8/16)
	 */
	public int bitcount;
	/**
	 * レイヤー配列
	 */
	FMFLayer2D[] layerArray;
	private byte[] bytes;
	private int _ptr;

	/**
	 * コンストラクタ<br>
	 * TODO: マップデータは8bitのみ
	 * 
	 * @param filepath
	 *            FMFファイル
	 * @throw ファイルフォーマット例外
	 */
	public FMFLoader(String filepath) throws Exception {

		bytes = ResourceFileReader.readFile(filepath).getBData();

		_ptr = 0;
		_ptr += 4;

		datasize = readLong(); // ヘッダを除いたサイズ [4byte]
		width = readLong(); // マップの幅 [4byte]
		height = readLong(); // マップの高さ [4byte]
		chipwidth = readByte(); // チップの幅 [1byte]
		chipheight = readByte(); // チップの高さ [1byte]
		layercount = readByte(); // レイヤー数 [1byte]
		bitcount = readByte(); // データのbit数(8/16) [1byte]
		layerArray = new FMFLayer2D[layercount];

		for (int i = 0; i < layercount; i++) {
			FMFLayer2D layer = new FMFLayer2D(width, height, bytes, _ptr, _ptr
					+ width * height);
			layerArray[i] = layer;
			_ptr += width * height;
		}

	}

	/**
	 * 指定レイヤーを取得する
	 * 
	 * @param id
	 *            レイヤーID
	 * @return 2次元マップ
	 */
	public FMFLayer2D getLayer(int id) {
		if (id < 0 || id >= layercount)
			return null;
		return layerArray[id];
	}

	/**
	 * 1byte読み込み
	 */
	private int readByte() {
		if (_ptr - 1 >= bytes.length)
			return -1;
		int result = bytes[_ptr];
		_ptr += 1;
		return result;
	}

	/**
	 * 4byte読み込み
	 */
	private int readLong() {
		if (_ptr - 4 >= bytes.length)
			return -1;
		int result = bytes[_ptr] + (bytes[_ptr + 1] << 8)
				+ (bytes[_ptr + 2] << 16) + (bytes[_ptr + 3] << 24);
		_ptr += 4;
		return result;
	}
}
