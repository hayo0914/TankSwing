package src.jp.co.yh123.distanceHolder;

import src.jp.co.yh123.tank.chara.IDistanceHolder;
import src.jp.co.yh123.tank.map.IMaptipInterface;
import src.jp.co.yh123.tank.map.Map;
import src.jp.co.yh123.tank.map.MapFactory;

public class DistanceMap implements IDistanceHolder {

	private static class Score {
		int seed = -1;
		short score = -1;
	}

	private final int MAX_SCOPE = 15;
	private int _offsetX;
	private int _offsetY;
	private Score[][] _distanceMap = null;
	private int _seed;

	public DistanceMap() {
		int max = (MAX_SCOPE * 2) + 1;
		int size = (MAX_SCOPE * 2) + 1;
		_distanceMap = new Score[size][size];
		for (int i = 0; i < max; i++) {
			for (int j = 0; j < max; j++) {
				_distanceMap[i][j] = new Score();
			}
		}
	}

	/**
	 * Recalculates the distance score.
	 */
	public void recalcDistance(int notifyDistance, int cellX, int cellY)
			throws Exception {
		// 初期化
		_seed++;
		// 範囲
		_offsetX = cellX - MAX_SCOPE;
		_offsetY = cellY - MAX_SCOPE;
		setDistance(MapFactory.getMap(), notifyDistance, cellX, cellY, cellX,
				cellY, -1, -1);
	}

	/**
	 * Returns the score of the specified cell. The score would be smaller as is
	 * near to the character. If its too far, returns -1. And if its equal to
	 * the character's position, returns 0.
	 */
	public int getDistanceScore(int cellX, int cellY) {

		// 対象チップとのオフセット計算
		int wx = cellX - _offsetX;
		int wy = cellY - _offsetY;

		int max = _distanceMap.length;
		if (wx >= max || wx < 0) {
			// 範囲外
			return -1;
		}
		if (wy >= max || wy < 0) {
			// 範囲外
			return -1;
		}
		Score s = _distanceMap[wx][wy];
		if (_seed != s.seed) {
			// 範囲外
			return -1;
		}
		return s.score;

	}

	/**
	 * Sets the the score to the specified cell.
	 */
	private void setDistanceScore(int score, int cellX, int cellY) {

		// 対象チップとのオフセット計算
		int wx = cellX - _offsetX;
		int wy = cellY - _offsetY;
		_distanceMap[wx][wy].score = (short) (MAX_SCOPE - score - 1);// 目標に近いほど小さい値にするため
		_distanceMap[wx][wy].seed = _seed;

	}

	/**
	 * Calculates the distance and sets the score recursively.
	 * 
	 * @param scope
	 *            Current Score.
	 * @param cellX
	 *            Next cell.
	 * @param cellY
	 *            Next cell.
	 * @param cellXBef1
	 *            Current cell.
	 * @param cellYBef1
	 *            Current cell.
	 * @param cellXBef2
	 *            Previous cell.
	 * @param cellYBef2
	 *            Previous cell.
	 */
	private void setDistance(Map map, int scope, int cellX, int cellY,
			int cellXBef1, int cellYBef1, int cellXBef2, int cellYBef2)
			throws Exception {
		// 既に計算済みでないかチェックする
		if (cellX == cellXBef2 && cellY == cellYBef2) {
			// 高速化
			return;
		}
		if (map.width() == cellX || cellX < 0) {
			return;
		}
		if (map.height() == cellY || cellY < 0) {
			return;
		}
		scope--;
		// 移動コストなし
		if (scope <= 0) {
			return;
		}
		int chk = getDistanceScore(cellX, cellY);
		if (chk != -1 && chk <= MAX_SCOPE - scope) {
			// 既により小さいスコアが設定済み
			return;
		}
		IMaptipInterface tip = map.get(cellX, cellY);
		// 今回が壁ならその先は不要
		if (tip.isWallOrBarrier()) {
			return;
		} else {
			// スコアセット
			setDistanceScore(scope, cellX, cellY);
		}

		// 上
		setDistance(map, scope, cellX, cellY - 1, cellX, cellY, cellXBef1,
				cellYBef1);
		// 下
		setDistance(map, scope, cellX, cellY + 1, cellX, cellY, cellXBef1,
				cellYBef1);
		// 左
		setDistance(map, scope, cellX - 1, cellY, cellX, cellY, cellXBef1,
				cellYBef1);
		// 右
		setDistance(map, scope, cellX + 1, cellY, cellX, cellY, cellXBef1,
				cellYBef1);

	}
}
