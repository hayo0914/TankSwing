package src.jp.co.yh123.tank.logic;

import src.jp.co.yh123.tank.game.GlobalGameValues;
import src.jp.co.yh123.tank.map.ICharaInterface;
import src.jp.co.yh123.tank.map.MapFactory;
import src.jp.co.yh123.zlibrary.fsm.IState;
import src.jp.co.yh123.zlibrary.fsm.State;
import src.jp.co.yh123.zlibrary.util.List;
import src.jp.co.yh123.zlibrary.util.ListComparator;

public class InitRoundState extends State implements IState {

	public void enter() throws Exception {
		GlobalGameValues.get().incrementRoundCount();
		List charas = MapFactory.getMap().getCharaAll();
		// イニシアチブの順序でソート
		charas.sort(new ListComparator() {
			public boolean compare(Object o1, Object o2) throws Exception {
				return ((ICharaInterface) o1).getInitiative() < ((ICharaInterface) o2)
						.getInitiative();
			}
		});
		for (int i = 0; i < charas.size(); i++) {
			((ILogicCharaInterface) charas.elementAt(i)).initRound();
		}
	}

	public void execute() throws Exception {
		List charas = MapFactory.getMap().getCharaAll();
		int moveCount = 0;
		int roundEndCount = 0;
		for (int i = 0; i < charas.size(); i++) {
			ILogicCharaInterface chara = (ILogicCharaInterface) charas
					.elementAt(i);
			if (chara.isTurnEnd()) {
				roundEndCount++;
			} else {
				moveCount++;
				chara.move();
				if (chara.isTurnEnd()) {
					roundEndCount++;
				}
			}
		}
		List effects = MapFactory.getMap().getAsyncEffectAll();
		for (int i = 0; i < effects.size(); i++) {
			ILogicEffectInterface effect = (ILogicEffectInterface) effects
					.elementAt(i);
			if (!effect.isEnd()) {
				effect.update();
			} else {
				effects.removeElement(effect);
				i--;
			}
		}
		List syncEffects = MapFactory.getMap().getSyncEffectAll();
		for (int i = 0; i < syncEffects.size(); i++) {
			ILogicEffectInterface effect = (ILogicEffectInterface) syncEffects
					.elementAt(i);
			if (!effect.isEnd()) {
				effect.update();
			} else {
				syncEffects.removeElement(effect);
				i--;
			}
		}
		if (moveCount - roundEndCount == 0) {
			enter();
		}
	}

	public void exit() throws Exception {

	}

	public String getStateName() {
		return "InitRoundState";
	}

}