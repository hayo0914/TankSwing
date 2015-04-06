package src.jp.co.yh123.tank.game;

public class GlobalGameValues {

	long turnCount = 0;
	long roundCount = 0;

	public long getTurnCount() {
		return turnCount;
	}

	public void incrementTurnCount() {
		turnCount++;
	}

	public long getRoundCount() {
		return roundCount;
	}

	public void incrementRoundCount() {
		roundCount++;
	}

	private GlobalGameValues() {

	}

	private static GlobalGameValues _instance = null;

	public static void createInstance() {
		_instance = new GlobalGameValues();
	}

	public static GlobalGameValues get() {
		return _instance;
	}
}
