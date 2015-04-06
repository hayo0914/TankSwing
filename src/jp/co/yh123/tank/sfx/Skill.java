package src.jp.co.yh123.tank.sfx;

import src.jp.co.yh123.tank.collabo.Attributes;
import src.jp.co.yh123.tank.collabo.ICharaClbInterface;
import src.jp.co.yh123.tank.collabo.IEffectClbInterface;
import src.jp.co.yh123.tank.collabo.IItemClbInterface;
import src.jp.co.yh123.tank.collabo.IMapObjectClbInterface;
import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;
import src.jp.co.yh123.tank.effectnew.EffectFactory;
import src.jp.co.yh123.tank.map.IActorInterface;
import src.jp.co.yh123.tank.map.MapFactory;
import src.jp.co.yh123.tank.menu.ISkillInterface;
import src.jp.co.yh123.zlibrary.util.DblPosition;
import src.jp.co.yh123.zlibrary.util.DebugUtil;
import src.jp.co.yh123.zlibrary.util.GameMath;
import src.jp.co.yh123.zlibrary.util.RandomValues;

public class Skill implements ISkillInterface {

	private RandomValues _randomizer = new RandomValues(System
			.currentTimeMillis());

	public void createExplodeEffect(ICharaClbInterface owner, int cellX,
			int cellY, int maxSpd) throws Exception {
		IBulletEffect e = EffectFactory.createBulletEffect();
		e.init((IMaptipClbInterface) MapFactory.getMap().get(cellX, cellY), 5,
				59, 1, _randomizer.getRandomInt(0, 360), (double) _randomizer
						.getRandomInt(0, maxSpd) * 0.1d);
		e.setOwner(owner);
		e.setEffectHitListener(new IBulletHitListener() {
			public void onCollision(IBulletEffect e, IMaptipClbInterface tip)
					throws Exception {
				ICharaClbInterface defender = tip.getChara();
				if (tip.isWallOrBarrier()) {
					e.changeSpd(e.getSpd() * 0.3d);
				}
				// MAP変更
				tip.damage(Attributes.ATT_PHIS);
				tip.damage(Attributes.ATT_HONO);
				// ITEM
				if (tip.hasItem()) {
					IItemClbInterface i = tip.getItem();
					i.damage(Attributes.ATT_PHIS);
					i.damage(Attributes.ATT_HONO);
				}
				// map object
				if (tip.hasMapObject()) {
					IMapObjectClbInterface o = tip.getMapObject();
					o.damage(Attributes.ATT_PHIS);
					o.damage(Attributes.ATT_HONO);
				}
				if (tip.hasChara()) {
					defender.damage(e.getOwner(), Attributes.ATT_PHIS,
							_randomizer.getRandomInt(5, 30));
				}
			}
		});
		MapFactory.getMap().addSyncEffect(e);
	}

	public void explosion(ICharaClbInterface owner) throws Exception {
		int animeId = 0;
		switch (convDeg(owner.getDirection(), 45)) {
		case 0:
			animeId = 55;
			break;
		case 45:
			animeId = 58;
			break;
		case 90:
			animeId = 57;
			break;
		case 135:
			animeId = 56;
			break;
		case 180:
			animeId = 54;
			break;
		case 225:
			animeId = 53;
			break;
		case 270:
			animeId = 52;
			break;
		case 315:
			animeId = 51;
			break;
		default:
			DebugUtil.error(String.valueOf(owner.getDirection()));
			animeId = 0;
		}
		IBulletEffect e = EffectFactory.createBulletEffect();
		e.init(owner.getCurrentMapTip(), -1, animeId, -1, owner.getDirection(),
				11);
		e.setOwner(owner);
		e.setUpdateListener(new IEffectUpdateListener() {
			public void onUpdate(IEffectClbInterface e) throws Exception {
				if (e.getUpdateCount() > 30) {
					e.setEnd();
				}
			}
		});
		e.setEffectHitListener(new IBulletHitListener() {
			public void onCollision(IBulletEffect e, IMaptipClbInterface tip)
					throws Exception {
				if (tip.isWallOrBarrier() || tip.hasChara()) {
					for (int i = 0; i < 8; i++) {
						createExplodeEffect(e.getOwner(), tip.getCellX(), tip
								.getCellY(), 30);
					}
					e.changeSpd(e.getSpd() * 0.3d);
				} else {
					if (e.getDistance() > 1) {
						for (int i = 0; i < 3; i++) {
							createExplodeEffect(e.getOwner(), tip.getCellX(),
									tip.getCellY(), 15);
						}
					}
				}
			}
		});
		MapFactory.getMap().addAsyncEffect(e);
	}

