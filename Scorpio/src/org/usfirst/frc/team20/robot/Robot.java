
package org.usfirst.frc.team20.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends IterativeRobot {

	// AutoModeChooser auto = new AutoModeChooser();

	public void robotInit() {
		// auto.clearRevBoard();
		Scorpio.poke();
		Scorpio.vision.start();
	}

	public void disabledInit() {
		// auto.clearRevBoard();
		Scorpio.poke();
	}

	public void disabledPeriodic() {
		// auto.revBoardHandler();
		// auto.displayRevBoardNum();
	}

	public void autonomousInit() {
		// Scorpio.autoModes.createTransformersRollOut();
		// Scorpio.autoModes.createTransformersTransform();
		// Scorpio.autoModes.createDriveStraightTime(1, 2);
		Scorpio.autoModes.createLowBar();
	}

	public void autonomousPeriodic() {
		// Scorpio.autoModes.executeTransformersRollOut();
		// Scorpio.autoModes.executeTransformersTransform();
		// Scorpio.autoModes.executeDriveStraightTime();
		Scorpio.autoModes.executeLowBar();
	}

	public void teleopPeriodic() {
		Scorpio.operator.opControls();
		Scorpio.driver.driverControls();
	}

	public void testPeriodic() {

	}

}
