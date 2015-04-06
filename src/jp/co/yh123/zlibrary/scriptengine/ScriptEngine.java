package src.jp.co.yh123.zlibrary.scriptengine;

import src.jp.co.yh123.zlibrary.scriptengine.ScriptEventObserver.ScriptEvent;
import src.jp.co.yh123.zlibrary.util.List;
import src.jp.co.yh123.zlibrary.util.Map;

public abstract class ScriptEngine {

	private Map database = null;

	private Logger logger = null;

	private ValueCalculator calculator = null;

	private ConditionEvaluator conditionEvaluator = null;

	private ThreadPool threadPool = null;

	private List livingThreads = null;

	public ScriptEngine(Map database, int maxThread) {
		logger = new Logger();
		calculator = new ValueCalculator();
		conditionEvaluator = new ConditionEvaluator();
		threadPool = new ThreadPool(maxThread);
		livingThreads = new List(maxThread);
		this.database = database;
	}

	private class Logger {

		private static final int DEBUG = 5;

		private static final int INFO = 3;

		private static final int ERROR = 1;

		private int logLevel = ERROR;

		/**
		 * @return the logLevel
		 */
		public int getLogLevel() {
			return logLevel;
		}

		/**
		 * @param logLevel
		 *            the logLevel to set
		 */
		public void setLogLevel(int logLevel) {
			this.logLevel = logLevel;
		}

		public void debug(Thread th, String message) throws Exception {
			if (getLogLevel() >= DEBUG) {
				System.out.print("script engine:");
				System.out.print("[DEBUG]");
				log(th, message);
			}
		}

		public void error(Thread th, String message) throws Exception {
			if (getLogLevel() >= ERROR) {
				System.out.print("script engine:");
				System.out.print("[ERROR]");
				log(th, message);
			}
		}

		public void info(Thread th, String message) throws Exception {
			if (getLogLevel() >= INFO) {
				System.out.print("script engine:");
				System.out.print("[INFO]");
				log(th, message);
			}
		}

		private void log(Thread th, String message) throws Exception {
			System.out.print("exec count:" + th.execCount + ":");
			System.out.print("exec state:" + th.state.getState() + ":");
			System.out.print("exec index:" + (th.execIndex + 1));
			if (th.execIndex >= 0 && th.execIndex < th.script.countLines()) {
				System.out.print(":current line:\""
						+ th.script.getStringLine(th.execIndex) + "\"");
			}
			System.out.print(":");
			System.out.println(message);
		}

	}

	static class State {
		private int state = INIT;

		private static final int ERROR = -1;

		private static final int INIT = 0;

		private static final int EXEC = 1;

		private static final int WAIT = 2;

		private static final int PAUSE = 3;

		private static final int END = 4;

		private void changeState(ScriptEngine engine, Thread th, int newState)
				throws Exception {
			engine.logger.debug(th, "change state:new state is " + newState);
			state = newState;
			switch (state) {
			case END:
				engine.logger.info(th, "notify END");
				th.observer.notify(th, ScriptEvent.EVENT_END, null);// FIXME:retval
				engine.livingThreads.removeElement(th);
				engine.threadPool.returnThread(th);
				break;
			case ERROR:
				engine.logger.error(th, "notify ERROR");
				th.observer.notify(th, ScriptEvent.EVENT_ERROR, null);
				break;
			case WAIT:
				engine.logger.info(th, "start wait");
				break;
			case PAUSE:
				engine.logger.info(th, "engine paused");
				break;
			case INIT:
			}
		}

		public int getState() {
			return state;
		}
	}

	protected void pause(Thread th) throws Exception {
		if (th.state.getState() == State.EXEC) {
			th.state.changeState(this, th, State.PAUSE);
		} else {
			logger.error(th, "pause is called but its not on exec state");
		}
	}

	protected void resume(Thread th) throws Exception {
		logger.info(th, "resume is called");
		switch (th.state.getState()) {
		case State.PAUSE:
			th.state.changeState(this, th, State.EXEC);
		default:
			return;
		}
	}

	public void setScript(Script script, String function, Map input,
			ScriptEventObserver observer) throws Exception {
		if (threadPool.availableTheads() == 0) {
			throw new Exception("no thread available");
		}
		Thread th = threadPool.getThead();
		th.script = script;
		th.functionName = function;
		th.input = input;
		th.output.clear();
		th.observer = observer;
		th.state.changeState(this, th, State.EXEC);
		th.execIndex = -1;
		th.execCount = 0;
		th.stack.clear();
		livingThreads.add(th);
		observer.notify(th, ScriptEvent.EVENT_START, null);
		// search function
		searchFunction(th);

	}

