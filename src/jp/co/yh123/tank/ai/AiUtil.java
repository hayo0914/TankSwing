package src.jp.co.yh123.tank.ai;

import src.jp.co.yh123.tank.chara.Chara;
import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;
import src.jp.co.yh123.tank.map.Map;
import src.jp.co.yh123.zlibrary.util.GameMath;
import src.jp.co.yh123.zlibrary.util.List;

public class AiUtil {

	protected static int moveFrameMonster = 0;

	protected static int[] cellMapX = { 1, 1, 0,// 0,45,90
			-1, -1, -1,// 135,180,225
			0, 1 };// 270,315

	protected static int[] cellMapY = { 0, 1, 1, // 0,45,90
			1, 0, -1,// 135,180,225
			-1, -1 };// 270,315

	/**
	 * ���F�\�Ȓ��ŁA�ł��߂��ɂ���G���擾����
	 * 
	 * @param c
	 * @return
	 * @throws Exception
	 */
	static Chara getEnemyNearBy(Chara c) throws Exception {
		List enemyList = c.getEnemyList();
		return getNearBy(c, enemyList);
	}

	/**
	 * �Ώۂƒ�����ɂ��邩�𔻒肷��
	 * 
	 * @param c
	 * @return
	 * @throws Exception
	 */
	static boolean isOnLine(Chara c, Chara tg) throws Exception {

		// ����

		int diffX = tg.getCellX() - c.getCellX();
		int diffY = tg.getCellY() - c.getCellY();
		if (diffX == 0 || diffY == 0) {
			return true;
		}

		if (GameMath.abs(diffX) - GameMath.abs(diffY) == 0) {
			return true;
		}

		return false;
	}

	/**
	 * �Ώۂ̃Z�����אڂ��Ă��邩�B�i1��̈ړ��œ��B�ł��邩�ۂ��j
	 * 
	 * @return
	 * @throws Exception
	 */
	static boolean isNext(Chara c, int cellX, int cellY) throws Exception {
		switch (c.getDistance(cellX, cellY)) {
		case 1:
			return true;
		case 2:
			if (c.canDoSomeThingDiagonally(c.getCellX(), c.getCellY(), cellX,
					cellY)) {
				return true;
			}
		default:
			return false;
		}
	}

	/**
	 * ���ڍU������(�ׂɂ���ꍇ�̂ݎ��s�\)
	 * 
	 * @param c
	 * @param tg
	 * @return Wait���K�v���ۂ�
	 * @throws Exception
	 */
	static boolean attack(Chara c, Chara tg) throws Exception {
		c.lookToTGCell(tg.getCurrentMapTip());
		// script
		model.scriptCurrentChara = c;
		model.scriptTgChara = tg;
		game.execScript(0, "attack", null);
		return true;
	}

