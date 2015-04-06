package src.jp.co.yh123.zlibrary.scriptengine;

import java.util.Vector;

import src.jp.co.yh123.zlibrary.util.DoubleStack;
import src.jp.co.yh123.zlibrary.util.StringStack;

public class ValueCalculator {

	private StringStack var = null;
	private Vector porstr = null;

	public double calcString(String strText) throws Exception {

		if (var == null) {
			var = new StringStack();
		} else {
			var.clear();
		}
		if (porstr == null) {
			porstr = new Vector();
		} else {
			porstr.removeAllElements();
		}
		StringBuffer val = new StringBuffer();
		StringBuffer str = new StringBuffer(strText);

		// 符号を変換する
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '+' || str.charAt(i) == '-') {
				if (i == 0
						|| (i >= 1 && (str.charAt(i - 1) == '+' || str
								.charAt(i - 1) == '-'))) {
					// 符号なので、1つ前に0を入れる。3+-5⇒3+0-5にする
					str.insert(i, '0');
				}
			}
		}

		str.insert(0, "(");
		str.append(")");
		String wk = null;
		while (str.length() != 0) {
			switch (str.charAt(0)) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4': // 数値の場合
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case '.':
				val.append(str.charAt(0));
				break;
			case '+': // +,-の場合は演算子スタックを掃き出す
			case '-':

				if (val.length() != 0) {
					porstr.addElement(val.toString());
				}
				val = new StringBuffer();
				while (var.size() != 0) {
					wk = var.pop();
					if (wk.equals("(")) {
						var.push(wk);
						break;
					}
					porstr.addElement(wk);
				}
				var.push(String.valueOf(str.charAt(0)));
				break;
			case '*': // *./の場合は演算子スタックに追加
			case '/':
				if (var.size() != 0) {
					porstr.addElement(val.toString());
				}
				val = new StringBuffer();
				wk = var.pop();
				if (var.size() != 0 && (wk.equals("*") || wk.equals("/"))) {
					porstr.addElement(wk);
				} else {
					var.push(wk);
				}
				var.push(String.valueOf(str.charAt(0)));
				break;
			case '(':
				var.push(String.valueOf(str.charAt(0)));
				break;
			case ')':
				if (var.size() != 0) {
					porstr.addElement(val.toString());
				}
				val = new StringBuffer();
				while (var.size() != 0) {
					wk = var.pop();
					if (wk.equals("(")) {
						break;
					}
					porstr.addElement(wk);
				}
				break;
			}
			str.deleteCharAt(0);
		}

		// 逆ポーランド記法の計算
		DoubleStack values = new DoubleStack();
		for (int i = 0; i < porstr.size(); i++) {
			String next = ((String) porstr.elementAt(i));
			if (next.length() > 0 && isDigit(next.charAt(0))) {
				values.push(Double.parseDouble(next));
			} else if (next.length() != 0) {
				if (values.size() < 2)
					throw new Exception("エラー:" + strText + ":" + next);
				double ope1, ope2;
				ope2 = values.pop();
				ope1 = values.pop();

				switch (next.charAt(0)) {
				case '-':
					values.push(ope1 - ope2);
					break;
				case '+':
					values.push(ope1 + ope2);
					break;
				case '/':
					values.push(ope1 / ope2);
					break;
				case '*':
					values.push(ope1 * ope2);
					break;
				}
			}
		}

		return values.pop();

	}

	private boolean isDigit(char c) {
		switch (c) {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			return true;
		default:
			return false;
		}
	}

}
