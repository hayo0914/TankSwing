package src.jp.co.yh123.tank.chara;

import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;
import src.jp.co.yh123.tank.map.Map;
import src.jp.co.yh123.tank.map.MapFactory;
import src.jp.co.yh123.zlibrary.util.DebugUtil;
import src.jp.co.yh123.zlibrary.util.GameMath;

public class ActionWalk implements ICharaAction {

	private int _toCellX = 0;

	private int _toCellY = 0;

	private int _moveFrame = 1;

	private double _tgX, _tgY;

	private int _execCount = 0;

	private double _spdX, _spdY;

	private boolean _isEnd = false;

	public ActionWalk(int moveFrame, int toCellX, int toCellY, Chara c)
			throws Exception {
		_moveFrame = moveFrame;
		_toCellX = toCellX;
		_toCellY = toCellY;
		setVxyToCellxy(c);
		IMaptipClbInterface tip = (IMaptipClbInterface) MapFactory.getMap()
				.get(c.getCellX(), c.getCellY());
		tip.reserve(c);
	}

	public void doAction(Chara c) throws Exception {
		_execCount++;

		if (_moveFrame > 1) {
			// 移動
			c.setPosition(c.getX() + _spdX, c.getY() + _spdY);

			// // 移動しすぎを矯正
			// if (_spdX > 0 && c.getX() > _tgX) {
			// // 行き過ぎ
			// c.setPosition(_tgX, c.getY());
			// } else if (_spdX < 0 && c.getX() < _tgX) {
			// // 行き過ぎ
			// c.setPosition(_tgX, c.getY());
			// }
			// if (_spdY > 0 && c.getY() > _tgY) {
			// // 行き過ぎ
			// c.setPosition(c.getX(), _tgY);
			// } else if (_spdY < 0 && c.getY() < _tgY) {
			// // 行き過ぎ
			// c.setPosition(c.getX(), _tgY);
			// }
		}

		// 到着判定
		if (_execCount >= _moveFrame) {
			// 到着
			MapFactory.getMap().get(c.getCellX(), c.getCellY()).removeChara(c);
			DebugUtil.assertTrue(c.isAlive());
			MapFactory.getMap().get(_toCellX, _toCellY).setChara(c);

			// 周りを確認
			c.watchAround();

			// // 罠判定
			// MapTip tip = c.getCurrentMaptip();
			// if (tip.isMapObject() && tip.getMapObject().isTrap()) {
			// ApplicationFacade.getGame().trapProc(tip, tip.getMapObject());
			// }

			_isEnd = true;
			c.useMovePoint(Chara.NORMAL_SPEED);
			c.changeAction(new ActionWait());
		}
	}

	public boolean isDoingAction(Chara c) {
		return !_isEnd;
	}

	/**
	 * 速度設定 ■目的に向かって弾を飛ばす <br>
	 * 目標に向かって飛ぶ弾の関数（） <br>
	 * { <br>
	 * 弾の初期化 <br>
	 * ├目標までの距離検出 <br>
	 * │・目標までの距離Ｘ＝（目標の座標Ｘ）ー（弾の座標Ｘ） <br>
	 * │・目標までの距離Ｙ＝（目標の座標Ｙ）ー（弾の座標Ｙ） <br>
	 * │ ↓ <br>
	 * └弾の発射角度取得し移動量設定 <br>
	 * ・弾の発射角度＝atan2(目標までの距離Ｙ、目標までの距離Ｘ) <br>
	 * ・弾の移動量Ｘ＝cos(弾の発射角度)×弾のスピード <br>
	 * ・弾の移動量Ｙ＝sin(弾の発射角度)×弾のスピード <br>
	 * ↓ <br>
	 * 移動量を計算し目的地まで飛ばす <br>
	 * ・弾の座標Ｘ＋＝弾の移動量Ｘ <br>
	 * ・弾の座標Ｙ＋＝弾の移動量Ｙ <br>
	 * ↓ <br>
	 * 当たるか画面外に出たら初めから <br>
	 * <br>
	 * 
	 * @param ent
	 * @return
	 */
	private void setVxyToCellxy(Chara c) throws Exception {

		if (_moveFrame > 1) {
			// // 移動先
			_tgX = Map.getCharaXofCell(c, _toCellX, _toCellY);
			_tgY = Map.getCharaYofCell(c, _toCellX, _toCellY);

			// 移動角度
			double deg = GameMath.atan2(_tgX - c.getX(), _tgY - c.getY());
			// double deg = c.getDirectionToNext(_toCellX, _toCellY);

			// フレーム数から移動速度計算
			this._spdX = GameMath.abs(_tgX - c.getX()) / (_moveFrame - 1);
			this._spdY = GameMath.abs(_tgY - c.getY()) / (_moveFrame - 1);
			// 移動速度
			_spdX = (double) (GameMath.cos((int) (deg + 0.5)) * _spdX);
			_spdY = (double) (GameMath.sin((int) (deg + 0.5)) * _spdY);
			// ApplicationFacade.getModel().map.dump();
		}

	}
}
