package src.jp.co.yh123.tank.menu;

import src.jp.co.yh123.zlibrary.util.List;

public interface ISimpleSelectMenuInterface {

	public static interface ISimpleSelectMenuCallBackInterface {

		public void selected(String answer) throws Exception;

	}

	public void show(List selectTexts,
			ISimpleSelectMenuCallBackInterface callback) throws Exception;
}
