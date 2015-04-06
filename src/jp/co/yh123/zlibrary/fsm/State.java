package src.jp.co.yh123.zlibrary.fsm;

public abstract class State implements IState {

	private StateMachine stateMachine = null;

	protected StateMachine getStateMachine() {
		return stateMachine;
	}

	protected void setStateMachine(StateMachine stateMachine) {
		this.stateMachine = stateMachine;
	}

	protected void changeState(State state) throws Exception {
		stateMachine.changeState(state);
	}

}
