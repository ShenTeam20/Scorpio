package org.usfirst.frc.team20.robot;

import org.usfirst.frc.team20.robot.Team20Libraries.T20GamePad;

import subsystems.Drivetrain.driveModes;

public class TsarControls extends Scorpio {

	private T20GamePad tsarJoy = new T20GamePad(T20GamePad.JS_TYPE_XBOX, 3);

	public void TsarControls() {
		tsarJoy.leftStickTolerance = .1;
	}

	private long mills = 0;
	private double heading = 0;
	private boolean navXOn = true;
	public double flyspeedHolder = 0, hoodPositonHolder = 0;
	private boolean helperBool = false;

	public void tsarControls() {
		indexer.indexerBumpSwitchWatchDog();
		hood.hoodHomeWatchdog();
		lance.lanceMovementWatchDog();
		flywheel.getSpeed();
		// System.out.println(" left" + drivetrain.getLeftSideEncVal() + "right"
		// + drivetrain.getRightSideEncVal());
		// lance.lanceSensors();

		if (tsarJoy.getAxisRightStickY() > .8) {
			salt.addSalt();
		} else {
			salt.addWater();
		}

		if (Math.abs(tsarJoy.getAxisTrigger()) > 0.2 && drivetrain.driveMode != driveModes.CAMERA_TARGET) {
			drivetrain.setRobotCentric();
			heading = drivetrain.getHeading();
			mills = System.currentTimeMillis() + 200;
		} else {
			if (drivetrain.driveMode != driveModes.CAMERA_TARGET && mills < System.currentTimeMillis()) {
				if (drivetrain.driveMode == driveModes.ROBOT_CENTRIC) {

					heading = drivetrain.getHeading();
				}
				if (ahrs.ahrs.isConnected() && navXOn)
					drivetrain.setFieldCentric();
			}
		}

		if (tsarJoy.getOneShotButtonStart()) {
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

		if (drivetrain.driveMode == driveModes.ROBOT_CENTRIC) {
			heading = tsarJoy.getAxisTrigger();
		}
		drivetrain.drive(tsarJoy.getAxisLeftStickY(), heading);

		// Tomahawk Controls
		if (tsarJoy.getButtonLS()) {
			tomahawks.actuateTomahawks();
		}
		if (tsarJoy.getButtonRS()) {
			if (hood.hoodIsActuallyHomed && hood.getHoodEnc() < 20000) {
				operator.hoodPositonHolder = 25000;
			}
			tomahawks.retractTomahawks();
		}

		// Lance controls
		if (tsarJoy.getOneShotButtonLB()) {
			lance.toggleLance();
			if (!lance.getMagSwitchIsExtened() && hood.getHoodEnc() > hood.HOOD_POS_SAFE && hood.hoodIsActuallyHomed) {
				hoodPositonHolder = hood.HOOD_POS_SAFE;
			}
		}

		if (tsarJoy.getOneShotButtonY()) {
			lance.intakeLance();
			indexer.intakeIndexer();
		}

		if (tsarJoy.getOneShotButtonB()) {
			lance.stopIntake();
			indexer.stopIndexer();
		}

		if (tsarJoy.getOneShotButtonX()) {
			lance.stopIntake();
			indexer.stopIndexer();
		}

		if (tsarJoy.getOneShotButtonA()) {
			lance.backDrive();
			indexer.backdriveIndexer();
		}

		// Flywheel controls

		if (tsarJoy.getButtonRB()) {
			flywheel.fire();
		}

		if (tsarJoy.getOneShotButtonBack()) {
			lance.stopIntake();
			indexer.stopIndexer();
			flyspeedHolder = flywheel.FLYSPEED_STOP;
		}

		// Hood

		if (tsarJoy.getPOV() == 270) {
			if (hood.hoodIsActuallyHomed)
				hoodPositonHolder = hood.HOOD_POS_OUTERWORKS;
			flyspeedHolder = flywheel.FLYSPEED_OUTERWORKS;
		}
		if (tsarJoy.getPOV() == 90) {
			if (hood.hoodIsActuallyHomed)
				hoodPositonHolder = hood.HOOD_POS_BATTER;
			flyspeedHolder = flywheel.FLYSPEED_BATTER;
		}
		if (tsarJoy.getPOV() == 0) {
			if (hood.hoodIsActuallyHomed)
				hoodPositonHolder = hood.HOOD_POS_THE_6;
			flyspeedHolder = flywheel.FLYSPEED_OUTERWORKS;
		}
		if (tsarJoy.getPOV() == 180) {

			if (hood.hoodIsActuallyHomed || drivetrain.driveMode != driveModes.CAMERA_TARGET) {
				hoodPositonHolder = 30000;
				helperBool = true;
			}

			if (!hood.hoodIsActuallyHomed) {
				hood.homeHood();
				hood.hoodIsActuallyHomed = true;
				hoodPositonHolder = 4000;
			}
			flyspeedHolder = flywheel.FLYSPEED_STOP;
		}

		if (helperBool && hood.getHoodEnc() < 40000) {
			hood.homeHood();
			helperBool = false;
		}

		flywheel.flywheelToSpeed(flyspeedHolder);
		if (drivetrain.driveMode != driveModes.CAMERA_TARGET && hood.getHoodHomeState()) {
			hood.moveHoodPositon(hoodPositonHolder);
		}

	}

}
