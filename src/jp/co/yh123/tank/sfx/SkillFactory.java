package src.jp.co.yh123.tank.sfx;

import src.jp.co.yh123.zlibrary.util.DebugUtil;

public class SkillFactory {

	private static Skill instance = null;

	public static void createInstance() {
		DebugUtil.assertIsNull(instance);
		instance = new Skill();
	}

	public static Skill getInstance() {
		DebugUtil.assertIsNotNull(instance);
		return instance;
	}

}
