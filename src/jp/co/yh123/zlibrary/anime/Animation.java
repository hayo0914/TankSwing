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
	 * �����蔻��
	 **************************************************************************/
	private HitBox lastHitBox = null;

	// �Z�O�����g
	public int[] segments = new int[50];

	// �o�^�Z�O�����g�̐�
	public int segmentNum = 0;

	Animation(int frameChangeInterval, int animeId, int maxLoopCount) {
	}

	/**
	 * �`�悷��A�j�����w�肵�A�A�j�������������܂��B
	 * 
	 * @param frameChangeInterval
	 *            ���t���[�����ƂɃA�j���𓮂��������w�肵�܂��B-1�Ȃ�t���[���͌Œ�ł��B
	 * @param animeId
	 *            �A�j���ԍ����w�肵�܂��B-1�̏ꍇ�̓A�j�����Ȃ����̂Ƃ݂Ȃ��܂��B<br>
	 *            �����I�ɂ́A-1�̏ꍇ��Exception���X���[���܂��B
	 * @param maxLoopCount
	 *            �A�j��������J��Ԃ��ĕ`�悷�邩���w�肵�܂��B-1�̏ꍇ�A�����ɕ`�悵�܂��B
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
	 * �A�j���̍X�V�����܂��B���t���[���Ăяo���Ă��������B
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
	 * �A�j���̃t���[�����X�V
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
	 * �A�j���[�V���������������܂��B
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
	 * �I�t�Z�b�g�t�`�悵�܂��B
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
	 * �ʒu���w��
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
	 * �����蔻��{�b�N�X�̔z����擾
	 * 
	 * @return
	 */
	public HitBox[] getHitbox() {
		AnimeFrame frame = anime[currentFrame];
		return frame.getHitbox();
	}

	/**
	 * �A�j���̉����擾
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
	 * �A�j���̏c���擾
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
	 * Animation���m�̓����蔻����`�F�b�N���܂��B
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
	 * �E�[���ق��̃G���e�B�e�B�Əd�Ȃ��Ă��邩�`�F�b�N����
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
				// �`�F�b�N
				if (other.chkTouchingPoint(x, y)) {
					lastHitBox = hits1[i];
					// �{���ɉE�ɂ�����̂��H
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
	 * ���[���ق��̃G���e�B�e�B�Əd�Ȃ��Ă��邩�`�F�b�N����
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
				// �`�F�b�N
				if (other.chkTouchingPoint(x, y)) {
					lastHitBox = hits1[i];
					// �{���ɍ��ɂ�����̂��H
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
	 * ��[���ق��̃G���e�B�e�B�Əd�Ȃ��Ă��邩�`�F�b�N����
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
				// �`�F�b�N
				if (other.chkTouchingPoint(x, y)) {
					lastHitBox = hits1[i];
					// �{���ɏ�ɂ�����̂��H
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
	 * ���[���ق��̃G���e�B�e�B�Əd�Ȃ��Ă��邩�`�F�b�N����
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
				// �`�F�b�N
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
	 * �w��̍��W���G���e�B�e�B�̓����ɂ��邩���`�F�b�N����
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
	 * �Z�O�����g�̓o�^�����Z�b�g
	 */
	public void initSegments() {
		segmentNum = 0;
	}

	/**
	 * �Z�O�����g��o�^
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