	public void changeLogLevel(int level) {
		logger.setLogLevel(level);
	}

	public boolean isOnProcess() {
		for (int i = 0; i < livingThreads.size(); i++) {
			Thread th = (Thread) livingThreads.elementAt(i);
			if (isOnProcess(th)) {
				return true;
			}
		}
		return false;
	}

	private boolean isOnProcess(Thread th) {
		switch (th.state.getState()) {
		case State.INIT:
			return false;
		case State.END:
			return false;
		case State.EXEC:
			return true;
		case State.WAIT:
			return true;
		case State.PAUSE:
			return true;
		case State.ERROR:
			return false;
		default:
			// FIXME:
			return false;
		}
	}

	public boolean isOnWait(Thread th) {
		switch (th.state.getState()) {
		case State.WAIT:
			return true;
		default:
			return false;
		}
	}

	public void exec() throws Exception {
		for (int i = 0; i < livingThreads.size(); i++) {
			Thread th = (Thread) livingThreads.elementAt(i);
			exec(th);
		}
	}

	private void exec(Thread th) throws Exception {
		switch (th.state.getState()) {
		case State.END:
			return;
		case State.PAUSE:
			return;
		case State.ERROR:
			return;
		case State.WAIT:
			checkWait(th);
			return;
		}
		if (!onExecCalled()) {
			logger.info(th, "exec canceled by descendant engine");
			return;
		}

		logger.info(th, "exec start");
		th.state.changeState(this, th, State.EXEC);
		try {
			while (th.state.getState() == State.EXEC) {
				logger.debug(th, "interpret line start");
				interpretNextLine(th);
				logger.debug(th, "interpret line end");
			}
		} catch (Exception e) {
			e.printStackTrace();
			th.state.changeState(this, th, State.ERROR);
		}
		logger.info(th, "exec end");

	}

	protected void skipLine(Thread th) throws Exception {
		logger.debug(th, "skip line");
		th.execIndex++;
	}

	protected void backLine(Thread th) throws Exception {
		logger.debug(th, "back line");
		th.execIndex--;
	}

	protected ScriptLine getNextLine(Thread th) throws Exception {
		logger.debug(th, "get next line");
		th.execIndex++;
		if (th.execIndex >= th.script.countLines()) {
			logger.error(th, "it's line end");
			th.state.changeState(this, th, State.ERROR);
			return null;
		} else {
			return th.script.getLine(th.execIndex);
		}
	}

	protected ScriptLine lookNextLine(Thread th) throws Exception {
		logger.debug(th, "look next line");
		if (th.execIndex >= th.script.countLines()) {
			logger.error(th, "it's line end");
			th.state.changeState(this, th, State.ERROR);
			return null;
		} else {
			return th.script.getLine(th.execIndex + 1);
		}
	}

	private void startWait(Thread th, long waitTime) throws Exception {
		th.waitStartTime = System.currentTimeMillis();
		th.waitTime = waitTime;
		th.state.changeState(this, th, State.WAIT);
	}

	private void checkWait(Thread th) throws Exception {
		long currentTime = System.currentTimeMillis();
		if (currentTime >= th.waitTime + th.waitStartTime) {
			logger.debug(th, "wait end");
			th.state.changeState(this, th, State.EXEC);
		} else {
			// do nothing
		}
	}

	private void searchFunction(Thread th) throws Exception {

		logger.info(th, "search function");
		for (int i = 0; i < th.script.countLines(); i++) {
			ScriptLine sl = th.script.getLine(i);
			String str = sl.nextToken();
			if (str.equals("function")) {
				str = sl.nextToken();
				if (str.equals(th.functionName)) {
					logger.info(th, "function found:" + th.functionName);
					th.execIndex = i;
					return;
				}
			}
		}

		throw new Exception("no such function:" + th.functionName);
	}

	protected int currentIndex(Thread th) {
		return th.execIndex;
	}

	protected void skipTo(Thread th, int toIndex) throws Exception {
		while (currentIndex(th) <= toIndex) {
			skipLine(th);
		}
	}

	protected void backTo(Thread th, int toIndex) throws Exception {
		while (currentIndex(th) >= toIndex) {
			backLine(th);
		}
	}

	// private int getSmallest(int i, int j, int k) {
	// if (i <= j) {
	// if (i <= k) {
	// return i;
	// } else {
	// if (k <= j) {
	// return k;
	// } else {
	// return j;
	// }
	// }
	// } else {
	// if (k <= j) {
	// return k;
	// } else {
	// return j;
	// }
	// }
	// }

