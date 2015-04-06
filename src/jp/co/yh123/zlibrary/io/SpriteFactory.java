package src.jp.co.yh123.zlibrary.io;

import src.jp.co.yh123.zlibrary.platform.ImageWrap;
import src.jp.co.yh123.zlibrary.platform.SpriteWrap;
import src.jp.co.yh123.zlibrary.util.Csv;

public class SpriteFactory {
	/***************************************************************************
	 * #画像マスタ<br>
	 * #id,ファイル名,spriteWidth,spriteHeight<br>
	 * 0,/res/ms1.png,16,16
	 **************************************************************************/
	private static SpriteFactory ins = null;

	public Csv master_img = null;

	private SpriteWrap[] sprIns = null;

	private SpriteFactory(Csv imgMaster) {
		master_img = imgMaster;
		if (imgMaster == null) {
			return;
		}
		sprIns = new SpriteWrap[master_img.getSize()];
		ins = this;
	}

	public static SpriteFactory getInstance() {
		return ins;
	}

	public static void createInstance(Csv imgMaster) throws Exception {
		ins = new SpriteFactory(imgMaster);
	}

	public SpriteWrap getSprite(int id) throws Exception {
		try {
			if (sprIns[id] == null) {
				// ロードが必要
				sprIns[id] = new SpriteWrap(ImageWrap.createImage(master_img
						.getString(id, 1)), master_img.getInt(id, 2),
						master_img.getInt(id, 3));
			}
			return sprIns[id];
		} catch (Exception e) {
			throw e;
		}
	}

	public void disposeSpr(int id) {
		if (sprIns[id] != null) {
			sprIns[id].dispose();
			sprIns[id] = null;
		}
	}
}
