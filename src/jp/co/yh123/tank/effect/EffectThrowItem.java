package src.jp.co.yh123.tank.effect;

import src.jp.co.yh123.tank.collabo.ICharaClbInterface;
import src.jp.co.yh123.tank.collabo.IItemClbInterface;
import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;
import src.jp.co.yh123.tank.map.IActorInterface;
import src.jp.co.yh123.tank.map.Map;
import src.jp.co.yh123.tank.map.MapFactory;
import src.jp.co.yh123.zlibrary.anime.Animation;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.util.GameMath;

public class EffectThrowItem extends Effect {

	public EffectThrowItem(EffectCallBack o) {
		super(o);
	}

	private IMaptipClbInterface _target = null;

	private double _targetX = 0;

	private double _targetY = 0;

	private double _spd = 0;

	private double _spdX = 0;

	private double _spdY = 0;

	private int _moveFrame = 0;

	private int _execCount = 0;

	private IItemClbInterface _item = null;

	private ICharaClbInterface _pitcher = null;

	private static final double SPEED = 10d;

	public void set(IActorInterface source, Animation anime,
			IItemClbInterface item, ICharaClbInterface pitcher,
			IMaptipClbInterface target) throws Exception {
		setAnime(anime);
		setCell(source.getCellX(), source.getCellY());
		_item = item;
		_pitcher = pitcher;
		_target = target;
		_targetX = _target.getX();
		_targetY = _target.getY();
		_spd = SPEED;
		_execCount = 0;

		int x = Map.getItemXofCell32(pitcher.getCellX(), pitcher.getCellY());
		int y = Map.getItemYofCell32(pitcher.getCellX(), pitcher.getCellY());
		setPosition(x, y);

		// �ړ��p�x
		double deg = GameMath.atan2(_targetX - x, _targetY - y);
		// �ړ����x
		_spdX = (double) (GameMath.cos((int) (deg + 0.5)) * _spd);
		_spdY = (double) (GameMath.sin((int) (deg + 0.5)) * _spd);
		// �ړ��t���[��
		int timeX = (int) (((_targetX - x) / _spdX) + 0.5d);
		int timeY = (int) (((_targetY - y) / _spdY) + 0.5d);
		_moveFrame = timeX >= timeY ? timeX : timeY;

		// �ꎞ���O//TODO:�C��
		// Map map = MapFactory.getMap();
		// if (item.isOnGround()) {
		// _item = map.get(item.getCellX(), item.getCellY()).removeItemByOne();
		// } else {
		// _item = pitcher.removeOneItem(item);
		// }

	}

	protected void animeUpdate() throws Exception {
		if (isEnd()) {
			return;
		}
		_execCount++;
		getAnime().update();

		// �ړ�
		setPosition(getX() + _spdX, getY() + _spdY);

		// �I������
		if (_execCount >= _moveFrame) {
			onHit();
			setEnd();
		}

	}

	private void onHit() throws Exception {
		boolean isGomi = false;

		// FIXME:
		// isGomi = _item.thrown(_pitcher, _target.getCellX(),
		// _target.getCellY());

		// �����挈��
		if (isGomi) {
			// Do Nothing
			// ���ɃS�~�ƂȂ��Ă��邽��
		} else {
			IMaptipClbInterface tip = (IMaptipClbInterface) MapFactory.getMap()
					.getCanSetItemCell(_target.getCellX(), _target.getCellY());
			if (tip != null && tip.getItem() == null) {
				// ��������̏���
				tip.setItem(_item);
			} else {
				// FIXME:
				// if (_item._itemId == tip.getItem()._itemId
				// && tip.getItem()._isStackable) {
				// tip.getItem().setStackNum(
				// tip.getItem()._stackNum + _item._stackNum);
				// } else {
				// // �u���ꏊ���Ȃ����ߑ�j
				// _item.destroyMessage();
				// }
			}
		}
	}

	public void draw(GameGraphic g, int offsetX, int offsetY) throws Exception {
		if (isEnd()) {
			return;
		}
		// IMaptipClbInterface tip = (IMaptipClbInterface) MapFactory.getMap()
		// .getByPoint(getX() + getWidth() / 2, getY() + getHeight() / 2);
		// if (tip != null) {
		// if (!tip.isVisible()) {
		// return;
		// } else if (tip.getVisibleUpdateTime() !=
		// getModel().visibleUpdateTime) {
		// // �������Ƃ͂��邪�A���ݎ��E�̊O
		// return;
		// }
		// }
		getAnime().setPosition(getX(), getY());
		getAnime().draw(g, offsetX, offsetY);

	}

	public IActorInterface getSource() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
