package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

import edu.wpi.first.wpilibj.Timer;

public class T20AutoCommandDriveStraightEncoder extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;
	private double speed, encCount, heading, startCount;

	public T20AutoCommandDriveStraightEncoder(double speed, double encCount) {
		this.isFinished = false;
		this.isStarted = false;
		this.speed = -speed;
		this.encCount = encCount;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Drive Straight At Speed: " + this.speed + " For Counts: " + this.encCount + ">");
			drivetrain.setFieldCentric();
			isStarted = !isStarted;
			heading = ahrs.ahrs.getAngle();
			this.startCount = Math.abs(drivetrain.getLeftSideEncVal());
		}
		drivetrain.drive(this.speed, this.heading);
		if ((startCount - Math.abs(drivetrain.getLeftSideEncVal()) < this.encCount)) {
			System.out.println("</Drive Straight At Speed: " + this.speed + " For Counts: " + this.encCount + ">");
			drivetrain.drive(0, this.heading);
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
		return new T20AutoCommandDriveStraightEncoder(this.speed, this.encCount);
	}

}
