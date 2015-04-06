package src.jp.co.yh123.tank.collabo;

public class CollisionChecker {

	public static boolean isHit(IEffectClbInterface effect,
			IMaptipClbInterface mapTip) throws Exception {

		return mapTip.getAnime().chkTouchingPoint(
				(int) ((effect.getX() + effect.getWidth() / 2) + 0.5),
				(int) (effect.getY() + effect.getHeight() / 2 + 0.5));
	}
}
