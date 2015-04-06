package src.jp.co.yh123.tank.griddungeon;

import src.jp.co.yh123.tank.map.IDungeonGeneratorInterface;
import src.jp.co.yh123.tank.map.IMaptipInterface;
import src.jp.co.yh123.tank.map.Map;
import src.jp.co.yh123.tank.resource.Res;
import src.jp.co.yh123.zlibrary.util.Csv;
import src.jp.co.yh123.zlibrary.util.DebugUtil;
import src.jp.co.yh123.zlibrary.util.IDArray;
import src.jp.co.yh123.zlibrary.util.IntPosition;
import src.jp.co.yh123.zlibrary.util.List;
import src.jp.co.yh123.zlibrary.util.RandomValues;

public class GridDungeonGenerator implements IDungeonGeneratorInterface {

	public void generate(Map map, int mapId, int floor, boolean goUpStair,
			long randomSeed) throws Exception {
		DebugUtil.debugLog("GridDungeon", "生成開始");
		_map = map;
		_mapId = mapId;
		_floor = floor;
		_goUpStair = goUpStair;
		_rv = new RandomValues(randomSeed);

		// フロア属性読み込み
		Csv csv = Res.get().csvDungeon;
		_name = csv.getString(_mapId, 1);
		_tileFloor = csv.getInt(_mapId, 2);
		_tileWall = csv.getInt(_mapId, 3);
		_tileCorridor = csv.getInt(_mapId, 4);
		_objDoorId = csv.getInt(_mapId, 5);
		_objUpStairId = csv.getInt(_mapId, 6);
		_objDownStairId = csv.getInt(_mapId, 7);
		_doorRate = csv.getInt(_mapId, 8);
		_minWidth = csv.getInt(_mapId, 9);
		_maxWidth = csv.getInt(_mapId, 10);
		_minHeight = csv.getInt(_mapId, 11);
		_maxHeight = csv.getInt(_mapId, 12);
		_bigFloorRate = csv.getInt(_mapId, 13);
		_mob = new IDArray(csv.getString(_mapId, 14));
		_minMobLv = csv.getInt(_mapId, 15);
		_maxMobLv = csv.getInt(_mapId, 16);
		_dropRarity = csv.getInt(_mapId, 17);
		_gridParam._gridSize = csv.getInt(_mapId, 18);
		_gridParam._roomSizeMin = csv.getInt(_mapId, 19);
		_gridParam._maxJunction = csv.getInt(_mapId, 20);
		_gridParam._roomRate = csv.getDouble(_mapId, 21);
		_gridParam._gridShapeType = csv.getInt(_mapId, 22);
		_width = ((_rv.getRandomInt(_minWidth, _maxWidth) / _gridParam._gridSize) + 1)
				* _gridParam._gridSize;
		_height = ((_rv.getRandomInt(_minHeight, _maxHeight) / _gridParam._gridSize) + 1)
				* _gridParam._gridSize;
		// マップインスタンス初期化
		_map.initFloorAll(_width, _height, _tileWall);
		// グリッドインスタンス初期化
		_gridList = new GridList(this, _map, _gridParam._gridSize);

		// マップ生成
		createRooms();
		// spawnMonster();
		// spawnMonster();
		spawnMonsterGroup(10);
		spawnMonsterGroup(10);
		spawnMonsterGroup(10);
		spawnItem();
		// spawnTrap();
		// 念のため周囲を壁で囲む
		setWallLimit();
		// スタート部屋を設定する
		setEnterPosition();
		DebugUtil.debugLog("GridDungeon", "生成完了");

	}

	public String getMapName() {
		return _name;
	}

	public IntPosition getEnterPoint() {
		return new IntPosition(_enterCellX, _enterCellY);
	}

