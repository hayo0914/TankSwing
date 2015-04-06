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
		public static final int WAIT = 1;// �⏕�ISkill
		public static final int ATTACK_PHIZ = 2;// �ߋ���
		public static final int ATTACK_DIST = 3;// ������
		public static final int ESCAPE = 4;
		public static final int APPROACH = 5;
		public static final int SKILL_ATTACK = 6;// �U���ISkill
		public static final int SKILL_ASSIST = 7;// �⏕�ISkill
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
		// �߂��ɓG�����邩�H(���F�\�Ȕ͈͂Ɍ���)
		Chara enemy = AiUtil.getEnemyNearBy(c);
		_targetEnemy = enemy;
		if (c.isAbleToMove()) {
			if (enemy != null) {
				// �X�L���g�p����
				evalUseSkill(c);
				// �אڂ��Ă��邩�H
				if (AiUtil.isNext(c, enemy.getCellX(), enemy.getCellY())) {
					DebugUtil.aiLog(c, enemy, "�אڂ��Ă���");
					// �אڂ��Ă���ˍU���A�������͉������̍U���A�������̓X�L���g�p
					_value.add(Value.ATTACK_PHIZ, PRIORITY_MEDIUM);
				} else {
					DebugUtil.aiLog(c, enemy, "�אڂ��Ă��Ȃ�");
					// �אڂ��Ă��Ȃ��˒ǂ� FIXME:�������͉������̍U���A�������̓X�L���g�p
					_value.add(Value.APPROACH, PRIORITY_MEDIUM);
				}
			} else {
				_leader = c.getLeader();
				if (_leader != null) {
					if (AiUtil
							.isNext(c, _leader.getCellX(), _leader.getCellY())) {
						// ���ɗׂɂ���ꍇ�A�҂i�D��x��j
						_value.add(Value.WAIT, PRIORITY_ROW);
					} else {
						// leader�����āA���ד��m�łȂ��ꍇ�B
						if (c.getDistance(_leader.getCellX(), _leader
								.getCellY()) >= 1) {
							// ���F�͈͂�Leader������΂��Ă���
							_value.add(Value.FOLLOW_LEADER, PRIORITY_MEDIUM);
						} else {
							// ���Ȃ���΁A�T��
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
		// �X�L���̂����A�����_����1�𔻒�
		int skillId = c.get_skill()
				.getKeyInt(
						getModel().randomizer.getRandomInt(1, c.get_skill()
								.size() + 1) - 1);
		_useSkillId = skillId;
		boolean use = false;
		// �g�p���邩�ۂ��̔�����s��
		int distance = _targetEnemy.getDistance(c.getCellX(), c.getCellY());
		DebugUtil.aiLog(c, _targetEnemy, "�X�L���g�p��������", String.valueOf(distance));
		// �^�C�v1
		switch (csv.getInt(skillId, 2)) {
		case 0:// �U���^�C�v�̃X�L��
			// �˒��̔���
			if (distance == -1 || distance > csv.getInt(skillId, 5)) {
				// �˒��O
				DebugUtil.aiLog(c, _targetEnemy, "�˒��O", String.valueOf(csv
						.getInt(skillId, 5)));
				return;
			}
			// �^�C�v2�ɂ�锻��
			switch (csv.getInt(skillId, 4)) {
			case 0:// �����^
				// ������ɂ��邩
				if (AiUtil.isOnLine(c, _targetEnemy)) {
					DebugUtil.aiLog(c, _targetEnemy, "������");
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
			DebugUtil.aiLog(c, _targetEnemy, "�X�L���g�p�]�����Z");
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
	// // �G���߂��ɂ���H
	// List enemylist = model.listPlayer;
	// for (int i = 0; i < enemylist.size(); i++) {
	// Chara enemy = enemylist.elementAtChara(i);
	// if (Map.canSee(c, enemy.getCellX(), enemy.getCellY())) {
	// // �G�����F�ł���ꍇ�U��
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

	// /* ���͂̃I�t�Z�b�gX �D�揇 �΂� */
	// private static int[] offsetXNaname = new int[] { 1, -1, 1, -1 };
	// /* ���͂̃I�t�Z�b�gY �D�揇 */
	// private static int[] offsetYNaname = new int[] { -1, -1, 1, 1 };
	// /* ���͂̃I�t�Z�b�gX �D�揇 �㉺�E�� */
	// private static int[] offsetX = new int[] { 0, 0, 1, -1 };
	// /* ���͂̃I�t�Z�b�gY �D�揇 */
	// private static int[] offsetY = new int[] { -1, 1, 0, 0 };
	// /* ���͂̃I�t�Z�b�g�̔z�� */
	// private int OFFSET = 4;

	public void move(Chara chara) throws Exception {
		// TODO Auto-generated method stub

	}

}
