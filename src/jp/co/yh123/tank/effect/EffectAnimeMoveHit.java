package src.jp.co.yh123.tank.effect;

import src.jp.co.yh123.tank.collabo.CollisionChecker;
import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;
import src.jp.co.yh123.tank.map.Map;
import src.jp.co.yh123.tank.map.MapFactory;
import src.jp.co.yh123.zlibrary.anime.Animation;
import src.jp.co.yh123.zlibrary.anime.AnimeFactory;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.util.GameMath;

public class EffectAnimeMoveHit extends Effect {

	public EffectAnimeMoveHit(EffectCallBack o) {
		super(o);
	}

	private double _spd = 0;

	private double _spdX = 0;

	/* �Z���P�ʂł̋��� */
	private int distance = 1;

	/**
	 * @return the distance
	 */
	protected int getDistance() {
		return distance;
	}

	protected double getSpdX() {
		return _spdX;
	}

	protected double getSpdY() {
		return _spdY;
	}

	private double _spdY = 0;

	private IMaptipClbInterface lastHitMapTip = null;

	private int _degree = 0;
	private IMaptipClbInterface _source = null;

	public IMaptipClbInterface getSource() {
		return _source;
	}

	protected int getDegree() {
		return _degree;
	}

	/**
	 * @param source
	 *            owner�Ɠ����Ƃ͌���Ȃ��Bsource��owner��set����Ȃ��B
	 * @param frameChangeInterval
	 * @param animeId
	 * @param maxLoopCount
	 * @param degree
	 * @param spd
	 * @throws Exception
	 */
	protected void set(IMaptipClbInterface source, int frameChangeInterval,
			int animeId, int maxLoopCount, int degree, double spd)
			throws Exception {
		distance = 1;
		Animation anime = getAnime();
		if (anime == null) {
			anime = AnimeFactory.getInstance().createAnime(frameChangeInterval,
					animeId, maxLoopCount);
		} else {
			anime.setAnime(frameChangeInterval, animeId, maxLoopCount);
		}
		setAnime(anime);
		_spd = spd;
		_degree = degree;
		changeSource(source);
		// this._source = source;
		// setCellX(source.getCellX());
		// setCellY(source.getCellY());
		// MapTip tip = map.get(source.getCellX(), source.getCellY());
		// lastHitMapTip = tip;
		// setX(Map.getCenterXOfMapTip(source.getCellX(), source.getCellY())
		// - anime.getWidth() / 2);
		// setY(Map.getCenterYOfMapTip(source.getCellX(), source.getCellY())
		// - anime.getHeight() / 2);
		_spdX = (double) (GameMath.cos((int) (degree + 0.5)) * _spd);
		_spdY = (double) (GameMath.sin((int) (degree + 0.5)) * _spd);
		setPosition(getX() + _spdX, getY() + _spdY);
	}

	public void changeSource(IMaptipClbInterface source) throws Exception {

		this._source = source;
		setCell(source.getCellX(), source.getCellY());
		// MapTip tip = map.get(source.getCellX(), source.getCellY());
		// lastHitMapTip = tip;
		setPosition(Map.getCenterXOfIMaptipInterface(source.getCellX(), source
				.getCellY())
				- getAnime().getWidth() / 2, Map.getCenterXOfIMaptipInterface(
				source.getCellX(), source.getCellY())
				- getAnime().getHeight() / 2);
	}

	public void changeDirection(int degree) {
		this._degree = degree;
		_spdX = (double) (GameMath.cos((int) (degree + 0.5)) * _spd);
		_spdY = (double) (GameMath.sin((int) (degree + 0.5)) * _spd);
	}

	protected void animeUpdate() throws Exception {
		if (isEnd()) {
			return;
		}
		getAnime().update();

		// �ړ�
		setPosition(getX() + _spdX, getY() + _spdY);

		// ��������
		Map map = MapFactory.getMap();
		double centerX = getX() + (getWidth() / 2);
		double centerY = getY() + (getHeight() / 2);
		IMaptipClbInterface tip = (IMaptipClbInterface) map.getByPoint(centerX,
				centerY);
		if (tip == null) {
			setEnd();
			return;
		}
		if (tip == lastHitMapTip) {
			return;
		}
		setCell(tip.getCellX(), tip.getCellY());
		// �����蔻��`�F�b�N
		if (!CollisionChecker.isHit(this, tip)) {
			return;// �������ĂȂ�
		}

		// // test
		// if (tip.getChara() != null && tip.getChara().isPlayer()) {
		// setOwner(tip.getChara());
		// bound();
		// } else {
		getObserver().onHit(this, tip);
		// }

		// �I��
		// �ǁi�h�A�j�ɏՓ�
		distance++;// �������Z
		lastHitMapTip = tip;
	}

