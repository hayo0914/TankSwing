package src.jp.co.yh123.zlibrary.util;

public interface ListComparator {

	/**
	 * falseだとo1が前に来る。
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 * @throws Exception
	 */
	public boolean compare(Object o1, Object o2) throws Exception;

}
