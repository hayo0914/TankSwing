package src.jp.co.yh123.tank.sfx;

import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;

public interface IBulletHitListener {

	/**
	 * MAPTIP�ɏՓ˂����ۂ̃��X�i
	 * 
	 * @param e
	 * @param tip
	 * @throws Exception
	 */
	public void onCollision(IBulletEffect e, IMaptipClbInterface tip)
			throws Exception;
}
