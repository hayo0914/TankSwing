package src.jp.co.yh123.zlibrary.fsm;


public interface IState {

	/**
	 * Stateに入ったとき
	 */
	public void enter() throws Exception ;

	/**
	 * Stateが実行されるとき
	 */
	public void execute()throws Exception ;

	/**
	 * State名称
	 */
	public String getStateName() ;

	/**
	 * Stateから抜けるとき
	 */
	public void exit() throws Exception ;

}
