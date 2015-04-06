package src.jp.co.yh123.tank.collabo;

import src.jp.co.yh123.tank.map.ICharaInterface;

public interface ICharaClbInterface extends ICharaInterface {

	/** This constant represents the body part "ç∂éË". */
	public static final String PART_LEFT_HAND = "ç∂";
	/** This constant represents the body part "âEéË". */
	public static final String PART_RIGHT_HAND = "âE";
	/** This constant represents the body part "ì∑". */
	public static final String PART_BODY = "ì∑";
	/** This constant represents the body part "ì™". */
	public static final String PART_HEAD = "ì™";
	/** This constant represents the body part "ë´". */
	public static final String PART_LEGS = "ë´";

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
