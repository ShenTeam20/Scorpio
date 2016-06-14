package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

public class T20AutoCommandIntakeStop extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;

	public T20AutoCommandIntakeStop() {
		this.isFinished = false;
		this.isStarted = false;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Intakes Are Stopping>");
			isStarted = !isStarted;
		}
		lance.stopIntake();
		indexer.stopIndexer();
		System.out.println("</Intakes Are Stopping>");
		this.isFinished = true;
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoCommandIntakeStop();
	}

}
