package src.jp.co.yh123.zlibrary.platform;

/**
 * 文字列を処理するための機能を提供します。
 * 
 * @author Takuto Nishioka
 */
public class StringUtil {

	/**
	 * 全角変換を指定する定数です。
	 */
	public static final int ZENKAKU = 0;

	/**
	 * 半角変換を指定する定数です。
	 */
	public static final int HANKAKU = 1;

	/**
	 * ひらがな変換を指定する定数です。
	 */
	public static final int HIRAKANA = 2;

	/**
	 * カタカナ変換を指定する定数です。
	 */
	public static final int KATAKANA = 3;

	// 半角カナ変換テーブルです。
	private static final char[] HAN_KANA = { '｡', '｢', '｣', '､', '･', 'ｦ', 'ｧ',
			'ｨ', 'ｩ', 'ｪ', 'ｫ', 'ｬ', 'ｭ', 'ｮ', 'ｯ', 'ｰ', 'ｱ', 'ｲ', 'ｳ', 'ｴ',
			'ｵ', 'ｶ', 'ｷ', 'ｸ', 'ｹ', 'ｺ', 'ｻ', 'ｼ', 'ｽ', 'ｾ', 'ｿ', 'ﾀ', 'ﾁ',
			'ﾂ', 'ﾃ', 'ﾄ', 'ﾅ', 'ﾆ', 'ﾇ', 'ﾈ', 'ﾉ', 'ﾊ', 'ﾋ', 'ﾌ', 'ﾍ', 'ﾎ',
			'ﾏ', 'ﾐ', 'ﾑ', 'ﾒ', 'ﾓ', 'ﾔ', 'ﾕ', 'ﾖ', 'ﾗ', 'ﾘ', 'ﾙ', 'ﾚ', 'ﾛ',
			'ﾜ', 'ﾝ', 'ﾞ', 'ﾟ' };

	// 全角カナ変換テーブルです。
	private static final char[] ZEN_KANA = { '。', '「', '」', '、', '・', 'ヲ', 'ァ',
			'ィ', 'ゥ', 'ェ', 'ォ', 'ャ', 'ュ', 'ョ', 'ッ', 'ー', 'ア', 'イ', 'ウ', 'エ',
			'オ', 'カ', 'キ', 'ク', 'ケ', 'コ', 'サ', 'シ', 'ス', 'セ', 'ソ', 'タ', 'チ',
			'ツ', 'テ', 'ト', 'ナ', 'ニ', 'ヌ', 'ネ', 'ノ', 'ハ', 'ヒ', 'フ', 'ヘ', 'ホ',
			'マ', 'ミ', 'ム', 'メ', 'モ', 'ヤ', 'ユ', 'ヨ', 'ラ', 'リ', 'ル', 'レ', 'ロ',
			'ワ', 'ン', '゛', '゜' };

	// 半角カナの最初の文字です。
	private static final char HAN_KANA_FIRST = HAN_KANA[0];

	// 半角カナの最後の文字です。
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
	 * 対象文字列を置換します。
	 * 
	 * @param target
	 *            対象文字列
	 * @param from
	 *            置換元の文字列
	 * @param to
	 *            置換先の文字列
	 * @return 置換された文字列を返します。
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
	 * 対象文字列を置換します。 <br>
	 * 置換元、置換先文字列を配列で指定できます。
	 * 
	 * @param target
	 *            対象文字列
	 * @param from
	 *            置換元の文字列配列
	 * @param to
	 *            置換先の文字列配列
	 * @return 置換された文字列を返します。
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
	 * 文字列置換
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
	 * 対象文字列に、指定された文字列がいくつ含まれるか調べます。
	 * 
	 * @param target
	 *            対象文字列
	 * @param by
	 *            カウント文字列
	 * @return 指定された文字列に含まれる個数を返します。
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
	 * 対象文字列を、指定された文字列に一致する位置で分割します。
	 * 
	 * @param target
	 *            対象文字列
	 * @param by
	 *            区切り文字列
	 * @return 指定された文字列で分割された文字列の配列を返します。
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
	 * CSV形式の文字列を分割します。 <br>
	 * 対象文字列は、"文字","文字","文字" 形式のCSV文字列である必要があります。
	 * 
	 * @param target
	 *            対象文字列
	 * @return 分割された文字列の配列を返します。
	 */
	public static String[] splitCsv(String target) {

		// "," をセパレータとして分割
		if (target.startsWith("\"") && target.endsWith("\"")) {
			target = target.substring(1, target.length() - 1);
			return split(target, "\",\"");
		}
		return new String[] { target };
	}

