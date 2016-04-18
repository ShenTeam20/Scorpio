package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

import edu.wpi.first.wpilibj.Timer;

public class T20AutoCommandDriveStraightTime extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;
	private double speed, time, heading;
	private Timer driveTimer = new Timer();

	public T20AutoCommandDriveStraightTime(double speed, double time) {
		this.isFinished = false;
		this.isStarted = false;
		this.time = time;
		this.speed = -speed;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Drive Straight At Speed: " + this.speed + " For Time: " + this.time + ">");
			drivetrain.setFieldCentric();
			isStarted = !isStarted;
			heading = ahrs.ahrs.getAngle();
			driveTimer.start();
		}
		if (driveTimer.get() < this.time) {
			drivetrain.drive(this.speed, this.heading);
		} else if (driveTimer.get() > this.time) {
			drivetrain.drive(0, this.heading);
			System.out.println("</Drive Straight At Speed: " + this.speed + " For Time: " + this.time + ">");
			this.isFinished = true;
		}
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoCommandDriveStraightTime(this.speed, this.time);
	}

}
