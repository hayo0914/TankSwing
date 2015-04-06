package src.jp.co.yh123.tank.collabo;

import src.jp.co.yh123.tank.map.IMapObjectInterface;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;

public interface IMapObjectClbInterface extends IMapObjectInterface {

	public boolean isBarrier() throws Exception;

	public boolean isDoor() throws Exception;

	// public boolean isDoorOpen() throws Exception;
	//
	// public boolean isPassable() throws Exception;

	public void draw(GameGraphic g, int offsetX, int offsetY) throws Exception;

	public void damage(String attribute) throws Exception;

	public boolean isUpStair() throws Exception;

	public boolean isDownStair() throws Exception;
}
