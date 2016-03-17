package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

public class T20AutoCommandDoNothing extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;

	public T20AutoCommandDoNothing() {
		this.isFinished = false;
		this.isStarted = false;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Literally Doing NOTHING>");
			isStarted = !isStarted;
		}
		System.out.println("</Literally Doing NOTHING>");
		this.isFinished = true;
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoCommandDoNothing();
	}

}
