package src.jp.co.yh123.tank.effect;

import src.jp.co.yh123.tank.collabo.Attributes;
import src.jp.co.yh123.tank.collabo.ICharaClbInterface;
import src.jp.co.yh123.tank.collabo.IMapObjectClbInterface;
import src.jp.co.yh123.tank.collabo.IMaptipClbInterface;
import src.jp.co.yh123.tank.effect.Effect.EffectCallBack;
import src.jp.co.yh123.tank.map.IActorInterface;
import src.jp.co.yh123.tank.map.Map;
import src.jp.co.yh123.tank.map.MapFactory;
import src.jp.co.yh123.tank.resource.Res;
import src.jp.co.yh123.zlibrary.util.Csv;
import src.jp.co.yh123.zlibrary.util.DebugUtil;
import src.jp.co.yh123.zlibrary.util.GameMath;
import src.jp.co.yh123.zlibrary.util.RandomValues;

public class EffectFactory {

	private static EffectFactory _instance = null;

	public static void createInstance() {
		DebugUtil.assertIsNull(_instance);
		_instance = new EffectFactory();
	}

	public static EffectFactory getInstance() {
		DebugUtil.assertIsNotNull(_instance);
		return _instance;

	}

	// static List rangeList = new List(30);// 範囲取得用
	private RandomValues _randomizer = new RandomValues(System
			.currentTimeMillis());

	public void createExplodeEffect(int level, ICharaClbInterface owner,
			int cellX, int cellY, int maxSpd) throws Exception {
		EffectAnimeMoveHit effectMove = new EffectAnimeMoveHit(
				new EffectCallBack() {

					public void onUpdate(Effect e) throws Exception {

					}

					public void onHit(Effect e, IMaptipClbInterface tip)
							throws Exception {
						ICharaClbInterface defender = tip.getChara();
						if (tip.isWallOrBarrier()) {
							((EffectAnimeMoveHit) e).changeSpd(0.3d);
						}
						// MAP変更
						// 端っこは不可
						// ITEM消去
						if (tip.hasItem()) {
							tip.getItem().damage(Attributes.ATT_PHIS);
							tip.getItem().damage(Attributes.ATT_HONO);
						}
						// map object消去(罠の消去も含む)
						if (tip.hasMapObject()) {
							tip.getMapObject().damage(Attributes.ATT_PHIS);
							tip.getMapObject().damage(Attributes.ATT_HONO);
						}
						if (tip.hasChara()) {
							// FIXME:耐性の計算など
							int damageFrom = (int) ((double) e.getLevel() * 0.15) + 5;
							int damage = _randomizer.getRandomInt(damageFrom,
									(int) (damageFrom * 1.7d));
							defender.damage(e.getOwner(), Attributes.ATT_PHIS,
									damage);
						}
					}

					public void onEnd(Effect e) throws Exception {

					}
				});
		effectMove.set((IMaptipClbInterface) MapFactory.getMap().get(cellX,
				cellY), 5, 59, 1, _randomizer.getRandomInt(0, 360),
				(double) _randomizer.getRandomInt(0, maxSpd) * 0.1d);
		effectMove.setOwner(owner);
		effectMove.setLevel(level);
		MapFactory.getMap().addSyncEffect(effectMove);
	}

	void createTwincleEffect(IActorInterface actor, int updateFrameInterval)
			throws Exception {

		int wkX = (int) actor.getX();
		int wkY = (int) actor.getY();

		int x = _randomizer.getRandomInt(wkX, wkX + actor.getWidth());
		int y = _randomizer.getRandomInt(wkY, wkY + actor.getHeight());

		EffectAnimeFreePos e = new EffectAnimeFreePos(new EffectCallBack() {
			public void onUpdate(Effect e) throws Exception {
			}

			public void onEnd(Effect e) throws Exception {
			}

			public void onHit(Effect e, IMaptipClbInterface tip)
					throws Exception {

			}
		});
		e.set(x, y, updateFrameInterval, 47, 1);
		MapFactory.getMap().addAsyncEffect(e);

	}

	private void createFireEffect(int level, ICharaClbInterface owner,
			int cellX, int cellY, int maxSpd, int degree) throws Exception {
		EffectAnimeMoveHit effectMove = new EffectAnimeMoveHit(
				new EffectCallBack() {

					public void onUpdate(Effect e) throws Exception {

					}

					public void onHit(Effect e, IMaptipClbInterface tip)
							throws Exception {
						EffectAnimeMoveHit eam = (EffectAnimeMoveHit) e;
						if (tip.isWallOrBarrier() && eam.getSource() != tip) {
							eam.changeSpd(0.15d);
						}
						ICharaClbInterface defender = tip.getChara();
						if (tip.hasChara()
								&& !(defender == e.getOwner() && eam
										.getSource() == tip)) {
							int damageFrom = (int) ((double) e.getLevel() * 0.05) + 5;
							int damage = _randomizer.getRandomInt(damageFrom,
									(int) ((double) damageFrom * 1.2d));
							defender.damage(e.getOwner(), Attributes.ATT_HONO,
									damage);
						}
						if (!tip.isWallOrBarrier()
								&& _randomizer.getRandomInt(0, 100) > 80
								&& eam.getSource() != tip) {
							createFireWallEffect(e.getLevel(), tip, e
									.getOwner());
						}
					}

					public void onEnd(Effect e) throws Exception {

					}
				});
		int deg = degree;
		if (deg < 0) {
			deg = _randomizer.getRandomInt(0, 360);
		}
		effectMove.set((IMaptipClbInterface) MapFactory.getMap().get(cellX,
				cellY), 4, 50, 1, deg, (double) _randomizer.getRandomInt(0,
				maxSpd) * 0.1d);
		effectMove.setOwner(owner);
		effectMove.setLevel(level);
		MapFactory.getMap().addSyncEffect(effectMove);
	}

