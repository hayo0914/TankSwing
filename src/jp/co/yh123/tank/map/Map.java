package src.jp.co.yh123.tank.map;

import src.jp.co.yh123.tank.game.GameConstants;
import src.jp.co.yh123.tank.griddungeon.GridDungeonGenerator;
import src.jp.co.yh123.tank.logic.ILogicMapInterface;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.util.DebugUtil;
import src.jp.co.yh123.zlibrary.util.GameMath;
import src.jp.co.yh123.zlibrary.util.List;
import src.jp.co.yh123.zlibrary.util.ListComparator;
import src.jp.co.yh123.zlibrary.util.RandomValues;

public class Map implements ILogicMapInterface {

	private ICharaInterface _player = null;
	private IEntityFactoryInterface _entityFactory = null;
	private static final int MAPTILE_SIZE = 24;
	private List _charaList = null;
	private List _asyncEffectList = null;
	private List _syncEffectList = null;
	private List _drawTipWorkList = null;
	private RandomValues _randomValues = new RandomValues(System
			.currentTimeMillis());

	Map(IEntityFactoryInterface entityFactory) {
		this._entityFactory = entityFactory;
		_charaList = new List(40);
		_asyncEffectList = new List(40);
		_syncEffectList = new List(40);
		_drawTipWorkList = new List(100);
	}

	public IEntityFactoryInterface getEntityFactory() {
		return this._entityFactory;
	}

	/*
	 * 公開メソッド
	 */
	public void addAsyncEffect(IEffectInterface effect) {
		_asyncEffectList.add(effect);
	}

	public void addSyncEffect(IEffectInterface effect) {
		_syncEffectList.add(effect);
	}

	public void createItem(int itemId, int cellX, int cellY) throws Exception {
		IItemInterface item = _entityFactory.makeItem();
		item.setItem(itemId);
		get(cellX, cellY).setItem(item);
	}

	public ICharaInterface createMonster(int monsterId, int cellX, int cellY,
			int level) throws Exception {
		ICharaInterface c = _entityFactory.makeChara();
		c.setChara(monsterId, ICharaInterface.TYPE_MONSTER, level);
		get(cellX, cellY).setChara(c);
		return c;
	}

	/**
	 * e1から見たe2の角度を計算して返却する
	 * 
	 * @param e1
	 * @param e2
	 * @return
	 */
	public int getDirection(IMaptipInterface e1, IMaptipInterface e2) {
		int tgX = e2.getCellX();
		int tgY = e2.getCellY();
		double deg = GameMath.atan2(tgX - e1.getCellX(), tgY - e1.getCellY());
		int ret = (int) ((int) (deg + 0.5) / 45) * 45;
		DebugUtil.assertTrue(ret % 45 == 0);// XXX
		return ret;
	}

	public List getCharaAll() throws Exception {
		return _charaList;
	}

	public List getSyncEffectAll() throws Exception {
		return _syncEffectList;
	}

	public List getAsyncEffectAll() throws Exception {
		return _asyncEffectList;
	}

	public void initMap() throws Exception {
		DebugUtil.debugLog("Map", "初期化開始");
		nextFloor();// FIXME:
		DebugUtil.debugLog("Map", "初期化完了");
	}

	public IMaptipInterface get(int x, int y) throws Exception {
		return map[y][x];
	}

	public static double getXofCell(int cellX, int cellY) {
		return cellX * MAPTILE_SIZE;
	}

	public static double getYofCell(int cellX, int cellY) {
		return cellY * MAPTILE_SIZE;
	}

	public IMaptipInterface getByPoint(double x, double y) {
		int cellX = (int) (x + 0.5) / MAPTILE_SIZE;
		int cellY = (int) (y + 0.5) / MAPTILE_SIZE;
		if (cellY >= map.length || cellY < 0) {
			return null;
		} else if (cellX >= map[cellY].length || cellX < 0) {
			return null;
		}
		return map[cellY][cellX];
	}

	public int width() {
		return map[0].length;
	}

	public int height() {
		return map.length;
	}

	public void setMapObject(int mapOjbectId, int cellX, int cellY)
			throws Exception {
		IMapObjectInterface mo = _entityFactory.makeMapObject();
		mo.setMapObject(mapOjbectId);
		get(cellX, cellY).setMapObject(mo);
	}

	public void setMapTile(int mapTileId, int cellX, int cellY)
			throws Exception {
		get(cellX, cellY).setMapTile(mapTileId);
	}

