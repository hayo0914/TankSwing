package src.jp.co.yh123.tank.ai;

import src.jp.co.yh123.tank.chara.Chara;
import src.jp.co.yh123.tank.chara.IAIInterface;
import src.jp.co.yh123.zlibrary.util.DebugUtil;

public class AiBasic implements IAIInterface {

	protected static final int PRIORITY_ROW = 1;
	protected static final int PRIORITY_MEDIUM = 2;
	protected static final int PRIORITY_HIGH = 3;

	class Value {

		public static final int SEARCH = 0;
		public static final int WAIT = 1;// 補助的Skill
		public static final int ATTACK_PHIZ = 2;// 近距離
		public static final int ATTACK_DIST = 3;// 遠距離
		public static final int ESCAPE = 4;
		public static final int APPROACH = 5;
		public static final int SKILL_ATTACK = 6;// 攻撃的Skill
		public static final int SKILL_ASSIST = 7;// 補助的Skill
		public static final int FOLLOW_LEADER = 8;
		public static final int TYPE_NUM = 9;

		private int[] values = null;

		private Value() {
			values = new int[TYPE_NUM];
		}

		private void init() {
			for (int i = 0; i < TYPE_NUM; i++) {
				values[i] = 0;
			}
		}

		void add(int type, int value) {
			values[type] += value;
		}

		int getMaxType(Chara c) {
			int maxType = 0;
			int maxVal = 0;
			for (int i = 0; i < TYPE_NUM; i++) {
				if (maxVal < values[i]) {
					maxVal = values[i];
					maxType = i;
				}
			}
			return maxType;
		}
	}

	private Value _value = null;

	private Chara _targetEnemy = null;

	private Chara _leader = null;

	private int _useSkillId = 0;

	public AiBasic() {
		_value = new Value();
	}

	public void evaluate(Chara c) throws Exception {
		_value.init();
		onEvaluate(c);
	}

	protected void onEvaluate(Chara c) throws Exception {
		// 近くに敵がいるか？(視認可能な範囲に限る)
		Chara enemy = AiUtil.getEnemyNearBy(c);
		_targetEnemy = enemy;
		if (c.isAbleToMove()) {
			if (enemy != null) {
				// スキル使用判定
				evalUseSkill(c);
				// 隣接しているか？
				if (AiUtil.isNext(c, enemy.getCellX(), enemy.getCellY())) {
					DebugUtil.aiLog(c, enemy, "隣接している");
					// 隣接している⇒攻撃、もしくは遠距離の攻撃、もしくはスキル使用
					_value.add(Value.ATTACK_PHIZ, PRIORITY_MEDIUM);
				} else {
					DebugUtil.aiLog(c, enemy, "隣接していない");
					// 隣接していない⇒追う FIXME:もしくは遠距離の攻撃、もしくはスキル使用
					_value.add(Value.APPROACH, PRIORITY_MEDIUM);
				}
			} else {
				_leader = c.getLeader();
				if (_leader != null) {
					if (AiUtil
							.isNext(c, _leader.getCellX(), _leader.getCellY())) {
						// 既に隣にいる場合、待つ（優先度低）
						_value.add(Value.WAIT, PRIORITY_ROW);
					} else {
						// leaderがいて、かつ隣同士でない場合。
						if (c.getDistance(_leader.getCellX(), _leader
								.getCellY()) >= 1) {
							// 視認範囲にLeaderがいればついていく
							_value.add(Value.FOLLOW_LEADER, PRIORITY_MEDIUM);
						} else {
							// いなければ、探索
							_value.add(Value.SEARCH, PRIORITY_MEDIUM);
						}
					}
				}
			}
		} else {
			// This character can't move for now.
			if (enemy != null) {
				c.botherSleeping(10);
			}
		}
	}

	private void evalUseSkill(Chara c) throws Exception {
		if (c.get_skill().size() == 0) {
			return;
		}
		if (getModel().randomizer.getRandomInt(0, 100) >= 50) {
			return;
		}
		Csv csv = Res.get().csvSpell;
		// スキルのうち、ランダムで1つを判定
		int skillId = c.get_skill()
				.getKeyInt(
						getModel().randomizer.getRandomInt(1, c.get_skill()
								.size() + 1) - 1);
		_useSkillId = skillId;
		boolean use = false;
		// 使用するか否かの判定を行う
		int distance = _targetEnemy.getDistance(c.getCellX(), c.getCellY());
		DebugUtil.aiLog(c, _targetEnemy, "スキル使用距離判定", String.valueOf(distance));
		// タイプ1
		switch (csv.getInt(skillId, 2)) {
		case 0:// 攻撃タイプのスキル
			// 射程の判定
			if (distance == -1 || distance > csv.getInt(skillId, 5)) {
				// 射程外
				DebugUtil.aiLog(c, _targetEnemy, "射程外", String.valueOf(csv
						.getInt(skillId, 5)));
				return;
			}
			// タイプ2による判定
			switch (csv.getInt(skillId, 4)) {
			case 0:// 直線型
				// 直線上にいるか
				if (AiUtil.isOnLine(c, _targetEnemy)) {
					DebugUtil.aiLog(c, _targetEnemy, "直線上");
					use = true;
				}
				break;
			default:
				DebugUtil.assertFalse(true);// error
				break;
			}
			break;
		default:
			DebugUtil.assertFalse(true);// error
			break;
		}
		if (use) {
			DebugUtil.aiLog(c, _targetEnemy, "スキル使用評価加算");
			_value.add(Value.SKILL_ATTACK, PRIORITY_MEDIUM * 2);
		}

	}

