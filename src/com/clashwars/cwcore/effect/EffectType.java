package com.clashwars.cwcore.effect;

public enum EffectType {

	/**
	 * Effect is once played instantly.
	 */
	INSTANT,
	/**
	 * Effect is several times played instantly. Set the interval with {@link BaseEffect.period}.
	 */
	REPEATING,
	/**
	 * Effect is once delayed played. Set delay with {@link BaseEffect.delay}.
	 */
	DELAYED;
	
}