	void bound() {
		_spdX *= -1;
		_spdY *= -1;
	}

	void changeSpd(double bairitsu) {
		_spdX = _spdX *= bairitsu;
		_spdY = _spdY *= bairitsu;
	}

	/**
	 * �Ō�ɏՓ˂����`�b�v�̕ǍۂɃG�t�F�N�g�̈ʒu�����킹��
	 * 
	 * @param tip
	 * @throws Exception
	 */
	void ajustWallLimit(IMaptipClbInterface tip) throws Exception {

		// // �����蔻��擾
		// HitBox h = tip.getAnime().getHitbox()[0];
		// // ����
		//
		// if (_spdX * _spdX >= _spdY * _spdY) {
		// // ���x���𒲐�
		// if (_spdX > 0) {
		// if (getX() + getWidth() / 2 >= tip.getX() + h.getX()) {
		// setX(tip.getX() + h.getX() - 1 - getWidth() / 2);
		// }
		// } else if (_spdX < 0) {
		// if (getX() + getWidth() / 2 <= tip.getX() + h.getX()
		// + h.getWidth()) {
		// setX(tip.getX() + h.getX() + h.getWidth() + 1 - getWidth()
		// / 2);
		// }
		// }
		// // �����蔻��`�F�b�N
		// if (tip.getAnime().chkTouchingPoint(
		// (int) (getX() + getWidth() / 2),
		// (int) (getY() + getHeight() / 2))) {
		// // �܂��������Ă���̂ŁAy��������
		// if (_spdY > 0) {
		// if (getY() + getHeight() / 2 >= tip.getY() + h.getY()) {
		// setY(tip.getY() + h.getY() - 1 - getHeight() / 2);
		// }
		// } else if (_spdY < 0) {
		// if (getY() + getHeight() / 2 <= tip.getY() + h.getY()
		// + h.getHeight()) {
		// setY(tip.getY() + h.getY() + h.getHeight() + 1
		// - getHeight() / 2);
		// }
		// }
		// }
		//
		// } else {
		// // ���y���𒲐�
		// if (_spdY > 0) {
		// if (getY() + getHeight() / 2 >= tip.getY() + h.getY()) {
		// setY(tip.getY() + h.getY() - 2 - getHeight() / 2);
		// }
		// } else if (_spdY < 0) {
		// if (getY() + getHeight() / 2 <= tip.getY() + h.getY()
		// + h.getHeight()) {
		// setY(tip.getY() + h.getY() + h.getHeight() + 1
		// - getHeight() / 2);
		// }
		// }
		// // �����蔻��`�F�b�N
		// if (tip.getAnime().chkTouchingPoint(
		// (int) (getX() + getWidth() / 2),
		// (int) (getY() + getHeight() / 2))) {
		// // �܂��������Ă���̂ŁAx��������
		// if (_spdX > 0) {
		// if (getX() + getWidth() / 2 >= tip.getX() + h.getX()) {
		// setX(tip.getX() + h.getX() - 1 - getWidth() / 2);
		// }
		// } else if (_spdX < 0) {
		// if (getX() + getWidth() / 2 <= tip.getX() + h.getX()
		// + h.getWidth()) {
		// setX(tip.getX() + h.getX() + h.getWidth() + 1
		// - getWidth() / 2);
		// }
		// }
		// }
		//
		// }

	}

	public void draw(GameGraphic g, int offsetX, int offsetY) throws Exception {
		if (isEnd()) {
			return;
		}
		getAnime().setPosition(getX(), getY());
		getAnime().draw(g, offsetX, offsetY);
		if (getAnime().isEnd()) {
			setEnd();
		}

	}

}
