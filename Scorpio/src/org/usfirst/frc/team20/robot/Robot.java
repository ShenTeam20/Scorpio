
package org.usfirst.frc.team20.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	Timer resetTimer = new Timer();
	Scorpio scorpio = new Scorpio();

	public void disabledInit() {
		SmartDashboard.putString("DB/String 0", "0");
		Scorpio.ahrs.ahrs.reset();
		resetTimer.start();
	}

	public void disabledPeriodic() {
		if (resetTimer.get() > 30) {
			Scorpio.ahrs.ahrs.reset();
			resetTimer.reset();
		}
	}

	public void autonomousInit() {
		Scorpio.smartDashAutoChooser.createAutoMode();
		Scorpio.ahrs.ahrs.getAngle();
	}

	public void autonomousPeriodic() {
		Scorpio.vision.vision.processImage();
		Scorpio.autoModes.executeMainAutoMode();

	}

	public void teleopPeriodic() {
		Scorpio.vision.vision.processImage();
		Scorpio.operator.opControls();
		Scorpio.driver.driverControls();
		// Scorpio.tsar.tsarControls();

	}

	public void testPeriodic() {
	}

}