	/**
	 * ����@�ɏ]���Č�����ύX����B
	 * 
	 * @param c
	 * @param tgMapTip
	 * @throws Exception
	 */
	static void lookToAsLeftHandMethod(Chara c) throws Exception {

		MapTip t;
		Model m = ApplicationFacade.getModel();
		int wk;
		int x = c.getCellX();
		int y = c.getCellY();
		Map map = m.map;

		// 5���̊m���ŉE��@�ɐ؂�ւ�
		if (m.randomizer.getRandomInt(0, 100) >= 50) {
			// �E��@
			// ������90���P�ʂɕύX
			c.setDirection((c.getDirection() / 90) * 90);

			// �E���`�F�b�N���A�E�ɕǂ�������΍���90������
			wk = Map.getDirection0to359(c.getDirection() + 90);
			t = map.getMapTipByDirection(wk, x, y);
			if (!(t.isWall() && !t.isDoor())) {
				c.setDirection(wk);
				return;
			}
			// �E�ɕǂ����鎞�A���ʂ��`�F�b�N���A�O�ɕǂ�������Β��i
			t = map.getMapTipByDirection(c.getDirection(), x, y);
			if (!(t.isWall() && !t.isDoor())) {
				return;
			}
			// �O�ɕǂ����鎞�A�����`�F�b�N���A���ɕǂ�������ΉE��90������
			wk = Map.getDirection0to359(c.getDirection() - 90);
			t = map.getMapTipByDirection(c.getDirection(), x, y);
			if (!(t.isWall() && !t.isDoor())) {
				c.setDirection(wk);
				return;
			}
			// ���E�O�E�E�Ƃ��ǂ̏ꍇ�A���]����B
			c.setDirection(Map.getDirection0to359(c.getDirection() + 180));
		} else {
			// ����@
			// ������90���P�ʂɕύX
			c.setDirection((c.getDirection() / 90) * 90);

			// �����`�F�b�N���A���ɕǂ�������΍���90������
			wk = Map.getDirection0to359(c.getDirection() - 90);
			t = map.getMapTipByDirection(wk, x, y);
			if (!(t.isWall() && !t.isDoor())) {
				c.setDirection(wk);
				return;
			}
			// ���ɕǂ����鎞�A���ʂ��`�F�b�N���A�O�ɕǂ�������Β��i
			t = map.getMapTipByDirection(c.getDirection(), x, y);
			if (!(t.isWall() && !t.isDoor())) {
				return;
			}
			// �O�ɕǂ����鎞�A�E���`�F�b�N���A�E�ɕǂ�������ΉE��90������
			wk = Map.getDirection0to359(c.getDirection() + 90);
			t = map.getMapTipByDirection(c.getDirection(), x, y);
			if (!(t.isWall() && !t.isDoor())) {
				c.setDirection(wk);
				return;
			}
			// ���E�O�E�E�Ƃ��ǂ̏ꍇ�A���]����B
			c.setDirection(Map.getDirection0to359(c.getDirection() + 180));
		}

	}

	/**
	 * �ׂɂ��邩�ǂ���
	 * 
	 * @return
	 * @throws Exception
	 */
	protected boolean isAdjacent(Chara c, Chara tg) throws Exception {
		switch (c.getDistance(tg.getCellX(), tg.getCellY())) {
		case 1:
			return true;
		case 2:
			if (Map.isAbleToMoveOblique(c.getCellX(), c.getCellY(), tg
					.getCellX(), tg.getCellY())) {
				return true;
			}
		default:
			return false;
		}
	}

	private static Chara getNearBy(Chara c, List charaList) throws Exception {

		int minScore = 999;
		Chara ret = null;

		// �ł��߂��ɂ���L������������
		for (int i = 0; i < charaList.size(); i++) {
			Chara enemy = charaList.elementAtChara(i);
			int score = enemy.getDistance(c.getCellX(), c.getCellY());
			if (score <= 0) {
				continue;
			}
			if (score < minScore) {
				minScore = score;
				ret = enemy;
			}
		}
		return ret;
	}

	/*-----------------------------------------------------
	 * direction
	 * �����B�Ō�ɐi�s���������Ɍ����Ă���B������g���̂́A�ړ����ɁA
	 * �����X�^�[�����̕����ɐi�ނ悤�ɂ��邽�߁B�ړ��̗D�揇�ʂ�
	 * �ȉ��̂Ƃ���B
	 * �Q�P�R
	 * �S���T
	 * �����̒l��45�����݂ŁA0�`315
	 *-----------------------------------------------------*/
	private static final int[] MOVE_DIRECTION_PRIORITY = { 0, 315, 45, 270, 90,
			225, 135, 180 };

	// �ڕW���Ȃ��ꍇ�̈ړ�
	// �D��x�̍��������ɐi��
	// �ړ��̗D�揇��
	// �Q�P�R
	// �S���T
	// �����̒l��45�����݂ŁA0�`315
	static boolean walk(Chara c) throws Exception {

		// �D�揇�ʂ̍������Ɉړ������s����
		for (int i = 0; i < MOVE_DIRECTION_PRIORITY.length; i++) {
			if (moveToRelativeDirection(c, MOVE_DIRECTION_PRIORITY[i])) {
				return true;
			}
		}
		return false;

	}