	protected Model getModel() {
		return ApplicationFacade.getModel();
	}

	protected Game getGame() {
		return ApplicationFacade.getGame();
	}

	protected ApplicationFacade getFacade() {
		return ApplicationFacade.getInstance();
	}

	public boolean onMove(Chara c) throws Exception {
		evaluate(c);
		boolean interceptLoop = false;
		if (c.isPlayer())
			System.out.println(_value.getMaxType(c));
		switch (_value.getMaxType(c)) {
		case Value.SEARCH:
			AiUtil.lookToAsLeftHandMethod(c);
			AiUtil.walk(c);
			c.reduceMovePoints();
			break;
		case Value.APPROACH:
			AiUtil.lookToApproach(c, _targetEnemy);
			AiUtil.walk(c);
			c.reduceMovePoints();
			break;
		case Value.ATTACK_PHIZ:
			interceptLoop = AiUtil.attack(c, _targetEnemy);
			c.reduceMovePointsAll();
			break;
		case Value.SKILL_ATTACK:
			interceptLoop = true;
			AiUtil.lookToTGDirection(c, _targetEnemy.getCurrentMaptip());
			getGame().effecter.execSkill(c, _useSkillId);
			c.reduceMovePointsAll();
			break;
		case Value.FOLLOW_LEADER:
			AiUtil.lookToApproach(c, _leader);
			AiUtil.walk(c);
			c.reduceMovePoints();
			break;
		case Value.WAIT:
			c.reduceMovePoints();
			break;
		default:
			break;
		}
		return interceptLoop;
	}

	// protected static int moveFrameMonster = 1;
	// protected static int[] cellMapX = { 1, 1, 0,// 0,45,90
	// -1, -1, -1,// 135,180,225
	// 0, 1 };// 270,315
	//
	// protected static int[] cellMapY = { 0, 1, 1, // 0,45,90
	// 1, 0, -1,// 135,180,225
	// -1, -1 };// 270,315
	// protected boolean hunt(Chara c) throws Exception {
	// Model model;
	// model = ApplicationFacade.getModel();
	//
	// // 敵が近くにいる？
	// List enemylist = model.listPlayer;
	// for (int i = 0; i < enemylist.size(); i++) {
	// Chara enemy = enemylist.elementAtChara(i);
	// if (Map.canSee(c, enemy.getCellX(), enemy.getCellY())) {
	// // 敵を視認できる場合攻撃
	// if (attack(c, enemy)) {
	// return true;
	// }
	// }
	// }
	// return false;
	// }

	public void onDamaged(Chara c) throws Exception {
		if (c.isSleeping()) {
			c.botherSleeping(1024);
		}
	}

	public void onDeath(Chara c) throws Exception {
	}

	public void onInit(Chara c) throws Exception {

		// Sets direction at random.
		c.setDirection((getModel().randomizer.getRandomInt(0, 359) / 45) * 45);

		if (c.isMonster() && getModel().randomizer.getRandomInt(0, 100) <= 30) {
			// sleeping
			c.sleep(false, getModel().randomizer.getRandomInt(50, 200));
		}

	}

	// /* 周囲のオフセットX 優先順 斜め */
	// private static int[] offsetXNaname = new int[] { 1, -1, 1, -1 };
	// /* 周囲のオフセットY 優先順 */
	// private static int[] offsetYNaname = new int[] { -1, -1, 1, 1 };
	// /* 周囲のオフセットX 優先順 上下右左 */
	// private static int[] offsetX = new int[] { 0, 0, 1, -1 };
	// /* 周囲のオフセットY 優先順 */
	// private static int[] offsetY = new int[] { -1, 1, 0, 0 };
	// /* 周囲のオフセットの配列数 */
	// private int OFFSET = 4;

	public void move(Chara chara) throws Exception {
		// TODO Auto-generated method stub

	}

}
