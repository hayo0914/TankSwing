package src.jp.co.yh123.zlibrary.scriptengine;

public interface ScriptEventObserver {

	public static class ScriptEvent {

		public static final int EVENT_END = 0;
		public static final int EVENT_ERROR = 1;
		public static final int EVENT_START = 2;

		private ScriptEvent() {

		}

		// private StringMap returnValues = null;
		//
		// public ScriptEvent(int type) {
		// setType(type);
		// }
		//
		// public ScriptEvent(int type, StringMap returnValues) {
		// setType(type);
		// setReturnValues(returnValues);
		// }
		//
		// private int type = -1;
		//
		// /**
		// * @return the type
		// */
		// public int getType() {
		// return type;
		// }
		//
		// /**
		// * @param type
		// * the type to set
		// */
		// public void setType(int type) {
		// this.type = type;
		// }
		//
		// /**
		// * @return the returnValues
		// */
		// public StringMap getReturnValues() {
		// return returnValues;
		// }
		//
		// /**
		// * @param returnValues
		// * the returnValues to set
		// */
		// public void setReturnValues(StringMap returnValues) {
		// this.returnValues = returnValues;
		// }
	}

	public void notify(Thread th, int event, String returnValue)
			throws Exception;

}
