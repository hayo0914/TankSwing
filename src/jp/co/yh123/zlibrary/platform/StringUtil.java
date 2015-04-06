package src.jp.co.yh123.zlibrary.platform;

/**
 * ��������������邽�߂̋@�\��񋟂��܂��B
 * 
 * @author Takuto Nishioka
 */
public class StringUtil {

	/**
	 * �S�p�ϊ����w�肷��萔�ł��B
	 */
	public static final int ZENKAKU = 0;

	/**
	 * ���p�ϊ����w�肷��萔�ł��B
	 */
	public static final int HANKAKU = 1;

	/**
	 * �Ђ炪�ȕϊ����w�肷��萔�ł��B
	 */
	public static final int HIRAKANA = 2;

	/**
	 * �J�^�J�i�ϊ����w�肷��萔�ł��B
	 */
	public static final int KATAKANA = 3;

	// ���p�J�i�ϊ��e�[�u���ł��B
	private static final char[] HAN_KANA = { '�', '�', '�', '�', '�', '�', '�',
			'�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�',
			'�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�',
			'�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�',
			'�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�',
			'�', '�', '�', '�' };

	// �S�p�J�i�ϊ��e�[�u���ł��B
	private static final char[] ZEN_KANA = { '�B', '�u', '�v', '�A', '�E', '��', '�@',
			'�B', '�D', '�F', '�H', '��', '��', '��', '�b', '�[', '�A', '�C', '�E', '�G',
			'�I', '�J', '�L', '�N', '�P', '�R', '�T', '�V', '�X', '�Z', '�\', '�^', '�`',
			'�c', '�e', '�g', '�i', '�j', '�k', '�l', '�m', '�n', '�q', '�t', '�w', '�z',
			'�}', '�~', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��',
			'��', '��', '�J', '�K' };

	// ���p�J�i�̍ŏ��̕����ł��B
	private static final char HAN_KANA_FIRST = HAN_KANA[0];

	// ���p�J�i�̍Ō�̕����ł��B
	private static final char HAN_KANA_LAST = HAN_KANA[HAN_KANA.length - 1];

	public static int toInt(String str) throws Exception {
		return Integer.parseInt(str);
	}

	public static double toDouble(String str) throws Exception {
		return Double.parseDouble(str);
	}

	public static String toStr(double d) throws Exception {
		return Double.toString(d);
	}

	public static String toStr(int p) throws Exception {
		return Double.toString(p);
	}

	/**
	 * �Ώە������u�����܂��B
	 * 
	 * @param target
	 *            �Ώە�����
	 * @param from
	 *            �u�����̕�����
	 * @param to
	 *            �u����̕�����
	 * @return �u�����ꂽ�������Ԃ��܂��B
	 */
	public static String replace(String target, String from, String to) {

		if (from.equals(to))
			return target;
		if (target.length() == 0 || from.length() == 0)
			return target;

		int fromLen = from.length();
		int toLen = to.length();
		int w = (fromLen > toLen) ? toLen : fromLen;

		int st = 0;
		int idx = 0;
		while ((idx = target.indexOf(from, st)) >= 0) {
			target = target.substring(0, idx) + to
					+ target.substring(idx + fromLen);
			st = idx + w;
		}
		return target;
	}

	/**
	 * �Ώە������u�����܂��B <br>
	 * �u�����A�u���敶�����z��Ŏw��ł��܂��B
	 * 
	 * @param target
	 *            �Ώە�����
	 * @param from
	 *            �u�����̕�����z��
	 * @param to
	 *            �u����̕�����z��
	 * @return �u�����ꂽ�������Ԃ��܂��B
	 */
	public static String replace(String target, String[] from, String[] to) {

		if (from.length != to.length)
			return target;

		for (int i = 0, len = from.length; i < len; i++) {
			target = replace(target, from[i], to[i]);
		}
		return target;
	}

	/**
	 * ������u��
	 * 
	 * @param text
	 * @param from
	 * @param to
	 * @return
	 */
	public static String replaceStr(String text, char from, String to) {
		if (text.indexOf(from) < 0)
			return text;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) != from)
				sb.append(text.charAt(i));
			else
				sb.append(to);
		}
		return sb.toString();
	}

	/**
	 * �Ώە�����ɁA�w�肳�ꂽ�����񂪂����܂܂�邩���ׂ܂��B
	 * 
	 * @param target
	 *            �Ώە�����
	 * @param by
	 *            �J�E���g������
	 * @return �w�肳�ꂽ������Ɋ܂܂�����Ԃ��܂��B
	 */
	public static int countStr(String target, String by) {

		if (target.length() == 0 || by.length() == 0)
			return 0;

		int idx = -1;
		int cnt = 0;
		while ((idx = target.indexOf(by, idx + 1)) >= 0) {
			cnt++;
		}

		return cnt;
	}

	/**
	 * �Ώە�������A�w�肳�ꂽ������Ɉ�v����ʒu�ŕ������܂��B
	 * 
	 * @param target
	 *            �Ώە�����
	 * @param by
	 *            ��؂蕶����
	 * @return �w�肳�ꂽ������ŕ������ꂽ������̔z���Ԃ��܂��B
	 */
	public static String[] split(String target, String by) {

		String[] ret = new String[countStr(target, by) + 1];

		int idx = 0;
		int cnt = 0;
		int byLen = by.length();

		while ((idx = target.indexOf(by, 0)) >= 0) {
			ret[cnt++] = target.substring(0, idx);
			target = target.substring(idx + byLen);
		}
		ret[cnt] = target;

		return ret;
	}

	/**
	 * CSV�`���̕�����𕪊����܂��B <br>
	 * �Ώە�����́A"����","����","����" �`����CSV������ł���K�v������܂��B
	 * 
	 * @param target
	 *            �Ώە�����
	 * @return �������ꂽ������̔z���Ԃ��܂��B
	 */
	public static String[] splitCsv(String target) {

		// "," ���Z�p���[�^�Ƃ��ĕ���
		if (target.startsWith("\"") && target.endsWith("\"")) {
			target = target.substring(1, target.length() - 1);
			return split(target, "\",\"");
		}
		return new String[] { target };
	}

	/**
	 * @param value
	 *            �Ώە�����
	 * @param old_str
	 *            �u����������
	 * @param new_str
	 *            �u���V������
	 * @return �u���㕶����
	 */
	public static String replaceAll(String value, String old_str, String new_str) {
		// null�`�F�b�N
		if (value == null || old_str == null || "".equals(old_str)) {
			return value;
		}
		StringBuffer ret = new StringBuffer();
		int old_len = old_str.length();
		int from_index = 0;
		int index = 0;
		boolean loop_flg = true;
		while (loop_flg) {
			index = value.indexOf(old_str, from_index);
			if (-1 < index) {
				ret.append(value.substring(from_index, index));
				ret.append(new_str);
				from_index = index + old_len;
			} else {
				ret.append(value.substring(from_index));
				loop_flg = false;
			}
		}
		return ret.toString();
	}

