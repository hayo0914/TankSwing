package src.jp.co.yh123.zlibrary.sound;
//package src.jp.co.yh123.zcore.sound;
//
//import com.jblend.media.smaf.phrase.Phrase;
//
//import src.jp.co.yh123.zcore.exception.YhUIException;
//
///**
// * サウンドデータを管理する機能を提供します。
// * 
// * @author Takuto Nishioka
// */
//public class SoundData {
//
//	// サウンドデータ
//	private Phrase _sound;
//
//	/**
//	 * コンストラクタは何もしません。
//	 */
//	public SoundData() {
//
//	}
//
//	/**
//	 * byteデータからサウンドをセットします。
//	 * 
//	 * @param data
//	 *        サウンドデータ
//	 * @throws YhUIException
//	 *        サウンドデータの初期化に失敗した場合に発生します。
//	 */
//	public void setSound(byte[] data) throws YhUIException {
//
//		try{
//			_sound = new Phrase(data);
//		}catch(Exception e){
//			throw new YhUIException(YhUIException.RESOURCE_LOAD_FAILED, e.getMessage());
//		}
//	}
//
//	/**
//	 * ファイルを指定してサウンドをセットします。
//	 * 
//	 * @param uri
//	 *        サウンドファイル名
//	 * @throws YhUIException
//	 *        サウンドデータの初期化に失敗した場合に発生します。
//	 */
//	public void setSound(String uri) throws YhUIException {
//
//		try{
//			_sound = new Phrase("/" + uri);
//		}catch(Exception e){
//			throw new YhUIException(YhUIException.RESOURCE_LOAD_FAILED, e.getMessage());
//		}
//	}
//
//	/**
//	 * サウンドデータを取得します。
//	 * 
//	 * @return サウンドデータを返します。
//	 */
//	public Phrase getSound() {
//
//		return _sound;
//	}
//
//	/**
//	 * サウンドデータを開放します。
//	 */
//	public void release() {
//
//		_sound = null;
//	}
//
//}
