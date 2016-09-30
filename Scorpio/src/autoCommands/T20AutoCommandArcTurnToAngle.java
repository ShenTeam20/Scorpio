package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

public class T20AutoCommandArcTurnToAngle extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;
	private double angle, heading, sysTime, speed;

	public T20AutoCommandArcTurnToAngle(double speed, double angle) {
		this.isFinished = false;
		this.isStarted = false;
		this.angle = angle;
		this.speed = -speed;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Turn To Heading: " + this.heading + ">");
			drivetrain.setFieldCentric();
			isStarted = !isStarted;
			heading = ahrs.ahrs.getAngle();
			heading += angle;
			if (heading > 360) {
				heading -= 360;
			}
			if (heading < 0) {
				heading += 360;
			}
		}

		drivetrain.drive(this.speed, this.heading);

		if (Math.abs(heading - ahrs.ahrs.getAngle()) > 2) {
			sysTime = System.currentTimeMillis();
		}

		if (Math.abs(heading - ahrs.ahrs.getAngle()) < 2 && System.currentTimeMillis() > sysTime + 500) {
			drivetrain.drive(0, this.heading);
			System.out.println("</Turn To Heading: " + this.heading + ">");
			this.isFinished = true;
		}
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		// Don't use this
		return new T20AutoCommandArcTurnToAngle(this.speed, this.heading);
	}

}
