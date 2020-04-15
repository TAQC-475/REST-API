package com.softserve.edu.rest.data;

public final class LifetimeRepository {

	private LifetimeRepository () {
	}

	public static Lifetime getDefault() {
		return getTypical();
	}

	public static Lifetime getTypical() {
		return new Lifetime("300000");
	}

	public static Lifetime getExtend() { return new Lifetime("900000"); }

	public static Lifetime getDefaultCooldownTime() { return new Lifetime("180000"); }

	public static Lifetime getNewCooldownTime() { return new Lifetime("360000"); }

	public static Lifetime getCooldownNegative() { return new Lifetime("-1"); }

	public static Lifetime getShort() {		return new Lifetime("5000"); }

	public static Lifetime getNegativeLifeTime() {		return new Lifetime("-5"); }

	public static Lifetime getZeroLifetime() {		return new Lifetime("0"); }

	public static Lifetime getCooldownMax() {
		return new Lifetime("43200000");
	}

	public static Lifetime getCooldownMoreThanMax() {
		return new Lifetime("43200001");
	}

	public static Lifetime getCooldownMin() {
		return new Lifetime("1");
	}

}
