package src.jp.co.yh123.tank.effectnew;

import src.jp.co.yh123.tank.map.IActorInterface;
import src.jp.co.yh123.tank.sfx.IMessageEffect;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.platform.HmFont;
import src.jp.co.yh123.zlibrary.util.DebugUtil;

public class MessageEffect extends Effect implements IMessageEffect {

	private String _message = null;
	private int _color = 0;
	private int _bgColor = 0;

	public void init(IActorInterface target, String strMsg, int color,
			int bgColor) throws Exception {
		_message = strMsg;
		_color = color;
		_bgColor = bgColor;
		moveY = -1.7d;
		setCell(target.getCellX(), target.getCellY());
		setPosition(target.getX() + (target.getWidth() / 2)
				- HmFont.getStringWidth(HmFont.FONT_MEDIUM, strMsg) / 2, target
				.getY()
				+ (target.getHeight() / 2));
	}

	private double moveY = 0;

	public void update() throws Exception {
		super.update();
		setPosition(getX(), getY() + moveY);
		if (getUpdateCount() > 40) {
			setEnd();
		}

	}

	public void draw(GameGraphic g, int offsetX, int offsetY) throws Exception {
		if (isEnd()) {
			return;
		}
		HmFont.setFont(g, HmFont.STYLE_PLAIN, HmFont.FONT_LARGE);
		g.drawBorderString(_message, (int) getX() - offsetX, (int) getY()
				- offsetY, _color, _bgColor);

	}
}