	private void createBrizzardEffect(int level, ICharaClbInterface owner,
			int cellX, int cellY, int maxSpd, int degree) throws Exception {
		EffectAnimeMoveHit effectMove = new EffectAnimeMoveHit(
				new EffectCallBack() {

					public void onUpdate(Effect e) throws Exception {

					}

					public void onHit(Effect e, IMaptipClbInterface tip)
							throws Exception {
						createTwincleEffect(e, 3);
						EffectAnimeMoveHit eam = (EffectAnimeMoveHit) e;
						if (tip.isWallOrBarrier() && eam.getSource() != tip) {
							eam.changeSpd(0.15d);
						}
						ICharaClbInterface defender = tip.getChara();
						if (tip.hasChara()
								&& !(defender == e.getOwner() && eam
										.getSource() == tip)) {
							int damageFrom = (int) ((double) e.getLevel() * 0.07) + 7;
							int damage = _randomizer.getRandomInt(damageFrom,
									(int) ((double) damageFrom * 1.1d));
							defender.damage(e.getOwner(), Attributes.ATT_KORI,
									damage);
						}
						Effect mapEff = (Effect) tip.getEffect();
						if (mapEff != null) {
							mapEff.setEnd();
						}
					}

					public void onEnd(Effect e) throws Exception {

					}
				});
		int deg = degree;
		if (deg < 0) {
			deg = _randomizer.getRandomInt(0, 360);
		}
		effectMove.set((IMaptipClbInterface) MapFactory.getMap().get(cellX,
				cellY), 4, 46, 1, deg, (double) _randomizer.getRandomInt(0,
				maxSpd) * 0.1d);
		effectMove.setOwner(owner);
		effectMove.setLevel(level);
		MapFactory.getMap().addSyncEffect(effectMove);
	}

	private void createPreIceEffect(int level, ICharaClbInterface owner,
			int cellX, int cellY, int maxSpd, int degree) throws Exception {
		EffectAnimeMoveHit effectMove = new EffectAnimeMoveHit(
				new EffectCallBack() {

					public void onUpdate(Effect e) throws Exception {

					}

					public void onHit(Effect e, IMaptipClbInterface tip)
							throws Exception {
						EffectAnimeMoveHit eam = (EffectAnimeMoveHit) e;
						if (eam.getSource() == tip) {
							return;
						}
						createIceEffect(e.getLevel(), e.getOwner(), tip
								.getCellX(), tip.getCellY());
						e.setEnd();
					}

					public void onEnd(Effect e) throws Exception {

					}
				});
		effectMove.set((IMaptipClbInterface) MapFactory.getMap().get(cellX,
				cellY), -1, 10, 1, degree, maxSpd * 0.1d);
		effectMove.setOwner(owner);
		effectMove.setLevel(level);
		MapFactory.getMap().addSyncEffect(effectMove);
	}

	private void createIceEffect(int level, ICharaClbInterface owner,
			int cellX, int cellY) throws Exception {
		EffectAnimeOnTip effect = new EffectAnimeOnTip(new EffectCallBack() {

			int execCnt = 0;

			public void onUpdate(Effect e) throws Exception {
				execCnt++;
				if (execCnt == 6) {
					// 数値は適当（エフェクトの真中くらい）
					IMaptipClbInterface tip = (IMaptipClbInterface) e
							.getPosition();
					ICharaClbInterface defender = tip.getChara();
					if (tip.hasChara() && !(defender == e.getOwner())) {
						int damageFrom = (int) ((double) e.getLevel() * 0.5) + 5;
						int damage = _randomizer.getRandomInt(damageFrom,
								(int) ((double) damageFrom * 1.2d));
						defender.damage(e.getOwner(), Attributes.ATT_KORI,
								damage);
					}
				}
			}

			public void onHit(Effect e, IMaptipClbInterface tip)
					throws Exception {
			}

			public void onEnd(Effect e) throws Exception {
			}

		});
		effect.set((IMaptipClbInterface) MapFactory.getMap().get(cellX, cellY),
				3, 48, 1);
		effect.setOwner(owner);
		effect.setLevel(level);
		MapFactory.getMap().addSyncEffect(effect);
		createAsyncAnimeEffectFore(effect, 4, 49, 1);
		for (int i = 0; i < 2; i++) {
			createTwincleEffect(effect, 1);
		}
	}

