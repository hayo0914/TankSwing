package src.jp.co.yh123.zlibrary.fsm;


public interface IState {

	/**
	 * State�ɓ������Ƃ�
	 */
	public void enter() throws Exception ;

	/**
	 * State�����s�����Ƃ�
	 */
	public void execute()throws Exception ;

	/**
	 * State����
	 */
	public String getStateName() ;

	/**
	 * State���甲����Ƃ�
	 */
	public void exit() throws Exception ;

}
