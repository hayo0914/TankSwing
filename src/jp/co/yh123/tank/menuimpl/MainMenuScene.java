package src.jp.co.yh123.tank.menuimpl;

import src.jp.co.yh123.tank.chara.Chara;
import src.jp.co.yh123.tank.menu.IMainMenuInterface;
import src.jp.co.yh123.zlibrary.anime.Animation;
import src.jp.co.yh123.zlibrary.anime.AnimeFactory;
import src.jp.co.yh123.zlibrary.platform.ActionEventAdapter;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.scene.ISceneInterface;
import src.jp.co.yh123.zlibrary.scene.SceneManagerFactory;
import src.jp.co.yh123.zlibrary.view.AbsolutePositonLayout;
import src.jp.co.yh123.zlibrary.view.Menu;
import src.jp.co.yh123.zlibrary.view.MenuItem;
import src.jp.co.yh123.zlibrary.view.VerticalMenu;
import src.jp.co.yh123.zlibrary.view.ViewGroup;
import src.jp.co.yh123.zlibrary.view.Menu.MenuEventListener;
import src.jp.co.yh123.zlibrary.view.VerticalMenu.VerticalMenuAttribute;

public class MainMenuScene implements ISceneInterface, IMainMenuInterface {

	private boolean _isEnd = false;

	private ViewGroup _rootView = null;

	private Menu _selectMenu = null;

	private int _execCount = 0;

	private int _offsetX = 100;

	private int _spdX = 20;

	private boolean _onExitAnime = false;

	private MenuItem _selectItem = null;

	private Animation _menuBack = null;

	private IMainMenuCallBackInterface _callback = null;

	public void show(Chara c, IMainMenuCallBackInterface callback)
			throws Exception {
		this._callback = callback;
		// ÉrÉÖÅ[çÏê¨
		_rootView = new AbsolutePositonLayout();
		_onExitAnime = false;
		addMenu();
		_execCount = 0;
		_menuBack = AnimeFactory.getInstance().createAnime(-1, 4, -1);
		SceneManagerFactory.getSceneManager().add(this);
	}

	public MainMenuScene() throws Exception {
		// do nothing
	}

	private void addMenu() throws Exception {

		VerticalMenuAttribute att = new VerticalMenuAttribute();
		int viewRow = 6;
		att.setViewRow(viewRow);
		VerticalMenu menu = new VerticalMenu(att);

		menu.add(new MainMenuItem("Item", IMainMenuInterface.MENU_PL_ITEM));
		menu
				.add(new MainMenuItem("Search",
						IMainMenuInterface.MENU_PL_ASHIMOTO));
		menu.add(new MainMenuItem("Skill", IMainMenuInterface.MENU_PL_SKILL));
		menu.add(new MainMenuItem("Status", IMainMenuInterface.MENU_PL_STATUS));
		menu.add(new MainMenuItem("Cancel", IMainMenuInterface.MENU_PL_CANCEL));

		menu.setMenuEventListener(new MenuEventListener() {

			public void menuSelected(Menu menu, MenuItem menuItem)
					throws Exception {
				if (menu == _selectMenu) {
					_selectItem = menuItem;
					_onExitAnime = true;
				}
			}
		});
		// menu.setPosition(5, 82);
		_selectMenu = menu;
		_rootView.addView(_selectMenu, true);

	}

	public String getName() {
		return "PlayerMenuScene";
	}

	public synchronized void onInit() throws Exception {
	}

	public synchronized void draw(GameGraphic g) throws Exception {
		_menuBack.setPosition(5, 70);
		_menuBack.draw(g, _offsetX, 0);
		_selectMenu.setPosition(10 + (-1 * _offsetX), 82);
		_rootView.draw(g);
	}

	public void update(int position) throws Exception {
		_rootView.update();
		_execCount++;
		if (_offsetX != 0 && !_onExitAnime) {
			_offsetX -= _spdX;
			if (_offsetX < 0) {
				_offsetX = 0;
			}
		} else if (_onExitAnime) {
			_offsetX += _spdX;
			if (_offsetX > 100) {
				_isEnd = true;
				_callback.selected(_selectItem.getId());
			}
		}
		if (position == 0) {
			if (!isEnd() && _execCount > 5 && !_onExitAnime) {
				_rootView.keyAction(ActionEventAdapter.getKeyState());
				_rootView.touchAction(ActionEventAdapter.getTouchState());
			}
		}
	}

	public void onExit() throws Exception {

	}

	public boolean isEnd() throws Exception {
		return _isEnd;
	}

}
