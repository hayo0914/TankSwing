package src.jp.co.yh123.tank.menuimpl;

import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.platform.HmFont;
import src.jp.co.yh123.zlibrary.view.MenuItem;

public class NumberInputMenuItem extends MenuItem {

	private int _number = 0;
	private String _strNumber = String.valueOf(_number);

	public NumberInputMenuItem(int id) throws Exception {
		super(id, "");
	}

	public int getHeight() {
		return 32;
	}

	public int getWidth() {
		return 16;
	}

	public void setNumber(int number) {
		_number = number;
		_strNumber = String.valueOf(number);
	}

	public void incrNumber() {
		if (_number == 9) {
			setNumber(0);
		} else {
			setNumber(_number + 1);
		}
	}

	public void decrNumber() {
		if (_number == 0) {
			setNumber(9);
		} else {
			setNumber(_number - 1);
		}
	}

	protected void onDraw(GameGraphic g) throws Exception {
		if (hasFocus()) {
			g.setColor(0xaaaa00);
			g.fillRect(getX() + 1, getY() + 1, getWidth(), getHeight());
		} else {
			g.setColor(0x228822);
			g.fillRect(getX() + 1, getY() + 1, getWidth(), getHeight());
		}
		g.setColor(0xffffff);
		g.drawRect(getX() + 1, getY() + 1, getWidth(), getHeight());
		HmFont.setFont(g, HmFont.STYLE_PLAIN, HmFont.FONT_MEDIUM);
		int charX = getX() + (getWidth() / 2)
				- (HmFont.getStringWidth(HmFont.FONT_MEDIUM, _strNumber) / 2);
		g.drawBorderString(_strNumber, charX, getY() + 4, 0xffffff, 0x000000);
	}

	protected void onUpdate() throws Exception {
	}

	public void onAddToMenu() throws Exception {
	}

	public void onSelect() throws Exception {

	}

	public int getNumber() {
		return _number;
	}

}
