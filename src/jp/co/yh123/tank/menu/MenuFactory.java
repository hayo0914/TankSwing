package src.jp.co.yh123.tank.menu;

import src.jp.co.yh123.tank.menuimpl.ItemListMenuScene;
import src.jp.co.yh123.tank.menuimpl.MainMenuScene;
import src.jp.co.yh123.tank.menuimpl.NumberInputMenuScene;
import src.jp.co.yh123.tank.menuimpl.SimpleSelectMenuScene;
import src.jp.co.yh123.tank.menuimpl.SkillMenuScene;

public class MenuFactory {

	public static IMainMenuInterface getMainMenu() throws Exception {
		return new MainMenuScene();
	}

	public static ISkillMenuInterface getSkillMenu() throws Exception {
		return new SkillMenuScene();
	}

	public static IItemListMenuInterface getItemListMenu() throws Exception {
		return new ItemListMenuScene();
	}

	public static ISimpleSelectMenuInterface getSimpleSelectMenu()
			throws Exception {
		return new SimpleSelectMenuScene();
	}

	public static INumberInputInterface getNumberImputMenu() throws Exception {
		return new NumberInputMenuScene();
	}

}
