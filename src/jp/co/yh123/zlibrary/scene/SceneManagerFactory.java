package src.jp.co.yh123.zlibrary.scene;

import src.jp.co.yh123.zlibrary.util.DebugUtil;

public class SceneManagerFactory {

	private static SceneManager _sceneMgr = null;

	public static void createInstance() {
		DebugUtil.assertIsNull(_sceneMgr);
		_sceneMgr = new SceneManager();
	}

	public static SceneManager getSceneManager() {
		DebugUtil.assertIsNotNull(_sceneMgr);
		return _sceneMgr;
	}

}
