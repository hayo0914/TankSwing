package src.jp.co.yh123.zlibrary.io;
//package src.jp.co.yh123.zcore.io;
//
//import javax.microedition.rms.RecordStore;
//
//public class MidpRecordStore {
//
//	public static void save(String name, byte[] data) throws Exception {
//		RecordStore rs = null;
//		try {
//			rs = RecordStore.openRecordStore(name, true);
//			if (rs.getNumRecords() == 0) {
//				rs.addRecord(data, 0, data.length);
//			} else {
//				rs.setRecord(1, data, 0, data.length);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			if (rs != null)
//				rs.closeRecordStore();
//			System.gc();
//		}
//	}
//
//	public static byte[] load(String name) throws Exception {
//		RecordStore rs = null;
//		try {
//			rs = RecordStore.openRecordStore(name, true);
//			if (rs.getNumRecords() == 0) {
//				return null;
//			} else {
//				return rs.getRecord(1);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			if (rs != null)
//				rs.closeRecordStore();
//			System.gc();
//		}
//	}
//
//}
