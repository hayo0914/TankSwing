package src.jp.co.yh123.zlibrary.platform;
// package src.jp.co.yh123.zcore.graphic;
//
// import javax.microedition.lcdui.Display;
// import javax.microedition.midlet.MIDlet;
// import javax.microedition.midlet.MIDletStateChangeException;
//
// import src.jp.co.yh123.zcore.base.DebugUtil;
//
// /**
// * �A�v���P�[�V�����̃��C���N���X�ł��B
// *
// * @author Takuto Nishioka
// */
// public abstract class BaseMain extends MIDlet {
//
// // resume����p
// private boolean _isResume = false;
//
// // �_�E�����[�h�p�X
// private static String _sourceURL = "";
//
// // static�A�N�Z�X�p
// private static BaseMain _MIDlet;
//
// /**
// * �R���X�g���N�^
// *
// */
// public BaseMain() {
// _sourceURL = getAppProperty("SourceURL");
// _MIDlet = this;
// }
//
// /**
// * �A�v���P�[�V�����̃G���g���[�|�C���g�ł��B<br>
// * MIDlet�̋N�������������A�J�n�i�ĊJ�j�����ۂɌĂ΂�܂��B<br>
// * �ꎞ��~����̍ĊJ���ɂ��Ă΂�邽�߁A�����ɃA�v���P�[�V�����̏������������L�q�����
// * �V�K�N������������������K�v�̖������̂��ēx���������Ă��܂����ꂪ����܂��B
// */
// protected void startApp() throws MIDletStateChangeException {
//
// try {
// if (!_isResume) {
// DebugUtil.system("BaseMain start()");
// appStart();
//
// } else {
// DebugUtil.system("BaseMain resume()");
// _isResume = false;
// appResume();
// }
// } catch (Exception e) {
// e.printStackTrace();
// }
// }
//
// /**
// * �A�v���P�[�V�������ꎞ��~�����ۂɌĂ΂�܂��B <br>
// * �@��ɂ���Ă͈ꎞ��~�܂łɔ�₹�鎞�Ԃɐ�����݂���ꍇ�����邽�߁A�����ő�ʂ� �������s�킸�ɂ��ނ悤�ȍH�v���K�v�ł��B
// */
// protected void pauseApp() {
//
// DebugUtil.system("BaseMain pauseApp()");
// _isResume = true;
// }
//
// /**
// * �A�v���P�[�V�������I������ۂɌĂ΂�܂��B <br>
// * �@��ɂ���Ă͏I���ɔ�₹�鎞�Ԃɐ�����݂���ꍇ�����邽�߁A�����ő�ʂ̏����� �s�킸�ɂ��ނ悤�ȍH�v���K�v�ł��B
// *
// * @param unconditional
// */
// protected void destroyApp(boolean unconditional)
// throws MIDletStateChangeException {
//
// DebugUtil.debug("[destroyApp]", "BaseMain destroyApp(" + unconditional +
// ")");
// }
//
// /**
// * �T�u�N���X�ŃI�[�o�[���C�h���܂��B
// *
// */
// public abstract void appStart() throws Exception;
//
// /**
// * �T�u�N���X�ŃI�[�o�[���C�h���܂��B
// *
// */
// public abstract void appResume() throws Exception;
//
// /**
// * �A�N�e�B�u�ȃL�����o�X��؂�ւ��܂��B
// *
// * @param cvs
// * �L���ɂ���L�����o�X�̃C���X�^���X
// */
// public void setCanvas(BaseCanvas cvs) {
// DebugUtil
// .system("BaseMain setCanvas(" + cvs.getClass().getName() + ")");
// Display.getDisplay(this).setCurrent(cvs);
// DebugUtil.debug("[setCanvas]", "setCurrent");
// DebugUtil.debug("[setCanvas]", "activateCvs");
// }
//
// /**
// * �A�v���P�[�V�������I�����܂��B
// *
// */
// public void exitApp() {
//
// DebugUtil.system("BaseMain exitApp()");
// try {
// destroyApp(false);
// notifyDestroyed();
// } catch (Exception e) {
// e.printStackTrace();
// }
// }
//
// /**
// * �u���E�U�Ŏw�肳�ꂽURL�ɃA�N�Z�X���܂��B<br>
// * ���̏ꍇ�A�A�v�����A���� resume() ���Ă΂�܂��B
// *
// * @param url
// * �J�ڐ�URL
// */
// public static void execBrowser(String[] url) {
//
// try {
// _MIDlet.platformRequest(url[0]);
// } catch (Exception e) {
// e.printStackTrace();
// }
// }
//
// /**
// * �A�v���_�E�����[�hURL���擾���܂��B<br>
// * JAD��getSourceURL�p�����^�Ŏw�肵��URL���擾���܂��B
// *
// * @return �A�v���_�E�����[�hURL��Ԃ��܂��B
// */
// public static String getPath() {
//
// return _sourceURL;
// }
//
// }
