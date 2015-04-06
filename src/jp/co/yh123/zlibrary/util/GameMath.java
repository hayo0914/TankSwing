package src.jp.co.yh123.zlibrary.util;

public class GameMath {

	private static double[] radian = null;

	private static double[] cos = null;

	private static double[] sin = null;

	public static int[][] atan2Deg = null;

	// private static int[] directionFromDeg = null;
	//
	// public static int[] DegFromDirection = null;

	public static double cos(int degree) {
		return cos[degree];
	}

	public static double sin(int degree) {
		return sin[degree];
	}

	public static double degToRad(int degree) {
		return radian[degree];
	}

	/**
	 * �p�x��0�`360�x�̊Ԃɕϊ����ĕԋp
	 * 
	 * @param degree
	 * @return
	 */
	public static int getDegree0To359(int degree) {
		while (degree < 0) {
			degree += 360;
		}
		while (degree >= 360) {
			degree -= 360;
		}
		return degree;
	}

	public static double abs(double param) {
		if (param < 0) {
			return param * -1;
		}
		return param;
	}

	public static int abs(int param) {
		if (param < 0) {
			return param * -1;
		}
		return param;
	}

	public static int min(int a, int b) {
		if (a > b) {
			return b;
		} else {
			return a;
		}
	}

	public static int max(int a, int b) {
		if (a > b) {
			return a;
		} else {
			return b;
		}
	}

	/**
	 * �p�x�f�[�^��256�{�ŕێ�����
	 */
	public static void initRadianData() {
		if (radian == null)
			radian = new double[361];
		if (cos == null)
			cos = new double[361];
		if (sin == null)
			sin = new double[361];
		for (int i = 0; i < radian.length; i++) {
			radian[i] = ((Math.toRadians(i)));
			cos[i] = ((Math.cos(radian[i])));
			sin[i] = ((Math.sin(radian[i])));
		}

		if (atan2Deg == null) {
			atan2Deg = new int[51][51];
		}

		for (int i = 0; i < atan2Deg.length; i++) {
			for (int j = 0; j < atan2Deg[i].length; j++) {

				atan2Deg[i][j] = (int) Math.toDegrees(atan2(i, j));

			}
		}

		// if (directionFromDeg == null) {
		// directionFromDeg = new int[72];
		// for (int i = 0; i < directionFromDeg.length; i++) {
		// directionFromDeg[i] = getDirectionFromDegree(i * 5);
		// }
		// }
		// if (DegFromDirection == null) {
		// DegFromDirection = new int[8];
		// for (int i = 0; i < DegFromDirection.length; i++) {
		// DegFromDirection[i] = getDegreeFromDirection(i);
		// }
		// }
	}

	private static double[] tanTanble = new double[360];
	static {
		for (int i = 0; i < tanTanble.length; i++) {
			// tan�e�[�u���̏�����(�͈͂�90�x)
			double tan = Math.tan(((0.5f / tanTanble.length) * i) * Math.PI);
			tanTanble[i] = tan;
		}
	}

	public static double atan2(double x, double y) {
		double absX = GameMath.abs(x);// �X�O�x�Ōv�Z����̂Ő�Βl�ɕϊ�
		double absY = GameMath.abs(y);
		int angle = tanTanble.length;// �p�x
		if (absX == 0)
			angle = tanTanble.length;// �X�O�x�̏ꍇ
		else if (absY == 0)
			angle = 0;// �O�x�̏ꍇ
		else {
			double tan = (double) (absY / absX);// �^���W�F���g�����߂�
			for (int i = 0; i < tanTanble.length - 1; i++) {
				if (tan >= tanTanble[i] && tan <= tanTanble[i + 1]) {// �p�x�e�[�u���̎w��͈͓��Ɏ��܂��Ă�����
					angle = i;// �p�x����
					break;
				}
			}
		}
		if (y >= 0 && x >= 0)
			angle += 0;// 0~90//���W�ϊ�
		else if (y >= 0 && x <= 0)
			angle = (tanTanble.length - angle) + (tanTanble.length * 1);// 90~180
		else if (y <= 0 && x <= 0)
			angle += (tanTanble.length * 2);// 180^270
		else if (y <= 0 && x >= 0)
			angle = (tanTanble.length - angle) + (tanTanble.length * 3);// 270~360
		double result;// ���W�A���ɕϊ�
		result = (double) (angle) / (double) (tanTanble.length * 4);
		result = result * (2 * Math.PI);
		return Math.toDegrees(result);// degree�ŕԋp
	}

	public static int SEIDO = 50;

