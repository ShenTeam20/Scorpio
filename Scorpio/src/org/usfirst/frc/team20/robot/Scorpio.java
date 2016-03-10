package org.usfirst.frc.team20.robot;

import org.usfirst.frc.team20.robot.Team20Libraries.AHRSGyro;
import org.usfirst.frc.team20.robot.Team20Libraries.VisionThread;

import auto.AutoModeChooser;
import subsystems.*;

public class Scorpio extends Constants {

	public static void poke() {

	}

	// Subsystems
	protected static Lance lance = new Lance(); // Done
	protected static Indexer indexer = new Indexer(); // Done
	protected static Tomahawks tomahawks = new Tomahawks(); // Done
	protected static Flywheel flywheel = new Flywheel(); // Done
	protected static Hood hood = new Hood(); // Done
	protected static Drivetrain drivetrain = new Drivetrain(); // Done

	// Vision
	protected static VisionThread vision = new VisionThread();// Done

	// Gyro
	protected static AHRSGyro ahrs = new AHRSGyro(); // Done

	// Auto
	protected static AutoModeChooser autoModeChooser = new AutoModeChooser();

	// Human Controls
	protected static OperatorControls operator = new OperatorControls(); // Done
	protected static DriverControls driver = new DriverControls(); // Done
}