	/**
	 * 地を這うタイプの電撃
	 * 
	 * @param owner
	 * @param cellX
	 * @param cellY
	 * @param maxSpd
	 * @throws Exception
	 */
	void createLightningEffect(int level, ICharaClbInterface owner, int cellX,
			int cellY, int maxSpd, int initDegree) throws Exception {
		EffectAnimeMoveHit effectMove = new EffectAnimeMoveHit(
				new EffectCallBack() {
					int execCount = 0;
					int maxCount = _randomizer.getRandomInt(10, 50);
					int wk = _randomizer.getRandomInt(3, 6);

					public void onUpdate(Effect e) throws Exception {
						execCount++;
						if (execCount >= maxCount) {
							e.setEnd();
						}
						if (execCount % wk == 0) {
							createAsyncAnimeEffectFore(e, 2, 60, 1);
						}
					}

					public void onHit(Effect e, IMaptipClbInterface tip)
							throws Exception {
						EffectAnimeMoveHit eam = (EffectAnimeMoveHit) e;

						if (tip.isWallOrBarrier()) {
							eam.ajustWallLimit(tip);
							eam.changeDirection((_randomizer.getRandomInt(0,
									360) / 45) * 45);
						} else if (execCount % 2 == 0) {
							eam.changeDirection((_randomizer.getRandomInt(0,
									360) / 45) * 45);
						}

						ICharaClbInterface defender = tip.getChara();
						if (tip.hasChara()) {
							int damageFrom = (int) ((double) e.getLevel() * 0.1) + 5;
							int damage = _randomizer.getRandomInt(damageFrom,
									(int) (damageFrom * 1.1d));
							defender.damage(e.getOwner(), Attributes.ATT_KAMI,
									damage);
							// 電気HITエフェクト
							createSyncAnimeEffectFore(defender, 4, 61, 1);
						}
					}

					public void onEnd(Effect e) throws Exception {

					}
				});
		effectMove.set((IMaptipClbInterface) MapFactory.getMap().get(cellX,
				cellY), 4, 111, -1,
				(_randomizer.getRandomInt(0, 360)) / 45 * 45, 2d);
		effectMove.setOwner(owner);
		effectMove.setLevel(level);
		for (int i = _randomizer.getRandomInt(0, 5); i >= 0; i--) {
			effectMove.getAnime().update();
		}
		MapFactory.getMap().addSyncEffect(effectMove);
	}

	public void createTrapShotgunEffect(IMaptipClbInterface source,
			IMapObjectClbInterface trap, int degree) throws Exception {
		// 銃弾エフェクト
		EffectAnimeMoveHit effect = new EffectAnimeMoveHit(
				new EffectCallBack() {

					public void onUpdate(Effect e) throws Exception {

					}

					public void onHit(Effect e, IMaptipClbInterface tip)
							throws Exception {
						if (tip == e.getPosition()) {
							return;
						}
						ICharaClbInterface defender = tip.getChara();
						EffectAnimeMoveHit eam = (EffectAnimeMoveHit) e;
						// int level = ((IMapObjectClbInterface)
						// e.getPosition())._level;
						int level = 1;
						if (defender != null) {
							// DMG処理
							// 命中率
							// Lv補正
							int shotPoint = (int) ((level + 1) * 1.3d);
							// ac
							// int ac = defender.getAC();
							int ac = 0;
							// 率計算
							double rate = 0;
							if (shotPoint + ac == 0) {
								rate = 0.95d;
							} else {
								rate = (double) shotPoint
										/ (double) (shotPoint + ac);
							}

							// 四捨五入
							rate = (double) ((int) ((rate + 0.05) * 100d));
							// 距離補正
							int range = 3;// XXX
							double distFix = (double) range
									/ (double) eam.getDistance();
							if (distFix > 1.0) {
								distFix = 1;
							}
							rate *= distFix;
							// 上限下限チェック
							if (rate > 95) {
								rate = 95;
							} else if (rate < 5) {
								rate = 5;
							}
							// 命中判定
							int roll = _randomizer.getRandomInt(0, 101);
							if (rate < roll) {
								// 外した
								return;
							}
							int damage = _randomizer.getRandomInt(level,
									(int) ((double) level * 1.5));

							// 距離補正
							// 威力劣化率 0で0.5
							double rekka = (double) range
									/ (double) eam.getDistance();
							if (rekka < 1d) {
								// 射程を超えている場合、加速度的に減衰させる
								rekka /= 2;
							} else {
								rekka = 1;
							}
							damage *= rekka;

							// dmg
							defender.damage(e.getOwner(), Attributes.ATT_BLLT,
									damage);
							e.setEnd();
						} else if (tip.isWallOrBarrier()) {
							eam.ajustWallLimit(tip);
							createAsyncAnimeEffectFore(eam, 2, 66, 1);
							eam.setEnd();
						}
					}

					public void onEnd(Effect e) throws Exception {

					}
				});
		effect.setOwner(null);
		effect.setPosition(trap);
		double speed = 10;
		int animeId = 69;
		effect.set(source, -1, animeId, -1, degree, speed);
		MapFactory.getMap().addSyncEffect(effect);
	}

