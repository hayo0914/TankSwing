package src.jp.co.yh123.tank.menu;

public interface IYesNoMenuInterface {

	public static final int YES = 0;
	public static final int NO = 1;

	public static interface IYesNoCallBackInterface {

		public void selected(int choice) throws Exception;

	}

	public void showWithItem(String question) throws Exception;

	public void showWithoutItem(String question) throws Exception;
}
