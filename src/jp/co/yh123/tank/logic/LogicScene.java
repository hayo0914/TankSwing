package src.jp.co.yh123.tank.logic;

import src.jp.co.yh123.tank.collabo.EntityFactory;
import src.jp.co.yh123.tank.map.IEntityFactoryInterface;
import src.jp.co.yh123.tank.map.MapFactory;
import src.jp.co.yh123.tank.resource.Res;
import src.jp.co.yh123.zlibrary.fsm.State;
import src.jp.co.yh123.zlibrary.fsm.StateMachine;
import src.jp.co.yh123.zlibrary.fsm.StateMachine.StateMachineObserver;
import src.jp.co.yh123.zlibrary.platform.GameGraphic;
import src.jp.co.yh123.zlibrary.scene.ISceneInterface;
import src.jp.co.yh123.zlibrary.util.DebugUtil;

public class LogicScene implements ISceneInterface {
	private StateMachine LogicFSM = null;
	private static ILogicMapInterface map = null;

	public String getName() {
		return "GameMainScene";
	}

	public synchronized void onInit() throws Exception {
		Res.load();
		IEntityFactoryInterface factory = new EntityFactory();
		map = MapFactory.createNewMap(factory);
		LogicFSM = new StateMachine(new StateMachineObserver() {

			public boolean onChangeState(State NextState) {
				DebugUtil.debugLog("LogicState", "ステート変更", NextState
						.getStateName());
				return false;
			}

			public void onInit() {
				// do nothing
			}

			public void onUpdate(State currentState) {
				// do nothing
			}
		}, States.stateInitMap);
	}

	public synchronized void draw(GameGraphic g) throws Exception {
		map.draw(g);
	}

	public void update(int position) throws Exception {
		if (position == 0) {
			LogicFSM.exec();
		}
	}

	public void onExit() throws Exception {

	}

	public boolean isEnd() throws Exception {
		return false;
	}

}
