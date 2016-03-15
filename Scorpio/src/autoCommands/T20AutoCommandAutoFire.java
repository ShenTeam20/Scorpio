package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

import edu.wpi.first.wpilibj.Timer;

public class T20AutoCommandAutoFire extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;
	private Timer fireTimer = new Timer();

	public T20AutoCommandAutoFire() {
		this.isFinished = false;
		this.isStarted = false;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Auto Firing>");
			flywheel.flywheelToSpeed(flywheel.FLYSPEED_OUTERWORKS);
			isStarted = !isStarted;
		}
		if (flywheel.getSpeed() > 9000) {
			flywheel.fire();
			fireTimer.start();
		}
		if (fireTimer.get() > 10) {
			System.out.println("</Auto Firing>");
			lance.stopIntake();
			indexer.stopIndexer();
			flywheel.flywheelToSpeed(flywheel.FLYSPEED_STOP);
			this.isFinished = true;
		}
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoCommandAutoFire();
	}

}
