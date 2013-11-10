package com.jloisel.application;

import com.google.common.collect.Range;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jloisel.car.api.Car;
import com.jloisel.car.immutable.ImmutableCarModule;
import com.jloisel.engine.turbo.TurboEngineModule;
import com.jloisel.powerband.api.PowerBand;
import com.jloisel.powerband.constant.ConstantPowerBandModule;

/**
 * Executes the application.
 * 
 * @author Jerome
 *
 */
public final class Main {
	// N.m (Newton Meter)
	static final int TORQUE = 350;
	// HP (Horse Power)
	static final int HORSE_POWER = 200;
	// RPM (Rotation Per Minute)
	static final Range<Integer> RPM_RANGE = Range.closed(1000, 6500);

	public static void main(String[] args) {
		final Injector injector = Guice.createInjector(
			new ImmutableCarModule(), 
			new TurboEngineModule(),
			new ConstantPowerBandModule(), 
			new WiringModule()
		);

		final Car car = injector.getInstance(Car.class);
		final PowerBand powerBand = car.powerBand();

		final int rpm = 2000;
		System.out.println("At " + rpm + " RPM, Car outputs:");
		System.out.println(powerBand.horsePower().apply(rpm)+ " hp");
		System.out.println(powerBand.torque().apply(rpm) + " N.m");
	}
}