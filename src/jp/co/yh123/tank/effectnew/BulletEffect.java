package src.jp.co.yh123.tank.effectnew;

import src.jp.co.yh123.tank.collabo.CollisionChecker;
import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;
import src.jp.co.yh123.tank.map.Map;
import src.jp.co.yh123.tank.map.MapFactory;
import src.jp.co.yh123.tank.sfx.IBulletEffect;
import src.jp.co.yh123.tank.sfx.IBulletHitListener;
import src.jp.co.yh123.zlibrary.anime.AnimeFactory;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.util.GameMath;

public class BulletEffect extends Effect implements IBulletEffect {

	BulletEffect() {

	}

	public int getDistance() {
		return _distance;
	}

	public void setEffectHitListener(IBulletHitListener l) throws Exception {
		_hitListener = l;
	}

	public void init(IMaptipClbInterface source, int frameChangeInterval,
			int animeId, int maxLoopCount, int degree, double spd)
			throws Exception {
		_distance = 1;
		setAnime(AnimeFactory.getInstance().createAnime(frameChangeInterval,
				animeId, maxLoopCount));
		_spd = spd;
		_degree = degree;
		setSource(source);
		setCell(source.getCellX(), source.getCellY());
		setPosition(Map.getCenterXOfIMaptipInterface(source.getCellX(), source
				.getCellY())
				- getAnime().getWidth() / 2, Map.getCenterYOfIMaptipInterface(
				source.getCellX(), source.getCellY())
				- getAnime().getHeight() / 2);
		_spdX = (double) (GameMath.cos((int) (degree + 0.5)) * _spd);
		_spdY = (double) (GameMath.sin((int) (degree + 0.5)) * _spd);
		setPosition(getX() + _spdX, getY() + _spdY);
	}

	public void setDirection(int degree) {
		this._degree = degree;
		_spdX = (double) (GameMath.cos((int) (degree + 0.5)) * _spd);
		_spdY = (double) (GameMath.sin((int) (degree + 0.5)) * _spd);
	}

	public void update() throws Exception {
		super.update();
		// 移動
		setPosition(getX() + _spdX, getY() + _spdY);
		getAnime().update();
		if (getAnime().isEnd()) {
			setEnd();
		}
		// 命中判定
		Map map = MapFactory.getMap();
		double centerX = getX() + (getWidth() / 2);
		double centerY = getY() + (getHeight() / 2);
		IMaptipClbInterface tip = (IMaptipClbInterface) map.getByPoint(centerX,
				centerY);
		if (tip == null) {
			setEnd();
			return;
		}
		if (tip == _lastHitMapTip) {
			return;
		}
		setCell(tip.getCellX(), tip.getCellY());
		// あたり判定チェック
		if (!CollisionChecker.isHit(this, tip)) {
			return;// 当たってない
		}
		_hitListener.onCollision(this, tip);
		_distance++;// 距離加算
		_lastHitMapTip = tip;
	}

	public void bound() {
		_spdX *= -1;
		_spdY *= -1;
	}

	public void changeSpd(double newSpeed) {
		_spd = newSpeed;
		_spdX = (double) (GameMath.cos((int) (_degree + 0.5)) * _spd);
		_spdY = (double) (GameMath.sin((int) (_degree + 0.5)) * _spd);
	}

	public double getSpd() {
		return _spd;
	}

	public int getDegree() {
		return _degree;
	}

	public void draw(GameGraphic g, int offsetX, int offsetY) throws Exception {
		if (isEnd()) {
			return;
		}
		getAnime().setPosition(getX(), getY());
		getAnime().draw(g, offsetX, offsetY);
	}

	private IBulletHitListener _hitListener = null;
	private int _degree = 0; // 進行角度
	private int _distance = 1; // セル単位での距離
	private double _spd = 0;
	private double _spdX = 0;
	private double _spdY = 0;
	private IMaptipClbInterface _lastHitMapTip = null;
}
