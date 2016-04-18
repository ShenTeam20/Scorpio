package org.usfirst.frc.team20.robot;

import org.usfirst.frc.team20.robot.Team20Libraries.T20GamePad;

import subsystems.Drivetrain.driveModes;

public class OperatorControls extends Scorpio {

	protected T20GamePad operatorJoy = new T20GamePad(T20GamePad.JS_TYPE_XBOX, 1);
	public double flyspeedHolder = 0, hoodPositonHolder = 0;
	private boolean helperBool = false;

	public OperatorControls() {

	}

	public void opControls() {
		indexer.indexerBumpSwitchWatchDog();
		hood.hoodHomeWatchdog();
		lance.lanceMovementWatchDog();
		// System.out.println(hood.getHoodEnc());
		flywheel.getSpeed();
		// System.out.println(" left" + drivetrain.getLeftSideEncVal() + "right"
		// + drivetrain.getRightSideEncVal());
		// lance.lanceSensors();

		// Lance controls
		if (operatorJoy.getOneShotButtonLB() && drivetrain.driveMode != driveModes.CAMERA_TARGET) {
			lance.toggleLance();
			if (!lance.getMagSwitchIsExtened() && hood.getHoodEnc() > hood.HOOD_POS_SAFE && hood.hoodIsActuallyHomed) {
				hoodPositonHolder = hood.HOOD_POS_SAFE;
			}
		}

		if (operatorJoy.getOneShotButtonY()) {
			lance.intakeLance();
			indexer.intakeIndexer();
		}

		if (operatorJoy.getOneShotButtonB()) {
			lance.stopIntake();
			indexer.stopIndexer();
		}

		if (operatorJoy.getOneShotButtonX()) {
			lance.stopIntake();
			indexer.stopIndexer();
		}

		if (operatorJoy.getOneShotButtonA()) {
			lance.backDrive();
			indexer.backdriveIndexer();
		}

		// Flywheel controls
		if (operatorJoy.getOneShotButtonBack()) {
			lance.stopIntake();
			indexer.stopIndexer();
			flyspeedHolder = flywheel.FLYSPEED_STOP;
		}

		if (operatorJoy.getOneShotButtonStart()) {
			flyspeedHolder = flywheel.FLYSPEED_OUTERWORKS;
		}

		if (operatorJoy.getAxisTrigger() > .8) {
			flywheel.fire();
		}

		// Hood controls
		if (drivetrain.driveMode != driveModes.CAMERA_TARGET && Math.abs(operatorJoy.getAxisRightStickY()) > .2) {
			hoodPositonHolder += (1000 * operatorJoy.getAxisRightStickY());
		}

		if (operatorJoy.getPOV() == 270) {
			if (hood.hoodIsActuallyHomed)
				hoodPositonHolder = hood.HOOD_POS_OUTERWORKS;
			flyspeedHolder = flywheel.FLYSPEED_OUTERWORKS;
		}
		if (operatorJoy.getPOV() == 90) {
			if (hood.hoodIsActuallyHomed)
				hoodPositonHolder = hood.HOOD_POS_BATTER;
			flyspeedHolder = flywheel.FLYSPEED_BATTER;
		}
		if (operatorJoy.getPOV() == 0) {
			if (hood.hoodIsActuallyHomed)
				hoodPositonHolder = hood.HOOD_POS_THE_6;
			flyspeedHolder = flywheel.FLYSPEED_OUTERWORKS;
		}
		if (operatorJoy.getPOV() == 180) {

			if (hood.hoodIsActuallyHomed || drivetrain.driveMode != driveModes.CAMERA_TARGET) {
				hoodPositonHolder = 20000;
				helperBool = true;
			}

			if (!hood.hoodIsActuallyHomed) {
				hood.homeHood();
				hood.hoodIsActuallyHomed = true;
				hoodPositonHolder = 4000;
			}
			flyspeedHolder = flywheel.FLYSPEED_STOP;
		}

		if (helperBool && hood.getHoodEnc() < 30000) {
			hood.homeHood();
			helperBool = false;
		}

		if (operatorJoy.getOneShotButtonLS()) {
			hood.enableHoodControl();
			hoodPositonHolder = hood.getHoodEnc();
		}

		flywheel.flywheelToSpeed(flyspeedHolder);
		if (drivetrain.driveMode != driveModes.CAMERA_TARGET && hood.getHoodHomeState()) {
			hood.moveHoodPositon(hoodPositonHolder);
		}
	}
}
