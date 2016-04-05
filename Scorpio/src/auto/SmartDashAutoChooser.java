package auto;

import org.usfirst.frc.team20.robot.Scorpio;

public class SmartDashAutoChooser extends Scorpio {
	private int pos;
	private boolean doExtend, shouldHighGoal;

	public SmartDashAutoChooser() {

	}

	public void setAutoModePos(int pos) {
		this.pos = pos;
	}

	public void createAutoMode(int pos, boolean doExtend, boolean shouldHighGoal) {
		this.pos = pos;
		this.doExtend = doExtend;
		this.shouldHighGoal = shouldHighGoal;
		autoModes.createMainAutoMode(this.pos, this.doExtend, this.shouldHighGoal);
	}

}
