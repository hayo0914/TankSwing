package src.jp.co.yh123.tank.griddungeon;

import src.jp.co.yh123.tank.map.Map;
import src.jp.co.yh123.zlibrary.util.IntPosition;
import src.jp.co.yh123.zlibrary.util.GameMath;

abstract class AbstractGrid {

	public static final int SHAPE_SQUARE = 0;

	public static final int SHAPE_RANDOM = 1;

	public static final int Lair = 0;

	static final int NO_DOOR = 0;

	static final int NEED_DOOR = 1;

	private Map _map;

	private int _gridWidth = 0;

	private int _gridHeight = 0;

	private int _x = 0;

	private int _y = 0;

	private int _toX = 0;

	private int _toY = 0;

	boolean _isToBeStartGrid = true;// �X�^�[�g�n�_�ɂȂ蓾��

	GridDungeonGenerator _param = null;
	GridDungeonGenerator.GridsParameter _gridParam = null;

	protected static IntPosition _position = new IntPosition();

	/**
	 * @return the x
	 */
	public int getX() {
		return _x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return _y;
	}

	/**
	 * @return the toX
	 */
	public int getToX() {
		return _toX;
	}

	/**
	 * @return the toY
	 */
	public int getToY() {
		return _toY;
	}

	AbstractGrid(int x, int y, int toX, int toY, GridDungeonGenerator param,
			GridDungeonGenerator.GridsParameter gridParam, Map map) {
		_x = x;
		_y = y;
		_toX = toX;
		_toY = toY;
		_gridWidth = toX - x;
		_gridHeight = toY - y;
		_map = map;
		_param = param;
		_gridParam = gridParam;
	}

	int getWidth() {
		return _gridWidth;
	}

	int getHeight() {
		return _gridHeight;
	}

	int connectNum = 0;

	/* �����̎�ނ��Ƃɂ��ƂȂ�ݒ肪�ς�� */
	int door = NEED_DOOR;
	boolean isConnectable = true;

	void setDoor(int x, int y) throws Exception {
		// ���ɂł���ꍇ�Ƃł��Ȃ��ꍇ������B
		// TODO:���̎��
		if (!_map.get(x, y).hasMapObject()) {
			_map.setMapObject(_param._objDoorId, x, y);
		}
	}

	void createGoal() {
		// TODO:����
	}

	/**
	 * ����ɕʂ̕����Ƃ̐ڑ���ǉ��ł��邩�ۂ�
	 * 
	 * @return
	 */
	boolean isConnectable() {
		return isConnectable;
	}

	abstract void create() throws Exception;

	/**
	 * �l�p�������쐬
	 * 
	 * @param grid
	 * @throws Exception
	 */
	protected void maskSquare(int floor, int roomSizeMin) throws Exception {

		int lx = _x + 1;
		int ly = _y + 1;
		int hx = _x + _gridWidth - 2;
		int hy = _y + _gridHeight - 2;

		int max = hx - lx;

		int roomWidth = _param._rv.getRandomInt(roomSizeMin, hx - lx);
		if (roomWidth > max) {
			roomWidth = max;
		}
		int roomHeight = _param._rv.getRandomInt(roomSizeMin, hy - ly);
		if (roomHeight > max) {
			roomHeight = max;
		}

		int offsetX = hx - lx - roomWidth;
		int offsetY = hy - ly - roomHeight;

		offsetX = _param._rv.getRandomInt(0, offsetX);
		offsetY = _param._rv.getRandomInt(0, offsetY);

		fill(floor, lx + offsetX, ly + offsetY, roomWidth, roomHeight);

	}

	/**
	 * �����_���`��
	 * 
	 * @param grid
	 * @throws Exception
	 */
	protected void maskRandom(int floor) throws Exception {

		int wkX = _param._rv.getRandomInt(_x + 2, _toX - 2);
		int wkY = _param._rv.getRandomInt(_y + 2, _toY - 2);

		int countMax = (int) ((_gridWidth) * (_gridHeight) * 0.6d);
		int count = 0;

		while (count < countMax) {
			_map.setMapTile(floor, wkX, wkY);
			int offX = 0;
			int offY = 0;
			switch (_param._rv.getRandomInt(0, 4)) {
			case 0:
				offX = 1;
				break;
			case 1:
				offX = -1;
				break;
			case 2:
				offY = 1;
				break;
			case 3:
				offY = -1;
				break;
			}
			if (wkX + offX >= _toX - 2 || wkX + offX <= _x + 2) {
				offX *= -1;
			}
			if (wkY + offY >= _toY - 2 || wkY + offY <= _y + 2) {
				offY *= -1;
			}
			wkX += offX;
			wkY += offY;
			count++;
		}

	}

