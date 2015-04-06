package src.jp.co.yh123.tank.mapobject;

import src.jp.co.yh123.tank.collabo.IMapObjectClbInterface;
import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;
import src.jp.co.yh123.tank.map.IMapObjectInterface;
import src.jp.co.yh123.tank.map.MapFactory;
import src.jp.co.yh123.tank.resource.Res;
import src.jp.co.yh123.zlibrary.anime.Animation;
import src.jp.co.yh123.zlibrary.anime.AnimeFactory;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.platform.HmFont;
import src.jp.co.yh123.zlibrary.util.DebugUtil;

public class MapObject implements IMapObjectInterface, IMapObjectClbInterface {

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

	boolean isTrap() {
		return _isTrap;
	}

	public void setMapObject(int mapOjbectId) throws Exception {

		_mapObjectId = mapOjbectId;

		int animeId = Res.get().csvMapObject.getInt(_mapObjectId, 3);

		// 初期設定
		if (_anime == null) {
			_anime = AnimeFactory.getInstance().createAnime(-1, animeId, -1);
		} else {
			_anime.setAnime(-1, animeId, -1);
		}
		// 名前
		_name = Res.get().csvMapObject.getString(_mapObjectId, 1);
		_nameOffset = HmFont.getStringWidth(HmFont.FONT_TINY, _name) / 2;
		// 通行可能フラグ
		_isPassble = Res.get().csvMapObject.getInt(_mapObjectId, 4) > 0;
		// 罠フラグ
		_isTrap = Res.get().csvMapObject.getInt(_mapObjectId, 2) > 0;
		// 強度
		_durability = Res.get().csvMapObject.getInt(_mapObjectId, 7);
		// 可視
		if (_isTrap) {
			_isVisible = false;
		} else {
			_isVisible = true;
		}
	}

	public void draw(GameGraphic g, int offsetX, int offsetY) throws Exception {
		if (!_isVisible) {
			return;
		}
		_anime.setPosition(getX(), getY());
		_anime.draw(g, offsetX, offsetY);
	}

	public void drawName(GameGraphic g, int offsetX, int offsetY) {
		if (!_isVisible) {
			return;
		}
		HmFont.setFont(g, HmFont.STYLE_PLAIN, HmFont.FONT_TINY);
		g.setColor(0xffffff);
	}

	public boolean isDoor() throws Exception {
		if (Res.get().csvMapObject.getInt(_mapObjectId, 5) > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isUpStair() throws Exception {
		return _mapObjectId == 6;
	}

	public boolean isDownStair() throws Exception {
		return _mapObjectId == 5;
	}

	public boolean isLocked() throws Exception {
		// FIXME:isLocked
		return false;
	}

	public boolean isDoorOpen() throws Exception {
		DebugUtil.assertTrue(isDoor());
		return _isPassble;
	}

	public void openDoor() throws Exception {
		DebugUtil.assertTrue(isDoor());
		DebugUtil.assertFalse(isDoorOpen());
		setMapObject(Res.get().csvMapObject.getInt(_mapObjectId, 6));
	}

	public void closeDoor() throws Exception {
		DebugUtil.assertTrue(isDoor());
		DebugUtil.assertTrue(isDoorOpen());
		setMapObject(Res.get().csvMapObject.getInt(_mapObjectId, 6));
	}

	public boolean isBarrier() throws Exception {
		return !_isPassble;
	}

	private int _mapObjectId = -1;
	int _durability = 1;
	boolean _isPassble = true;
	public boolean _isVisible = true;
	boolean _isTrap = false;
	String _name = null;
	int _nameOffset = 0;
	private int _cellX = 0;
	private int _cellY = 0;
	private double _x = 0;
	private double _y = 0;
	private Animation _anime = null;

	public void damage(String attribute) throws Exception {
		if (_durability > 0) {
			// 破壊可能
			_durability = 0;
			((IMaptipClbInterface) MapFactory.getMap().get(_cellX, _cellY))
					.removeMapObject();
		}
	}

}
