package src.jp.co.yh123.zlibrary.collision;

public class HitBox {

	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;

	public HitBox(int x, int y, int width, int height, int damageflag,
			int damageAnime) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public double getCenterX(int posX) {
		return posX + x + width / 2;
	}

	public double getCenterY(int posY) {
		return posY + y + height / 2;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
