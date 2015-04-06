package src.jp.co.yh123.zlibrary.scriptengine;

public class ConditionEvaluator {

	private ValueCalculator vc = new ValueCalculator();

	private String strBefore = "";

	private String strAfter = "";

	private static String[] condArray = new String[] { "==", "<=", "<", ">=",
			">", };

	public boolean evaluate(String str) throws Exception {
		if (vc == null) {
			vc = new ValueCalculator();
		}

		// ‘OŒã‚É•ªŠ„
		// StringBuffer sb = new StringBuffer(str);
		String cond = null;
		int condIndex = 0;
		boolean result = false;
		for (condIndex = 0; condIndex < condArray.length; condIndex++) {
			cond = condArray[condIndex];
			if (concat(str, cond)) {
				result = true;
				break;
			}
			// if (concat(sb, cond)) {
			// result = true;
			// break;
			// }
		}
		if (!result) {
			throw new Exception("invalid condition:" + str);
		}

		// •]‰¿
		boolean isDigit = false;
		double before = 0;
		double after = 0;
		try {
			before = vc.calcString(strBefore);
			after = vc.calcString(strAfter);
			isDigit = true;
		} catch (Exception e) {
			isDigit = false;
		}
		// "==", "<", "<=", ">", ">="
		if (isDigit) {
			if (cond.equals("==")) {
				return before == after;
			} else if (cond.equals("<")) {
				return before < after;
			} else if (cond.equals("<=")) {
				return before <= after;
			} else if (cond.equals(">")) {
				return before > after;
			} else if (cond.equals(">=")) {
				return before >= after;
			} else {
				throw new Exception("invalid condition:" + str);
			}
		} else {
			if (cond.equals("==")) {
				return strBefore.equals(strAfter);
			} else if (cond.equals("<")) {
				return (strBefore.compareTo(strAfter) < 0);
			} else if (cond.equals("<=")) {
				return (strBefore.compareTo(strAfter) <= 0);
			} else if (cond.equals(">")) {
				return (strBefore.compareTo(strAfter) > 0);
			} else if (cond.equals(">=")) {
				return (strBefore.compareTo(strAfter) >= 0);
			} else {
				throw new Exception("invalid condition:" + str);
			}
		}

	}

	private boolean concat(String str, String cond) {
		int index = str.indexOf(cond);
		if (index != -1) {
			strBefore = str.substring(0, index);
			strAfter = str.substring(index + cond.length(), str.length());
			return true;
		} else {
			return false;
		}

	}

}
