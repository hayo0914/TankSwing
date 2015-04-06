package src.jp.co.yh123.tank.sfx;

import src.jp.co.yh123.tank.collabo.ICharaClbInterface;
import src.jp.co.yh123.tank.collabo.IEffectClbInterface;
import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;

public interface IBulletEffect extends IEffectClbInterface {

	/**
	 * @param source
	 *            発生位置(MAPTIPを指定)
	 * @param frameChangeInterval
	 * @param animeId
	 * @param maxLoopCount
	 * @param degree
	 *            発射角度
	 * @param spd
	 *            速度
	 * @throws Exception
	 */
	public void init(IMaptipClbInterface source, int frameChangeInterval,
			int animeId, int maxLoopCount, int degree, double spd)
			throws Exception;

	/**
	 * このEffectの発生原因となったCharaを保存する
	 * 
	 * @param owner
	 *            発生原因となったChara
	 */
	public void setOwner(ICharaClbInterface owner) throws Exception;

	/**
	 * このEffectがMAPTIPと衝突した時のリスナを保存する
	 * 
	 * @param l
	 *            リスナ
	 * @throws Exception
	 */
	public void setEffectHitListener(IBulletHitListener l) throws Exception;

	/**
	 * バウンドさせる
	 */
	public void bound();

	/**
	 * 指定の速度を変更する
	 * 
	 * @param bairitsu
	 *            新しい速度
	 */
	public void changeSpd(double newSpeed);

	/**
	 * 進行方向を変更する
	 * 
	 * @param degree
	 */
	public void setDirection(int degree);

	/**
	 * 速度を返却する
	 */
	public double getSpd();

	/**
	 * 角度を返却する
	 */
	public int getDegree();

	/**
	 * 発生箇所からの距離を返却する
	 * 
	 * @return 発生箇所からの距離(通過したMAPTIPの数。初期値は1）
	 */
	public int getDistance();
}