	// フロア属性
	int _mapId = 0;
	int _floor = 0;
	String _name = null;
	int _tileFloor = 0;
	int _tileWall = 0;
	int _tileCorridor = 0;
	int _objDoorId = 0;
	int _objUpStairId = 0;
	int _objDownStairId = 1;
	int _doorRate = 50;
	/** 全体の幅 */
	int _width = 50;
	/** 全体の高さ */
	int _height = 50;
	int _minWidth = 50;
	int _maxWidth = 50;
	int _minHeight = 50;
	int _maxHeight = 50;
	int _bigFloorRate = 5;
	IDArray _mob = null;
	int _minMobLv = 20;
	int _maxMobLv = 50;
	int _dropRarity = 50;
	boolean _goUpStair = true;
	long _seed = 0;
	/** 入口x位置 */
	int _enterCellX = 0;
	/** 入口y位置 */
	int _enterCellY = 0;
	RandomValues _rv = null;
	Map _map = null;

	// インスタンス
	private GridList _gridList;

	static class GridsParameter {
		/**
		 * 1つのグリッドのサイズをあらわす。<br>
		 * 6以上で、かつwidth,heightの両方の約数でなければならない。
		 */
		int _gridSize = 20;
		/** 最小部屋サイズをあらわす。 */
		int _roomSizeMin = 10;
		/** グリッドが部屋になる確率を0.1~1の範囲で表す。(マップにおける部屋の割合最大値) */
		double _roomRate = 0.9;
		/** 部屋同士をつなぐ通路の数をあらわす。大きいほど複雑な構造になる。 */
		int _maxJunction = 4;// 1なら一部屋、2なら一本道 。大きいほど複雑になりやすい
		/** グリッド形状 */
		int _gridShapeType = AbstractGrid.SHAPE_SQUARE;
	}

	private GridsParameter _gridParam = new GridsParameter();

	/**
	 * グリッドリスト
	 * 
	 * @author YOU
	 */
	private class GridList {
		int roomCount = 0;
		AbstractGrid[][] grids;
		GridDungeonGenerator _dungeonGrid = null;

		GridList(GridDungeonGenerator param, Map map, int gridSize) {
			_dungeonGrid = param;
			grids = new AbstractGrid[map.height() / gridSize][map.width()
					/ gridSize];
		}

		AbstractGrid get(int gridX, int gridY) {
			return grids[gridY][gridX];
		}

		/**
		 * いろいろな部屋を作成する
		 * 
		 * @param grid
		 */
		AbstractGrid create(int gridX, int gridY) throws Exception {
			roomCount++;
			int x = gridX * _gridParam._gridSize;
			int y = gridY * _gridParam._gridSize;
			switch (_gridParam._gridShapeType) {
			case AbstractGrid.SHAPE_SQUARE:
				grids[gridY][gridX] = new SquareGrid(x, y, x
						+ _gridParam._gridSize, y + _gridParam._gridSize,
						_dungeonGrid, _gridParam, _map);
				break;
			case AbstractGrid.SHAPE_RANDOM:
				grids[gridY][gridX] = new RandomGrid(x, y, x
						+ _gridParam._gridSize, y + _gridParam._gridSize,
						_dungeonGrid, _gridParam, _map);
				break;
			default:
				DebugUtil.assertFalse(true);
				break;
			}
			grids[gridY][gridX].create();
			if (roomCount == 1) {
				grids[gridY][gridX].createGoal();
			}
			return grids[gridY][gridX];
		}

		int gridWidth() {
			return grids[0].length;
		}

		int gridHeight() {
			return grids.length;
		}

		int gridNum() {
			return gridWidth() * gridWidth();
		}

		AbstractGrid getRandomGrid() throws Exception {
			// 壁グリッドを取得
			int x = _rv.getRandomInt(0, gridWidth());
			int y = _rv.getRandomInt(0, gridHeight());
			AbstractGrid grid = get(x, y);
			if (roomCount >= gridNum()) {
				return null;
			}

			// FIXME:無限ループが起きる可能性あり
			while (true) {
				x = _rv.getRandomInt(0, gridWidth());
				y = _rv.getRandomInt(0, gridHeight());
				grid = get(x, y);
				if (grid == null) {
					grid = create(x, y);
					break;
				}
			}
			return grid;
		}

