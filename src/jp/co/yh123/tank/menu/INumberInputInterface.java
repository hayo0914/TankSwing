package src.jp.co.yh123.tank.menu;

public interface INumberInputInterface {

	public static interface INumberInputCallBackInterface {

		public void numberInputed(int inputNumber,
				INumberInputInterface inputMenu) throws Exception;

	}

	public void show(int initNumber, INumberInputCallBackInterface callback)
			throws Exception;

	public void end() throws Exception;

}
