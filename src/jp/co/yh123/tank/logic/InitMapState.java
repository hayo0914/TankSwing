package src.jp.co.yh123.tank.logic;

import src.jp.co.yh123.tank.game.GlobalGameValues;
import src.jp.co.yh123.tank.map.MapFactory;
import src.jp.co.yh123.tank.sfx.SkillFactory;
import src.jp.co.yh123.zlibrary.fsm.IState;
import src.jp.co.yh123.zlibrary.fsm.State;

public class InitMapState extends State implements IState {

	public void enter() throws Exception {
		GlobalGameValues.createInstance();
		SkillFactory.createInstance();
		MapFactory.getMap().initMap();
		changeState(States.stateInitRound);
	}

	public void execute() throws Exception {
	}

	public void exit() throws Exception {

	}

	public String getStateName() {
		return "InitMapState";
	}

}