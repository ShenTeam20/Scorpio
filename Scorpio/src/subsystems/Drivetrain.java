package subsystems;

import org.usfirst.frc.team20.robot.Constants;
import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.HeadingMasterExecutor;
import org.usfirst.frc.team20.robot.Team20Libraries.T20CANTalon;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain extends Scorpio {
	// 8500
	private final double scaling = 8500;
	// 10000

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
		printAVGCurrent();
		switch (driveMode) {
		case ROBOT_CENTRIC:
			headingSetpoint = 0;
			// drivetrainHeadingPID.setPID(.05, .00052, .12);
			// drivetrainHeadingPID.setPID(.1, 0, .6);
			hood.setAutoTargetHoodPID(0, 0, 0);
			driveManual(speed, heading);
			break;
		case FIELD_CENTRIC:
			headingSetpoint = 0;
			// drivetrainHeadingPID.setPID(.05, .00052, .12);
			// drivetrainHeadingPID.setPID(.06, 0.0006, 0.11);
			drivetrainHeadingPID.setOPRange(-1, 1);
			hood.setAutoTargetHoodPID(0, 0, 0);
			driveAuto(speed, heading);
			break;
		case CAMERA_TARGET:
			drivetrainHeadingPID.setPID(.06, 0.0006, 0.11);
			drivetrainHeadingPID.setOPRange(-1, 1);
			hood.setAutoTargetHoodPID(.003, 0.00001, .00000001);
			driveCamera(speed / 2);
		}
	}

	private void driveCamera(double speed) {
		speed = -speed;
		getCameraValues();

		double hoodSetpoint = width * (1.53) - 50.0;
		// System.out.println(" " + width);
		if (headingSetpoint == 0) {
			headingSetpoint = vision.vision.getAngle();
		}
		// if (Math.abs(headingSetpoint - ahrs.ahrs.getAngle()) < 7) {
		// // drivetrainHeadingPID.setPID(.04, 0.0006, 0.05);
		// drivetrainHeadingPID.setPID(.08, 0.001, 0.075);
		// } else {
		// drivetrainHeadingPID.setPID(.06, 0.0006, 0.075);
		// }
		if (vision.vision.targetAquired) {
			// // filter heading setpoint .9 to .1 new
			// headingSetpoint = (headingSetpoint * .9)
			// + ((ahrs.ahrs.getAngle() + ((recCenterX + (width *
			// hoodOffsetRatio)) * cameraSpan)) * .1);
			// headingSetpoint = ahrs.ahrs.getAngle() + ((recCenterX + (width *
			// hoodOffsetRatio)) * cameraSpan);
			headingSetpoint = vision.vision.getAngle();
			hood.moveHoodPositon(vision.vision.getHoodSpoint());
			drivetrainHeadingPID.setSP(headingSetpoint);
			// drivetrainHeadingPID.setSP(ahrs.ahrs.getAngle());
			// System.out.println(" heading SP " + headingSetpoint + " ahrs: "
			// + ahrs.ahrs.getAngle());
			// drivetrainHeadingPID.setPV(ahrs.ahrs.getAngle());
			headingOffset = Math.abs(headingSetpoint - ahrs.ahrs.getAngle());
		} else {
			headingOffset = -1;
			hood.moveHoodPositon(hood.getHoodEnc());
			drivetrainHeadingPID.setPV(ahrs.ahrs.getAngle());
		}
		masterRight.set((-1 * speed + -drivetrainHeadingPID.getOP()) * scaling);
		masterLeft.set(((speed) + (-drivetrainHeadingPID.getOP())) * scaling);
	}

	private void driveAuto(double speed, double heading) {
		// printAVGCurrent();
		speed = -speed;
		double p = 0.0, i = 0.0, d = 0.0;
		try {
			p = Double.parseDouble(SmartDashboard.getString("DB/String 1"));
			i = Double.parseDouble(SmartDashboard.getString("DB/String 2"));
			d = Double.parseDouble(SmartDashboard.getString("DB/String 3"));
			if (p == 0) {
				SmartDashboard.putString("DB/String 1", "0.06");
				SmartDashboard.putString("DB/String 2", "0.0006");
				SmartDashboard.putString("DB/String 3", "0.11");
			} // if
		} catch (Exception e) {
			e.printStackTrace();
		}
		drivetrainHeadingPID.setPID(p, i, d);
		drivetrainHeadingPID.setSP(heading);
		drivetrainHeadingPID.setPV(ahrs.ahrs.getAngle());
		masterRight.set((-1 * speed + -drivetrainHeadingPID.getOP()) * scaling);
		masterLeft.set(((speed) + (-drivetrainHeadingPID.getOP())) * scaling);
		followerLeft.set(0);
		followerRight.set(2);
		headingOffset = Math.abs(heading - ahrs.ahrs.getAngle());

	}

	double mLeftCurrent, sLeftCurrent, mRightCurrent, sRightCurrent;

	public void printAVGCurrent() {
		mLeftCurrent = mLeftCurrent * .9 + masterLeft.getOutputCurrent() * .1;
		sLeftCurrent = sLeftCurrent * .9 + followerLeft.getOutputCurrent() * .1;
		mRightCurrent = mRightCurrent * .9 + masterRight.getOutputCurrent() * .1;
		sRightCurrent = sRightCurrent * .9 + followerRight.getOutputCurrent() * .1;
		System.out.println("mLeftCur: " + mLeftCurrent + "  sLeftCur: " + sLeftCurrent + "mRightCur: " + mRightCurrent
				+ "  sRightCur: " + sRightCurrent);
	}

	private void driveManual(double speed, double heading) {
		speed = -speed * 1.4;
		masterRight.set((-speed - heading) * scaling);
		masterLeft.set(((speed) - heading) * scaling);
		followerLeft.set(0);
		followerRight.set(2);
		headingOffset = -1;

	}

	private void pidtuner() {
		// double p = 0.0, i = 0.0, d = 0.0;
		// try {
		// p = Double.parseDouble(SmartDashboard.getString("DB/String 1"));
		// i = Double.parseDouble(SmartDashboard.getString("DB/String 2"));
		// d = Double.parseDouble(SmartDashboard.getString("DB/String 3"));
		// if (p == 0) {
		// SmartDashboard.putString("DB/String 1", "0.06");
		// SmartDashboard.putString("DB/String 2", "0.0006");
		// SmartDashboard.putString("DB/String 3", "0.11");
		// } // if
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// drivetrainHeadingPID.setPID(p, i, d);
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
