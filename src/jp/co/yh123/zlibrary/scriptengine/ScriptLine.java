package src.jp.co.yh123.zlibrary.scriptengine;

import java.util.Vector;


public class ScriptLine {

	private StringReader sr = null;
	private int index = 0;
	private Vector tokens = null;
	private String line = null;

	/**
	 * @return the line
	 */
	public String getLine() {
		return line;
	}

	/**
	 * @param line
	 *            the line to set
	 */
	public void setLine(String line) {
		this.line = line;
	}

	private class StringReader {

		private int index = -1;
		private int size = 0;
		private String line = null;

		public StringReader(String line) {
			this.line = line;
			size = line.length();
			init();
		}

		public char nextChara() {
			index++;
			return line.charAt(index);
		}

		public boolean hasNext() {
			if (index < size - 1) {
				return true;
			} else {
				return false;
			}
		}

	}

	ScriptLine(String line) throws Exception {
		setLine(line);
		tokenize(line);
	}

	int countToken() {
		if (tokens == null) {
			return 0;
		} else {
			return tokens.size();
		}
	}

	void init() {
		index = -1;
	}

	private void tokenize(String line) throws Exception {
		index = -1;
		if (line == null || line.length() == 0) {
			return;
		}
		tokens = new Vector(10);
		sr = new StringReader(line);

		StringBuffer sb = new StringBuffer();
		while (sr.hasNext()) {
			char c = sr.nextChara();
			switch (c) {
			case '#':
				if (sb.length() == 0 && tokens.size() == 0) {
					// コメント行のため、行全体を無視する。
					return;
				} else {
					sb.append(c);
					continue;
				}
			case '\t':
				if (sb.length() == 0) {
					// バッファに何もなければ無視
					continue;
				} else {
					// 単語区切り
					tokens.addElement(sb.toString());
					sb = new StringBuffer();
					continue;
				}
			case '\r':
				// 無視
				continue;
			case '\n':
				// 無視
				continue;
			case ' ':
				if (sb.length() == 0) {
					// バッファに何もなければ無視
					continue;
				} else {
					// 単語区切り
					tokens.addElement(sb.toString());
					sb = new StringBuffer();
					continue;
				}
			case '"':
				if (sb.length() == 0) {
					// バッファに何もなければ文字列の開始
					parseText(sb, sr);
					tokens.addElement(sb.toString());
					sb = new StringBuffer();
					continue;
				} else {
					// 不正
					System.out.println("script line error:");
					System.out.println(sb.toString() + ":" + String.valueOf(c));
					tokens = null;
					return;
				}
			default:
				// 単語の一部
				sb.append(c);
				break;

			}

		}
		if (sb.length() != 0) {
			tokens.addElement(sb.toString());
		}

	}

	private void parseText(StringBuffer sb, StringReader sr) {

		while (sr.hasNext()) {

			char c = sr.nextChara();
			if (c == '"') {
				// 文字列の終了
				return;
			} else {
				sb.append(c);
			}

		}

	}

	public boolean hasNext() {

		if (tokens == null) {
			return false;
		}

		if (index < tokens.size() - 1) {
			return true;
		} else {
			return false;
		}
	}

	public String tokenAt(int colIndex) throws Exception {
		return (String) tokens.elementAt(colIndex);
	}

	public String nextToken() {
		index++;
		return (String) tokens.elementAt(index);
	}

	public String lookNextToken() {
		return (String) tokens.elementAt(index + 1);
	}

	public String getAllToken() {
		StringBuffer sb = new StringBuffer();
		while (hasNext()) {
			sb.append(nextToken());
		}
		return sb.toString();
	}

}