	public void createTrapPoisonGusEffect(IMaptipClbInterface source,
			IMapObjectClbInterface trap) throws Exception {
		// 毒ガスエフェクト
		EffectAnimeOnTip effect = new EffectAnimeOnTip(new EffectCallBack() {
			// long roundCnt = getModel().roundCount + 1;
			// long startRound = getModel().roundCount + 1;

			public void onUpdate(Effect e) throws Exception {
				// if (roundCnt != getModel().roundCount) {
				// roundCnt = getModel().roundCount;

				Map map = MapFactory.getMap();
				IMaptipClbInterface tip = (IMaptipClbInterface) map.get(e
						.getPosition().getCellX(), e.getPosition().getCellY());
				if (tip.getChara() != null) {
					// if (tip.getChara().isPlayer()) {
					// getGame().selfMessage(
					// tip.getChara().getNameForMessage()
					// + "は毒ガスを吸い込んだ!");
					// }
					tip.getChara().damage(e.getOwner(),
							Attributes.ATT_POIS,// 毒
							_randomizer.getRandomInt(e.getLevel() / 2,
									(int) ((double) e.getLevel() * 2)));
					// poison化
				}
				// if (getModel().roundCount - startRound >= e.getLevel()) {
				// e.setEnd();
				// }
				if (_randomizer.getRandomInt(0, 100) > 90) {
					e.setEnd();
				}
				// }
			}

			public void onHit(Effect e, IMaptipClbInterface tip)
					throws Exception {

			}

			public void onEnd(Effect e) throws Exception {

			}
		});
		effect.setOwner(null);
		int animeId = 110;
		effect.set(source, 14, animeId, -1);
		// effect.setLevel(trap._level);
		MapFactory.getMap().addAsyncEffect(effect);

	}

	void createFireWallEffect(int level, IMaptipClbInterface source,
			ICharaClbInterface owner) throws Exception {
		// 火壁エフェクト
		EffectAnimeOnTip effect = new EffectAnimeOnTip(new EffectCallBack() {
			// long roundCnt = getModel().roundCount + 1;
			// long endRound = getModel().roundCount
			// + _randomizer.getRandomInt(15, 45);

			public void onUpdate(Effect e) throws Exception {
				// if (roundCnt != getModel().roundCount) {
				// roundCnt = getModel().roundCount;

				Map map = MapFactory.getMap();
				IMaptipClbInterface tip = (IMaptipClbInterface) map.get(e
						.getPosition().getCellX(), e.getPosition().getCellY());
				if (tip.hasChara()) {
					// if (tip.getChara().isPlayer()) {
					// getGame().selfMessage(
					// tip.getChara().getNameForMessage()
					// + "の体を炎が包み込んだ！");
					// }
					int damageFrom = (int) ((double) e.getLevel() * 0.08) + 3;
					int damage = _randomizer.getRandomInt(damageFrom,
							(int) (damageFrom * 1.2d));
					ICharaClbInterface tg = tip.getChara();
					tg.damage(e.getOwner(), Attributes.ATT_HONO, damage);
					// TODO:アイテムを焼く
				}
				// if (endRound <= roundCnt) {
				// e.setEnd();
				// }
				if (_randomizer.getRandomInt(0, 100) > 95) {
					e.setEnd();
				}

				// }
			}

			public void onHit(Effect e, IMaptipClbInterface tip)
					throws Exception {

			}

			public void onEnd(Effect e) throws Exception {

			}
		});
		effect.setOwner(owner);
		int animeId = 112;
		effect.set(source, 6, animeId, -1);
		effect.setLevel(level);
		MapFactory.getMap().addAsyncEffect(effect);

	}

