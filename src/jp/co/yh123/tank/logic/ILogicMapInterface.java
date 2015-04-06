package src.jp.co.yh123.tank.logic;

import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.util.List;

public interface ILogicMapInterface {

	public void initMap() throws Exception;

	public void draw(GameGraphic g) throws Exception;

	/**
	 * @return 現在MAPに存在するILogicCharaInterfaceのインスタンスのリストを返却する。
	 * @throws Exception
	 */
	public List getCharaAll() throws Exception;

	public List getSyncEffectAll() throws Exception;

	public List getAsyncEffectAll() throws Exception;
}
