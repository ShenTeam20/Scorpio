package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

public class T20AutoCommandFlywheelToSpeed extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;
	private double speed;

	public T20AutoCommandFlywheelToSpeed(double speed) {
		this.isFinished = false;
		this.isStarted = false;
		this.speed = speed;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Flywheel Going To RPM: " + this.speed + ">");
			isStarted = !isStarted;
		}
		flywheel.flywheelToSpeed(this.speed);
		if (flywheel.getSpeed() >= this.speed) {
			System.out.println("</Flywheel Going To RPM: " + this.speed + ">");
			this.isFinished = true;
		}
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoCommandFlywheelToSpeed(this.speed);
	}

}
