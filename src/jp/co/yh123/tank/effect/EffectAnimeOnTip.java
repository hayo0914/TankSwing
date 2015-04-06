package src.jp.co.yh123.tank.effect;

import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;
import src.jp.co.yh123.tank.map.IActorInterface;
import src.jp.co.yh123.zlibrary.anime.Animation;
import src.jp.co.yh123.zlibrary.anime.AnimeFactory;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;

public class EffectAnimeOnTip extends Effect {

	public EffectAnimeOnTip(EffectCallBack o) {
		super(o);
	}

	public void set(IMaptipClbInterface target, int frameChangeInterval,
			int animeId, int maxLoopCount) throws Exception {
		Animation anime = getAnime();
		if (anime == null) {
			anime = AnimeFactory.getInstance().createAnime(frameChangeInterval,
					animeId, maxLoopCount);
		} else {
			anime.setAnime(frameChangeInterval, animeId, maxLoopCount);
		}
		setAnime(anime);
		setPosition(target);
		setCell(target.getCellX(), target.getCellY());
		double x = target.getX() + target.getWidth() / 2 - anime.getWidth() / 2;
		double y = target.getY() + target.getHeight() / 2 - anime.getHeight()
				/ 2;
		setPosition(x, y);
		if (target.hasEffect()) {
			((Effect) target.getEffect()).setEnd();
		}
		target.setEffect(this);
	}

	protected void animeUpdate() throws Exception {
		if (isEnd()) {
			return;
		}
		double x = getPosition().getX() + getPosition().getWidth() / 2
				- getAnime().getWidth() / 2;
		double y = getPosition().getY() + getPosition().getHeight() / 2
				- getAnime().getHeight() / 2;
		setPosition(x, y);
		getAnime().update();
		if (getAnime().isEnd()) {
			setEnd();
		}
	}

	public void setEnd() throws Exception {
		super.setEnd();
		((IMaptipClbInterface) getPosition()).removeEffect();
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