	protected void interpretNextLine(Thread th) throws Exception {
		ScriptLine sl = getNextLine(th);
		// system
		if (!sl.hasNext()) {
			logger.debug(th, "there's no token");
			return;
		}

		String str = sl.nextToken();
		logger.debug(th, sl.getLine());
		if (str.equals("endfunction")) {
			logger.info(th, "endfunction found");
			th.state.changeState(this, th, State.END);
		} else if (str.equals("sysout")) {
			logger.debug(th, "sysout found");
			System.out.println(getValue(th, sl));
		} else if (str.equals("calc")) {
			logger.debug(th, "calc found");
			String val = getValue(th, sl);
			double result = calc(th, val);
			th.stack.push(String.valueOf(result));
		} else if (str.equals("stackpush")) {
			logger.debug(th, "stackpush found");
			String val = getValue(th, sl);
			th.stack.push(val);
		} else if (str.equals("endif")) {
			logger.debug(th, "endif found, do nothing here");
		} else if (str.equals("stacksize")) {
			logger.debug(th, "stacksize found");
			th.stack.push(String.valueOf(th.stack.size()));
		} else if (str.equals("datapush")) {
			logger.debug(th, "datapush found");
			String key = getValue(th, sl.nextToken());
			String val = getValue(th, sl);
			logger.debug(th, key);
			logger.debug(th, val);
			database.addValue(key, val);
		} else if (str.equals("dataget")) {
			logger.debug(th, "dataget found");
			String key = getValue(th, sl);
			logger.debug(th, key);
			th.stack.push((String) database.getValue(key));
		} else if (str.equals("dataremove")) {
			logger.debug(th, "dataremove found");
			String key = getValue(th, sl);
			logger.debug(th, key);
			database.removeValue(key);
		} else if (str.equals("outpush")) {
			logger.debug(th, "outpush found");
			String key = getValue(th, sl.nextToken());
			String val = getValue(th, sl);
			logger.debug(th, key);
			logger.debug(th, val);
			th.output.addValue(key, val);
		} else if (str.equals("outget")) {
			logger.debug(th, "outget found");
			String key = getValue(th, sl);
			logger.debug(th, key);
			th.stack.push((String) th.output.getValue(key));
		} else if (str.equals("outremove")) {
			logger.debug(th, "outremove found");
			String key = getValue(th, sl);
			logger.debug(th, key);
			th.output.removeValue(key);
		} else if (str.equals("outclear")) {
			logger.debug(th, "outclear found");
			th.output.clear();
		} else if (str.equals("inget")) {
			logger.debug(th, "inget found");
			String key = getValue(th, sl);
			logger.debug(th, key);
			th.stack.push((String) th.input.getValue(key));
		} else if (str.equals("goto")) {
			logger.debug(th, "goto found");
			String label = ":" + sl.nextToken();
			logger.debug(th, "search line start");
			int toIndex = th.script.searchLine(label, currentIndex(th));
			if (toIndex >= 0) {
				logger.debug(th, "label found");
				skipTo(th, toIndex);
			} else {
				logger.debug(th, "label not found" + label);
			}
		} else if (str.equals("backto")) {
			logger.debug(th, "backto found");
			String label = ":" + sl.nextToken();
			logger.debug(th, "search line start");
			int toIndex = th.script.searchLineToBack(label, currentIndex(th));
			if (toIndex >= 0) {
				logger.debug(th, "label found");
				backTo(th, toIndex);
			} else {
				logger.debug(th, "label not found" + label);
			}
		} else if (str.equals("wait")) {
			logger.debug(th, "wait found");
			long wait = Long.parseLong(getValue(th, sl));
			startWait(th, wait);
		} else if (str.equals("if") || str.equals("elseif")) {
			logger.debug(th, "if found");
			String cond = getValue(th, sl);
			if (conditionEvaluator.evaluate(cond)) {
				logger.debug(th, "condition is true");
				sl = lookNextLine(th);
				// exec to else,elseif,endif
				logger.debug(th, "start else search");
				lp: while (true) {
					sl = lookNextLine(th);
					if (sl.countToken() > 0) {
						String chk = sl.tokenAt(0);
						if (chk.equals("elseif") || chk.equals("else")) {
							logger.debug(th, "else found after true");
							break lp;
						} else if (chk.equals("endif")) {
							logger.debug(th, "endif found after true");
							backLine(th);
							break lp;
						} else if (chk.equals("goto") || chk.equals("backto")) {
							logger.debug(th, "goto found in if statement");
							interpretNextLine(th);
							return;
							// break lp;
						} else {
							interpretNextLine(th);
						}// end if
					} else {// token=0
						interpretNextLine(th);
					}// end if
				}// end while
				logger.debug(th, "end else search");
				logger.debug(th, "start endif search");
				// skip to endif
				int toIndex = th.script.searchLine("endif", currentIndex(th));
				if (toIndex >= 0) {
					logger.debug(th, "endif found");
					skipTo(th, toIndex);
				} else {
					logger.debug(th, "endif not found");
				}
				logger.debug(th, "end endif search");
			} else {// condition = false
				logger.debug(th, "condition is false");
				// skip to elseif,endif,else
				lp: while (true) {
					sl = lookNextLine(th);
					if (sl.countToken() > 0) {
						String chk = sl.nextToken();
						if (chk.equals("elseif") || chk.equals("endif")
								|| chk.equals("else")) {
							break lp;
						} else {
							skipLine(th);
						}
					} else {
						skipLine(th);
					}
				}// end while
			}// end if
		} else {
			logger.debug(th, "no system library found");
			sl.init();
			onNoSystemLibrary(th, sl);
		}
		logger.debug(th, "interpret end");

	}

