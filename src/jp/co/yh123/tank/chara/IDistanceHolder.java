package src.jp.co.yh123.tank.chara;

public interface IDistanceHolder {

	/**
	 * 他者から見たその中心点への距離
	 * 
	 * @param notifyDistance
	 *            最も遠くから気づかれる距離
	 * @param cellX
	 *            中心点X
	 * @param cellY
	 *            中心点Y
	 * @throws Exception
	 */
	public void recalcDistance(int notifyDistance, int cellX, int cellY)
			throws Exception;

	/**
	 * Returns the score of the specified cell. The score would be smaller as is
	 * near to the character. If its too far, returns -1. And if its equal to
	 * the character's position, returns 0.
	 */
	public int getDistanceScore(int cellX, int cellY);

}
