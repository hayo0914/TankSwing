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
	 * このEffectの現在位置のMAPTIPを取得する
	 * 
	 * @return
	 * @throws Exception
	 */
	public IMaptipClbInterface getCurrentPosition() throws Exception;

	/**
	 * このEffectを終了させる
	 */
	public void setEnd() throws Exception;

	/**
	 * Roundが変わった際のリスナを保存する
	 * 
	 * @param listener
	 */
	public void setRoundInitListener(IEffectRoundInitListener listener);

	/**
	 * Update時のリスナを保存する
	 * 
	 * @param listener
	 */
	public void setUpdateListener(IEffectUpdateListener listener);

	/**
	 * @return 更新回数を返却する
	 */
	public int getUpdateCount();
}
