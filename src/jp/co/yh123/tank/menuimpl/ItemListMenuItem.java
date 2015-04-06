package src.jp.co.yh123.tank.menuimpl;

import src.jp.co.yh123.tank.collabo.IItemClbInterface;
import src.jp.co.yh123.zlibrary.anime.Animation;
import src.jp.co.yh123.zlibrary.anime.AnimeFactory;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.platform.HmFont;
import src.jp.co.yh123.zlibrary.view.MenuItem;

public class ItemListMenuItem extends MenuItem {

	private static Animation anime = null;

	private IItemClbInterface item = null;
	private Animation itemAnime = null;

	public ItemListMenuItem(int id, IItemClbInterface item) throws Exception {
		super(id, "");
		this.item = item;
		if (item != null) {
			itemAnime = AnimeFactory.getInstance().createAnime(-1,
					item.getAnimeId(), -1);
		}
	}

	public int getHeight() {
		return 32;
	}

	public int getWidth() {
		return 32;
	}

	protected void onUpdate() throws Exception {
	}

	public void onAddToMenu() throws Exception {
		if (anime == null) {
			anime = AnimeFactory.getInstance().createAnime(-1, 8, -1);
		}
	}

	public void onSelect() throws Exception {
		// TODO Auto-generated method stub

	}

	IItemClbInterface getItem() {
		return item;
	}

	void setItem(IItemClbInterface item) {
		this.item = item;
	}

	@Override
	protected void onDraw(GameGraphic g) throws Exception {
		int x = getX(), y = getY();
		anime.setPosition(x, y);
		anime.draw(g, 0, 0);
		if (hasFocus()) {
			int color = 0xeeee88;
			g.setColor(color);
			g.drawRect(x + 1, y + 1, 28, 29);
			g.drawRect(x + 2, y + 2, 26, 27);
		}
		if (item != null) {
			itemAnime.setPosition(x + 2, y);
			itemAnime.draw(g, 0, 0);
			if (item.isEquipped()) {
				HmFont.setFont(g, HmFont.STYLE_PLAIN, HmFont.FONT_SMALL);
				g.drawBorderString("E", x + 22, y + 16, 0x339933, 0xffffff);
			}
			// FIXME:
			if (item.getStackedNum() != 1) {
				g.drawBorderString(String.valueOf(item.getStackedNum()),
						(int) getX() + 2 - 0, (int) getY() - 0 + 2, 0xffffff,
						0x000000);
			}
		}
	}

}
