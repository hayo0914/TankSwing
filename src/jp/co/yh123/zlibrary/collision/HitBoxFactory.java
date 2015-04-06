package src.jp.co.yh123.zlibrary.collision;

import src.jp.co.yh123.zlibrary.util.Csv;
import src.jp.co.yh123.zlibrary.util.List;

public class HitBoxFactory {

	private static HitBoxFactory instance = null;

	private static List interHitboxes = null;
	private static HitBox[][] hitboxList = null;
	private static final int HITBOX_ARRAY_SIZE_INIT = 100;

	public static void createInstance(Csv hitboxTable) throws Exception {
		if (instance == null)
			instance = new HitBoxFactory(hitboxTable);
	}

	private HitBoxFactory(Csv hitboxTable) throws Exception {

		if (interHitboxes != null) {
			return;
		}

		interHitboxes = new List(HITBOX_ARRAY_SIZE_INIT);
		HitBox hit = null;
		List wk = null;
		int preNumber = 0;
		wk = new List(HITBOX_ARRAY_SIZE_INIT);
		int size = hitboxTable.getSize();
		for (int i = 0; i < size; i++) {

			int number = hitboxTable.getInt(i, 0);

			if (preNumber != number) {
				// 違う種類のオブジェクトのヒットボックスになったので、切り替える
				interHitboxes.add(vecToHitboxArray(wk));
				wk = new List(HITBOX_ARRAY_SIZE_INIT);
			}
			preNumber = number;

			int x = hitboxTable.getInt(i, 1);
			int y = hitboxTable.getInt(i, 2);
			int w = hitboxTable.getInt(i, 3);
			int h = hitboxTable.getInt(i, 4);
			int damageflag = hitboxTable.getInt(i, 5);
			int damageAnime = hitboxTable.getInt(i, 6);
			hit = new HitBox(x, y, w, h, damageflag, damageAnime);
			wk.add(hit);
		}
		interHitboxes.add(vecToHitboxArray(wk));

		hitboxList = new HitBox[interHitboxes.size()][];
		HitBox[] hitsWk = null;
		for (int i = 0; i < interHitboxes.size(); i++) {
			hitsWk = (HitBox[]) interHitboxes.elementAt(i);
			if (hitsWk.length == 1 && hitsWk[0].getWidth() == 0) {
				hitboxList[i] = null;
			} else {
				hitboxList[i] = hitsWk;
			}
		}
		interHitboxes = null;
	}

	public static HitBoxFactory getInstance() {
		return instance;
	}

	public HitBox[] getHitBoxOfSHObject(int number) {

		return hitboxList[number];
	}

	private HitBox[] vecToHitboxArray(List vec) {

		HitBox[] hits = new HitBox[vec.size()];
		for (int i = 0; i < vec.size(); i++) {
			hits[i] = (HitBox) vec.elementAt(i);
		}
		return hits;

	}
}
