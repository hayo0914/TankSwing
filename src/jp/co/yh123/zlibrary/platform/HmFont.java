package src.jp.co.yh123.zlibrary.platform;

import java.awt.Font;

/**
 * フォントの管理
 * 
 * @author Y.H
 */
public class HmFont {

	/**
	 * スタイル
	 */
	public static int STYLE_PLAIN = Font.PLAIN;

	/**
	 * スタイル
	 */
	public static int STYLE_ITALIC = Font.ITALIC;

	private static class FontProp {

		private FontProp() {
			// do nothing
		}

		private int fontWidth = 0;
		private int fontHeight = 0;
		private int fontType = 0;
		private int fontStyle = 0;
	}

	public static FontProp FONT_TINY;
	public static FontProp FONT_SMALL;
	public static FontProp FONT_MEDIUM;
	public static FontProp FONT_LARGE;
	static {
		FONT_TINY = new FontProp();
		FONT_TINY.fontType = 10;// swingではsizeをpixel指定
		FONT_TINY.fontStyle = STYLE_PLAIN;// default
		FONT_TINY.fontWidth = 5;// 全角なら*2
		FONT_TINY.fontHeight = 10;

		FONT_SMALL = new FontProp();
		FONT_SMALL.fontType = 10;// swingではsizeをpixel指定
		FONT_SMALL.fontStyle = STYLE_PLAIN;// default
		FONT_SMALL.fontWidth = 5;// 全角なら*2
		FONT_SMALL.fontHeight = 12;

		FONT_MEDIUM = new FontProp();
		FONT_MEDIUM.fontType = 12;// swingではsizeをpixel指定
		FONT_MEDIUM.fontStyle = STYLE_PLAIN;// default
		FONT_MEDIUM.fontWidth = 6;// 全角なら*2
		FONT_MEDIUM.fontHeight = 14;

		FONT_LARGE = new FontProp();
		FONT_LARGE.fontType = 16;// swingではsizeをpixel指定
		FONT_LARGE.fontStyle = STYLE_PLAIN;// default
		FONT_LARGE.fontWidth = 8;// 全角なら*2
		FONT_LARGE.fontHeight = 16;
	}

	// 現在有効なフォントオブジェクト
	private static FontProp activeFont = FONT_TINY;

	/**
	 * 半角（半角英数記号、半角ｶﾅ）か判断する
	 * 
	 * @param c
	 *            対象char
	 * @return 半角（半角英数記号、半角ｶﾅ）の場合 true
	 */
	public static boolean isHankaku(char c) {
		boolean res = false;
		if (('\u0020' <= c && c <= '\u007e') || // 半角英数記号
				('\uff61' <= c && c <= '\uff9f')) { // 半角カタカナ
			res = true;
		}
		return res;
	}

	/**
	 * フォントをセットします。
	 * 
	 * @param fontType
	 *            フォントタイプ
	 */
	public static void setFont(GameGraphic g, int styleType, FontProp fontType) {
		activeFont = fontType;
		activeFont.fontStyle = styleType;
		Font font = new Font("Monospaced", activeFont.fontStyle,
				activeFont.fontType);
		// activeFont = Font.getFont(Font.FACE_MONOSPACE, styleType, fontType);
		g.getGraphics().setFont(font);
	}

	public static int getStringWidth(FontProp fontType, String str) {
		int width = 0;
		int len = str.length();
		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			if (isHankaku(c)) {
				width += fontType.fontWidth;
			} else {
				width += fontType.fontWidth * 2;
			}
		}
		return width;
	}

	public static int getFontHeight() {
		return activeFont.fontHeight;
	}

}
