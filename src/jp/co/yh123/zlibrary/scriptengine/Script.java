package src.jp.co.yh123.zlibrary.scriptengine;

import java.util.Vector;

import src.jp.co.yh123.zlibrary.util.StringTokenizer;

public class Script {

	private Vector scriptStringLines = null;

	public Script(String script) throws Exception {

		if (script == null || script.length() == 0) {
			return;
		}

		// ScriptLineごとに分割
		scriptStringLines = new Vector(100);

		StringTokenizer st = new StringTokenizer(script, "\r\n");

		while (st.hasMoreTokens()) {
			String nextToken = st.nextToken();
			if (nextToken.length() == 0) {
				continue;
			} else {
				scriptStringLines.addElement(nextToken);
			}
		}

	}

	/**
	 * @param firstToken
	 * @param indexFrom
	 *            この行は検索対象に含みます。
	 * @return 見つからない場合は-1を返却
	 */
	public int searchLine(String firstToken, int indexFrom) throws Exception {
		int searchLine = indexFrom;
		while (searchLine < countLines()) {
			ScriptLine sl = getLine(searchLine);
			if (sl.countToken() > 0 && sl.tokenAt(0).equals(firstToken)) {
				return searchLine;
			} else {
				searchLine++;
			}
		}
		return -1;
	}

	/**
	 * @param firstToken
	 * @param indexFrom
	 *            この行は検索対象に含みます。
	 * @return 見つからない場合は-1を返却
	 */
	public int searchLineToBack(String firstToken, int indexFrom)
			throws Exception {
		int searchLine = indexFrom;
		while (searchLine >= 0) {
			ScriptLine sl = getLine(searchLine);
			if (sl.countToken() > 0 && sl.tokenAt(0).equals(firstToken)) {
				return searchLine;
			} else {
				searchLine--;
			}
		}
		return -1;
	}

	public int countLines() {
		if (scriptStringLines == null) {
			return 0;
		} else {
			return scriptStringLines.size();
		}
	}

	public String getStringLine(int index) {
		String line = (String) scriptStringLines.elementAt(index);
		return line;
	}

	public ScriptLine getLine(int index) throws Exception {
		return new ScriptLine((String) scriptStringLines.elementAt(index));
	}

}
