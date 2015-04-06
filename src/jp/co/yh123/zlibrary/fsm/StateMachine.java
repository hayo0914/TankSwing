package src.jp.co.yh123.zlibrary.fsm;

public class StateMachine {

	private State currentState = null;

	private int currentStateFrameCount = 0;

	private int frameCount = 0;

	private StateMachineObserver observer = null;

	public static interface StateMachineObserver {
		/**
		 * 中止する場合はtrue
		 * 
		 * @return
		 */
		public boolean onChangeState(State NextState);

		public void onUpdate(State currentState);

		public void onInit();
	}

	public StateMachine(StateMachineObserver observer, State initialState)
			throws Exception {
		currentStateFrameCount = 0;
		frameCount = 0;
		this.observer = observer;
		observer.onInit();
		changeState(initialState);
	}

	/**
	 * 毎フレームで実行
	 * 
	 * @throws Exception
	 */
	public void exec() throws Exception {
		// checkStateChange();
		currentState.setStateMachine(this);
		currentState.execute();
		currentStateFrameCount++;
		frameCount++;
		observer.onUpdate(currentState);
	}

	/**
	 * Stateを変更する
	 * 
	 * @param owner
	 * @param NextState
	 * @throws Exception
	 */
	void changeState(State NextState) throws Exception {
		if (observer.onChangeState(NextState)) {
			return;
		}
		if (currentState != null) {
			currentState.setStateMachine(this);
			currentState.exit();
		}
		currentStateFrameCount = 0;
		currentState = NextState;
		currentState.setStateMachine(this);
		currentState.enter();
	}

	/**
	 * 現在のステートを取得
	 * 
	 * @return
	 */
	public State getCurrentState() {
		return currentState;
	}

}
