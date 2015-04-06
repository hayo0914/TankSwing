package src.jp.co.yh123.tank.griddungeon;

import src.jp.co.yh123.tank.griddungeon.GridDungeonGenerator.GridsParameter;
import src.jp.co.yh123.tank.map.Map;

class RandomGrid extends AbstractGrid {

	RandomGrid(int x, int y, int toX, int toY, GridDungeonGenerator param,
			GridsParameter gridParam, Map map) {
		super(x, y, toX, toY, param, gridParam, map);
	}

	void create() throws Exception {
		maskRandom(_param._tileFloor);
	}
}