	public static int getDegAtan2Tbl(int x, int y) {

		try {

			int wkX = x;
			int wkY = y;
			wkX = GameMath.abs(wkX);
			wkY = GameMath.abs(wkY);
			if (wkX >= SEIDO) {
				wkX /= 10;
			}
			if (wkY >= SEIDO) {
				wkY /= 10;
			}

			if (x < 0 && y >= 0) {
				return ((90 - atan2Deg[wkX][wkY]) * 2) + atan2Deg[wkX][wkY];
			}
			if (x >= 0 && y < 0) {
				return atan2Deg[wkX][wkY] + (360 - (atan2Deg[wkX][wkY] * 2));
			}
			if (x < 0 && y < 0) {
				return atan2Deg[wkX][wkY] + 180;
			}
			return atan2Deg[wkX][wkY];
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("�G���[�F" + x + ":" + y);
			return 0;
		}
	}

	// /**
	// * x,y�ɑ΂���Radian�̊p�x���v�Z
	// *
	// * @param x
	// * @param y
	// * @return
	// */
	// public static double atan2(double x, double y) {
	// double absX = Math.abs(x);// �X�O�x�Ōv�Z����̂Ő�Βl�ɕϊ�
	// double absY = Math.abs(y);
	// int angle = tanTanble.length;// �p�x
	// if (absX == 0)
	// angle = tanTanble.length;// �X�O�x�̏ꍇ
	// else if (absY == 0)
	// angle = 0;// �O�x�̏ꍇ
	// else {
	// double tan = (double) (absY / absX);// �^���W�F���g�����߂�
	// for (int i = 0; i < tanTanble.length - 1; i++) {
	// if (tan >= tanTanble[i] && tan <= tanTanble[i + 1]) {//
	// �p�x�e�[�u���̎w��͈͓��Ɏ��܂��Ă�����
	// angle = i;// �p�x����
	// break;
	// }
	// }
	// }
	// if (y >= 0 && x >= 0)
	// angle += 0;// 0~90//���W�ϊ�
	// else if (y >= 0 && x <= 0)
	// angle = (tanTanble.length - angle) + (tanTanble.length * 1);// 90~180
	// else if (y <= 0 && x <= 0)
	// angle += (tanTanble.length * 2);// 180^270
	// else if (y <= 0 && x >= 0)
	// angle = (tanTanble.length - angle) + (tanTanble.length * 3);// 270~360
	// double result;// ���W�A���ɕϊ�
	// result = (double) (angle) / (double) (tanTanble.length * 4);
	// result = result * (2 * Math.PI);
	// return result;
	// }
	//
	// /**
	// * tan���i�[���Ă���e�[�u���B�z��̃T�C�Y��ς���Ή𑜓x���ς��
	// */
	// private static double[] tanTanble = new double[360];
	// static {
	// for (int i = 0; i < tanTanble.length; i++) {
	// // tan�e�[�u���̏�����(�͈͂�90�x)
	// double tan = Math.tan(((0.5f / tanTanble.length) * i) * Math.PI);
	// tanTanble[i] = tan;
	// }
	// }

	// /**
	// * �p�x����8�����ɕϊ�
	// *
	// * @param degree
	// * @return
	// */
	// public static int getDirectionFromDegree(int degree) {
	// if (degree >= 338 || degree <= 23) {
	// return 2;
	// } else if (degree >= 23 && degree <= 68) {
	// return 1;
	// } else if (degree >= 68 && degree <= 113) {
	// return 0;
	// } else if (degree >= 113 && degree <= 158) {
	// return 7;
	// } else if (degree >= 158 && degree <= 203) {
	// return 6;
	// } else if (degree >= 203 && degree <= 248) {
	// return 5;
	// } else if (degree >= 248 && degree <= 293) {
	// return 4;
	// } else if (degree >= 293 && degree <= 338) {
	// return 3;
	// }
	// // System.out.println("�ւ�Ȃ̂���" + degree);
	// return 0;
	// }

	// /**
	// * 8��������p�x�ɕϊ�
	// *
	// * @param degree
	// * @return
	// */
	// public static int getDegreeFromDirection(int direction) {
	//
	// // �ق�Ƃ͉���������
	// switch (direction) {
	// case 2:
	// return 0;
	// case 3:
	// return 315;
	// case 4:
	// return 270;
	// case 5:
	// return 225;
	// case 6:
	// return 180;
	// case 7:
	// return 135;
	// case 0:
	// return 90;
	// case 1:
	// return 45;
	// default:
	// // System.out.println("�ւ�Ȃ̂���" + direction);
	// return 0;
	// }
	// }
}
