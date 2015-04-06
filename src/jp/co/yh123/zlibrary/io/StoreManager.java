package src.jp.co.yh123.zlibrary.io;
//package src.jp.co.yh123.zcore.io;
//
//import java.io.IOException;
//
//import javax.microedition.rms.RecordStore;
//
///**
// * レコードストアを操作するための機能を提供します。<br>
// * このクラスでは、Dojaへの移植を考慮しているため、一つのレコードストアのみ扱います。<br>
// * <br>
// * 
// * [MIDP制約]<br>
// * レコードストアを利用するためには、JADでMIDlet-Data-Sizeを指定する必要があります。<br>
// * 使用できる容量は基本的に最大512KBで、JARとJADとレコードストアサイズ合計1Mまでです。<br>
// * 例) MIDlet-Data-Size: 512000<br>
// * 
// * [クラス利用手順]<br>
// * 1. 作成するレコードを1からの連番で定義します。<br>
// * 2. レコードサイズ(_SIZE)を定義します。<br>
// * （全ての合計がMIDlet-Data-Size以下になるようにします）<br>
// * 
// * @author Takuto Nishioka
// */
//public class StoreManager {
//
//	/**
//	 * レコードストア RSTORE レコード ID=1
//	 */
//	public static final int NO_1 = 1;
//
//	/**
//	 * レコードストア RSTORE レコード ID=2
//	 */
//	public static final int NO_2 = 2;
//
//	/**
//	 * レコードストア RSTORE レコード ID=3
//	 */
//	public static final int NO_3 = 3;
//
//	// レコードサイズ (byte)
//	private static final int[] _SIZE = new int[] {
//			1000, 1000, 1000
//	};
//
//	// レコードストアオブジェクト
//	private static RecordStore _rs;
//
//	/**
//	 * コンストラクタは何もしません。
//	 */
//	public StoreManager() {
//
//	}
//
//	/**
//	 * レコードストアをオープンします。
//	 * 
//	 * @param isInit
//	 *          レコードストアがない場合に作成するかどうか
//	 * @throws IOException
//	 *           レコードオープンに失敗した場合に発生します。
//	 */
//	private void openRstore(boolean isInit) throws IOException {
//
//		try {
//			// 一つしか利用しないので名称は固定
//			_rs = RecordStore.openRecordStore("RSTORE", isInit);
//		} catch (Exception e) {
//			_rs = null;
//			throw new IOException("[openRstore OPEN失敗]");
//		}
//	}
//
//	/**
//	 * レコードストアをクローズします。
//	 */
//	private void closeRstore() {
//
//		try {
//			if (_rs != null) _rs.closeRecordStore();
//		} catch (Exception e) {
//			_rs = null;
//		}
//	}
//
//	/**
//	 * レコードストアを初期化します。
//	 * 
//	 * @throws IOException
//	 *           レコードの初期化に失敗した場合に発生します。
//	 */
//	private void initRstore() throws IOException {
//
//		try {
//			openRstore(true);
//			int rnum = _rs.getNumRecords();
//
//			// レコード数が変更されていたらレコード全削除
//			if (rnum != 0 && rnum != _SIZE.length) {
//				for (int i = 0; i < rnum; i++) {
//					_rs.deleteRecord(i);
//				}
//				rnum = 0;
//			}
//
//			// レコード初期化
//			if (rnum == 0) {
//				for (int i = 0; i < _SIZE.length; i++) {
//					byte[] w = new byte[_SIZE[i]];
//					_rs.addRecord(w, 0, w.length);
//				}
//			}
//
//		} catch (Exception e) {
//			throw new IOException(e.getMessage() + "[initRstore 初期化失敗]");
//
//		} finally {
//			closeRstore();
//		}
//	}
//
//	/**
//	 * 指定したサイズで処理できるかチェックします。
//	 * 
//	 * @param area
//	 *          対象領域
//	 * @param offset
//	 *          書込/読込開始位置を指定
//	 * @param len
//	 *          書込/読込長さを指定
//	 * @throws IOException
//	 *           無効な領域、サイズが指定された場合に発生します。
//	 */
//	private void checkSize(int area, int offset, int len) throws IOException {
//
//		// レコードストア初期化
//		initRstore();
//
//		if (area >= _SIZE.length) {
//			throw new IOException("[checkSize 無効な領域] area=" + area + ", offset=" + offset + ", len=" + len);
//
//		} else if (offset + len > _SIZE[area]) {
//			throw new IOException("[checkSize overflow] area=" + area + ", offset=" + offset + ", len=" + len);
//		}
//	}
//
//	/**
//	 * レコードストアの指定領域をクリアします。
//	 * 
//	 * @param area
//	 *          対象領域
//	 * @throws IOException
//	 *           クリアに失敗した場合に発生します。
//	 */
//	public void clear(int area) throws IOException {
//
//		checkSize(area, 0, 0);
//
//		// 空データを上書きしてクリア
//		write(new byte[_SIZE[area]], area, 0);
//	}
//
//	/**
//	 * レコードストアへデータを書き込みます。
//	 * 
//	 * @param data
//	 *        書き込み対象データ配列
//	 * @param area
//	 *        対象領域
//	 * @param offset
//	 *        書き込み開始位置
//	 * @throws IOException
//	 *         書き込みに失敗した場合に発生します。
//	 * @throws NullPointerException
//	 *         dataがnullの場合に発生します。
//	 */
//	public void write(byte[] data, int area, int offset) throws IOException, NullPointerException {
//
//		if (data == null) {
//			throw new NullPointerException("[write null] area=" + area + ", offset=" + offset);
//		}
//
//		checkSize(area, offset, data.length);
//
//		try {
//			openRstore(true);
//			_rs.setRecord(area, data, offset, data.length);
//
//		} catch (Exception e) {
//			throw new IOException(e.getMessage() + "[write] area=" + area + ", offset=" + offset);
//
//		} finally {
//			closeRstore();
//		}
//	}
//
//	/**
//	 * レコードストアからデータを読み込みます。
//	 * 
//	 * @param area
//	 *          対象領域
//	 * @param offset
//	 *          読み込み開始位置
//	 * @param len
//	 *          読み込む長さ
//	 * @throws IOException
//	 *           読み込みに失敗した場合に発生します。
//	 */
//	public byte[] read(int area, int offset, int len) throws IOException {
//
//		checkSize(area, offset, len);
//
//		byte[] ret = new byte[len];
//		try {
//			openRstore(false);
//			System.arraycopy(_rs.getRecord(area), offset, ret, 0, len);
//
//		} catch (Exception e) {
//			throw new IOException(e.getMessage() + "[read] area=" + area + ", offset=" + offset + ", len=" + len);
//
//		} finally {
//			closeRstore();
//		}
//		return ret;
//	}
//}
