package src.jp.co.yh123.tank.sfx;

import src.jp.co.yh123.tank.collabo.ICharaClbInterface;
import src.jp.co.yh123.tank.collabo.IEffectClbInterface;
import src.jp.co.yh123.tank.map.IActorInterface;

public interface ITaskEffect extends IEffectClbInterface {

	/**
	 * @param source
	 *            �����ʒu(Actor���w��)
	 * @param frameChangeInterval
	 *            �A�j���X�V�t���[���Ԋu
	 * @param animeId
	 *            �A�j��ID
	 * @param maxLoopCount
	 *            ���[�v��
	 * @throws Exception
	 */
	public void init(IActorInterface source) throws Exception;

	/**
	 * ����Effect�̔��������ƂȂ���Chara��ۑ�����
	 * 
	 * @param owner
	 *            ���������ƂȂ���Chara
	 */
	public void setOwner(ICharaClbInterface owner) throws Exception;

}
