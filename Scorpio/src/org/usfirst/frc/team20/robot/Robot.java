
package org.usfirst.frc.team20.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	Timer resetTimer = new Timer();
	private Scorpio scorpio = new Scorpio();
	private SendableChooser autoChooser = new SendableChooser();

	public void disabledInit() {
		Scorpio.ahrs.ahrs.reset();
		resetTimer.start();
		autoChooser.addDefault("Do Nothing", "nothing");
		autoChooser.addDefault("Position 1 Cross", "pos1cross");
		autoChooser.addDefault("Position 2-5 Cross", "pos2to5cross");
		autoChooser.addDefault("Position 2-5 Cross & Return", "pos2to5crossreturn");
		autoChooser.addDefault("Position 2 Batter Shot", "pos2battershot");
		autoChooser.addDefault("Position 3 Batter Shot", "pos3battershot");
		autoChooser.addDefault("Position 4 Batter Shot", "pos4battershot");
		autoChooser.addDefault("Position 5 Batter Shot", "pos5battershot");
		autoChooser.addDefault("Position 3 Outerworks Shot", "pos3outerworksshot");
		autoChooser.addDefault("Position 4 Outerworks Shot", "pos4outerworksshot");
		autoChooser.addDefault("Position 5 Steal Auto", "pos5steal");
		SmartDashboard.putData("Auto Modes:", autoChooser);
		SmartDashboard.putBoolean(" Collector", false);
		SmartDashboard.putDouble("Flywheel Speed ", Scorpio.flywheel.getSpeed());
		SmartDashboard.putBoolean(" Hood Homed", !Scorpio.hood.getHoodHomeSensor());
		SmartDashboard.putBoolean(" Have Ball?", Scorpio.indexer.getIndexerBumpSwitch());
		SmartDashboard.putDouble("Left Encoders ", Scorpio.drivetrain.getLeftSideEncVal());
		SmartDashboard.putDouble("Right Encoders ", Scorpio.drivetrain.getRightSideEncVal());
		SmartDashboard.putDouble("Gyro ", Scorpio.ahrs.ahrs.getAngle());
	}

	public void smartDashboardUpdater() {
		SmartDashboard.putBoolean(" Hood Homed", !Scorpio.hood.getHoodHomeSensor());
		SmartDashboard.putBoolean(" Have Ball?", Scorpio.indexer.getIndexerBumpSwitch());
		SmartDashboard.putDouble("Flywheel Speed ", Scorpio.flywheel.getSpeed());
		SmartDashboard.putDouble("Left Encoders ", Scorpio.drivetrain.getLeftSideEncVal());
		SmartDashboard.putDouble("Right Encoders ", Scorpio.drivetrain.getRightSideEncVal());
		SmartDashboard.putDouble("Gyro ", Scorpio.ahrs.ahrs.getAngle());
	}

	public void disabledPeriodic() {
		Scorpio.hood.hoodHomeWatchDog();
		if (Scorpio.hood.hoodIsActuallyHomed)
			Scorpio.operator.hoodPositonHolder = Scorpio.hood.getHoodEnc();
		if (resetTimer.get() > 30) {
			Scorpio.ahrs.ahrs.reset();
			resetTimer.reset();
		}
		smartDashboardUpdater();
	}

	public void autonomousInit() {
		Scorpio.ahrs.ahrs.getAngle();
		Scorpio.autoModes.createAuto((String) autoChooser.getSelected());
	}

	public void autonomousPeriodic() {
		Scorpio.vision.vision.processImage();
		Scorpio.autoModes.executeMainAuto();
		smartDashboardUpdater();
	}

	public void teleopInit() {
		Scorpio.operator.hoodPositonHolder = Scorpio.hood.getHoodEnc();
	}

	public void teleopPeriodic() {
		Scorpio.vision.vision.processImage();
		Scorpio.operator.opControls();
		Scorpio.driver.driverControls();
		// Scorpio.tsar.tsarControls();
		smartDashboardUpdater();
	}

	public void testPeriodic() {
		smartDashboardUpdater();
	}

}
