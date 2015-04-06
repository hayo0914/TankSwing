package src.jp.co.yh123.zlibrary.util;

import java.util.Vector;

public class Stack {

	private Vector stack = null;

	public Stack() {
		stack = new Vector(30);
	}

	public void push(Object val) {
		stack.addElement(val);
	}

	public Object pop() throws Exception {
		Object o = stack.elementAt(stack.size() - 1);
		stack.removeElementAt(stack.size() - 1);
		return o;
	}

	public Object top() throws Exception {
		Object o = stack.elementAt(stack.size() - 1);
		return o;
	}

	public int size() {
		return stack.size();
	}

	public void clear() {
		stack.removeAllElements();
	}

}
