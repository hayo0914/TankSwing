package src.jp.co.yh123.zlibrary.view;

import src.jp.co.yh123.zlibrary.platform.GameGraphic;

/**
 * @author YOU
 * 描画位置は常にビュー自身が決めます。
 */
public class AbsolutePositonLayout extends ViewGroup {
	
	public AbsolutePositonLayout() throws Exception {
	}

	protected void onAttachedToViewGroup() throws Exception {
		// TODO Auto-generated method stub

	}

	protected void onDraw(GameGraphic g) throws Exception {
		int size = childCount();
		for (int i = 0; i < size; i++) {
			View v = getChildAt(i);
			if(v != null){
				v.draw(g);	
			}
		}
	}

	public void onFocusChanged(boolean hasFocus) throws Exception {
		// TODO Auto-generated method stub

	}

	protected void onRemoved() throws Exception {
		// TODO Auto-generated method stub

	}

	protected void onUpdate() throws Exception {
		// TODO Auto-generated method stub

	}

}
