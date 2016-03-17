
package org.usfirst.frc.team20.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends IterativeRobot {

	Timer resetTimer = new Timer();

	public void robotInit() {
		Scorpio.poke();
	}

	public void disabledInit() {
		Scorpio.ahrs.ahrs.reset();
		resetTimer.start();
		Scorpio.vision.start();
		Scorpio.poke();
		Scorpio.autoModeChooser.clearRevBoard();
	}

	public void disabledPeriodic() {
		Scorpio.autoModeChooser.revBoardHandler();
		if (resetTimer.get() > 30) {
			Scorpio.ahrs.ahrs.reset();
			resetTimer.reset();
		}
	}

	public void autonomousInit() {
		Scorpio.ahrs.ahrs.reset();
		Scorpio.autoModes.createMainAutoMode();
	}

	public void autonomousPeriodic() {
		Scorpio.autoModes.executeMainAutoMode();
	}

	public void teleopPeriodic() {
		Scorpio.operator.opControls();
		Scorpio.driver.driverControls();
	}

	public void testPeriodic() {

	}

}
