package src.jp.co.yh123.zlibrary.platform;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * �O���t�B�b�N�A�t�H���g�Ɋ֘A����@�\
 * 
 * @author Y.H
 */
public class GameGraphic {

	// ��ʉ��T�C�Y
	private int _width = 0;

	// ��ʏc�T�C�Y
	private int _height = 0;

	// �o�b�t�@�����O�p�I�t�X�N���[���C���[�W�B
	// private Image _cvs;

	// �`��Ώ�Graphic
	private Graphics2D _g;

	// ��ʕ`��pGraphic
	// private Graphics _gon;

	// �o�b�t�@�����O�pGraphic
	// private Graphics _goff;
	// �g��k���p
	public final int STRETCH_QUALITY_HIGH = 2;

	public final int STRETCH_QUALITY_LOW = 1;

	public final int STRETCH_QUALITY_NORMAL = 0;

	public int offsetX = 0;

	public int offsetY = 0;

	/**
	 * ��ʃT�C�Y���Z�b�g���܂��B
	 * 
	 * @param width
	 *            ��
	 * @param height
	 *            �c
	 */
	public void setCvs(int width, int height) {

		_width = width;
		_height = height;

		// �o�b�t�@�����O�p
		// _cvs = Image.createImage(_width, _height);
		// _goff = _cvs.getGraphics();
	}

	/**
	 * ��ʂ̕����擾���܂��B
	 * 
	 * @return ��ʂ̕���Ԃ��܂��B
	 */
	public int getWidth() {

		return _width;
	}

	/**
	 * ��ʂ̍������擾���܂��B
	 * 
	 * @return ��ʂ̍�����Ԃ��܂��B
	 */
	public int getHeight() {

		return _height;
	}

	/**
	 * Graphics�C���X�^���X���Z�b�g���܂��B
	 * 
	 * @param g2
	 *            Graphics�C���X�^���X
	 */
	public void setGraphics(Graphics2D g2) {
		_g = g2;
		// _gon = g2;
		// _g = _gon;
	}

	/**
	 * Graphics�C���X�^���X��Ԃ��܂��B
	 * 
	 * @return Graphics�C���X�^���X
	 */
	public Graphics2D getGraphics() {

		return _g;
	}

	/**
	 * �`��f�o�C�X�ɑ΂��āA�_�u���o�b�t�@�����O�̊J�n��錾���܂��B
	 */
	// public void lock() {
	//
	// _g = _goff;
	// }
	/**
	 * �`��f�o�C�X�ɑ΂��āA�_�u���o�b�t�@�����O�̏I����錾���܂��B<br>
	 * ��������ߖ񂷂�ꍇ��GameCanvas���p��������flushGraphics()�𗘗p��������悢�B<br>
	 * �����A������̕����ł���Ή����[�V�����Ή����y�B
	 * 
	 * @param b
	 *            �����I�Ƀt���b�V�����邩�ǂ���
	 */
	// public void unlock(boolean b) {
	//
	// if (_g == _goff) {
	// _gon.drawImage(_cvs, 0, 0, Graphics.LEFT | Graphics.TOP);
	// }
	// _g = _gon;
	// }
	// /**
	// * ��`�̈��w�i�F�œh��Ԃ��܂��B
	// *
	// * @param x1
	// * ��`�̍����X���W
	// * @param y1
	// * ��`�̍����Y���W
	// * @param width
	// * ��`�̕�
	// * @param height
	// * ��`�̍���
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
	// * ��ʂ�w�i�F�œh��Ԃ��܂��B
	// *
	// */
	// public void clearAll() {
	//
	// clearRect(0, 0, 1000, 1000);
	// }
	/**
	 * �������`�悵�܂��B
	 * 
	 * @param str
	 *            �`�悷�镶����
	 * @param x
	 *            X���W
	 * @param y
	 *            Y���W
	 */
	public void drawString(String str, int x, int y) {
		_g.drawString(str, x, y + (HmFont.getFontHeight()));
		// _g.drawString(str, x, y, Graphics.LEFT | Graphics.TOP);
	}

	public void drawMemory(GameGraphic g) {
		// ������
		Runtime run = Runtime.getRuntime();
		String mem = String.valueOf(run.freeMemory());
		mem += "/" + String.valueOf(run.totalMemory());
		g.setColor(0xff9999);
		HmFont.setFont(g, HmFont.STYLE_PLAIN, HmFont.FONT_SMALL);
		g.drawString(mem, 0, 0);
	}

