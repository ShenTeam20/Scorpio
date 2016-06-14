package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

import edu.wpi.first.wpilibj.Timer;

public class T20AutoCommandLanceUp extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;
	private Timer lanceTime = new Timer();

	public T20AutoCommandLanceUp() {
		this.isFinished = false;
		this.isStarted = false;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Lance Is Moving Up>");
			isStarted = !isStarted;
			lanceTime.start();
		}
		lance.lanceMovementWatchDog();
		if (lanceTime.get() > 2) {
			lanceTime.stop();
			this.isFinished = true;
			System.out.println("</Lance Is Moving Up>");
		}

	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoCommandLanceUp();
	}

}