	public void createBulletEffect(IMaptipClbInterface source,
			ICharaClbInterface owner, int bulletEquipId, boolean isSync,
			int degree) throws Exception {
		// 銃弾エフェクト
		EffectAnimeMoveHit effect = new EffectAnimeMoveHit(
				new EffectCallBack() {

					public void onUpdate(Effect e) throws Exception {
					}

					public void onHit(Effect e, IMaptipClbInterface tip)
							throws Exception {

						if (tip == e.getPosition()) {
							return;
						}
						ICharaClbInterface defender = tip.getChara();
						EffectAnimeMoveHit eam = (EffectAnimeMoveHit) e;
						if (defender != null && defender != e.getOwner()) {

							// DMG処理
							// 命中率
							// int shotPoint = e.getOwner().getShootPoint();
							int shotPoint = 50;
							// ac
							// int ac = defender.getAC();
							int ac = 0;
							// 率計算
							double rate = 0;
							if (shotPoint + ac == 0) {
								rate = 0.95d;
							} else {
								rate = (double) shotPoint
										/ (double) (shotPoint + ac);
							}

							// 四捨五入
							rate = (double) ((int) ((rate + 0.05) * 100d));
							// Item補正
							// if (e.getOwner().get_equip_shooter() != null) {
							// rate += e.getOwner().get_equip_shooter()
							// .get_equipHitAssist();
							// }
							// 距離補正
							// int range = e.getOwner().get_equip_shooter()
							// .getRange();
							// double distFix = (double) range
							// / (double) eam.getDistance();
							// if (distFix > 1.0) {
							// distFix = 1;
							// }
							// rate *= distFix;
							// 上限下限チェック
							if (rate > 95) {
								rate = 95;
							} else if (rate < 5) {
								rate = 5;
							}
							// 命中判定
							int roll = _randomizer.getRandomInt(0, 101);
							if (rate < roll) {
								// 外した
								return;
							}

							// int damage =
							// _randomizer.getRandomInt(e.getOwner()
							// .get_equip_shooter().get_equipDmgFrom(), e
							// .getOwner().get_equip_shooter()
							// .get_equipDmgTo());
							int damage = _randomizer.getRandomInt(0, 20);
							// dex補正
							// damage += (int) ((double) e.getOwner().get_dex()
							// * 0.2d);

							// 距離補正
							// 威力劣化率 0で0.5
							// double rekka = (double) range
							// / (double) eam.getDistance();
							// if (rekka < 1d) {
							// // 射程を超えている場合、加速度的に減衰させる
							// rekka /= 2;
							// } else {
							// rekka = 1;
							// }
							// damage *= rekka;

							// クリティカル判定
							boolean cret = false;
							// if (e.getOwner().get_equip_shooter()
							// .get_equipCriticalRate() >= _randomizer
							// .getRandomInt(0, 101)) {
							// // クリティカル
							// if (e.getOwner().isPlayer()
							// || defender.isPlayer()) {
							// getGame().selfMessage("クリティカル！");
							// }
							// cret = true;
							// damage = (int) ((double) damage * 1.5d);
							// }

							// dmg
							if (cret) {
								defender.damage(e.getOwner(),
										Attributes.ATT_BLLT, damage);// TODO:属性は武器でない場合、モンスターの身体、マスタから取得
							} else {
								defender.damage(e.getOwner(),
										Attributes.ATT_BLLT, damage);
							}
							e.setEnd();
						} else if (tip.isWallOrBarrier()) {
							eam.ajustWallLimit(tip);
							createAsyncAnimeEffectFore(eam, 2, 66, 1);
							eam.setEnd();
						}
					}

					public void onEnd(Effect e) throws Exception {
					}
				});
		effect.setOwner(owner);
		double speed = 11;
		int animeId = 0;
		switch (bulletEquipId) {
		case 3:// 7.62mm弾
			switch (translateDegree(degree)) {
			case 0:
				animeId = 87;
				break;
			case 45:
				animeId = 90;
				break;
			case 90:
				animeId = 89;
				break;
			case 135:
				animeId = 88;
				break;
			case 180:
				animeId = 85;
				break;
			case 225:
				animeId = 82;
				break;
			case 270:
				animeId = 83;
				break;
			case 315:
				animeId = 84;
				break;
			default:
				DebugUtil.error();
			}
			break;
		case 6:// 5.56mm弾
			switch (translateDegree(degree)) {
			case 0:
				animeId = 76;
				break;
			case 45:
				animeId = 79;
				break;
			case 90:
				animeId = 78;
				break;
			case 135:
				animeId = 77;
				break;
			case 180:
				animeId = 74;
				break;
			case 225:
				animeId = 71;
				break;
			case 270:
				animeId = 72;
				break;
			case 315:
				animeId = 73;
				break;
			default:
				DebugUtil.error();
			}
			break;
		case 10:// ショットガンの弾
			animeId = 69;
			break;
		default:
			// error
			DebugUtil.assertFalse(true);
			break;
		}
		effect.set(source, -1, animeId, -1, degree, speed);

		if (isSync) {
			MapFactory.getMap().addSyncEffect(effect);
		} else {
			MapFactory.getMap().addAsyncEffect(effect);
		}
	}

	public void createRegenerateEffect(ICharaClbInterface c, int num)
			throws Exception {
		// 回復数字エフェクト
		int randX = _randomizer.getRandomInt(0, 10);
		if (_randomizer.getRandomInt(0, 2) == 1) {
			randX *= -1;
		}
		int randY = _randomizer.getRandomInt(0, 10);
		if (_randomizer.getRandomInt(0, 2) == 1) {
			randY *= -1;
		}
		EffectMessage effect = new EffectMessage(new EffectCallBack() {

			public void onUpdate(Effect e) throws Exception {
			}

			public void onHit(Effect e, IMaptipClbInterface tip)
					throws Exception {
			}

			public void onEnd(Effect e) throws Exception {
			}
		});
		double x = c.getX() + (c.getWidth() / 2);
		effect.set(c, String.valueOf(num), x + randX, c.getY()
				+ (c.getHeight() / 2) - 12 + randY, 0x3399ff, 0x000000);
		MapFactory.getMap().addAsyncEffect(effect);
	}

	public void createMessageEffect(IActorInterface target, String message)
			throws Exception {
		// 文字エフェクト
		int randX = _randomizer.getRandomInt(0, 10);
		if (_randomizer.getRandomInt(0, 2) == 1) {
			randX *= -1;
		}
		int randY = _randomizer.getRandomInt(0, 10);
		if (_randomizer.getRandomInt(0, 2) == 1) {
			randY *= -1;
		}
		EffectMessage effect = new EffectMessage(new EffectCallBack() {

			public void onUpdate(Effect e) throws Exception {
			}

			public void onHit(Effect e, IMaptipClbInterface tip)
					throws Exception {
			}

			public void onEnd(Effect e) throws Exception {
			}
		});
		double x = target.getX() + (target.getWidth() / 2);
		effect.set(target, message, x + randX, target.getY()
				+ (target.getHeight() / 2) - 12 + randY, 0xffffff, 0x000000);
		MapFactory.getMap().addAsyncEffect(effect);
	}

	public void createExpEffect(ICharaClbInterface c, int num) throws Exception {

		// Exp数字エフェクト
		StringBuffer sb = new StringBuffer();
		sb.append("+");
		sb.append(num);
		sb.append("EXP");
		EffectExp effect = new EffectExp(new EffectCallBack() {

			public void onUpdate(Effect e) throws Exception {
			}

			public void onHit(Effect e, IMaptipClbInterface tip)
					throws Exception {
			}

			public void onEnd(Effect e) throws Exception {
			}
		});
		effect.set(c, sb.toString(), c.getX() + (c.getWidth() / 2), c.getY()
				+ (c.getHeight() / 2) - 12);
		MapFactory.getMap().addAsyncEffect(effect);
	}

