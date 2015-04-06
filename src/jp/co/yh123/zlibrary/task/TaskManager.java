package src.jp.co.yh123.zlibrary.task;

import src.jp.co.yh123.zlibrary.util.Queue;

public class TaskManager {

	private int _maxTasks = 0;
	private Queue _taskQueue = null;

	private class TaskSet {
		TaskSet(ITaskInterface task, ITaskEndListener listener) {
			this.task = task;
			this.listener = listener;
		}

		TaskSet(ITaskInterface task) {
			this.task = task;
			this.listener = null;
		}

		ITaskInterface task;
		ITaskEndListener listener;
	}

	/**
	 * @param maxTasks
	 */
	public TaskManager(int maxTasks) {
		init(maxTasks);
	}

	/**
	 * Removes all tasks.
	 */
	public void clearTasks() {
		init(_maxTasks);
	}

	/**
	 * Adds new task.
	 * 
	 * @param task
	 */
	public void addTask(ITaskInterface task) {
		_taskQueue.add(new TaskSet(task));
	}

	/**
	 * Adds new task.
	 * 
	 * @param task
	 * @param listener
	 */
	public void addTask(ITaskInterface task, ITaskEndListener listener) {
		_taskQueue.add(new TaskSet(task, listener));
	}

	/**
	 * @return If this has tasks to execute, returns true.
	 */
	public boolean hasTasks() {
		return _taskQueue.size() != 0;
	}

	/**
	 * If the first task ends after the execution, executes next task at once.
	 * And if the first task does not end in one execution, doesn't execute the
	 * next.
	 * 
	 * @param millSecond
	 */
	public void execTasks(long millSecond) {
		long current = System.currentTimeMillis();
		int size = _taskQueue.size();
		int count = 0;
		while (System.currentTimeMillis() - current < millSecond
				&& count < size) {
			TaskSet taskSet = (TaskSet) _taskQueue.removeFirst();
			count++;
			if (taskSet.task.execute()) {
				taskSet.listener.taskEnds();
			} else {
				_taskQueue.add(taskSet);
			}
		}
	}

	private void init(int maxTasks) {
		_maxTasks = maxTasks;
		_taskQueue = new Queue(maxTasks);
	}
}
