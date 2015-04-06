package src.jp.co.yh123.zlibrary.scriptengine;

import src.jp.co.yh123.zlibrary.scriptengine.ScriptEngine.State;
import src.jp.co.yh123.zlibrary.util.Map;
import src.jp.co.yh123.zlibrary.util.StringStack;

public class Thread {

	int execIndex = -1;

	State state = null;

	int execCount = 0;

	long waitStartTime = 0;

	long waitTime = 0;

	StringStack stack = null;

	public StringStack getStack() {
		return stack;
	}

	Map output = new Map();

	Map input = null;

	ScriptEventObserver observer = null;

	Script script = null;

	String functionName = null;

	public Thread() {
	}

	void init() {
		state = new State();
		stack = new StringStack();
		output.clear();
		input = null;
	}

	public Object getInput(String key) throws Exception {
		return input.getValue(key);
	}

	public Object getOutput(String key) throws Exception {
		return output.getValue(key);
	}

	public void addInput(String key, String val) throws Exception {
		input.addValue(key, val);
	}

	public void addOutput(String key, String val) throws Exception {
		output.addValue(key, val);
	}

	public boolean countainInput(String key) throws Exception {
		return input.contain(key);
	}

	public boolean countainOutput(String key) throws Exception {
		return output.contain(key);
	}

	public void removeInput(String key) throws Exception {
		input.removeValue(key);
	}

	public void removeOutput(String key) throws Exception {
		output.removeValue(key);
	}
}
