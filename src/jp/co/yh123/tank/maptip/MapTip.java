package src.jp.co.yh123.tank.maptip;

import src.jp.co.yh123.tank.collabo.ICharaClbInterface;
import src.jp.co.yh123.tank.collabo.IEffectClbInterface;
import src.jp.co.yh123.tank.collabo.IItemClbInterface;
import src.jp.co.yh123.tank.collabo.IMapObjectClbInterface;
import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;
import src.jp.co.yh123.tank.map.ICharaInterface;
import src.jp.co.yh123.tank.map.IItemInterface;
import src.jp.co.yh123.tank.map.IMapObjectInterface;
import src.jp.co.yh123.tank.map.IMaptipInterface;
import src.jp.co.yh123.tank.map.Map;
import src.jp.co.yh123.tank.map.MapFactory;
import src.jp.co.yh123.tank.mapobject.MapObject;
import src.jp.co.yh123.tank.resource.Res;
import src.jp.co.yh123.zlibrary.anime.Animation;
import src.jp.co.yh123.zlibrary.anime.AnimeFactory;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.util.DebugUtil;

public class MapTip implements IMaptipInterface, IMaptipClbInterface {
	private int _cellX = 0;

	private int _cellY = 0;

	private double _x = 0;

	private double _y = 0;

	private Animation _anime = null;

	private IEffectClbInterface _effect = null;

	private boolean _isVisible = true;

	public Animation getAnime() {
		return _anime;
	}

	private ICharaClbInterface _reserve = null;

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

	public MapTip() {
		if (shadowAnime == null) {
			try {
				shadowAnime = AnimeFactory.getInstance().createAnime(-1, 104,
						-1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setMapTile(int mapTileId) throws Exception {
		// 設定
		this._mapTileId = mapTileId;
		int animeId = Res.get().csvMapTile.getInt(mapTileId, 5);
		if (_anime == null) {
			_anime = AnimeFactory.getInstance().createAnime(-1, animeId, -1);
		} else {
			_anime.setAnime(-1, animeId, -1);
		}
		_isWall = Res.get().csvMapTile.getInt(mapTileId, 2);
	}

	public void draw(GameGraphic g, int offsetX, int offsetY) throws Exception {
		// if (isVisible()) {
		_anime.setPosition(getX(), getY());
		_anime.draw(g, offsetX, offsetY);
		if (hasMapObject()) {
			getMapObject().draw(g, offsetX, offsetY);
		}
		if (hasItem()) {
			getItem().draw(g, offsetX, offsetY);
		}
	}

	public void setChara(ICharaClbInterface chara) throws Exception {
		DebugUtil.assertFalse(hasChara());
		this._chara = chara;
		chara.setCell(getCellX(), getCellY());
		chara.setPosition(Map.getCharaXofCell(chara, getCellX(), getCellY()),
				Map.getCharaYofCell(chara, getCellX(), getCellY()));
	}

	public IItemClbInterface getItem() throws Exception {
		return this._item;
	}

	public boolean hasItem() throws Exception {
		return this._item != null;
	}

	public boolean isWallOrBarrier() throws Exception {
		return (_isWall > 0 || (hasMapObject() && _mapObject.isBarrier()));
	}

	public boolean hasChara() {
		return _chara != null;
	}

	public boolean hasMapObject() {
		return getMapObject() != null;
	}

	public void setChara(ICharaInterface chara) throws Exception {
		setChara((ICharaClbInterface) chara);
	}

	public void setMapObject(IMapObjectInterface mo) throws Exception {
		setMapObject((MapObject) mo);
	}

	int getMapTileId() {
		return _mapTileId;
	}

	public void removeChara(ICharaInterface c) throws Exception {
		DebugUtil.assertEqual(this._chara, c);
		_chara = null;
		_reserve = null;
	}

	public IMapObjectClbInterface getMapObject() {
		return _mapObject;
	}

	public ICharaClbInterface getChara() throws Exception {
		return _chara;
	}

	void setMapObject(MapObject mapObject) throws Exception {
		mapObject.setCell(getCellX(), getCellY());
		mapObject.setPosition(Map.getXofCell(getCellX(), getCellY()), Map
				.getYofCell(getCellX(), getCellY()));
		_mapObject = mapObject;
	}

	// public boolean isAbleToSetItem() throws Exception {
	// if (isWallOrBarrier()) {
	// return false;
	// }
	// if (hasItem()) { // TODO:修正（スタックアイテム対応が必要）
	// return false;
	// }
	// return true;
	// }

	public void removeMapObject() throws Exception {
		this._mapObject = null;
	}

	public void setItem(IItemInterface item) throws Exception {
		DebugUtil.assertFalse(hasMapObject());
		DebugUtil.assertFalse(isWallOrBarrier());
		IItemClbInterface i = (IItemClbInterface) item;
		if (hasItem()) {
			if (getItem().isStackable(i)) {
				i.stackTo(getItem());
			} else {
				DebugUtil.error();
			}
		} else {
			item.setCell(getCellX(), getCellY());
			item.setPosition(Map.getItemXofCell32(getCellX(), getCellY()), Map
					.getItemYofCell32(getCellX(), getCellY()));
			_item = i;
		}
	}

	private ICharaClbInterface _chara = null;

	private IItemClbInterface _item = null;

	private IMapObjectClbInterface _mapObject = null;

	private int _mapTileId = -1;

	private int _isWall = 0;

	private static Animation shadowAnime = null;

	public boolean isReserved() throws Exception {
		return _reserve != null;
	}

	public void reserve(ICharaClbInterface chara) throws Exception {
		DebugUtil.assertIsNull(_reserve);
		_reserve = chara;

	}

	public void removeItem() throws Exception {
		DebugUtil.assertIsNotNull(_item);
		_item = null;
	}

	public boolean hasEffect() throws Exception {
		return _effect != null;
	}

	public IEffectClbInterface getEffect() throws Exception {
		return _effect;
	}

	public void setEffect(IEffectClbInterface effect) throws Exception {
		DebugUtil.assertIsNotNull(effect);
		_effect = effect;
	}

	public void removeEffect() throws Exception {
		DebugUtil.assertIsNotNull(_effect);
		_effect = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * src.jp.co.yh123.tank.collabo.IMaptipClbInterface#isVisibleFromPlayer()
	 * FIXME:
	 */
	public boolean isVisibleFromPlayer() throws Exception {
		return _isVisible;
	}

	public void damage(String attribute) throws Exception {
		// 端は変更不可
		if (getCellX() == 0 || getCellX() == MapFactory.getMap().width() - 1) {
			return;
		}
		if (getCellY() == 0 || getCellY() == MapFactory.getMap().height() - 1) {
			return;
		}
		// TODO:炎だったら草を土にしたりとか。
		if (_mapTileId != 3 && _mapTileId != 4) {
			setMapTile(6);
		}
	}

	public boolean isAbleToSetChara() throws Exception {
		if (isWallOrBarrier()) {
			return false;
		}
		if (hasChara()) {
			return false;
		}
		if (isReserved()) {
			return false;
		}
		return true;
	}
}
