package src.jp.co.yh123.tank.item;

import src.jp.co.yh123.tank.collabo.IItemClbInterface;
import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;
import src.jp.co.yh123.tank.map.IItemInterface;
import src.jp.co.yh123.tank.map.MapFactory;
import src.jp.co.yh123.tank.resource.Res;
import src.jp.co.yh123.zlibrary.anime.Animation;
import src.jp.co.yh123.zlibrary.anime.AnimeFactory;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.platform.HmFont;
import src.jp.co.yh123.zlibrary.util.Csv;
import src.jp.co.yh123.zlibrary.util.DebugUtil;
import src.jp.co.yh123.zlibrary.util.List;
import src.jp.co.yh123.zlibrary.util.RandomValues;

public class Item implements IItemInterface, IItemClbInterface {

	public int getWidth() throws Exception {
		return this.anime.getWidth();
	}

	public int getHeight() throws Exception {
		return this.anime.getHeight();
	}

	public int getCellX() {
		return _cellX;
	}

	public int getCellY() {
		return _cellY;
	}

	public void setCell(int cellX, int cellY) throws Exception {
		this._cellX = cellX;
		this._cellY = cellY;

	}

	public void setPosition(double x, double y) {
		this._x = x;
		this._y = y;

	}

	public double getX() {
		return _x;
	}

	public double getY() {
		return _y;
	}

	private boolean isSameTypeItem(Item i) {
		return (i.itemId == itemId);
	}

	public boolean isStackable() {
		return isStackable;
	}

	private void addStackNum(int addNum) {
		stackNum += addNum;
	}

	public boolean isStackable(IItemClbInterface another) {
		Item i = (Item) another;
		return (isSameTypeItem(i) && isStackable());
	}

	public void stackTo(IItemClbInterface another) {
		DebugUtil.assertTrue(isStackable(another));
		Item i = (Item) another;
		i.addStackNum(stackNum);
	}

	public void damage(String attribute) throws Exception {
		// FIXME:
		IMaptipClbInterface m = (IMaptipClbInterface) MapFactory.getMap().get(
				getCellX(), getCellY());
		if (m.hasItem()) {
			m.removeItem();
		}
	}

	// private static final int[] QUALITY_LV = { 0, 1, 2, 3, 4 };
	private static final String[] QUALITY_TEXT = { "粗悪", "低品質", "通常", "良質",
			"精巧", "名器", "神器" };
	private static final double[] QUALITY_LV_POWER_ADD = { -0.5, -0.2, 0, 0.15,
			0.3, 0.45, 0.6 };
	private static final int[] QUALITY_LV_HIT_ADD = { -15, -10, 0, 5, 10, 15,
			15, 20 };
	private static final int[] QUALITY_LV_CRITICAL_RATE = { -5, -3, 0, 5, 10,
			15, 20 };
	private static final double[] QUALITY_LV_AC = { -0.5, -0.35, 0, 0.1, 0.2,
			0.3, 0.4 };

	// /* 地面にある状態か、否か */
	// public boolean _onGround = false;

	// public boolean isOnGround() {
	// return _onGround;
	// }

	public void setItem(int itemId) throws Exception {
		// setPosition(ApplicationFacade.getModel().map, cellX, cellY);
		// map.get(cellX, cellY).setItem(this);
		// _onGround = true;

		Csv csv = Res.get().csvItem;
		this.itemId = itemId;
		equipId = csv.getInt(itemId, 1);
		name = csv.getString(itemId, 2);
		anime = AnimeFactory.getInstance().createAnime(-1, -1, -1);
		int animeId = csv.getInt(itemId, 3);
		anime.setAnime(-1, animeId, -1);
		itemType = csv.getInt(itemId, 4);
		qualityLevel = csv.getInt(itemId, 5);
		weight = (int) (stackNum * csv.getDouble(itemId, 6) * 1000);
		isStackable = csv.getInt(itemId, 7) == 1;
		time = 0;
		specified = 1;// FIXME:
		isEquipped = false;
		// _onGround = false;
		// synchronized (getModel()) {
		// _itemSequenceId = getModel().itemSequence;
		// getModel().itemSequence++;
		// }

		if (itemType == ITEM_EQUIP) {
			Csv csvEquip = Res.get().csvEquip;
			equipRange = csvEquip.getInt(equipId, 1);
			// _equipPower = csvEquip.getInt(_equipId, 4);
			equipDmgFrom = csvEquip.getInt(equipId, 4);
			equipDmgTo = csvEquip.getInt(equipId, 5);
			equipAC = csvEquip.getInt(equipId, 6);
			// _equipDamageAdd = csvEquip.getInt(_equipId, 7);
			equipBulletItemId = csvEquip.getInt(equipId, 7);
			equipTwoHands = csvEquip.getInt(equipId, 8);
			equipDurability = csvEquip.getInt(equipId, 9);
			equipHitAssist = csvEquip.getInt(equipId, 10);
			equipCriticalRate = csvEquip.getInt(equipId, 12);

			// 品質決定
			if (getEquipType() != Item.EQUIP_TYPE1_SHOT_MATERIAL) {
				int quality = _randomValues.getRandomInt(0, 101);
				if (quality == 0) {
					if (_randomValues.getRandomInt(0, 20) == 0) {
						// S+級
						equipQuality = 6;
					} else {
						// S級
						equipQuality = 5;
					}
				} else if (quality <= 5) {
					equipQuality = 4;
				} else if (quality <= 10) {
					equipQuality = 3;
				} else if (quality <= 40) {
					equipQuality = 2;
				} else if (quality <= 70) {
					equipQuality = 1;
				} else {
					equipQuality = 0;
				}
				StringBuffer sb = new StringBuffer(name);
				sb.append("(");
				sb.append(getEquipQualityText());
				sb.append(")");
				name = sb.toString();
			}

			// エンチャント決定
			setEnchant();

		}
		setStackNum(1);

		if (itemType == ITEM_EQUIP
				&& getEquipType() == EQUIP_TYPE1_SHOT_MATERIAL) {
			// BULLETの場合//XXX:
			setStackNum(_randomValues.getRandomInt(30, 100));
		}

	}

