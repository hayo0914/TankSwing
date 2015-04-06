package src.jp.co.yh123.zlibrary.view;

/**
 * @author YOU
 */
public abstract class Menu extends ViewGroup {

	public static interface MenuEventListener {

		public void menuSelected(Menu menu, MenuItem menuItem) throws Exception;

		// public void menuCanceled(Menu menu) throws Exception;

	}

	private MenuEventListener l = null;

	public Menu() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	public MenuItem getCurrentSelect() throws Exception {
		View view = findFocus();
		return (MenuItem) view;
	}

	public void initSelection() throws Exception {
		if (childCount() == 0) {
			return;
		}
		getChildAt(0).requestFocus();
	}

	public void remove(MenuItem menu) throws Exception {
		removeView((View) menu);
	}

	public void setFocus(MenuItem menu) throws Exception {
		View v = (View) menu;
		v.requestFocus();
	}

	public void add(MenuItem menuItem) throws Exception {
		menuItem.onAddToMenu();
		addView((View) menuItem);
	}

	public void setMenuEventListener(MenuEventListener l) throws Exception {
		this.l = l;
	}

	protected MenuEventListener getMenuEventListener() {
		return l;
	}

}
