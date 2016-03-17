package org.usfirst.frc.team20.robot;

import org.usfirst.frc.team20.robot.Team20Libraries.T20GamePad;

import subsystems.Drivetrain.driveModes;

public class OperatorControls extends Scorpio {

	protected T20GamePad operatorJoy = new T20GamePad(T20GamePad.JS_TYPE_XBOX, 1);
	public double flyspeedHolder = 0, hoodPositonHolder = 0;

	public OperatorControls() {

	}

	public void opControls() {
		indexer.indexerBumpSwitchWatchDog();
		hood.hoodHomeWatchdog();
		lance.lanceMovementWatchDog();
		// flywheel.getSpeed();
		// System.out.println(" "+hood.getHoodEnc());
		// lance.lanceSensors();

		// Lance controls
		if (operatorJoy.getOneShotButtonLB()) {
			lance.toggleLance();
			if (!lance.getMagSwitchIsExtened() && hood.getHoodEnc() > hood.HOOD_POS_SAFE) {
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
			hoodPositonHolder += (10000 * operatorJoy.getAxisRightStickY());
		}

		if (operatorJoy.getPOV() == 270) {
			hoodPositonHolder = hood.HOOD_POS_OUTERWORKS;
			flyspeedHolder = flywheel.FLYSPEED_OUTERWORKS;
		}
		if (operatorJoy.getPOV() == 90) {
			hoodPositonHolder = hood.HOOD_POS_BATTER;
			flyspeedHolder = flywheel.FLYSPEED_BATTER;
		}
		if (operatorJoy.getPOV() == 0) {
			hoodPositonHolder = hood.HOOD_POS_SAFE;
			flyspeedHolder = flywheel.FLYSPEED_STOP;
		}
		if (operatorJoy.getPOV() == 180) {
			hood.homeHood();
			hoodPositonHolder = 4000;
			flyspeedHolder = flywheel.FLYSPEED_STOP;
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
