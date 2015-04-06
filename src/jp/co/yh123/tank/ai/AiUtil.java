package src.jp.co.yh123.tank.ai;

import src.jp.co.yh123.tank.chara.Chara;
import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;
import src.jp.co.yh123.tank.map.Map;
import src.jp.co.yh123.zlibrary.util.GameMath;
import src.jp.co.yh123.zlibrary.util.List;

public class AiUtil {

	protected static int moveFrameMonster = 0;

	protected static int[] cellMapX = { 1, 1, 0,// 0,45,90
			-1, -1, -1,// 135,180,225
			0, 1 };// 270,315

	protected static int[] cellMapY = { 0, 1, 1, // 0,45,90
			1, 0, -1,// 135,180,225
			-1, -1 };// 270,315

	/**
	 * 視認可能な中で、最も近くにいる敵を取得する
	 * 
	 * @param c
	 * @return
	 * @throws Exception
	 */
	static Chara getEnemyNearBy(Chara c) throws Exception {
		List enemyList = c.getEnemyList();
		return getNearBy(c, enemyList);
	}

	/**
	 * 対象と直線状にあるかを判定する
	 * 
	 * @param c
	 * @return
	 * @throws Exception
	 */
	static boolean isOnLine(Chara c, Chara tg) throws Exception {

		// 距離

		int diffX = tg.getCellX() - c.getCellX();
		int diffY = tg.getCellY() - c.getCellY();
		if (diffX == 0 || diffY == 0) {
			return true;
		}

		if (GameMath.abs(diffX) - GameMath.abs(diffY) == 0) {
			return true;
		}

		return false;
	}

	/**
	 * 対象のセルが隣接しているか。（1回の移動で到達できるか否か）
	 * 
	 * @return
	 * @throws Exception
	 */
	static boolean isNext(Chara c, int cellX, int cellY) throws Exception {
		switch (c.getDistance(cellX, cellY)) {
		case 1:
			return true;
		case 2:
			if (c.canDoSomeThingDiagonally(c.getCellX(), c.getCellY(), cellX,
					cellY)) {
				return true;
			}
		default:
			return false;
		}
	}

	/**
	 * 直接攻撃する(隣にいる場合のみ実行可能)
	 * 
	 * @param c
	 * @param tg
	 * @return Waitが必要か否か
	 * @throws Exception
	 */
	static boolean attack(Chara c, Chara tg) throws Exception {
		c.lookToTGCell(tg.getCurrentMapTip());
		// script
		model.scriptCurrentChara = c;
		model.scriptTgChara = tg;
		game.execScript(0, "attack", null);
		return true;
	}

	/**
	 * 左手法に従って向きを変更する。
	 * 
	 * @param c
	 * @param tgMapTip
	 * @throws Exception
	 */
	static void lookToAsLeftHandMethod(Chara c) throws Exception {

		MapTip t;
		Model m = ApplicationFacade.getModel();
		int wk;
		int x = c.getCellX();
		int y = c.getCellY();
		Map map = m.map;

		// 5割の確率で右手法に切り替え
		if (m.randomizer.getRandomInt(0, 100) >= 50) {
			// 右手法
			// 向きを90°単位に変更
			c.setDirection((c.getDirection() / 90) * 90);

			// 右をチェックし、右に壁が無ければ左に90°旋回
			wk = Map.getDirection0to359(c.getDirection() + 90);
			t = map.getMapTipByDirection(wk, x, y);
			if (!(t.isWall() && !t.isDoor())) {
				c.setDirection(wk);
				return;
			}
			// 右に壁がある時、正面をチェックし、前に壁が無ければ直進
			t = map.getMapTipByDirection(c.getDirection(), x, y);
			if (!(t.isWall() && !t.isDoor())) {
				return;
			}
			// 前に壁がある時、左をチェックし、左に壁が無ければ右に90°旋回
			wk = Map.getDirection0to359(c.getDirection() - 90);
			t = map.getMapTipByDirection(c.getDirection(), x, y);
			if (!(t.isWall() && !t.isDoor())) {
				c.setDirection(wk);
				return;
			}
			// 左・前・右とも壁の場合、反転する。
			c.setDirection(Map.getDirection0to359(c.getDirection() + 180));
		} else {
			// 左手法
			// 向きを90°単位に変更
			c.setDirection((c.getDirection() / 90) * 90);

			// 左をチェックし、左に壁が無ければ左に90°旋回
			wk = Map.getDirection0to359(c.getDirection() - 90);
			t = map.getMapTipByDirection(wk, x, y);
			if (!(t.isWall() && !t.isDoor())) {
				c.setDirection(wk);
				return;
			}
			// 左に壁がある時、正面をチェックし、前に壁が無ければ直進
			t = map.getMapTipByDirection(c.getDirection(), x, y);
			if (!(t.isWall() && !t.isDoor())) {
				return;
			}
			// 前に壁がある時、右をチェックし、右に壁が無ければ右に90°旋回
			wk = Map.getDirection0to359(c.getDirection() + 90);
			t = map.getMapTipByDirection(c.getDirection(), x, y);
			if (!(t.isWall() && !t.isDoor())) {
				c.setDirection(wk);
				return;
			}
			// 左・前・右とも壁の場合、反転する。
			c.setDirection(Map.getDirection0to359(c.getDirection() + 180));
		}

	}

	/**
	 * 隣にいるかどうか
	 * 
	 * @return
	 * @throws Exception
	 */
	protected boolean isAdjacent(Chara c, Chara tg) throws Exception {
		switch (c.getDistance(tg.getCellX(), tg.getCellY())) {
		case 1:
			return true;
		case 2:
			if (Map.isAbleToMoveOblique(c.getCellX(), c.getCellY(), tg
					.getCellX(), tg.getCellY())) {
				return true;
			}
		default:
			return false;
		}
	}

