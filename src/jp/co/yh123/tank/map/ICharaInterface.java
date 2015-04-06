package src.jp.co.yh123.tank.map;

import src.jp.co.yh123.tank.logic.ILogicCharaInterface;

/**
 * @author hamaday
 */
public interface ICharaInterface extends ILogicCharaInterface, IActorInterface {

	public static final int TYPE_DEFAULT = -1;

	/** The type of the Monster. */
	public static final int TYPE_MONSTER = 0;

	/** The type of the Player. */
	public static final int TYPE_PLAYER = 1;

	/** The type of the Good NPC. */
	public static final int TYPE_GOOD_NPC = 2;

	public void setChara(int charaId, int charaType, int level)
			throws Exception;

}
