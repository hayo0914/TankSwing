package src.jp.co.yh123.tank.logic;

public interface ILogicCharaInterface {

	public void move() throws Exception;

	public void initRound() throws Exception;

	public void initTurn() throws Exception;

	public boolean isExists() throws Exception;

	public boolean isTurnEnd() throws Exception;

	public int getInitiative() throws Exception;

}
