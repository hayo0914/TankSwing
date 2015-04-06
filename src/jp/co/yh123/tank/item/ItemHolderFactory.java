package src.jp.co.yh123.tank.item;

import src.jp.co.yh123.tank.chara.IItemHolderInterface;
import src.jp.co.yh123.zlibrary.util.List;

public class ItemHolderFactory {

	public static IItemHolderInterface createItemHolder(List items) {
		return new ItemHolder(items);
	}

}
