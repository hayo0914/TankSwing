package src.jp.co.yh123.zlibrary.util;

public class StringStack {

	private Stack stack = null;

	public StringStack() {
		stack = new Stack();
	}

	public void push(String val) {
		stack.push(val);
	}

	public String pop() throws Exception {
		return (String) stack.pop();
	}

	public String top() throws Exception {
		return (String) stack.top();
	}

	public int size() {
		return stack.size();
	}

	public void clear() {
		stack.clear();
	}

}
