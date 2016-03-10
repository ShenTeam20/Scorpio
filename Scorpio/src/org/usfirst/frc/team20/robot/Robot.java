
package org.usfirst.frc.team20.robot;

import org.usfirst.frc.team20.robot.Team20Libraries.REVDigitBoard;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	REVDigitBoard revDB = new REVDigitBoard();

	public void robotInit() {
		revDB.clear();
	}

	public void disabledInit() {
		revDB.clear();
		Scorpio.poke();
		// Scorpio.vision.start();
	}

	public void disabledPeriodic() {

		// System.out.println(Scorpio.ahrs.ahrs.getAngle());
	}

	public void autonomousInit() {
	}

	public void autonomousPeriodic() {
		double temp;
	}

	public void teleopPeriodic() {
		Scorpio.operator.opControls();
		Scorpio.driver.driverControls();
	}

	public void testPeriodic() {

	}

}
