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
// * アプリケーションのメインクラスです。
// *
// * @author Takuto Nishioka
// */
// public abstract class BaseMain extends MIDlet {
//
// // resume判定用
// private boolean _isResume = false;
//
// // ダウンロードパス
// private static String _sourceURL = "";
//
// // staticアクセス用
// private static BaseMain _MIDlet;
//
// /**
// * コンストラクタ
// *
// */
// public BaseMain() {
// _sourceURL = getAppProperty("SourceURL");
// _MIDlet = this;
// }
//
// /**
// * アプリケーションのエントリーポイントです。<br>
// * MIDletの起動準備が整い、開始（再開）される際に呼ばれます。<br>
// * 一時停止からの再開時にも呼ばれるため、ここにアプリケーションの初期化処理を記述すると
// * 新規起動時しか初期化する必要の無いものを再度初期化してしまう恐れがあります。
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
// * アプリケーションが一時停止される際に呼ばれます。 <br>
// * 機器によっては一時停止までに費やせる時間に制限を設ける場合があるため、ここで大量の 処理を行わずにすむような工夫が必要です。
// */
// protected void pauseApp() {
//
// DebugUtil.system("BaseMain pauseApp()");
// _isResume = true;
// }
//
// /**
// * アプリケーションが終了する際に呼ばれます。 <br>
// * 機器によっては終了に費やせる時間に制限を設ける場合があるため、ここで大量の処理を 行わずにすむような工夫が必要です。
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
// * サブクラスでオーバーライドします。
// *
// */
// public abstract void appStart() throws Exception;
//
// /**
// * サブクラスでオーバーライドします。
// *
// */
// public abstract void appResume() throws Exception;
//
// /**
// * アクティブなキャンバスを切り替えます。
// *
// * @param cvs
// * 有効にするキャンバスのインスタンス
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
// * アプリケーションを終了します。
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
// * ブラウザで指定されたURLにアクセスします。<br>
// * この場合、アプリ復帰時に resume() が呼ばれます。
// *
// * @param url
// * 遷移先URL
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
// * アプリダウンロードURLを取得します。<br>
// * JADのgetSourceURLパラメタで指定したURLを取得します。
// *
// * @return アプリダウンロードURLを返します。
// */
// public static String getPath() {
//
// return _sourceURL;
// }
//
// }
