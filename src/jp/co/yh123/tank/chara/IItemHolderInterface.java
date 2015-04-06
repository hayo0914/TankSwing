package src.jp.co.yh123.tank.chara;

import src.jp.co.yh123.tank.collabo.IItemClbInterface;
import src.jp.co.yh123.zlibrary.util.List;

public interface IItemHolderInterface {

	public List getItemList() throws Exception;

	public void sortItems() throws Exception;

	public void addItem(IItemClbInterface item) throws Exception;

}
