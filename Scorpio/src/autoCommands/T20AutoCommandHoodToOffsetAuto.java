package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

public class T20AutoCommandHoodToOffsetAuto extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;
	private double hoodSetpoint;

	public T20AutoCommandHoodToOffsetAuto() {
		this.isFinished = false;
		this.isStarted = false;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Hood Moving To Offset Position>");
			hoodSetpoint = hood.HOOD_POS_OFFSET_AUTO + hood.getHoodEnc();
			isStarted = !isStarted;
		}
		hood.moveHoodPositon(hoodSetpoint);
		if (Math.abs(hood.getHoodEnc() - hoodSetpoint) < 900) {
			System.out.println("</Hood Moving To Offset Position>");
			this.isFinished = true;
		}

	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoCommandHoodToOffsetAuto();
	}

}
