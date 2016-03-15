package subsystems;

import org.usfirst.frc.team20.robot.Constants;
import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.HeadingMasterExecutor;
import org.usfirst.frc.team20.robot.Team20Libraries.T20CANTalon;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
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

	public driveModes driveMode = driveModes.FIELD_CENTRIC;
	private double headingOffset;

	public Drivetrain() {
		followerLeft.changeControlMode(TalonControlMode.Follower);
		followerRight.changeControlMode(TalonControlMode.Follower);
		masterLeft.changeControlMode(TalonControlMode.Speed);
		masterRight.changeControlMode(TalonControlMode.Speed);
		masterLeft.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		masterRight.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		masterLeft.enableControl();
		masterRight.enableControl();
		masterLeft.setPID(.08, 0, 0);
		masterRight.setPID(.08, 0, 0);
		masterLeft.setPosition(0);
		masterRight.setPosition(0);
		drivetrainPIDThread.start();
	}

	public void resetEncoders() {
		masterLeft.setPosition(0);
		masterRight.setPosition(0);
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
			// drivetrainHeadingPID.setPID(.085, 0.005, 0.07);
			drivetrainHeadingPID.setOPRange(-1, 1);
			hood.setAutoTargetHoodPID(.003, 0.00001, .00000001);
			driveCamera(speed / 2);
		}
	}

	private void driveCamera(double speed) {
		System.out.println(width);
		speed = -speed;
		getCameraValues();
//		System.out.println("                       RECXY   " + recCenterY);
		double cameraSpan = 0.09;
		double hoodOffsetRatio = .61;
		// .8695
		double hoodSetpoint = width * (1.53) - 142.0;
//		System.out.println("                                         " + width);
		if (headingSetpoint == 0) {
			headingSetpoint = ahrs.ahrs.getAngle() + ((recCenterX + (width * hoodOffsetRatio)) * cameraSpan);
		}
		if (Math.abs(headingSetpoint - ahrs.ahrs.getAngle()) < 7) {
			// drivetrainHeadingPID.setPID(.04, 0.0006, 0.05);
			drivetrainHeadingPID.setPID(.08, 0.001, 0.075);
		} else {
			drivetrainHeadingPID.setPID(.06, 0.0006, 0.075);
		}

		if (vision.vision.targetAquired) {
			// // filter heading setpoint .9 to .1 new
			// headingSetpoint = (headingSetpoint * .9)
			// + ((ahrs.ahrs.getAngle() + ((recCenterX + (width *
			// hoodOffsetRatio)) * cameraSpan)) * .1);
			headingSetpoint = ahrs.ahrs.getAngle() + ((recCenterX + (width * hoodOffsetRatio)) * cameraSpan);
			hood.setAutoTargetHood_PV_SP(recCenterY, hoodSetpoint);
			hood.moveHoodPVbus(-hood.getAutoTargetHood_OP());
			drivetrainHeadingPID.setSP(headingSetpoint);
			drivetrainHeadingPID.setPV(ahrs.ahrs.getAngle());
			headingOffset = Math.abs(headingSetpoint - ahrs.ahrs.getAngle());
		} else {
			headingOffset = -1;
			hood.setAutoTargetHood_PV_SP(hoodSetpoint, hoodSetpoint);
			hood.moveHoodPVbus(-hood.getAutoTargetHood_OP());
			drivetrainHeadingPID.setPV(ahrs.ahrs.getAngle());
		}
		masterRight.set((-1 * speed + -drivetrainHeadingPID.getOP()) * scaling);
		masterLeft.set(((speed) + (-drivetrainHeadingPID.getOP())) * scaling);
	}

	private void driveAuto(double speed, double heading) {
		getCameraValues();
//		System.out.println("                       RECXY   " + recCenterY);
//		System.out.println("                                         " + width);
		speed = -speed;
		drivetrainHeadingPID.setSP(heading);
		drivetrainHeadingPID.setPV(ahrs.ahrs.getAngle());
		masterRight.set((-1 * speed + -drivetrainHeadingPID.getOP()) * scaling);
		masterLeft.set(((speed) + (-drivetrainHeadingPID.getOP())) * scaling);
		followerLeft.set(0);
		followerRight.set(2);
		headingOffset = Math.abs(heading - ahrs.ahrs.getAngle());

	}

	private void driveManual(double speed, double heading) {
		getCameraValues();
		// System.out.println(" RECXY " + recCenterY);
		// System.out.println(" " + width);
		speed = -speed;
		masterRight.set((-speed - heading) * scaling);
		masterLeft.set(((speed) - heading) * scaling);
		followerLeft.set(0);
		followerRight.set(2);
		headingOffset = -1;

	}

	public double getHeading() {
		return ahrs.ahrs.getAngle();
	}

	public double getLeftSideEncVal() {
		return masterLeft.getEncPosition();
	}

	public double getRightSideEncVal() {
		return masterRight.getEncPosition();
	}

	public double getHeadingOffSet() {
		return headingOffset;
	}

}
