package src.jp.co.yh123.tank.ai;

import src.jp.co.yh123.tank.chara.IAIInterface;

public class AIFactory {

	public static IAIInterface getPlayerAI() {
		return new PlayerAI();
	}

}
