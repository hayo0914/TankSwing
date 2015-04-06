package src.jp.co.yh123.tank.sync;

import src.jp.co.yh123.tank.logic.ISyncTaskCounter;
import src.jp.co.yh123.tank.script.SyncTaskCounter;
import src.jp.co.yh123.zlibrary.util.DebugUtil;

public class SyncTaskCounterFactory {

	private static ISyncTaskCounter syncTaskCounter = null;

	public static ISyncTaskCounter createAndInitSyncTaskCounter() {
		DebugUtil.assertIsNull(syncTaskCounter);
		syncTaskCounter = new SyncTaskCounter();
		return syncTaskCounter;
	}

	public static ISyncTaskCounter getSyncTaskCounter() {
		return syncTaskCounter;
	}

}
