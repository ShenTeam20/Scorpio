package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

public class T20AutoCommandHoodTo610Position extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;

	public T20AutoCommandHoodTo610Position() {
		this.isFinished = false;
		this.isStarted = false;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Hood Moving To 610 Position>");
			isStarted = !isStarted;
		}
		hood.moveHoodPositon(hood.HOOD_POS_THE_6);
		if (Math.abs(hood.getHoodEnc() - hood.HOOD_POS_THE_6) < 6000) {
			System.out.println("</Hood Moving To Outerworks Position>");
			this.isFinished = true;
		}

	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoCommandHoodTo610Position();
	}

}
