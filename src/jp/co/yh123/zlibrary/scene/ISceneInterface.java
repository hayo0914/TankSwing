package src.jp.co.yh123.zlibrary.scene;

import src.jp.co.yh123.zlibrary.platform.GameGraphic;

public interface ISceneInterface {

	public void update(int position) throws Exception;

	public void draw(GameGraphic g) throws Exception;

	public void onInit() throws Exception;

	public void onExit() throws Exception;

	public boolean isEnd() throws Exception;

}
