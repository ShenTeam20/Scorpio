package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

public class T20AutoCommandIntakeBackDrive extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;

	public T20AutoCommandIntakeBackDrive() {
		this.isFinished = false;
		this.isStarted = false;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Intakes Are BackDriving>");
			isStarted = !isStarted;
		}
		lance.backDrive();
		indexer.backdriveIndexer();
		System.out.println("</Intakes Are BackDriving>");
		this.isFinished = true;
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoCommandIntakeBackDrive();
	}

}