	static boolean moveToRelativeDirection(Chara c, int relativeDirection)
			throws Exception {
		// ���Ίp�x����A�Ώۂ̃Z���ւ̃I�t�Z�b�g���v�Z����
		// ���݂̌����p�x�Ƒ��Ίp�x�����Ίp�x�v�Z
		int wkDir = c.getDirection() + relativeDirection;
		if (wkDir >= 360) {
			wkDir -= 360;
		}
		// ��Ίp�x��45�Ŋ����āA�e�[�u������Z���̃I�t�Z�b�g���擾
		int num = wkDir / 45;// 0�`7�֕ϊ�
		int x = cellMapX[num];
		int y = cellMapY[num];
		if (Map.isAbleToMoveTo(c, c.getCellX() + x, c.getCellY() + y)) {
			c.setDirection(wkDir);
			// �ړ�
			ApplicationFacade.getGame().moveTo(moveFrameMonster, c,
					c.getCellX() + x, c.getCellY() + y);
			return true;
		}

		// �ړ��͂ł��Ȃ����A�����ł��邱�Ƃ��Ȃ����`�F�b�N
		Map map = ApplicationFacade.getModel().map;
		MapTip tip = map.get(c.getCellX() + x, c.getCellY() + y);
		if (c.doSomethingTo(tip)) {
			// �������s����
			// �����J����Ȃ�
			return true;
		}

		return false;
	}

	/* ���͂̃I�t�Z�b�gX �D�揇 �΂� */
	private final static int[] offsetXNaname = new int[] { 1, -1, 1, -1 };
	/* ���͂̃I�t�Z�b�gY �D�揇 */
	private final static int[] offsetYNaname = new int[] { -1, -1, 1, 1 };
	/* ���͂̃I�t�Z�b�gX �D�揇 �㉺�E�� */
	private final static int[] offsetX = new int[] { 0, 0, 1, -1 };
	/* ���͂̃I�t�Z�b�gY �D�揇 */
	private final static int[] offsetY = new int[] { -1, 1, 0, 0 };
	/* ���͂̃I�t�Z�b�g�̔z�� */
	private final static int OFFSET = 4;

	/**
	 * �ڕW�̃^�[�Q�b�g�ɍł��߂��i�H���Ƃ�����Ɍ���
	 * 
	 * @param c
	 * @param enemy
	 * @return
	 * @throws Exception
	 */
	static boolean lookToApproach(Chara c, Chara target) throws Exception {
		// �G����������ɕ����]��
		// �߂��ɓG�����邩�`�F�b�N
		int ret = target.getDistance(c.getCellX(), c.getCellY());
		if (ret == -1) {
			// ����
			return false;
		}

		// �X�R�A��������������Z��(=�ڕW�ɋ߂�)��T��

		int minX = 0;
		int minY = 0;
		int minScore = 99999;

		// �㉺���E
		for (int i = 0; i < OFFSET; i++) {
			int wkX = c.getCellX() + offsetX[i];
			int wkY = c.getCellY() + offsetY[i];
			int wkScore = target.getDistance(wkX, wkY);
			if (wkScore >= 1 && wkScore < minScore && ret - wkScore == 1) {
				minScore = wkScore;
				minX = wkX;
				minY = wkY;
				break;
			}
		}

		// �΂�(�����X�R�A�Ȃ�㉺���E�����΂߂�D�悷��B�΂߂̂ق����������悢���߁B)
		for (int i = 0; i < OFFSET; i++) {
			int wkX = c.getCellX() + offsetXNaname[i];
			int wkY = c.getCellY() + offsetYNaname[i];
			int wkScore = target.getDistance(wkX, wkY);
			if (wkScore >= 1 && wkScore < minScore && ret - wkScore == 2) {
				// 2����Ă��邪�A�΂߂Ȃ̂�1�ňړ��ł���
				if (Map.isAbleToMoveOblique(c.getCellX(), c.getCellY(), wkX,
						wkY)) {
					// �΂߈ړ��\�Ȉʒu
					minScore = wkScore;
					minX = wkX;
					minY = wkY;
					break;
				}
			}
		}

		if (minX == c.getCellX() && minY == c.getCellY()) {
			// �߂��Z���𔭌��ł��Ȃ�����
			return false;
		}

		// �߂��ق��̃Z���̕����Ɍ���
		lookToTGDirection(c, ApplicationFacade.getModel().map.get(minX, minY));

		return true;
	}

}