	private void setEnchant() throws Exception {

		// エンチャント決定
		Csv csvEquip = Res.get().csvEquip;
		equipEnchantActive = csvEquip.getString(equipId, 13);
		equipEnchantPassive = csvEquip.getString(equipId, 14);

		// for test
		if (_randomValues.getRandomInt(0, 5) == 1) {
			equipEnchantActive += _randomValues.getRandomInt(0, 5) + "="
					+ _randomValues.getRandomInt(5, 100);
		}
		if (_randomValues.getRandomInt(0, 5) == 1) {
			equipEnchantPassive += _randomValues.getRandomInt(4, 16) + "="
					+ _randomValues.getRandomInt(5, 100);
		}

	}

	/**
	 * 射程
	 * 
	 * @throws Exception
	 */
	public int getRange() throws Exception {
		return equipRange;
	}

	// public void addUseMenu(List list) throws Exception {
	// switch (_itemType) {
	// case ITEM_DRINK:
	// list.add(new Select(GameConstants.MENU_ITEM_DRINK, -1, "飲む"));
	// break;
	// case ITEM_EQUIP:
	// if (_isEquipped) {
	// list
	// .add(new Select(GameConstants.MENU_ITEM_UN_EQUIP, -1,
	// "はずす"));
	// } else {
	// list.add(new Select(GameConstants.MENU_ITEM_EQUIP, -1, "装備する"));
	// }
	// break;
	// }
	// list.add(new Select(GameConstants.MENU_ITEM_THROW, -1, "投げる"));
	// if (_isStackable && !_onGround) {
	// list.add(new Select(GameConstants.MENU_ITEM_MATOMERU, -1, "まとめる"));
	// if (_stackNum > 1) {
	// list.add(new Select(GameConstants.MENU_ITEM_WAKERU, -1, "分ける"));
	// }
	// }
	//
	// if (!_onGround) {
	// list.add(new Select(GameConstants.MENU_ITEM_PUT, -1, "置く"));
	// }
	//
	// return;
	// }

	public String getNameWithCount() {
		if (isStackable) {
			return String.valueOf(stackNum) + "個の" + getName();
		} else {
			return getName();
		}
	}

	public String getName() {
		if (specified == 0) {
			return "未鑑定品";
		} else {
			return name;
		}
	}

	public boolean isEquipped() {
		return isEquipped;
	}

	public void setStackNum(int num) throws Exception {
		Csv csv = Res.get().csvItem;
		this.stackNum = num;
		weight = (int) (csv.getDouble(itemId, 6) * 1000) * stackNum;
		if (stackNum != 1) {
			strStackNum = String.valueOf(num);
		}
	}

	public int getStackedNum() throws Exception {
		return this.stackNum;
	}

	// public void destroyMessage() {
	// StringBuffer sb = new StringBuffer();
	// switch (_itemType) {
	// case ITEM_DRINK:
	// sb.append(getName());
	// sb.append("は");
	// sb.append("砕け散った");
	// break;
	// case ITEM_EQUIP:
	// sb.append(getName());
	// sb.append("は");
	// sb.append("使い物にならなくなった");
	// break;
	// default:
	// sb.append(getName());
	// sb.append("は");
	// sb.append("使い物にならなくなった");
	// break;
	// }
	// getGame().seeMessage(sb.toString());
	// }