	/**
	 * �����̕������`�悵�܂��B
	 * 
	 * @param str
	 *            �`�悷�镶����
	 * @param x
	 *            X���W
	 * @param y
	 *            Y���W
	 */
	public void drawBoldString(String str, int x, int y) {

		drawString(str, x, y);
		drawString(str, x + 1, y);
	}

	/**
	 * ���t�̕������`�悵�܂��B
	 * 
	 * @param str
	 *            �`�悷�镶����
	 * @param x
	 *            X���W
	 * @param y
	 *            Y���W
	 * @param color
	 *            �����F
	 * @param bgcolor
	 *            �w�i�F
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
	 * ��`�̈��h��Ԃ��܂��B
	 * 
	 * @param x1
	 *            ��`�̍����X���W
	 * @param y1
	 *            ��`�̍����Y���W
	 * @param xl2
	 *            ��`�̕�
	 * @param yl2
	 *            ��`�̍���
	 */
	public void fillRect(int x1, int y1, int xl2, int yl2) {

		_g.fillRect(x1, y1, xl2, yl2);
	}

	/**
	 * ��`��`�悵�܂��B
	 * 
	 * @param x1
	 *            ��`�̍����X���W
	 * @param y1
	 *            ��`�̍����Y���W
	 * @param xl2
	 *            ��`�̕�
	 * @param yl2
	 *            ��`�̍���
	 */
	public void drawRect(int x1, int y1, int xl2, int yl2) {

		_g.drawRect(x1, y1, xl2, yl2);
	}

	public void setFont(Font font) {
		_g.setFont(font);
	}

	// /**
	// * ��`�̈��h��Ԃ��܂��i�A���t�@�T�|�[�g�j�B
	// *
	// * @param x1
	// * ��`�̍����X���W
	// * @param y1
	// * ��`�̍����Y���W
	// * @param xl2
	// * ��`�̕�
	// * @param yl2
	// * ��`�̍���
	// */
	// public void fillAlphaRect(int x1, int y1, int xl2, int yl2) {
	//
	// // �@��ˑ�?
	// // ��x�쐬�����摜�̓L���b�V�����������悢����
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
	 * ������`�悵�܂��B
	 * 
	 * @param x1
	 *            �����̕`��J�n�_��X���W
	 * @param y1
	 *            �����̕`��J�n�_��Y���W
	 * @param x2
	 *            �����̕`��I���_��X���W
	 * @param y2
	 *            �����̕`��I���_��Y���W
	 */
	public void drawLine(int x1, int y1, int x2, int y2) {

		_g.drawLine(x1, y1, x2, y2);
	}

	/**
	 * �C���[�W��`�悵�܂��B
	 * 
	 * @param img
	 *            �`�悷��C���[�W�I�u�W�F�N�g
	 * @param x
	 *            X���W
	 * @param y
	 *            Y���W
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
	// * �C���[�W���g��E�k�����ĕ`�悵�܂��B
	// *
	// * @param img
	// * �`�悷��C���[�W�I�u�W�F�N�g
	// * @param dx
	// * �`���̋�`�̍���� X ���W
	// * @param dy
	// * �`���̋�`�̍���� Y ���W
	// * @param ldx
	// * �`���̋�`�̕�
	// * @param ldy
	// * �`���̋�`�̍�
	// * @param sx
	// * �`�挳�̋�`�̍���� X ���W
	// * @param sy
	// * �`�挳�̋�`�̍���� Y ���W
	// * @param lsx
	// * �`�挳�̋�`�̕�
	// * @param lsy
	// * �`�挳�̋�`�̍���
	// */
	// public void drawScaledImage(ImageWrap img, int dx, int dy, int ldx,
	// int ldy, int sx, int sy, int lsx, int lsy, int quality) {
	//
	// // TODO MEXA�̋@��ˑ�API�Ȃ̂ŕʓr�Ǝ��������K�v
	// GraphicsUtil.drawRegion(_g, img.getImg(), sx, sy, lsx, lsy,
	// GraphicsUtil.TRANS_NONE, dx, dy, ldx, ldy, Graphics.LEFT
	// | Graphics.TOP, quality);
	// }

	/**
	 * �ʂ�`�悵�܂��B
	 * 
	 * @param x
	 *            �ʂ̍����X���W
	 * @param y
	 *            �ʂ̍����Y���W
	 * @param width
	 *            �ʂ̕�
	 * @param height
	 *            �ʂ̍���
	 * @param startAngle
	 *            �ʂ̎n�_�̊p�x
	 * @param arcAngle
	 *            �ʂ̎n�_����̊p�x
	 */
	public void drawArc(int x, int y, int width, int height, int startAngle,
			int arcAngle) {

		_g.drawArc(x, y, width, height, startAngle, arcAngle);
	}