	public void createDamageEffect(ICharaClbInterface defender, String msg)
			throws Exception {
		// ダメージ数字エフェクト
		EffectMessage effect = new EffectMessage(new EffectCallBack() {

			public void onUpdate(Effect e) throws Exception {
			}

			public void onHit(Effect e, IMaptipClbInterface tip)
					throws Exception {
			}

			public void onEnd(Effect e) throws Exception {
			}
		});

		int randX = _randomizer.getRandomInt(0, 10);
		if (_randomizer.getRandomInt(0, 2) == 1) {
			randX *= -1;
		}
		int randY = _randomizer.getRandomInt(0, 10);
		if (_randomizer.getRandomInt(0, 2) == 1) {
			randY *= -1;
		}
		double x = defender.getX() + (defender.getWidth() / 2) + randX;
		int color = 0xff0000;
		// if (defender.isMonster()) {
		// color = 0xffff00;
		// }//FIXME:
		effect.set(defender, msg, x, defender.getY()
				+ (defender.getHeight() / 2) - 12 + randY, color, 0x000000);
		MapFactory.getMap().addAsyncEffect(effect);
	}

	public void createBloodEffect(ICharaClbInterface defender) throws Exception {
		// ダメージエフェクト（血）
		createAsyncAnimeEffectFore(defender, 6, 70, 1);
	}

	/**
	 * 45度単位の角度に変換
	 * 
	 * @param degree
	 * @return
	 */
	private int translateDegree(int degree) {

		// 345,15->0、344,300->315,16,75->45
		double wk = (double) degree + 22.5d;
		if (wk >= 360d) {
			wk -= 360d;
		}
		return (int) (wk / 45d) * 45;

	}

	/**
	 * スキル実行
	 * 
	 * @throws Exception
	 */
	public void execSkill(ICharaClbInterface c, int skillId) throws Exception {

		// int level = (int) ((double) c.get_level() * 0.3);
		// level += (int) ((double) c.get_skillLvSpell() * 0.5);
		// level += (int) ((double) c.get_int() * 0.7);

		int level = 5;
		Csv csv = Res.get().csvSpell;
		// getGame().seeMessage(
		// c.getNameForMessage() + "は" + csv.getString(skillId, 1)
		// + "を放った！");

		switch (skillId) {
		case 0:
			// エクスプロージョン
			// FIXME:
			explosion(level, c, c.getDirection(),
					(IMaptipClbInterface) MapFactory.getMap().get(c.getCellX(),
							c.getCellY()), true);
			break;
		case 1:
			// thunder bolt
			lightningBall(level, c, c.getDirection(),
					(IMaptipClbInterface) MapFactory.getMap().get(c.getCellX(),
							c.getCellY()), true);
			break;
		case 2:
			// ice ball
			// iceStorm(level, c, c.getDirection(), c.getCurrentMaptip(), true);
			brizzard(level, c, (IMaptipClbInterface) MapFactory.getMap().get(
					c.getCellX(), c.getCellY()));
			break;
		case 3:
			// ファイアボール
			fireball(level, c, c.getDirection(),
					(IMaptipClbInterface) MapFactory.getMap().get(c.getCellX(),
							c.getCellY()), true);
			break;
		case 4:
			// ファイアブレス
			firebless(level, c, (IMaptipClbInterface) MapFactory.getMap().get(
					c.getCellX(), c.getCellY()));
			break;
		default:
			break;
		}
	}

	private void explosion(int level, ICharaClbInterface owner, int degree,
			IMaptipClbInterface source, boolean isSync) throws Exception {

		// エフェクト
		EffectAnimeMoveHit effect = new EffectAnimeMoveHit(
				new EffectCallBack() {

					private int execCnt = 0;
					private int hitCount = 0;

					public void onUpdate(Effect e) throws Exception {
						execCnt++;
						if (execCnt > 30) {
							e.setEnd();
							return;
						}
					}

					public void onHit(Effect e, IMaptipClbInterface tip)
							throws Exception {
						if (tip.isWallOrBarrier() || tip.hasChara()) {
							for (int i = 0; i < 8; i++) {
								createExplodeEffect(e.getLevel(), e.getOwner(),
										tip.getCellX(), tip.getCellY(), 30);
							}
							((EffectAnimeMoveHit) e).changeSpd(0.3d);
						} else {
							if (((EffectAnimeMoveHit) e).getDistance() > 1) {
								for (int i = 0; i < 3; i++) {
									createExplodeEffect(e.getLevel(), e
											.getOwner(), tip.getCellX(), tip
											.getCellY(), 15);
								}
							}
						}
					}

					public void onEnd(Effect e) throws Exception {
					}
				});
		effect.setOwner(owner);
		effect.setLevel(level);
		double speed = 11;
		int animeId = 0;

		switch (translateDegree(degree)) {
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
			DebugUtil.error("存在しない方向：WFTHROW_ITEM:" + String.valueOf(degree));
		}

		effect.set(source, -1, animeId, -1, degree, speed);

		if (isSync) {
			MapFactory.getMap().addSyncEffect(effect);
		} else {
			MapFactory.getMap().addAsyncEffect(effect);
		}
	}

