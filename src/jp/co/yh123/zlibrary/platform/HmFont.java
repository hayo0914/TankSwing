package src.jp.co.yh123.zlibrary.platform;

import java.awt.Font;

/**
 * �t�H���g�̊Ǘ�
 * 
 * @author Y.H
 */
public class HmFont {

	/**
	 * �X�^�C��
	 */
	public static int STYLE_PLAIN = Font.PLAIN;

	/**
	 * �X�^�C��
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
		FONT_TINY.fontType = 10;// swing�ł�size��pixel�w��
		FONT_TINY.fontStyle = STYLE_PLAIN;// default
		FONT_TINY.fontWidth = 5;// �S�p�Ȃ�*2
		FONT_TINY.fontHeight = 10;

		FONT_SMALL = new FontProp();
		FONT_SMALL.fontType = 10;// swing�ł�size��pixel�w��
		FONT_SMALL.fontStyle = STYLE_PLAIN;// default
		FONT_SMALL.fontWidth = 5;// �S�p�Ȃ�*2
		FONT_SMALL.fontHeight = 12;

		FONT_MEDIUM = new FontProp();
		FONT_MEDIUM.fontType = 12;// swing�ł�size��pixel�w��
		FONT_MEDIUM.fontStyle = STYLE_PLAIN;// default
		FONT_MEDIUM.fontWidth = 6;// �S�p�Ȃ�*2
		FONT_MEDIUM.fontHeight = 14;

		FONT_LARGE = new FontProp();
		FONT_LARGE.fontType = 16;// swing�ł�size��pixel�w��
		FONT_LARGE.fontStyle = STYLE_PLAIN;// default
		FONT_LARGE.fontWidth = 8;// �S�p�Ȃ�*2
		FONT_LARGE.fontHeight = 16;
	}

	// ���ݗL���ȃt�H���g�I�u�W�F�N�g
	private static FontProp activeFont = FONT_TINY;

	/**
	 * ���p�i���p�p���L���A���p�Łj�����f����
	 * 
	 * @param c
	 *            �Ώ�char
	 * @return ���p�i���p�p���L���A���p�Łj�̏ꍇ true
	 */
	public static boolean isHankaku(char c) {
		boolean res = false;
		if (('\u0020' <= c && c <= '\u007e') || // ���p�p���L��
				('\uff61' <= c && c <= '\uff9f')) { // ���p�J�^�J�i
			res = true;
		}
		return res;
	}

	/**
	 * �t�H���g���Z�b�g���܂��B
	 * 
	 * @param fontType
	 *            �t�H���g�^�C�v
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
