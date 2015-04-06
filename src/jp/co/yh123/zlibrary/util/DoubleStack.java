package src.jp.co.yh123.zlibrary.util;

public class DoubleStack {

	private Stack stack = null;

	public DoubleStack() {
		stack = new Stack();
	}

	public void push(double val) {
		stack.push(new Double(val));
	}

	public double pop() throws Exception {
		return ((Double) stack.pop()).doubleValue();
	}

	public double top() throws Exception {
		return ((Double) stack.top()).doubleValue();
	}

	public int size() {
		return stack.size();
	}

	public void clear() {
		stack.clear();
	}

}
