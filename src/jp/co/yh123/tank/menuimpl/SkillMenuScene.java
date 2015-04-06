package src.jp.co.yh123.tank.menuimpl;

import src.jp.co.yh123.tank.game.GameConstants;
import src.jp.co.yh123.tank.menu.ISkillMenuInterface;
import src.jp.co.yh123.tank.resource.Res;
import src.jp.co.yh123.zlibrary.platform.ActionEventAdapter;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.scene.ISceneInterface;
import src.jp.co.yh123.zlibrary.scene.SceneManagerFactory;
import src.jp.co.yh123.zlibrary.util.Csv;
import src.jp.co.yh123.zlibrary.util.DataArray;
import src.jp.co.yh123.zlibrary.view.AbsolutePositonLayout;
import src.jp.co.yh123.zlibrary.view.Menu;
import src.jp.co.yh123.zlibrary.view.MenuItem;
import src.jp.co.yh123.zlibrary.view.VerticalMenu;
import src.jp.co.yh123.zlibrary.view.ViewGroup;
import src.jp.co.yh123.zlibrary.view.Menu.MenuEventListener;
import src.jp.co.yh123.zlibrary.view.VerticalMenu.VerticalMenuAttribute;

public class SkillMenuScene implements ISceneInterface, ISkillMenuInterface {

	private DataArray _skillList = null;

	private ViewGroup rootView = null;

	private VerticalMenu selectMenu = null;

	private boolean _isEnd = false;

	private int execCount = 0;

	private int x = 0;
	private int y = 0;

	private ISkillMenuCallBackInterface _callback = null;

	public void show(DataArray skillList, ISkillMenuCallBackInterface callback)
			throws Exception {
		_callback = callback;
		_skillList = skillList;
		// ƒrƒ…[ì¬
		rootView = new AbsolutePositonLayout();
		_isEnd = false;
		addMenu();
		execCount = 0;
		x = 5;
		y = 10;
		SceneManagerFactory.getSceneManager().add(this);
	}

	public SkillMenuScene() {
		// do nothing
	}

	private void addMenu() throws Exception {

		VerticalMenuAttribute att = new VerticalMenuAttribute();
		int viewRow = _skillList.size();
		if (viewRow > 8) {
			viewRow = 8;
		}
		att.setViewRow(viewRow);
		VerticalMenu menu = new VerticalMenu(att);

		Csv spellCsv = Res.get().csvSpell;
		for (int i = 0; i < _skillList.size(); i++) {
			menu.add(new SimpleSelectMenuItem(spellCsv.getString(_skillList
					.getKeyInt(i), 1), i));
		}

		menu.setMenuEventListener(new MenuEventListener() {

			public void menuSelected(Menu menu, MenuItem menuItem)
					throws Exception {

				if (menu == selectMenu) {
					_isEnd = true;
					_callback.selected(menuItem.getId());
				}
			}
		});
		menu.setPosition(GameConstants.SIZE_DISPLAY_WIDTH - 86,
				GameConstants.SIZE_DISPLAY_HEIGHT - 5 - (viewRow + 1) * 20);
		selectMenu = menu;
		rootView.addView(selectMenu, true);

	}

	public String getName() {
		return "SpellMenuScene";
	}

	public synchronized void onInit() throws Exception {
	}

	public synchronized void draw(GameGraphic g) throws Exception {
		rootView.setX(x);
		rootView.setY(y);
		rootView.draw(g);
		int x = selectMenu.getX();
		int y = selectMenu.getY();
		int width = selectMenu.getWidth();
		int height = selectMenu.getHeight();

		// ˜g
		g.setColor(0x333333);
		g.drawRect(x, y, width, height);
		g.setColor(0xcccc22);
		g.drawRect(x + 1, y + 1, width - 2, height - 2);
		g.setColor(0x333333);
		g.drawRect(x + 2, y + 2, width - 4, height - 4);
	}

	public void update(int position) throws Exception {

		rootView.update();
		execCount++;
		if (position == 0) {
			if (!_isEnd && execCount > 3) {
				rootView.keyAction(ActionEventAdapter.getKeyState());
				rootView.touchAction(ActionEventAdapter.getTouchState());
			}
		}
	}

	public void onExit() throws Exception {
	}

	public boolean isEnd() throws Exception {
		return _isEnd;
	}
}
