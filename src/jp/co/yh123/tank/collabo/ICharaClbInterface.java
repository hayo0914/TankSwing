package src.jp.co.yh123.tank.collabo;

import src.jp.co.yh123.tank.map.ICharaInterface;

public interface ICharaClbInterface extends ICharaInterface {

	/** This constant represents the body part "����". */
	public static final String PART_LEFT_HAND = "��";
	/** This constant represents the body part "�E��". */
	public static final String PART_RIGHT_HAND = "�E";
	/** This constant represents the body part "��". */
	public static final String PART_BODY = "��";
	/** This constant represents the body part "��". */
	public static final String PART_HEAD = "��";
	/** This constant represents the body part "��". */
	public static final String PART_LEGS = "��";

	public void damage(ICharaClbInterface attacker, String attribute, int damage)
			throws Exception;

	/**
	 * Returns the chara's direction 0 to 359 degree
	 */
	public int getDirection();

	public boolean isPlayer();

	public boolean isMonster();

	public boolean isGoodNPC();

	/**
	 * Returns the Maptip that is in front of this Chara.
	 * 
	 * @return
	 * @throws Exception
	 */
	public IMaptipClbInterface getFrontMapTip() throws Exception;

	/**
	 * Returns the Maptip where this Chara.
	 * 
	 * @return
	 * @throws Exception
	 */
	public IMaptipClbInterface getCurrentMapTip() throws Exception;
}