	/**
	 * @param value
	 *            対象文字列
	 * @param old_str
	 *            置換旧文字列
	 * @param new_str
	 *            置換新文字列
	 * @return 置換後文字列
	 */
	public static String replaceAll(String value, String old_str, String new_str) {
		// nullチェック
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
	 * 対象文字列を、指定された文字列に一致する位置で分割し、intに変換します。
	 * 
	 * @param target
	 *            対象文字列
	 * @param by
	 *            区切り文字列
	 * @return 指定された文字列で分割された数値の配列を返します。
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
	 * 数字を全角⇔半角変換します。
	 * 
	 * @param str
	 *            変換対象文字列
	 * @param type
	 *            ZENKAKU(0), HANKAKU(1)
	 * @return 文字列を変換して返します。
	 */
	public static String cnvNumber(String str, int type) {

		if (str.length() == 0)
			return "";

		StringBuffer sb = new StringBuffer(str);
		for (int i = 0, len = sb.length(); i < len; i++) {
			char c = sb.charAt(i);
			if (type == HANKAKU) {
				if ('０' <= c && c <= '９') {
					sb.setCharAt(i, (char) (c - '０' + '0'));
				}
			} else {
				if ('0' <= c && c <= '9') {
					sb.setCharAt(i, (char) (c - '0' + '０'));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 英字を全角⇔半角変換します。
	 * 
	 * @param str
	 *            変換対象文字列
	 * @param type
	 *            ZENKAKU(0), HANKAKU(1)
	 * @return 文字列を変換して返します。
	 */
	public static String cnvAlphabet(String str, int type) {

		if (str.length() == 0)
			return "";

		StringBuffer sb = new StringBuffer(str);
		for (int i = 0, len = sb.length(); i < len; i++) {
			char c = sb.charAt(i);
			if (type == HANKAKU) {
				if ('ａ' <= c && c <= 'ｚ') {
					sb.setCharAt(i, (char) (c - 'ａ' + 'a'));
				} else if ('Ａ' <= c && c <= 'Ｚ') {
					sb.setCharAt(i, (char) (c - 'Ａ' + 'A'));
				}
			} else {
				if ('a' <= c && c <= 'z') {
					sb.setCharAt(i, (char) (c - 'a' + 'ａ'));
				} else if ('A' <= c && c <= 'Z') {
					sb.setCharAt(i, (char) (c - 'A' + 'Ａ'));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * かな⇔カナを変換します。
	 * 
	 * @param str
	 *            変換対象文字列
	 * @param type
	 *            HIRAKANA(2), KATAKANA(2)
	 * @return 文字列を変換して返します。
	 */
	public static String cnvKana2(String str, int type) {

		if (str.length() == 0)
			return "";

		StringBuffer sb = new StringBuffer(str);
		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);
			if (type == HIRAKANA) {
				if ('ァ' <= c && c <= 'ン') {
					sb.setCharAt(i, (char) (c - 'ァ' + 'ぁ'));
				} else if (c == 'ヵ') {
					sb.setCharAt(i, 'か');
				} else if (c == 'ヶ') {
					sb.setCharAt(i, 'け');
				} else if (c == 'ヴ') {
					sb.setCharAt(i, 'う');
					sb.insert(i + 1, '゛');
					i++;
				}
			} else {
				if ('ぁ' <= c && c <= 'ん') {
					sb.setCharAt(i, (char) (c - 'ぁ' + 'ァ'));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 半角カタカナから全角カタカナへ変換します。
	 * 
	 * @param str
	 *            変換対象文字列
	 * @return 文字列を変換して返します。
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
	 * 半角カタカナから全角カタカナへ変換します。
	 * 
	 * @param c
	 *            変換前の文字
	 * @return 変換後の文字を返します。
	 */
	private static char cnvKanaChar(char c) {

		if (c >= HAN_KANA_FIRST && c <= HAN_KANA_LAST) {
			return ZEN_KANA[c - HAN_KANA_FIRST];
		} else {
			return c;
		}
	}

	/**
	 * 2文字目が濁点・半濁点で、1文字目に加えることができる場合は、合成した文字を返します。 <br>
	 * 合成ができないときは、c1を返します。
	 * 
	 * @param c1
	 *            変換前の1文字目
	 * @param c2
	 *            変換前の2文字目
	 * @return 変換後の文字を返します。
	 */
	private static char mergeKanaChar(char c1, char c2) {

		if (c2 == 'ﾞ') {
			// "ｶｷｸ..." とするとカの判定が上手く行かない
			if ("_ｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾊﾋﾌﾍﾎ".indexOf(c1) > 0) {
				switch (c1) {
				case 'ｶ':
					return 'ガ';
				case 'ｷ':
					return 'ギ';
				case 'ｸ':
					return 'グ';
				case 'ｹ':
					return 'ゲ';
				case 'ｺ':
					return 'ゴ';
				case 'ｻ':
					return 'ザ';
				case 'ｼ':
					return 'ジ';
				case 'ｽ':
					return 'ズ';
				case 'ｾ':
					return 'ゼ';
				case 'ｿ':
					return 'ゾ';
				case 'ﾀ':
					return 'ダ';
				case 'ﾁ':
					return 'ヂ';
				case 'ﾂ':
					return 'ヅ';
				case 'ﾃ':
					return 'デ';
				case 'ﾄ':
					return 'ド';
				case 'ﾊ':
					return 'バ';
				case 'ﾋ':
					return 'ビ';
				case 'ﾌ':
					return 'ブ';
				case 'ﾍ':
					return 'ベ';
				case 'ﾎ':
					return 'ボ';
				}
			}
		} else if (c2 == 'ﾟ') {
			if ("_ﾊﾋﾌﾍﾎ".indexOf(c1) > 0) {
				switch (c1) {
				case 'ﾊ':
					return 'パ';
				case 'ﾋ':
					return 'ピ';
				case 'ﾌ':
					return 'プ';
				case 'ﾍ':
					return 'ペ';
				case 'ﾎ':
					return 'ポ';
				}
			}
		}
		return c1;
	}

	/**
	 * 文字列に数値以外が含まれていないかチェックします。
	 * 
	 * @return 適正な値であればtrueを返します。
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
	 * 文字列に英字以外が含まれていないかチェックします。
	 * 
	 * @param str
	 *            対象文字列
	 * @return 適正な値であればtrueを返します。
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
