package src.jp.co.yh123.tank.resource;

import src.jp.co.yh123.zlibrary.anime.AnimeFactory;
import src.jp.co.yh123.zlibrary.collision.HitBoxFactory;
import src.jp.co.yh123.zlibrary.io.SpriteFactory;
import src.jp.co.yh123.zlibrary.io.ScriptFactory;
import src.jp.co.yh123.zlibrary.util.Csv;
import src.jp.co.yh123.zlibrary.util.GameMath;

/**
 * リソースのポインタを保持
 * 
 * @author hamaday
 * 
 */
public class Res {

	private static Res res = null;
	/***************************************************************************
	 * マスタ
	 **************************************************************************/

	public Csv csvEquip = null;

	public Csv csvSpell = null;

	public Csv csvRace = null;

	public Csv csvCharaImg = null;

	public Csv csvDungeon = null;

	public Csv csvMapTile = null;

	public Csv csvMapObject = null;

	public Csv csvMonster = null;

	public Csv csvItem = null;

	public Csv csvEnchant = null;

	private Res() {

	}

	public static Res get() {
		return res;
	}

	private void loadData() throws Exception {

		// 角度テーブル初期化
		GameMath.initRadianData();

		// 画像マスタ初期化
		SpriteFactory.createInstance(Csv.createCsvFromResourceFile("/res/master/image.csv"));

		// あたり判定マスタ初期化
		HitBoxFactory.createInstance(Csv
				.createCsvFromResourceFile("/res/master/hitbox.csv"));

		// アニメマスタ初期化
		AnimeFactory.createInstance(Csv
				.createCsvFromResourceFile("/res/master/anime.csv"));

		// スクリプトマスタ初期化
		ScriptFactory
				.init(Csv.createCsvFromResourceFile("/res/master/script.csv"));

		// マスタ読み込み
		csvEquip = Csv.createCsvFromResourceFile("/res/master/equip.csv");
		csvRace = Csv.createCsvFromResourceFile("/res/master/race.csv");
		csvCharaImg = Csv.createCsvFromResourceFile("/res/master/charaimg.csv");
		csvDungeon = Csv.createCsvFromResourceFile("/res/master/dungeon.csv");
		csvMapTile = Csv.createCsvFromResourceFile("/res/master/maptile.csv");
		csvMapObject = Csv
				.createCsvFromResourceFile("/res/master/mapobject.csv");
		csvMonster = Csv.createCsvFromResourceFile("/res/master/monster.csv");
		csvItem = Csv.createCsvFromResourceFile("/res/master/item.csv");
		csvEnchant = Csv.createCsvFromResourceFile("/res/master/enchant.csv");
		csvSpell = Csv.createCsvFromResourceFile("/res/master/spell.csv");
	}

	public static void load() throws Exception {
		if (res != null) {
			throw new Exception("duplicate loading");
		}
		res = new Res();
		res.loadData();
	}

}
