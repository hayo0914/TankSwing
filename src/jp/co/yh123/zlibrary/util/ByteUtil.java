package src.jp.co.yh123.zlibrary.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class ByteUtil {

	public static void bitOn(byte[] data, int number) {
		int wkint = number / 8;
		if (wkint >= data.length) {
			return;
		}
		byte wk = 0x01;
		wk = (byte) (((wk & 0xff) << (number % 8)) & 0xff);
		data[wkint] = (byte) ((data[wkint] | (wk & 0xff)) & 0xff);
	}

	public static void bitOff(byte[] data, int number) {
		int wkint = number / 8;
		if (wkint >= data.length) {
			return;
		}
		byte wk = (byte) 0xfe;
		wk = (byte) (((wk & 0xff) << (number % 8)) & 0xff);
		data[wkint] = (byte) ((data[wkint] & (wk & 0xff)) & 0xff);
	}

	public static boolean checkBit(byte[] data, int number) {
		int wkint = number / 8;
		if (wkint >= data.length) {
			return false;
		}
		byte wk = 0x01;
		wk = (byte) (((wk & 0xff) << (number % 8)) & 0xff);
		byte wk2 = data[wkint];
		if (((byte) (0xff & wk) & (byte) (0xff & wk2)) >= 1) {
			return true;
		} else {
			return false;
		}

	}

	public static void dumpBit(byte[] data) {
		for (int i = 0; i < data.length; i++) {
			System.out.println(String.valueOf(data[i] & 0xff));
		}
	}

	public static void intToByte2(byte[] data, int number) {
		if (data == null) {
			return;
		} else if (data.length < 2) {
			return;
		}
		data[1] = (byte) (number & 0xFF);
		data[0] = (byte) ((number >>> 8) & 0xFF);

		return;
	}

	/**
	 * @param data0
	 *            256~65535�̌�
	 * @param data1
	 *            0~256�̌�
	 * @return
	 */
	public static int byte2ToInt(byte data0, byte data1) {
		int ret = 0;
		ret += data0 & 0xff;
		ret <<= 8;
		ret += data1 & 0xff;
		return ret;
	}

	/**
	 * Stream�����[�h����Byte�ɃZ�b�g����
	 * 
	 * @param in
	 *            HTTP, Resoruce�Ȃǂ�Stream
	 * @return byte�f�[�^��Ԃ��܂��B
	 * @throws IOException
	 */
	public static byte[] readInputStream(InputStream in) throws IOException {

		ByteArrayOutputStream out = null;
		byte[] w = new byte[10240];
		byte[] data;

		try {
			out = new ByteArrayOutputStream();
			while (true) {
				int size = in.read(w);
				if (size <= 0)
					break;
				out.write(w, 0, size);
			}
			data = out.toByteArray();

		} catch (Exception e) {
			data = null;

		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}

		if (data == null) {
			throw new IOException();
		}

		return data;
	}

	/**
	 * �����񂩂�o�C�g�z����擾���܂��B
	 * 
	 * @param str
	 *            �Ώە�����
	 * @return UTF-8�̃o�C�g�z���Ԃ��܂��B
	 */
	public static byte[] getBytes(String str) {

		byte[] b = null;
		try {
			b = str.getBytes("MS932");
		} catch (UnsupportedEncodingException e) {
			b = str.getBytes();
		}
		return b;
	}

	/**
	 * �o�C�g�z�񂩂當����𐶐����܂��B
	 * 
	 * @param data
	 *            �o�C�g�z��
	 * @return UTF-8�̕������Ԃ��܂��B
	 */
	public static String byte2Str(byte[] data) {

		String str = null;
		// str = new String(data);
		try {
			str = new String(data, 0, data.length, "MS932");
		} catch (UnsupportedEncodingException e) {
			str = new String(data);
		}
		return str;
	}
}
