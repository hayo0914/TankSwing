package src.jp.co.yh123.tank.map;

import src.jp.co.yh123.zlibrary.util.IntPosition;

public interface IDungeonGeneratorInterface {

	public void generate(Map map, int mapId, int floor, boolean goUpStair,
			long randomSeed) throws Exception;

	public String getMapName();

	public IntPosition getEnterPoint();

}
