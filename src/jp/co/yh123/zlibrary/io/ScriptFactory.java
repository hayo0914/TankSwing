package src.jp.co.yh123.zlibrary.io;

import src.jp.co.yh123.zlibrary.scriptengine.Script;
import src.jp.co.yh123.zlibrary.util.Csv;

public class ScriptFactory {
	/***************************************************************************
	 * #scriptID,ファイルパス <br>
	 * 0,/res/basic.script
	 **************************************************************************/
	private static ScriptFactory ins = null;

	private Csv scriptMaster = null;

	private Script[] scripts = null;

	private ScriptFactory() {
		// do nothing
	}

	public static void init(Csv scriptMaster) throws Exception {
		if (ins != null) {
			throw new Exception("多重インスタンス化");
		}
		ins = new ScriptFactory(scriptMaster);
	}

	public static ScriptFactory getInstance() {
		return ins;
	}

	public ScriptFactory(Csv scriptMaster) {
		this.scriptMaster = scriptMaster;
		scripts = new Script[scriptMaster.getSize()];
		ins = this;
	}

	public Script getScript(int id) throws Exception {
		try {
			if (scripts[id] == null) {
				// ロードが必要
				scripts[id] = new Script(new String(ResourceFileReader
						.readFile(scriptMaster.getString(id, 1)).getBData()));
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return scripts[id];
	}

	public void disposeSpr(int id) {
		if (scripts[id] != null) {
			scripts[id] = null;
		}
	}
}
