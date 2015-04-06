package src.jp.co.yh123.zlibrary.view;


public abstract class MenuItem extends View {

	private String text = "";

	private int id = 0;

	protected MenuItem(int id, String text) throws Exception {
		this.id = id;
		this.text = text;
	}

	/**
	 * ���j���[��ID���擾
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * ���j���[�̃e�L�X�g���擾
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
	 * ������
	 * 
	 * @throws Exception
	 */
	public abstract void onAddToMenu() throws Exception;

	/**
	 * ���j���[���莞
	 * @throws Exception
	 */
	public abstract void onSelect() throws Exception;

	/**
	 * �T�C�Y���擾
	 * 
	 * @return
	 */
	public abstract int getWidth();

	/**
	 * �T�C�Y���擾
	 * 
	 * @return
	 */
	public abstract int getHeight();

}
