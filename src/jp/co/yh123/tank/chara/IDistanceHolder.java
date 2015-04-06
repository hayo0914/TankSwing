package src.jp.co.yh123.tank.chara;

public interface IDistanceHolder {

	/**
	 * ���҂��猩�����̒��S�_�ւ̋���
	 * 
	 * @param notifyDistance
	 *            �ł���������C�Â���鋗��
	 * @param cellX
	 *            ���S�_X
	 * @param cellY
	 *            ���S�_Y
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
