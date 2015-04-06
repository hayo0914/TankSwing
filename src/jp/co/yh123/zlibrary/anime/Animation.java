package src.jp.co.yh123.zlibrary.anime;

import src.jp.co.yh123.zlibrary.collision.HitBox;
import src.jp.co.yh123.zlibrary.io.SpriteFactory;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.platform.SpriteWrap;

public class Animation {
	private int currentFrame = 0;

	private int updateCount = 0;

	private int currentLoopCount = 0;

	private boolean end = false;

	private int frameChangeInterval = 0;

	private int animeId = -1;

	private int maxLoopCount = -1;

	private double posX = 0;

	private double posY = 0;

	private AnimeFrame[] anime = null;

	/***************************************************************************
	 * 当たり判定
	 **************************************************************************/
	private HitBox lastHitBox = null;

	// セグメント
	public int[] segments = new int[50];

	// 登録セグメントの数
	public int segmentNum = 0;

	Animation(int frameChangeInterval, int animeId, int maxLoopCount) {
	}

	/**
	 * 描画するアニメを指定し、アニメを初期化します。
	 * 
	 * @param frameChangeInterval
	 *            何フレームごとにアニメを動かすかを指定します。-1ならフレームは固定です。
	 * @param animeId
	 *            アニメ番号を指定します。-1の場合はアニメがないものとみなします。<br>
	 *            将来的には、-1の場合はExceptionをスローします。
	 * @param maxLoopCount
	 *            アニメを何回繰り返して描画するかを指定します。-1の場合、無限に描画します。
	 * @param loopType
	 * @throws Exception
	 */
	public void setAnime(int updateInter, int animeId, int animeCount)
			throws Exception {
		this.frameChangeInterval = updateInter;
		this.animeId = animeId;
		this.maxLoopCount = animeCount;
		this.initAnime();
	}

	/**
	 * アニメの更新をします。毎フレーム呼び出してください。
	 * 
	 * @param ent
	 * @throws Exception
	 */
	public void update() throws Exception {
		if (end || frameChangeInterval <= 0) {
			return;
		}
		updateCount++;
		if (updateCount % frameChangeInterval == 0) {
			updateCount = 0;
			frameUpdate();
		}
	}

	/**
	 * アニメのフレームを更新
	 * 
	 * @throws Exception
	 */
	private void frameUpdate() throws Exception {
		currentFrame++;
		if (anime.length == currentFrame) {
			currentLoopCount++;
			currentFrame = 0;
			if (maxLoopCount != -1 && currentLoopCount >= maxLoopCount) {
				end = true;
			}
		}
	}

	/**
	 * アニメーションを初期化します。
	 * 
	 * @param ent
	 */
	private void initAnime() throws Exception {
		if (animeId < 0) {
			end = true;
			return;
		}
		anime = AnimeFactory.getInstance().getAnime(animeId);
		currentLoopCount = 0;
		updateCount = 0;
		end = false;
		currentFrame = 0;
	}

	public int getCurrentLoopCount() {
		return currentLoopCount;
	}

	/**
	 * オフセット付描画します。
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param offsetX
	 * @param offsetY
	 * @throws Exception
	 */
	public void draw(GameGraphic g, int offsetX, int offsetY) throws Exception {
		if (end) {
			return;
		}

		AnimeFrame frame = anime[currentFrame];
		SpriteWrap spr = SpriteFactory.getInstance().getSprite(frame.getSprId());
		spr.setFrame(frame.getFrameId());
		spr.setPosition((int) (posX - offsetX), (int) (posY - offsetY));
		spr.paint(g);
	}

	/**
	 * 位置を指定
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(double x, double y) {
		posX = x;
		posY = y;
	}

	public double getPositionX() {
		return posX;
	}

	public double getPositionY() {
		return posY;
	}

	/**
	 * あたり判定ボックスの配列を取得
	 * 
	 * @return
	 */
	public HitBox[] getHitbox() {
		AnimeFrame frame = anime[currentFrame];
		return frame.getHitbox();
	}

