package src.jp.co.yh123.zlibrary.platform;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * グラフィック、フォントに関連する機能
 * 
 * @author Y.H
 */
public class GameGraphic {

	// 画面横サイズ
	private int _width = 0;

	// 画面縦サイズ
	private int _height = 0;

	// バッファリング用オフスクリーンイメージ。
	// private Image _cvs;

	// 描画対象Graphic
	private Graphics2D _g;

	// 画面描画用Graphic
	// private Graphics _gon;

	// バッファリング用Graphic
	// private Graphics _goff;
	// 拡大縮小用
	public final int STRETCH_QUALITY_HIGH = 2;

	public final int STRETCH_QUALITY_LOW = 1;

	public final int STRETCH_QUALITY_NORMAL = 0;

	public int offsetX = 0;

	public int offsetY = 0;

	/**
	 * 画面サイズをセットします。
	 * 
	 * @param width
	 *            横
	 * @param height
	 *            縦
	 */
	public void setCvs(int width, int height) {

		_width = width;
		_height = height;

		// バッファリング用
		// _cvs = Image.createImage(_width, _height);
		// _goff = _cvs.getGraphics();
	}

	/**
	 * 画面の幅を取得します。
	 * 
	 * @return 画面の幅を返します。
	 */
	public int getWidth() {

		return _width;
	}

	/**
	 * 画面の高さを取得します。
	 * 
	 * @return 画面の高さを返します。
	 */
	public int getHeight() {

		return _height;
	}

	/**
	 * Graphicsインスタンスをセットします。
	 * 
	 * @param g2
	 *            Graphicsインスタンス
	 */
	public void setGraphics(Graphics2D g2) {
		_g = g2;
		// _gon = g2;
		// _g = _gon;
	}

	/**
	 * Graphicsインスタンスを返します。
	 * 
	 * @return Graphicsインスタンス
	 */
	public Graphics2D getGraphics() {

		return _g;
	}

	/**
	 * 描画デバイスに対して、ダブルバッファリングの開始を宣言します。
	 */
	// public void lock() {
	//
	// _g = _goff;
	// }
	/**
	 * 描画デバイスに対して、ダブルバッファリングの終了を宣言します。<br>
	 * メモリを節約する場合はGameCanvasを継承させてflushGraphics()を利用する方がよい。<br>
	 * ただ、こちらの方式であれば横モーション対応が楽。
	 * 
	 * @param b
	 *            強制的にフラッシュするかどうか
	 */
	// public void unlock(boolean b) {
	//
	// if (_g == _goff) {
	// _gon.drawImage(_cvs, 0, 0, Graphics.LEFT | Graphics.TOP);
	// }
	// _g = _gon;
	// }
	// /**
	// * 矩形領域を背景色で塗りつぶします。
	// *
	// * @param x1
	// * 矩形の左上のX座標
	// * @param y1
	// * 矩形の左上のY座標
	// * @param width
	// * 矩形の幅
	// * @param height
	// * 矩形の高さ
	// */
	// public void clearRect(int x1, int y1, int width, int height) {
	//
	// int[] color = { 0, 0, 0 };
	// if (_lastColor != null) {
	// color = new int[] { _lastColor[0], _lastColor[1], _lastColor[2] };
	// }
	// _g.setColor(255, 255, 255);
	// _g.fillRect(x1, y1, width, height);
	// _g.setColor(color[0], color[1], color[2]);
	// }
	// /**
	// * 画面を背景色で塗りつぶします。
	// *
	// */
	// public void clearAll() {
	//
	// clearRect(0, 0, 1000, 1000);
	// }
	/**
	 * 文字列を描画します。
	 * 
	 * @param str
	 *            描画する文字列
	 * @param x
	 *            X座標
	 * @param y
	 *            Y座標
	 */
	public void drawString(String str, int x, int y) {
		_g.drawString(str, x, y + (HmFont.getFontHeight()));
		// _g.drawString(str, x, y, Graphics.LEFT | Graphics.TOP);
	}

	public void drawMemory(GameGraphic g) {
		// メモリ
		Runtime run = Runtime.getRuntime();
		String mem = String.valueOf(run.freeMemory());
		mem += "/" + String.valueOf(run.totalMemory());
		g.setColor(0xff9999);
		HmFont.setFont(g, HmFont.STYLE_PLAIN, HmFont.FONT_SMALL);
		g.drawString(mem, 0, 0);
	}

	/**
	 * 太字の文字列を描画します。
	 * 
	 * @param str
	 *            描画する文字列
	 * @param x
	 *            X座標
	 * @param y
	 *            Y座標
	 */
	public void drawBoldString(String str, int x, int y) {

		drawString(str, x, y);
		drawString(str, x + 1, y);
	}

