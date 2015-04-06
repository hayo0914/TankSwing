package src.jp.co.yh123.zlibrary.platform;

public class SpriteWrap {

	private int length = 0;

	private ImageWrap img = null;

	private int width = 0;

	private int height = 0;

	private int[] x = null;

	private int[] y = null;

	private int posX = 0;

	private int posY = 0;

	private int frame = 0;

	public SpriteWrap(ImageWrap img, int width, int height) {

		this.img = img;
		this.width = width;
		this.height = height;

		int wkW = 0;
		int wkH = 0;

		int length = 0;

		int wk = (img.getWidth() * img.getHeight()) / (width * height);

		x = new int[wk];
		y = new int[wk];

		h: while (true) {
			if (wkH >= img.getHeight()) {
				break h;
			}
			w: while (true) {
				if (wkW >= img.getWidth()) {
					break w;
				}
				x[length] = wkW;
				y[length] = wkH;
				wkW += width;
				length++;
			}
			wkH += height;
			wkW = 0;
		}
		this.length = length;
	}

	public void nextFrame() {
		frame++;
		if (frame >= length) {
			frame = 0;
		}
	}

	public void setPosition(int x, int y) {
		this.posX = x;
		this.posY = y;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

	public int getFrame() {
		return frame;
	}

	public void paint(GameGraphic g) {
		g.drawImageRegion(img, posX, posY, x[frame], y[frame], width, height);
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void dispose() {
		if (img != null)
			img.dispose();
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
