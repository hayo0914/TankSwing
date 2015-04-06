package src.jp.co.yh123.zlibrary.fsm;

public class StateMachine {

	private State currentState = null;

	private int currentStateFrameCount = 0;

	private int frameCount = 0;

	private StateMachineObserver observer = null;

	public static interface StateMachineObserver {
		/**
		 * ���~����ꍇ��true
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
	 * ���t���[���Ŏ��s
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
	 * State��ύX����
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
	 * ���݂̃X�e�[�g���擾
	 * 
	 * @return
	 */
	public State getCurrentState() {
		return currentState;
	}

}
