package subsystems;

import org.usfirst.frc.team20.robot.Constants;
import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.HeadingMasterExecutor;
import org.usfirst.frc.team20.robot.Team20Libraries.T20CANTalon;

import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class Drivetrain extends Scorpio {

	private final double scaling = 8500;

	private double recCenterX = 0, recCenterY = 0, width = 0, height = 0, headingSetpoint = 0;

	private T20CANTalon masterLeft = new T20CANTalon(Constants.DRIVETRAIN_MASTER_LEFT_MOTOR_PORT);
	private T20CANTalon followerLeft = new T20CANTalon(Constants.DRIVETRAIN_FOLLOWER_LEFT_MOTOR_PORT);
	private T20CANTalon masterRight = new T20CANTalon(Constants.DRIVETRAIN_MASTER_RIGHT_MOTOR_PORT);
	private T20CANTalon followerRight = new T20CANTalon(Constants.DRIVETRAIN_FOLLOWER_RIGHT_MOTOR_PORT);

	private HeadingMasterExecutor drivetrainHeadingPID = new HeadingMasterExecutor();
	private Thread drivetrainPIDThread = new Thread(drivetrainHeadingPID);

	public enum driveModes {
		ROBOT_CENTRIC, FIELD_CENTRIC, CAMERA_TARGET
	}

	public driveModes driveMode = driveModes.ROBOT_CENTRIC;

	public Drivetrain() {
		followerLeft.changeControlMode(TalonControlMode.Follower);
		followerRight.changeControlMode(TalonControlMode.Follower);
		masterLeft.changeControlMode(TalonControlMode.Speed);
		masterRight.changeControlMode(TalonControlMode.Speed);
		masterLeft.enableControl();
		masterRight.enableControl();
		masterLeft.setPID(.08, 0, 0);
		masterRight.setPID(.08, 0, 0);
		masterLeft.setPosition(0);
		masterRight.setPosition(0);
		drivetrainPIDThread.start();
	}

	public void getCameraValues() {
		recCenterX = vision.vision.recCenterXCoordinate();
		recCenterY = vision.vision.recCenterYCoordinate();
		width = vision.vision.width;
	}

	public void setRobotCentric() {
		driveMode = driveModes.ROBOT_CENTRIC;
	}

	public void setFieldCentric() {
		driveMode = driveModes.FIELD_CENTRIC;
	}

	public void setCameraTargetMode() {
		driveMode = driveModes.CAMERA_TARGET;
	}

	public void drive(double speed, double heading) {
		switch (driveMode) {
		case ROBOT_CENTRIC:
			headingSetpoint = 0;
			drivetrainHeadingPID.setPID(.1, 0, .6);
			hood.setAutoTargetHoodPID(0, 0, 0);
			driveManual(speed, heading);
			break;
		case FIELD_CENTRIC:
			headingSetpoint = 0;
			drivetrainHeadingPID.setPID(.06, 0.0006, 0.11);
			drivetrainHeadingPID.setOPRange(-1, 1);
			hood.setAutoTargetHoodPID(0, 0, 0);
			driveAuto(speed, heading);
			break;
		case CAMERA_TARGET:
			drivetrainHeadingPID.setPID(.085, 0.005, 0.07);
			drivetrainHeadingPID.setOPRange(-1, 1);
			hood.setAutoTargetHoodPID(.003, 0.00001, .00000001);
			driveCamera(speed / 2);
		}
	}

	private void driveCamera(double speed) {
		getCameraValues();
		double cameraSpan = 0.09;
		double hoodSetpoint = -70;
		if (headingSetpoint == 0) {
			headingSetpoint = ahrs.ahrs.getAngle() + ((recCenterX + (width * .61)) * cameraSpan);
		}
		if (Math.abs(headingSetpoint - ahrs.ahrs.getAngle()) < 10) {
			drivetrainHeadingPID.setPID(.14, 0.006, 0.07);
		} else {
			drivetrainHeadingPID.setPID(.07, 0.005, 0.07);
		}
		headingSetpoint = (headingSetpoint * .9)
				+ ((ahrs.ahrs.getAngle() + ((recCenterX + (width * .61)) * cameraSpan)) * .1);
		System.out.println("                               " + headingSetpoint);
		if (vision.vision.targetAquired) {
			hood.setAutoTargetHood_PV_SP(recCenterY, hoodSetpoint);
			hood.moveHoodPositon(-hood.getAutoTargetHood_OP());
			drivetrainHeadingPID.setSP(headingSetpoint);
			drivetrainHeadingPID.setPV(ahrs.ahrs.getAngle());
		} else {
			hood.setAutoTargetHood_PV_SP(hoodSetpoint, hoodSetpoint);
			hood.moveHoodPositon(-hood.getAutoTargetHood_OP());
			drivetrainHeadingPID.setPV(headingSetpoint);
		}
		masterRight.set((-1 * speed + -drivetrainHeadingPID.getOP()) * scaling);
		masterLeft.set(((speed) + (-drivetrainHeadingPID.getOP())) * scaling);
	}

	private void driveAuto(double speed, double heading) {
		speed = -speed;
		drivetrainHeadingPID.setSP(heading);
		drivetrainHeadingPID.setPV(ahrs.ahrs.getAngle());
		masterRight.set((-1 * speed + -drivetrainHeadingPID.getOP()) * scaling);
		masterLeft.set(((speed) + (-drivetrainHeadingPID.getOP())) * scaling);
		followerLeft.set(0);
		followerRight.set(2);

	}

	private void driveManual(double speed, double heading) {
		speed = -speed;
		masterRight.set((-speed - heading) * scaling);
		masterLeft.set(((speed) - heading) * scaling);
		followerLeft.set(0);
		followerRight.set(2);
	}

	public double getHeading() {
		return ahrs.ahrs.getAngle();
	}

}