	public void fireball(ICharaClbInterface owner) throws Exception {
		IMaptipClbInterface source = owner.getCurrentMapTip();
		int degree = owner.getDirection();
		int animeId = 0;
		switch (convDeg(degree, 45)) {
		case 0:
			animeId = 55;
			break;
		case 45:
			animeId = 58;
			break;
		case 90:
			animeId = 57;
			break;
		case 135:
			animeId = 56;
			break;
		case 180:
			animeId = 54;
			break;
		case 225:
			animeId = 53;
			break;
		case 270:
			animeId = 52;
			break;
		case 315:
			animeId = 51;
			break;
		default:
			DebugUtil.error(String.valueOf(degree));
		}
		IBulletEffect e = EffectFactory.createBulletEffect();
		e.init(source, -1, animeId, -1, degree, 11);
		e.setOwner(owner);
		e.setEffectHitListener(new IBulletHitListener() {
			public void onCollision(IBulletEffect e, IMaptipClbInterface tip)
					throws Exception {
				if (tip.isWallOrBarrier() || tip.hasChara()) {
					for (int i = 0; i < 10; i++) {
						createFireEffect(e.getOwner(), tip.getCellX(), tip
								.getCellY(), 25, -1);
					}
					e.setEnd();
				} else {
					if (e.getDistance() > 1) {
						for (int i = 0; i < 3; i++) {
							createFireEffect(e.getOwner(), tip.getCellX(), tip
									.getCellY(), 15, -1);
						}
					}
				}
			}
		});
		MapFactory.getMap().addAsyncEffect(e);
	}

	private void createFireEffect(ICharaClbInterface owner, int cellX,
			int cellY, int maxSpd, int degree) throws Exception {
		int deg = degree;
		if (deg < 0) {
			deg = _randomizer.getRandomInt(0, 360);
		}
		IBulletEffect e = EffectFactory.createBulletEffect();
		e
				.init((IMaptipClbInterface) MapFactory.getMap().get(cellX,
						cellY), 4, 50, 1, deg, (double) _randomizer
						.getRandomInt(0, maxSpd) * 0.1d);
		e.setOwner(owner);
		e.setEffectHitListener(new IBulletHitListener() {
			public void onCollision(IBulletEffect e, IMaptipClbInterface tip)
					throws Exception {
				// if (tip.isWallOrBarrier() && e.getSource() != tip) {
				if (tip.isWallOrBarrier()) {
					e.changeSpd(e.getSpd() * 0.01d);
				}
				ICharaClbInterface defender = tip.getChara();
				if (tip.hasChara() && defender != e.getOwner()) {
					int damage = _randomizer.getRandomInt(5, 15);
					defender.damage(e.getOwner(), Attributes.ATT_HONO, damage);
				}
				if (!tip.isWallOrBarrier()
						&& _randomizer.getRandomInt(0, 100) > 80
						&& e.getSource() != tip) {
					createFireWallEffect(tip, e.getOwner());
				}
			}
		});
		MapFactory.getMap().addAsyncEffect(e);
	}

	private void createFireWallEffect(IMaptipClbInterface source,
			ICharaClbInterface owner) throws Exception {
		IMapEffect e = EffectFactory.createMapEffect();
		e.init(source, 6, 112, -1);
		e.setOwner(owner);
		e.setRoundInitListener(new IEffectRoundInitListener() {

			public void onRoundInit(IEffectClbInterface e) throws Exception {

				IMaptipClbInterface tip = (IMaptipClbInterface) MapFactory
						.getMap().get(e.getSource().getCellX(),
								e.getSource().getCellY());
				tip.damage(Attributes.ATT_HONO);
				if (tip.hasChara()) {
					int damage = _randomizer.getRandomInt(5, 10);
					ICharaClbInterface tg = tip.getChara();
					tg.damage(e.getOwner(), Attributes.ATT_HONO, damage);
				}
				if (tip.hasMapObject()) {
					tip.getMapObject().damage(Attributes.ATT_HONO);
				}
				if (tip.hasItem()) {
					tip.getItem().damage(Attributes.ATT_HONO);
				}
				if (_randomizer.getRandomInt(0, 100) > 95) {
					e.setEnd();
				}
				if (_randomizer.getRandomInt(0, 100) > 90) {
					// 延焼
					IMaptipClbInterface wk = (IMaptipClbInterface) MapFactory
							.getMap().getRandomNeighborTip(tip);
					if (!wk.isWallOrBarrier() && !wk.hasEffect()) {
						createFireWallEffect(wk, e.getOwner());
					}
				}
			}
		});
		MapFactory.getMap().addAsyncEffect(e);

	}

