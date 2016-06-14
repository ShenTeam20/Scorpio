package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

public class T20AutoCommandHoodToOuterworksPosition extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;

	public T20AutoCommandHoodToOuterworksPosition() {
		this.isFinished = false;
		this.isStarted = false;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Hood Moving To Outerworks Position>");
			isStarted = !isStarted;
		}
		hood.moveHoodPositon(hood.HOOD_POS_OUTERWORKS);
		if (Math.abs(hood.getHoodEnc() - hood.HOOD_POS_OUTERWORKS) < 800) {
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
		return new T20AutoCommandHoodToOuterworksPosition();
	}

}
