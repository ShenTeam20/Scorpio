package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

import edu.wpi.first.wpilibj.Timer;

public class T20AutoCommandLanceWatchDog extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;
	private Timer lanceTimer = new Timer();

	public T20AutoCommandLanceWatchDog() {
		this.isFinished = false;
		this.isStarted = false;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Lance Is Watchdoging>");
			isStarted = !isStarted;
			lanceTimer.start();
		}
		lance.lanceMovementWatchDog();
		if (lanceTimer.get() > .2) {
			lanceTimer.stop();
			System.out.println("</Lance Is Watchdoging>");
			this.isFinished = true;
		}
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoCommandLanceWatchDog();
	}

}