	public void iceNeedle(ICharaClbInterface owner) throws Exception {
		IBulletEffect e = EffectFactory.createBulletEffect();
		e.init(owner.getCurrentMapTip(), -1, 10, -1, owner.getDirection(), 9);
		e.setOwner(owner);
		e.setUpdateListener(new IEffectUpdateListener() {
			public void onUpdate(IEffectClbInterface e) throws Exception {
				if (e.getUpdateCount() >= 14) {
					e.setEnd();
				}
			}
		});
		e.setEffectHitListener(new IBulletHitListener() {

			public void onCollision(IBulletEffect e, IMaptipClbInterface tip)
					throws Exception {
				if (e.getSource() == tip) {
					return;
				}
				int wkDeg = e.getDegree() - 180;
				for (int i = 0; i < 3; i++) {
					wkDeg += 90;
					wkDeg = GameMath.getDegree0To359(wkDeg);
					createPreIceEffect(e.getOwner(), tip.getCellX(), tip
							.getCellY(), 20, wkDeg);
				}
				if (tip.isWallOrBarrier()) {
					e.setEnd();
				}
			}
		});
		MapFactory.getMap().addAsyncEffect(e);
	}

	private void createPreIceEffect(ICharaClbInterface owner, int cellX,
			int cellY, int maxSpd, int degree) throws Exception {
		IBulletEffect e = EffectFactory.createBulletEffect();
		e.init((IMaptipClbInterface) MapFactory.getMap().get(cellX, cellY), -1,
				10, 1, degree, maxSpd * 0.1d);
		e.setOwner(owner);
		e.setEffectHitListener(new IBulletHitListener() {
			public void onCollision(IBulletEffect e, IMaptipClbInterface tip)
					throws Exception {
				if (e.getSource() == tip) {
					return;
				}
				if (!tip.isWallOrBarrier()) {
					createIceEffect(e.getOwner(), tip.getCellX(), tip
							.getCellY());
				}
				e.setEnd();
			}
		});
		MapFactory.getMap().addAsyncEffect(e);
	}

	private void createIceEffect(ICharaClbInterface owner, int cellX, int cellY)
			throws Exception {
		IMapEffect e = EffectFactory.createMapEffect();
		e.init((IMaptipClbInterface) MapFactory.getMap().get(cellX, cellY), 3,
				48, 1);
		e.setOwner(owner);
		e.setUpdateListener(new IEffectUpdateListener() {
			public void onUpdate(IEffectClbInterface e) throws Exception {
				if (e.getUpdateCount() == 6) {
					// 数値は適当（エフェクトの真中くらい）
					IMaptipClbInterface tip = (IMaptipClbInterface) e
							.getCurrentPosition();
					ICharaClbInterface defender = tip.getChara();
					if (tip.hasChara() && !(defender == e.getOwner())) {
						defender.damage(e.getOwner(), Attributes.ATT_KORI,
								_randomizer.getRandomInt(5,
										(int) ((double) 10 * 1.2d)));
					}
				}
			}
		});
		MapFactory.getMap().addAsyncEffect(e);
		for (int i = 0; i < 2; i++) {
			createTwincleEffect(e, 1);
		}
	}

	private void createTwincleEffect(IActorInterface actor,
			int updateFrameInterval) throws Exception {
		int wkX = (int) actor.getX();
		int wkY = (int) actor.getY();
		int x = _randomizer.getRandomInt(wkX, wkX + actor.getWidth());
		int y = _randomizer.getRandomInt(wkY, wkY + actor.getHeight());
		IAnimeEffect e = EffectFactory.createAnimeEffect();
		e.init(new DblPosition(x, y), updateFrameInterval, 47, 1);
		MapFactory.getMap().addAsyncEffect(e);
	}

