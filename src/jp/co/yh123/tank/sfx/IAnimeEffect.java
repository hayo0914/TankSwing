package src.jp.co.yh123.tank.sfx;

import src.jp.co.yh123.tank.collabo.ICharaClbInterface;
import src.jp.co.yh123.tank.collabo.IEffectClbInterface;
import src.jp.co.yh123.tank.map.IActorInterface;
import src.jp.co.yh123.zlibrary.util.DblPosition;

public interface IAnimeEffect extends IEffectClbInterface {

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
	public void init(IActorInterface source, int frameChangeInterval,
			int animeId, int maxLoopCount) throws Exception;

	/**
	 * @param source
	 *            �����ʒu(���S�̍��W���w��)
	 * @param frameChangeInterval
	 *            �A�j���X�V�t���[���Ԋu
	 * @param animeId
	 *            �A�j��ID
	 * @param maxLoopCount
	 *            ���[�v��
	 * @throws Exception
	 */
	public void init(DblPosition source, int frameChangeInterval, int animeId,
			int maxLoopCount) throws Exception;

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

}
