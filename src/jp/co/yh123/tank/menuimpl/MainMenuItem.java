package src.jp.co.yh123.tank.menuimpl;

import src.jp.co.yh123.tank.chara.Chara;
import src.jp.co.yh123.tank.menu.IMainMenuInterface;
import src.jp.co.yh123.zlibrary.anime.Animation;
import src.jp.co.yh123.zlibrary.anime.AnimeFactory;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.platform.HmFont;
import src.jp.co.yh123.zlibrary.view.MenuItem;

public class MainMenuItem extends MenuItem implements IMainMenuInterface {

	public void show(Chara c, IMainMenuCallBackInterface callback)
			throws Exception {

	}

	private static Animation animeSelect = null;

	private static Animation animeNotSelect = null;

	public MainMenuItem(String text, int id) throws Exception {
		super(id, text);
	}

	public int getHeight() {
		return 24;
	}

	public int getWidth() {
		return 85;
	}

	protected void onUpdate() throws Exception {
	}

	public void onAddToMenu() throws Exception {
		if (animeSelect == null) {
			animeSelect = AnimeFactory.getInstance().createAnime(-1, 5, -1);
		}
		if (animeNotSelect == null) {
			animeNotSelect = AnimeFactory.getInstance().createAnime(-1, 6, -1);
		}
	}

	public void onSelect() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDraw(GameGraphic g) throws Exception {
		int x = getX(), y = getY();
		if (hasFocus()) {
			animeSelect.setPosition(x, y);
			animeSelect.draw(g, 0, 0);
			HmFont.setFont(g, HmFont.STYLE_PLAIN, HmFont.FONT_TINY);
			g.drawBorderString(getText(), x + 29, y + 4, 0x000000, 0xaaaa00);
		} else {
			animeNotSelect.setPosition(x, y);
			animeNotSelect.draw(g, 0, 0);
			HmFont.setFont(g, HmFont.STYLE_PLAIN, HmFont.FONT_TINY);
			g.drawBorderString(getText(), x + 29, y + 4, 0x000000, 0xaaaa00);
		}

	}

}
