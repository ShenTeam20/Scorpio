
package org.usfirst.frc.team20.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends IterativeRobot {

	// AutoModeChooser auto = new AutoModeChooser();

	Timer resetTimer = new Timer();

	public void robotInit() {
		// auto.clearRevBoard();
		Scorpio.poke();
		Scorpio.vision.start();
	}

	public void disabledInit() {
		Scorpio.ahrs.ahrs.reset();
		resetTimer.start();
		// auto.clearRevBoard();
		Scorpio.poke();
	}

	public void disabledPeriodic() {
		if (resetTimer.get() > 30) {
			Scorpio.ahrs.ahrs.reset();
			resetTimer.reset();
		}
		// auto.revBoardHandler();
		// auto.displayRevBoardNum();
	}

	public void autonomousInit() {
		Scorpio.ahrs.ahrs.reset();
		Scorpio.autoModes.createLowBarHighGoal();
	}

	public void autonomousPeriodic() {
		Scorpio.autoModes.executeLowBarHighGoal();
	}

	public void teleopPeriodic() {
		Scorpio.operator.opControls();
		Scorpio.driver.driverControls();
	}

	public void testPeriodic() {

	}

}
