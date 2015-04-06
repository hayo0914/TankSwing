package src.jp.co.yh123.tank.chara;

import src.jp.co.yh123.distanceHolder.DistanceMap;
import src.jp.co.yh123.tank.ai.AIFactory;
import src.jp.co.yh123.tank.collabo.ICharaClbInterface;
import src.jp.co.yh123.tank.collabo.IItemClbInterface;
import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;
import src.jp.co.yh123.tank.item.ItemHolderFactory;
import src.jp.co.yh123.tank.map.ICharaInterface;
import src.jp.co.yh123.tank.map.Map;
import src.jp.co.yh123.tank.map.MapFactory;
import src.jp.co.yh123.tank.menu.IItemListMenuInterface;
import src.jp.co.yh123.tank.menu.IMainMenuInterface;
import src.jp.co.yh123.tank.menu.INumberInputInterface;
import src.jp.co.yh123.tank.menu.MenuFactory;
import src.jp.co.yh123.tank.menu.IItemListMenuInterface.IItemMenuCallBackInterface;
import src.jp.co.yh123.tank.menu.IMainMenuInterface.IMainMenuCallBackInterface;
import src.jp.co.yh123.tank.menu.INumberInputInterface.INumberInputCallBackInterface;
import src.jp.co.yh123.tank.menu.ISimpleSelectMenuInterface.ISimpleSelectMenuCallBackInterface;
import src.jp.co.yh123.tank.menu.ISkillMenuInterface.ISkillMenuCallBackInterface;
import src.jp.co.yh123.tank.resource.Res;
import src.jp.co.yh123.tank.sfx.SkillFactory;
import src.jp.co.yh123.zlibrary.anime.Animation;
import src.jp.co.yh123.zlibrary.anime.AnimeFactory;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.platform.HmFont;
import src.jp.co.yh123.zlibrary.util.Csv;
import src.jp.co.yh123.zlibrary.util.DataArray;
import src.jp.co.yh123.zlibrary.util.DebugUtil;
import src.jp.co.yh123.zlibrary.util.GameMath;
import src.jp.co.yh123.zlibrary.util.IntPosition;
import src.jp.co.yh123.zlibrary.util.List;
import src.jp.co.yh123.zlibrary.util.RandomValues;

/**
 * This class instance represents a character of Player, or NPC or Monster.
 * 
 * @author Y.H
 * @since 1.0
 */
public class Chara implements ICharaInterface, ICharaClbInterface {

	private static RandomValues _randomValues = new RandomValues(System
			.currentTimeMillis());

	public int getWidth() throws Exception {
		return this._anime.getWidth();
	}