	/**
	 * 縁付の文字列を描画します。
	 * 
	 * @param str
	 *            描画する文字列
	 * @param x
	 *            X座標
	 * @param y
	 *            Y座標
	 * @param color
	 *            文字色
	 * @param bgcolor
	 *            背景色
	 */
	public void drawBorderString(String str, int x, int y, int color,
			int bgcolor) {

		setColor(bgcolor);
		drawString(str, x - 1, y - 1);
		drawString(str, x - 1, y);
		drawString(str, x - 1, y + 1);

		drawString(str, x, y - 1);
		drawString(str, x, y + 1);

		drawString(str, x + 1, y - 1);
		drawString(str, x + 1, y);
		drawString(str, x + 1, y + 1);

		setColor(color);
		drawString(str, x, y);
	}

	/**
	 * 矩形領域を塗りつぶします。
	 * 
	 * @param x1
	 *            矩形の左上のX座標
	 * @param y1
	 *            矩形の左上のY座標
	 * @param xl2
	 *            矩形の幅
	 * @param yl2
	 *            矩形の高さ
	 */
	public void fillRect(int x1, int y1, int xl2, int yl2) {

		_g.fillRect(x1, y1, xl2, yl2);
	}

	/**
	 * 矩形を描画します。
	 * 
	 * @param x1
	 *            矩形の左上のX座標
	 * @param y1
	 *            矩形の左上のY座標
	 * @param xl2
	 *            矩形の幅
	 * @param yl2
	 *            矩形の高さ
	 */
	public void drawRect(int x1, int y1, int xl2, int yl2) {

		_g.drawRect(x1, y1, xl2, yl2);
	}

	public void setFont(Font font) {
		_g.setFont(font);
	}

	// /**
	// * 矩形領域を塗りつぶします（アルファサポート）。
	// *
	// * @param x1
	// * 矩形の左上のX座標
	// * @param y1
	// * 矩形の左上のY座標
	// * @param xl2
	// * 矩形の幅
	// * @param yl2
	// * 矩形の高さ
	// */
	// public void fillAlphaRect(int x1, int y1, int xl2, int yl2) {
	//
	// // 機種依存?
	// // 一度作成した画像はキャッシュした方がよいかな
	//
	// int[] color = getLastColor();
	// int argb = color[3] << 24 | color[0] << 16 | color[1] << 8 | color[2];
	//
	// int size = xl2 * yl2;
	// int[] px = new int[size];
	//
	// for (int i = 0; i < size; i++) {
	// px[i] = argb;
	// }
	//
	// Image img = Image.createRGBImage(px, xl2, yl2, true);
	// _g.drawImage(img, x1, y1, Graphics.LEFT | Graphics.TOP);
	// }

	/**
	 * 直線を描画します。
	 * 
	 * @param x1
	 *            直線の描画開始点のX座標
	 * @param y1
	 *            直線の描画開始点のY座標
	 * @param x2
	 *            直線の描画終了点のX座標
	 * @param y2
	 *            直線の描画終了点のY座標
	 */
	public void drawLine(int x1, int y1, int x2, int y2) {

		_g.drawLine(x1, y1, x2, y2);
	}

	/**
	 * イメージを描画します。
	 * 
	 * @param img
	 *            描画するイメージオブジェクト
	 * @param x
	 *            X座標
	 * @param y
	 *            Y座標
	 */
	public void drawImage(ImageWrap img, int x, int y) {

		// _g.drawImage(img.getImg(), x, y, Graphics.LEFT | Graphics.TOP);
		_g.drawImage(img.getImg(), x, y, null);
	}

	private int r = 0;

	private int g = 0;

	private int b = 0;

	public void setColor(int c) {
		r = (c & 0xff0000) >> 16;
		g = (c & 0xff00) >> 8;
		b = (c & 0xff);
		setColorRGB(r, g, b);
	}

