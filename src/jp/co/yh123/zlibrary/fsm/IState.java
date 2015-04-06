package src.jp.co.yh123.zlibrary.fsm;


public interface IState {

	/**
	 * State‚É“ü‚Á‚½‚Æ‚«
	 */
	public void enter() throws Exception ;

	/**
	 * State‚ªÀs‚³‚ê‚é‚Æ‚«
	 */
	public void execute()throws Exception ;

	/**
	 * State–¼Ì
	 */
	public String getStateName() ;

	/**
	 * State‚©‚ç”²‚¯‚é‚Æ‚«
	 */
	public void exit() throws Exception ;

}
