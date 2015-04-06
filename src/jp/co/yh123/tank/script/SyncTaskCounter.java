package src.jp.co.yh123.tank.script;

import src.jp.co.yh123.tank.logic.ISyncTaskCounter;

public class SyncTaskCounter implements ISyncTaskCounter {

	private int syncCounter = 0;

	public boolean isSync() {
		return false;
	}

	void notifySyncStart() {
		syncCounter++;
	}

	void notifySyncEnd() {
		syncCounter--;
	}
}
