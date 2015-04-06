package src.jp.co.yh123.tank.effectnew;

import src.jp.co.yh123.tank.collabo.ICharaClbInterface;
import src.jp.co.yh123.tank.collabo.IEffectClbInterface;
import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;
import src.jp.co.yh123.tank.game.GlobalGameValues;
import src.jp.co.yh123.tank.map.IActorInterface;
import src.jp.co.yh123.tank.map.MapFactory;
import src.jp.co.yh123.tank.sfx.IEffectRoundInitListener;
import src.jp.co.yh123.tank.sfx.IEffectUpdateListener;
import src.jp.co.yh123.zlibrary.anime.Animation;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.util.DebugUtil;

public abstract class Effect implements IEffectClbInterface {

	public abstract void draw(GameGraphic g, int offsetX, int offsetY)
			throws Exception;

	/*
	 * åˆäJMethod
	 */
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

	public IActorInterface getSource() {
		return _source;
	}

	public ICharaClbInterface getOwner() {
		return _owner;
	}

	public void setSource(IActorInterface source) {
		_source = source;
	}

	public void setOwner(ICharaClbInterface owner) {
		_owner = owner;
	}

	public void update() throws Exception {
		DebugUtil.assertFalse(isEnd());
		updateCount++;
		if (_roundInitListener != null
				&& _round != GlobalGameValues.get().getRoundCount()) {
			_round = GlobalGameValues.get().getRoundCount();
			_roundInitListener.onRoundInit(this);
		}
		if (_updateListener != null) {
			_updateListener.onUpdate(this);
		}
	}

	public int getUpdateCount() {
		return updateCount;
	}

	public boolean isEnd() {
		return _isEnd;
	}

	public void setEnd() throws Exception {
		_isEnd = true;
	}

	public IMaptipClbInterface getCurrentPosition() throws Exception {
		return (IMaptipClbInterface) MapFactory.getMap().getByPoint(
				getX() + getWidth() / 2, getY() + getHeight() / 2);
	}

	public void setUpdateListener(IEffectUpdateListener listener) {
		_updateListener = listener;
	}

	public void setRoundInitListener(IEffectRoundInitListener listener) {
		_roundInitListener = listener;
		_round = GlobalGameValues.get().getRoundCount();
	}

	// public void setWall() {
	// _isWall = true;
	// }
	//
	// public void setAttribute(String attribute) {
	// _attribute = attribute;
	// }
	//
	// private String _attribute = null;
	// private boolean _isWall = false;// TrueÇÃèÍçáëºÇÃEffectÇ∆ã£çáÇ∑ÇÈ
	private long _round = 0;
	private IEffectUpdateListener _updateListener = null;
	private IEffectRoundInitListener _roundInitListener = null;
	private int _cellX = 0;
	private int _cellY = 0;
	private double _x = 0;
	private double _y = 0;
	private Animation _anime = null;
	private boolean _isEnd = false;
	private int updateCount = 0;
	/** å¥àˆÇ∆Ç»Ç¡ÇΩCharaÇï€éùÇ∑ÇÈ */
	private ICharaClbInterface _owner = null;
	/** î≠ê∂ÇµÇΩèâä˙à íuÇï€éùÇ∑ÇÈ */
	private IActorInterface _source = null;
}
