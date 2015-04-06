package src.jp.co.yh123.zlibrary.view;

import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.platform.KeyState;
import src.jp.co.yh123.zlibrary.platform.TouchState;
import src.jp.co.yh123.zlibrary.util.List;

public abstract class ViewGroup extends View {

	private List children = null;

	public static interface OnFocusChangeListener {

		public void onFocusChanged(View v, boolean hasFocus);
	}

	private OnFocusChangeListener onFocusChangeListener = null;

	private View focusView = null;

	public void setOnFocusChangeListener(OnFocusChangeListener l) {
		this.onFocusChangeListener = l;
	}

	public ViewGroup() throws Exception {
		children = new List(3);
	}

	/**
	 * @author YOU ビューグループのレイアウト定義用
	 */
	public static class AttributeSet {

	}

	protected void requestChildFocus(View view) throws Exception {
		setFocus(view);
	}

	private void setFocus(View v) throws Exception {
		focusView = v;
		fireFocusChangedEvent();
	}

	protected void fireFocusChangedEvent() throws Exception {
		boolean hasListener = onFocusChangeListener != null;
		View focus = focusView;
		boolean hasFocus = false;
		for (int i = 0; i < children.size(); i++) {
			View view = getChildAt(i);
			hasFocus = focus == view;
			view.onFocusChanged();
			if (hasListener) {
				onFocusChangeListener.onFocusChanged(view, hasFocus);
			}
		}
	}

	public void update() throws Exception {
		onUpdate();
		for (int i = 0; i < children.size(); i++) {
			View view = (View) children.elementAt(i);
			view.update();
		}
	}

	public void keyAction(KeyState keyState) throws Exception {
		if (onKeyAction(keyState)) {
			return;
		}
		for (int i = 0; i < children.size(); i++) {
			View view = (View) children.elementAt(i);
			if (view.onKeyAction(keyState)) {
				return;
			}
		}
	}

	public void touchAction(TouchState touchState) throws Exception {
		if (onTouchAction(touchState)) {
			return;
		}
		for (int i = 0; i < children.size(); i++) {
			View view = (View) children.elementAt(i);
			if (view.onTouchAction(touchState)) {
				return;
			}
		}
	}

	public void draw(GameGraphic g) throws Exception {
		if (!isVisible()) {
			return;
		}
		onDraw(g);
	}

	public void addView(View view) throws Exception {
		children.add(view);
		view.setParent(this);
		view.onAttachedToViewGroup();
		if (focusView == null && view.isFocusable()) {
			setFocus(view);
		}
	}

	public void addView(View view, boolean needsFocus) throws Exception {
		addView(view);
		view.requestFocus();
	}

	public View findFocus() throws Exception {
		return focusView;
	}

	public int childCount() {
		return children.size();
	}

	public View getChildAt(int pos) {
		return (View) children.elementAt(pos);
	}

	protected int getPosition(View v) {
		return children.indexOf(v);
	}

	public void removeView(View view) throws Exception {
		children.removeElement(view);
		view.setParent(null);
		view.onRemoved();
		if (childCount() > 0) {
			View lastView = (View) children.getLast();
			lastView.requestFocus();
		}
	}

	public void removeAllChildrenAndGrandChildren() throws Exception {
		for (int i = 0; i < children.size(); i++) {
			View view = (View) children.elementAt(i);
			view.removeAllChildren();
		}
		while (childCount() != 0) {
			removeView(getChildAt(0));
		}
	}

	public void removeAllChildren() throws Exception {
		while (childCount() != 0) {
			removeView(getChildAt(0));
		}
	}

}
