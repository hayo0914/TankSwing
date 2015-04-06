package src.jp.co.yh123.zlibrary.view;

import src.jp.co.yh123.zlibrary.platform.ActionEventAdapter;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.platform.KeyState;
import src.jp.co.yh123.zlibrary.platform.TouchState;

/**
 * @author YOU ���Ăɕ`��B1��̂݁B
 */
public class VerticalMenu extends Menu {

	private int viewRow = 1;

	public int getViewRow() {
		return viewRow;
	}

	public static class VerticalMenuAttribute extends AttributeSet {

		private int viewRow = 10;

		public void setViewRow(int viewRow) {
			this.viewRow = viewRow;
		}
	}

	public VerticalMenu(VerticalMenuAttribute attribute) throws Exception {
		super();
		this.viewRow = attribute.viewRow;
	}

	private int getDrawStartPos() throws Exception {
		// �I���ӏ�
		View focus = findFocus();
		int focusPos = 0;
		if (focus != null) {
			focusPos = getPosition(focus);
		}
		int drawStartPos = 0;
		if (focusPos != 0) {
			drawStartPos = (focusPos / viewRow) * viewRow;
		}
		return drawStartPos;
	}

	protected void onDraw(GameGraphic g) throws Exception {

		// �`��Ώ�
		// (int)(�I�����j���[�̈ʒu / �`��s��)* �`��s�� = �`��J�n�ʒu
		int drawStartPos = getDrawStartPos();
		int drawEndPos = drawStartPos + viewRow;
		if (drawEndPos > childCount()) {
			drawEndPos = childCount();
		}

		// �`��Ώۂ̃��j���[�A�C�e����`��
		int x = getX(), y = getY();
		for (int i = drawStartPos; i < drawEndPos; i++) {
			View v = getChildAt(i);
			v.setPosition(x, y);
			v.draw(g);
			y += v.getHeight();
		}
	}

	public int getHeight() throws Exception {
		// �`��Ώ�
		// (int)(�I�����j���[�̈ʒu / �`��s��)* �`��s�� = �`��J�n�ʒu
		int drawStartPos = getDrawStartPos();
		int drawEndPos = drawStartPos + viewRow;
		if (drawEndPos > childCount()) {
			drawEndPos = childCount();
		}
		int height = 0;
		// �`��Ώۂ̃��j���[�A�C�e��
		for (int i = drawStartPos; i < drawEndPos; i++) {
			View v = getChildAt(i);
			height += v.getHeight();
		}
		return height;
	}

	public int getWidth() throws Exception {
		if (childCount() == 0) {
			return 0;
		}
		// �`��Ώۂ̃��j���[�A�C�e��
		View v = getChildAt(0);
		return v.getWidth();
	}

	protected boolean onKeyAction(KeyState keyState) throws Exception {

		// �L�[�C�x���g�`�F�b�N
		if (hasFocus()) {
			// ���g�Ƀt�H�[�J�X������ꍇ�̂݃`�F�b�N����
			int currentFocus = getPosition(findFocus());
			int max = childCount();
			int visibleTopPos = getDrawStartPos();
			KeyState key = ActionEventAdapter.getKeyState();
			if (key.chkKeyPushCount(KeyState.KEY_DOWN) > 0) {
				currentFocus++;
				if (currentFocus >= max
						|| currentFocus >= visibleTopPos + viewRow) {
					currentFocus = visibleTopPos;
				}
			}
			if (key.chkKeyPushCount(KeyState.KEY_UP) > 0) {
				currentFocus--;
				if (currentFocus < 0 || currentFocus < visibleTopPos) {
					currentFocus = visibleTopPos + viewRow - 1;
					if (currentFocus >= max) {
						currentFocus = max - 1;
					}
				}
			}
			if (key.chkKeyPushCount(KeyState.KEY_LEFT) > 0) {
				currentFocus -= viewRow;
				if (currentFocus < 0) {
					currentFocus = max + currentFocus;
				}
			}
			if (key.chkKeyPushCount(KeyState.KEY_RIGHT) > 0) {
				currentFocus += viewRow;
				if (currentFocus >= max) {
					currentFocus = currentFocus - max;
				}
			}
			getChildAt(currentFocus).requestFocus();
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

	/*------------------------------------------------------
	 * �C���^�[�t�F�[�X���\�b�h
	 *------------------------------------------------------*/
	public MenuItem getMenu(int visiblePosition) throws Exception {
		int visibleStartPos = getDrawStartPos();
		return (MenuItem) getChildAt(visibleStartPos + visiblePosition);
	}

}
