package src.jp.co.yh123.zlibrary.util;

import java.util.Random;

public class RandomValues {

	private Random rand;

	public RandomValues(long seed) {
		rand = new Random(seed);
	}

	/**
	 * @param min
	 * @param max
	 * @return random value between min to max-1
	 */
	public synchronized int getRandomInt(int min, int max) {
		if (min == max) {
			return min;
		}
		int rlt = (int) (next() * (max - min));
		rlt += min;
		if (rlt >= max) {
			rlt = max;
		} else if (rlt < min) {
			rlt = min;
		}
		return rlt;
	}

	public synchronized double next() {
		return rand.nextDouble();
	}

}
