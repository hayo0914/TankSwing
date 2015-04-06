package src.jp.co.yh123.zlibrary.view;


public abstract class MenuItem extends View {

	private String text = "";

	private int id = 0;

	protected MenuItem(int id, String text) throws Exception {
		this.id = id;
		this.text = text;
	}

	/**
	 * メニューのIDを取得
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * メニューのテキストを取得
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 初期化
	 * 
	 * @throws Exception
	 */
	public abstract void onAddToMenu() throws Exception;

	/**
	 * メニュー決定時
	 * @throws Exception
	 */
	public abstract void onSelect() throws Exception;

	/**
	 * サイズを取得
	 * 
	 * @return
	 */
	public abstract int getWidth();

	/**
	 * サイズを取得
	 * 
	 * @return
	 */
	public abstract int getHeight();

}
