package src.jp.co.yh123.tank.collabo;

import src.jp.co.yh123.tank.map.IActorInterface;
import src.jp.co.yh123.tank.map.IEffectInterface;
import src.jp.co.yh123.tank.sfx.IEffectRoundInitListener;
import src.jp.co.yh123.tank.sfx.IEffectUpdateListener;
import src.jp.co.yh123.zlibrary.anime.Animation;

public interface IEffectClbInterface extends IEffectInterface {

	public Animation getAnime();

	public void setAnime(Animation anime);

	public ICharaClbInterface getOwner() throws Exception;

	public IActorInterface getSource() throws Exception;

	/**
	 * ����Effect�̌��݈ʒu��MAPTIP���擾����
	 * 
	 * @return
	 * @throws Exception
	 */
	public IMaptipClbInterface getCurrentPosition() throws Exception;

	/**
	 * ����Effect���I��������
	 */
	public void setEnd() throws Exception;

	/**
	 * Round���ς�����ۂ̃��X�i��ۑ�����
	 * 
	 * @param listener
	 */
	public void setRoundInitListener(IEffectRoundInitListener listener);

	/**
	 * Update���̃��X�i��ۑ�����
	 * 
	 * @param listener
	 */
	public void setUpdateListener(IEffectUpdateListener listener);

	/**
	 * @return �X�V�񐔂�ԋp����
	 */
	public int getUpdateCount();
}