	static List rangeList = new List(30);// 範囲取得用

	/**
	 * @return ゴミになったか否か
	 * @throws Exception
	 */
	// public boolean thrown(ICharaClbInterface pitcher, int tgCellX, int
	// tgCellY)
	// throws Exception {
	//
	// if (_itemType == ITEM_GRANADE) {
	// // グレネードの場合
	// // 爆発
	// rangeList.removeAllElements();
	// MapFactory.getMap().getRangeMaptip(rangeList, tgCellX, tgCellY, 1);
	// for (int i = 0; i < rangeList.size(); i++) {
	// IMaptipClbInterface bombPoint = (IMaptipClbInterface) rangeList
	// .elementAt(i);
	// for (int j = 0; j < 2; j++) {
	// SkillFactory.getInstance().createExplodeEffect(pitcher,
	// bombPoint.getCellX(), bombPoint.getCellY(), 15);
	// }
	// }
	// return true;
	// }
	//
	// if (chara != null && pitcher != chara) {
	// // 投げた先に誰かがいた場合
	// // 命中判定
	// double attackPoint = ((pitcher.get_agi() * 0.8d) + (pitcher
	// .get_str() * 0.2d))
	// * (pitcher.get_skillLvThrowing() * 0.1 + 1);
	// // hit判定//XXX:
	// // ac
	// int ac = chara.getAC();
	// // 率計算
	// double rate = 0;
	// if (attackPoint + ac == 0) {
	// rate = 0.95d;
	// } else {
	// rate = (double) attackPoint / (double) (attackPoint + ac);
	// }
	// // 四捨五入
	// rate = (double) ((int) ((rate + 0.05) * 100d));
	// if (rate > 95) {
	// // 上限
	// rate = 95;
	// } else if (rate < 5) {
	// // 下限
	// rate = 5;
	// }
	//
	// // 命中判定
	// StringBuffer sb1;
	// sb1 = new StringBuffer();
	// sb1.append("投擲命中率 ");
	// sb1.append(rate);
	// sb1.append("％");
	// getGame().seeMessage(sb1.toString());
	// // 判定
	// if (rate < getModel().randomizer.getRandomInt(0, 101)) {
	// // 外れた
	// StringBuffer sb = new StringBuffer();
	// sb.append(getName());
	// sb.append("は");
	// // sb.append(chara.getNameForMessage());
	// sb.append("床に落ちた");// TODO:種類によって分割
	// game.seeMessage(sb.toString());
	// return false;
	// } else {
	// // 誰かに当たった場合
	// switch (_itemType) {
	// case ITEM_DRINK:
	// if (useDrink(chara)) {
	// // ゴミ化
	// return true;
	// }
	// return false;
	// case ITEM_EQUIP:
	// int damage = getModel().randomizer.getRandomInt(
	// get_equipDmgFrom(), get_equipDmgTo()); // TODO:ダメージ計算
	// StringBuffer sb = new StringBuffer();
	// sb.append(getName());
	// sb.append("は");
	// sb.append(chara.getNameForMessage());
	// sb.append("に突き刺さった");// TODO:種類によって分割
	// game.seeMessage(sb.toString());
	// chara.damage(pitcher, Chara.ATT_PHIS, damage);// XXX:武器の属性
	// if (chara.isDead()) {
	// sb = new StringBuffer();
	// }
	// // TODO:折れる、砕けるなど
	// return false;
	// }
	// }
	// }// 投げた先に誰かがいた場合
	// return false;
	// }
	//
	// /**
	// * のんだ場合の処理
	// *
	// * @param user
	// * @return ゴミになったか否か
	// * @throws Exception
	// */
	// public boolean useDrink(Chara user) throws Exception {
	// switch (_itemId) {
	// case 0:// ポーション
	// getGame().seeMessage(
	// user.getNameForMessage() + "は" + getName() + "を飲んだ。");
	// int regPoint = (int) ((user.get_hp() * 0.7d) + 0.5d);
	// if (user.isPlayer()) {
	// if (user.get_hp_reduce() == 0) {
	// getGame().selfMessage("とくに何の効果も感じられない");
	// } else if (user.get_hp_reduce() <= regPoint) {
	// getGame().selfMessage("薬の力であなたの傷は完全に癒えた");
	// } else {
	// getGame().selfMessage("薬の力であなたの傷は少し癒えた");
	// }
	// }
	// user.regenerate(regPoint, true);
	// return true;
	// default:
	// break;
	// }
	// return false;
	// }

