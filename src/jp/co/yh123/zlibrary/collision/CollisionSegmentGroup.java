package src.jp.co.yh123.zlibrary.collision;

public class CollisionSegmentGroup {

	// このグループのセグメントを格納する配列
	private CollisionSegment[] segments = null;

	// １セグメントの横サイズ
	private int segmentWidth = 0;

	// １セグメントの縦サイズ
	private int segmentHeight = 0;

	private int segmentNum = 10;

	/**
	 * @param maxObjectNum
	 *            １セグメントに格納する最大オブジェクト数
	 * @param width
	 *            マップの横サイズ
	 * @param height
	 *            マップの縦サイズ
	 */
	public CollisionSegmentGroup(int maxObjectNum, int width, int height) {
		segments = new CollisionSegment[segmentNum * segmentNum];
		for (int i = 0; i < segments.length; i++) {
			segments[i] = new CollisionSegment(maxObjectNum);
		}
		segmentWidth = width / segmentNum;
		segmentHeight = height / segmentNum;
	}

	/**
	 * セグメントデータをリセットする
	 */
	public void initSegments() {
		for (int i = 0; i < segments.length; i++) {
			segments[i].initObjList();
		}
	}

	// /**
	// * 当たり判定に余裕を持たせるため、幅に足す
	// */
	// private final static int extend = 0;

	/**
	 * エンティティをセグメントに登録する
	 * 
	 * @param ent
	 */
	synchronized public void registryEntity(ICollisionCheckEntity anime) {

		// ワークエリア
		int wk1 = 0;
		int wk2 = 0;
		int wk3 = 0;
		int wk4 = 0;
		int wk5 = 0;

		int wk = 0;
		int x = (int) anime.getX();
		int y = (int) anime.getY();
		int wid = anime.getWidth();
		int hei = anime.getHeight();

		// 左上
		wk1 = (int) ((x / segmentWidth) + (((y / segmentHeight)) * segmentNum));
		if (wk1 < segments.length && wk1 >= 0) {
			segments[wk1].addShObject(anime);
			anime.addCollisionSegment(segments[wk1]);
		} else {
			return;
		}

		// 右下
		wk2 = (int) (((x + wid) / segmentWidth) + ((((y + hei) / segmentHeight)) * segmentNum));

		if (wk1 == wk2) {
			return;
		}

		if (wk2 < segments.length && wk2 >= 0) {
			segments[wk2].addShObject(anime);
			anime.addCollisionSegment(segments[wk2]);
		} else {
			return;
		}

		// 間を全部入れる.
		wk3 = (wk2 % segmentNum) - (wk1 % segmentNum);
		wk4 = (wk2 / segmentNum) - (wk1 / segmentNum);

		for (int i = 0; i <= wk4; i++) {
			wk = i * segmentNum;
			for (int j = 0; j <= wk3; j++) {
				wk5 = wk1 + wk + j;
				segments[wk5].addShObject(anime);
				anime.addCollisionSegment(segments[wk5]);
			}
		}

	}

	/**
	 * 衝突判定を行う<br>
	 * 衝突していた場合、両方のエンティティのHITがコールされる<br>
	 * パフォーマンス劣化防止のため、1回のチェックで1HITまでしかチェックしない
	 * 
	 * @throws Exception
	 */
	public boolean collisionCheck(ICollisionCheckEntity ent) throws Exception {

		CollisionSegment[] entSegs = ent.getCollisionSegmentsList();
		int size = ent.getCollisionSegmentsLength();
		for (int i = 0; i < size; i++) {
			ICollisionCheckEntity[] others = entSegs[i].getObjList();
			for (int j = 0; j < entSegs[i].entityNum; j++) {
				ICollisionCheckEntity other = others[j];
				// 当たり判定をチェック
				if (ent.getAnime().isTouching(other.getAnime())) {
					// 両オブジェクトの衝突処理をコール
					ent.onHit(other);
					other.onHit(ent);
					return true;
				}
			}
		}
		return false;
	}
}
