package src.jp.co.yh123.tank.sfx;

import src.jp.co.yh123.tank.collabo.ICharaClbInterface;
import src.jp.co.yh123.tank.collabo.IEffectClbInterface;
import src.jp.co.yh123.tank.map.IActorInterface;

public interface ITaskEffect extends IEffectClbInterface {

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
	public void init(IActorInterface source) throws Exception;

	/**
	 * このEffectの発生原因となったCharaを保存する
	 * 
	 * @param owner
	 *            発生原因となったChara
	 */
	public void setOwner(ICharaClbInterface owner) throws Exception;

}
