package src.jp.co.yh123.zlibrary.io;
//package src.jp.co.yh123.zcore.io;
//
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//import javax.microedition.io.Connector;
//
//import com.j_phone.io.StorageConnection;
//
//public class MidpStrage {
//
//	public static final int MEM_CARD = 0;
//	public static final int MEM_BODY = 1;
//	private static final String PATH_CARD = "file:///mc/";
//	private static final String PATH_BODY = "file:///ms/";
//
//	/**
//	 * �t�@�C���̏�������
//	 */
//	public static void writeFile(String folderName, String fileName,
//			byte[] data, int memory) throws Exception {
//		String folder;
//		StorageConnection c = null;
//		OutputStream out = null;
//		try {
//			// �t�H���_�̐���
//			// folder = fileName.substring(0, fileName.lastIndexOf('/') + 1);
//
//			if (memory == MEM_BODY) {
//				folder = PATH_BODY + folderName + "/";
//			} else {
//				folder = PATH_CARD + folderName + "/";
//			}
//			System.out.println(folder);
//			c = (StorageConnection) Connector.open(folder);
//			if (!c.exists()) {
//				c.createFolder();
//			}
//			c.close();
//
//			// �t�@�C���Ƃ̐ڑ�
//			System.out.println(folder);
//			c = (StorageConnection) Connector.open(folder + fileName);
//			out = c.openOutputStream();
//
//			// �t�@�C���ւ̏�������
//			out.write(data);
//
//			// �t�@�C���Ƃ̐ؒf
//			out.close();
//			c.close();
//		} catch (Exception e) {
//			try {
//				if (c != null)
//					c.close();
//				if (out != null)
//					out.close();
//			} catch (Exception e2) {
//			}
//			throw e;
//		}
//	}
//
//	/**
//	 * �t�@�C���̃��l�[��
//	 */
//	public static void renameFile(String folderName, String fileName,
//			String fileNameNew, int memory) throws Exception {
//		String folder;
//		StorageConnection c = null;
//		try {
//			if (memory == MEM_BODY) {
//				folder = PATH_BODY + folderName + "/";
//			} else {
//				folder = PATH_CARD + folderName + "/";
//			}
//			// �t�@�C���Ƃ̐ڑ�
//			System.out.println(folder);
//			c = (StorageConnection) Connector.open(folder + fileName);
//			if (c.exists()) {
//				c.renameTo(fileNameNew);
//			}
//
//			c.close();
//		} catch (Exception e) {
//			try {
//				if (c != null)
//					c.close();
//			} catch (Exception e2) {
//			}
//			throw e;
//		}
//	}
//
//	/**
//	 * ����t�H���_�ȉ��̃t�H���_�ꗗ���擾
//	 */
//	public static String[] getFolderList(String folderName, int memory)
//			throws Exception {
//		String folder;
//		StorageConnection c = null;
//		String[] rtn = null;
//		try {
//			if (memory == MEM_BODY) {
//				folder = PATH_BODY + folderName + "/";
//			} else {
//				folder = PATH_CARD + folderName + "/";
//			}
//			// �t�@�C���Ƃ̐ڑ�
//			System.out.println(folder);
//			c = (StorageConnection) Connector.open(folder);
//			if (c.exists()) {
//				rtn = c.list();
//			}
//
//			c.close();
//		} catch (Exception e) {
//			try {
//				if (c != null)
//					c.close();
//			} catch (Exception e2) {
//			}
//			throw e;
//		}
//		return rtn;
//	}
//
//	/**
//	 * �t�@�C���̍폜
//	 */
//	public static void deleteFile(String folderName, String fileName, int memory)
//			throws Exception {
//		String folder;
//		StorageConnection c = null;
//		try {
//			if (memory == MEM_BODY) {
//				folder = PATH_BODY + folderName + "/";
//			} else {
//				folder = PATH_CARD + folderName + "/";
//			}
//			// �t�@�C���Ƃ̐ڑ�
//			System.out.println(folder);
//			c = (StorageConnection) Connector.open(folder + fileName);
//			if (c.exists()) {
//				c.delete();
//			}
//
//			c.close();
//		} catch (Exception e) {
//			try {
//				if (c != null)
//					c.close();
//			} catch (Exception e2) {
//			}
//			throw e;
//		}
//	}
//
//	/**
//	 * �t�@�C���̓ǂݍ���
//	 */
//	public static byte[] readFile(String folderName, String fileName, int memory)
//			throws Exception {
//		int size;
//		byte[] w = new byte[10240];
//		StorageConnection c = null;
//		InputStream in = null;
//		ByteArrayOutputStream out = null;
//		String folder = null;
//		if (memory == MEM_BODY) {
//			folder = PATH_BODY + folderName;
//		} else {
//			folder = PATH_CARD + folderName;
//		}
//		try {
//			// �t�@�C���Ƃ̐ڑ�
//			c = (StorageConnection) Connector.open(folder + "/" + fileName);
//			in = c.openInputStream();
//			out = new ByteArrayOutputStream();
//
//			// �t�@�C������̓ǂݍ���
//			while (true) {
//				w = new byte[10240];
//				size = in.read(w);
//				if (size <= 0)
//					break;
//				out.write(w, 0, size);
//			}
//
//			// �t�@�C���Ƃ̐ؒf
//			out.close();
//			in.close();
//			c.close();
//			return out.toByteArray();
//		} catch (Exception e) {
//			try {
//				if (c != null)
//					c.close();
//				if (in != null)
//					in.close();
//				if (out != null)
//					out.close();
//			} catch (Exception e2) {
//				e.printStackTrace();
//			}
//			e.printStackTrace();
//			return null;
//		}
//	}
//}
