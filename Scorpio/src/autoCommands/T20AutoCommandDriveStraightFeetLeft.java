package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

public class T20AutoCommandDriveStraightFeetLeft extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;
	private double speed, distanceInFeet, heading, encCount, startCount;
	private final double COUNT_TO_FEET_NUM = 3610;

	public T20AutoCommandDriveStraightFeetLeft(double speed, double distanceInFeet) {
		this.isFinished = false;
		this.isStarted = false;
		this.speed = -speed;
		this.distanceInFeet = distanceInFeet;
	}

	@Override
	public void execute() {
		if (isFinished) {
			drivetrain.resetEncoders();
			return;
		}

		if (!isStarted) {
			System.out
					.println("<Drive Straight At Speed: " + this.speed + " For Distance: " + this.distanceInFeet + ">");
			drivetrain.setFieldCentric();
			isStarted = !isStarted;
			heading = ahrs.ahrs.getAngle();
			this.encCount = (this.distanceInFeet * COUNT_TO_FEET_NUM) + Math.abs(drivetrain.getLeftSideEncVal());
		}
		heading = ahrs.ahrs.getAngle();
		if ((this.speed == 1 || this.speed == -1) && Math.abs(drivetrain.getLeftSideEncVal()) >= this.encCount * .25) {
			drivetrain.drive(this.speed * .75, this.heading);
		} else if ((this.speed == 1 || this.speed == -1)
				&& Math.abs(drivetrain.getLeftSideEncVal()) >= this.encCount * .50) {
			drivetrain.drive(this.speed * .50, this.heading);
		} else if ((this.speed == 1 || this.speed == -1)
				&& Math.abs(drivetrain.getLeftSideEncVal()) >= this.encCount * .75) {
			drivetrain.drive(this.speed * .25, this.heading);
		} else if (((this.speed == 1 || this.speed == -1)
				&& Math.abs(drivetrain.getLeftSideEncVal()) <= this.encCount * .25)
				|| (this.speed < 1 && this.speed > -1)) {
			drivetrain.drive(this.speed, this.heading);
		}
		if (Math.abs(drivetrain.getLeftSideEncVal()) >= this.encCount) {
			System.out.println("</Drive Straight At Speed: " + this.speed + " For Distance: " + this.distanceInFeet
					+ ">" + " encCount" + this.encCount + " startCount" + this.startCount);
			drivetrain.drive(0, this.heading);
			if (ahrs.ahrs.getAngle() < this.heading + 10 && ahrs.ahrs.getAngle() > this.heading - 10)
				this.isFinished = true;
		}
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		// Don't use
		return new T20AutoCommandDriveStraightFeetLeft(this.speed, this.distanceInFeet);
	}

}
