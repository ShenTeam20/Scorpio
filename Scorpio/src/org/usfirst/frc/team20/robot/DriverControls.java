package org.usfirst.frc.team20.robot;

import org.usfirst.frc.team20.robot.Team20Libraries.T20GamePad;

import subsystems.Drivetrain.driveModes;

public class DriverControls extends Scorpio {

	private T20GamePad driverJoy = new T20GamePad(T20GamePad.JS_TYPE_XBOX, 0);

	private double heading = 0;

	public DriverControls() {
		driverJoy.leftStickTolerance = .1;
	}

	private long mills = 0;

	public void driverControls() {
		System.out
				.println("              " + drivetrain.getLeftSideEncVal() + "     " + drivetrain.getRightSideEncVal());
		if (Math.abs(driverJoy.getAxisTrigger()) > 0.2 && drivetrain.driveMode != driveModes.CAMERA_TARGET) {
			drivetrain.setRobotCentric();
			heading = drivetrain.getHeading();
			mills = System.currentTimeMillis() + 200;
		} else {
			if (drivetrain.driveMode != driveModes.CAMERA_TARGET && mills < System.currentTimeMillis()) {
				if (drivetrain.driveMode == driveModes.ROBOT_CENTRIC) {
					heading = drivetrain.getHeading();
				}
				drivetrain.setFieldCentric();
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
			heading = 0;
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
			tomahawks.actuateTomahawks();
		}
		if (driverJoy.getButtonX()) {
			tomahawks.retractTomahawks();
		}
	}

}
