package src.jp.co.yh123.zlibrary.io;
//package src.jp.co.yh123.zcore.io;
//
//import java.io.IOException;
//
//import javax.microedition.rms.RecordStore;
//
///**
// * ���R�[�h�X�g�A�𑀍삷�邽�߂̋@�\��񋟂��܂��B<br>
// * ���̃N���X�ł́ADoja�ւ̈ڐA���l�����Ă��邽�߁A��̃��R�[�h�X�g�A�݈̂����܂��B<br>
// * <br>
// * 
// * [MIDP����]<br>
// * ���R�[�h�X�g�A�𗘗p���邽�߂ɂ́AJAD��MIDlet-Data-Size���w�肷��K�v������܂��B<br>
// * �g�p�ł���e�ʂ͊�{�I�ɍő�512KB�ŁAJAR��JAD�ƃ��R�[�h�X�g�A�T�C�Y���v1M�܂łł��B<br>
// * ��) MIDlet-Data-Size: 512000<br>
// * 
// * [�N���X���p�菇]<br>
// * 1. �쐬���郌�R�[�h��1����̘A�ԂŒ�`���܂��B<br>
// * 2. ���R�[�h�T�C�Y(_SIZE)���`���܂��B<br>
// * �i�S�Ă̍��v��MIDlet-Data-Size�ȉ��ɂȂ�悤�ɂ��܂��j<br>
// * 
// * @author Takuto Nishioka
// */
//public class StoreManager {
//
//	/**
//	 * ���R�[�h�X�g�A RSTORE ���R�[�h ID=1
//	 */
//	public static final int NO_1 = 1;
//
//	/**
//	 * ���R�[�h�X�g�A RSTORE ���R�[�h ID=2
//	 */
//	public static final int NO_2 = 2;
//
//	/**
//	 * ���R�[�h�X�g�A RSTORE ���R�[�h ID=3
//	 */
//	public static final int NO_3 = 3;
//
//	// ���R�[�h�T�C�Y (byte)
//	private static final int[] _SIZE = new int[] {
//			1000, 1000, 1000
//	};
//
//	// ���R�[�h�X�g�A�I�u�W�F�N�g
//	private static RecordStore _rs;
//
//	/**
//	 * �R���X�g���N�^�͉������܂���B
//	 */
//	public StoreManager() {
//
//	}
//
//	/**
//	 * ���R�[�h�X�g�A���I�[�v�����܂��B
//	 * 
//	 * @param isInit
//	 *          ���R�[�h�X�g�A���Ȃ��ꍇ�ɍ쐬���邩�ǂ���
//	 * @throws IOException
//	 *           ���R�[�h�I�[�v���Ɏ��s�����ꍇ�ɔ������܂��B
//	 */
//	private void openRstore(boolean isInit) throws IOException {
//
//		try {
//			// ��������p���Ȃ��̂Ŗ��̂͌Œ�
//			_rs = RecordStore.openRecordStore("RSTORE", isInit);
//		} catch (Exception e) {
//			_rs = null;
//			throw new IOException("[openRstore OPEN���s]");
//		}
//	}
//
//	/**
//	 * ���R�[�h�X�g�A���N���[�Y���܂��B
//	 */
//	private void closeRstore() {
//
//		try {
//			if (_rs != null) _rs.closeRecordStore();
//		} catch (Exception e) {
//			_rs = null;
//		}
//	}
//
//	/**
//	 * ���R�[�h�X�g�A�����������܂��B
//	 * 
//	 * @throws IOException
//	 *           ���R�[�h�̏������Ɏ��s�����ꍇ�ɔ������܂��B
//	 */
//	private void initRstore() throws IOException {
//
//		try {
//			openRstore(true);
//			int rnum = _rs.getNumRecords();
//
//			// ���R�[�h�����ύX����Ă����烌�R�[�h�S�폜
//			if (rnum != 0 && rnum != _SIZE.length) {
//				for (int i = 0; i < rnum; i++) {
//					_rs.deleteRecord(i);
//				}
//				rnum = 0;
//			}
//
//			// ���R�[�h������
//			if (rnum == 0) {
//				for (int i = 0; i < _SIZE.length; i++) {
//					byte[] w = new byte[_SIZE[i]];
//					_rs.addRecord(w, 0, w.length);
//				}
//			}
//
//		} catch (Exception e) {
//			throw new IOException(e.getMessage() + "[initRstore ���������s]");
//
//		} finally {
//			closeRstore();
//		}
//	}
//
//	/**
//	 * �w�肵���T�C�Y�ŏ����ł��邩�`�F�b�N���܂��B
//	 * 
//	 * @param area
//	 *          �Ώۗ̈�
//	 * @param offset
//	 *          ����/�Ǎ��J�n�ʒu���w��
//	 * @param len
//	 *          ����/�Ǎ��������w��
//	 * @throws IOException
//	 *           �����ȗ̈�A�T�C�Y���w�肳�ꂽ�ꍇ�ɔ������܂��B
//	 */
//	private void checkSize(int area, int offset, int len) throws IOException {
//
//		// ���R�[�h�X�g�A������
//		initRstore();
//
//		if (area >= _SIZE.length) {
//			throw new IOException("[checkSize �����ȗ̈�] area=" + area + ", offset=" + offset + ", len=" + len);
//
//		} else if (offset + len > _SIZE[area]) {
//			throw new IOException("[checkSize overflow] area=" + area + ", offset=" + offset + ", len=" + len);
//		}
//	}
//
//	/**
//	 * ���R�[�h�X�g�A�̎w��̈���N���A���܂��B
//	 * 
//	 * @param area
//	 *          �Ώۗ̈�
//	 * @throws IOException
//	 *           �N���A�Ɏ��s�����ꍇ�ɔ������܂��B
//	 */
//	public void clear(int area) throws IOException {
//
//		checkSize(area, 0, 0);
//
//		// ��f�[�^���㏑�����ăN���A
//		write(new byte[_SIZE[area]], area, 0);
//	}
//
//	/**
//	 * ���R�[�h�X�g�A�փf�[�^���������݂܂��B
//	 * 
//	 * @param data
//	 *        �������ݑΏۃf�[�^�z��
//	 * @param area
//	 *        �Ώۗ̈�
//	 * @param offset
//	 *        �������݊J�n�ʒu
//	 * @throws IOException
//	 *         �������݂Ɏ��s�����ꍇ�ɔ������܂��B
//	 * @throws NullPointerException
//	 *         data��null�̏ꍇ�ɔ������܂��B
//	 */
//	public void write(byte[] data, int area, int offset) throws IOException, NullPointerException {
//
//		if (data == null) {
//			throw new NullPointerException("[write null] area=" + area + ", offset=" + offset);
//		}
//
//		checkSize(area, offset, data.length);
//
//		try {
//			openRstore(true);
//			_rs.setRecord(area, data, offset, data.length);
//
//		} catch (Exception e) {
//			throw new IOException(e.getMessage() + "[write] area=" + area + ", offset=" + offset);
//
//		} finally {
//			closeRstore();
//		}
//	}
//
//	/**
//	 * ���R�[�h�X�g�A����f�[�^��ǂݍ��݂܂��B
//	 * 
//	 * @param area
//	 *          �Ώۗ̈�
//	 * @param offset
//	 *          �ǂݍ��݊J�n�ʒu
//	 * @param len
//	 *          �ǂݍ��ޒ���
//	 * @throws IOException
//	 *           �ǂݍ��݂Ɏ��s�����ꍇ�ɔ������܂��B
//	 */
//	public byte[] read(int area, int offset, int len) throws IOException {
//
//		checkSize(area, offset, len);
//
//		byte[] ret = new byte[len];
//		try {
//			openRstore(false);
//			System.arraycopy(_rs.getRecord(area), offset, ret, 0, len);
//
//		} catch (Exception e) {
//			throw new IOException(e.getMessage() + "[read] area=" + area + ", offset=" + offset + ", len=" + len);
//
//		} finally {
//			closeRstore();
//		}
//		return ret;
//	}
//}
