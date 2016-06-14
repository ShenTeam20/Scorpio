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
		lance.lanceMovementWatchDog();
		hood.hoodHomeWatchDog();
		// flywheel.getSpeed();
		// lance.lanceSensors();

		// Lance controls
		if (operatorJoy.getOneShotButtonLB() && drivetrain.driveMode != driveModes.CAMERA_TARGET) {
			lance.toggleLance();
			if (!lance.getMagSwitchIsExtened() && hood.getHoodEnc() < hood.HOOD_POS_SAFE && hood.hoodIsActuallyHomed) {
				hoodPositonHolder = hood.HOOD_POS_SAFE;
				flyspeedHolder = flywheel.FLYSPEED_STOP;
			}
		}

		// COLLECTOR CONTROL
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
		// END COLLECTOR CONTROL

		// FLYWHEEL CONTROL
		if (operatorJoy.getOneShotButtonBack()) {
			lance.stopIntake();
			indexer.stopIndexer();
			flyspeedHolder = flywheel.FLYSPEED_STOP;
		}

		if (operatorJoy.getOneShotButtonStart()) {
			flyspeedHolder = flywheel.FLYSPEED_OUTERWORKS;
		}
		// END FLYWHEEL CONTROL

		// FIRE BALL
		if (operatorJoy.getAxisTrigger() > .8) {
			flywheel.fire();
		}

		if (operatorJoy.getAxisTrigger() < -.8 && drivetrain.getHeadingOffSet() < 3 && drivetrain.getHeadingOffSet() > 0
				&& flywheel.getSpeed() > 8200) {
			flywheel.fire();
		}
		// END FIRE BALL

		// Hood CONTROL
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
			hood.hoodIsActuallyHomed = false;
			flyspeedHolder = flywheel.FLYSPEED_STOP;
		}
		// END HOOD CONTROL

		// RENABLE THE HOOD
		if (operatorJoy.getOneShotButtonLS()) {
			hood.enableHoodControl();
			hoodPositonHolder = hood.getHoodEnc();
		}

		// SET FLYWHEEL SPEED

		flywheel.flywheelToSpeed(flyspeedHolder);

		// FLASHLIGHT CONTROL
		if (flyspeedHolder > 0) {
			flashA_AH.flashlightOn();
		} else {
			flashA_AH.flashlightOff();
		}

		// SET HOOD POSITION
		if (drivetrain.driveMode != driveModes.CAMERA_TARGET && hood.hoodIsActuallyHomed
				&& !lance.getMagSwitchIsExtened()) {
			hood.moveHoodPositon(hoodPositonHolder);
		} else {
			hoodPositonHolder = hood.getHoodEnc();
		}

		// System.out.println(" hood pos holder: " + hoodPositonHolder + " hood
		// location: " + hood.getHoodEnc()
		// + " flyspeedholder: " + flyspeedHolder);
	}
}
