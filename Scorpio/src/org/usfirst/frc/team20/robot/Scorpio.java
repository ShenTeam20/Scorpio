package org.usfirst.frc.team20.robot;

import org.usfirst.frc.team20.robot.Team20Libraries.AHRSGyro;
import org.usfirst.frc.team20.robot.Team20Libraries.VisionThread;

import auto.AutoModesV2;
import subsystems.Drivetrain;
import subsystems.Flashlight;
import subsystems.Flywheel;
import subsystems.Hood;
import subsystems.Indexer;
import subsystems.Lance;
import subsystems.Tomahawks;

public class Scorpio {

	public Scorpio() {

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
	protected static Flashlight flashA_AH = new Flashlight();

	// Gyro
	protected static AHRSGyro ahrs = new AHRSGyro(); // Done

	// Auto
	protected static AutoModesV2 autoModes = new AutoModesV2();

	// Human Controls
	protected static OperatorControls operator = new OperatorControls(); // Done
	protected static DriverControls driver = new DriverControls(); // Done
	protected static TsarControls tsar = new TsarControls(); // DONE
}
