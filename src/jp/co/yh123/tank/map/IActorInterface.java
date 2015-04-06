package src.jp.co.yh123.tank.map;

import src.jp.co.yh123.zlibrary.platform.GameGraphic;

/**
 * @author hamaday
 * 
 */
public interface IActorInterface {

	public int getWidth() throws Exception;

	public int getHeight() throws Exception;

	public int getCellX();

	public int getCellY();

	public void setCell(int cellX, int cellY) throws Exception;

	public void setPosition(double x, double y);

	public double getX();

	public double getY();

	public void draw(GameGraphic g, int offsetX, int offsetY) throws Exception;
}
