package src.jp.co.yh123.zlibrary.sound;
//package src.jp.co.yh123.zcore.sound;
//
//import com.jblend.media.smaf.phrase.PhrasePlayer;
//import com.jblend.media.smaf.phrase.PhraseTrack;
//
//import src.jp.co.yh123.zcore.exception.YhUIException;
//
///**
// * サウンドデータを再生するための機能を提供します。<br>
// * <br>
// * JSCLでは下記データの同時再生がサポートされていますが、このクラスではSMAF(mmf)をサポートしていません。<br>
// * <li>SMAF/Phrase(spf)：4和音×4トラック
// * <li>SMAF(mmf)：16和音×1トラック
// * 
// * @author Takuto Nishioka
// */
//public class SoundPlayer {
//
//	// シングルトン
//	private static SoundPlayer _soundPlayer;
//
//	private PhrasePlayer _player;
//
//	private PhraseTrack[] _phrase;
//
//	/**
//	 * BGMサウンド
//	 */
//	public static final int BGM = 0;
//
//	/**
//	 * SEサウンド
//	 */
//	public static final int SE = 1;
//
//	/**
//	 * サウンドプレイヤーを初期化します。
//	 */
//	private SoundPlayer() {
//
//		_player = PhrasePlayer.getPlayer();
//		_phrase = new PhraseTrack[2];
//		_phrase[BGM] = _player.getTrack();
//		_phrase[SE] = _player.getTrack();
//	}
//
//	/**
//	 * サウンドプレイヤーを取得します。
//	 * 
//	 * @return サウンドプレイヤーインスタンスを返します。
//	 */
//	public static SoundPlayer getInstance() {
//
//		if(_soundPlayer == null){
//			_soundPlayer = new SoundPlayer();
//		}
//		return _soundPlayer;
//	}
//
//	/**
//	 * BGMサウンドをセットします。
//	 * 
//	 * @param sound
//	 *        サウンドデータ
//	 * @throws YhUIException
//	 *         サウンドデータが初期化されていない場合などに発生します。
//	 */
//	public void setBGM(SoundData sound) throws YhUIException {
//
//		try{
//			_phrase[BGM].setPhrase(sound.getSound());	
//		}catch(Exception e){
//			throw new YhUIException(YhUIException.RESOURCE_NOT_READY, e.getMessage());
//		}
//	}
//
//	/**
//	 * SEサウンドをセットします。
//	 * 
//	 * @param sound
//	 *        サウンドデータ
//	 * @throws YhUIException
//	 *         サウンドデータが初期化されていない場合などに発生します。
//	 */
//	public void setSE(SoundData sound) throws YhUIException {
//
//		try{
//			_phrase[SE].setPhrase(sound.getSound());
//		}catch(Exception e){
//			throw new YhUIException(YhUIException.RESOURCE_NOT_READY, e.getMessage());
//		}
//	}
//
//	/**
//	 * 再生します。
//	 * 
//	 * @param type
//	 *        サウンドの種別
//	 */
//	public void play(int type) {
//
//		if(type == BGM){
//			stop(BGM);
//			_phrase[BGM].play(0);
//		}else{
//			stop(SE);
//			_phrase[SE].play();
//		}
//	}
//
//	/**
//	 * 一時停止します。
//	 * 
//	 * @param type
//	 *        サウンドの種別
//	 */
//	public void pause(int type) {
//
//		if(type == BGM){
//			_phrase[BGM].pause();
//		}else{
//			_phrase[SE].pause();
//		}
//	}
//
//	/**
//	 * 一時停止から再開します。
//	 * 
//	 * @param type
//	 *        サウンドの種別
//	 */
//	public void restart(int type) {
//
//		if(type == BGM){
//			_phrase[BGM].resume();
//		}else{
//			_phrase[SE].resume();
//		}
//	}
//
//	/**
//	 * 停止します。
//	 * 
//	 * @param type
//	 *        サウンドの種別
//	 */
//	public void stop(int type) {
//
//		if(type == BGM){
//			_phrase[BGM].stop();
//		}else{
//			_phrase[SE].stop();
//		}
//	}
//	
//	/**
//	 * サウンドを開放します。
//	 * 
//	 * @param type
//	 *        開放するサウンド
//	 */
//	public void remove(int type){
//
//		if(type == BGM){
//			_phrase[BGM].removePhrase();
//		}else{
//			_phrase[SE].removePhrase();
//		}
//	}
//
//}
