package src.jp.co.yh123.tank.menu;

import src.jp.co.yh123.tank.collabo.IItemClbInterface;
import src.jp.co.yh123.zlibrary.util.List;

public interface IItemListMenuInterface {

	public static interface IItemMenuCallBackInterface {

		public void selected(IItemClbInterface selectedItem) throws Exception;

	}

	public void show(List itemList, IItemMenuCallBackInterface callback)
			throws Exception;

	public void end() throws Exception;

}
