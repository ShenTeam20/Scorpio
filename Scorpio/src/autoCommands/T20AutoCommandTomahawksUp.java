package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

public class T20AutoCommandTomahawksUp extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;

	public T20AutoCommandTomahawksUp() {
		this.isFinished = false;
		this.isStarted = false;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Tomahawks Are Moving Up>");
			isStarted = !isStarted;
		}
		tomahawks.retractTomahawks();
		System.out.println("</Tomahawks Are Moving Up>");
		this.isFinished = true;

	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoCommandTomahawksUp();
	}

}