	/**
	 * �ʂ�h��Ԃ��܂��B
	 * 
	 * @param x
	 *            �ʂ̍����X���W
	 * @param y
	 *            �ʂ̍����Y���W
	 * @param width
	 *            �ʂ̕�
	 * @param height
	 *            �ʂ̍���
	 * @param startAngle
	 *            �ʂ̎n�_�̊p�x
	 * @param arcAngle
	 *            �ʂ̎n�_����̊p�x
	 */
	public void fillArc(int x, int y, int width, int height, int startAngle,
			int arcAngle) {

		_g.fillArc(x, y, width, height, startAngle, arcAngle);
	}

	// ---------------------------------------------------------------
	// �J���[�֘A
	//
	// ---------------------------------------------------------------

	// �Ō�ɃZ�b�g���ꂽ�J���[
	private int[] _lastColor;

	/**
	 * ���ݗL���ȃJ���[���擾���܂��B
	 * 
	 * @return RGB�̐��l�z���Ԃ��܂��B
	 */
	public int[] getLastColor() {

		if (_lastColor == null) {
			return new int[] { 0, 0, 0, 255 };
		} else {
			return _lastColor;
		}
	}

	/**
	 * �J���[���Z�b�g���܂��B
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
	 * �J���[���Z�b�g���܂��B
	 * 
	 * @param c1
	 *            R
	 * @param c2
	 *            G
	 * @param c3
	 *            B
	 * @param c4
	 *            �A���t�@�l
	 */
	public void setColorRGB(int c1, int c2, int c3, int c4) {

		// �A���t�@�l�͐ݒ�ł��Ȃ��B
		_g.setColor(new Color(c1, c2, c3));
		// _lastColor = new int[] { c1, c2, c3, c4 };
	}

	// ---------------------------------------------------------------
	// �^�O�����`�惁�\�b�h
	//
	// ---------------------------------------------------------------
	// // <b>�^�O
	// private final String TAG_BOLD = "b";
	//
	// // <u>�^�O
	// private final String TAG_ULINE = "u";
	//
	// // <c>�^�O
	// private final String TAG_COLOR = "c";
	//
	// // <br>�^�O
	// private final String TAG_BR = "br";
	//
	// // <s>�^�O
	// private final String TAG_SIZE = "s";

	// // <b>���L�����ǂ���
	// private boolean USE_BOLD = false;
	//
	// // <u>���L�����ǂ���
	// private boolean USE_ULINE = false;
	//
	// // <br>���L�����ǂ���
	// private boolean USE_BR = false;

	// // �����F�ޔ�p
	// private Stack _color = new Stack();
	//
	// // �t�H���g�T�C�Y�ޔ�p
	// private Stack _font = new Stack();

	// /**
	// * ��`���ꂽ�^�O�𗘗p���ĕ�����𑕏��`�悵�܂��B<br>
	// *
	// * �y���p�\�^�O�z �����T�C�Y�ݒ� <s=�T�C�Y>...</s> �itiny, small, medium, large ���w��\�j
	// �����F�ݒ�
	// * <c=R,G,B>...</c> ���� <b>...</b> �A���_�[���C�� <u>...</u> ���s <br>
	// *
	// * @param str
	// * �^�O���܂񂾕�����
	// * @param cvsStX
	// * �`��J�n�ʒuX
	// * @param cvsStY
	// * �`��J�n�ʒuY
	// * @param cvsEnX
	// * �`��I���ʒuX
	// * @param lineHeight
	// * �s��
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
	// // �I���^�O
	// if (next.equals("/")) {
	// tagSt = idxSt + 1;
	// tagEn = str.indexOf(">", tagSt);
	// disableTag(str.substring(tagSt + 1, tagEn));
	// // �J�n�^�O
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
	// // ���s�^�O�͏I�����Ȃ��̂�
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
	// * �w��^�O��L���ɂ��܂��B
	// *
	// * @param tag
	// * �L���ɂ���^�O
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
	// * �w��^�O�𖳌��ɂ��܂��B
	// *
	// * @param tag
	// * �����ɂ���^�O
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
	 * �摜�̎w���`�̈��`��
	 */
	public void drawImageRegion(ImageWrap img, int posX, int posY, int srcX,
			int srcY, int width, int height) {
		// getGraphics().drawRegion(img.getImg(), srcX, srcY, width, height,
		// Sprite.TRANS_NONE, posX, posY, Graphics.LEFT | Graphics.TOP);
		getGraphics().drawImage(img.getImg(), posX, posY, posX + width,
				posY + height, srcX, srcY, srcX + width, srcY + height, null);
	}
}