	/**
	 * アニメの横幅取得
	 * 
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public int getWidth() throws Exception {
		SpriteWrap spr = null;
		AnimeFrame frame = anime[currentFrame];
		spr = SpriteFactory.getInstance().getSprite(frame.getSprId());
		return spr.getWidth();
	}

	/**
	 * アニメの縦幅取得
	 * 
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public int getHeight() throws Exception {
		SpriteWrap spr;
		AnimeFrame frame = anime[currentFrame];
		spr = SpriteFactory.getInstance().getSprite(frame.getSprId());
		return spr.getHeight();
	}

	/**
	 * Animation同士の当たり判定をチェックします。
	 */
	public boolean isTouching(Animation other) {

		HitBox[] hits1 = getHitbox();
		HitBox[] hits2 = other.getHitbox();
		if (hits1 == null || hits2 == null) {
			return false;
		}
		for (int i = 0; i < hits1.length; i++) {
			for (int j = 0; j < hits2.length; j++) {
				if (hits1[i] == null || hits2[j] == null) {
					return false;
				}
				if ((getPosX() + hits1[i].getX() < other.getPosX()
						+ hits2[j].getX() + hits2[j].getWidth())
						&& (this.getPosX() + hits1[i].getX()
								+ hits1[i].getWidth() > other.getPosX()
								+ hits2[j].getX())
						&& (this.getPosY() + hits1[i].getY() < other.getPosY()
								+ hits2[j].getY() + hits2[j].getHeight())
						&& (this.getPosY() + hits1[i].getY()
								+ hits1[i].getHeight() > other.getPosY()
								+ hits2[j].getY())) {
					other.setLastHitBox(hits2[j]);
					setLastHitBox(hits1[i]);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 右端がほかのエンティティと重なっているかチェックする
	 * 
	 * @param other
	 */
	public boolean chkTouchingRight(Animation other) throws Exception {

		HitBox[] hits1 = getHitbox();
		if (hits1 == null) {
			return false;
		}
		for (int i = 0; i < hits1.length; i++) {
			int x = (int) (this.posX) + hits1[i].getX() + hits1[i].getWidth();
			int y = (int) (this.posY) + hits1[i].getY();
			int toY = y + hits1[i].getY() + hits1[i].getHeight();

			inner: while (true) {
				// チェック
				if (other.chkTouchingPoint(x, y)) {
					lastHitBox = hits1[i];
					// 本当に右にあるものか？
					if (hits1[i].getCenterX((int) getPosX()) <= other
							.getLastHitBox().getCenterX((int) other.getPosX())) {
						return true;
					}
				}
				if (y == toY) {
					break inner;
				}
				y += other.getHeight();
				if (y > toY) {
					y = toY;
				}
			}
		}
		return false;
	}

	/**
	 * 左端がほかのエンティティと重なっているかチェックする
	 * 
	 * @param other
	 */
	public boolean chkTouchingLeft(Animation other) throws Exception {
		HitBox[] hits1 = getHitbox();
		if (hits1 == null) {
			return false;
		}
		for (int i = 0; i < hits1.length; i++) {
			int x = (int) this.posX + hits1[i].getX();
			int y = (int) this.posY + hits1[i].getY();
			int toY = y + hits1[i].getY() + hits1[i].getHeight();

			inner: while (true) {
				// チェック
				if (other.chkTouchingPoint(x, y)) {
					lastHitBox = hits1[i];
					// 本当に左にあるものか？
					if (hits1[i].getCenterX((int) getPosX()) >= other
							.getLastHitBox().getCenterX((int) other.getPosX())) {
						return true;
					}
				}
				if (y == toY) {
					break inner;
				}
				y += other.getHeight();
				if (y > toY) {
					y = toY;
				}
			}
		}
		return false;
	}

	/**
	 * 上端がほかのエンティティと重なっているかチェックする
	 * 
	 * @param other
	 */
	public boolean chkTouchingTop(Animation other) throws Exception {
		HitBox[] hits1 = getHitbox();
		if (hits1 == null) {
			return false;
		}
		for (int i = 0; i < hits1.length; i++) {
			int x = (int) this.getPosX() + hits1[i].getX();
			int y = (int) this.getPosY() + hits1[i].getY();
			int toX = x + hits1[i].getX() + hits1[i].getWidth();

			inner: while (true) {
				// チェック
				if (other.chkTouchingPoint(x, y)) {
					lastHitBox = hits1[i];
					// 本当に上にあるものか？
					if (hits1[i].getCenterY((int) getPosY()) >= other.lastHitBox
							.getCenterY((int) other.getPosY())) {
						return true;
					}
				}
				if (x == toX) {
					break inner;
				}
				x += other.getWidth();
				if (x > toX) {
					x = toX;
				}
			}
		}
		return false;
	}

	/**
	 * 下端がほかのエンティティと重なっているかチェックする
	 * 
	 * @param other
	 */
	public boolean chkTouchingBottom(Animation other) throws Exception {
		HitBox[] hits1 = getHitbox();
		if (hits1 == null) {
			return false;
		}
		for (int i = 0; i < hits1.length; i++) {
			int x = (int) this.getPosX() + hits1[i].getX();
			int y = (int) (this.getPosY()) + hits1[i].getY()
					+ hits1[i].getHeight();
			int toX = x + hits1[i].getX() + hits1[i].getWidth();

			inner: while (true) {
				// チェック
				if (other.chkTouchingPoint(x, y)) {
					lastHitBox = hits1[i];
					if (hits1[i].getCenterY((int) getPosY()) <= other.lastHitBox
							.getCenterY((int) other.getPosY())) {
						return true;
					}
				}
				if (x == toX) {
					break inner;
				}
				x += other.getWidth();
				if (x > toX) {
					x = toX;
				}
			}
		}
		return false;
	}

	/**
	 * 指定の座標がエンティティの内側にあるかをチェックする
	 * 
	 * @param x
	 * @param y
	 */
	public boolean chkTouchingPoint(int x, int y) {
		HitBox[] hits = getHitbox();

		for (int i = 0; i < hits.length; i++) {
			if (x >= this.getPosX() + hits[i].getX()
					&& x <= this.getPosX() + hits[i].getX()
							+ hits[i].getWidth()) {
				if (y >= this.getPosY() + hits[i].getY()
						&& y <= this.getPosY() + hits[i].getY()
								+ hits[i].getHeight()) {
					return true;
				}
			}
		}

		return false;
	}

	private synchronized double getPosX() {
		return posX;
	}

	private synchronized double getPosY() {
		return posY;
	}

	private HitBox getLastHitBox() {
		return lastHitBox;
	}

	private void setLastHitBox(HitBox lastHitBox) {
		this.lastHitBox = lastHitBox;
	}

	public boolean isEnd() {
		return end;
	}

	/**
	 * セグメントの登録をリセット
	 */
	public void initSegments() {
		segmentNum = 0;
	}

	/**
	 * セグメントを登録
	 * 
	 * @param segment
	 */
	public void addSegment(int segment) {
		if (segmentNum >= segments.length) {
			return;
		}
		segments[segmentNum] = segment;
		segmentNum++;
	}

	public int getAnimeId() {
		return animeId;
	}

	public void setPositionX(double posX) {
		this.posX = posX;
	}

	public void setPositionY(double posY) {
		this.posY = posY;
	}
}
