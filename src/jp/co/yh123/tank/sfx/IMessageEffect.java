package src.jp.co.yh123.tank.sfx;

import src.jp.co.yh123.tank.collabo.IEffectClbInterface;
import src.jp.co.yh123.tank.map.IActorInterface;

public interface IMessageEffect extends IEffectClbInterface {

	/**
	 * @param source
	 * @param strMsg
	 * @param x
	 * @param y
	 * @param color
	 * @param bgColor
	 * @throws Exception
	 */
	public void init(IActorInterface source, String strMsg, int color,
			int bgColor) throws Exception;
}
