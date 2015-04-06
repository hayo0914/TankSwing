package src.jp.co.yh123.tank.sfx;

import src.jp.co.yh123.tank.collabo.ICharaClbInterface;
import src.jp.co.yh123.tank.collabo.IEffectClbInterface;
import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;

public interface IMapEffect extends IEffectClbInterface {

	/**
	 * ����Effect���I��������
	 */
	public void setEnd() throws Exception;

	/**
	 * @param source
	 *            �����ʒu(MAPTIP���w��)
	 * @param frameChangeInterval
	 *            �A�j���X�V�t���[���Ԋu
	 * @param animeId
	 *            �A�j��ID
	 * @param maxLoopCount
	 *            ���[�v��
	 * @throws Exception
	 */
	public void init(IMaptipClbInterface source, int frameChangeInterval,
			int animeId, int maxLoopCount) throws Exception;

	/**
	 * ����Effect�̔��������ƂȂ���Chara��ۑ�����
	 * 
	 * @param owner
	 *            ���������ƂȂ���Chara
	 */
	public void setOwner(ICharaClbInterface owner) throws Exception;

	/**
	 * Round���ς�����ۂ̃��X�i��ۑ�����
	 * 
	 * @param listener
	 */
	public void setRoundInitListener(IEffectRoundInitListener listener);

	/**
	 * ����Effect�̌��݈ʒu��MAPTIP���擾����
	 * 
	 * @return
	 * @throws Exception
	 */
	public IMaptipClbInterface getCurrentPosition() throws Exception;
}
