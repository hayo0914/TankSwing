package src.jp.co.yh123.tank.ai;

import src.jp.co.yh123.tank.chara.ActionWalk;
import src.jp.co.yh123.tank.chara.Chara;
import src.jp.co.yh123.tank.chara.IAIInterface;
import src.jp.co.yh123.tank.game.GameConstants;
import src.jp.co.yh123.zlibrary.platform.ActionEventAdapter;
import src.jp.co.yh123.zlibrary.platform.KeyState;

public class PlayerAI implements IAIInterface {

	public void move(Chara c) throws Exception {

		/*
		 * �v���C���[���A�N�V�������N�����������`�F�b�N����BmoveFlug�́A
		 * �v���C���[���U����ړ��Ȃǂ̃A�N�V�������s�����ꍇ��TRUE�ƂȂ�B
		 * ������TRUE�̂Ƃ��́A�v���C���[�̃A�N�V�����ɓK�؂ȃ^�C�v��ݒ肵�A
		 * �s���̃X�e�[�g�ɑJ�ڂ�����B�����Ƀ��[�g�̃X�e�[�g���v���C���[�s��(Move)�X�e�[�g�ɑJ�ڂ���
		 */

		// �ړ��̏���
		// �L�[�`�F�b�N
		int moveX = 0;
		int moveY = 0;
		boolean moveFlug = false;

		// �L�[��Ԏ擾
		KeyState key = ActionEventAdapter.getKeyState();

		if (key.chkKeyPushState(KeyState.KEY_AST)) {
			// �������j���[
			c.showAshimotoMenu();
			return;
		}

		if (GameConstants.IS_MOBILE) {
			// �e���L�[����`�F�b�N ���g�є�
			if (key.chkKeyPushState(KeyState.KEY_8)) {
				// ��
				moveY++;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_2)) {
				// ��
				moveY--;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_4)) {
				// ��
				moveX--;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_6)) {
				// �E
				moveX++;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_9)) {
				// �E��
				moveX++;
				moveY++;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_7)) {
				// ����
				moveX--;
				moveY++;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_1)) {
				// ����
				moveX--;
				moveY--;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_3)) {
				// �E��
				moveX++;
				moveY--;
				moveFlug = true;
			}
		} else {
			// �e���L�[����`�F�b�N
			if (key.chkKeyPushState(KeyState.KEY_2)) {
				// ��
				moveY++;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_8)) {
				// ��
				moveY--;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_4)) {
				// ��
				moveX--;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_6)) {
				// �E
				moveX++;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_3)) {
				// �E��
				moveX++;
				moveY++;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_1)) {
				// ����
				moveX--;
				moveY++;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_7)) {
				// ����
				moveX--;
				moveY--;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_9)) {
				// �E��
				moveX++;
				moveY--;
				moveFlug = true;
			}
		}
		if (key.chkKeyPushState(KeyState.KEY_ENTER)) {
			// ���j���[�\��
			c.showMainMenu();
			return;
		}
		// �����]���H
		// if (key.chkKeyPushState(KeyState.KEY_SFT1)) {
		// // ������I������
		// getGame().selfMessage("�ǂ̕����H");
		// // WF�J�n
		// getGame().showMediator(
		// new SceneSelectDirection(
		// ApplicationFacade.getModel().currentChara),
		// true);
		// return;
		// }
		// �����݁H
		// if (key.chkKeyPushState(KeyState.KEY_SFT2)) {
		// changeState(StatePlayerAshibumi.get());
		// return;
		// }
		// �ˌ�
		// if (key.chkKeyPushState(KeyState.KEY_SHP)) {
		// if (p.shoot1()) {
		// p.reduceMovePointsAll();
		// changeState(StatePlayerTurnEnd.get());
		// } else {
		// // script
		// getModel().scriptCurrentChara = p;
		// getGame().execScript(2, "wait", null);
		// }
		// return;
		// }

		// �}�X�ړ�?
		if ((!moveFlug) || (moveX == 0 && moveY == 0)) {
			return;
		}
		int toCellX = c.getCellX() + moveX;
		int toCellY = c.getCellY() + moveY;
		// ������ύX
		c.setDirection(c.getDirectionToNext(toCellX, toCellY));
		if (!c.canGoTo(toCellX, toCellY)) {
			// // �ړ���Ɉړ��ł��Ȃ��ꍇ
			// if (getModel().map.get(toCellX, toCellY).getChara() != null)
			// {
			// int direction = Map.getDirectionToNext(p, toCellX,
			// toCellY);
			// if (Map.isOblique(direction)
			// && !Map.isAbleToMoveOblique(p.getCellX(), p
			// .getCellY(), toCellX, toCellY)) {
			// // �ǂ�����
			// return;
			// }
			// // enemy������ꍇ
			// Chara e = getModel().map.get(toCellX,
			// toCellY).getChara();
			// if (e.isMonster()) {
			// // enemy�Ȃ�U��
			// p.reduceMovePointsAll();
			// // script
			// getModel().scriptCurrentChara = p;
			// getModel().scriptTgChara = e;
			// getGame().execScript(0, "attack", null);
			// changeState(StatePlayerTurnEnd.get());
			// return;
			// } else if (e.isGoodNPC()) {
			// // GoodNPC�Ȃ����ւ�
			// getGame().moveTo(Model.PLAYER_MOVE_FRAME, p,
			// e.getCellX(), e.getCellY());
			// getGame().moveTo(Model.PLAYER_MOVE_FRAME, e,
			// p.getCellX(), p.getCellY());
			// p.reduceMovePoints();
			// e.reduceMovePoints();
			// changeState(StatePlayerTurnEnd.get());
			// return;
			// } else {
			// return;
			// }
			// } else {
			// if ((tip.hasMapObject() && tip.getMapObject().isDoor())
			// && p.doSomethingTo(tip)) {
			// // �s���悪���ŁA���܂��Ă���ꍇ�̂݁A
			// // do something���\�b�h���Ăяo���ăh�A�������ŊJ����
			// // ����ȊO�́A�둀��������N�����Ă��܂����߁A���̃��\�b�h�͎��s���Ȃ�
			// p.reduceMovePointsAll();
			// changeState(StatePlayerTurnEnd.get());
			// return;
			// } else {
			// return;
			// }
			// }
		} else {
			// �ړ��ł���ꍇ
			c.reduceMovePoints();
			c.changeAction(new ActionWalk(3, toCellX, toCellY, c));
		}
	}
}
