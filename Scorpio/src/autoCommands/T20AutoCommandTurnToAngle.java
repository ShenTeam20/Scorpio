package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

import edu.wpi.first.wpilibj.Timer;

public class T20AutoCommandTurnToAngle extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;
	private double angle, heading, sysTime;

	public T20AutoCommandTurnToAngle(double angle) {
		this.isFinished = false;
		this.isStarted = false;
		this.angle = angle;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Turn To Heading: " + this.heading + ">");
			isStarted = !isStarted;
			drivetrain.setFieldCentric();
			heading = ahrs.ahrs.getAngle();
			heading += angle;
			if (heading > 360) {
				heading -= 360;
			}
			if (heading < 0) {
				heading += 360;
			}
		}

		drivetrain.drive(0, this.heading);

		if (Math.abs(heading - ahrs.ahrs.getAngle()) > 10) {
			sysTime = System.currentTimeMillis();
		}

		if (Math.abs(heading - ahrs.ahrs.getAngle()) < 10 && System.currentTimeMillis() > sysTime + 500) {
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
		return new T20AutoCommandTurnToAngle(this.heading);
	}

}
