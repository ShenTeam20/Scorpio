package org.usfirst.frc.team20.robot;

import org.usfirst.frc.team20.robot.Team20Libraries.T20GamePad;

import subsystems.Drivetrain.driveModes;

public class DriverControls extends Scorpio {

	private T20GamePad driverJoy = new T20GamePad(T20GamePad.JS_TYPE_XBOX, 0);

	private double heading = 0;
	private boolean navXOn = true;

	public DriverControls() {
		driverJoy.leftStickTolerance = .1;
	}

	private long mills = 0;

	public void driverControls() {
		if (Math.abs(driverJoy.getAxisTrigger()) > 0.2 && drivetrain.driveMode != driveModes.CAMERA_TARGET) {
			drivetrain.setRobotCentric();
			heading = drivetrain.getHeading();
			mills = System.currentTimeMillis() + 200;
		} else {
			if (drivetrain.driveMode != driveModes.CAMERA_TARGET && mills < System.currentTimeMillis()) {
				if (drivetrain.driveMode == driveModes.ROBOT_CENTRIC) {

					heading = drivetrain.getHeading();
				}
				if (ahrs.ahrs.isConnected() && navXOn) {
					drivetrain.setFieldCentric();
				}
			}
		}

		if (driverJoy.getOneShotButtonStart()) {
			switch (drivetrain.driveMode) {
			case FIELD_CENTRIC:
				drivetrain.setCameraTargetMode();
				break;
			case ROBOT_CENTRIC:
				heading = drivetrain.getHeading();
				drivetrain.setCameraTargetMode();
				break;
			case CAMERA_TARGET:
				heading = drivetrain.getHeading();
				drivetrain.setRobotCentric();
			}
		}

		if (driverJoy.getOneShotButtonBack()) {
			ahrs.ahrs.reset();
			heading = ahrs.ahrs.getAngle();
		}

		if (driverJoy.getOneShotButtonA()) {
			drivetrain.resetEncoders();
		}

		if (drivetrain.driveMode == driveModes.ROBOT_CENTRIC) {
			heading = driverJoy.getAxisTrigger();
		}
		drivetrain.drive(driverJoy.getAxisLeftStickY(), heading);

		// Tomahawk Controls
		if (driverJoy.getButtonB()) {
			if (hood.hoodIsActuallyHomed && hood.getHoodEnc() < 30000) {
				operator.hoodPositonHolder = 55000;
			}
			tomahawks.actuateTomahawks();
		}
		if (driverJoy.getButtonX()) {
			if (hood.hoodIsActuallyHomed && hood.getHoodEnc() < 30000) {
				operator.hoodPositonHolder = 55000;
			}
			tomahawks.retractTomahawks();
		}

		if (driverJoy.getPOV() == 180) {
			hood.setHoodHomeState(true);
		}
		if (driverJoy.getPOV() == 0) {
			salt.addSalt();
		} else {
			salt.addWater();
		}
	}

}
