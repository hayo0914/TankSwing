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
	 * 角度を0～360度の間に変換して返却
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
	 * 角度データを256倍で保持する
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
			// tanテーブルの初期化(範囲は90度)
			double tan = Math.tan(((0.5f / tanTanble.length) * i) * Math.PI);
			tanTanble[i] = tan;
		}
	}

	public static double atan2(double x, double y) {
		double absX = GameMath.abs(x);// ９０度で計算するので絶対値に変換
		double absY = GameMath.abs(y);
		int angle = tanTanble.length;// 角度
		if (absX == 0)
			angle = tanTanble.length;// ９０度の場合
		else if (absY == 0)
			angle = 0;// ０度の場合
		else {
			double tan = (double) (absY / absX);// タンジェントを求める
			for (int i = 0; i < tanTanble.length - 1; i++) {
				if (tan >= tanTanble[i] && tan <= tanTanble[i + 1]) {// 角度テーブルの指定範囲内に収まっていたら
					angle = i;// 角度決定
					break;
				}
			}
		}
		if (y >= 0 && x >= 0)
			angle += 0;// 0~90//座標変換
		else if (y >= 0 && x <= 0)
			angle = (tanTanble.length - angle) + (tanTanble.length * 1);// 90~180
		else if (y <= 0 && x <= 0)
			angle += (tanTanble.length * 2);// 180^270
		else if (y <= 0 && x >= 0)
			angle = (tanTanble.length - angle) + (tanTanble.length * 3);// 270~360
		double result;// ラジアンに変換
		result = (double) (angle) / (double) (tanTanble.length * 4);
		result = result * (2 * Math.PI);
		return Math.toDegrees(result);// degreeで返却
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
			System.out.println("エラー：" + x + ":" + y);
			return 0;
		}
	}

	// /**
	// * x,yに対するRadianの角度を計算
	// *
	// * @param x
	// * @param y
	// * @return
	// */
	// public static double atan2(double x, double y) {
	// double absX = Math.abs(x);// ９０度で計算するので絶対値に変換
	// double absY = Math.abs(y);
	// int angle = tanTanble.length;// 角度
	// if (absX == 0)
	// angle = tanTanble.length;// ９０度の場合
	// else if (absY == 0)
	// angle = 0;// ０度の場合
	// else {
	// double tan = (double) (absY / absX);// タンジェントを求める
	// for (int i = 0; i < tanTanble.length - 1; i++) {
	// if (tan >= tanTanble[i] && tan <= tanTanble[i + 1]) {//
	// 角度テーブルの指定範囲内に収まっていたら
	// angle = i;// 角度決定
	// break;
	// }
	// }
	// }
	// if (y >= 0 && x >= 0)
	// angle += 0;// 0~90//座標変換
	// else if (y >= 0 && x <= 0)
	// angle = (tanTanble.length - angle) + (tanTanble.length * 1);// 90~180
	// else if (y <= 0 && x <= 0)
	// angle += (tanTanble.length * 2);// 180^270
	// else if (y <= 0 && x >= 0)
	// angle = (tanTanble.length - angle) + (tanTanble.length * 3);// 270~360
	// double result;// ラジアンに変換
	// result = (double) (angle) / (double) (tanTanble.length * 4);
	// result = result * (2 * Math.PI);
	// return result;
	// }
	//
	// /**
	// * tanを格納しているテーブル。配列のサイズを変えれば解像度が変わる
	// */
	// private static double[] tanTanble = new double[360];
	// static {
	// for (int i = 0; i < tanTanble.length; i++) {
	// // tanテーブルの初期化(範囲は90度)
	// double tan = Math.tan(((0.5f / tanTanble.length) * i) * Math.PI);
	// tanTanble[i] = tan;
	// }
	// }

	// /**
	// * 角度から8方向に変換
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
	// // System.out.println("へんなのきた" + degree);
	// return 0;
	// }

	// /**
	// * 8方向から角度に変換
	// *
	// * @param degree
	// * @return
	// */
	// public static int getDegreeFromDirection(int direction) {
	//
	// // ほんとは下が正しい
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
	// // System.out.println("へんなのきた" + direction);
	// return 0;
	// }
	// }
}