	public int getEquipType() throws Exception {
		Csv csvEquip = Res.get().csvEquip;
		return csvEquip.getInt(equipId, 2);
	}

	public int getEquipTypeDetail() throws Exception {
		Csv csvEquip = Res.get().csvEquip;
		return csvEquip.getInt(equipId, 3);
	}

	public void draw(GameGraphic g, int offsetX, int offsetY) throws Exception {
		anime.setPosition(getX(), getY());
		anime.draw(g, offsetX, offsetY);
		if (stackNum != 1) {
			HmFont.setFont(g, HmFont.STYLE_ITALIC, HmFont.FONT_TINY);
			// FIXME:
			g.drawBorderString(strStackNum, (int) getX() + 2 - offsetX,
					(int) getY() - offsetY + 2, 0xffffff, 0x000000);
		}
	}

	public String getEquipQualityText() {
		return QUALITY_TEXT[equipQuality];
	}

	public int getEquipRange() {
		return equipRange;
	}

	public double getWeight() {
		return (double) weight / 1000d;
	}

	public int getEquipDmgFrom() {
		int ret = (int) ((double) equipDmgFrom + (double) equipDmgFrom
				* QUALITY_LV_POWER_ADD[equipQuality]);
		if (ret < 1) {
			ret = 1;
		}
		return ret;
	}

	public int getEquipDmgTo() {
		int ret = (int) ((double) equipDmgTo + (double) equipDmgTo
				* QUALITY_LV_POWER_ADD[equipQuality]);
		if (ret < 1) {
			ret = 1;
		}
		return ret;
	}

	public int getEquipDurability() {
		return equipDurability;
	}

	public int getEquipHitAssist() {
		return equipHitAssist + QUALITY_LV_HIT_ADD[equipQuality];
	}

	public int getItemType() {
		return itemType;
	}

	public int getEquipCriticalRate() {
		int ret = equipCriticalRate + QUALITY_LV_CRITICAL_RATE[equipQuality];
		if (ret < 3) {
			ret = 3;
		}
		return ret;
	}

	public String getEquipEnchantActive() {
		return equipEnchantActive;
	}

	public String getEquipEnchantPassive() {
		return equipEnchantPassive;
	}

	public int getEquipBulletItemId() {
		return equipBulletItemId;
	}

	public int getEquipAC() {
		return equipAC + (int) ((double) equipAC * QUALITY_LV_AC[equipQuality]);
	}

	public int getAnimeId() throws Exception {
		return anime.getAnimeId();
	}

	private static RandomValues _randomValues = new RandomValues(System
			.currentTimeMillis());

	private int itemId = 0;

	private int equipRange = 0;

	private int equipQuality = 0;

	private int itemType = 0;

	private int qualityLevel = 0;

	private int weight = 0;

	private int equipId = 0;

	private String name = "";

	private int time = 0;

	private int specified = 0;

	private boolean isEquipped = false;

	// 装備関連のパラメタ
	// int _equipPower = 0;

	private int stackNum = 1; // スタック数

	private boolean isStackable = false; // スタック

	private String strStackNum = null; // スタック数

	private int equipDmgFrom = 0;
	//
	private int equipDmgTo = 0;

	// int _equipDamageAdd = 0;

	private int equipAC = 0;

	private int equipTwoHands = 0;

	private int equipDurability = 0;

	private int equipHitAssist = 0;

	private int equipBulletItemId = 0;

	private int equipCriticalRate = 0;

	private String equipEnchantActive = "";

	private String equipEnchantPassive = "";

	private int _cellX = 0;

	private int _cellY = 0;

	private double _x = 0;

	private double _y = 0;

	private Animation anime = null;

	public int getSortNum() throws Exception {
		Csv csv = Res.get().csvItem;
		return csv.getInt(itemId, 8);
	}

	public boolean isBulletMatch(IItemClbInterface bulletItem) throws Exception {
		DebugUtil
				.assertTrue(getEquipType() == IItemClbInterface.EQUIP_TYPE1_SHOOTER);
		Item i = (Item) bulletItem;
		return getEquipBulletItemId() == i.itemId;
	}

	public void memorizeEquipped() {
		DebugUtil.assertFalse(isEquipped);
		isEquipped = true;
	}

	public void memorizeUnequipped() {
		DebugUtil.assertTrue(isEquipped);
		isEquipped = false;
	}

	public IItemClbInterface devideItem(int number) throws Exception {
		DebugUtil.assertTrue(isStackable());
		DebugUtil.assertLarger(number, 0);
		DebugUtil.assertSmaller(number, getStackedNum());
		setStackNum(stackNum - number);
		Item newItem = new Item();
		newItem.setItem(itemId);
		newItem.setStackNum(number);
		return newItem;
	}
}
