package src.jp.co.yh123.zlibrary.collision;

public class CollisionSegmentGroup {

	// ���̃O���[�v�̃Z�O�����g���i�[����z��
	private CollisionSegment[] segments = null;

	// �P�Z�O�����g�̉��T�C�Y
	private int segmentWidth = 0;

	// �P�Z�O�����g�̏c�T�C�Y
	private int segmentHeight = 0;

	private int segmentNum = 10;

	/**
	 * @param maxObjectNum
	 *            �P�Z�O�����g�Ɋi�[����ő�I�u�W�F�N�g��
	 * @param width
	 *            �}�b�v�̉��T�C�Y
	 * @param height
	 *            �}�b�v�̏c�T�C�Y
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
	 * �Z�O�����g�f�[�^�����Z�b�g����
	 */
	public void initSegments() {
		for (int i = 0; i < segments.length; i++) {
			segments[i].initObjList();
		}
	}

	// /**
	// * �����蔻��ɗ]�T���������邽�߁A���ɑ���
	// */
	// private final static int extend = 0;

	/**
	 * �G���e�B�e�B���Z�O�����g�ɓo�^����
	 * 
	 * @param ent
	 */
	synchronized public void registryEntity(ICollisionCheckEntity anime) {

		// ���[�N�G���A
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

		// ����
		wk1 = (int) ((x / segmentWidth) + (((y / segmentHeight)) * segmentNum));
		if (wk1 < segments.length && wk1 >= 0) {
			segments[wk1].addShObject(anime);
			anime.addCollisionSegment(segments[wk1]);
		} else {
			return;
		}

		// �E��
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

		// �Ԃ�S�������.
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
	 * �Փ˔�����s��<br>
	 * �Փ˂��Ă����ꍇ�A�����̃G���e�B�e�B��HIT���R�[�������<br>
	 * �p�t�H�[�}���X�򉻖h�~�̂��߁A1��̃`�F�b�N��1HIT�܂ł����`�F�b�N���Ȃ�
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
				// �����蔻����`�F�b�N
				if (ent.getAnime().isTouching(other.getAnime())) {
					// ���I�u�W�F�N�g�̏Փˏ������R�[��
					ent.onHit(other);
					other.onHit(ent);
					return true;
				}
			}
		}
		return false;
	}
}