	public int getHeight() throws Exception {
		return this._anime.getHeight();
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
		recalcDistance();
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

	public void setChara(int charaId, int charaType, int level)
			throws Exception {
		// status
		loadStatus(charaId);
		// AI
		setCharaType(charaType);
		switch (charaType) {
		case ICharaInterface.TYPE_PLAYER:
			_ai = AIFactory.getPlayerAI();
			break;
		default:
			DebugUtil.error("存在しない");
		}
		// level
		for (int i = 0; i < level; i++) {
			levelup();
		}
	}

	private IAIInterface _ai = null;

	private void setCharaType(int charaType) {
		_charaType = charaType;
	}

	/**
	 * This constants indicates the normal point to consume for character
	 * moving.
	 */
	public static final int NORMAL_SPEED = 600;
	/**
	 * This list holds the item instances that this character holds.
	 */
	private IItemHolderInterface _itemHolder = null;

	public List getItems() throws Exception {
		return _itemHolder.getItemList();
	}

	//
	// private Chara _self = this;
	//
	// /** The Enchant instance this character has. */
	// private Enchant _enchantEffect = null;
	// /** The State instance this character has. */
	// private State _state = null;
	// /** The Tolerance instance this character has. */
	// private Tolerance _tolerance = null;
	// /**
	// * This values holds the active enchant this character has, without any
	// * equipment or other special effects. Ex) "4=5/"
	// */
	// private String _defaultActiveEnchant = null;
	// /**
	// * This values holds the passive enchant this character has, without any
	// * equipment or other special effects Ex) "4=5/".
	// */
	// private String _defaultPassiveEnchant = null;
	//
	// /** The distance this character can see without any effect. */
	// private int _visibleArea = 5;// 視界基本
	//
	// /** This text holds all the skills of this character. For example,
	// "4=0/0=0" */
	// private String _strSkill = "";
	// /** This is the list of this character's skills. */
	// private DataArray _skill = null;
	//
	// /** The item this character equips. */
	// Item _equip_rightHand = null;
	//
	// /** The item this character equips. */
	// Item _equip_leftHand = null;
	//
	// /** The item this character equips. */
	// Item _equip_shooter = null;// 遠隔
	//
	// public Item get_equip_shooter() {
	// return _equip_shooter;
	// }
	//
	// public void set_equip_shooter(Item equipShooter) {
	// _equip_shooter = equipShooter;
	// }
	//
	// /** The item this character equips. */
	// Item _equip_shot_material = null;// 遠隔
	//
	// /** The item this character equips. */
	// Item _equip_body = null;// 体
	//
	/** This is used for calculating the distance map. */
	private IDistanceHolder distanceHolder = null;

	//
	// /** The AI of this character. */
	// private AiBasic ai = null;
	//
	// /** The id of this character. */
	// int _charaId = 0;
	//
	// /** The type of this character, representing is NPC of Player or Monster.
	// */
	// int _charaType = -1;
	//
	// public int get_charaType() {
	// return _charaType;
	// }
	//
	// public void set_charaType(int charaType) {
	// _charaType = charaType;
	// }
	//
	// /**
	// * The direction of this character, and this must be 0 to 315 by 45
	// degrees.
	// */
	// int _direction = 0;
	//
	// /** The level of this character. */
	// int _level = 0;
	//
	// /** The exp point to the next level of this character. */
	// long _expNext = 0;
	//
	// /** The total exp point of this character. */
	// long _expTotal = 0;
	//
	// /** The name of this character. */
	// String _name = "ななし";
	//
	// /** The sex of this character. */
	// int _sex = 0;
	//
	// /** The sex of this character. */
	// int _speed = 12;
	//
	// /** The strength of this character. */
	// int _str = 0;
	//
	// /** The intelligence of this character. */
	// int _int = 0;
	//
	// // int _pie = 0;
	//
	// /** The vitality of this character. */
	// int _vit = 0;
	//
	// /** The agility of this character. */
	// int _agi = 0;
	//
	// /** The luck of this character. */
	// int _luc = 0;
	//
	// /** The dexterity of this character. */
	// int _dex = 0;
	//
	// /** The intelligence of this character. */
	// int _ac = 0;
	//
	// /** The total life of this character. */
	// int _hp = 10;
	//
	// /** The reduced life of this character. */
	// int _hp_reduce = 0;
	//
	// /** The total mana of this character. */
	// int _mp = 10;
	//
	// /** The reduced mana of this character. */
	// int _mp_reduce = 0;
	//
	// /** The remaining move point of this character. */
	// int _movePoint = 0;
	//
	// public int get_movePoint() {
	// return _movePoint;
	// }
	//
	// public void set_movePoint(int movePoint) {
	// _movePoint = movePoint;
	// }
	//
	// /**
	// * The body parts of this character, for instance "頭胴右左足"
	// */
	// String _parts = null; /* 体のパーツ */
	//
	// /**
	// * this holds the text represents the level of this character , for
	// instance
	// * "40Lv"
	// */
	// private String _levelString = null;
	//
	// /**
	// * The flag indicates whether this character moved or not in current turn.
	// */
	// boolean _isMoved = false;
	//
	// public boolean isMoved() {
	// return _isMoved;
	// }
	//
	// public void setMoved(boolean isMoved) {
	// _isMoved = isMoved;
	// }
	//
	// /** This value indicates the character's skill Level of using sword. */
	// int _skillLvSword = 0;
	// /** This value indicates the character's skill Level of using gun. */
	// int _skillLvGun = 20;
	// /** This value indicates the character's skill Level of casting spell. */
	// int _skillLvSpell = 0;
	// /** This value indicates the character's skill Level of throwing
	// something. */
	// int _skillLvThrowing = 0;
	//
	// public void set_skillLvThrowing(int skillLvThrowing) {
	// _skillLvThrowing = skillLvThrowing;
	// }
	//
	// /**
	// * This value indicates the character's skill Level of fighting without
	// * weapon.
	// */
	// int _skillLvHandToHand = 0;
	//
	// /** The counts of the total turns of this character. */
	// long _turn = 0;
	// /** The counts of the attack miss. */
	// int _missCount = 0;// 攻撃ミスの回数カウント
	// /**
	// * The reduced speed of this character. This is calculate every initial
	// * round.
	// */
	// int _speedDown = 0;// 状態異常などによる速度低下分 roundの最初に再計算する
	//
	// /**
	// * Returns the list of enemy characters.
	// */
	// public List getEnemyList() {
	//
	// if (isControlled()) {
	// if (isMonster()) {
	// return getModel().listEnemiesToPlayer;
	// } else if (isPlayer()) {
	// return getModel().listEnemiesToMonster;
	// } else if (isGoodNPC()) {
	// return getModel().listEnemiesToMonster;
	// } else {
	// DebugUtil.assertFalse(true);
	// return null;
	// }
	// } else if (!isSane()) {
	// switch (getModel().randomizer.getRandomInt(0, 2)) {
	// case 0:
	// return getModel().listEnemiesToPlayer;
	// case 1:
	// return getModel().listEnemiesToMonster;
	// default:
	// return getModel().listEnemiesToMonster;
	// }
	// } else {
	// if (isMonster()) {
	// return getModel().listEnemiesToMonster;
	// } else if (isPlayer()) {
	// return getModel().listEnemiesToPlayer;
	// } else if (isGoodNPC()) {
	// return getModel().listEnemiesToPlayer;
	// } else {
	// DebugUtil.assertFalse(true);
	// return null;
	// }
	// }
	// }
	//
	// /**
	// * Returns the list of friend characters.
	// */
	// List getFriendList() {
	// if (isControlled()) {
	// if (isMonster()) {
	// return getModel().listEnemiesToMonster;
	// } else if (isPlayer()) {
	// return getModel().listEnemiesToPlayer;
	// } else if (isGoodNPC()) {
	// return getModel().listEnemiesToPlayer;
	// } else {
	// DebugUtil.assertFalse(true);
	// return null;
	// }
	// } else if (!isSane()) {
	// switch (getModel().randomizer.getRandomInt(0, 2)) {
	// case 0:
	// return getModel().listEnemiesToMonster;
	// case 1:
	// return getModel().listEnemiesToPlayer;
	// default:
	// return getModel().listEnemiesToPlayer;
	// }
	// } else {
	// if (isMonster()) {
	// return getModel().listEnemiesToPlayer;
	// } else if (isPlayer()) {
	// return getModel().listEnemiesToMonster;
	// } else if (isGoodNPC()) {
	// return getModel().listEnemiesToMonster;
	// } else {
	// DebugUtil.assertFalse(true);
	// return null;
	// }
	// }
	// }
	//
	// /**
	// * Returns the leader character of this character.
	// */
	// public Chara getLeader() {
	// if (isControlled()) {
	// Chara e = _state.getAttacker(State.CONTROL);
	// if (!(e.isControlled() && e._state.getAttacker(State.CONTROL) != this)) {
	// return e;
	// }
	// }
	// if (isMonster()) {
	// return null;// FIXME:getleader
	// } else if (isPlayer()) {
	// // DebugUtil.assertFalse(true);
	// return null;
	// } else if (isGoodNPC()) {
	// return getModel().player;
	// } else {
	// DebugUtil.assertFalse(true);
	// return null;
	// }
	// }
	//
	// /**
	// * Changes the ai.
	// */
	// void changeAi(AiBasic ai) {
	// this.ai = ai;
	// }
	//
	// /**
	// * The Tolerance class saves the all tolerance values, and those values
	// * imply the power to reduce the effects of all type of the Attributes.
	// */
	// private static class Tolerance {
	// // 【耐性】
	// // -∞ ~ +30 (1=10%)
	// // 900 >= 吸収
	// private short[] _toleranceArray = null;
	// private static final int MAX_TOLERANCE = 900;
	//
	// /**
	// * Initializes the tolerance of the character. The parameter represents
	// * the tolerance points that the character has.
	// *
	// * @param strTole
	// * Ex)/物=30/毒=80/
	// */
	// void initTolerance(String strTole) {
	// _toleranceArray = new short[ATT_ARRAY.length()];
	// increaseTolerance(strTole);
	// }
	//
	// /**
	// * Increases the Tolerance of the character.
	// *
	// * @param strTole
	// * Ex)/物=30/毒=80/
	// */
	// void increaseTolerance(String strTole) {
	// try {
	// if (strTole == null || strTole.length() == 0) {
	// return;
	// }
	// DataArray da = new DataArray(strTole);
	// for (int i = 0; i < da.size(); i++) {
	// String attrib = da.getKey(i);
	// int index = index(attrib);
	// if (index >= 0) {
	// _toleranceArray[index] += da.getValue(i);
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// /**
	// * Returns the tolerance of the specified attribute. If the value is 0,
	// * the character has no tolerance against the attribute. And if the
	// * value if over 900, the character has ability to absorbtion of the
	// * attribute.
	// */
	// int getTolerance(String attribute) {
	// if (_toleranceArray == null) {
	// return -1;
	// }
	// int index = index(attribute);
	// if (index == -1) {
	// return 0;
	// }
	// return _toleranceArray[index];
	// }
	//
	// /**
	// * Returns the index of this attribute.
	// */
	// private int index(String attribute) {
	// return ATT_ARRAY.indexOf(attribute);
	// }
	//
	// }
	//
	// /**
	// * dump Chara instance information for debugging
	// */
	// public void dumpCharaInfo() {
	// System.out.println("\t< CHARA-INFO START >");
	// System.out.print("\t");
	// System.out.println(getNameForMessage());
	// System.out.print("\tCellX=");
	// System.out.println(getCellX());
	// System.out.print("\tCellY=");
	// System.out.println(getCellY());
	// System.out.print("\tMovePoint=");
	// System.out.println(_movePoint);
	// System.out.print("\tSpeed=");
	// System.out.println(get_speed());
	// System.out.print("\tLeader=");
	// System.out.println(getLeader() == null ? "null" : getLeader()
	// .getNameForMessage());
	// System.out.println("\t< CHARA-INFO END >");
	// }
	//
	// /**
	// * Returns the visible distance.
	// */
	// public int get_visibleArea() {
	// int ret = _visibleArea;
	// ret += _enchantEffect._en_visibleArea;
	// if (ret < 0) {
	// ret = 0;
	// } else if (ret > 10) {
	// ret = 10;
	// }
	// return ret;
	// }
	//
	// /**
	// * This class save the state of this chara. Ex)Poison,Sleep,Desease. The
	// * methods onInitRound and onInitTurn must be called once in a round or a
	// * turn.
	// */
	// private class State {
	// public static final int POISON = 0;
	// public static final int STRONG_POISON = 1;
	// public static final int SLEEP = 2;
	// public static final int CONTROL = 3;
	// public static final int NUM = 4;
	//
	// private short state_array[];
	// private Chara state_attacker[];
	//
	// // private String stateString = null;
	//
	// State() {
	// state_array = new short[NUM];
	// state_attacker = new Chara[NUM];
	// }
	//
	// /**
	// * Returns if the State is on.
	// */
	// boolean is(int state) {
	// return state_array[state] != 0;
	// }
	//
	// /**
	// * Returns if the State is on.
	// */
	// Chara getAttacker(int state) {
	// DebugUtil.assertTrue(is(state));
	// DebugUtil.assertTrue(state_attacker[state] != null);
	// return state_attacker[state];
	// }
	//
	// /**
	// * Increases the state level. if the level is zero then the state is
	// * off.
	// *
	// * @param attacker
	// * The character instance that made the cause of this
	// * effect.If this character dies, the attacker gains
	// * experience points.
	// * @param state
	// * The State to be increased.
	// * @param num
	// * The level num to be incleased.
	// */
	// void increase(Chara attacker, int state, int num) {
	// state_array[state] += num;
	// state_attacker[state] = attacker;
	// }
	//
	// /**
	// * Decrease the state level. if the level is zero then the state is off.
	// *
	// * @param state
	// * The State to be increased.
	// * @param num
	// * The level num to be incleased.
	// * @throws Exception
	// */
	// void decrease(int state, int num) throws Exception {
	// if (is(state)) {
	// state_array[state] -= num;
	// if (state_array[state] < 0)
	// state_array[state] = 0;
	// if (!is(state)) {
	// switch (state) {
	// case SLEEP:
	// getGame().selfMessage(getNameForMessage() + "は目を覚ました。");
	// break;
	// case CONTROL:
	// getGame().selfMessage(getNameForMessage() + "はわれに返った");
	// break;
	// case POISON:
	// getGame().selfMessage(
	// getNameForMessage() + "の気分はよくなった。");
	// break;
	// default:
	// break;
	// }
	// }
	// }
	// }
	//
	// /**
	// * Returns the state level that is to be >= 0
	// */
	// short get(int state) {
	// return state_array[state];
	// }
	//
	// /**
	// * Checks some states, and process the effect of the state on the
	// * initiating time of the round.
	// *
	// * @param c
	// * The character has this state.
	// */
	// void onInitRound() throws Exception {
	// if (is(POISON) && _turn % 3 == 0) {
	// int damage = (int) (get_hp() * 0.05d);
	// damage(state_attacker[POISON], ATT_NONE, "毒",
	// getModel().randomizer.getRandomInt(
	// (int) (damage * 0.7d) + 1,
	// (int) (damage * 1.3d)) + 1);
	// decrease(POISON, 1);
	// }
	// if (is(SLEEP)) {
	// decrease(SLEEP, getModel().randomizer.getRandomInt(1, 10));
	// }
	// }
	//
	// /**
	// * Checks some states, and process the effect of the state on the
	// * initiating time of the Turn.
	// *
	// * @param c
	// * The character has this state.
	// */
	// void onInitTurn(Chara c) throws Exception {
	// if (is(CONTROL)) {
	// decrease(CONTROL, getModel().randomizer.getRandomInt(1, 10));
	// }
	// }
	// }
	//
	// /**
	// * This class save the type of enchants and effects of the enchant this
	// * character has. When something change happen for this character, the
	// * "initPassiveEnchant" must be called. And "checkPassiveEnchant" must be
	// * called every turn of this character.
	// */
	// private class Enchant {
	//
	// /** The effects of the enchant that is happening on this character. */
	// int _en_visibleArea = 0;// 視界enchant修正
	// int _en_speed = 0;
	// int _en_str = 0;
	// int _en_int = 0;
	// int _en_vit = 0;
	// int _en_agi = 0;
	// int _en_luc = 0;
	// int _en_dex = 0;
	// int _en_ac = 0;
	// int _en_hp = 0;
	// int _en_mp = 0;
	// int _en_notify_distance = 80;// FIXME:
	//
	// /** The effects on tolerance. */
	// Tolerance _en_tolerance = null;
	//
	// /** This holds all of the passive enchants ID. */
	// short[] _en_key = null;
	//
	// /** This holds all of the passive enchants Value. */
	// short[] _en_value = null;
	//
	// Enchant() {
	// _en_tolerance = new Tolerance();
	// }
	//
	// /**
	// * type = 0:ターン開始時、1:ラウンド開始時
	// *
	// * @throws Exception
	// */
	// void checkPassiveEnchant(int type) throws Exception {
	// if (_en_key == null) {
	// return;
	// }
	// Csv csvEnchant = Res.get().csvEnchant;
	// // エンチャント効果
	// for (int i = 0; i < _en_key.length; i++) {
	// int enchantId = _en_key[i];
	// if (csvEnchant.getInt(enchantId, 7) == type) {
	// effectPassiveEnchant(_en_key[i], _en_value[i]);
	// }
	// }
	// }
	//
	// /**
	// * Initializes all of the passive enchants of the equipment ,spells or
	// * others.This must be called every time when this character changes the
	// * equip, or other events that changes the passive enchant of this
	// * character.
	// */
	// void initPassiveEnchant() throws Exception {
	// _en_visibleArea = 0;
	// _en_speed = 0;
	// _en_str = 0;
	// _en_int = 0;
	// _en_vit = 0;
	// _en_agi = 0;
	// _en_luc = 0;
	// _en_dex = 0;
	// _en_ac = 0;
	// _en_hp = 0;
	// _en_mp = 0;
	// _en_notify_distance = 0;
	// _en_tolerance.initTolerance(null);
	//
	// StringBuffer sb = new StringBuffer();
	// sb.append(_defaultPassiveEnchant);
	// for (int i = 0; i < _items.size(); i++) {
	// Item it = (Item) _items.elementAt(i);
	// if (it._isEquipped && it._equipEnchantPassive.length() != 0) {
	// sb.append(it._equipEnchantPassive);
	// sb.append("/");
	// }
	// }
	//
	// DataArray passiveEnchant = null;
	// if (sb.length() != 0) {
	// passiveEnchant = new DataArray(sb.toString());
	// } else {
	// passiveEnchant = null;
	// _en_key = null;
	// _en_value = null;
	// return;
	// }
	//
	// Csv csvEnchant = Res.get().csvEnchant;
	// _en_key = new short[passiveEnchant.size()];
	// _en_value = new short[passiveEnchant.size()];
	//
	// for (int i = 0; i < passiveEnchant.size(); i++) {
	// _en_key[i] = (short) passiveEnchant.getKeyInt(i);
	// _en_value[i] = (short) passiveEnchant.getValue(i);
	// }
	//
	// // 常時エンチャント効果チェック
	// // 常時エンチャント効果
	// for (int i = 0; i < _en_key.length; i++) {
	// if (csvEnchant.getInt(_en_key[i], 7) == 2) {
	// // 常時
	// effectPassiveEnchantAlways(_en_key[i], _en_value[i]);
	// }
	// }
	//
	// // HPエンチャント効果で残りHPが0以下になる場合、
	// // 1に設定
	// if (isDead()) {
	// _hp_reduce = get_hp() - 1;
	// }
	//
	// // MPは0に
	// if (get_mp() - _mp_reduce <= 0) {
	// _mp_reduce = get_mp();
	// }
	//
	// }
	//
	// /**
	// * Processes the passive effect like "per 3round" or "per every turn".
	// */
	// private void effectPassiveEnchant(int enchantId, int power)
	// throws Exception {
	// Csv csvEnchant = Res.get().csvEnchant;
	//
	// switch (csvEnchant.getInt(enchantId, 2)) {
	// case 2:
	// // regenerate/3round
	// if (getModel().roundCount % 3 == 0) {
	// int reg = (int) ((double) get_hp() * (double) power * 0.01d);
	// regenerate(reg, true);
	// }
	// break;
	// default:
	// DebugUtil.assertTrue(false);// error
	// }
	// }
	//
	// /**
	// * Processes the permanent and passive effect.
	// */
	// private void effectPassiveEnchantAlways(int enchantId, int power)
	// throws Exception {
	// Csv csvEnchant = Res.get().csvEnchant;
	// switch (csvEnchant.getInt(enchantId, 2)) {
	// case 3:// 明るさ
	// _en_visibleArea += power;
	// if (isPlayer()) {
	// getGame().setVisible(getModel().player);
	// }
	// break;
	// case 4:// hp
	// _en_hp += (int) ((double) power * 0.01d * _hp);
	// break;
	// case 5:// 速度
	// _en_speed += (int) (_speed * (double) power * 0.01d);
	// break;
	// case 6:// mp
	// _en_mp += (int) ((double) power * 0.01d * _mp);
	// break;
	// case 7:// 耐性
	// StringBuffer sb1 = new StringBuffer(csvEnchant.getString(
	// enchantId, 5));
	// sb1.append("=");
	// sb1.append(power);
	// _en_tolerance.increaseTolerance(sb1.toString());
	// break;
	// case 8:// Hiding
	// _en_notify_distance += (int) (power);
	// break;
	// default:
	// DebugUtil.assertFalse(true);
	// break;
	// }
	// }
	// }
	//
	// /**
	// * Recalculates the map of the distance map. this must be called when this
	// * character moves.
	// */
	// public void recalculatesDistanceMap() throws Exception {
	// distanceMap.calcCharaMoveDistanceMap();
	// }
	//
	/**
	 * return distance that is to to be notified by enemy. this is used for
	 * player and good NPC.
	 */
	public int getNotifyDistance() {
		return 10;
		// int rate = _enchantEffect._en_notify_distance;
		// int wk = (int) ((double) rate * 15d * 0.01d);
		// int ret = 15 - (int) (wk);
		// if (ret < 1)
		// return 1;
		// else if (ret > 15)
		// return 15;
		// else
		// return ret;
	}

	//
	// /**
	// * Returns the rate of hiding.
	// */
	// int getNotifyDistanceRate() {
	// return _enchantEffect._en_notify_distance;
	// }
	//
	// /**
	// * Returns the distance between this character and the specified cell. If
	// * the character is next to the cell, the return value would be 1 or 2.
	// And
	// * if the distance is too far, returns -1.The value would be larger As the
	// * distance is to be far.
	// */
	// public int getDistance(int cellX, int cellY) throws Exception {
	// int ret = distanceMap.getDistanceScore(cellX, cellY);
	// return ret;
	// }
	//
	// /**
	// * Returns the relative direction for the specified cell from this
	// * character. The value becomes 0 to 315 by 45 degrees.
	// */
	// public int getDirectionByOffset(int offsetX, int offsetY) {
	// switch (offsetX) {
	// case -1:
	// switch (offsetY) {
	// case -1:
	// return 225;
	// case 0:
	// return 180;
	// case 1:
	// return 135;
	// default:
	// // あり得ないパターン
	// DebugUtil.assertFalse(true);
	// return 0;
	// }
	// case 0:
	// switch (offsetY) {
	// case -1:
	// return 270;
	// case 1:
	// return 90;
	// default:
	// // あり得ないパターン
	// DebugUtil.assertFalse(true);
	// return 0;
	// }
	// case 1:
	// switch (offsetY) {
	// case -1:
	// return 315;
	// case 0:
	// return 0;
	// case 1:
	// return 45;
	// default:
	// // あり得ないパターン
	// DebugUtil.assertFalse(true);
	// return 0;
	// }
	// default:
	// // あり得ないパターン
	// DebugUtil.assertFalse(true);
	// return 0;
	// }
	// }
	//
	// /**
	// * Returns the pixel width of this character's sprite.
	// */
	// public int getWidth() throws Exception {
	// return getAnime().getWidth();
	// }
	//
	// /**
	// * Returns the pixel height of this character's sprite.
	// */
	// public int getHeight() throws Exception {
	// return getAnime().getHeight();
	// }
	//
	/**
	 * Returns whether this character is player or not.
	 */
	public boolean isPlayer() {
		switch (_charaType) {
		case TYPE_PLAYER:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Returns whether this character is GOOD_NPC or not.
	 */
	public boolean isGoodNPC() {
		switch (_charaType) {
		case TYPE_GOOD_NPC:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Returns whether this character is Monster or not.
	 */
	public boolean isMonster() {
		switch (_charaType) {
		case TYPE_MONSTER:
			return true;
		default:
			return false;
		}
	}

	//
	// /**
	// * Returns whether this character is monster or not.
	// */
	// public boolean isMonster() {
	// switch (_charaType) {
	// case TYPE_MONSTER:
	// return true;
	// default:
	// return false;
	// }
	// }
	//
	// /**
	// * Returns whether this character is good NPC or not.
	// */
	// public boolean isGoodNPC() {
	// switch (_charaType) {
	// case TYPE_GOOD_NPC:
	// return true;
	// default:
	// return false;
	// }
	// }
	//
	// /**
	// * Returns whether this character has the specified body part.
	// */
	// boolean havePart(String part) {
	// return _parts.indexOf(part) >= 0;
	// }
	//
	// /**
	// * Sets the direction of this character.The value must be 0 to 315 by 45
	// * degrees.
	// */
	// public void setDirection(int direction) {
	// _direction = direction;
	// }
	//
	/**
	 * Returns the direction of this character. The value must be 0 to 315 by 45
	 * degrees.
	 */
	public int getDirection() {
		return _direction;
	}

	//
	// /**
	// * Initializes this character as a player.
	// */
	// public void initPlayer(int characterId) throws Exception {
	// loadStatus(characterId);
	// _charaType = TYPE_PLAYER;
	// }
	//
	// /**
	// * Initializes this character as a good NPC.
	// */
	// public void initGoodNpc(int characterId) throws Exception {
	// loadStatus(characterId);
	// _charaType = TYPE_GOOD_NPC;
	// ai = new AiCat();
	// }
	//
	// /**
	// * Initializes this character as a Monster.
	// */
	// public void initMonster(int characterId) throws Exception {
	// loadStatus(characterId);
	// _charaType = TYPE_MONSTER;
	// }
	//
	// /**
	// * Initializes this character on starting round. This method must be
	// called
	// * every round.
	// */
	// public void onInitRound() throws Exception {
	// // ステート
	// _state.onInitRound();
	// // エンチャント
	// _enchantEffect.checkPassiveEnchant(1);
	// if (isDead()) {
	// return;
	// }
	// // ダメージによる速度低下
	// double hpRate = ((double) get_hp() - _hp_reduce) / (double) get_hp();
	// if (hpRate < 0.2d) {
	// // 3割以下で速度は3割低下
	// if (isPlayer() && _speedDown == 0) {
	// if (!isAbleToMove()) {
	// getGame().selfMessage(
	// getNameForMessage() + "は深手を負って動きが遅くなった");
	// }
	// }
	// _speedDown = (int) (get_speed() * 0.3d);
	// if (get_speed() - _speedDown <= 0) {
	// _speedDown = get_speed() - 1;
	// }
	// } else {
	// _speedDown = 0;
	// }
	// // 行動ポイント
	// if (isAbleToMove()) {
	// _movePoint += get_speed() - _speedDown;
	// }
	// }
	//
	// /**
	// * Returns if this character can move or not. Checks if he sleeps, or is
	// * paralyzed and so on.
	// */
	// public boolean isAbleToMove() {
	// if (_state.is(State.SLEEP)) {
	// return false;
	// }
	// return true;
	// }
	//
	// /**
	// * Initializes this character on starting turn. This method must be called
	// * every turn.
	// */
	// public void onInitTurn() throws Exception {
	// // ステート
	// _state.onInitTurn(this);
	// // エンチャント
	// _enchantEffect.checkPassiveEnchant(0);
	// // ターンカウント
	// _turn++;
	// }
	//
	// /**
	// * Initializes when this character set on the dungeon.
	// */
	// public void onInit() throws Exception {
	// ai.onInit(this);
	// }
	//
	// /**
	// * Removes specified item instance from this character's item list and
	// * returns the item.
	// */
	// public Item removeOneItem(Item item) throws Exception {
	// DebugUtil.assertFalse(item.isOnGround());
	// if (item._stackNum == 1 && item._isEquipped) {
	// unEquip(item);
	// }
	// if (item._stackNum > 1) {
	// item.setStackNum(item._stackNum - 1);
	// Item i = new Item();
	// i.initItem(item._itemId);
	// i.setStackNum(1);
	// return i;
	// } else {
	// _items.removeElement(item);
	// return item;
	// }
	// }
	//
	// /**
	// * Removes specified item instance by one, from this character's item
	// list.
	// */
	// public void removeStackItem(Item item) throws Exception {
	// DebugUtil.assertFalse(item.isOnGround());
	// if (item._isEquipped) {
	// unEquip(item);
	// }
	// _items.removeElement(item);
	// }
	//
	// /**
	// * Returns the name of this character, changed to use in game message.
	// */
	// public String getNameForMessage() {
	// if (isPlayer()) {
	// return "あなた";
	// } else {
	// StringBuffer sb = new StringBuffer();
	// sb.append(_name);
	// sb.append(_levelString);
	// return sb.toString();
	// }
	// }
	//
	// /**
	// * Returns the name of this character.
	// */
	// public String getName() {
	// return _name;
	// }
	//
	// /**
	// * Draws the status of this character.
	// */
	// public void drawStatus(Graphic g) {
	//
	// // HP
	// HmFont.setFont(g, HmFont.STYLE_PLAIN, HmFont.FONT_SMALL);
	// g.drawBorderString("HP", 150, 17, 0x33CC33, 0x000000);
	// HmFont.setFont(g, HmFont.STYLE_PLAIN, HmFont.FONT_MEDIUM);
	// StringBuffer sb = new StringBuffer();
	// sb.append(get_hp() - _hp_reduce);
	// sb.append("/");
	// sb.append(get_hp());
	// g.drawBorderString(sb.toString(), 165, 15, 0xFFFFFF, 0x000000);
	//
	// // MP
	// // MktFont.setFont(g, MktFont.STYLE_PLAIN, MktFont.FONT_SMALL);
	// // g.drawBorderString("MP", 150, 37, 0xCC27CC, 0x000000);
	// // MktFont.setFont(g, MktFont.STYLE_PLAIN, MktFont.FONT_MEDIUM);
	// // sb = new StringBuffer();
	// // sb.append(get_mp() - _mp_reduce);
	// // sb.append("/");
	// // sb.append(get_mp());
	// // g.drawBorderString(sb.toString(), 165, 35, 0xFFFFFF, 0x000000);
	// // 状態
	//
	// }
	//
	/**
	 * Initialize the status of this character.
	 */
	private void loadStatus(int characterId) throws Exception {
		distanceHolder = new DistanceMap();
		_action = new ActionWait();
		_itemHolder = ItemHolderFactory.createItemHolder(new List(10));
		Csv csv = Res.get().csvMonster;
		_charaId = characterId;
		_level = 0;
		_name = csv.getString(characterId, 1);
		_sex = csv.getInt(characterId, 2);
		_anime = AnimeFactory.getInstance().createAnime(-1, -1, -1);
		int animeId = Res.get().csvMonster.getInt(characterId, 3);
		_anime.setAnime(-1, animeId, -1);

		_speed = csv.getInt(characterId, 5);
		_str = csv.getInt(characterId, 6);
		_int = csv.getInt(characterId, 7);
		// _pie = csv.getInt(monsterId, 8);
		_vit = csv.getInt(characterId, 9);
		_agi = csv.getInt(characterId, 10);
		_luc = csv.getInt(characterId, 11);
		_ac = csv.getInt(characterId, 12);
		_dex = csv.getInt(characterId, 13);
		_parts = csv.getString(characterId, 14);

		// 耐性
		// _tolerance = new Tolerance();
		// _tolerance.initTolerance(csv.getString(characterId, 23));

		// デフォルトエンチャント
		_defaultActiveEnchant = csv.getString(characterId, 24);
		_defaultPassiveEnchant = csv.getString(characterId, 25);

		// Skill
		_strSkill = csv.getString(characterId, 26);
		_skill = new DataArray(_strSkill);

		// デフォルト
		_movePoint = 0;
		_isMoved = false;

		// HP計算
		culcMaxHp();
		// MP計算
		culcMaxMp();
		// Lv文字列
		_levelString = "Lv" + _level;
		// NextExp計算
		culcExpNext();

		// // エンチャント状態
		// _enchantEffect = new Enchant();
		// _enchantEffect.initPassiveEnchant();
		//
		// // state
		// _state = new State();
		//
		// // private map
		// distanceMap = new DistanceMap(this);
		//
		// // ai
		// ai = new AiBasic();

	}

	/**
	 * Returns the list of skills of this character.
	 */
	public DataArray getSkill() {
		return _skill;
	}

	/**
	 * Calculate the max MP from other status.
	 */
	private void culcMaxMp() {
		_mp = (_int * 3);
	}

	/**
	 * Processes the level up.
	 */
	public void levelup() throws Exception {
		_level++;
		_levelString = "Lv" + _level;

		// パラメタ上昇
		Csv csv = Res.get().csvMonster;
		// str
		int rateStr = csv.getInt(_charaId, 15);
		int rateInt = csv.getInt(_charaId, 16);
		int rateVit = csv.getInt(_charaId, 18);
		int rateAgi = csv.getInt(_charaId, 19);
		int rateLuc = csv.getInt(_charaId, 20);
		int rateDex = csv.getInt(_charaId, 22);
		int wk = 0;
		for (int i = 0; i < 3; i++) {
			// 最大5上昇
			if (_randomValues.getRandomInt(0, 101) < rateStr) {
				_str++;
				wk++;
			}
			if (_randomValues.getRandomInt(0, 101) < rateInt) {
				_int++;
				wk++;
			}
			if (_randomValues.getRandomInt(0, 101) < rateVit) {
				_vit++;
				wk++;
			}
			if (_randomValues.getRandomInt(0, 101) < rateAgi) {
				_agi++;
				wk++;
			}
			if (_randomValues.getRandomInt(0, 101) < rateLuc) {
				_luc++;
				wk++;
			}
			if (_randomValues.getRandomInt(0, 101) < rateDex) {
				_dex++;
				wk++;
			}
			if (wk >= 5) {
				break;
			}
		}
		culcMaxHp();
		culcExpNext();
	}

	/**
	 * Calculate the experience point that is required to be next level.
	 */
	private void culcExpNext() throws Exception {
		long wk = (_level + 1) * (_level + 1) * (_level + 1) * 10 + 200;
		Csv csv = Res.get().csvMonster;
		wk = (long) (csv.getDouble(_charaId, 17) * wk);
		_expNext = wk;
	}

	/**
	 * Returns the experience point that is gain from the specified enemy.
	 */
	private int calcExp(Chara enemy) throws Exception {
		Csv csv = Res.get().csvMonster;
		double wk = csv.getDouble(enemy._charaId, 8);
		wk *= enemy._level * 2;

		// LV差によって調整
		int diff = _level - enemy._level;
		if (diff < 0) {
			wk += ((double) wk * diff * -1 * 0.1);
		} else if (diff < 5) {
			wk *= 1;
		} else if (diff < 10) {
			wk *= 0.5d;
		} else if (diff < 15) {
			wk *= 0.3d;
		} else {
			wk *= 0.1d;
		}
		return (int) wk;
	}

	//
	// /**
	// * Adds the specified value to this character's total experience points.
	// And
	// * if the total EXP is enough, does the process of level up.
	// */
	// public void addExp(long exp) throws Exception {
	// // TODO:LvUpエフェクト実装
	// _expTotal += exp;
	// // boolean isLevelUp = false;
	// while (_expNext <= _expTotal) {
	// // LV上昇の場合TRUE（エフェクト等を見せる）
	// levelup();
	// // isLevelUp = true;
	// }
	// }
	//
	/**
	 * Calculate the max HP from other status.
	 */
	private void culcMaxHp() {
		_hp = (_vit * 4 + _str);
	}

	//
	// /**
	// * Equips the specified item. The item to equip must be on this
	// character's
	// * item list.
	// */
	// public void equipItem(Item item) throws Exception {
	// DebugUtil.assertFalse(item._isEquipped);
	// DebugUtil.assertFalse(item._onGround);
	// // FIXME:
	// switch (item._equipType) {
	// case Item.EQUIP_TYPE1_WEAPON:
	// if (!havePart(PART_LEFT_HAND) && !havePart(PART_LEFT_HAND)) {
	// if (isPlayer()) {
	// getGame().selfMessage("手がないので使えない");
	// }
	// break;
	// }
	// if (_equip_rightHand != null) {
	// unEquip(_equip_rightHand);
	// }
	// _equip_rightHand = item;
	// if (isPlayer()) {
	// getGame().selfMessage(item.getName() + "を装備した");
	// }
	// break;
	// case Item.EQUIP_TYPE1_ARMOR:
	// switch (item._equipType2) {
	// case Item.EQUIP_TYPE2_BODY:
	// if (!havePart(PART_BODY)) {
	// if (isPlayer()) {
	// getGame().selfMessage("胴がないので使えない");
	// }
	// break;
	// }
	// if (_equip_body != null) {
	// unEquip(_equip_body);
	// }
	// _equip_body = item;
	// if (isPlayer()) {
	// getGame().selfMessage(item.getName() + "を装備した");
	// }
	// break;
	// }
	// break;
	// case Item.EQUIP_TYPE1_SHOOTER:
	// if (_equip_shooter != null) {
	// unEquip(_equip_shooter);
	// }
	// if (!havePart(PART_LEFT_HAND) && !havePart(PART_LEFT_HAND)) {
	// if (isPlayer()) {
	// getGame().selfMessage("手がないので使えない");
	// }
	// break;
	// }
	// _equip_shooter = item;
	// if (isPlayer()) {
	// getGame().selfMessage(item.getName() + "を装備した");
	// }
	// // 対応する弾丸を自動で装備する
	// boolean chkBlt = false;
	// search: for (int i = 0; i < _items.size(); i++) {
	// Item m = (Item) _items.elementAt(i);
	// if (_equip_shooter._equipBulletItemId == m._itemId) {
	// if (!m._isEquipped) {
	// equipItem(m);
	// }
	// chkBlt = true;
	// break search;
	// }
	// }
	// if (!chkBlt) {
	// if (isPlayer()) {
	// getGame().selfMessage("弾がない");
	// }
	// }
	// break;
	// case Item.EQUIP_TYPE1_SHOT_MATERIAL:
	// if (_equip_shot_material != null) {
	// unEquip(_equip_shot_material);
	// }
	// if (!havePart(PART_LEFT_HAND) && !havePart(PART_LEFT_HAND)) {
	// if (isPlayer()) {
	// getGame().selfMessage("手がないので装備できない");
	// }
	// break;
	// }
	// _equip_shot_material = item;
	// if (isPlayer()) {
	// getGame().selfMessage(item.getName() + "を装備した");
	// }
	// break;
	// default:
	// break;
	// }
	// item._isEquipped = true;
	// // エンチャント再計算
	// _enchantEffect.initPassiveEnchant();
	// }
	//
	// /**
	// * Takes off the specified item.
	// */
	// public void unEquip(Item item) throws Exception {
	// DebugUtil.assertTrue(item._isEquipped);
	// DebugUtil.assertFalse(item._onGround);
	// switch (item._equipType) {
	// case Item.EQUIP_TYPE1_WEAPON:
	// if (item == _equip_rightHand) {
	// _equip_rightHand = null;
	// } else {
	// DebugUtil.assertTrue(_equip_leftHand == item);
	// _equip_leftHand = null;
	// }
	// break;
	// case Item.EQUIP_TYPE1_SHOOTER:
	// DebugUtil.assertTrue(_equip_shooter != null);
	// _equip_shooter = null;
	// break;
	// case Item.EQUIP_TYPE1_SHOT_MATERIAL:
	// DebugUtil.assertTrue(_equip_shot_material != null);
	// _equip_shot_material = null;
	// break;
	// case Item.EQUIP_TYPE1_ARMOR:
	// switch (item._equipType2) {
	// case Item.EQUIP_TYPE2_BODY:
	// DebugUtil.assertTrue(_equip_body != null);
	// _equip_body = null;
	// break;
	// default:
	// DebugUtil.assertFalse(true);// エラー
	// break;
	// }
	// break;
	// default:
	// DebugUtil.assertFalse(true);// エラー
	// break;
	// }
	// item._isEquipped = false;
	// // エンチャント再計算
	// _enchantEffect.initPassiveEnchant();
	// }
	//
	// /**
	// * Picks the item on the ground.
	// */
	// public void pickItem() throws Exception {
	// Item item = getModel().map.get(getCellX(), getCellY())
	// .removeItemByAll();
	// if (item == null) {
	// DebugUtil.error("pickItem不存在");
	// return;
	// }
	// switch (_charaType) {
	// case TYPE_PLAYER:
	// StringBuffer sb = new StringBuffer();
	// sb.append(getNameForMessage());
	// sb.append(item.getName());
	// sb.append("を拾った");
	// getGame().selfMessage(sb.toString());
	// break;
	// default:
	// break;
	// }
	// addItem(item, true);
	// DebugUtil.assertFalse(item._onGround);
	// }
	//
	// /**
	// * Adds specified item to this character's item list.
	// *
	// * @param item
	// * The item to adds.
	// * @param isShouldBeStacked
	// * Whether the specified item should be stacked(if the item is
	// * able to stack).
	// */
	// public void addItem(Item item, boolean isShouldBeStacked) throws
	// Exception {
	// if (item._onGround || item._isEquipped) {
	// // error
	// DebugUtil.assertFalse(true);
	// }
	//
	// if (isShouldBeStacked && item._isStackable) {
	// // stack可能なものをまとめる
	// for (int i = 0; i < _items.size(); i++) {
	// Item wkItem = (Item) _items.elementAt(i);
	// if (item._itemId == wkItem._itemId) {
	// wkItem.setStackNum(item._stackNum + wkItem._stackNum);
	// return;
	// }
	// }
	// }
	// _items.add(item);
	//
	// }
	//
	// /**
	// * Executes some action.
	// */
	// public boolean move() throws Exception {
	// if (!isAbleToMove()) {
	// return false;
	// } else {
	// return ai.onMove(this);
	// }
	// }
	//
	/**
	 * Reduces the move points as a result of moving.
	 */
	public void reduceMovePoints() throws Exception {
		_movePoint -= NORMAL_SPEED;
		_isMoved = true;
	}

	//
	/**
	 * Reduces the move points as a result of some Action like drink a POTION.
	 */
	public void reduceMovePointsAll() throws Exception {
		while (_movePoint >= NORMAL_SPEED) {
			_movePoint -= NORMAL_SPEED;
		}
		_isMoved = true;
	}

	//
	// /** This list is used to calculate the weapon's index. */
	// private static List tempWeaponList = new List(4);
	//
	// /**
	// * Attacks to the specified character nearby.<br>
	// *
	// * 近接攻撃<b> indexは呼び出し回数<br>
	// * 1回目は、右手武器、2回目は左手武器というように順番に実施。おわったらtrueを返却
	// *
	// * @param defender
	// * The character to be attacked by this character.
	// * @param index
	// * The count of calling this method on the turn.
	// * @return Whether this character's attack is end or not.
	// */
	// public boolean attack(Chara defender, int index) throws Exception {
	//
	// Model model;
	// Game game;
	// model = getModel();
	// game = getGame();
	//
	// // 武器インデックスを計算
	// // 最初のみ武器リストを初期化。途中で武器が増減することは考慮しない。
	// tempWeaponList.removeAllElements();
	// if (_equip_rightHand != null) {
	// tempWeaponList.add(_equip_rightHand);
	// }
	// if (_equip_leftHand != null) {
	// tempWeaponList.add(_equip_leftHand);
	// }
	// Item weapon = null;
	// if (tempWeaponList.size() != 0) {
	// weapon = (Item) tempWeaponList.elementAt(index);
	// }
	//
	// // hit判定
	// int attackPoint = getAttackPoint();
	// // ac
	// int ac = defender.getAC();
	// // 率計算
	// double rate = 0;
	// if (attackPoint + ac == 0) {
	// rate = 0.95d;
	// } else {
	// rate = (double) attackPoint / (double) (attackPoint + ac);
	// }
	//
	// // 四捨五入
	// rate = (double) ((int) ((rate + 0.05) * 100d));
	// // ミスの分だけ命中率が高まる
	// rate += _missCount * 8;
	// if (rate > 95) {
	// // 上限
	// rate = 95;
	// } else if (rate < 5) {
	// // 下限
	// rate = 5;
	// }
	//
	// StringBuffer sb = new StringBuffer();
	// if (weapon != null) {
	// // 片手swordの場合
	// switch (weapon.get_equipType2()) {
	// case Item.EQUIP_TYPE2_SWORD:
	// sb.append(defender.getNameForMessage());
	// sb.append("に斬りかかった！");
	// break;
	// default:
	// sb.append(getNameForMessage());
	// sb.append("の攻撃！");
	// break;
	// }
	// } else {
	// sb.append(getNameForMessage());
	// sb.append("の攻撃！");
	// }
	// sb.append("（致傷率 ");
	// sb.append(String.valueOf(rate));
	// sb.append("％）");
	// game.selfMessage(sb.toString());
	//
	// // ロール
	// int roll = model.randomizer.getRandomInt(0, 101);
	// if (roll <= rate) {
	// // 命中
	// _missCount = 0;
	// int damage = 0;
	// boolean isCrit = false;
	// if (weapon == null) {
	// // 素手
	// // FIXME:
	// int wk = (int) (((double) (get_str() + 1) + (get_dex() + 1)) * 0.5d);
	// wk += wk * _skillLvHandToHand * 0.1;
	// int from = (int) (wk * 0.8);
	// int to = wk;
	// damage = model.randomizer.getRandomInt(from, to + 1);
	// } else {
	// // FIXME:両手の場合、各スキルの場合のコード
	// // 片手swordの場合
	// double skill = _skillLvSword;
	// double wk = (((get_str() * 0.5d + get_dex() * 0.5d) * 0.5d) + (skill *
	// 0.5d)) * 0.1d;
	// wk = getModel().randomizer.getRandomInt((int) (wk * weapon
	// .get_equipDmgFrom()), (int) (wk * weapon
	// .get_equipDmgTo()));
	// wk = (double) model.randomizer.getRandomInt(90, 110) * 0.01d
	// * wk;
	// damage = (int) wk;
	//
	// // クリティカル判定
	// if (weapon.get_equipCriticalRate() >= model.randomizer
	// .getRandomInt(0, 101)) {
	// // クリティカル
	// isCrit = true;
	// if (isPlayer() || defender.isPlayer()) {
	// getGame().selfMessage("クリティカル！");
	// }
	// damage = (int) ((double) damage * 2d);
	// }
	//
	// }
	// // dmg
	// if (isCrit) {
	// defender.damage(this, ATT_PHIS, "クリティカル", damage);// XXX:物理のみでよい？
	// } else {
	// defender.damage(this, ATT_PHIS, damage);// XXX:物理のみでよい？
	// }
	//
	// // 命中した。エンチャントの処理を実行
	// if (weapon != null && weapon._equipEnchantActive.length() != 0) {
	// ProcessEnchantAttack proc = new ProcessEnchantAttack(this,
	// defender, weapon, new DataArray(
	// weapon._equipEnchantActive), 0, damage);
	// getGame().processManager.startProcess(proc);
	// }
	// if (damage == 0 || defender.isDead()
	// || index >= tempWeaponList.size() - 1) {
	// return true;// 終
	// } else {
	// return false;
	// }
	// } else {
	// // game.message("しかし命中しなかった");
	// _missCount++;
	// return true;// 終
	// }
	// }
	//
	// /**
	// * Starts sleeping.
	// */
	// public void sleep(boolean showMsg, int intensity) {
	// if (!_state.is(State.SLEEP)) {
	// if (_movePoint > 0) {
	// _movePoint = 0;
	// }
	// if (showMsg)
	// getGame().selfMessage(getNameForMessage() + "は眠りに落ちた");
	// }
	// _state.increase(null, State.SLEEP, intensity);
	// }
	//
	// /**
	// * Sets this character under control of the attacker.
	// */
	// void controll(Chara attacker, int intensity) {
	// if (!_state.is(State.CONTROL)) {
	// getGame().seeMessage(
	// getNameForMessage() + "は" + attacker.getNameForMessage()
	// + "を");
	// getGame().seeMessage("喜ばせたい気持ちで一杯だ");
	// }
	// _state.increase(attacker, State.CONTROL, intensity);
	// }
	//
	// /**
	// * Bothers sleeping.
	// */
	// public void botherSleeping(int intensity) throws Exception {
	// _state.decrease(State.SLEEP, intensity);
	// }
	//
	// /**
	// * Bothers controlling.
	// */
	// void botherControlling(int intensity) throws Exception {
	// _state.decrease(State.CONTROL, intensity);
	// }
	//
	// /**
	// * Returns if this character is sleeping.
	// */
	// public boolean isSleeping() {
	// return _state.is(State.SLEEP);
	// }
	//
	// /**
	// * Returns if this character is under control by enemy.
	// */
	// public boolean isControlled() {
	// return _state.is(State.CONTROL);
	// }
	//
	// /**
	// * Returns if this character is Sane. (正気出ない場合false)
	// */
	// boolean isSane() {
	// return !_state.is(State.CONTROL);
	// }
	//
	// /**
	// * This method is called from scripts.
	// *
	// * @return Whether the bullets has ran out.
	// */
	// public void shoot2(int offsetDegree, boolean isSync, boolean
	// consumeBullets)
	// throws Exception {
	// if (_equip_shot_material == null) {
	// return;
	// }
	//
	// // 射出角度 = 射手角度 + オフセット角度
	// int degree = getDirection() + offsetDegree;
	// if (degree >= 360) {
	// degree -= 360;
	// } else if (degree < 0) {
	// degree += 360;
	// }
	//
	// getGame().effecter.createBulletEffect(getCurrentMaptip(), this,
	// _equip_shot_material._equipId, isSync, degree);
	//
	// // 弾を減らす処理
	// if (consumeBullets) {
	// removeOneItem(_equip_shot_material);
	// if (_equip_shot_material == null && isPlayer()) {
	// // 弾切れ、消滅させる
	// getGame().selfMessage("弾切れのようだ");
	// }
	// }
	// }
	//
	// /**
	// * Does the process of the hitting by a gun or other projectile weapon.
	// *
	// * @param defender
	// * The character that is shoot by this character.
	// * @param cellDistance
	// * The distance between the defender and this character(shooter).
	// * @return 当たったらTRUE
	// */
	// boolean onHitBullet(Chara defender, int cellDistance) throws Exception {
	//
	// // 命中率
	// int shotPoint = getShootPoint();
	// // ac
	// int ac = defender.getAC();
	// // 率計算
	// double rate = 0;
	// if (shotPoint + ac == 0) {
	// rate = 0.95d;
	// } else {
	// rate = (double) shotPoint / (double) (shotPoint + ac);
	// }
	//
	// // 四捨五入
	// rate = (double) ((int) ((rate + 0.05) * 100d));
	// // Item補正
	// if (_equip_shooter != null) {
	// rate += ((double) _equip_shooter.get_equipHitAssist() * 0.01d * rate);
	// }
	// // 距離補正
	// int range = _equip_shooter.getRange();
	// double distFix = (double) range / (double) cellDistance;
	// if (distFix > 1.0) {
	// distFix = 1;
	// }
	// rate *= distFix;
	// // ミスした分だけ命中率が高まる
	// rate += _missCount * 5;
	// // 上限下限チェック
	// if (rate > 95) {
	// rate = 95;
	// } else if (rate < 5) {
	// rate = 5;
	// }
	// // 命中判定
	// int roll = getModel().randomizer.getRandomInt(0, 101);
	// if (rate < roll) {
	// // 外した
	// _missCount++;
	// return false;
	// }
	// _missCount = 0;
	//
	// int damage = getModel().randomizer.getRandomInt(_equip_shooter
	// .get_equipDmgFrom(), _equip_shooter.get_equipDmgTo());
	// // dex補正
	// damage += (int) ((double) get_dex() * 0.2d);
	//
	// // 距離補正
	// // 威力劣化率 0で0.5
	// double rekka = (double) range / (double) cellDistance;
	// if (rekka < 1d) {
	// // 射程を超えている場合、加速度的に減衰させる
	// rekka /= 2;
	// } else {
	// rekka = 1;
	// }
	// damage *= rekka;
	//
	// // クリティカル判定
	// boolean isCrit = false;
	// if (_equip_shooter.get_equipCriticalRate() >= getModel().randomizer
	// .getRandomInt(0, 101)) {
	// // クリティカル
	// if (isPlayer() || defender.isPlayer()) {
	// getGame().selfMessage("クリティカル！");
	// }
	// damage = (int) ((double) damage * 2d);
	// isCrit = true;
	// }
	// // dmg
	// if (isCrit) {
	// defender.damage(this, ATT_PHIS, "クリティカル", damage);// XXX:物理のみでよい？
	// } else {
	// defender.damage(this, ATT_PHIS, damage);// XXX:物理のみでよい？
	// }
	// return true;
	// }
	//
	// /**
	// * Shoot with the gun or other projectile weapon.
	// *
	// * @return Whether this character could execute shooting or not.
	// */
	// public boolean shoot1() throws Exception {
	// if (_equip_shooter == null) {
	// if (isPlayer()) {
	// getGame().selfMessage("遠隔武器を装備していない");
	// }
	// return false;
	// }
	// if (_equip_shooter._equipType2 == Item.EQUIP_TYPE2_GUN) {
	// return shootWithGun();
	// } else if (_equip_shooter._equipType2 == Item.EQUIP_TYPE2_BOW) {
	// return ShootWithBow();
	// }
	// return false;
	// }
	//
	// private boolean shootWithGun() throws Exception {
	// if (_equip_shot_material == null) {
	// if (isPlayer()) {
	// getGame().selfMessage("弾が無い");
	// }
	// return false;
	// }
	// if (_equip_shooter._equipBulletItemId != _equip_shot_material._itemId) {
	// // 正しい弾を装備していない
	// if (isPlayer()) {
	// getGame().selfMessage("それを撃ちだすことはできない");
	// }
	// return false;
	// }
	// // script FIXME:キャッシュ化
	// getModel().scriptCurrentChara = this;
	// switch (_equip_shooter._equipId) {
	// case 2:// マシンガン
	// getGame().execScript(1, "ak47", null);
	// break;
	// case 7:// マグナム
	// getGame().execScript(1, "magnum", null);
	// break;
	// case 8:// ハンドガン
	// getGame().execScript(1, "handgun", null);
	// break;
	// case 9:// ショットガン
	// getGame().execScript(1, "shotgun", null);
	// break;
	// case 12:// ヘビーマシンガン
	// getGame().execScript(1, "hvygun", null);
	// break;
	// case 11:// スナイパーライフル
	// getGame().execScript(1, "snipe", null);
	// break;
	// case 13:// オートショットガン
	// getGame().execScript(1, "shotgun2", null);
	// break;
	// default:
	// DebugUtil.assertFalse(true);// error
	// break;
	// }
	// return true;
	// }
	//
	// private boolean ShootWithBow() {
	// return false;
	// }
	//
	// /** The wait time when player performed some action like open/close door.
	// */
	// private static long WAIT_TIME_EXEC_ACTION = 200;
	//
	// /**
	// * Executes some action to the target cell, if its possible. If there is
	// no
	// * action is possible, nothing would be happen and returns false.
	// *
	// * @param mapTip
	// * The target MapTip of the action.
	// * @return Whether This character executed action or not.
	// */
	// public boolean doSomethingTo(MapTip mapTip) throws Exception {
	//
	// MapObject obj = mapTip.getMapObject();
	// if (obj != null) {
	// // マップオブジェクトがある場合
	// if (obj.isDoor()) {
	// // ドアの場合
	// if (obj.isDoorOpen()) {
	// // 空いている場合
	// if (!havePart(PART_LEFT_HAND) && !havePart(PART_RIGHT_HAND)) {
	// if (isPlayer()) {
	// getGame().selfMessage("手がないのでできない");
	// }
	// }
	// if (mapTip.getChara() != null) {
	// if (isPlayer()) {
	// getGame().selfMessage(
	// mapTip.getChara().getNameForMessage()
	// + "がいるのでできない");
	// }
	// } else {
	// if (isPlayer() && !isSane()) {
	// obj.closeDoor();
	// getGame().selfMessage("扉を閉めた。");
	// StatePlayerTurnEnd.get().waitTime = WAIT_TIME_EXEC_ACTION;
	// // 視界範囲更新
	// ApplicationFacade.getGame().setVisible(
	// ApplicationFacade.getModel().player);
	// recalculatesDistanceMap();
	// return true;
	// }
	// }
	// } else {
	// // 閉じている場合
	// if (!havePart(PART_LEFT_HAND) && !havePart(PART_RIGHT_HAND)) {
	// if (isPlayer()) {
	// getGame().selfMessage("手がないのでできない");
	// }
	// } else {
	// if (obj.isLocked()) {// 鍵がかかっている場合
	// if (isPlayer()) {
	// getGame().selfMessage("鍵がかかっている");
	// }
	// } else {// 鍵がかかっていない場合
	// obj.openDoor();
	// if (isPlayer()) {
	// getGame().selfMessage("扉を開けた");
	// StatePlayerTurnEnd.get().waitTime = WAIT_TIME_EXEC_ACTION;
	// // 視界範囲更新
	// ApplicationFacade.getGame().setVisible(
	// ApplicationFacade.getModel().player);
	// recalculatesDistanceMap();
	// }
	// return true;
	// }
	// }
	// }// IF 閉じている場合
	//
	// }// ドアの場合
	//
	// }// マップオブジェクトがある場合
	// return false;
	// }
	//
	// /**
	// * Returns the armor class of this character
	// */
	// public int getAC() {
	// int ac = (int) ((_level * 0.1) + (get_luc() * 0.1) + (get_agi() * 0.1));
	// ac += (_equip_body == null ? 0 : _equip_body.get_equipAC());
	// ac += get_ac();
	// return ac;
	// }
	//
	// /**
	// * Returns the damage that is rolled by this character(attacker).
	// */
	// int getAttackPoint() {
	// int result = 0;
	//
	// // 基本
	// result += _level * 0.1d + get_str() * 0.5d + get_dex() * 0.5d + 0.1d
	// * get_luc();
	// if (isMonster()) {
	// result += ((0.1d * (double) (get_str() + get_dex()) / 2) * _level *
	// 0.1d);
	// }
	// result /= 3;
	// // 熟練度補正
	// // スキル補正
	// // Item補正
	// if (_equip_rightHand != null) {
	// result += (int) ((double) result
	// * (double) _equip_rightHand.get_equipHitAssist() * 0.01d);
	// }
	// if (result < 1) {
	// result = 1;
	// }
	// return result;
	// }
	//
	// /**
	// * Returns the damage that is rolled by this character(attacker) by gun.
	// */
	// public int getShootPoint() {
	// int result = 0;
	// // 基本
	// result += (0.5d * get_dex()) + (0.1d * _luc) + _skillLvGun;
	// // Lv補正
	// result += (_level * 0.1d);
	// // 熟練度補正
	// // スキル補正
	// return result;
	// }
	//
	// /**
	// * Returns whether this character is dead or alive.
	// */
	// public boolean isDead() {
	// return get_hp() - _hp_reduce <= 0;
	// }
	//
	// /**
	// * Returns the ratio of the (remaining life / max life) of this character.
	// */
	// public double getHpRatio() {
	// return ((double) get_hp() - (double) _hp_reduce) / (double) get_hp();
	// }
	//

	/**
	 * Adds the damage and reduce life of this character.
	 */
	public void damage(ICharaClbInterface attacker_clb, String attribute,
			int damage) throws Exception {

		Chara attacker = (Chara) attacker_clb;

		if (!isAlive()) {
			return;
			// DebugUtil.error("既に死亡している");
			// DebugUtil.assertFalse(true);// error
			// return;
		}

		// 耐性計算
		// int tol = _tolerance.getTolerance(attribute);
		// tol += _enchantEffect._en_tolerance.getTolerance(attribute);
		// if (tol <= Tolerance.MAX_TOLERANCE) {
		// // 耐性修正
		// int fix = (int) (damage * (tol * 0.01d));
		// if (damage - fix < 0) {
		// // 下限は0
		// damage = 0;
		// } else {
		// damage -= fix;
		// }
		// } else {
		// // 吸収
		// regenerate(damage, true);
		// }

		// ダメージ
		_hp_reduce += damage;
		if (getHp() - _hp_reduce < 0) {
			_hp_reduce = getHp();
		}

		// メッセージ
		// StringBuffer sb = new StringBuffer(prefixMsg);
		// if (sb.length() != 0) {
		// sb.append(":");
		// }
		// sb.append(damage);
		// getGame().effecter.createDamageEffect(this, sb.toString());
		SkillFactory.getInstance().createDamageMessage(this, damage);
		SkillFactory.getInstance().createBloodEffect(this);

		if (!isAlive()) {
			// sb = new StringBuffer();
			// sb.append(getNameForMessage());
			// sb.append("は死んだ");
			// getGame().seeMessage(sb.toString());

			if (attacker != null && attacker.isPlayer()) {
				// int exp = attacker.calcExp(this);
				// getGame().effecter.createExpEffect(this, exp);
				// attacker.addExp(exp);
				//
				// sb = new StringBuffer();
				// sb.append(exp);
				// sb.append("の経験を得た。");
				// getGame().seeMessage(sb.toString());
			}
		}
		// 死亡判定
		if (!isAlive()) {
			// ai.onDeath(this);
			// sb = new StringBuffer();
			// if (_charaType == TYPE_PLAYER) {
			// // キャラをゲームから除外
			// DebugUtil.debug("debug", "pl dead remove start");
			// getModel().removePlayer(this);
			// DebugUtil.debug("debug", "pl dead remove end");
			// } else if (_charaType == TYPE_MONSTER) {
			// // キャラをゲームから除外
			// DebugUtil.debug("debug", "chara dead remove start");
			// getModel().removeMonster(this);
			// DebugUtil.debug("debug", "chara dead remove end");
			// } else if (_charaType == TYPE_GOOD_NPC) {
			// // キャラをゲームから除外
			// DebugUtil.debug("debug", "chara dead remove start");
			// getModel().removeGoodNpc(this);
			// DebugUtil.debug("debug", "chara dead remove end");
			// } else {
			// DebugUtil.assertFalse(true);
			// }
			// getCurrentMaptip().removeChara(this);
		} else {
			// // 属性による効果
			// if (Chara.ATT_POIS.equals(attribute)) {
			// // poison化
			// int pRate = 50;// 50%の確率がデフォルト
			// if (tol != -1) {
			// pRate = (_tolerance.getTolerance(Chara.ATT_POIS) * 10) + 50;
			// if (pRate <= 5) {
			// pRate = 5;
			// } else if (pRate >= 95) {
			// pRate = 95;
			// }
			// }
			// if (getModel().randomizer.getRandomInt(0, 101) <= pRate) {
			// // Poison化決定
			// if (isPlayer()) {
			// if (_state.is(State.POISON)) {
			// getGame().selfMessage(
			// getNameForMessage() + "は更に気分が悪くなった");
			// } else {
			// getGame().selfMessage(
			// getNameForMessage() + "は気分が悪くなった");
			// }
			// }
			// _state.increase(attacker, State.POISON, (short) (_state
			// .get(State.POISON) + (pRate / 6)));// pRateラウンド
			// }
		}
		// ai.onDamaged(this);
	}

	// }

	//
	/**
	 * Regenerate the life of this character.
	 */
	public void regenerate(int regen, boolean needEffect) throws Exception {
		if (_hp_reduce > 0) {
			_hp_reduce -= regen;
			if (_hp_reduce < 0) {
				_hp_reduce = 0;
			}
			// if (needEffect) {
			// getGame().effecter.createRegenerateEffect(this, regen);
			// }
		}
	}

	//
	/** The width of the life bar. */
	private static final int LEN_HP_BAR = 18;
	/** The height of the life bar. */
	private static final int HEIGHT_HP_BAR = 2;

	public void setDirection(int degree) {
		DebugUtil.assertTrue(degree % 45 == 0);
		_direction = degree;
	}

	//
	/**
	 * Draws the character sprite.
	 */
	public void draw(GameGraphic g, int offsetX, int offsetY) throws Exception {
		_anime.setPosition(getX(), getY());
		_anime.draw(g, offsetX, offsetY);

		HmFont.setFont(g, HmFont.STYLE_PLAIN, HmFont.FONT_TINY);
		g.drawBorderString(_levelString, (int) getX() + 4 - offsetX,
				(int) getY() - offsetY - 18, 0xffffff, 0x000000);

		if (!isPlayer()) {
			// hpバー座標
			int x = (int) getX() + 4 - offsetX;
			int y = (int) getY() - 5 - offsetY;
			// 割合
			double hpRate = ((double) getHp() - _hp_reduce) / (double) getHp();
			int len = (int) ((hpRate * LEN_HP_BAR) + 0.5d);
			if (len == 0) {
				if (getHp() != _hp_reduce) {
					len = 1;
				}
			}
			if (hpRate <= 0.20d) {
				// 2割以下
				g.setColor(0xff5555);
			} else if (hpRate <= 0.4d) {
				// 4割以下
				g.setColor(0xffff55);
			} else {
				g.setColor(0x3394ff);
			}
			g.fillRect(x, y, len, HEIGHT_HP_BAR);
			g.setColor(0x000000);
			g.drawRect(x - 1, y - 1, LEN_HP_BAR + 1, HEIGHT_HP_BAR + 1);

		}
		// // _movePoint
		// g.setColor(0x5555ff);
		// HmFont.setFont(g, HmFont.STYLE_ITALIC, HmFont.FONT_TINY);
		// g.drawBorderString(String.valueOf(_movePoint), (int) getX() + 4
		// - offsetX, (int) getY() - offsetY - (anime.getHeight() / 2)
		// + 10, 0x5555ff, 0x000000);

		// #ifdef debug
		if (true) {
			g.setColor(0xff0000);
			int centerX = (int) getX() + 12 - offsetX;
			int centerY = (int) getY() + 12 - offsetY;
			int offset = 12;
			switch (_direction) {
			case 0:
				g.fillRect(centerX + offset, centerY, 3, 3);
				break;
			case 45:
				g.fillRect(centerX + offset, centerY + offset, 3, 3);
				break;
			case 90:
				g.fillRect(centerX, centerY + offset, 3, 3);
				break;
			case 135:
				g.fillRect(centerX - offset, centerY + offset, 3, 3);
				break;
			case 180:
				g.fillRect(centerX - offset, centerY, 3, 3);
				break;
			case 225:
				g.fillRect(centerX - offset, centerY - offset, 3, 3);
				break;
			case 270:
				g.fillRect(centerX, centerY - offset, 3, 3);
				break;
			case 315:
				g.fillRect(centerX + offset, centerY - offset, 3, 3);
				break;
			default:
				System.out.println("what:" + _direction);
				break;
			}
		}
	}

	//
	// /**
	// * Returns the experience point to become next level.
	// */
	// public long get_expNext() {
	// return _expNext;
	// }
	//
	// /**
	// * Returns the total experience point this character has earned.
	// */
	// public long get_expTotal() {
	// return _expTotal;
	// }
	//
	// /**
	// * Returns the sexuality of this character.
	// */
	// public int get_sex() {
	// return _sex;
	// }
	//
	// /**
	// * Returns the speed of this character.
	// */
	// public int get_speed() {
	// return _speed + _enchantEffect._en_speed;
	// }
	//
	// /**
	// * Returns the intelligence of this character.
	// */
	// public int get_int() {
	// return _int + _enchantEffect._en_int;
	// }
	//
	// /**
	// * Returns whether this character should be removed from the memory when
	// the
	// * player changes the floor. <br>
	// * 階層移動時にメモリから削除する対象か否か。
	// */
	// public boolean isTobeClear() {
	// if (isPlayer()) {
	// return false;
	// } else if (isGoodNPC()) {
	// return false;
	// }
	// return true;// とりあえず、プレイヤー以外はクリア
	// // TODO NPC、ペット
	// }
	//
	/**
	 * Returns the vitality of this character.
	 */
	public int getVit() {
		return _vit;
		// return _vit + _enchantEffect._en_vit;
	}

	/**
	 * Returns the agility of this character.
	 */
	public int getAgi() {
		return _agi;
		// return _agi + _enchantEffect._en_agi;
	}

	/**
	 * Returns the luck of this character.
	 */
	public int getLuc() {
		return _luc;
		// return _luc + _enchantEffect._en_luc;
	}

	/**
	 * Returns the dexterity of this character.
	 */
	public int getDex() {
		// return _dex + _enchantEffect._en_dex;
		return _dex;
	}

	/**
	 * Returns the max life of this character.
	 */
	public int getHp() {
		return _hp;
		// return _hp + _enchantEffect._en_hp;
	}

	/**
	 * Returns the reduced life of this character.
	 */
	public int getHp_reduce() {
		return _hp_reduce;
	}

	//
	// /**
	// * Returns the max Mana of this character.
	// */
	// public int get_mp() {
	// return _mp + _enchantEffect._en_mp;
	// }
	//
	// /**
	// * Returns the reduced Mana of this character.
	// */
	// public int get_mp_reduce() {
	// return _mp_reduce;
	// }
	//
	// /**
	// * Returns the level of this character.
	// */
	// public String get_levelText() {
	// return _levelString;
	// }
	//
	// /**
	// * Returns the skill of sword of this character.
	// */
	// public int get_skillLvSword() {
	// return _skillLvSword;
	// }
	//
	// /**
	// * Returns the skill of gun of this character.
	// */
	// public int get_skillLvGun() {
	// return _skillLvGun;
	// }
	//
	// /**
	// * Returns the skill of castinc spell of this character.
	// */
	// public int get_skillLvSpell() {
	// return _skillLvSpell;
	// }
	//
	// /**
	// * Returns the skill of throwing something of this character.
	// */
	// public int get_skillLvThrowing() {
	// return _skillLvThrowing;
	// }
	//
	// /**
	// * Returns the skill of fighting without weapon of this character.
	// */
	// public int get_skillLvHandToHand() {
	// return _skillLvHandToHand;
	// }
	//
	// /**
	// * Returns the level of this character.
	// */
	// public int get_level() {
	// return _level;
	// }
	//
	// /**
	// * Returns the strength of this character.
	// */
	// public int get_str() {
	// return _str + _enchantEffect._en_str;
	// }
	//
	// /**
	// * Returns the armor class of this character.
	// */
	// public int get_ac() {
	// int ret = _ac + _enchantEffect._en_ac;
	// if (isMonster()) {
	// ret += _ac * _level / 5;
	// }
	// return ret;
	// }

	private int _cellX = 0;

	private int _cellY = 0;

	private double _x = 0;

	private double _y = 0;

	private Animation _anime = null;

	// /** The Enchant instance this character has. */
	// private Enchant _enchantEffect = null;
	// /** The State instance this character has. */
	// private State _state = null;
	// /** The Tolerance instance this character has. */
	// private Tolerance _tolerance = null;
	/**
	 * This values holds the active enchant this character has, without any
	 * equipment or other special effects. Ex) "4=5/"
	 */
	private String _defaultActiveEnchant = null;
	/**
	 * This values holds the passive enchant this character has, without any
	 * equipment or other special effects Ex) "4=5/".
	 */
	private String _defaultPassiveEnchant = null;

	/** The distance this character can see without any effect. */
	private int _visibleArea = 5;// 視界基本

	/**
	 * This text holds all the skills of this character. For example, "4=0/0=0"
	 */
	private String _strSkill = "";
	/** This is the list of this character's skills. */
	private DataArray _skill = null;

	/** The item this character equips. */
	IItemClbInterface _equip_rightHand = null;

	/** The item this character equips. */
	IItemClbInterface _equip_leftHand = null;

	/** The item this character equips. */
	IItemClbInterface _equip_shooter = null;// 遠隔

	public IItemClbInterface getEquip_shooter() {
		return _equip_shooter;
	}

	public void setEquip_shooter(IItemClbInterface equipShooter) {
		_equip_shooter = equipShooter;
	}

	/** The item this character equips. */
	IItemClbInterface _equip_shot_material = null;// 遠隔

	/** The item this character equips. */
	IItemClbInterface _equip_body = null;// 体

	// /** This is used for calculating the distance map. */
	// private DistanceMap distanceMap = null;
	//
	// /** The AI of this character. */
	// private AiBasic ai = null;

	/** The id of this character. */
	int _charaId = 0;

	/**
	 * The direction of this character, and this must be 0 to 315 by 45 degrees.
	 */
	int _direction = 0;

	/** The level of this character. */
	int _level = 0;

	/** The exp point to the next level of this character. */
	long _expNext = 0;

	/** The total exp point of this character. */
	long _expTotal = 0;

	/** The name of this character. */
	String _name = "ななし";

	/** The sex of this character. */
	int _sex = 0;

	/** The sex of this character. */
	int _speed = 12;

	/** The strength of this character. */
	int _str = 0;

	/** The intelligence of this character. */
	int _int = 0;

	// int _pie = 0;

	/** The vitality of this character. */
	int _vit = 0;

	/** The agility of this character. */
	int _agi = 0;

	/** The luck of this character. */
	int _luc = 0;

	/** The dexterity of this character. */
	int _dex = 0;

	/** The intelligence of this character. */
	int _ac = 0;

	/** The total life of this character. */
	int _hp = 10;

	/** The reduced life of this character. */
	int _hp_reduce = 0;

	/** The total mana of this character. */
	int _mp = 10;

	/** The reduced mana of this character. */
	int _mp_reduce = 0;

	/** The remaining move point of this character. */
	int _movePoint = 0;

	public int getMovePoint() {
		return _movePoint;
	}

	public void setMovePoint(int movePoint) {
		_movePoint = movePoint;
	}

	/**
	 * The body parts of this character, for instance "頭胴右左足"
	 */
	String _parts = null; /* 体のパーツ */

	/**
	 * this holds the text represents the level of this character , for instance
	 * "40Lv"
	 */
	private String _levelString = null;

	/**
	 * The flag indicates whether this character moved or not in current turn.
	 */
	boolean _isMoved = false;

	public boolean isMoved() {
		return _isMoved;
	}

	public void setMoved(boolean isMoved) {
		_isMoved = isMoved;
	}

	/** This value indicates the character's skill Level of using sword. */
	int _skillLvSword = 0;
	/** This value indicates the character's skill Level of using gun. */
	int _skillLvGun = 20;
	/** This value indicates the character's skill Level of casting spell. */
	int _skillLvSpell = 0;
	/**
	 * This value indicates the character's skill Level of throwing something.
	 */
	int _skillLvThrowing = 0;

	public void setSkillLvThrowing(int skillLvThrowing) {
		_skillLvThrowing = skillLvThrowing;
	}

	/**
	 * This value indicates the character's skill Level of fighting without
	 * weapon.
	 */
	int _skillLvHandToHand = 0;

	/** The counts of the total turns of this character. */
	long _turn = 0;
	/** The counts of the attack miss. */
	int _missCount = 0;// 攻撃ミスの回数カウント
	/**
	 * The reduced speed of this character. This is calculate every initial
	 * round.
	 */
	int _speedDown = 0;// 状態異常などによる速度低下分 roundの最初に再計算する

	private int _charaType = TYPE_DEFAULT;

	public int getInitiative() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void initRound() throws Exception {
		addMovePoint(NORMAL_SPEED);
	}

	void useMovePoint(int point) {
		_movePoint -= point;
	}

	void addMovePoint(int point) {
		_movePoint += point;
	}

	private void recalcDistance() throws Exception {
		distanceHolder.recalcDistance(getNotifyDistance(), getCellX(),
				getCellY());
	}

	public void initTurn() {

	}

	public boolean isExists() {
		return true;
	}

	public boolean isTurnEnd() {
		return getMovePoint() < NORMAL_SPEED;
	}

	public void move() throws Exception {
		if (_action.isDoingAction(this)) {
			_action.doAction(this);
		} else {
			_ai.move(this);
			_action.doAction(this);
		}
	}

	private ICharaAction _action = null;

	public void changeAction(ICharaAction action) throws Exception {
		_action = action;
	}

	boolean isAlive() {
		return getHp() - getHp_reduce() > 0;
	}

	void watchAround() throws Exception {
		// プレイヤーなら視界範囲更新
		// if (_chara.isPlayer()) {
		// ApplicationFacade.getGame().setVisible(_chara);
		// }

	}

	/**
	 * 対象のセルに行くことが可能であればTRUE、それ以外はFALSEを返却する。
	 * <前提条件>対象のセルは現在セルと隣り合った8方向のセルのうちのどれかであること。
	 * 
	 * @param toCellX
	 * @param toCellY
	 * @return
	 * @throws Exception
	 */
	public boolean canGoTo(int toCellX, int toCellY) throws Exception {
		IMaptipClbInterface tip = (IMaptipClbInterface) MapFactory.getMap()
				.get(toCellX, toCellY);
		if (tip.isWallOrBarrier()) {
			return false;
		} else if (tip.isReserved()) {
			return false;
		}
		if ((toCellX - getCellX() != 0 && toCellY - getCellY() != 0)
				&& !canDoSomeThingDiagonally(getCellX(), getCellY(), toCellX,
						toCellY)) {
			return false;
		}
		return true;
	}

	private static final int DIRECTION_UP = 270;
	private static final int DIRECTION_DOWN = 90;
	private static final int DIRECTION_RIGHT = 0;
	private static final int DIRECTION_LEFT = 180;
	private static final int DIRECTION_UPRIGHT = 315;
	private static final int DIRECTION_UPLEFT = 225;
	private static final int DIRECTION_DOWNRIGHT = 45;
	private static final int DIRECTION_DOWNLEFT = 135;

	private static final int[][] NEIGHBOR_CELL = // 角度,x,y
	{ { 0, 1, 0 }, // 0°,x,y
			{ 45, 1, 1 }, // 45°,x,y
			{ 90, 0, 1 }, // 90°,x,y
			{ 135, -1, 1 }, // 135°,x,y
			{ 180, -1, 0 }, // 180°,x,y
			{ 225, -1, -1 }, // 225°,x,y
			{ 270, 0, -1 }, // 270°,x,y
			{ 315, 1, -1 }, // 315°,x,y
	};

	/**
	 * 指定方向の隣り合うMAPTIPを返却する。
	 * 
	 * @param direction
	 *            DIRECTION_*のいずれかの値。(90度刻みで0度~315度)
	 * @return
	 * @throws Exception
	 */
	public IMaptipClbInterface getNeighborCellMapTip(int direction)
			throws Exception {
		int wk = direction % 45;
		return (IMaptipClbInterface) MapFactory.getMap().get(
				getCellX() + NEIGHBOR_CELL[wk][1],
				getCellY() + +NEIGHBOR_CELL[wk][2]);
	}

	/**
	 * 指定方向の隣り合うセルを返却する。
	 * 
	 * @param direction
	 *            DIRECTION_*のいずれかの値。(90度刻みで0度~315度)
	 * @return
	 * @throws Exception
	 */
	public IntPosition getNeighborCell(int direction) throws Exception {
		int wk = direction % 45;
		return new IntPosition(getCellX() + NEIGHBOR_CELL[wk][1], getCellY()
				+ +NEIGHBOR_CELL[wk][2]);
	}

	private int[][] neighborCell = { { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 },
			{ -1, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 } };

	public IMaptipClbInterface getFrontMapTip() throws Exception {
		int idx = getDirection() / 45;
		return (IMaptipClbInterface) MapFactory.getMap().get(
				getCellX() + neighborCell[idx][0],
				getCellY() + neighborCell[idx][1]);
	}

	public IMaptipClbInterface getCurrentMapTip() throws Exception {
		return (IMaptipClbInterface) MapFactory.getMap().get(getCellX(),
				getCellY());
	}

	/**
	 * Returns the distance between this character and the specified cell. If
	 * the character is next to the cell, the return value would be 1 or 2. And
	 * if the distance is too far, returns -1.The value would be larger As the
	 * distance is to be far.
	 */
	public int getDistance(int cellX, int cellY) throws Exception {
		int ret = distanceHolder.getDistanceScore(cellX, cellY);
		return ret;
	}

	/**
	 * 向きを対象のセルに向ける
	 * 
	 * @param c
	 * @param tgMapTip
	 * @throws Exception
	 */
	public void lookToTGCell(IMaptipClbInterface tgMapTip) throws Exception {
		// TGの方向を向く
		int ret = MapFactory.getMap()
				.getDirection(getCurrentMapTip(), tgMapTip);
		setDirection(ret);
	}

	/**
	 * Returns direction by one of the "DIRECTION_*" constants. If the position
	 * is not next to the actor, Returns -1;
	 */
	public int getDirectionToNext(int toCellX, int toCellY) throws Exception {
		int cx = getCellX();
		int cy = getCellY();
		int wx = GameMath.abs(toCellX - cx);
		int wy = GameMath.abs(toCellY - cy);
		DebugUtil.assertSmaller(wx, 2);
		DebugUtil.assertSmaller(wy, 2);
		if (cy < toCellY) {
			if (cx == toCellX) {
				return DIRECTION_DOWN;
			} else if (cx > toCellX) {
				return DIRECTION_DOWNLEFT;
			} else {
				return DIRECTION_DOWNRIGHT;
			}
		} else if (cy == toCellY) {
			if (cx > toCellX) {
				return DIRECTION_LEFT;
			} else {
				return DIRECTION_RIGHT;
			}
		} else if (cy > toCellY) {
			if (cx == toCellX) {
				return DIRECTION_UP;
			} else if (cx > toCellX) {
				return DIRECTION_UPLEFT;
			} else {
				return DIRECTION_UPRIGHT;
			}
		}
		DebugUtil.error();
		return -1;

	}

	/**
	 * If its able to move to the cell, Returns true. This method considers only
	 * about walls.
	 */
	public boolean canDoSomeThingDiagonally(int fromCellX, int fromCellY,
			int toCellX, int toCellY) throws Exception {

		// 斜め移動可能確認
		int moveX = toCellX - fromCellX;
		int moveY = toCellY - fromCellY;
		Map map = MapFactory.getMap();
		if (moveX == 1 && moveY == 1) {
			// 右下
			// 右か下に壁があると不可
			if (map.get(fromCellX, fromCellY + 1).isWallOrBarrier()) {
				return false;
			} else if (map.get(fromCellX + 1, fromCellY).isWallOrBarrier()) {
				return false;
			} else {
				return true;
			}
		} else if (moveX == -1 && moveY == 1) {
			// 左下
			// 左か下に壁があると不可
			if (map.get(fromCellX, fromCellY + 1).isWallOrBarrier()) {
				return false;
			} else if (map.get(fromCellX - 1, fromCellY).isWallOrBarrier()) {
				return false;
			} else {
				return true;
			}
		} else if (moveX == -1 && moveY == -1) {
			// 左上
			// 左か上に壁があると不可
			if (map.get(fromCellX, fromCellY - 1).isWallOrBarrier()) {
				return false;
			} else if (map.get(fromCellX - 1, fromCellY).isWallOrBarrier()) {
				return false;
			} else {
				return true;
			}
		} else if (moveX == 1 && moveY == -1) {
			// 右上
			// 右か上に壁があると不可
			if (map.get(fromCellX, fromCellY - 1).isWallOrBarrier()) {
				return false;
			} else if (map.get(fromCellX + 1, fromCellY).isWallOrBarrier()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * メインメニューの表示
	 * 
	 * @throws Exception
	 */
	public void showMainMenu() throws Exception {
		MenuFactory.getMainMenu().show(this, new IMainMenuCallBackInterface() {

			public void selected(int choice) throws Exception {
				switch (choice) {
				case IMainMenuInterface.MENU_PL_SKILL:
					showSkillMenu();
					break;
				case IMainMenuInterface.MENU_PL_ITEM:
					showItemListMenu();
					break;
				default:
					break;
				}

			}
		});
	}

	/**
	 * Skillメニューの表示
	 * 
	 * @throws Exception
	 */
	private void showSkillMenu() throws Exception {
		MenuFactory.getSkillMenu().show(
				new DataArray("0=0/1=0/2=0/3=0/4=0/5=0"),
				new ISkillMenuCallBackInterface() {
					public void selected(int choice) throws Exception {
						execSkill(choice);
					}
				});
	}

	/**
	 * ItemListメニューの表示
	 * 
	 * @throws Exception
	 */
	private void showItemListMenu() throws Exception {
		_itemListMenu = MenuFactory.getItemListMenu();
		_itemListMenu.show(getItems(), new IItemMenuCallBackInterface() {
			public void selected(IItemClbInterface selectedItem)
					throws Exception {
				// アイテム使用メニューの表示
				showItemUseMenu(selectedItem);
			}
		});
	}

	private static final String ITEM_USE_CANCEL = "キャンセル";
	private static final String ITEM_USE_SORT = "ソート";
	private static final String ITEM_USE_CLOSE = "閉じる";
	private static final String ITEM_USE_DRINK = "飲む";
	private static final String ITEM_USE_UNEQUIP = "外す";
	private static final String ITEM_USE_EQUIP = "装備する";
	private static final String ITEM_USE_THROW = "投げる";
	private static final String ITEM_USE_SUMUP = "まとめる";
	private static final String ITEM_USE_DEVIDE = "分ける";
	private static final String ITEM_USE_PLACE = "置く";
	private IItemListMenuInterface _itemListMenu = null;
	private IItemClbInterface _selectedItem = null;

	/**
	 * Item使用メニューの表示
	 * 
	 * @param selectedItem
	 *            選択されたアイテム
	 * @param i
	 *            IItemListMenuInterfaceインスタンス
	 * @throws Exception
	 */
	private void showItemUseMenu(IItemClbInterface selectedItem)
			throws Exception {
		_selectedItem = selectedItem;
		List l = new List();
		l.add(ITEM_USE_CANCEL);
		l.add(ITEM_USE_SORT);

		if (selectedItem != null) {
			// アイテム使用選択肢
			switch (selectedItem.getItemType()) {
			case IItemClbInterface.ITEM_DRINK:
				l.add(ITEM_USE_DRINK);
				break;
			case IItemClbInterface.ITEM_EQUIP:
				if (selectedItem.isEquipped()) {
					l.add(ITEM_USE_UNEQUIP);
				} else {
					l.add(ITEM_USE_EQUIP);
				}
				break;
			}
			l.add(ITEM_USE_THROW);
			IMaptipClbInterface tip = getCurrentMapTip();
			if (selectedItem.isStackable()
					&& !(tip.hasItem() && tip.getItem() == selectedItem)) {
				// 床に置いていない。かつスタック可能な場合
				l.add(ITEM_USE_SUMUP);
				if (selectedItem.getStackedNum() > 1) {
					// 分けられる場合。
					l.add(ITEM_USE_DEVIDE);
				}
			}

			if (!(tip.hasItem() && tip.getItem() == selectedItem)) {
				// 床にないアイテムの場合
				l.add(ITEM_USE_PLACE);
			}
		}

		l.add(ITEM_USE_CLOSE);

		MenuFactory.getSimpleSelectMenu().show(l,
				new ISimpleSelectMenuCallBackInterface() {
					public void selected(String answer) throws Exception {
						if (ITEM_USE_CLOSE.equals(answer)) {
							_itemListMenu.end();
						} else if (ITEM_USE_SORT.equals(answer)) {
							_itemHolder.sortItems();
							_itemListMenu.end();
							showItemListMenu();
						} else if (ITEM_USE_EQUIP.equals(answer)) {
							equipItem(_selectedItem);
							_itemListMenu.end();
						} else if (ITEM_USE_UNEQUIP.equals(answer)) {
							unEquip(_selectedItem);
							_itemListMenu.end();
						} else if (ITEM_USE_DEVIDE.equals(answer)) {
							// アイテムを分割 START
							MenuFactory.getNumberImputMenu().show(
									_selectedItem.getStackedNum(),
									new INumberInputCallBackInterface() {

										public void numberInputed(
												int inputNumber,
												INumberInputInterface inputMenu)
												throws Exception {
											// 数値が決定されたので分ける
											if (inputNumber > _selectedItem
													.getStackedNum()) {
												DebugUtil.debugLog("Chara",
														"その数に分けることはできない");
												// getGame().selfMessage(
												// "その数に分けることはできない");
											} else {
												if (inputNumber != 0
														&& inputNumber != _selectedItem
																.getStackedNum()) {

													IItemClbInterface newItem = _selectedItem
															.devideItem(inputNumber);
													_itemHolder
															.addItem(newItem);
													DebugUtil
															.debugLog(
																	"Chara",
																	_selectedItem
																			.getNameWithCount()
																			+ "と"
																			+ newItem
																					.getNameWithCount()
																			+ "に分けた");
													// 行動終了
													inputMenu.end();
													_itemListMenu.end();
													// FIXME:
													// reduceMovePointsAll();
												} else {
													// キャンセル
													inputMenu.end();
												}
											}
										}
									});
							// アイテムを分割 END
						}
					}
				});
	}

	/**
	 * Equips the specified item. The item to equip must be on this character's
	 * item list.
	 */
	private void equipItem(IItemClbInterface item) throws Exception {
		DebugUtil.assertFalse(item.isEquipped());
		IMaptipClbInterface tip = getCurrentMapTip();
		DebugUtil.assertFalse((tip.hasItem() && tip.getItem() == item));
		switch (item.getEquipType()) {
		case IItemClbInterface.EQUIP_TYPE1_WEAPON:
			if (!havePart(PART_LEFT_HAND) && !havePart(PART_LEFT_HAND)) {
				break;
			}
			if (_equip_rightHand != null) {
				unEquip(_equip_rightHand);
			}
			_equip_rightHand = item;
			break;
		case IItemClbInterface.EQUIP_TYPE1_ARMOR:
			switch (item.getEquipTypeDetail()) {
			case IItemClbInterface.EQUIP_TYPE2_BODY:
				if (!havePart(PART_BODY)) {
					break;
				}
				if (_equip_body != null) {
					unEquip(_equip_body);
				}
				_equip_body = item;
				break;
			}
			break;
		case IItemClbInterface.EQUIP_TYPE1_SHOOTER:
			if (_equip_shooter != null) {
				unEquip(_equip_shooter);
			}
			if (!havePart(PART_LEFT_HAND) && !havePart(PART_LEFT_HAND)) {
				break;
			}
			_equip_shooter = item;
			// 対応する弾丸を自動で装備する
			// boolean chkBlt = false;
			search: for (int i = 0; i < getItems().size(); i++) {
				IItemClbInterface m = (IItemClbInterface) getItems().elementAt(
						i);
				if (_equip_shooter.isBulletMatch(m)) {
					if (!m.isEquipped()) {
						equipItem(m);
					}
					// chkBlt = true;
					break search;
				}
			}
			// if (!chkBlt) {
			// if (isPlayer()) {
			// getGame().selfMessage("弾がない");
			// }
			// }
			break;
		case IItemClbInterface.EQUIP_TYPE1_SHOT_MATERIAL:
			if (_equip_shot_material != null) {
				unEquip(_equip_shot_material);
			}
			if (!havePart(PART_LEFT_HAND) && !havePart(PART_LEFT_HAND)) {
				// if (isPlayer()) {
				// getGame().selfMessage("手がないので装備できない");
				// }
				break;
			}
			_equip_shot_material = item;
			// if (isPlayer()) {
			// getGame().selfMessage(item.getName() + "を装備した");
			// }
			break;
		default:
			break;
		}
		item.memorizeEquipped();
		// エンチャント再計算
		// _enchantEffect.initPassiveEnchant();
	}

	/**
	 * Returns whether this character has the specified body part.
	 */
	private boolean havePart(String part) {
		return _parts.indexOf(part) >= 0;
	}

	/**
	 * Takes off the specified item.
	 */
	private void unEquip(IItemClbInterface item) throws Exception {
		DebugUtil.assertFalse(item.isEquipped());
		IMaptipClbInterface tip = getCurrentMapTip();
		DebugUtil.assertFalse((tip.hasItem() && tip.getItem() == item));
		switch (item.getItemType()) {
		case IItemClbInterface.EQUIP_TYPE1_WEAPON:
			if (item == _equip_rightHand) {
				_equip_rightHand = null;
			} else {
				DebugUtil.assertTrue(_equip_leftHand == item);
				_equip_leftHand = null;
			}
			break;
		case IItemClbInterface.EQUIP_TYPE1_SHOOTER:
			DebugUtil.assertTrue(_equip_shooter != null);
			_equip_shooter = null;
			break;
		case IItemClbInterface.EQUIP_TYPE1_SHOT_MATERIAL:
			DebugUtil.assertTrue(_equip_shot_material != null);
			_equip_shot_material = null;
			break;
		case IItemClbInterface.EQUIP_TYPE1_ARMOR:
			switch (item.getEquipTypeDetail()) {
			case IItemClbInterface.EQUIP_TYPE2_BODY:
				DebugUtil.assertTrue(_equip_body != null);
				_equip_body = null;
				break;
			default:
				DebugUtil.assertFalse(true);// エラー
				break;
			}
			break;
		default:
			DebugUtil.assertFalse(true);// エラー
			break;
		}
		item.memorizeUnequipped();
		// エンチャント再計算
		// _enchantEffect.initPassiveEnchant();
	}

	private static final String ASHIMOTO_PICKUP = "拾う";
	private static final String ASHIMOTO_GO_DOWN_STAIR = "降りる";
	private static final String ASHIMOTO_GO_UP_STAIR = "上がる";

	/**
	 * 足元メニューの表示
	 * 
	 * @throws Exception
	 */
	public void showAshimotoMenu() throws Exception {
		List l = new List();
		IMaptipClbInterface tip = getCurrentMapTip();
		if (tip.hasItem()) {
			l.add(ASHIMOTO_PICKUP);
		}
		if (tip.hasMapObject() && tip.getMapObject().isDownStair()) {
			l.add(ASHIMOTO_GO_DOWN_STAIR);
		} else if (tip.hasMapObject() && tip.getMapObject().isUpStair()) {
			l.add(ASHIMOTO_GO_UP_STAIR);
		}
		if (l.size() == 0) {
			return;
		}
		MenuFactory.getSimpleSelectMenu().show(l,
				new ISimpleSelectMenuCallBackInterface() {
					public void selected(String answer) throws Exception {
						if (ASHIMOTO_PICKUP.equals(answer)) {
							pickItem();
						} else if (ASHIMOTO_GO_DOWN_STAIR.equals(answer)
								|| ASHIMOTO_GO_UP_STAIR.equals(answer)) {
							MapFactory.getMap().nextFloor();
						}
					}
				});
	}

	/**
	 * 足元のアイテムを拾う
	 * 
	 * @throws Exception
	 */
	public void pickItem() throws Exception {
		IMaptipClbInterface tip = getCurrentMapTip();
		getItems().add(tip.getItem());
		tip.removeItem();
	}

	private void execSkill(int skillId) throws Exception {
		switch (skillId) {
		case 0:
			SkillFactory.getInstance().explosion(this);
			break;
		case 2:
			SkillFactory.getInstance().brizzard(this);
			break;
		case 3:
			SkillFactory.getInstance().fireball(this);
			break;
		case 4:
			SkillFactory.getInstance().firebless(this);
			break;
		case 5:
			SkillFactory.getInstance().iceNeedle(this);
			break;
		default:
			SkillFactory.getInstance().fireball(this);
			break;
		}
		// EffectFactory.getInstance().execSkill(this, skillId);
	}
	
}
