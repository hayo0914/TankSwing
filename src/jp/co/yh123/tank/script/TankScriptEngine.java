package src.jp.co.yh123.tank.script;

import src.jp.co.yh123.tank.chara.ActionMoveForward;
import src.jp.co.yh123.tank.chara.Chara;
import src.jp.co.yh123.tank.sync.SyncTaskCounterFactory;
import src.jp.co.yh123.zlibrary.platform.StringUtil;
import src.jp.co.yh123.zlibrary.scriptengine.Script;
import src.jp.co.yh123.zlibrary.scriptengine.ScriptEngine;
import src.jp.co.yh123.zlibrary.scriptengine.ScriptEventObserver;
import src.jp.co.yh123.zlibrary.scriptengine.ScriptLine;
import src.jp.co.yh123.zlibrary.scriptengine.Thread;
import src.jp.co.yh123.zlibrary.scriptengine.ScriptEventObserver.ScriptEvent;
import src.jp.co.yh123.zlibrary.util.Map;

public class TankScriptEngine extends ScriptEngine implements
		ScriptEventObserver {

	private static TankScriptEngine instance = new TankScriptEngine();

	private TankScriptEngine() {
		super(new Map(), 30);
	}

	public TankScriptEngine getInstance() {
		return instance;
	}

	protected boolean onExecCalled() throws Exception {
		return true;
	}

	private String syncFlg = "1";

	protected void onNoSystemLibrary(Thread th, ScriptLine currentLine)
			throws Exception {

		if ("wkgoforward".equals(currentLine.lookNextToken())) {
			Chara c = (Chara) th.getInput("chara");
			ActionMoveForward action = new ActionMoveForward(c, 4);
			c.changeAction(action);
		} else if ("atk".equals(currentLine.lookNextToken())) {
			currentLine.nextToken();
			String wkNext = getValue(th, currentLine);
			Chara c = (Chara) th.getInput("chara");
			int wkIdx = (int) Double.parseDouble(wkNext);
			if (c.attack(model.scriptTgChara, wkIdx)) {
				th.getStack().push("1");// å¯â î≠ìÆèIÇÌÇË
			} else {
				th.getStack().push("0");// Ç‹ÇæèIÇÌÇÁÇ»Ç¢
			}
		} else if ("shotA".equals(currentLine.lookNextToken())) {
			// è¡îÔÇ†ÇË
			currentLine.nextToken();
			model.scriptCurrentChara.shoot2(StringUtil.toInt(currentLine
					.nextToken()), false, true);
		} else if ("shotSyncA".equals(currentLine.lookNextToken())) {
			// è¡îÔÇ†ÇË
			currentLine.nextToken();
			model.scriptCurrentChara.shoot2(StringUtil.toInt(currentLine
					.nextToken()), true, true);
		} else if ("shotB".equals(currentLine.lookNextToken())) {
			// è¡îÔÇ»Çµ
			currentLine.nextToken();
			model.scriptCurrentChara.shoot2(StringUtil.toInt(currentLine
					.nextToken()), false, false);
		} else if ("shotSyncB".equals(currentLine.lookNextToken())) {
			// è¡îÔÇ»Çµ
			currentLine.nextToken();
			model.scriptCurrentChara.shoot2(StringUtil.toInt(currentLine
					.nextToken()), true, false);
		} else if ("wfgoback".equals(currentLine.lookNextToken())) {
			ProcessBack proc = new ProcessBack(model.scriptCurrentChara, 4);
			game.processManager.startProcess(proc);
		} else if ("sync".equals(currentLine.lookNextToken())) {
			SyncTaskCounter s = (SyncTaskCounter) SyncTaskCounterFactory
					.getSyncTaskCounter();
			s.notifySyncStart();
			th.addOutput(syncFlg, syncFlg);
		} else if ("syncoff".equals(currentLine.lookNextToken())) {
			SyncTaskCounter s = (SyncTaskCounter) SyncTaskCounterFactory
					.getSyncTaskCounter();
			th.removeOutput(syncFlg);
			s.notifySyncEnd();
		}
	}

	public void notify(Thread th, int event, String returnValue)
			throws Exception {
		if (event == ScriptEvent.EVENT_END) {
			if (th.countainOutput(syncFlg)) {
				th.removeOutput(syncFlg);
			}
		}
	}

}
