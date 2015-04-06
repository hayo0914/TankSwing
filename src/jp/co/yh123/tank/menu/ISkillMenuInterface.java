package src.jp.co.yh123.tank.menu;

import src.jp.co.yh123.zlibrary.util.DataArray;

public interface ISkillMenuInterface {

	public static final int MENU_PL_CANCEL = -1;

	public static final int MENU_PL_ITEM = 0;

	public static final int MENU_PL_ASHIMOTO = 1;

	public static final int MENU_PL_STATUS = 2;

	public static final int MENU_PL_SKILL = 3;

	public static interface ISkillMenuCallBackInterface {

		public void selected(int choice) throws Exception;

	}

	public void show(DataArray skillList, ISkillMenuCallBackInterface callback)
			throws Exception;

}