	// /**
	// * イメージを拡大・縮小して描画します。
	// *
	// * @param img
	// * 描画するイメージオブジェクト
	// * @param dx
	// * 描画先の矩形の左上の X 座標
	// * @param dy
	// * 描画先の矩形の左上の Y 座標
	// * @param ldx
	// * 描画先の矩形の幅
	// * @param ldy
	// * 描画先の矩形の高
	// * @param sx
	// * 描画元の矩形の左上の X 座標
	// * @param sy
	// * 描画元の矩形の左上の Y 座標
	// * @param lsx
	// * 描画元の矩形の幅
	// * @param lsy
	// * 描画元の矩形の高さ
	// */
	// public void drawScaledImage(ImageWrap img, int dx, int dy, int ldx,
	// int ldy, int sx, int sy, int lsx, int lsy, int quality) {
	//
	// // TODO MEXAの機種依存APIなので別途独自実装が必要
	// GraphicsUtil.drawRegion(_g, img.getImg(), sx, sy, lsx, lsy,
	// GraphicsUtil.TRANS_NONE, dx, dy, ldx, ldy, Graphics.LEFT
	// | Graphics.TOP, quality);
	// }

	/**
	 * 弧を描画します。
	 * 
	 * @param x
	 *            弧の左上のX座標
	 * @param y
	 *            弧の左上のY座標
	 * @param width
	 *            弧の幅
	 * @param height
	 *            弧の高さ
	 * @param startAngle
	 *            弧の始点の角度
	 * @param arcAngle
	 *            弧の始点からの角度
	 */
	public void drawArc(int x, int y, int width, int height, int startAngle,
			int arcAngle) {

		_g.drawArc(x, y, width, height, startAngle, arcAngle);
	}

	/**
	 * 弧を塗りつぶします。
	 * 
	 * @param x
	 *            弧の左上のX座標
	 * @param y
	 *            弧の左上のY座標
	 * @param width
	 *            弧の幅
	 * @param height
	 *            弧の高さ
	 * @param startAngle
	 *            弧の始点の角度
	 * @param arcAngle
	 *            弧の始点からの角度
	 */
	public void fillArc(int x, int y, int width, int height, int startAngle,
			int arcAngle) {

		_g.fillArc(x, y, width, height, startAngle, arcAngle);
	}

	// ---------------------------------------------------------------
	// カラー関連
	//
	// ---------------------------------------------------------------

	// 最後にセットされたカラー
	private int[] _lastColor;

	/**
	 * 現在有効なカラーを取得します。
	 * 
	 * @return RGBの数値配列を返します。
	 */
	public int[] getLastColor() {

		if (_lastColor == null) {
			return new int[] { 0, 0, 0, 255 };
		} else {
			return _lastColor;
		}
	}

	/**
	 * カラーをセットします。
	 * 
	 * @param c1
	 *            R
	 * @param c2
	 *            G
	 * @param c3
	 *            B
	 */
	public void setColorRGB(int c1, int c2, int c3) {

		_g.setColor(new Color(c1, c2, c3));
		// _g.setColor(c1, c2, c3);
		// _lastColor = new int[] { c1, c2, c3, 255 };
	}

	public void setColorRGB(int[] color) {
		setColorRGB(color[0], color[1], color[2]);
	}

	/**
	 * カラーをセットします。
	 * 
	 * @param c1
	 *            R
	 * @param c2
	 *            G
	 * @param c3
	 *            B
	 * @param c4
	 *            アルファ値
	 */
	public void setColorRGB(int c1, int c2, int c3, int c4) {

		// アルファ値は設定できない。
		_g.setColor(new Color(c1, c2, c3));
		// _lastColor = new int[] { c1, c2, c3, c4 };
	}

	// ---------------------------------------------------------------
	// タグ装飾描画メソッド
	//
	// ---------------------------------------------------------------
	// // <b>タグ
	// private final String TAG_BOLD = "b";
	//
	// // <u>タグ
	// private final String TAG_ULINE = "u";
	//
	// // <c>タグ
	// private final String TAG_COLOR = "c";
	//
	// // <br>タグ
	// private final String TAG_BR = "br";
	//
	// // <s>タグ
	// private final String TAG_SIZE = "s";

	// // <b>が有効かどうか
	// private boolean USE_BOLD = false;
	//
	// // <u>が有効かどうか
	// private boolean USE_ULINE = false;
	//
	// // <br>が有効かどうか
	// private boolean USE_BR = false;

	// // 文字色退避用
	// private Stack _color = new Stack();
	//
	// // フォントサイズ退避用
	// private Stack _font = new Stack();

