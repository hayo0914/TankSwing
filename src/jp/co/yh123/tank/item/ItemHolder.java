package src.jp.co.yh123.tank.item;

import src.jp.co.yh123.tank.chara.IItemHolderInterface;
import src.jp.co.yh123.tank.collabo.IItemClbInterface;
import src.jp.co.yh123.zlibrary.util.DebugUtil;
import src.jp.co.yh123.zlibrary.util.List;
import src.jp.co.yh123.zlibrary.util.ListComparator;

public class ItemHolder implements IItemHolderInterface {
	/**
	 * This list holds the item instances that this character holds.
	 */
	private List _items = null;

	public ItemHolder(List items) {
		DebugUtil.assertIsNotNull(items);
		_items = items;
	}

	public List getItemList() throws Exception {
		return _items;
	}

	public void sortItems() throws Exception {
		_items.sort(new ListComparator() {
			public boolean compare(Object o1, Object o2) throws Exception {
				IItemClbInterface i1 = (IItemClbInterface) o1;
				IItemClbInterface i2 = (IItemClbInterface) o2;
				if (i1.isEquipped() && !i2.isEquipped()
						&& i2.getSortNum() >= 20) {
					return false;
				} else if (!i1.isEquipped() && i2.isEquipped()
						&& i1.getSortNum() >= 20) {
					return true;
				}
				return i1.getSortNum() > i2.getSortNum();
			}
		});
	}

	public void addItem(IItemClbInterface item) throws Exception {
		_items.add(item);
	}

}
