package src.jp.co.yh123.tank.effectnew;

import src.jp.co.yh123.tank.map.IActorInterface;
import src.jp.co.yh123.tank.sfx.ITaskEffect;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.util.DblPosition;

public class TaskEffect extends Effect implements ITaskEffect {

	TaskEffect() {

	}

	public void init(IActorInterface source) throws Exception {
		setSource(source);
		setCell(source.getCellX(), source.getCellY());
		double x = source.getX() + source.getWidth() / 2 - getWidth() / 2;
		double y = source.getY() + source.getHeight() / 2 - getHeight() / 2;
		setPosition(x, y);
	}

	/*
	 * 公開Method
	 */
	public int getWidth() throws Exception {
		return 0;
	}

	public int getHeight() throws Exception {
		return 0;
	}

	/**
	 * @param source
	 *            発生位置(中心の座標を指定)
	 * @throws Exception
	 */
	public void init(DblPosition source) throws Exception {
		double x = source.getX() - getWidth() / 2;
		double y = source.getY() - getHeight() / 2;
		setPosition(x, y);
		setSource(null);
	}

	public void draw(GameGraphic g, int offsetX, int offsetY) throws Exception {
		// do nothing
	}

}
