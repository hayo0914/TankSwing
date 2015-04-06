package src.jp.co.yh123.zlibrary.platform;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.net.URL;

public class ImageWrap implements ImageObserver {

	private Image bufimg = null;

	private int width = -1;

	private int height = -1;

	public static ImageWrap createImage(String url) throws Exception {
		ImageWrap img = new ImageWrap();
		URL filename = img.getClass().getResource(url);
		img.setImg(Toolkit.getDefaultToolkit().createImage(filename));
		while (img.width == -1 || img.height == -1) {
			img.width = img.bufimg.getWidth(img);
			img.height = img.bufimg.getHeight(img);
			Thread.sleep(5);
		}

		return img;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void dispose() {
		if (getImg() != null) {
			setImg(null);
		}
	}

	public Image getImg() {
		return bufimg;
	}

	public void setImg(Image bufimg) {
		this.bufimg = bufimg;
	}

	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		this.width = width;
		this.height = height;
		return false;
	}

}
