package src.jp.co.yh123.tank.effect;

import src.jp.co.yh123.tank.map.IActorInterface;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.platform.HmFont;

public class EffectMessage extends Effect {

	public EffectMessage(EffectCallBack o) {
		super(o);
	}

	private String message = null;
	private int color = 0;
	private int bgColor = 0;

	public void set(IActorInterface target, String strMsg, double x, double y,
			int color, int bgColor) throws Exception {
		message = strMsg;
		this.color = color;
		this.bgColor = bgColor;
		execCount = 0;
		moveY = -1.2;
		setPosition(x, y);
		setCell(target.getCellX(), target.getCellY());
		setPosition(getX() - HmFont.getStringWidth(HmFont.FONT_MEDIUM, strMsg)
				/ 2, getY());
	}

	private int execCount = 0;

	private double moveY = 0;

	protected void animeUpdate() throws Exception {
		if (isEnd()) {
			return;
		}
		execCount++;
		setPosition(getX(), getY() + moveY);
		if (execCount > 40) {
			setEnd();
		}

	}

	public void draw(GameGraphic g, int offsetX, int offsetY) throws Exception {

		if (isEnd()) {
			return;
		}

		HmFont.setFont(g, HmFont.STYLE_PLAIN, HmFont.FONT_MEDIUM);
		g.drawBorderString(message, (int) getX() - offsetX, (int) getY()
				- offsetY, color, bgColor);

	}

	public IActorInterface getSource() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
