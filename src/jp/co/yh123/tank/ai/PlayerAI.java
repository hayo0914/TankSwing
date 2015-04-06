package src.jp.co.yh123.tank.ai;

import src.jp.co.yh123.tank.chara.ActionWalk;
import src.jp.co.yh123.tank.chara.Chara;
import src.jp.co.yh123.tank.chara.IAIInterface;
import src.jp.co.yh123.tank.game.GameConstants;
import src.jp.co.yh123.zlibrary.platform.ActionEventAdapter;
import src.jp.co.yh123.zlibrary.platform.KeyState;

public class PlayerAI implements IAIInterface {

	public void move(Chara c) throws Exception {

		/*
		 * プレイヤーがアクションを起こしたかをチェックする。moveFlugは、
		 * プレイヤーが攻撃や移動などのアクションを行った場合にTRUEとなる。
		 * そしてTRUEのときは、プレイヤーのアクションに適切なタイプを設定し、
		 * 行動のステートに遷移させる。同時にルートのステートもプレイヤー行動(Move)ステートに遷移する
		 */

		// 移動の処理
		// キーチェック
		int moveX = 0;
		int moveY = 0;
		boolean moveFlug = false;

		// キー状態取得
		KeyState key = ActionEventAdapter.getKeyState();

		if (key.chkKeyPushState(KeyState.KEY_AST)) {
			// 足元メニュー
			c.showAshimotoMenu();
			return;
		}

		if (GameConstants.IS_MOBILE) {
			// テンキー操作チェック ★携帯版
			if (key.chkKeyPushState(KeyState.KEY_8)) {
				// 下
				moveY++;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_2)) {
				// 上
				moveY--;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_4)) {
				// 左
				moveX--;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_6)) {
				// 右
				moveX++;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_9)) {
				// 右下
				moveX++;
				moveY++;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_7)) {
				// 左下
				moveX--;
				moveY++;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_1)) {
				// 左上
				moveX--;
				moveY--;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_3)) {
				// 右上
				moveX++;
				moveY--;
				moveFlug = true;
			}
		} else {
			// テンキー操作チェック
			if (key.chkKeyPushState(KeyState.KEY_2)) {
				// 下
				moveY++;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_8)) {
				// 上
				moveY--;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_4)) {
				// 左
				moveX--;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_6)) {
				// 右
				moveX++;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_3)) {
				// 右下
				moveX++;
				moveY++;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_1)) {
				// 左下
				moveX--;
				moveY++;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_7)) {
				// 左上
				moveX--;
				moveY--;
				moveFlug = true;
			} else if (key.chkKeyPushState(KeyState.KEY_9)) {
				// 右上
				moveX++;
				moveY--;
				moveFlug = true;
			}
		}
		if (key.chkKeyPushState(KeyState.KEY_ENTER)) {
			// メニュー表示
			c.showMainMenu();
			return;
		}
		// 方向転換？
		// if (key.chkKeyPushState(KeyState.KEY_SFT1)) {
		// // 方向を選択する
		// getGame().selfMessage("どの方向？");
		// // WF開始
		// getGame().showMediator(
		// new SceneSelectDirection(
		// ApplicationFacade.getModel().currentChara),
		// true);
		// return;
		// }
		// 足踏み？
		// if (key.chkKeyPushState(KeyState.KEY_SFT2)) {
		// changeState(StatePlayerAshibumi.get());
		// return;
		// }
		// 射撃
		// if (key.chkKeyPushState(KeyState.KEY_SHP)) {
		// if (p.shoot1()) {
		// p.reduceMovePointsAll();
		// changeState(StatePlayerTurnEnd.get());
		// } else {
		// // script
		// getModel().scriptCurrentChara = p;
		// getGame().execScript(2, "wait", null);
		// }
		// return;
		// }

		// マス移動?
		if ((!moveFlug) || (moveX == 0 && moveY == 0)) {
			return;
		}
		int toCellX = c.getCellX() + moveX;
		int toCellY = c.getCellY() + moveY;
		// 向きを変更
		c.setDirection(c.getDirectionToNext(toCellX, toCellY));
		if (!c.canGoTo(toCellX, toCellY)) {
			// // 移動先に移動できない場合
			// if (getModel().map.get(toCellX, toCellY).getChara() != null)
			// {
			// int direction = Map.getDirectionToNext(p, toCellX,
			// toCellY);
			// if (Map.isOblique(direction)
			// && !Map.isAbleToMoveOblique(p.getCellX(), p
			// .getCellY(), toCellX, toCellY)) {
			// // 壁がある
			// return;
			// }
			// // enemyがいる場合
			// Chara e = getModel().map.get(toCellX,
			// toCellY).getChara();
			// if (e.isMonster()) {
			// // enemyなら攻撃
			// p.reduceMovePointsAll();
			// // script
			// getModel().scriptCurrentChara = p;
			// getModel().scriptTgChara = e;
			// getGame().execScript(0, "attack", null);
			// changeState(StatePlayerTurnEnd.get());
			// return;
			// } else if (e.isGoodNPC()) {
			// // GoodNPCなら入れ替え
			// getGame().moveTo(Model.PLAYER_MOVE_FRAME, p,
			// e.getCellX(), e.getCellY());
			// getGame().moveTo(Model.PLAYER_MOVE_FRAME, e,
			// p.getCellX(), p.getCellY());
			// p.reduceMovePoints();
			// e.reduceMovePoints();
			// changeState(StatePlayerTurnEnd.get());
			// return;
			// } else {
			// return;
			// }
			// } else {
			// if ((tip.hasMapObject() && tip.getMapObject().isDoor())
			// && p.doSomethingTo(tip)) {
			// // 行く先が扉で、かつ閉まっている場合のみ、
			// // do somethingメソッドを呼び出してドアを自動で開ける
			// // それ以外は、誤操作を引き起こしてしまうため、そのメソッドは実行しない
			// p.reduceMovePointsAll();
			// changeState(StatePlayerTurnEnd.get());
			// return;
			// } else {
			// return;
			// }
			// }
		} else {
			// 移動できる場合
			c.reduceMovePoints();
			c.changeAction(new ActionWalk(3, toCellX, toCellY, c));
		}
	}
}
