package src.jp.co.yh123.zlibrary.view;

import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.platform.KeyState;
import src.jp.co.yh123.zlibrary.platform.TouchState;

/**
 * @author YOU UIの基底クラス
 */
public abstract class View {

	private int width = 0;

	private int height = 0;

	private int x = 0;

	private int y = 0;

	private boolean visible = true;

	private ViewGroup parent = null;

	protected ViewGroup getParent() {
		return parent;
	}

	protected void setParent(ViewGroup vg) {
		this.parent = vg;
	}

	public void requestFocus() throws Exception {
		if (parent != null && isFocusable()) {
			parent.requestChildFocus(this);
		}
	}

	public boolean hasFocus() throws Exception {
		if (parent != null) {
			return parent.findFocus() == this;
		}
		return false;
	}

	public boolean isFocusable() {
		return true;
	}

	public void removeAllChildren() throws Exception {

	}

	public void removeAllChildrenAndGrandChildren() throws Exception {

	}

	/*--------------------------------------------------------------
	 * イベント＆コールバック
	 *--------------------------------------------------------------*/

	public void draw(GameGraphic g) throws Exception {
		if (!isVisible()) {
			return;
		}
		onDraw(g);
	}

	protected abstract void onDraw(GameGraphic g) throws Exception;

	protected void onFocusChanged() throws Exception {

	}

	protected boolean onKeyAction(KeyState keyState) throws Exception {
		return false;
	}

	protected boolean onTouchAction(TouchState touchState) throws Exception {
		return false;
	}

	public void update() throws Exception {
		onUpdate();
	}

	protected abstract void onUpdate() throws Exception;

	protected void onRemoved() throws Exception {

	}

	protected void onAttachedToViewGroup() throws Exception {

	}

	/*--------------------------------------------------------------
	 * Getter ＆ Setter
	 *--------------------------------------------------------------*/

	public int getHeight() throws Exception {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() throws Exception {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
