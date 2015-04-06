package src.jp.co.yh123.zlibrary.anime;

import src.jp.co.yh123.zlibrary.collision.HitBoxFactory;
import src.jp.co.yh123.zlibrary.util.Csv;
import src.jp.co.yh123.zlibrary.util.List;

public class AnimeFactory {
	/***************************************************************************
	 * #�A�j���[�V�����}�X�^��<br>
	 * #�ԍ�,imgid,frameid,�����蔻��ID 0,0,0,1,�u���b�N,,<br>
	 * 1,0,1,1,�u���b�N�オ��,,<br>
	 * 2,1,12,2,P,������,��<br>
	 * 2,1,13,2,P,������,��<br>
	 * 2,1,14,2,P,������,��
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

		// ������
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
				// ����ID�͂��ׂĎ擾
				frames = new AnimeFrame[framevec.size()];
				for (int k = 0; k < framevec.size(); k++) {
					frames[k] = (AnimeFrame) framevec.elementAt(k);
				}
				animevec.add(frames);
				framevec.removeAllElements();
			}
			framevec.add(frame);
		}
		// �Ō�̂�ǉ�
		frames = new AnimeFrame[framevec.size()];
		for (int k = 0; k < framevec.size(); k++) {
			frames[k] = (AnimeFrame) framevec.elementAt(k);
		}
		animevec.add(frames);
		framevec.removeAllElements();

		// �A�j����S�擾
		animes = new AnimeFrame[animevec.size()][];
		for (int i = 0; i < animevec.size(); i++) {
			animes[i] = (AnimeFrame[]) animevec.elementAt(i);
		}
		// dump();
	}

	/**
	 * �V�����A�j���̃C���X�^���X��ԋp���܂��B
	 * 
	 * @param frameChangeInterval
	 *            ���t���[�����ƂɃA�j���𓮂��������w�肵�܂��B-1�Ȃ�t���[���͌Œ�ł��B
	 * @param animeId
	 *            �A�j���ԍ����w�肵�܂��B-1�̏ꍇ�̓A�j�����Ȃ����̂Ƃ݂Ȃ��܂��B<br>
	 *            �����I�ɂ́A-1�̏ꍇ��Exception���X���[���܂��B
	 * @param maxLoopCount
	 *            �A�j��������J��Ԃ��ĕ`�悷�邩���w�肵�܂��B-1�̏ꍇ�A�����ɕ`�悵�܂��B
	 * @return �V�����A�j���C���X�^���X
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
