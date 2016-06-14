package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

public class T20AutoCommandToggleLance extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;

	public T20AutoCommandToggleLance() {
		this.isFinished = false;
		this.isStarted = false;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Lance Is Toggling>");
			isStarted = !isStarted;
		}
		lance.toggleLance();
		this.isFinished = true;
		System.out.println("</Lance Is Toggling>");

	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoCommandToggleLance();
	}

}