	/**
	 * @return return false if is need to exec cancel;
	 * @throws Exception
	 */
	protected abstract boolean onExecCalled() throws Exception;

	protected abstract void onNoSystemLibrary(Thread th, ScriptLine currentLine)
			throws Exception;

	protected double calc(Thread th, String value) throws Exception {
		logger.debug(th, value);
		return calculator.calcString(value);
	}

	private String stackPop(Thread th) throws Exception {
		if (th.stack.size() == 0) {
			logger.error(th, "pop:stack is empty!");
			throw new Exception("pop:stack is empty!");
		} else {
			return th.stack.pop();
		}
	}

	private String stackTop(Thread th) throws Exception {
		if (th.stack.size() == 0) {
			logger.error(th, "top:stack is empty!");
			throw new Exception("top:stack is empty!");
		} else {
			String ret = th.stack.top();
			return ret;
		}
	}

	private static final String CODE_POP = "stackpop";

	private static final String CODE_TOP = "stacktop";

	protected String getValue(Thread th, String str) throws Exception {
		StringBuffer sb = new StringBuffer(str);

		int index = str.indexOf("stack");
		String wkStr = str;
		replace: while (index != -1) {
			int popindex = wkStr.indexOf(CODE_POP, index);
			int topindex = wkStr.indexOf(CODE_TOP, index);
			if (popindex != -1 && topindex != -1) {
				if (popindex < topindex) {
					sb.delete(index, index + CODE_POP.length());
					sb.insert(index, stackPop(th));
				} else {
					sb.delete(index, index + CODE_TOP.length());
					sb.insert(index, stackTop(th));
				}
			} else if (popindex != -1) {
				sb.delete(index, index + CODE_POP.length());
				sb.insert(index, stackPop(th));
			} else if (topindex != -1) {
				sb.delete(index, index + CODE_TOP.length());
				sb.insert(index, stackTop(th));
			} else {
				break replace;
			}
			wkStr = sb.toString();
			index = wkStr.indexOf("stack");
		}
		// int index = sb.indexOf("stack");
		// replace: while (index != -1) {
		// int popindex = sb.indexOf(CODE_POP, index);
		// int topindex = sb.indexOf(CODE_TOP, index);
		// if (popindex != -1 && topindex != -1) {
		// if (popindex < topindex) {
		// sb.replace(index, index + CODE_POP.length(), stackPop());
		// } else {
		// sb.replace(index, index + CODE_TOP.length(), stackTop());
		// }
		// } else if (popindex != -1) {
		// sb.replace(index, index + CODE_POP.length(), stackPop());
		// } else if (topindex != -1) {
		// sb.replace(index, index + CODE_TOP.length(), stackTop());
		// } else {
		// break replace;
		// }
		// index = sb.indexOf("stack");
		// }

		logger.debug(th, "analyse value end");
		return sb.toString();
	}

	protected String getValue(Thread th, ScriptLine sl) throws Exception {
		logger.debug(th, "getValue");
		StringBuffer sb = new StringBuffer();
		while (sl.hasNext()) {
			sb.append(sl.nextToken());
		}
		String rtn = sb.toString();
		logger.debug(th, rtn);
		return getValue(th, sb.toString());
	}

}
