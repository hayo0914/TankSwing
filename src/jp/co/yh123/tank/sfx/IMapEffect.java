package src.jp.co.yh123.tank.sfx;

import src.jp.co.yh123.tank.collabo.ICharaClbInterface;
import src.jp.co.yh123.tank.collabo.IEffectClbInterface;
import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;

public interface IMapEffect extends IEffectClbInterface {

	/**
	 * このEffectを終了させる
	 */
	public void setEnd() throws Exception;

	/**
	 * @param source
	 *            発生位置(MAPTIPを指定)
	 * @param frameChangeInterval
	 *            アニメ更新フレーム間隔
	 * @param animeId
	 *            アニメID
	 * @param maxLoopCount
	 *            ループ回数
	 * @throws Exception
	 */
	public void init(IMaptipClbInterface source, int frameChangeInterval,
			int animeId, int maxLoopCount) throws Exception;

	/**
	 * このEffectの発生原因となったCharaを保存する
	 * 
	 * @param owner
	 *            発生原因となったChara
	 */
	public void setOwner(ICharaClbInterface owner) throws Exception;

	/**
	 * Roundが変わった際のリスナを保存する
	 * 
	 * @param listener
	 */
	public void setRoundInitListener(IEffectRoundInitListener listener);

	/**
	 * このEffectの現在位置のMAPTIPを取得する
	 * 
	 * @return
	 * @throws Exception
	 */
	public IMaptipClbInterface getCurrentPosition() throws Exception;
}
