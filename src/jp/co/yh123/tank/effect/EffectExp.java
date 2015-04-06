package src.jp.co.yh123.tank.effect;

import src.jp.co.yh123.tank.map.IActorInterface;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.platform.HmFont;

public class EffectExp extends Effect {

	public EffectExp(EffectCallBack o) {
		super(o);
	}

	private String expString = "";

	/**
	 * @return the expString
	 */
	public String getExpString() {
		return expString;
	}

	/**
	 * @param expStrging
	 *            the expString to set
	 */
	public void setExpString(String expString) {
		this.expString = expString;
	}

	public void set(IActorInterface target, String expString, double x, double y)
			throws Exception {
		execCount = 0;
		showCount = 0;
		moveY = -0.6;
		gravity = 0;
		setPosition(x, y);
		setCell(target.getCellX(), target.getCellY());
		setExpString(expString);
		setPosition(getX()
				- HmFont.getStringWidth(HmFont.FONT_LARGE, expString) / 2,
				getY());
		initX = getX();
		initY = getY();
	}

	int execCount = 0;

	int showCount = 0;

	double initX = 0;

	double initY = 0;

	double moveY = 0;

	double gravity = 0;

	protected void animeUpdate() throws Exception {
		if (isEnd()) {
			return;
		}
		execCount++;
		setPosition(getX(), getY() + moveY);
		moveY += gravity;

		if (execCount >= 80) {
			setEnd();
		}

	}

	public void draw(GameGraphic g, int offsetX, int offsetY) throws Exception {

		if (isEnd()) {
			return;
		}

		HmFont.setFont(g, HmFont.STYLE_PLAIN, HmFont.FONT_LARGE);
		g.drawBorderString(expString, (int) getX() - offsetX, (int) getY()
				- offsetY, 0xff55ff, 0x000000);

	}

	public IActorInterface getSource() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