	private void iceStorm(int level, ICharaClbInterface owner, int degree,
			IMaptipClbInterface source, boolean isSync) throws Exception {

		// エフェクト
		EffectAnimeMoveHit effect = new EffectAnimeMoveHit(
				new EffectCallBack() {

					private int execCnt = 0;

					public void onUpdate(Effect e) throws Exception {
						execCnt++;
						if (execCnt >= 14) {
							e.setEnd();
						}
					}

					public void onHit(Effect e, IMaptipClbInterface tip)
							throws Exception {
						EffectAnimeMoveHit eam = (EffectAnimeMoveHit) e;
						if (eam.getSource() == tip) {
							return;
						}
						int wkDeg = eam.getDegree() - 180;
						for (int i = 0; i < 3; i++) {
							wkDeg += 90;
							wkDeg = GameMath.getDegree0To359(wkDeg);
							createPreIceEffect(e.getLevel(), e.getOwner(), tip
									.getCellX(), tip.getCellY(), 20, wkDeg);
						}
						if (tip.isWallOrBarrier()) {
							e.setEnd();
						}
					}

					public void onEnd(Effect e) throws Exception {
					}
				});
		effect.setOwner(owner);
		effect.setLevel(level);
		double speed = 9;
		int animeId = 10;
		effect.set(source, -1, animeId, -1, degree, speed);
		effect.setPosition(effect.getX() - effect.getSpdX(), effect.getY()
				- effect.getSpdY());

		if (isSync) {
			MapFactory.getMap().addSyncEffect(effect);
		} else {
			MapFactory.getMap().addAsyncEffect(effect);
		}
	}

	private void lightningBall(int level, ICharaClbInterface owner, int degree,
			IMaptipClbInterface source, boolean isSync) throws Exception {

		// エフェクト
		EffectAnimeMoveHit effect = new EffectAnimeMoveHit(
				new EffectCallBack() {

					private int execCnt = 0;

					public void onUpdate(Effect e) throws Exception {
						execCnt++;
						if (execCnt % 2 == 0) {
							createAsyncAnimeEffectFore(e, 1, 60, 1);
						}
					}

					public void onHit(Effect e, IMaptipClbInterface tip)
							throws Exception {
						EffectAnimeMoveHit eam = (EffectAnimeMoveHit) e;
						if (tip.isWallOrBarrier()
								|| (tip.hasChara() && tip.getChara() != e
										.getOwner())) {
							for (int i = 0; i < 18; i++) {
								createLightningEffect(e.getLevel(), e
										.getOwner(), tip.getCellX(), tip
										.getCellY(), 15, eam.getDegree());
							}
							e.setEnd();
						}

					}

					public void onEnd(Effect e) throws Exception {
					}
				});
		effect.setOwner(owner);
		effect.setLevel(level);
		double speed = 11;
		int animeId = 62;
		effect.set(source, -1, animeId, -1, degree, speed);
		effect.setPosition(effect.getX() - effect.getSpdX(), effect.getY()
				- effect.getSpdY());

		if (isSync) {
			MapFactory.getMap().addSyncEffect(effect);
		} else {
			MapFactory.getMap().addAsyncEffect(effect);
		}
	}

	private void fireball(int level, ICharaClbInterface owner, int degree,
			IMaptipClbInterface source, boolean isSync) throws Exception {

		// エフェクト
		EffectAnimeMoveHit effect = new EffectAnimeMoveHit(
				new EffectCallBack() {

					private int execCnt = 0;

					public void onUpdate(Effect e) throws Exception {
						execCnt++;
					}

					public void onHit(Effect e, IMaptipClbInterface tip)
							throws Exception {
						if (tip.isWallOrBarrier() || tip.hasChara()) {
							EffectAnimeMoveHit eam = (EffectAnimeMoveHit) e;
							if (tip.isWallOrBarrier()) {
								eam.ajustWallLimit(tip);
							}
							IMaptipClbInterface currTip = (IMaptipClbInterface) MapFactory
									.getMap().get(eam.getCellX(),
											eam.getCellY());
							for (int i = 0; i < 10; i++) {
								createFireEffect(e.getLevel(), e.getOwner(),
										currTip.getCellX(), currTip.getCellY(),
										25, -1);
							}
							e.setEnd();
						} else {
							if (((EffectAnimeMoveHit) e).getDistance() > 1) {
								for (int i = 0; i < 3; i++) {
									createFireEffect(e.getLevel(),
											e.getOwner(), tip.getCellX(), tip
													.getCellY(), 15, -1);
								}
							}
						}
					}

					public void onEnd(Effect e) throws Exception {
					}
				});
		effect.setOwner(owner);
		effect.setLevel(level);
		double speed = 11;
		int animeId = 0;

		switch (translateDegree(degree)) {
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
			DebugUtil.error("存在しない方向：WFTHROW_ITEM:" + String.valueOf(degree));
		}

		effect.set(source, -1, animeId, -1, degree, speed);

		if (isSync) {
			MapFactory.getMap().addSyncEffect(effect);
		} else {
			MapFactory.getMap().addAsyncEffect(effect);
		}
	}

