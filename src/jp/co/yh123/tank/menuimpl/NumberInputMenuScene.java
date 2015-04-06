package src.jp.co.yh123.tank.menuimpl;

import src.jp.co.yh123.tank.menu.INumberInputInterface;
import src.jp.co.yh123.zlibrary.platform.ActionEventAdapter;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.platform.KeyState;
import src.jp.co.yh123.zlibrary.platform.StringUtil;
import src.jp.co.yh123.zlibrary.scene.ISceneInterface;
import src.jp.co.yh123.zlibrary.scene.SceneManagerFactory;
import src.jp.co.yh123.zlibrary.util.DebugUtil;
import src.jp.co.yh123.zlibrary.view.AbsolutePositonLayout;
import src.jp.co.yh123.zlibrary.view.Menu;
import src.jp.co.yh123.zlibrary.view.MenuItem;
import src.jp.co.yh123.zlibrary.view.TableMenu;
import src.jp.co.yh123.zlibrary.view.ViewGroup;
import src.jp.co.yh123.zlibrary.view.Menu.MenuEventListener;
import src.jp.co.yh123.zlibrary.view.TableMenu.TableMenuAttribute;

public class NumberInputMenuScene implements ISceneInterface,
		INumberInputInterface {

	private ViewGroup _rootView = null;

	private Menu _selectMenu = null;

	private boolean _isEnd = false;

	private INumberInputCallBackInterface _callback = null;

	private int _execCount = 0;

	private NumberInputMenuScene _ins = this;

	public NumberInputMenuScene() throws Exception {
		// do nothing
	}

	private void addNumMenu() throws Exception {

		TableMenuAttribute att = new TableMenuAttribute();
		int viewRow = 1;
		int viewCol = 8;
		att.setViewRow(viewRow);
		att.setViewCol(viewCol);
		Menu menu = new TableMenu(att);

		for (int i = 0; i < viewCol; i++) {
			menu.add(new NumberInputMenuItem(i));
		}
		menu.setMenuEventListener(new MenuEventListener() {

			public void menuSelected(Menu menu, MenuItem menuItem)
					throws Exception {
				if (menu == _selectMenu) {
					int num = getNumber();
					_callback.numberInputed(num, _ins);
				}
			}
		});
		menu.setFocus((NumberInputMenuItem) menu.getChildAt(viewCol - 1));
		_selectMenu = menu;
		_rootView.addView(_selectMenu, true);

	}

	public void end() throws Exception {
		_isEnd = true;
	}

	private void setNumber(int num) throws Exception {
		String str = String.valueOf(num);
		DebugUtil.assertTrue(str.length() <= 8);

		int index = 0;
		for (int i = str.length() - 1; i >= 0; i--) {
			int wkNum = StringUtil.toInt(String.valueOf(str.charAt(i)));
			NumberInputMenuItem numItem = (NumberInputMenuItem) _selectMenu
					.getChildAt(_selectMenu.childCount() - index - 1);
			index++;
			numItem.setNumber(wkNum);
		}
	}

	private int getNumber() {
		int wk = 0;
		int baisu = 1;
		for (int i = _selectMenu.childCount() - 1; i >= 0; i--) {
			NumberInputMenuItem numItem = (NumberInputMenuItem) _selectMenu
					.getChildAt(i);
			wk += numItem.getNumber() * baisu;
			baisu *= 10;
		}
		return wk;
	}

	public boolean isEnd() throws Exception {
		return _isEnd;
	}

	public void show(int initNumber, INumberInputCallBackInterface callback)
			throws Exception {
		_callback = callback;
		_rootView = new AbsolutePositonLayout();
		addNumMenu();
		setNumber(initNumber);
		_execCount = 0;
		SceneManagerFactory.getSceneManager().add(this);
	}

	public String getName() {
		return "NumberInputMenuScene";
	}

	public synchronized void onInit() throws Exception {
	}

	public synchronized void draw(GameGraphic g) throws Exception {
		_selectMenu.setPosition(40, 40);
		_rootView.draw(g);
	}

	public void update(int position) throws Exception {
		_rootView.update();
		_execCount++;
		if (position == 0) {
			if (!isEnd() && _execCount > 5) {
				_rootView.keyAction(ActionEventAdapter.getKeyState());
				_rootView.touchAction(ActionEventAdapter.getTouchState());

				// 数値変更のためのキー状態取得
				KeyState key = ActionEventAdapter.getKeyState();
				// boolean change = false;
				if (key.chkKeyPushCount(KeyState.KEY_UP) > 0
						|| key.chkKeyPushCount(KeyState.KEY_8) > 0) {
					// 上
					NumberInputMenuItem numItem = (NumberInputMenuItem) _selectMenu
							.getCurrentSelect();
					numItem.incrNumber();
				} else if (key.chkKeyPushCount(KeyState.KEY_DOWN) > 0
						|| key.chkKeyPushCount(KeyState.KEY_2) > 0) {
					// 下
					NumberInputMenuItem numItem = (NumberInputMenuItem) _selectMenu
							.getCurrentSelect();
					numItem.decrNumber();
				}
			}
		}
	}

	public void onExit() throws Exception {
	}

}