	public void brizzard(ICharaClbInterface owner) throws Exception {
		ITaskEffect e = EffectFactory.createTaskEffect();
		e.init((IMaptipClbInterface) MapFactory.getMap().get(owner.getCellX(),
				owner.getCellY()));
		e.setOwner(owner);
		e.setUpdateListener(new IEffectUpdateListener() {
			public void onUpdate(IEffectClbInterface e) throws Exception {
				if (e.getUpdateCount() > 25) {
					e.setEnd();
					return;
				}
				if (e.getUpdateCount() % 8 == 0) {
					for (int degree = -36; degree <= 36; degree += 4) {
						int shotDeg = degree + e.getOwner().getDirection();
						shotDeg = GameMath.getDegree0To359(shotDeg);
						createBrizzardEffect(e.getOwner(), e.getOwner()
								.getCellX(), e.getOwner().getCellY(),
								_randomizer.getRandomInt(40, 90), shotDeg);

					}
				}
			}
		});
		MapFactory.getMap().addAsyncEffect(e);
	}

	private void createBrizzardEffect(ICharaClbInterface owner, int cellX,
			int cellY, int maxSpd, int degree) throws Exception {
		IBulletEffect e = EffectFactory.createBulletEffect();
		// e.init((IMaptipClbInterface)
		// MapFactory.getMap().get(owner.getCellX(),
		// owner.getCellY()), 4, 46, 1, degree, (double) _randomizer
		// .getRandomInt(0, maxSpd) * 0.1d);
		e.init(owner.getFrontMapTip(), 4, 46, 1, degree, (double) _randomizer
				.getRandomInt(0, maxSpd) * 0.1d);
		e.setOwner(owner);
		e.setEffectHitListener(new IBulletHitListener() {
			public void onCollision(IBulletEffect e, IMaptipClbInterface tip)
					throws Exception {
				if (_randomizer.getRandomInt(0, 100) >= 90) {
					createTwincleEffect(e, 3);
				}
				if (tip.isWallOrBarrier()) {
					// if (tip.isWallOrBarrier() && e.getSource() != tip) {
					e.changeSpd(e.getSpd() * 0.01d);
				}
				ICharaClbInterface defender = tip.getChara();
				if (tip.hasChara()
						&& !(defender == e.getOwner() && e.getSource() == tip)) {
					defender.damage(e.getOwner(), Attributes.ATT_KORI,
							_randomizer.getRandomInt(8,
									(int) ((double) 15 * 1.1d)));
				}
				if (tip.hasEffect()) {
					tip.getEffect().setEnd();
				}
			}
		});
		MapFactory.getMap().addAsyncEffect(e);
	}

	/**
	 * 指定単位の角度に変換
	 * 
	 * @param degree
	 * @return
	 */
	private int convDeg(int degree, int separateDegree) {

		// 345,15->0、344,300->315,16,75->45
		double wk = (double) degree + separateDegree / 2;
		if (wk >= 360d) {
			wk -= 360d;
		}
		return (int) (wk / separateDegree) * separateDegree;

	}

	public void createDamageMessage(ICharaClbInterface defender, int damage)
			throws Exception {

		IMessageEffect e = EffectFactory.createMessageEffect();
		int color = 0xffffff;
		if (defender.isMonster()) {
			color = 0xffff00;
		}
		e.init(defender, String.valueOf(damage), color, 0x000000);
		MapFactory.getMap().addAsyncEffect(e);

	}

	public void createBloodEffect(ICharaClbInterface defender) throws Exception {
		// ダメージエフェクト（血）
		IAnimeEffect e = EffectFactory.createAnimeEffect();
		e.init(defender, 6, 70, 1);
		MapFactory.getMap().addAsyncEffect(e);
	}

	public void firebless(ICharaClbInterface owner) throws Exception {

		ITaskEffect e = EffectFactory.createTaskEffect();

		e.init(owner.getFrontMapTip());
		e.setOwner(owner);
		e.setUpdateListener(new IEffectUpdateListener() {
			private int degree = -30;
			private int diff = 2;

			public void onUpdate(IEffectClbInterface e) throws Exception {
				if (e.getUpdateCount() % 2 == 0) {
					int shotDeg = degree + e.getOwner().getDirection();
					if (shotDeg < 0) {
						shotDeg += 360;
					} else if (shotDeg >= 360) {
						shotDeg -= 360;
					}
					for (int j = 0; j < 2; j++) {
						createFireEffect(e.getOwner(), e.getCellX(), e
								.getCellY(), _randomizer.getRandomInt(40, 70),
								shotDeg);
					}
				}
				if (degree >= 30) {
					e.setEnd();
				}
				degree += diff;
			}
		});
		MapFactory.getMap().addSyncEffect(e);
	}

	Skill() {

	}
}
