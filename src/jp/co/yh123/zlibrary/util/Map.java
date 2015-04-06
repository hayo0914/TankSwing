package src.jp.co.yh123.zlibrary.util;

import java.util.Hashtable;

public class Map {

	private Hashtable map = new Hashtable();

	public Map() {

	}

	public void clear() {
		map.clear();
	}

	public void addValue(String key, Object value) throws Exception {
		map.put(key, value);
	}

	public Object getValue(String key) throws Exception {
		return map.get(key);
	}

	public void removeValue(String key) throws Exception {
		map.remove(key);
	}

	public boolean contain(String key) throws Exception {
		return map.containsKey(key);
	}
	
}
