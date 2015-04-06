package src.jp.co.yh123.tank.menuimpl;

import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.platform.HmFont;
import src.jp.co.yh123.zlibrary.view.MenuItem;

public class SimpleSelectMenuItem extends MenuItem {

	static int DEF_COLOR = 0x75797F;
	int color_offset = 0;
	int color_pm = +1;
	int count = 0;

	public SimpleSelectMenuItem(String text, int id) throws Exception {
		super(id, text);
	}

	public int getHeight() {
		return 20;
	}

	public int getWidth() {
		return 80;
	}

	protected void onDraw(GameGraphic g) throws Exception {
		int x = getX(), y = getY();
		if (hasFocus()) {
			g.setColor(DEF_COLOR + color_offset);// グレー
			g.fillRect(x, y, getWidth(), getHeight());
			HmFont.setFont(g, HmFont.STYLE_PLAIN, HmFont.FONT_TINY);
			g.drawBorderString(getText(), x + 20, y + 4, 0xdddddd, 0x222222);
		} else {
			g.setColor(0x545C64);// グレー
			g.fillRect(x, y, getWidth(), getHeight());
			HmFont.setFont(g, HmFont.STYLE_PLAIN, HmFont.FONT_TINY);
			g.drawBorderString(getText(), x + 20, y + 4, 0xdddddd, 0x222222);// 茶色

		}
	}

	protected void onUpdate() throws Exception {
		if (hasFocus()) {
			count++;
			if (count <= 12) {
				color_offset += color_pm * 0x050505;
				if (count == 12) {
					color_pm *= -1;
					count = 0;
				}
			}
		}
	}

	public void onAddToMenu() throws Exception {
	}

	public void onSelect() throws Exception {

	}

}
