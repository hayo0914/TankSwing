package src.jp.co.yh123.tank.effect;

import src.jp.co.yh123.tank.map.IActorInterface;
import src.jp.co.yh123.zlibrary.anime.Animation;
import src.jp.co.yh123.zlibrary.anime.AnimeFactory;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;

public class EffectAnimeFreePos extends Effect {

	public EffectAnimeFreePos(EffectCallBack o) {
		super(o);
	}

	public void set(double centerX, double centerY, int frameChangeInterval,
			int animeId, int maxLoopCount) throws Exception {
		Animation anime = getAnime();
		if (anime == null) {
			anime = AnimeFactory.getInstance().createAnime(frameChangeInterval,
					animeId, maxLoopCount);
		} else {
			anime.setAnime(frameChangeInterval, animeId, maxLoopCount);
		}
		setAnime(anime);
		setPosition(null);
		setCell(0, 0);
		double x = centerX - anime.getWidth() / 2;
		double y = centerY - anime.getHeight() / 2;
		setPosition(x, y);
	}

	protected void animeUpdate() throws Exception {
		if (isEnd()) {
			return;
		}
		getAnime().update();
		if (getAnime().isEnd()) {
			setEnd();
		}
	}

	public void draw(GameGraphic g, int offsetX, int offsetY) throws Exception {

		Animation anime = getAnime();
		if (isEnd()) {
			return;
		} else if (anime.isEnd()) {
			setEnd();
		}
		// IMaptipClbInterface tip = (IMaptipClbInterface) MapFactory.getMap()
		// .getByPoint(getX() + getWidth() / 2, getY() + getHeight() / 2);
		// if (tip != null) {
		// if (!tip.isVisible()) {
		// return;
		// } else if (tip.getVisibleUpdateTime() !=
		// getModel().visibleUpdateTime) {
		// // å©ÇΩÇ±Ç∆ÇÕÇ†ÇÈÇ™ÅAåªç›éãäEÇÃäO
		// return;
		// }
		// }
		anime.setPosition(getX(), getY());
		anime.draw(g, offsetX, offsetY);
	}

	public IActorInterface getSource() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
