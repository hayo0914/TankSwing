package src.jp.co.yh123.tank.effect;

import src.jp.co.yh123.tank.collabo.ICharaClbInterface;
import src.jp.co.yh123.tank.collabo.IEffectClbInterface;
import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;
import src.jp.co.yh123.tank.map.IActorInterface;
import src.jp.co.yh123.tank.map.MapFactory;
import src.jp.co.yh123.tank.sfx.IEffectRoundInitListener;
import src.jp.co.yh123.tank.sfx.IEffectUpdateListener;
import src.jp.co.yh123.zlibrary.anime.Animation;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;

public abstract class Effect implements IEffectClbInterface {

	private int _cellX = 0;

	private int _cellY = 0;

	private double _x = 0;

	public int getUpdateCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	private double _y = 0;

	private Animation _anime = null;

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

	public Animation getAnime() {
		return _anime;
	}

	public void setAnime(Animation anime) {
		_anime = anime;
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

	public static interface EffectCallBack {

		public void onUpdate(Effect e) throws Exception;

		public void onHit(Effect e, IMaptipClbInterface tip) throws Exception;

		public void onEnd(Effect e) throws Exception;

	}

	public Effect(EffectCallBack o) {
		setObserver(o);
	}

	private ICharaClbInterface owner = null;// å¥àˆé“
	private IActorInterface _position = null;// èâä˙à íu
	private int _level = 0;

	public int getLevel() {
		return _level;
	}

	public IActorInterface getSource() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setRoundInitListener(IEffectRoundInitListener listener) {
		// TODO Auto-generated method stub

	}

	public void setUpdateListener(IEffectUpdateListener listener) {
		// TODO Auto-generated method stub

	}

	public IMaptipClbInterface getCurrentPosition() throws Exception {
		return (IMaptipClbInterface) MapFactory.getMap().getByPoint(
				getX() + getWidth() / 2, getY() + getHeight() / 2);
	}

	public void setLevel(int level) {
		_level = level;
	}

	public IActorInterface getPosition() {
		return _position;
	}

	protected void setPosition(IActorInterface position) {
		this._position = position;
	}

	public ICharaClbInterface getOwner() {
		return owner;
	}

	public void setOwner(ICharaClbInterface owner) {
		this.owner = owner;
	}

	private EffectCallBack observer = null;

	private boolean isEnd = false;

	public void update() throws Exception {
		animeUpdate();
		getObserver().onUpdate(this);
	}

	protected abstract void animeUpdate() throws Exception;

	public abstract void draw(GameGraphic g, int offsetX, int offsetY)
			throws Exception;

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd() throws Exception {
		isEnd = true;
		getObserver().onEnd(this);
	}

	protected EffectCallBack getObserver() {
		return observer;
	}

	protected void setObserver(EffectCallBack o) {
		this.observer = o;
	}
}
