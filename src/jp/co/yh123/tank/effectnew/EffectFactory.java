package src.jp.co.yh123.tank.effectnew;

import src.jp.co.yh123.tank.sfx.IMessageEffect;
import src.jp.co.yh123.tank.sfx.ITaskEffect;

public class EffectFactory {

	public static AnimeEffect createAnimeEffect() throws Exception {
		return new AnimeEffect();

	}

	public static BulletEffect createBulletEffect() throws Exception {
		return new BulletEffect();
	}

	public static MapEffect createMapEffect() throws Exception {
		return new MapEffect();
	}

	public static IMessageEffect createMessageEffect() throws Exception {
		return new MessageEffect();
	}

	public static ITaskEffect createTaskEffect() throws Exception {
		return new TaskEffect();
	}
}
