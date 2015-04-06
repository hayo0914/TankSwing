package src.jp.co.yh123.tank.chara;

import src.jp.co.yh123.tank.chara.Chara;
import src.jp.co.yh123.tank.map.IMaptipInterface;
import src.jp.co.yh123.tank.map.Map;
import src.jp.co.yh123.tank.map.MapFactory;
import src.jp.co.yh123.zlibrary.util.GameMath;

public class ActionMoveForward implements ICharaAction {

	private boolean _isEnd = false;
	private Chara _chara = null;

	private int _moveFrame = 4;

	private double _tgX, _tgY;

	private int _execCount = 0;

	private double _spdX, _spdY;

	public ActionMoveForward(Chara c, int moveFrame) throws Exception {
		_chara = c;
		_moveFrame = moveFrame;
		_execCount = 0;
		setGoVxyToCellxy();
	}

	public boolean isEnd() throws Exception {
		return _isEnd;
	}

	public String getName() {
		return "ProcForward";
	}

	private void setGoVxyToCellxy() throws Exception {

		IMaptipInterface tip = MapFactory.getMap().getMaptipByDirection(
				_chara.getDirection(), _chara.getCellX(), _chara.getCellY());
		int toCellX = tip.getCellX();
		int toCellY = tip.getCellY();

		// 移動先
		_tgX = (Map.getCharaXofCell(_chara, toCellX, toCellY) + _chara.getX()) / 2;
		_tgY = (Map.getCharaYofCell(_chara, toCellX, toCellY) + _chara.getY()) / 2;

		// 移動角度
		double deg = GameMath.atan2(_tgX - _chara.getX(), _tgY - _chara.getY());

		// フレーム数から移動速度計算
		this._spdX = GameMath.abs(_tgX - _chara.getX()) / (_moveFrame - 1);
		this._spdY = GameMath.abs(_tgY - _chara.getY()) / (_moveFrame - 1);

		// 移動速度
		_spdX = (double) (GameMath.cos((int) (deg + 0.5)) * _spdX);
		_spdY = (double) (GameMath.sin((int) (deg + 0.5)) * _spdY);

	}

	public void doAction(Chara c) throws Exception {

		_execCount++;

		// 移動
		_chara.setPosition(_chara.getX() + _spdX, _chara.getY() + _spdY);

		// 移動しすぎを矯正
		if (_spdX > 0 && _chara.getX() > _tgX) {
			// 行き過ぎ
			_chara.setPosition(_tgX, _chara.getY());
		} else if (_spdX < 0 && _chara.getX() < _tgX) {
			// 行き過ぎ
			_chara.setPosition(_tgX, _chara.getY());
		}
		if (_spdY > 0 && _chara.getY() > _tgY) {
			// 行き過ぎ
			_chara.setPosition(_chara.getX(), _tgY);
		} else if (_spdY < 0 && _chara.getY() < _tgY) {
			// 行き過ぎ
			_chara.setPosition(_chara.getX(), _tgY);
		}

		// 到着判定
		if (_execCount >= _moveFrame) {
			_execCount = 0;
			// 到着
			_isEnd = true;
		}
	}

	public boolean isDoingAction(Chara c) throws Exception {
		return !_isEnd;
	}

}