		AbstractGrid getRandomGridIsRoom() throws Exception {
			// FIXME:無限ループが起きる可能性あり
			AbstractGrid grid;
			while (true) {
				int x = _rv.getRandomInt(0, gridWidth());
				int y = _rv.getRandomInt(0, gridHeight());
				grid = get(x, y);
				if (grid != null) {
					break;
				}
			}
			return grid;
		}

		AbstractGrid getVerticalNeighbor(AbstractGrid g) throws Exception {

			if (gridHeight() == 1) {
				return null;
			}

			int gridX = (g.getX() / _gridParam._gridSize);
			int gridY = (g.getY() / _gridParam._gridSize);
			int offX = 0;
			int offY = 0;
			switch (_rv.getRandomInt(0, 2)) {
			case 0:
				offX = 0;
				offY = 1;
				break;
			case 1:
				offX = 0;
				offY = -1;
				break;
			}

			if (offY + gridY >= gridHeight() || offY + gridY < 0) {
				offY *= -1;
			}
			AbstractGrid gridNeighbor = get(offX + gridX, offY + gridY);
			if (gridNeighbor == null) {
				gridNeighbor = create(offX + gridX, offY + gridY);
			}
			return gridNeighbor;

		}

		AbstractGrid getHorizontalNeighbor(AbstractGrid g) throws Exception {

			if (gridWidth() == 1) {
				return null;
			}

			int gridX = (g.getX() / _gridParam._gridSize);
			int gridY = (g.getY() / _gridParam._gridSize);

			int offX = 0;
			int offY = 0;
			switch (_rv.getRandomInt(0, 2)) {
			case 0:
				offX = 1;
				offY = 0;
				break;
			case 1:
				offX = -1;
				offY = 0;
				break;
			}

			if (offX + gridX >= gridWidth() || offX + gridX < 0) {
				offX *= -1;
			}
			AbstractGrid gridNeighbor = get(offX + gridX, offY + gridY);
			if (gridNeighbor == null) {
				gridNeighbor = create(offX + gridX, offY + gridY);
			}
			return gridNeighbor;

		}

		double getRoomRate() {
			return (double) roomCount / (double) gridNum();
		}

	}

	/**
	 * 部屋生成
	 * 
	 * @param gl
	 * @throws Exception
	 */
	private void createRooms() throws Exception {
		// 最初にゴール作成
		AbstractGrid grid = _gridList.getRandomGrid();
		List rooms = new List(30);
		rooms.add(grid);

		IntPosition pos = grid.getRandomPositionIsFloor();
		if (_goUpStair) {
			_map.setMapObject(_objUpStairId, pos.getX(), pos.getY());
		} else {
			_map.setMapObject(_objDownStairId, pos.getX(), pos.getY());
		}
		grid._isToBeStartGrid = false;// ゴールとスタートは同じグリッドにしない

		// 部屋の割合が閾値を超えるまで、部屋を作成
		while (_gridList.roomCount == 0
				|| _gridList.getRoomRate() < _gridParam._roomRate) {
			// ゴールに隣接するグリッドからランダムに部屋を作成
			boolean isH = false;
			// 事前チェック
			boolean chk = false;
			if (grid.connectNum >= _gridParam._maxJunction) {
				chk = true;
			}

			AbstractGrid neighbor = null;
			if (!chk) {
				// 隣を取得
				if (_rv.getRandomInt(0, 2) > 0) {
					// 縦の隣接を取得
					isH = false;
					neighbor = _gridList.getVerticalNeighbor(grid);
					if (neighbor == null
							|| (neighbor.connectNum >= _gridParam._maxJunction || !neighbor
									.isConnectable())) {
						// 横の隣接を取得
						isH = true;
						neighbor = _gridList.getHorizontalNeighbor(grid);
					}
				} else {
					// 横の隣接を取得
					isH = true;
					neighbor = _gridList.getHorizontalNeighbor(grid);
					if (neighbor == null
							|| (neighbor.connectNum >= _gridParam._maxJunction || !neighbor
									.isConnectable())) {
						// 縦の隣接を取得
						isH = false;
						neighbor = _gridList.getVerticalNeighbor(grid);
					}
				}
				// 隣をチェック
				if (neighbor == null) {
					chk = true;
				} else if (neighbor.connectNum >= _gridParam._maxJunction
						|| !neighbor.isConnectable()) {
					chk = true;
				}

			}
			// 処理
			if (chk) {
				// この隣は使用不可
				if (rooms.size() == 0) {
					// 1つもリストに残っていない場合は部屋の生成をやめる
					break;
				} else {
					// 隣接グリッドが取れない場合は、1つ前に戻って隣接グリッドを取り直す。
					grid = (AbstractGrid) rooms.getLast();
					rooms.removeLast();
				}
			} else {
				rooms.add(neighbor);
				AbstractGrid.connect(_map, this, grid, neighbor, isH);
			}

		}
	}

