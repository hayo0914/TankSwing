package src.jp.co.yh123.zlibrary.sound;
//package src.jp.co.yh123.zcore.sound;
//
//import com.jblend.media.smaf.phrase.PhrasePlayer;
//import com.jblend.media.smaf.phrase.PhraseTrack;
//
//import src.jp.co.yh123.zcore.exception.YhUIException;
//
///**
// * �T�E���h�f�[�^���Đ����邽�߂̋@�\��񋟂��܂��B<br>
// * <br>
// * JSCL�ł͉��L�f�[�^�̓����Đ����T�|�[�g����Ă��܂����A���̃N���X�ł�SMAF(mmf)���T�|�[�g���Ă��܂���B<br>
// * <li>SMAF/Phrase(spf)�F4�a���~4�g���b�N
// * <li>SMAF(mmf)�F16�a���~1�g���b�N
// * 
// * @author Takuto Nishioka
// */
//public class SoundPlayer {
//
//	// �V���O���g��
//	private static SoundPlayer _soundPlayer;
//
//	private PhrasePlayer _player;
//
//	private PhraseTrack[] _phrase;
//
//	/**
//	 * BGM�T�E���h
//	 */
//	public static final int BGM = 0;
//
//	/**
//	 * SE�T�E���h
//	 */
//	public static final int SE = 1;
//
//	/**
//	 * �T�E���h�v���C���[�����������܂��B
//	 */
//	private SoundPlayer() {
//
//		_player = PhrasePlayer.getPlayer();
//		_phrase = new PhraseTrack[2];
//		_phrase[BGM] = _player.getTrack();
//		_phrase[SE] = _player.getTrack();
//	}
//
//	/**
//	 * �T�E���h�v���C���[���擾���܂��B
//	 * 
//	 * @return �T�E���h�v���C���[�C���X�^���X��Ԃ��܂��B
//	 */
//	public static SoundPlayer getInstance() {
//
//		if(_soundPlayer == null){
//			_soundPlayer = new SoundPlayer();
//		}
//		return _soundPlayer;
//	}
//
//	/**
//	 * BGM�T�E���h���Z�b�g���܂��B
//	 * 
//	 * @param sound
//	 *        �T�E���h�f�[�^
//	 * @throws YhUIException
//	 *         �T�E���h�f�[�^������������Ă��Ȃ��ꍇ�Ȃǂɔ������܂��B
//	 */
//	public void setBGM(SoundData sound) throws YhUIException {
//
//		try{
//			_phrase[BGM].setPhrase(sound.getSound());	
//		}catch(Exception e){
//			throw new YhUIException(YhUIException.RESOURCE_NOT_READY, e.getMessage());
//		}
//	}
//
//	/**
//	 * SE�T�E���h���Z�b�g���܂��B
//	 * 
//	 * @param sound
//	 *        �T�E���h�f�[�^
//	 * @throws YhUIException
//	 *         �T�E���h�f�[�^������������Ă��Ȃ��ꍇ�Ȃǂɔ������܂��B
//	 */
//	public void setSE(SoundData sound) throws YhUIException {
//
//		try{
//			_phrase[SE].setPhrase(sound.getSound());
//		}catch(Exception e){
//			throw new YhUIException(YhUIException.RESOURCE_NOT_READY, e.getMessage());
//		}
//	}
//
//	/**
//	 * �Đ����܂��B
//	 * 
//	 * @param type
//	 *        �T�E���h�̎��
//	 */
//	public void play(int type) {
//
//		if(type == BGM){
//			stop(BGM);
//			_phrase[BGM].play(0);
//		}else{
//			stop(SE);
//			_phrase[SE].play();
//		}
//	}
//
//	/**
//	 * �ꎞ��~���܂��B
//	 * 
//	 * @param type
//	 *        �T�E���h�̎��
//	 */
//	public void pause(int type) {
//
//		if(type == BGM){
//			_phrase[BGM].pause();
//		}else{
//			_phrase[SE].pause();
//		}
//	}
//
//	/**
//	 * �ꎞ��~����ĊJ���܂��B
//	 * 
//	 * @param type
//	 *        �T�E���h�̎��
//	 */
//	public void restart(int type) {
//
//		if(type == BGM){
//			_phrase[BGM].resume();
//		}else{
//			_phrase[SE].resume();
//		}
//	}
//
//	/**
//	 * ��~���܂��B
//	 * 
//	 * @param type
//	 *        �T�E���h�̎��
//	 */
//	public void stop(int type) {
//
//		if(type == BGM){
//			_phrase[BGM].stop();
//		}else{
//			_phrase[SE].stop();
//		}
//	}
//	
//	/**
//	 * �T�E���h���J�����܂��B
//	 * 
//	 * @param type
//	 *        �J������T�E���h
//	 */
//	public void remove(int type){
//
//		if(type == BGM){
//			_phrase[BGM].removePhrase();
//		}else{
//			_phrase[SE].removePhrase();
//		}
//	}
//
//}
