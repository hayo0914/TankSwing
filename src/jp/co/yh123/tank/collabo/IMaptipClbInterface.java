package src.jp.co.yh123.tank.collabo;

import src.jp.co.yh123.tank.map.IMaptipInterface;
import src.jp.co.yh123.zlibrary.anime.Animation;

public interface IMaptipClbInterface extends IMaptipInterface {

	public Animation getAnime();

	public void removeMapObject() throws Exception;

	public void removeItem() throws Exception;

	public IItemClbInterface getItem() throws Exception;

	public IMapObjectClbInterface getMapObject() throws Exception;

	public ICharaClbInterface getChara() throws Exception;

	public boolean hasEffect() throws Exception;

	public boolean isVisibleFromPlayer() throws Exception;

	public IEffectClbInterface getEffect() throws Exception;

	public void setEffect(IEffectClbInterface effect) throws Exception;

	public void removeEffect() throws Exception;

	public void damage(String attribute) throws Exception;

	/**
	 * 移動先のマップチップに他のCharaが移動できないよう予約する。
	 * 
	 * @param chara
	 * @return
	 * @throws Exception
	 */
	public void reserve(ICharaClbInterface chara) throws Exception;

	public boolean isReserved() throws Exception;
}
