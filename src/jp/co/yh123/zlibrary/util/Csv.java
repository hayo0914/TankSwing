package src.jp.co.yh123.zlibrary.util;

import src.jp.co.yh123.zlibrary.io.ResourceFileReader;
import src.jp.co.yh123.zlibrary.platform.StringUtil;

public class Csv {

	private List data = null;
	private List results = null;
	private int keyColumn = 0;
	private HashIndex index = null;

	public Csv(byte[] bData, int keyColumn) throws Exception {
		String wk = ByteUtil.byte2Str(bData);
		data = StringToList(wk);
		results = new List(10);
		index = new HashIndex(data.size(), this, keyColumn);
	}

	public Csv(byte[] bData) throws Exception {
		String wk = ByteUtil.byte2Str(bData);
		data = StringToList(wk);
	}

	public static Csv createCsvFromResourceFile(String path, int keyColumn)
			throws Exception {
		return new Csv(ResourceFileReader.readFile(path).getBData(), 0);
	}

	public static Csv createCsvFromResourceFile(String path) throws Exception {
		return new Csv(ResourceFileReader.readFile(path).getBData());
	}

	public void spool() {
		try {
			List col = null;
			System.out.println("行サイズ:" + getSize());
			for (int i = 0; i < data.size(); i++) {
				col = getData(i);
				if (col == null) {
					continue;
				}
				for (int j = 0; j < col.size(); j++) {
					System.out.print(col.elementAt(j));
					if (j < col.size() - 1) {
						System.out.print("||");
					} else {
						System.out.print("\n");
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getSize() {
		if (data == null) {
			return 0;
		}
		return data.size();
	}

	public int getColSize() throws Exception {
		if (data == null) {
			return 0;
		}
		return getData(0).size();
	}

	public List getDataByKey(String key) throws Exception {
		if (index == null) {
			throw new Exception("No Index");
		}
		results.removeAllElements();
		int indexRow = index.get(key);
		String workKey = null;
		for (int i = indexRow; i < data.size(); i++) {
			workKey = getString(i, keyColumn);
			if (workKey.equals(key)) {
				results.add(getData(i));
			} else {
				break;
			}
		}
		return results;
	}

	private static final int INITIAL_ARRAY_SIZE = 100;

	/**
	 * CSV形式のstringをvectorに変換(rowVector(colVector))
	 * 
	 * @param data
	 * @return
	 */
	private static List StringToList(String data) throws Exception {

		List rows = new List(INITIAL_ARRAY_SIZE);
		try {

			int nextRow = 0;
			String strRow = null;
			List cols = null;
			data = StringUtil.replaceAll(data, "\t", "");
			loop: while (true) {
				// strRow = data.substring(nextRow, data
				// .indexOf("\n", nextRow + 1) - 1);
				strRow = data.substring(nextRow, data
						.indexOf("\n", nextRow + 1));
				strRow = StringUtil.replace(strRow, "\r", "");
				strRow = StringUtil.replace(strRow, "\n", "");
				int index = 0;
				cols = new List(INITIAL_ARRAY_SIZE);
				while (strRow.indexOf(",", index) >= 0) {
					cols.add(strRow
							.substring(index, strRow.indexOf(",", index)));
					index = strRow.indexOf(",", index) + 1;
				}
				cols.add(strRow.substring(index, strRow.length()));
				// コメント行
				if (strRow.indexOf("#") != 0) {
					rows.add(cols);
				}

				nextRow = data.indexOf("\n", nextRow) + 1;
				if (nextRow + 1 >= data.length()) {
					break loop;
				}
			}
			return rows;
		} catch (Exception e) {
			System.out.println("csv変換失敗:" + (rows.size() + 1) + "行目");
			System.out.println(data);
			throw e;
		}
	}

	public List getData(int row) throws Exception {
		return (List) data.elementAt(row);
	}

	public int getInt(int row, int col) throws Exception {
		List rowV = getData(row);
		return rowV.elementAtInt(col);
	}

	public double getDouble(int row, int col) throws Exception {
		List rowV = getData(row);
		return rowV.elementAtDouble(col);
	}

	public String getString(int row, int col) throws Exception {
		List rowV = getData(row);
		return rowV.elementAtString(col);
	}

}
