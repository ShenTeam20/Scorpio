package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

public class T20AutoCommandHoodToLowPosition extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;

	public T20AutoCommandHoodToLowPosition() {
		this.isFinished = false;
		this.isStarted = false;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}
		if (!isStarted) {
			isStarted = !isStarted;
		}
		if (!hood.getHoodHomeSensor()) {
			isFinished = true;
		} else {
			hood.hoodIsActuallyHomed = false;
			hood.hoodHomeWatchDog();
		}
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoCommandHoodToLowPosition();
	}

}