	private static Chara getNearBy(Chara c, List charaList) throws Exception {

		int minScore = 999;
		Chara ret = null;

		// 最も近くにいるキャラを見つける
		for (int i = 0; i < charaList.size(); i++) {
			Chara enemy = charaList.elementAtChara(i);
			int score = enemy.getDistance(c.getCellX(), c.getCellY());
			if (score <= 0) {
				continue;
			}
			if (score < minScore) {
				minScore = score;
				ret = enemy;
			}
		}
		return ret;
	}

	/*-----------------------------------------------------
	 * direction
	 * 向き。最後に進行した方向に向いている。これを使うのは、移動時に、
	 * モンスターが一定の方向に進むようにするため。移動の優先順位は
	 * 以下のとおり。
	 * ２１３
	 * ４モ５
	 * 向きの値は45°刻みで、0～315
	 *-----------------------------------------------------*/
	private static final int[] MOVE_DIRECTION_PRIORITY = { 0, 315, 45, 270, 90,
			225, 135, 180 };

	// 目標がない場合の移動
	// 優先度の高い方向に進む
	// 移動の優先順位
	// ２１３
	// ４モ５
	// 向きの値は45°刻みで、0～315
	static boolean walk(Chara c) throws Exception {

		// 優先順位の高い順に移動を試行する
		for (int i = 0; i < MOVE_DIRECTION_PRIORITY.length; i++) {
			if (moveToRelativeDirection(c, MOVE_DIRECTION_PRIORITY[i])) {
				return true;
			}
		}
		return false;

	}

	static boolean moveToRelativeDirection(Chara c, int relativeDirection)
			throws Exception {
		// 相対角度から、対象のセルへのオフセットを計算する
		// 現在の向き角度と相対角度から絶対角度計算
		int wkDir = c.getDirection() + relativeDirection;
		if (wkDir >= 360) {
			wkDir -= 360;
		}
		// 絶対角度を45で割って、テーブルからセルのオフセットを取得
		int num = wkDir / 45;// 0～7へ変換
		int x = cellMapX[num];
		int y = cellMapY[num];
		if (Map.isAbleToMoveTo(c, c.getCellX() + x, c.getCellY() + y)) {
			c.setDirection(wkDir);
			// 移動
			ApplicationFacade.getGame().moveTo(moveFrameMonster, c,
					c.getCellX() + x, c.getCellY() + y);
			return true;
		}

		// 移動はできないが、何かできることがないかチェック
		Map map = ApplicationFacade.getModel().map;
		MapTip tip = map.get(c.getCellX() + x, c.getCellY() + y);
		if (c.doSomethingTo(tip)) {
			// 何かを行った
			// 扉を開けるなど
			return true;
		}

		return false;
	}

	/* 周囲のオフセットX 優先順 斜め */
	private final static int[] offsetXNaname = new int[] { 1, -1, 1, -1 };
	/* 周囲のオフセットY 優先順 */
	private final static int[] offsetYNaname = new int[] { -1, -1, 1, 1 };
	/* 周囲のオフセットX 優先順 上下右左 */
	private final static int[] offsetX = new int[] { 0, 0, 1, -1 };
	/* 周囲のオフセットY 優先順 */
	private final static int[] offsetY = new int[] { -1, 1, 0, 0 };
	/* 周囲のオフセットの配列数 */
	private final static int OFFSET = 4;

	/**
	 * 目標のターゲットに最も近い進路をとる方向に向く
	 * 
	 * @param c
	 * @param enemy
	 * @return
	 * @throws Exception
	 */
	static boolean lookToApproach(Chara c, Chara target) throws Exception {
		// 敵がいる方向に方向転換
		// 近くに敵がいるかチェック
		int ret = target.getDistance(c.getCellX(), c.getCellY());
		if (ret == -1) {
			// 遠い
			return false;
		}

		// スコアが一つだけ小さいセル(=目標に近い)を探す

		int minX = 0;
		int minY = 0;
		int minScore = 99999;

		// 上下左右
		for (int i = 0; i < OFFSET; i++) {
			int wkX = c.getCellX() + offsetX[i];
			int wkY = c.getCellY() + offsetY[i];
			int wkScore = target.getDistance(wkX, wkY);
			if (wkScore >= 1 && wkScore < minScore && ret - wkScore == 1) {
				minScore = wkScore;
				minX = wkX;
				minY = wkY;
				break;
			}
		}

		// 斜め(同じスコアなら上下左右よりも斜めを優先する。斜めのほうが効率がよいため。)
		for (int i = 0; i < OFFSET; i++) {
			int wkX = c.getCellX() + offsetXNaname[i];
			int wkY = c.getCellY() + offsetYNaname[i];
			int wkScore = target.getDistance(wkX, wkY);
			if (wkScore >= 1 && wkScore < minScore && ret - wkScore == 2) {
				// 2離れているが、斜めなので1で移動できる
				if (Map.isAbleToMoveOblique(c.getCellX(), c.getCellY(), wkX,
						wkY)) {
					// 斜め移動可能な位置
					minScore = wkScore;
					minX = wkX;
					minY = wkY;
					break;
				}
			}
		}

		if (minX == c.getCellX() && minY == c.getCellY()) {
			// 近いセルを発見できなかった
			return false;
		}

		// 近いほうのセルの方向に向く
		lookToTGDirection(c, ApplicationFacade.getModel().map.get(minX, minY));

		return true;
	}

}
