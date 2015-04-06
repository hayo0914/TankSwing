package src.jp.co.yh123.tank.map;

import src.jp.co.yh123.tank.logic.ILogicMapInterface;

public class MapFactory {

	private static Map map = null;

	public static Map getMap() {
		return map;
	}

	public static ILogicMapInterface createNewMap(
			IEntityFactoryInterface entityFactory) {
		map = new Map(entityFactory);
		return map;
	}

}