	/**
	 * �O���b�h���w��̃^�C���Ŗ��߂�
	 * 
	 * @param type
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @throws Exception
	 */
	protected void fill(int type, int x, int y, int width, int height)
			throws Exception {
		int toX = x + width;
		int toY = y + height;
		for (int i = x; i <= toX; i++) {
			for (int j = y; j <= toY; j++) {
				_map.setMapTile(type, i, j);
			}
		}
	}

	static void connect(Map map, GridDungeonGenerator param, AbstractGrid g1,
			AbstractGrid g2, boolean isHorizontal) throws Exception {
		g1.connectNum++;
		g2.connectNum++;

		// �O���b�h�̕��בւ�
		// ���܂��͏�ɂ���O���b�h��g1
		if (g1.getX() > g2.getX() || g1.getY() > g2.getY()) {
			AbstractGrid swapG = g2;
			g2 = g1;
			g1 = swapG;
		}

		// �ڑ��J�n�n�_�T��
		// �O���b�h�̒[�ɒʘH�����ƕs����N���邩������Ȃ��_�ɒ���

		// TODO:�v�C�����N�_���A���X�����ɂȂ��Ă���ꏊ�łȂ���΂Ȃ�Ȃ��B
		// �łȂ��ƁA�ςȂƂ��납��ʘH���͂��������Ă��܂��@�E�E
		IntPosition pos = getRandomPointFloor(map, param, g1.getX() + 1, g1
				.getY() + 1, g1.getToX() - 1, g1.getToY() - 1);
		int x1 = pos.getX();
		int y1 = pos.getY();

		IntPosition pos2 = getRandomPointFloor(map, param, g2.getX() + 1, g2
				.getY() + 1, g2.getToX() - 1, g2.getToY() - 1);
		int x2 = pos2.getX();
		int y2 = pos2.getY();

		if (isHorizontal) {
			// �ǒT��g1(���ɂ���O���b�h�j
			int wkX1 = g1.getToX() - 1;
			while (wkX1 > x1) {
				if (!map.get(wkX1 - 1, y1).isWallOrBarrier()) {
					break;
				}
				wkX1--;
			}
			x1 = wkX1;

			// �ǒT��g2
			int wkX2 = g2.getX();
			while (wkX2 < x2) {
				if (!map.get(wkX2 + 1, y2).isWallOrBarrier()) {
					break;
				}
				wkX2++;
			}
			x2 = wkX2;

			// �גʂ��̏ꍇ�AY�������킹��
			if (x1 + 1 == x2) {
				if (param._rv.getRandomInt(0, 2) < 1) {
					y2 = y1;
				} else {
					y1 = y2;
				}
			}

			map.setMapTile(param._tileCorridor, x1, y1);
			map.setMapTile(param._tileCorridor, x2, y2);
			if (param._rv.getRandomInt(0, 100) <= param._doorRate) {
				g1.setDoor(x1, y1);
				g2.setDoor(x2, y2);
			}
			connectH(map, param, x1, y1, x2, y2);
		} else {
			// �ǒT��g1�i��ɂ���O���b�h�j
			int wkY1 = g1.getToY() - 1;
			while (wkY1 > y1) {
				if (!map.get(x1, wkY1 - 1).isWallOrBarrier()) {
					break;
				}
				wkY1--;
			}
			y1 = wkY1;

			// �ǒT��g2
			int wkY2 = g2.getY();
			while (wkY2 < y2) {
				if (!map.get(x2, wkY2 + 1).isWallOrBarrier()) {
					break;
				}
				wkY2++;
			}
			y2 = wkY2;

			// �גʂ��̏ꍇ�AX�������킹��
			if (y1 + 1 == y2) {
				if (param._rv.getRandomInt(0, 2) < 1) {
					x2 = x1;
				} else {
					x1 = x2;
				}
			}
			map.setMapTile(param._tileCorridor, x1, y1);
			map.setMapTile(param._tileCorridor, x2, y2);
			if (param._rv.getRandomInt(0, 100) <= param._doorRate) {
				g1.setDoor(x1, y1);
				g2.setDoor(x2, y2);
			}
			connectV(map, param, x1, y1, x2, y2);
		}
	}