	private void firebless(int level, ICharaClbInterface owner,
			IMaptipClbInterface source) throws Exception {

		// エフェクト
		EffectNothing effect = new EffectNothing(new EffectCallBack() {

			private int execCnt = 0;
			private int degree = -30;
			private int diff = 2;

			public void onUpdate(Effect e) throws Exception {
				execCnt++;
				if (execCnt % 2 == 0) {
					// if (degree > 30 || degree < -30) {
					// diff += -1;
					// }
					int shotDeg = degree + e.getOwner().getDirection();
					if (shotDeg < 0) {
						shotDeg += 360;
					} else if (shotDeg >= 360) {
						shotDeg -= 360;
					}
					for (int j = 0; j < 2; j++) {
						createFireEffect(e.getLevel(), e.getOwner(), e
								.getOwner().getCellX(),
								e.getOwner().getCellY(), _randomizer
										.getRandomInt(40, 70), shotDeg);
					}
				}
				if (degree >= 30) {
					e.setEnd();
				}
				degree += diff;
			}

			public void onHit(Effect e, IMaptipClbInterface tip)
					throws Exception {
				if (tip.isWallOrBarrier() || tip.hasChara()) {
					EffectAnimeMoveHit eam = (EffectAnimeMoveHit) e;
					if (tip.isWallOrBarrier()) {
						eam.ajustWallLimit(tip);
					}
					IMaptipClbInterface currTip = (IMaptipClbInterface) MapFactory
							.getMap().get(eam.getCellX(), eam.getCellY());
					for (int i = 0; i < 10; i++) {
						createFireEffect(e.getLevel(), e.getOwner(), currTip
								.getCellX(), currTip.getCellY(), 25, -1);
					}
					e.setEnd();
				} else {
					if (((EffectAnimeMoveHit) e).getDistance() > 1) {
						for (int i = 0; i < 3; i++) {
							createFireEffect(e.getLevel(), e.getOwner(), tip
									.getCellX(), tip.getCellY(), 15, -1);
						}
					}
				}
			}

			public void onEnd(Effect e) throws Exception {
			}
		});

		effect.setOwner(owner);
		effect.setLevel(level);
		effect.set(source);
		MapFactory.getMap().addSyncEffect(effect);
	}

	private void brizzard(int level, ICharaClbInterface owner,
			IMaptipClbInterface source) throws Exception {

		// エフェクト
		EffectNothing effect = new EffectNothing(new EffectCallBack() {

			private int execCnt = 0;

			public void onUpdate(Effect e) throws Exception {
				execCnt++;
				if (execCnt > 25) {
					e.setEnd();
					return;
				}
				if (execCnt % 8 == 0) {
					for (int degree = -36; degree <= 36; degree += 4) {
						int shotDeg = degree + e.getOwner().getDirection();
						shotDeg = GameMath.getDegree0To359(shotDeg);
						createBrizzardEffect(e.getLevel(), e.getOwner(), e
								.getOwner().getCellX(),
								e.getOwner().getCellY(), _randomizer
										.getRandomInt(40, 90), shotDeg);

					}
				}
			}

			public void onHit(Effect e, IMaptipClbInterface tip)
					throws Exception {
			}

			public void onEnd(Effect e) throws Exception {
			}
		});

		effect.setOwner(owner);
		effect.setLevel(level);
		effect.set(source);
		MapFactory.getMap().addSyncEffect(effect);
	}

	void createSyncAnimeEffectBack(IActorInterface tg, int frameChangeInterval,
			int animeId, int maxLoopCount) throws Exception {
		EffectAnime effect = new EffectAnime(new EffectCallBack() {

			public void onUpdate(Effect e) throws Exception {
			}

			public void onHit(Effect e, IMaptipClbInterface tip)
					throws Exception {
			}

			public void onEnd(Effect e) throws Exception {
			}
		});
		effect.set(tg, frameChangeInterval, animeId, maxLoopCount);
		MapFactory.getMap().addSyncEffect(effect);
	}

	void createAsyncAnimeEffectBack(IActorInterface tg,
			int frameChangeInterval, int animeId, int maxLoopCount)
			throws Exception {
		EffectAnime effect = new EffectAnime(new EffectCallBack() {

			public void onUpdate(Effect e) throws Exception {
			}

			public void onHit(Effect e, IMaptipClbInterface tip)
					throws Exception {
			}

			public void onEnd(Effect e) throws Exception {
			}
		});
		effect.set(tg, frameChangeInterval, animeId, maxLoopCount);
		MapFactory.getMap().addAsyncEffect(effect);
	}

	void createSyncAnimeEffectFore(IActorInterface tg, int frameChangeInterval,
			int animeId, int maxLoopCount) throws Exception {
		EffectAnime effect = new EffectAnime(new EffectCallBack() {

			public void onUpdate(Effect e) throws Exception {
			}

			public void onHit(Effect e, IMaptipClbInterface tip)
					throws Exception {
			}

			public void onEnd(Effect e) throws Exception {
			}
		});
		effect.set(tg, frameChangeInterval, animeId, maxLoopCount);
		MapFactory.getMap().addSyncEffect(effect);
	}

	void createAsyncAnimeEffectFore(IActorInterface tg,
			int frameChangeInterval, int animeId, int maxLoopCount)
			throws Exception {
		EffectAnime effect = new EffectAnime(new EffectCallBack() {

			public void onUpdate(Effect e) throws Exception {
			}

			public void onHit(Effect e, IMaptipClbInterface tip)
					throws Exception {
			}

			public void onEnd(Effect e) throws Exception {
			}
		});
		effect.set(tg, frameChangeInterval, animeId, maxLoopCount);
		MapFactory.getMap().addAsyncEffect(effect);
	}
}