	/**
	 * degree方向の正面のチップを取得する
	 * 
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public IMaptipInterface getMaptipByDirection(int degree,
			int cellX, int cellY) throws Exception {

		switch (degree) {
		case 0:
			return get(cellX + 1, cellY);
		case 45:
			return get(cellX + 1, cellY + 1);
		case 90:
			return get(cellX, cellY + 1);
		case 135:
			return get(cellX - 1, cellY + 1);
		case 180:
			return get(cellX - 1, cellY);
		case 225:
			return get(cellX - 1, cellY - 1);
		case 270:
			return get(cellX, cellY - 1);
		case 315:
			return get(cellX + 1, cellY - 1);
		default:
			DebugUtil.assertFalse(true);// error
			return null;
		}

	}

	// /**
	// * e1から見たe2の角度を計算して返却する
	// *
	// * @param e1
	// * @param e2
	// * @return
	// */
	// public static int getDirection(IMaptipInterface e1, IMaptipInterface e2)
	// {
	// int tgX = e2.getCellX();
	// int tgY = e2.getCellY();
	// double deg = YHMath.atan2(tgX - e1.getCellX(), tgY - e1.getCellY());
	// int ret = (int) ((int) (deg + 0.5) / 45) * 45;
	// DebugUtil.assertTrue(ret % 45 == 0);
	// return ret;
	// }
	//
	// public IMaptipInterface getRandomNeighborTip(IMaptipInterface tip)
	// throws Exception {
	// int dir = randomizer.getRandomInt(0, 8) * 45;
	// return getIMaptipInterfaceByDirection(dir, tip.getCellX(), tip
	// .getCellY());
	// }

	public void draw(GameGraphic g) throws Exception {
		// Offset設定
		if (_player != null) {
			setOffsetByChara(_player);
		}

		// Effectを描画順にソート
		_asyncEffectList.sort(new ListComparator() {
			public boolean compare(Object o1, Object o2) throws Exception {
				IEffectInterface e1 = (IEffectInterface) o1;
				IEffectInterface e2 = (IEffectInterface) o2;
				return e1.getCellY() > e2.getCellY();
			}
		});
		_syncEffectList.sort(new ListComparator() {
			public boolean compare(Object o1, Object o2) throws Exception {
				IEffectInterface e1 = (IEffectInterface) o1;
				IEffectInterface e2 = (IEffectInterface) o2;
				return e1.getCellY() > e2.getCellY();
			}
		});

		// マップ描画開始
		int fromX = 0;
		int fromY = 0;
		int toX = 0;
		int toY = 0;
		// 描画範囲確定
		// from
		if (drawMapOffsetX == 0) {
			fromX = 0;
		} else {
			fromX = (drawMapOffsetX / Map.MAPTILE_SIZE) - 1;
			if (fromX < 0) {
				fromX = 0;
			}
		}
		if (drawMapOffsetY == 0) {
			fromY = 0;
		} else {
			fromY = (drawMapOffsetY / Map.MAPTILE_SIZE) - 1;
			if (fromY < 0) {
				fromY = 0;
			}
		}

		// to
		toX = ((drawMapOffsetX + GameConstants.SIZE_DISPLAY_WIDTH) / Map.MAPTILE_SIZE) + 1;
		toY = ((drawMapOffsetY + GameConstants.SIZE_DISPLAY_HEIGHT) / Map.MAPTILE_SIZE) + 1;
		if (toX >= width()) {
			toX = width();
		}
		if (toY >= height()) {
			toY = height();
		}

		_drawTipWorkList.removeAllElements();
		int asyncEffectIndex = 0;
		int syncEffectIndex = 0;
		for (int i = fromY; i < toY; i++) {
			for (int j = fromX; j < toX; j++) {
				IMaptipInterface tip = get(j, i);
				tip.draw(g, drawMapOffsetX, drawMapOffsetY);
				_drawTipWorkList.add(tip);
			}
		}

		// Chara,Effect描画
		int cellYIndex = 0;
		for (int i = 0; i < _drawTipWorkList.size(); i++) {
			IMaptipInterface tip = (IMaptipInterface) _drawTipWorkList
					.elementAt(i);
			cellYIndex = tip.getCellY();
			drawEffect: while (true) {
				if (asyncEffectIndex >= _asyncEffectList.size()) {
					break drawEffect;
				}
				IEffectInterface e = (IEffectInterface) _asyncEffectList
						.elementAt(asyncEffectIndex);
				if (e.getCellY() >= cellYIndex) {
					break drawEffect;
				}
				e.draw(g, drawMapOffsetX, drawMapOffsetY);
				asyncEffectIndex++;
			}
			drawSyncEffect: while (true) {
				if (syncEffectIndex >= _syncEffectList.size()) {
					break drawSyncEffect;
				}
				IEffectInterface e = (IEffectInterface) _syncEffectList
						.elementAt(syncEffectIndex);
				if (e.getCellY() >= cellYIndex) {
					break drawSyncEffect;
				}
				e.draw(g, drawMapOffsetX, drawMapOffsetY);
				syncEffectIndex++;
			}
			if (tip.hasChara()) {
				tip.getChara().draw(g, drawMapOffsetX, drawMapOffsetY);
			}
		}
		// Effectの残りを描画
		for (int i = asyncEffectIndex; i < _asyncEffectList.size(); i++) {
			IEffectInterface c = (IEffectInterface) _asyncEffectList
					.elementAt(i);
			c.draw(g, drawMapOffsetX, drawMapOffsetY);
		}
		for (int i = syncEffectIndex; i < _syncEffectList.size(); i++) {
			IEffectInterface c = (IEffectInterface) _syncEffectList
					.elementAt(i);
			c.draw(g, drawMapOffsetX, drawMapOffsetY);
		}

	}

