package src.jp.co.yh123.zlibrary.util;

public class IntPosition {

	public IntPosition() {
		this.cellX = 0;
		this.cellY = 0;
	}

	public IntPosition(int cellX, int cellY) {
		this.cellX = cellX;
		this.cellY = cellY;
	}

	public int getX() {
		return cellX;
	}

	public void setX(int cellX) {
		this.cellX = cellX;
	}

	public int getY() {
		return cellY;
	}

	public void setY(int cellY) {
		this.cellY = cellY;
	}

	private int cellX = 0;
	private int cellY = 0;

}