	private void setWallLimit() throws Exception {
		for (int i = 0; i < _map.width(); i++) {
			_map.setMapTile(_tileWall, i, 0);
		}
		for (int i = 0; i < _map.width(); i++) {
			_map.setMapTile(_tileWall, i, _map.height() - 1);
		}
		for (int i = 0; i < _map.height(); i++) {
			_map.setMapTile(_tileWall, 0, i);
		}
		for (int i = 0; i < _map.height(); i++) {
			_map.setMapTile(_tileWall, _map.width() - 1, i);
		}
	}

	/**
	 * プレイヤーのスタート部屋を決定
	 * 
	 * @throws Exception
	 */
	private void setEnterPosition() throws Exception {
		AbstractGrid g = _gridList.getRandomGridIsRoom();
		IntPosition pos = g.getRandomPositionIsFloor();
		while (_map.get(pos.getX(), pos.getY()).hasMapObject()) {
			pos = g.getRandomPositionIsFloor();
		}
		if (_goUpStair) {
			_map.setMapObject(_objDownStairId, pos.getX(), pos.getY());
		} else {
			_map.setMapObject(_objUpStairId, pos.getX(), pos.getY());
		}
		_enterCellX = pos.getX();
		_enterCellY = pos.getY();
	}

	private void spawnItem() throws Exception {
		int safety = 100;
		int count = 0;
		int num = 25;
		String strItem = "0/1/2/2/2/3/4/5/6/7/8/9/10/11/12/13/14/15/16/17/18/";
		IDArray id = new IDArray(strItem, _rv);
		while (num > 0) {
			int x = _rv.getRandomInt(0, _map.width() - 1);
			int y = _rv.getRandomInt(0, _map.height() - 1);
			IMaptipInterface t = _map.get(x, y);
			if (!t.isWallOrBarrier() && !t.hasItem() && !t.hasMapObject()) {
				_map.createItem(id.getRandomID(), x, y);
				num--;
			} else {
				count++;
				if (count >= safety) {
					break;
				}
			}
		}
	}

	protected void spawnMonsterGroup(int maxMonsterNum) throws Exception {
		int safety = 100;
		int count = 0;
		int num = maxMonsterNum;
		String mon = "0/0/0/0/0/0/0/2/2/2/2/2/2/3/3/3/3/3/3/4/";
		IDArray monA = new IDArray(mon, _rv);
		List l = new List(100);
		_map.getRangeMaptip(l, _rv.getRandomInt(0, _map.width() - 1), _rv
				.getRandomInt(0, _map.height() - 1), 20);

		for (int i = 0; i < l.size(); i++) {
			if (num > 0) {
				IMaptipInterface tip = (IMaptipInterface) l.elementAt(i);
				if (tip.isAbleToSetChara() && _enterCellX != tip.getCellX()
						&& _enterCellY != tip.getCellY()) {
					if (num != maxMonsterNum) {
						_map.createMonster(monA.getRandomID(), tip.getCellX(),
								tip.getCellY(), _rv.getRandomInt(1, 12));
					} else {
						if (_rv.getRandomInt(1, 10) <= 2) {
							_map.createMonster(5, tip.getCellX(), tip
									.getCellY(), 5);// boss:versus

						}
					}
					num--;
				} else {
					count++;
					if (count >= safety) {
						break;
					}
				}
			} else {
				break;
			}
		}
	}

}
