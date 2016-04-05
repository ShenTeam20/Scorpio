package org.usfirst.frc.team20.robot;

import org.usfirst.frc.team20.robot.Team20Libraries.AHRSGyro;
import org.usfirst.frc.team20.robot.Team20Libraries.VisionTargeting2;
import org.usfirst.frc.team20.robot.Team20Libraries.VisionThread;

import auto.AutoModeChooser;
import auto.AutoModes;
import auto.SmartDashAutoChooser;
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
//	protected static VisionThread vision = new VisionThread();// Done
	protected static VisionTargeting2 vision = new VisionTargeting2();
	// Gyro
	protected static AHRSGyro ahrs = new AHRSGyro(); // Done

	// Auto
	protected static AutoModeChooser autoModeChooser = new AutoModeChooser();
	protected static SmartDashAutoChooser smartDashAutoChooser = new SmartDashAutoChooser();
	protected static AutoModes autoModes = new AutoModes();
	protected static String whatAuto = "";

	// Human Controls
	protected static OperatorControls operator = new OperatorControls(); // Done
	protected static DriverControls driver = new DriverControls(); // Done
}
