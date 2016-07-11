package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

import edu.wpi.first.wpilibj.Timer;

public class T20AutoCommandDelaySeconds extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;
	private double speed, time, heading;
	private Timer driveTimer = new Timer();

	public T20AutoCommandDelaySeconds(double time) {
		this.isFinished = false;
		this.isStarted = false;
		this.time = time;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Delay: " + " For Time: " + this.time + ">");
			isStarted = !isStarted;
			driveTimer.start();
		}
		if (driveTimer.get() < this.time) {
			drivetrain.drive(0, ahrs.ahrs.getAngle());
		} else if (driveTimer.get() > this.time) {
			System.out.println("</Delay: " + " For Time: " + this.time + ">");
			this.isFinished = true;
		}
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoCommandDelaySeconds(this.time);
	}

}
