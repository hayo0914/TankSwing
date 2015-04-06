package src.jp.co.yh123.zlibrary.view;

import src.jp.co.yh123.zlibrary.platform.ActionEventAdapter;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.platform.KeyState;
import src.jp.co.yh123.zlibrary.platform.TouchState;

/**
 * @author YOU テーブル型メニュー
 */
public class TableMenu extends Menu {

	private int viewRow = 1;

	private int viewCol = 1;

	public static class TableMenuAttribute extends AttributeSet {

		private int viewRow = 10;

		private int viewCol = 10;

		public void setViewRow(int viewRow) {
			this.viewRow = viewRow;
		}

		public void setViewCol(int viewCol) {
			this.viewCol = viewCol;
		}
	}

	public TableMenu(TableMenuAttribute attribute) throws Exception {
		super();
		this.viewRow = attribute.viewRow;
		this.viewCol = attribute.viewCol;
	}

	private int getDrawStartPos() throws Exception {
		// 選択箇所
		View focus = findFocus();
		int focusPos = 0;
		if (focus != null) {
			focusPos = getPosition(focus);
		}
		int drawStartPos = 0;
		// 1ページのアイテム数
		int itemNum = viewRow * viewCol;
		if (focusPos != 0) {
			drawStartPos = (focusPos / itemNum) * itemNum;
		}
		return drawStartPos;
	}

	protected void onDraw(GameGraphic g) throws Exception {

		// 描画対象
		// (int)(選択メニューの位置 / 描画行数)* 描画行数 = 描画開始位置
		int drawStartPos = getDrawStartPos();

		// 描画対象のメニューアイテムを描画
		int x = getX(), y = getY();
		int position = drawStartPos;
		int maxHeight = 0;
		draw: for (int i = 0; i < viewRow; i++) {
			for (int j = 0; j < viewCol; j++) {
				View v = getChildAt(position);
				v.setPosition(x, y);
				v.draw(g);
				x += v.getWidth();
				if (v.getHeight() > maxHeight) {
					maxHeight = v.getHeight();
				}
				position++;
				if (position >= childCount()) {
					break draw;
				}
			}
			x = getX();
			y += maxHeight;
			maxHeight = 0;
		}
	}

	protected boolean onKeyAction(KeyState keyState) throws Exception {

		int beforeCurrentFocus = getPosition(findFocus());
		// キーイベントチェック
		if (hasFocus()) {
			// 自身にフォーカスがある場合のみチェックする
			int currentFocus = getPosition(findFocus());
			int max = childCount();
			int itemNumPerPage = viewRow * viewCol;
			KeyState key = ActionEventAdapter.getKeyState();
			if (key.chkKeyPushCount(KeyState.KEY_DOWN) > 0) {
				if (currentFocus + viewCol < max) {
					currentFocus += viewCol;
				} else {
					currentFocus = getCol(currentFocus);
				}
			}
			if (key.chkKeyPushCount(KeyState.KEY_UP) > 0) {
				if (currentFocus - viewCol >= 0) {
					currentFocus -= viewCol;
				} else {
					int wk = (max / itemNumPerPage) * itemNumPerPage;
					wk = wk + ((viewRow - 1) * viewCol) + getCol(currentFocus);

					while (wk >= max) {
						wk -= viewCol;
					}
					currentFocus = wk;
				}
			}
			if (key.chkKeyPushCount(KeyState.KEY_LEFT) > 0) {
				if (getCol(currentFocus) == 0) {
					currentFocus = currentFocus + viewCol - 1;
					if (currentFocus >= max) {
						currentFocus = max - 1;
					}
				} else {
					currentFocus--;
				}
			}
			if (key.chkKeyPushCount(KeyState.KEY_RIGHT) > 0) {
				if (getCol(currentFocus) == viewCol - 1) {
					currentFocus = currentFocus - viewCol + 1;
				} else {
					currentFocus++;
					if (currentFocus >= max) {
						currentFocus = max - getCol(max);
					}
				}
			}
			if (beforeCurrentFocus != currentFocus) {
				getChildAt(currentFocus).requestFocus();
			}
			if (key.chkKeyPushCount(KeyState.KEY_ENTER) > 0) {
				getMenuEventListener().menuSelected(this,
						(MenuItem) findFocus());
			}
			// key.clearKeyPushCount();
			return true;
		}
		return false;
	}

	protected boolean onTouchAction(TouchState touchState) {
		return false;
	}

	protected void onUpdate() throws Exception {

	}

	private int getCol(int pos) {
		pos %= (viewRow * viewCol);
		return pos - (getRow(pos) * viewCol);
	}

	private int getRow(int pos) {
		pos %= (viewRow * viewCol);
		return pos / viewCol;
	}

	/*------------------------------------------------------
	 * インターフェースメソッド
	 *------------------------------------------------------*/
	public MenuItem getMenu(int visiblePosition) throws Exception {
		int visibleStartPos = getDrawStartPos();
		return (MenuItem) getChildAt(visibleStartPos + visiblePosition);
	}

}
