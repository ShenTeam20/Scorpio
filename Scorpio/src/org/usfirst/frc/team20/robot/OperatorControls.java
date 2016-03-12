package org.usfirst.frc.team20.robot;

import org.usfirst.frc.team20.robot.Team20Libraries.T20GamePad;

import subsystems.Drivetrain.driveModes;

public class OperatorControls extends Scorpio {

	protected T20GamePad operatorJoy = new T20GamePad(T20GamePad.JS_TYPE_XBOX, 1);
	private double tempFlyspeed = 0, tempHoodPos = 0;

	public OperatorControls() {

	}

	public void opControls() {
		indexer.indexerBumpSwitchWatchDog();
		hood.hoodHomeWatchdog();
		lance.lanceMovementWatchDog();
		// lance.lanceSensors();

		// Lance controls
		if (operatorJoy.getOneShotButtonLB()) {
			lance.toggleLance();
			if (!lance.getMagSwitchIsExtened()) {
				tempHoodPos = hood.HOOD_POS_SAFE;
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
			tempFlyspeed = flywheel.FLYSPEED_STOP;
		}

		if (operatorJoy.getOneShotButtonStart()) {
			tempFlyspeed = flywheel.FLYSPEED_OUTERWORKS;
		}

		if (operatorJoy.getAxisTrigger() > .8) {
			flywheel.fire();
		}

		// Hood controls
		if (operatorJoy.getPOV() == 270) {
			tempHoodPos = hood.HOOD_POS_OUTERWORKS;
			tempFlyspeed = flywheel.FLYSPEED_OUTERWORKS;
		}
		if (operatorJoy.getPOV() == 90) {
			tempHoodPos = hood.HOOD_POS_BATTER;
			tempFlyspeed = flywheel.FLYSPEED_BATTER;
		}
		if (operatorJoy.getPOV() == 0) {
			tempHoodPos = hood.HOOD_POS_SAFE;
			tempFlyspeed = flywheel.FLYSPEED_STOP;
		}
		if (operatorJoy.getPOV() == 180) {
			hood.homeHood();
			tempHoodPos = 4000;
			tempFlyspeed = flywheel.FLYSPEED_STOP;
		}
		if (operatorJoy.getOneShotButtonLS()) {
			hood.enableHoodControl();
			tempHoodPos = hood.getHoodEnc();
		}

		flywheel.flywheelToSpeed(tempFlyspeed);
		if (drivetrain.driveMode != driveModes.CAMERA_TARGET && hood.getHoodHomeState()) {
			hood.moveHoodPositon(tempHoodPos);
		}
	}
}
