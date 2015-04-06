package src.jp.co.yh123.zlibrary.anime;

import src.jp.co.yh123.zlibrary.collision.HitBoxFactory;
import src.jp.co.yh123.zlibrary.util.Csv;
import src.jp.co.yh123.zlibrary.util.List;

public class AnimeFactory {
	/***************************************************************************
	 * #アニメーションマスタ例<br>
	 * #番号,imgid,frameid,当たり判定ID 0,0,0,1,ブロック,,<br>
	 * 1,0,1,1,ブロック上が草,,<br>
	 * 2,1,12,2,P,左歩き,左<br>
	 * 2,1,13,2,P,左歩き,左<br>
	 * 2,1,14,2,P,左歩き,左
	 **************************************************************************/

	private static AnimeFactory instance = null;

	private static Csv master = null;

	private static AnimeFrame[][] animes = null;

	private static final int INITIAL_ARRAY_SIZE = 100;

	public static void createInstance(Csv animeTable) throws Exception {
		if (instance == null)
			instance = new AnimeFactory(animeTable);
	}

	public static AnimeFactory getInstance() {
		return instance;
	}

	private AnimeFactory(Csv csv) throws Exception {

		master = csv;
		List animevec = new List(INITIAL_ARRAY_SIZE);

		// 初期化
		List framevec = new List(INITIAL_ARRAY_SIZE);
		AnimeFrame frame = null;
		AnimeFrame[] frames = null;
		int id = 0;
		int preId = 0;
		for (int i = 0; i < master.getSize(); i++) {
			preId = id;
			id = master.getInt(i, 0);
			frame = AnimeFrame.newAnimeFrame(master.getInt(i, 1), master
					.getInt(i, 2), HitBoxFactory.getInstance()
					.getHitBoxOfSHObject(master.getInt(i, 3)));
			if (id != preId && framevec.size() != 0) {
				// このIDはすべて取得
				frames = new AnimeFrame[framevec.size()];
				for (int k = 0; k < framevec.size(); k++) {
					frames[k] = (AnimeFrame) framevec.elementAt(k);
				}
				animevec.add(frames);
				framevec.removeAllElements();
			}
			framevec.add(frame);
		}
		// 最後のを追加
		frames = new AnimeFrame[framevec.size()];
		for (int k = 0; k < framevec.size(); k++) {
			frames[k] = (AnimeFrame) framevec.elementAt(k);
		}
		animevec.add(frames);
		framevec.removeAllElements();

		// アニメを全取得
		animes = new AnimeFrame[animevec.size()][];
		for (int i = 0; i < animevec.size(); i++) {
			animes[i] = (AnimeFrame[]) animevec.elementAt(i);
		}
		// dump();
	}

	/**
	 * 新しいアニメのインスタンスを返却します。
	 * 
	 * @param frameChangeInterval
	 *            何フレームごとにアニメを動かすかを指定します。-1ならフレームは固定です。
	 * @param animeId
	 *            アニメ番号を指定します。-1の場合はアニメがないものとみなします。<br>
	 *            将来的には、-1の場合はExceptionをスローします。
	 * @param maxLoopCount
	 *            アニメを何回繰り返して描画するかを指定します。-1の場合、無限に描画します。
	 * @return 新しいアニメインスタンス
	 * @throws Exception
	 */
	public Animation createAnime(int frameChangeInterval, int animeId,
			int maxLoopCount) throws Exception {
		Animation anime = new Animation(frameChangeInterval, animeId,
				maxLoopCount);
		anime.setAnime(frameChangeInterval, animeId, maxLoopCount);
		return anime;
	}

	public AnimeFrame[] getAnime(int id) throws Exception {
		return animes[id];
	}

	public void dump() {
		for (int i = 0; i < animes.length; i++) {
			for (int j = 0; j < animes[i].length; j++) {
				animes[i][j].dump();
				System.out.print(",");
			}
			System.out.println();
		}
	}

	int getAnimePatternNum() {
		return animes.length;
	}

}