	// /**
	// * 定義されたタグを利用して文字列を装飾描画します。<br>
	// *
	// * 【利用可能タグ】 文字サイズ設定 <s=サイズ>...</s> （tiny, small, medium, large を指定可能）
	// 文字色設定
	// * <c=R,G,B>...</c> 太字 <b>...</b> アンダーライン <u>...</u> 改行 <br>
	// *
	// * @param str
	// * タグを含んだ文字列
	// * @param cvsStX
	// * 描画開始位置X
	// * @param cvsStY
	// * 描画開始位置Y
	// * @param cvsEnX
	// * 描画終了位置X
	// * @param lineHeight
	// * 行間
	// */
	// public void drawTagString(String str, int cvsStX, int cvsStY, int cvsEnX,
	// int lineHeight) {
	//
	// int x = cvsStX;
	// int y = cvsStY;
	//
	// int len = str.length();
	// int idxSt = 0;
	// int tagSt = 0;
	// int tagEn = 0;
	//
	// String now = "";
	// String next = "";
	//
	// MktFont.setFont(this, MktFont.STYLE_PLAIN, MktFont.FONT_TINY);
	//
	// while (idxSt < len) {
	//
	// now = str.substring(idxSt, idxSt + 1);
	// int[] fs = MktFont.getFontSize(now);
	// int xmgn = fs[0] + 2;
	//
	// if (now.equals("<")) {
	// next = str.substring(idxSt + 1, idxSt + 2);
	// // 終了タグ
	// if (next.equals("/")) {
	// tagSt = idxSt + 1;
	// tagEn = str.indexOf(">", tagSt);
	// disableTag(str.substring(tagSt + 1, tagEn));
	// // 開始タグ
	// } else {
	// tagSt = idxSt;
	// tagEn = str.indexOf(">", tagSt);
	// enableTag(str.substring(tagSt + 1, tagEn));
	// }
	// idxSt = tagEn;
	//
	// } else {
	// // System.out.print(now);
	// if (USE_BR) {
	// x = cvsStX;
	// y += lineHeight;
	// // 改行タグは終了がないので
	// disableTag(TAG_BR);
	// }
	// if (x >= cvsEnX) {
	// x = cvsStX;
	// y += lineHeight;
	// }
	//
	// drawString(now, x, y);
	// if (USE_BOLD)
	// drawString(now, x + 1, y);
	// if (USE_ULINE)
	// drawLine(x, y, x + xmgn, y);
	// x += xmgn;
	// }
	// idxSt++;
	// }
	// }

	// /**
	// * 指定タグを有効にします。
	// *
	// * @param tag
	// * 有効にするタグ
	// */
	// private void enableTag(String tag) {
	//
	// String[] elem = StringUtil.split(tag, "=");
	//
	// if (elem[0].equals(TAG_BOLD))
	// USE_BOLD = true;
	// else if (elem[0].equals(TAG_BR))
	// USE_BR = true;
	// else if (elem[0].equals(TAG_ULINE))
	// USE_ULINE = true;
	// else if (elem[0].equals(TAG_COLOR)) {
	// _color.push(getLastColor());
	// int[] c = StringUtil.splitInt(elem[1], ",");
	// setColorRGB(c[0], c[1], c[2]);
	// } else if (elem[0].equals(TAG_SIZE)) {
	// _font.push(new int[] { MktFont.getFontType() });
	// int size = MktFont.FONT_TINY;
	// if (elem[1].equals("small"))
	// size = MktFont.FONT_SMALL;
	// else if (elem[1].equals("medium"))
	// size = MktFont.FONT_MEDIUM;
	// else if (elem[1].equals("large"))
	// size = MktFont.FONT_LARGE;
	// MktFont.setFont(this, MktFont.STYLE_PLAIN, size);
	// }
	// }

	// /**
	// * 指定タグを無効にします。
	// *
	// * @param tag
	// * 無効にするタグ
	// */
	// private void disableTag(String tag) {
	//
	// if (tag.equals(TAG_BOLD))
	// USE_BOLD = false;
	// else if (tag.equals(TAG_BR))
	// USE_BR = false;
	// else if (tag.equals(TAG_ULINE))
	// USE_ULINE = false;
	// else if (tag.equals(TAG_COLOR)) {
	// int[] c = (int[]) _color.pop();
	// setColorRGB(c[0], c[1], c[2]);
	// } else if (tag.equals(TAG_SIZE)) {
	// int[] f = (int[]) _font.pop();
	// MktFont.setFont(this, MktFont.STYLE_PLAIN, f[0]);
	// }
	// }

	/**
	 * 画像の指定矩形領域を描画
	 */
	public void drawImageRegion(ImageWrap img, int posX, int posY, int srcX,
			int srcY, int width, int height) {
		// getGraphics().drawRegion(img.getImg(), srcX, srcY, width, height,
		// Sprite.TRANS_NONE, posX, posY, Graphics.LEFT | Graphics.TOP);
		getGraphics().drawImage(img.getImg(), posX, posY, posX + width,
				posY + height, srcX, srcY, srcX + width, srcY + height, null);
	}
}
