package src.jp.co.yh123.tank.effect;

import src.jp.co.yh123.tank.map.IActorInterface;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;

public class EffectNothing extends Effect {

	public EffectNothing(EffectCallBack o) {
		super(o);
	}

	public void set(IActorInterface target) throws Exception {
		setPosition(target);
		setCell(target.getCellX(), target.getCellY());
		double x = target.getX() + target.getWidth() / 2;
		double y = target.getY() + target.getHeight() / 2;
		setPosition(x, y);
	}

	protected void animeUpdate() throws Exception {
	}

	public void draw(GameGraphic g, int offsetX, int offsetY) throws Exception {
	}

	public IActorInterface getSource() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
