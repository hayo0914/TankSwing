package src.jp.co.yh123.zlibrary.util;

public class HashIndex {

	int[] indexTable;
	int indexSize;

	public HashIndex(int indexSize, Csv csv, int keyColumn) throws Exception {
		this.indexSize = indexSize;
		this.indexTable = new int[indexSize];
		createIndex(csv, keyColumn);
	}

	public void createIndex(Csv csv, int keyColumn) throws Exception {
		int size = csv.getSize();
		String key = null;
		for (int i = 0; i < size; i++) {
			key = csv.getString(i, keyColumn);
			add(key, i);
		}
	}

	public void add(String key, int rownum) throws Exception {
		int hash = getHash(key, indexSize);
		indexTable[hash] = rownum;
	}

	public int get(String key) throws Exception {
		return indexTable[getHash(key, indexSize)];
	}

	private int getHash(String str, int size) {
		return str.hashCode() % size;
	}
}
