package src.jp.co.yh123.tank.logic;

public interface ITaskRunner {

	public void execTasks();

	public boolean hasTask();

	public boolean isSync();

	public void notifySyncStart();

	public void notifySyncEnd();
}
