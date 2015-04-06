package src.jp.co.yh123.tank.menuimpl;

import src.jp.co.yh123.tank.collabo.IItemClbInterface;
import src.jp.co.yh123.tank.menu.IItemListMenuInterface;
import src.jp.co.yh123.tank.resource.Res;
import src.jp.co.yh123.zlibrary.platform.ActionEventAdapter;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.platform.HmFont;
import src.jp.co.yh123.zlibrary.scene.ISceneInterface;
import src.jp.co.yh123.zlibrary.scene.SceneManagerFactory;
import src.jp.co.yh123.zlibrary.util.Csv;
import src.jp.co.yh123.zlibrary.util.DataArray;
import src.jp.co.yh123.zlibrary.util.List;
import src.jp.co.yh123.zlibrary.view.AbsolutePositonLayout;
import src.jp.co.yh123.zlibrary.view.Menu;
import src.jp.co.yh123.zlibrary.view.MenuItem;
import src.jp.co.yh123.zlibrary.view.TableMenu;
import src.jp.co.yh123.zlibrary.view.ViewGroup;
import src.jp.co.yh123.zlibrary.view.Menu.MenuEventListener;
import src.jp.co.yh123.zlibrary.view.TableMenu.TableMenuAttribute;

public class ItemListMenuScene implements ISceneInterface,
		IItemListMenuInterface {

	private int _execCount = 0;
	private ItemStat _itemStat = null;
	private ItemList _itemList = null;
	private IItemMenuCallBackInterface _callback = null;
	private boolean _isEnd = false;

	public ItemListMenuScene() {
		// do nothing
	}

	public void end() throws Exception {
		_isEnd = true;
	}

	public void show(List itemList, IItemMenuCallBackInterface callback)
			throws Exception {
		_callback = callback;
		_execCount = 0;
		_itemList = new ItemList(itemList, new MenuEventListener() {
			public void menuSelected(Menu menu, MenuItem menuItem)
					throws Exception {
				if (menu == _itemList._itemMenu) {
					_callback.selected(((ItemListMenuItem) menuItem).getItem());
				}
			}
		});

		_itemStat = new ItemStat();
		SceneManagerFactory.getSceneManager().add(this);
	}

	private class ItemList {
		static final int ITEM_LIST_SPD_X = 20;
		int x = 0;
		int y = 0;
		MenuItem _lastFocusItem = null;
		Menu _itemMenu = null;
		int _itemListOffsetX = 100;
		ViewGroup _rootView = null;

		private ItemList(List itemList, MenuEventListener l) throws Exception {

			_rootView = new AbsolutePositonLayout();

			x = 140;
			y = 18;

			_lastFocusItem = null;
			TableMenuAttribute att = new TableMenuAttribute();
			int viewRow = 7;
			int viewCol = 3;
			att.setViewRow(viewRow);
			att.setViewCol(viewCol);
			Menu menu = new TableMenu(att);

			int itemNum = itemList.size();
			for (int i = 0; i < itemNum; i++) {
				IItemClbInterface item = (IItemClbInterface) itemList
						.elementAt(i);
				menu.add(new ItemListMenuItem(i, item));
			}

			int unit = viewCol * viewRow;
			int rest = ((itemNum / unit) + 1) * unit - itemNum;
			for (int i = 0; i < rest; i++) {
				menu.add(new ItemListMenuItem(i, null));
			}

			menu.setMenuEventListener(l);
			_itemMenu = menu;
			_rootView.addView(_itemMenu, true);
		}

		void update(boolean isTop) throws Exception {

			if (_itemListOffsetX != 0) {
				_itemListOffsetX -= ITEM_LIST_SPD_X;
				if (_itemListOffsetX < 0) {
					_itemListOffsetX = 0;
				}
			}
			// フォーカス変更チェック
			MenuItem i = _itemMenu.getCurrentSelect();
			if (i != _lastFocusItem) {
				_lastFocusItem = i;
				IItemClbInterface item = ((ItemListMenuItem) _lastFocusItem)
						.getItem();
				_itemStat.setItem(item);
			}

			_rootView.update();
			if (isTop) {
				if (!isEnd() && _execCount > 5) {
					_rootView.keyAction(ActionEventAdapter.getKeyState());
					_rootView.touchAction(ActionEventAdapter.getTouchState());
				}
			}
		}

		void draw(GameGraphic g) throws Exception {
			_itemMenu.setPosition(x + (-1 * _itemListOffsetX), y);
			_rootView.draw(g);
		}
	}

	private class ItemStat {
		IItemClbInterface _item = null;
		int x = 0;
		int y = 0;
		final int WIDTH = 130;
		final int HEIGHT = 240;

		ItemStat() {
			setX(5);
			setY(10);
		}

		void setItem(IItemClbInterface item) {
			_item = item;
		}

		public void update() throws Exception {

		}

		void draw(GameGraphic g) throws Exception {

			// g.setColor(0x000000);
			// g.setColor(0xB5862B);// 茶色
			g.setColor(0x545C64);// グレー
			g.fillRect(x, y, WIDTH, HEIGHT);

			g.setColor(0xffffff);
			g.drawRect(x, y, WIDTH, HEIGHT);
			// int titleColor = 0xaaaaff;
			// int valueColor = 0xffffff;
			int titleColor = 0xaaaaff;
			int valueColor = 0xdddddd;
			int enchaColor = 0x22bb00;
			int bgColor = 0x222222;

			if (_item == null) {
				return;
			}
			Csv csvItem = Res.get().csvItem;
			// Animation itemImg = _item.getAnime();
			int offX = x + 4;
			int offY = y + 4;
			int offSetWidth = 60;
			// itemImg.setPosition(offX, offY);
			// itemImg.draw(g, 0, 0);
			// offX += 34;
			g.setColor(valueColor);
			HmFont.setFont(g, HmFont.STYLE_PLAIN, HmFont.FONT_SMALL);
			g.drawBorderString(_item.getNameWithCount(), offX, offY,
					valueColor, bgColor);
			// FIXME:cache化
			if (_item.getItemType() == IItemClbInterface.ITEM_EQUIP) {
				//
				if (_item.getEquipType() != IItemClbInterface.EQUIP_TYPE1_SHOT_MATERIAL) {
					offY += HmFont.getFontHeight() + 2;
					// g.setColor(titleColor);
					g.drawBorderString("品質", offX, offY, titleColor, bgColor);
					// g.setColor(valueColor);
					g.drawBorderString(String.valueOf(_item
							.getEquipQualityText()), offX + offSetWidth, offY,
							valueColor, bgColor);
				}
				//
				if (_item.getEquipType() == IItemClbInterface.EQUIP_TYPE1_WEAPON
						|| _item.getEquipType() == IItemClbInterface.EQUIP_TYPE1_SHOOTER) {
					offY += HmFont.getFontHeight() + 2;
					// g.setColor(titleColor);
					g.drawBorderString("攻撃力", offX, offY, titleColor, bgColor);
					// g.setColor(valueColor);
					g.drawBorderString(String.valueOf(_item.getEquipDmgFrom())
							+ " - " + String.valueOf(_item.getEquipDmgTo()),
							offX + offSetWidth, offY, valueColor, bgColor);
					//
					offY += HmFont.getFontHeight() + 2;
					// g.setColor(titleColor);
					g
							.drawBorderString("致傷率補正", offX, offY, titleColor,
									bgColor);
					// g.setColor(valueColor);
					if (_item.getEquipHitAssist() >= 0) {
						g.drawBorderString(String.valueOf("+"
								+ _item.getEquipHitAssist() + "%"), offX
								+ offSetWidth, offY, valueColor, bgColor);
					} else {
						g.drawBorderString(String.valueOf(_item
								.getEquipHitAssist()
								+ "%"), offX + offSetWidth, offY, valueColor,
								bgColor);
					}
					//
					offY += HmFont.getFontHeight() + 2;
					// g.setColor(titleColor);
					g.drawBorderString("ｸﾘﾃｨｶﾙ率", offX, offY, titleColor,
							bgColor);
					// g.setColor(valueColor);
					g.drawBorderString(String.valueOf(_item
							.getEquipCriticalRate()
							+ "%"), offX + offSetWidth, offY, valueColor,
							bgColor);
					//
					if (_item.getEquipTypeDetail() == IItemClbInterface.EQUIP_TYPE2_GUN) {
						//
						offY += HmFont.getFontHeight() + 2;
						// g.setColor(titleColor);
						g.drawBorderString("弾型", offX, offY, titleColor,
								bgColor);
						// g.setColor(valueColor);
						g.drawBorderString(csvItem.getString(_item
								.getEquipBulletItemId(), 2),
								offX + offSetWidth, offY, valueColor, bgColor);
					}
				} else if (_item.getEquipType() == IItemClbInterface.EQUIP_TYPE1_ARMOR) {
					//
					offY += HmFont.getFontHeight() + 2;
					// g.setColor(titleColor);
					g.drawBorderString("ｱｰﾏｰｸﾗｽ", offX, offY, titleColor,
							bgColor);
					// g.setColor(valueColor);
					g.drawBorderString(String.valueOf(_item.getEquipAC()), offX
							+ offSetWidth, offY, valueColor, bgColor);

				}
			}
			//
			offY += HmFont.getFontHeight() + 2;
			// g.setColor(titleColor);
			g.drawBorderString("重さ(Kg)", offX, offY, titleColor, bgColor);
			// g.setColor(valueColor);
			g.drawBorderString(String.valueOf(_item.getWeight()), offX
					+ offSetWidth, offY, valueColor, bgColor);
			//
			if (_item.getEquipEnchantActive().length() != 0
					|| _item.getEquipEnchantPassive().length() != 0) {
				Csv csvEnchant = Res.get().csvEnchant;
				offY += HmFont.getFontHeight() + 2;
				// g.setColor(titleColor);
				g.drawBorderString("--特殊効果--", offX, offY, titleColor, bgColor);
				//
				DataArray id = new DataArray(_item.getEquipEnchantActive());
				for (int i = 0; i < id.size(); i++) {
					offY += HmFont.getFontHeight() + 2;
					// g.setColor(0x22ff00);
					int enchantId = id.getKeyInt(i);
					StringBuffer sb = new StringBuffer(csvEnchant.getString(
							enchantId, 1));
					sb.append(" ");
					sb.append(id.getValue(i));
					sb.append(csvEnchant.getString(enchantId, 11));
					g.drawBorderString(sb.toString(), offX, offY, enchaColor,
							bgColor);
				}
				//
				DataArray id2 = new DataArray(_item.getEquipEnchantPassive());
				for (int i = 0; i < id2.size(); i++) {
					offY += HmFont.getFontHeight() + 2;
					// g.setColor(0x22ff00);
					int enchantId = id2.getKeyInt(i);
					StringBuffer sb = new StringBuffer(csvEnchant.getString(
							enchantId, 1));
					sb.append(" ");
					sb.append(id2.getValue(i));
					sb.append(csvEnchant.getString(enchantId, 11));
					g.drawBorderString(sb.toString(), offX, offY, enchaColor,
							bgColor);
				}
			}

		}

		public void setX(int x) {
			this.x = x;
		}

		public void setY(int y) {
			this.y = y;
		}
	}

	public String getName() {
		return "ItemMenuScene";
	}

	public synchronized void onInit() throws Exception {
	}

	public synchronized void draw(GameGraphic g) throws Exception {
		_itemList.draw(g);
		_itemStat.draw(g);
	}

	public void update(int position) throws Exception {
		_execCount++;
		_itemList.update(position == 0);
		_itemStat.update();
	}

	public void onExit() throws Exception {
	}

	public boolean isEnd() throws Exception {
		return _isEnd;
	}

}
