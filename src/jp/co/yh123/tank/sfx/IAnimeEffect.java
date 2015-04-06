package src.jp.co.yh123.tank.sfx;

import src.jp.co.yh123.tank.collabo.ICharaClbInterface;
import src.jp.co.yh123.tank.collabo.IEffectClbInterface;
import src.jp.co.yh123.tank.map.IActorInterface;
import src.jp.co.yh123.zlibrary.util.DblPosition;

public interface IAnimeEffect extends IEffectClbInterface {

	/**
	 * @param source
	 *            発生位置(Actorを指定)
	 * @param frameChangeInterval
	 *            アニメ更新フレーム間隔
	 * @param animeId
	 *            アニメID
	 * @param maxLoopCount
	 *            ループ回数
	 * @throws Exception
	 */
	public void init(IActorInterface source, int frameChangeInterval,
			int animeId, int maxLoopCount) throws Exception;

	/**
	 * @param source
	 *            発生位置(中心の座標を指定)
	 * @param frameChangeInterval
	 *            アニメ更新フレーム間隔
	 * @param animeId
	 *            アニメID
	 * @param maxLoopCount
	 *            ループ回数
	 * @throws Exception
	 */
	public void init(DblPosition source, int frameChangeInterval, int animeId,
			int maxLoopCount) throws Exception;

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

}
