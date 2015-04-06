package src.jp.co.yh123.tank.collabo;

import src.jp.co.yh123.tank.map.IItemInterface;

public interface IItemClbInterface extends IItemInterface {

	public static final int ITEM_DRINK = 0;

	public static final int ITEM_EQUIP = 1;

	public static final int ITEM_GRANADE = 2;

	public static final int EQUIP_TYPE1_WEAPON = 0;

	public static final int EQUIP_TYPE1_SHOOTER = 1;

	public static final int EQUIP_TYPE1_SHOT_MATERIAL = 2;

	public static final int EQUIP_TYPE2_SWORD = 0;

	public static final int EQUIP_TYPE2_TWOHANDED_SWORD = 1;

	public static final int EQUIP_TYPE2_GUN = 2;

	public static final int EQUIP_TYPE2_GUN_BULLET = 3;

	public static final int EQUIP_TYPE2_BOW = 4;

	public static final int EQUIP_TYPE2_ARROW = 5;

	public static final int EQUIP_TYPE2_ROD = 6;

	public static final int EQUIP_TYPE1_ARMOR = 3;

	public static final int EQUIP_TYPE2_BODY = 0;

	public void damage(String attribute) throws Exception;

	public boolean isStackable(IItemClbInterface another);

	public boolean isStackable();

	public boolean isEquipped();

	public void stackTo(IItemClbInterface another);

	public int getAnimeId() throws Exception;

	public String getNameWithCount();

	public int getItemType();

	/**
	 * このアイテムで撃ちだせるアイテムかどうかを判定
	 * 
	 * @param bulletItem
	 *            弾アイテム
	 * @return
	 * @throws Exception
	 */
	public boolean isBulletMatch(IItemClbInterface bulletItem) throws Exception;

	public int getSortNum() throws Exception;

	public int getEquipType() throws Exception;

	public String getEquipQualityText();

	public int getStackedNum() throws Exception;

	public int getEquipDmgTo();

	public int getEquipDmgFrom();

	public int getEquipHitAssist();

	public int getEquipCriticalRate();

	public int getEquipTypeDetail() throws Exception;

	public int getEquipBulletItemId();

	public int getEquipAC();

	public IItemClbInterface devideItem(int number) throws Exception;

	public String getEquipEnchantActive();

	public String getEquipEnchantPassive();

	public double getWeight();

	public void memorizeEquipped();

	public void memorizeUnequipped();

}
