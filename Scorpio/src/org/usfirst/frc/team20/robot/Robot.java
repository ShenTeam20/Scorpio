
package org.usfirst.frc.team20.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	Timer resetTimer = new Timer();
	Scorpio scorpio = new Scorpio();
	private boolean visionStart = false;

	public void robotVisionThread() {
		SmartDashboard.putString("DB/String 0", "0");
		Scorpio.poke();
	}

	public void disabledInit() {
		SmartDashboard.putString("DB/String 0", "0");
		Scorpio.ahrs.ahrs.reset();
		resetTimer.start();
		if (!visionStart) {
			// Scorpio.vision.start();

			visionStart = true;
			visionStart = Scorpio.vision.vision.init();
		}
	}

	public void disabledPeriodic() {
		String tempString = SmartDashboard.getString("DB/String 0");
		if (tempString.length() > 0) {
			int tempInt;
			try {
				tempInt = Integer.parseInt(tempString);
			} catch (Exception e) {
				tempInt = 0;
			}

			Scorpio.smartDashAutoChooser.setAutoModePos(tempInt);
		} else {
			Scorpio.smartDashAutoChooser.setAutoModePos(0);
		}
		if (resetTimer.get() > 30) {
			Scorpio.ahrs.ahrs.reset();
			resetTimer.reset();
		}
	}

	public void autonomousInit() {
		String tempString = SmartDashboard.getString("DB/String 0");
		if (tempString.length() > 0) {
			int tempInt;
			try {
				tempInt = Integer.parseInt(tempString);
			} catch (Exception e) {
				tempInt = 0;
			}

			Scorpio.autoModes.createMainAutoMode(tempInt, SmartDashboard.getBoolean("DB/Button 1"),
					SmartDashboard.getBoolean("DB/Button 3"));

			Scorpio.smartDashAutoChooser.setAutoModePos(tempInt);
		} else {
			Scorpio.smartDashAutoChooser.setAutoModePos(0);
		}
		Scorpio.ahrs.ahrs.reset();
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
