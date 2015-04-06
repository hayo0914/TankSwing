package src.jp.co.yh123.zlibrary.util;

public class DebugUtil {

	public static void assertEqual(int a, int b) {
		if (a != b)
			error();
	}

	public static void assertIsNull(Object o) {
		if (o != null)
			error();
	}

	public static void assertIsNotNull(Object o) {
		if (o == null)
			error();
	}

	public static void assertLarger(int a, int b) {
		if (a <= b)
			error();
	}

	public static void assertSmaller(int a, int b) {
		if (a >= b)
			error();
	}

	public static void assertEqual(Object a, Object b) {
		if (a != b)
			error();
	}

	public static void assertTrue(boolean a) {
		if (!a)
			error();
	}

	public static void assertFalse(boolean a) {
		if (a)
			error();
	}

	public static void debugLog(String category, String message) {
		StringBuffer sb = new StringBuffer(category);
		sb.insert(0, "[");
		sb.append("]");
		sb.append(message);
		System.out.println(sb.toString());
	}

	public static void debugLog(String category, String category2,
			String message) {
		StringBuffer sb = new StringBuffer(category);
		sb.insert(0, "[");
		sb.append("]");
		sb.append("(");
		sb.append(category2);
		sb.append(")");
		sb.append(message);
		System.out.println(sb.toString());
	}

	public static void error() {
		System.out.println(1 / 0);
	}

	public static void error(String message) {
		System.out.println(message);
		error();
	}

}
