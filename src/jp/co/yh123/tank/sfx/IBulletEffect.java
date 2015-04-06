package src.jp.co.yh123.tank.sfx;

import src.jp.co.yh123.tank.collabo.ICharaClbInterface;
import src.jp.co.yh123.tank.collabo.IEffectClbInterface;
import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;

public interface IBulletEffect extends IEffectClbInterface {

	/**
	 * @param source
	 *            �����ʒu(MAPTIP���w��)
	 * @param frameChangeInterval
	 * @param animeId
	 * @param maxLoopCount
	 * @param degree
	 *            ���ˊp�x
	 * @param spd
	 *            ���x
	 * @throws Exception
	 */
	public void init(IMaptipClbInterface source, int frameChangeInterval,
			int animeId, int maxLoopCount, int degree, double spd)
			throws Exception;

	/**
	 * ����Effect�̔��������ƂȂ���Chara��ۑ�����
	 * 
	 * @param owner
	 *            ���������ƂȂ���Chara
	 */
	public void setOwner(ICharaClbInterface owner) throws Exception;

	/**
	 * ����Effect��MAPTIP�ƏՓ˂������̃��X�i��ۑ�����
	 * 
	 * @param l
	 *            ���X�i
	 * @throws Exception
	 */
	public void setEffectHitListener(IBulletHitListener l) throws Exception;

	/**
	 * �o�E���h������
	 */
	public void bound();

	/**
	 * �w��̑��x��ύX����
	 * 
	 * @param bairitsu
	 *            �V�������x
	 */
	public void changeSpd(double newSpeed);

	/**
	 * �i�s������ύX����
	 * 
	 * @param degree
	 */
	public void setDirection(int degree);

	/**
	 * ���x��ԋp����
	 */
	public double getSpd();

	/**
	 * �p�x��ԋp����
	 */
	public int getDegree();

	/**
	 * �����ӏ�����̋�����ԋp����
	 * 
	 * @return �����ӏ�����̋���(�ʉ߂���MAPTIP�̐��B�����l��1�j
	 */
	public int getDistance();
}