	public void initFloorAll(int width, int height, int initialType)
			throws Exception {
		map = new IMaptipInterface[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				map[i][j] = _entityFactory.makeMapTip();
				map[i][j].setPosition(getXofCell(j, i), getYofCell(j, i));
				map[i][j].setCell(j, i);
				map[i][j].setMapTile(initialType);
			}
		}
	}

	public static int getCharaXofCell(ICharaInterface c, int cellX, int cellY)
			throws Exception {
		return getIMaptipInterfaceXofCell(cellX, cellY) + 15 - c.getWidth() / 2;
	}

	public static int getCharaYofCell(ICharaInterface c, int cellX, int cellY)
			throws Exception {
		return getIMaptipInterfaceYofCell(cellX, cellY) + 24 - c.getHeight();
	}

	public static int getItemXofCell32(int cellX, int cellY) {
		return getIMaptipInterfaceXofCell(cellX, cellY);
	}

	public static int getItemYofCell32(int cellX, int cellY) {
		return getIMaptipInterfaceYofCell(cellX, cellY) - 4 - 4;
	}

	public static int getCenterXOfIMaptipInterface(int cellX, int cellY) {
		return getIMaptipInterfaceXofCell(cellX, cellY) + 16;
	}

	public static int getDirection0to359(int deg) {
		while (deg < 0) {
			deg += 360;
		}
		while (deg >= 360) {
			deg -= 360;
		}
		return deg;
	}

	public static int getCenterYOfIMaptipInterface(int cellX, int cellY) {
		return getIMaptipInterfaceYofCell(cellX, cellY) + 19;
	}

	/*
	 * 内部メソッド
	 */
	public void nextFloor() throws Exception {
		DebugUtil.debugLog("Map", "次フロア初期化開始");
		if (currentUpDownDirection) {
			currentLocalFloor++;
		} else {
			currentLocalFloor--;
		}
		IDungeonGeneratorInterface dungeon = new GridDungeonGenerator();
		dungeon.generate(this, randomizer.getRandomInt(0, 5),
				currentLocalFloor, currentUpDownDirection, System
						.currentTimeMillis());

		// データセット
		currentFloorName = dungeon.getMapName();
		currentFloorString = currentFloorMainName + " " + "第"
				+ currentLocalFloor + "階層　" + currentFloorName;
		DebugUtil.debugLog("Map", "次フロア初期化完了");

		// プレイヤーキャラをセット//TODO:ここではないはず・・・
		if (_player == null) {
			ICharaInterface chara = _entityFactory.makeChara();
			chara.setChara(3, ICharaInterface.TYPE_PLAYER, 20);
			get(dungeon.getEnterPoint().getX(), dungeon.getEnterPoint().getY())
					.setChara(chara);
			_player = chara;
			_charaList.add(_player);
		} else {
			get(dungeon.getEnterPoint().getX(), dungeon.getEnterPoint().getY())
					.setChara(_player);
		}

	}

	void setOffsetByChara(ICharaInterface chara) {
		drawMapOffsetX = (int) (chara.getX()
				- (GameConstants.SIZE_DISPLAY_WIDTH / 2) + 16);
		drawMapOffsetY = (int) (chara.getY()
				- (GameConstants.SIZE_DISPLAY_HEIGHT / 2) + 16);
	}

	/*
	 * 状態
	 */
	// オフセット
	int drawMapOffsetX = 0;
	int drawMapOffsetY = 0;
	int currentLocalFloor = 0;// 現在階層（ローカル）
	int currentMainFloor = 0;// 現在階層
	int mainFloorMax = 0;// 最高到達階層
	int currentDungeonId = 0;// 現在ダンジョンID
	String currentFloorMainName = "シティ";// 現在ダンジョン名
	String currentFloorName = "フロア名";// 現在フロア名
	boolean currentUpDownDirection = true;// 現在ダンジョンの向き(true:上り)
	String currentFloorString = "";
	RandomValues randomizer = new RandomValues(System.currentTimeMillis());

	int _gridSize = 0;
	int _posXStart = 0;
	int _posYStart = 0;
	/***************************************************************************
	 * 最新視界更新時間
	 **************************************************************************/
	long visibleUpdateTime = 0;
	/***************************************************************************
	 * インスタンス
	 **************************************************************************/
	private IMaptipInterface[][] map = null; // layer0

	private static int getIMaptipInterfaceXofCell(int cellX, int cellY) {
		return (24 * cellX);
	}

	static int getIMaptipInterfaceYofCell(int cellX, int cellY) {
		return (24 * cellY);
	}

	/**
	 * 指定のセルの周辺で、アイテムを置けるセルを取得<br>
	 * 6マス以上離れた場所しかなければNULL
	 * 
	 * @return
	 * @throws Exception
	 */
	public IMaptipInterface getCanSetItemCell(int cellX, int cellY)
			throws Exception {
		return searchCanSetItem(5, cellX, cellY, -1, -1);
	}

	private int[][] neighborCell = { { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 },
			{ -1, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 } };

	/**
	 * 指定のセルに隣り合うランダムのセルを取得<br>
	 * 
	 * @return
	 * @throws Exception
	 */
	public IMaptipInterface getRandomNeighborTip(IMaptipInterface tip)
			throws Exception {
		int wk = _randomValues.getRandomInt(0, neighborCell.length);
		int x = tip.getCellX() + neighborCell[wk][0];
		int y = tip.getCellY() + neighborCell[wk][1];
		if (x < 0) {
			x = 0;
		} else if (x >= width()) {
			x = width() - 1;
		}
		if (y < 0) {
			y = 0;
		} else if (y >= height()) {
			y = height() - 1;
		}
		return get(x, y);
	}

	private IMaptipInterface searchCanSetItem(int maxDist, int cellX,
			int cellY, int cellXBef, int cellYBef) throws Exception {
		if (maxDist <= 0) {
			return null;
		}
		if (cellX >= width() || cellX < 0) {
			// 範囲外
			return null;
		}
		if (cellY >= height() || cellY < 0) {
			// 範囲外
			return null;
		}
		if (cellX == cellXBef && cellY == cellYBef) {
			// 重複
			return null;
		}
		IMaptipInterface tip = get(cellX, cellY);
		maxDist--;
		if (!tip.hasItem() && !tip.hasMapObject()) {
			return tip;
		}

		// 周辺探索
		IMaptipInterface tip1;
		IMaptipInterface tip2;
		IMaptipInterface tip3;
		IMaptipInterface tip4;

		// 上
		tip1 = searchCanSetItem(maxDist, cellX, cellY - 1, cellX, cellY);
		// 下
		tip2 = searchCanSetItem(maxDist, cellX - 1, cellY, cellX, cellY);
		// 右
		tip3 = searchCanSetItem(maxDist, cellX + 1, cellY, cellX, cellY);
		// 左
		tip4 = searchCanSetItem(maxDist, cellX, cellY + 1, cellX, cellY);

		if (tip1 == null && tip2 == null && tip3 == null && tip4 == null) {
			return null;
		}

		// 上下左右のNULLでないチップのうち、ランダムのチップを返却
		if (tip1 != null) {
			if (tip2 != null) {
				if (randomizer.getRandomInt(0, 100) > 50) {
					return tip1;
				} else {
					return tip2;
				}
			} else if (tip3 != null) {
				if (randomizer.getRandomInt(0, 100) > 50) {
					return tip1;
				} else {
					return tip3;
				}
			} else if (tip4 != null) {
				if (randomizer.getRandomInt(0, 100) > 50) {
					return tip1;
				} else {
					return tip4;
				}
			} else {
				return tip1;
			}
		} else if (tip2 != null) {
			if (tip3 != null) {
				if (randomizer.getRandomInt(0, 100) > 50) {
					return tip2;
				} else {
					return tip3;
				}
			} else if (tip4 != null) {
				if (randomizer.getRandomInt(0, 100) > 50) {
					return tip2;
				} else {
					return tip4;
				}
			} else {
				return tip2;
			}
		} else if (tip3 != null) {
			if (tip4 != null) {
				if (randomizer.getRandomInt(0, 100) > 50) {
					return tip3;
				} else {
					return tip4;
				}
			} else {
				return tip3;
			}
		} else if (tip4 != null) {
			return tip4;
		}
		return null;
	}

	/**
	 * 指定範囲のセルを取得
	 * 
	 * @param centerX
	 * @param centerY
	 * @param range
	 * @throws Exception
	 */
	public void getRangeMaptip(List list, int centerX, int centerY, int range)
			throws Exception {
		// 右回りの渦巻き状に検索していく。
		list.removeAllElements();
		int hankei = 1;
		int maxHankei = range;
		list.add(get(centerX, centerY));
		while (hankei <= maxHankei) {
			if (hankei > range) {
				break;
			}
			int wkX = centerX - hankei;
			int wkY = centerY - hankei;
			if (wkX >= 0 && wkY >= 0) {
				lp: for (int i = wkX; i <= centerX + hankei - 1; i++) {
					if (i >= width() || i <= -1) {
						break lp;
					}
					list.add(get(i, wkY));
				}
			}
			wkX = centerX + hankei;
			wkY = centerY - hankei;
			if (wkX < width() && wkY >= 0) {
				lp: for (int i = wkY; i <= centerY + hankei - 1; i++) {
					if (i >= height() || i <= -1) {
						break lp;
					}
					list.add(get(wkX, i));
				}
			}
			wkX = centerX + hankei;
			wkY = centerY + hankei;
			if (wkX < width() && wkY < height()) {
				lp: for (int i = wkX; i >= centerX - hankei + 1; i--) {
					if (i < 0) {
						break lp;
					}
					list.add(get(i, wkY));
				}
			}
			wkX = centerX - hankei;
			wkY = centerY + hankei;
			if (wkX >= 0 && wkY < height()) {
				lp: for (int i = wkY; i >= centerY - hankei + 1; i--) {
					if (i < 0) {
						break lp;
					}
					list.add(get(wkX, i));
				}
			}

			hankei++;
		}

	}

	// /**
	// * 直線の突き当りを検索
	// *
	// * @return
	// * @throws Exception
	// */
	// IMaptipInterface getEnd(int sourceX, int sourceY, int degree)
	// throws Exception {
	// int moveX = 0;
	// int moveY = 0;
	// switch (degree) {
	// case 0:
	// moveX = 1;
	// moveY = 0;
	// break;
	// case 45:
	// moveX = 1;
	// moveY = 1;
	// break;
	// case 90:
	// moveX = 0;
	// moveY = 1;
	// break;
	// case 135:
	// moveX = -1;
	// moveY = 1;
	// break;
	// case 180:
	// moveX = -1;
	// moveY = 0;
	// break;
	// case 225:
	// moveX = -1;
	// moveY = -1;
	// break;
	// case 270:
	// moveX = 0;
	// moveY = -1;
	// break;
	// case 315:
	// moveX = 1;
	// moveY = -1;
	// break;
	// default:
	// DebugUtil.error("存在しない方向：WFTHROW_ITEM:" + String.valueOf(degree));
	// }
	//
	// int currentX = sourceX;
	// int currentY = sourceY;
	// int tgCellX = currentX;
	// int tgCellY = currentY;
	// search: while (true) {
	// IMaptipInterface tipCurrent = get(currentX, currentY);
	// if (width() <= currentX + moveX || height() <= currentY + moveY) {
	// // マップ外に出てしまうので、ここで終わる
	// tgCellX = currentX;
	// tgCellY = currentY;
	// break search;
	// } else {
	// IMaptipInterface tipNext = get(currentX + moveX, currentY
	// + moveY);
	// if (tipNext.isWall()) {
	// tgCellX = currentX;
	// tgCellY = currentY;
	// break search;
	// } else if (tipCurrent.hasChara()
	// && !(currentX == sourceX && currentY == sourceY)) {
	// tgCellX = currentX;
	// tgCellY = currentY;
	// break search;
	// }
	// // else if (!isAbleToMoveNaname(currentX, currentY, currentX
	// // + moveX, currentY + moveY)) {
	// // tgCellX = currentX;
	// // tgCellY = currentY;
	// // break search;
	// // }
	// }
	// currentX += moveX;
	// currentY += moveY;
	// }
	// return get(tgCellX, tgCellY);
	// }

}
