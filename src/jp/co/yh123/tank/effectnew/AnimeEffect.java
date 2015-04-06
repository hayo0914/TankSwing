package src.jp.co.yh123.tank.effectnew;

import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;
import src.jp.co.yh123.tank.map.IActorInterface;
import src.jp.co.yh123.tank.sfx.IAnimeEffect;
import src.jp.co.yh123.zlibrary.anime.Animation;
import src.jp.co.yh123.zlibrary.anime.AnimeFactory;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.util.DblPosition;
import src.jp.co.yh123.zlibrary.util.DebugUtil;

public class AnimeEffect extends Effect implements IAnimeEffect {

	AnimeEffect() {

	}

	public void init(IActorInterface source, int frameChangeInterval,
			int animeId, int maxLoopCount) throws Exception {
		setAnime(AnimeFactory.getInstance().createAnime(frameChangeInterval,
				animeId, maxLoopCount));
		setSource(source);
		setCell(source.getCellX(), source.getCellY());
		double x = source.getX() + source.getWidth() / 2 - getWidth() / 2;
		double y = source.getY() + source.getHeight() / 2 - getHeight() / 2;
		setPosition(x, y);
	}

	/**
	 * @param source
	 *            発生位置(中心の座標を指定)
	 * @param frameChangeInterval
	 *            アニメ更新フレーム間隔
	 * @param animeId
	 *            アニメID
	 * @param maxLoopCount
	 *            ループ回数
	 * @throws Exception
	 */
	public void init(DblPosition source, int frameChangeInterval, int animeId,
			int maxLoopCount) throws Exception {
		setAnime(AnimeFactory.getInstance().createAnime(frameChangeInterval,
				animeId, maxLoopCount));
		double x = source.getX() - getWidth() / 2;
		double y = source.getY() - getHeight() / 2;
		setPosition(x, y);
		setSource(null);
	}

	public void update() throws Exception {
		super.update();
		if (getSource() != null) {
			double x = getSource().getX() + getSource().getWidth() / 2
					- getAnime().getWidth() / 2;
			double y = getSource().getY() + getSource().getHeight() / 2
					- getAnime().getHeight() / 2;
			setPosition(x, y);
		}
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
