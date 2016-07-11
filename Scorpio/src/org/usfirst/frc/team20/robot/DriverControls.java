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
		System.out.println(
				"One side " + drivetrain.getLeftSideEncVal() + " other side " + drivetrain.getRightSideEncVal());

		// System.out.println(" navX" + ahrs.ahrs.getAngle());
		if (Math.abs(driverJoy.getAxisTrigger()) > 0.05 && drivetrain.driveMode != driveModes.CAMERA_TARGET) {
			drivetrain.setRobotCentric();
			heading = drivetrain.getHeading();
			mills = System.currentTimeMillis() + 200;
		} else {
			if (drivetrain.driveMode != driveModes.CAMERA_TARGET && mills < System.currentTimeMillis()) {
				if (drivetrain.driveMode == driveModes.ROBOT_CENTRIC) {

					heading = drivetrain.getHeading();
				}
				if (ahrs.ahrs.isConnected() && navXOn) {
					// drivetrain.setFieldCentric();
				}
			}
		}

		// SWITCH DRIVE MODE
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
		// END SWITCH DRIVE MODE

		// RESET SENSORS
		if (driverJoy.getOneShotButtonBack()) {
			ahrs.ahrs.reset();
			heading = ahrs.ahrs.getAngle();
		}

		if (driverJoy.getOneShotButtonA()) {
			drivetrain.resetEncoders();
		}
		// END RESET SENSORS

		if (drivetrain.driveMode == driveModes.ROBOT_CENTRIC) {
			heading = driverJoy.getAxisTrigger();
		}
		drivetrain.drive(driverJoy.getAxisLeftStickY(), heading);

		// TOMAHAWK CONTROL
		if (driverJoy.getButtonB()) {
			tomahawks.actuateTomahawks();
		}
		if (driverJoy.getButtonX()) {
			tomahawks.retractTomahawks();
		}
		// END TOMAHAWK CONTROL
	}

}
