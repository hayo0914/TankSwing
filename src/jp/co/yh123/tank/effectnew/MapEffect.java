package src.jp.co.yh123.tank.effectnew;

import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;
import src.jp.co.yh123.tank.sfx.IMapEffect;
import src.jp.co.yh123.zlibrary.anime.Animation;
import src.jp.co.yh123.zlibrary.anime.AnimeFactory;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;

public class MapEffect extends Effect implements IMapEffect {

	MapEffect() {

	}

	public void init(IMaptipClbInterface source, int frameChangeInterval,
			int animeId, int maxLoopCount) throws Exception {
		setAnime(AnimeFactory.getInstance().createAnime(frameChangeInterval,
				animeId, maxLoopCount));
		setSource(source);
		setCell(source.getCellX(), source.getCellY());
		double x = source.getX() + source.getWidth() / 2 - getWidth() / 2;
		double y = source.getY() + source.getHeight() / 2 - getHeight() / 2;
		setPosition(x, y);
		if (source.hasEffect()) {
			source.getEffect().setEnd();
		}
		source.setEffect(this);
	}

	public void setEnd() throws Exception {
		super.setEnd();
		((IMaptipClbInterface) getSource()).removeEffect();
	}

	public void update() throws Exception {
		super.update();
		double x = getSource().getX() + getSource().getWidth() / 2
				- getAnime().getWidth() / 2;
		double y = getSource().getY() + getSource().getHeight() / 2
				- getAnime().getHeight() / 2;
		setPosition(x, y);
		getAnime().update();
		if (getAnime().isEnd()) {
			setEnd();
		}
	}

	public void draw(GameGraphic g, int offsetX, int offsetY) throws Exception {
		if (isEnd()) {
			return;
		}
		IMaptipClbInterface tip = getCurrentPosition();
		if (tip != null && !tip.isVisibleFromPlayer()) {
			return;
		}
		Animation anime = getAnime();
		anime.setPosition(getX(), getY());
		anime.draw(g, offsetX, offsetY);
	}
}
