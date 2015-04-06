package src.jp.co.yh123.tank.chara;

import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;
import src.jp.co.yh123.tank.map.Map;
import src.jp.co.yh123.tank.map.MapFactory;
import src.jp.co.yh123.zlibrary.util.DebugUtil;
import src.jp.co.yh123.zlibrary.util.GameMath;

public class ActionWalk implements ICharaAction {

	private int _toCellX = 0;

	private int _toCellY = 0;

	private int _moveFrame = 1;

	private double _tgX, _tgY;

	private int _execCount = 0;

	private double _spdX, _spdY;

	private boolean _isEnd = false;

	public ActionWalk(int moveFrame, int toCellX, int toCellY, Chara c)
			throws Exception {
		_moveFrame = moveFrame;
		_toCellX = toCellX;
		_toCellY = toCellY;
		setVxyToCellxy(c);
		IMaptipClbInterface tip = (IMaptipClbInterface) MapFactory.getMap()
				.get(c.getCellX(), c.getCellY());
		tip.reserve(c);
	}

	public void doAction(Chara c) throws Exception {
		_execCount++;

		if (_moveFrame > 1) {
			// �ړ�
			c.setPosition(c.getX() + _spdX, c.getY() + _spdY);

			// // �ړ�������������
			// if (_spdX > 0 && c.getX() > _tgX) {
			// // �s���߂�
			// c.setPosition(_tgX, c.getY());
			// } else if (_spdX < 0 && c.getX() < _tgX) {
			// // �s���߂�
			// c.setPosition(_tgX, c.getY());
			// }
			// if (_spdY > 0 && c.getY() > _tgY) {
			// // �s���߂�
			// c.setPosition(c.getX(), _tgY);
			// } else if (_spdY < 0 && c.getY() < _tgY) {
			// // �s���߂�
			// c.setPosition(c.getX(), _tgY);
			// }
		}

		// ��������
		if (_execCount >= _moveFrame) {
			// ����
			MapFactory.getMap().get(c.getCellX(), c.getCellY()).removeChara(c);
			DebugUtil.assertTrue(c.isAlive());
			MapFactory.getMap().get(_toCellX, _toCellY).setChara(c);

			// ������m�F
			c.watchAround();

			// // 㩔���
			// MapTip tip = c.getCurrentMaptip();
			// if (tip.isMapObject() && tip.getMapObject().isTrap()) {
			// ApplicationFacade.getGame().trapProc(tip, tip.getMapObject());
			// }

			_isEnd = true;
			c.useMovePoint(Chara.NORMAL_SPEED);
			c.changeAction(new ActionWait());
		}
	}

	public boolean isDoingAction(Chara c) {
		return !_isEnd;
	}

	/**
	 * ���x�ݒ� ���ړI�Ɍ������Ēe���΂� <br>
	 * �ڕW�Ɍ������Ĕ�Ԓe�̊֐��i�j <br>
	 * { <br>
	 * �e�̏����� <br>
	 * ���ڕW�܂ł̋������o <br>
	 * ���E�ڕW�܂ł̋����w���i�ڕW�̍��W�w�j�[�i�e�̍��W�w�j <br>
	 * ���E�ڕW�܂ł̋����x���i�ڕW�̍��W�x�j�[�i�e�̍��W�x�j <br>
	 * �� �� <br>
	 * ���e�̔��ˊp�x�擾���ړ��ʐݒ� <br>
	 * �E�e�̔��ˊp�x��atan2(�ڕW�܂ł̋����x�A�ڕW�܂ł̋����w) <br>
	 * �E�e�̈ړ��ʂw��cos(�e�̔��ˊp�x)�~�e�̃X�s�[�h <br>
	 * �E�e�̈ړ��ʂx��sin(�e�̔��ˊp�x)�~�e�̃X�s�[�h <br>
	 * �� <br>
	 * �ړ��ʂ��v�Z���ړI�n�܂Ŕ�΂� <br>
	 * �E�e�̍��W�w�{���e�̈ړ��ʂw <br>
	 * �E�e�̍��W�x�{���e�̈ړ��ʂx <br>
	 * �� <br>
	 * �����邩��ʊO�ɏo���珉�߂��� <br>
	 * <br>
	 * 
	 * @param ent
	 * @return
	 */
	private void setVxyToCellxy(Chara c) throws Exception {

		if (_moveFrame > 1) {
			// // �ړ���
			_tgX = Map.getCharaXofCell(c, _toCellX, _toCellY);
			_tgY = Map.getCharaYofCell(c, _toCellX, _toCellY);

			// �ړ��p�x
			double deg = GameMath.atan2(_tgX - c.getX(), _tgY - c.getY());
			// double deg = c.getDirectionToNext(_toCellX, _toCellY);

			// �t���[��������ړ����x�v�Z
			this._spdX = GameMath.abs(_tgX - c.getX()) / (_moveFrame - 1);
			this._spdY = GameMath.abs(_tgY - c.getY()) / (_moveFrame - 1);
			// �ړ����x
			_spdX = (double) (GameMath.cos((int) (deg + 0.5)) * _spdX);
			_spdY = (double) (GameMath.sin((int) (deg + 0.5)) * _spdY);
			// ApplicationFacade.getModel().map.dump();
		}

	}
}