	/**
	 * �Ώە�������A�w�肳�ꂽ������Ɉ�v����ʒu�ŕ������Aint�ɕϊ����܂��B
	 * 
	 * @param target
	 *            �Ώە�����
	 * @param by
	 *            ��؂蕶����
	 * @return �w�肳�ꂽ������ŕ������ꂽ���l�̔z���Ԃ��܂��B
	 */
	public static int[] splitInt(String target, String by) {

		String[] p = split(target, by);
		int[] ret = new int[p.length];

		for (int i = 0; i < p.length; i++) {
			try {
				ret[i] = Integer.parseInt(p[i]);
			} catch (NumberFormatException e) {
				return new int[] { 0 };
			}
		}
		return ret;
	}

	/**
	 * ������S�p�̔��p�ϊ����܂��B
	 * 
	 * @param str
	 *            �ϊ��Ώە�����
	 * @param type
	 *            ZENKAKU(0), HANKAKU(1)
	 * @return �������ϊ����ĕԂ��܂��B
	 */
	public static String cnvNumber(String str, int type) {

		if (str.length() == 0)
			return "";

		StringBuffer sb = new StringBuffer(str);
		for (int i = 0, len = sb.length(); i < len; i++) {
			char c = sb.charAt(i);
			if (type == HANKAKU) {
				if ('�O' <= c && c <= '�X') {
					sb.setCharAt(i, (char) (c - '�O' + '0'));
				}
			} else {
				if ('0' <= c && c <= '9') {
					sb.setCharAt(i, (char) (c - '0' + '�O'));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * �p����S�p�̔��p�ϊ����܂��B
	 * 
	 * @param str
	 *            �ϊ��Ώە�����
	 * @param type
	 *            ZENKAKU(0), HANKAKU(1)
	 * @return �������ϊ����ĕԂ��܂��B
	 */
	public static String cnvAlphabet(String str, int type) {

		if (str.length() == 0)
			return "";

		StringBuffer sb = new StringBuffer(str);
		for (int i = 0, len = sb.length(); i < len; i++) {
			char c = sb.charAt(i);
			if (type == HANKAKU) {
				if ('��' <= c && c <= '��') {
					sb.setCharAt(i, (char) (c - '��' + 'a'));
				} else if ('�`' <= c && c <= '�y') {
					sb.setCharAt(i, (char) (c - '�`' + 'A'));
				}
			} else {
				if ('a' <= c && c <= 'z') {
					sb.setCharAt(i, (char) (c - 'a' + '��'));
				} else if ('A' <= c && c <= 'Z') {
					sb.setCharAt(i, (char) (c - 'A' + '�`'));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * ���ȁ̃J�i��ϊ����܂��B
	 * 
	 * @param str
	 *            �ϊ��Ώە�����
	 * @param type
	 *            HIRAKANA(2), KATAKANA(2)
	 * @return �������ϊ����ĕԂ��܂��B
	 */
	public static String cnvKana2(String str, int type) {

		if (str.length() == 0)
			return "";

		StringBuffer sb = new StringBuffer(str);
		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);
			if (type == HIRAKANA) {
				if ('�@' <= c && c <= '��') {
					sb.setCharAt(i, (char) (c - '�@' + '��'));
				} else if (c == '��') {
					sb.setCharAt(i, '��');
				} else if (c == '��') {
					sb.setCharAt(i, '��');
				} else if (c == '��') {
					sb.setCharAt(i, '��');
					sb.insert(i + 1, '�J');
					i++;
				}
			} else {
				if ('��' <= c && c <= '��') {
					sb.setCharAt(i, (char) (c - '��' + '�@'));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * ���p�J�^�J�i����S�p�J�^�J�i�֕ϊ����܂��B
	 * 
	 * @param str
	 *            �ϊ��Ώە�����
	 * @return �������ϊ����ĕԂ��܂��B
	 */
	public static String cnvKana(String str) {

		if (str.length() == 0)
			return "";
		if (str.length() == 1)
			return cnvKanaChar(str.charAt(0)) + "";

		StringBuffer sb = new StringBuffer(str);
		int i = 0;
		for (i = 0; i < sb.length() - 1; i++) {
			char originalChar1 = sb.charAt(i);
			char originalChar2 = sb.charAt(i + 1);
			char margedChar = mergeKanaChar(originalChar1, originalChar2);
			if (margedChar != originalChar1) {
				sb.setCharAt(i, margedChar);
				sb.deleteCharAt(i + 1);
			} else {
				char convertedChar = cnvKanaChar(originalChar1);
				if (convertedChar != originalChar1) {
					sb.setCharAt(i, convertedChar);
				}
			}
		}
		if (i < sb.length()) {
			char originalChar1 = sb.charAt(i);
			char convertedChar = cnvKanaChar(originalChar1);
			if (convertedChar != originalChar1) {
				sb.setCharAt(i, convertedChar);
			}
		}
		return sb.toString();
	}

	/**
	 * ���p�J�^�J�i����S�p�J�^�J�i�֕ϊ����܂��B
	 * 
	 * @param c
	 *            �ϊ��O�̕���
	 * @return �ϊ���̕�����Ԃ��܂��B
	 */
	private static char cnvKanaChar(char c) {

		if (c >= HAN_KANA_FIRST && c <= HAN_KANA_LAST) {
			return ZEN_KANA[c - HAN_KANA_FIRST];
		} else {
			return c;
		}
	}

	/**
	 * 2�����ڂ����_�E�����_�ŁA1�����ڂɉ����邱�Ƃ��ł���ꍇ�́A��������������Ԃ��܂��B <br>
	 * �������ł��Ȃ��Ƃ��́Ac1��Ԃ��܂��B
	 * 
	 * @param c1
	 *            �ϊ��O��1������
	 * @param c2
	 *            �ϊ��O��2������
	 * @return �ϊ���̕�����Ԃ��܂��B
	 */
	private static char mergeKanaChar(char c1, char c2) {

		if (c2 == '�') {
			// "���..." �Ƃ���ƃJ�̔��肪��肭�s���Ȃ�
			if ("_��������������������".indexOf(c1) > 0) {
				switch (c1) {
				case '�':
					return '�K';
				case '�':
					return '�M';
				case '�':
					return '�O';
				case '�':
					return '�Q';
				case '�':
					return '�S';
				case '�':
					return '�U';
				case '�':
					return '�W';
				case '�':
					return '�Y';
				case '�':
					return '�[';
				case '�':
					return '�]';
				case '�':
					return '�_';
				case '�':
					return '�a';
				case '�':
					return '�d';
				case '�':
					return '�f';
				case '�':
					return '�h';
				case '�':
					return '�o';
				case '�':
					return '�r';
				case '�':
					return '�u';
				case '�':
					return '�x';
				case '�':
					return '�{';
				}
			}
		} else if (c2 == '�') {
			if ("_�����".indexOf(c1) > 0) {
				switch (c1) {
				case '�':
					return '�p';
				case '�':
					return '�s';
				case '�':
					return '�v';
				case '�':
					return '�y';
				case '�':
					return '�|';
				}
			}
		}
		return c1;
	}

	/**
	 * ������ɐ��l�ȊO���܂܂�Ă��Ȃ����`�F�b�N���܂��B
	 * 
	 * @return �K���Ȓl�ł����true��Ԃ��܂��B
	 */
	public static boolean checkNumber(String str) {

		if (str.length() == 0)
			return false;

		for (int i = 0, len = str.length(); i < len; i++) {
			char c = str.charAt(i);
			if (c < '0' || '9' < c) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ������ɉp���ȊO���܂܂�Ă��Ȃ����`�F�b�N���܂��B
	 * 
	 * @param str
	 *            �Ώە�����
	 * @return �K���Ȓl�ł����true��Ԃ��܂��B
	 */
	public static boolean checkAlphabet(String str) {

		if (str.length() == 0)
			return false;

		for (int i = 0, len = str.length(); i < len; i++) {
			char c = str.charAt(i);
			if (!('a' <= c && c <= 'z') && !('A' <= c && c <= 'Z')) {
				return false;
			}
		}
		return true;
	}

}