	/**
	 * �͈͓��ŕǂłȂ��ꏊ�������_���Ɏ擾���APosition�Ɋi�[���ĕԋp<br>
	 * �Ƃ肤��l�͈̔͂�fromX�`toX-1�AfromY�`toY-1
	 * 
	 * @param pos
	 * @param fromX
	 * @param fromY
	 * @param toX
	 * @param toY
	 */
	protected static IntPosition getRandomPointFloor(Map map,
			GridDungeonGenerator param, int fromX, int fromY, int toX, int toY)
			throws Exception {

		int centerX = param._rv.getRandomInt(fromX, toX);
		int centerY = param._rv.getRandomInt(fromY, toY);

		// �E���̉Q������Ɍ������Ă����B�ŏ��Ɍ��������ǂłȂ��ꏊ��ԋp
		int hankei = 0;
		int maxHankei = (GameMath.max(toX - fromX, toY - fromY));
		while (hankei <= maxHankei) {
			int wkX = centerX - hankei;
			int wkY = centerY - hankei;
			if (wkX < fromX) {
				wkX = fromX;
			}
			if (wkY < fromY) {
				wkY = fromY;
			}
			lp: for (int i = wkX; i <= centerX + hankei; i++) {
				if (i >= toX) {
					break lp;
				}
				if (!map.get(i, wkY).isWallOrBarrier()) {
					return new IntPosition(i, wkY);
				}
			}
			wkX = centerX + hankei;
			wkY = centerY - hankei;
			if (wkX >= toX) {
				wkX = toX - 1;
			}
			if (wkY < fromY) {
				wkY = fromY;
			}
			lp: for (int i = wkY; i <= centerY + hankei; i++) {
				if (i >= toY) {
					break lp;
				}
				if (!map.get(wkX, i).isWallOrBarrier()) {
					return new IntPosition(wkX, i);
				}
			}
			wkX = centerX + hankei;
			wkY = centerY + hankei;
			if (wkX >= toX) {
				wkX = toX - 1;
			}
			if (wkY >= toY) {
				wkY = toY - 1;
			}
			lp: for (int i = wkX; i >= centerX - hankei; i--) {
				// System.out.println(i + ":" + wkY);
				if (i < fromX) {
					// System.out.println("break");
					break lp;
				}
				if (!map.get(i, wkY).isWallOrBarrier()) {
					return new IntPosition(i, wkY);
				}
			}
			wkX = centerX - hankei;
			wkY = centerY + hankei;
			if (wkX < fromX) {
				wkX = fromX;
			}
			if (wkY >= toY) {
				wkY = toY - 1;
			}
			lp: for (int i = wkY; i >= centerY - hankei; i--) {
				if (i < fromY) {
					break lp;
				}
				if (!map.get(wkX, i).isWallOrBarrier()) {
					return new IntPosition(wkX, i);
				}
			}

			hankei++;
		}
		throw new Exception("grid�ɂ��ׂłȂ��ӏ����Ȃ�");

	}

	IntPosition getRandomPositionIsFloor() throws Exception {
		return getRandomPointFloor(_map, _param, _x, _y, _x + _gridWidth, _y
				+ _gridHeight);
	}

	private static void connectH(Map map, GridDungeonGenerator param, int x1,
			int y1, int x2, int y2) throws Exception {

		int randX = 0;
		int width = GameMath.abs(x2 - x1);

		if (width < 2) {
			return;
		} else {
			randX = param._rv.getRandomInt(1, width);
		}
		int cornerX = GameMath.min(x1, x2) + randX;
		int moveX = 0;
		int moveY = 0;
		if (x1 <= x2) {
			moveX = 1;
		} else {
			moveX = -1;
		}
		if (y1 <= y2) {
			moveY = 1;
		} else {
			moveY = -1;
		}

		int currX = x1;
		int currY = y1;
		// �ʘH1
		while (currX != cornerX) {
			currX += moveX;
			if (map.get(currX, currY).isWallOrBarrier()) {
				map.setMapTile(param._tileCorridor, currX, currY);
			}
		}
		// �ʘH2(�c)
		while (currY != y2) {
			currY += moveY;
			if (map.get(currX, currY).isWallOrBarrier()) {
				map.setMapTile(param._tileCorridor, currX, currY);
			}
		}
		// �ʘH3
		while (currX != x2 - 1) {
			currX += moveX;
			if (map.get(currX, currY).isWallOrBarrier()) {
				map.setMapTile(param._tileCorridor, currX, currY);
			}
		}
	}

	private static void connectV(Map map, GridDungeonGenerator param, int x1,
			int y1, int x2, int y2) throws Exception {

		int randY = 0;
		int height = GameMath.abs(y2 - y1);

		if (height < 2) {
			return;
		} else {
			randY = param._rv.getRandomInt(1, height);
		}
		int cornerY = GameMath.min(y1, y2) + randY;
		int moveX = 0;
		int moveY = 0;
		if (x1 <= x2) {
			moveX = 1;
		} else {
			moveX = -1;
		}
		if (y1 <= y2) {
			moveY = 1;
		} else {
			moveY = -1;
		}

		int currX = x1;
		int currY = y1;
		// �ʘH1
		while (currY != cornerY) {
			currY += moveY;
			if (map.get(currX, currY).isWallOrBarrier()) {
				map.setMapTile(param._tileCorridor, currX, currY);
			}
		}
		// �ʘH2(�c)
		while (currX != x2) {
			currX += moveX;
			if (map.get(currX, currY).isWallOrBarrier()) {
				map.setMapTile(param._tileCorridor, currX, currY);
			}
		}
		// �ʘH3
		while (currY != y2 - 1) {
			currY += moveY;
			if (map.get(currX, currY).isWallOrBarrier()) {
				map.setMapTile(param._tileCorridor, currX, currY);
			}
		}
	}
}
