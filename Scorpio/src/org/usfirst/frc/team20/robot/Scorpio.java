package org.usfirst.frc.team20.robot;

import org.usfirst.frc.team20.robot.Team20Libraries.AHRSGyro;
import org.usfirst.frc.team20.robot.Team20Libraries.VisionThread;

import auto.AutoModes;
import auto.SmartDashAutoChooser;
import subsystems.Drivetrain;
import subsystems.Flywheel;
import subsystems.Hood;
import subsystems.Indexer;
import subsystems.Lance;
import subsystems.SystematicAcension_by_LiftingTechnology;
import subsystems.Tomahawks;

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
	protected static SystematicAcension_by_LiftingTechnology salt = new SystematicAcension_by_LiftingTechnology(); // DONE

	// Vision
	protected static VisionThread vision = new VisionThread();// Done

	// Gyro
	protected static AHRSGyro ahrs = new AHRSGyro(); // Done

	// Auto
	protected static SmartDashAutoChooser smartDashAutoChooser = new SmartDashAutoChooser(); // DONE
	protected static AutoModes autoModes = new AutoModes();

	// Human Controls
	protected static OperatorControls operator = new OperatorControls(); // Done
	protected static DriverControls driver = new DriverControls(); // Done
	protected static TsarControls tsar = new TsarControls(); // DONE
}
