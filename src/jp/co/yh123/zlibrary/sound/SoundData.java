package src.jp.co.yh123.zlibrary.sound;
//package src.jp.co.yh123.zcore.sound;
//
//import com.jblend.media.smaf.phrase.Phrase;
//
//import src.jp.co.yh123.zcore.exception.YhUIException;
//
///**
// * �T�E���h�f�[�^���Ǘ�����@�\��񋟂��܂��B
// * 
// * @author Takuto Nishioka
// */
//public class SoundData {
//
//	// �T�E���h�f�[�^
//	private Phrase _sound;
//
//	/**
//	 * �R���X�g���N�^�͉������܂���B
//	 */
//	public SoundData() {
//
//	}
//
//	/**
//	 * byte�f�[�^����T�E���h���Z�b�g���܂��B
//	 * 
//	 * @param data
//	 *        �T�E���h�f�[�^
//	 * @throws YhUIException
//	 *        �T�E���h�f�[�^�̏������Ɏ��s�����ꍇ�ɔ������܂��B
//	 */
//	public void setSound(byte[] data) throws YhUIException {
//
//		try{
//			_sound = new Phrase(data);
//		}catch(Exception e){
//			throw new YhUIException(YhUIException.RESOURCE_LOAD_FAILED, e.getMessage());
//		}
//	}
//
//	/**
//	 * �t�@�C�����w�肵�ăT�E���h���Z�b�g���܂��B
//	 * 
//	 * @param uri
//	 *        �T�E���h�t�@�C����
//	 * @throws YhUIException
//	 *        �T�E���h�f�[�^�̏������Ɏ��s�����ꍇ�ɔ������܂��B
//	 */
//	public void setSound(String uri) throws YhUIException {
//
//		try{
//			_sound = new Phrase("/" + uri);
//		}catch(Exception e){
//			throw new YhUIException(YhUIException.RESOURCE_LOAD_FAILED, e.getMessage());
//		}
//	}
//
//	/**
//	 * �T�E���h�f�[�^���擾���܂��B
//	 * 
//	 * @return �T�E���h�f�[�^��Ԃ��܂��B
//	 */
//	public Phrase getSound() {
//
//		return _sound;
//	}
//
//	/**
//	 * �T�E���h�f�[�^���J�����܂��B
//	 */
//	public void release() {
//
//		_sound = null;
//	}
//
//}
