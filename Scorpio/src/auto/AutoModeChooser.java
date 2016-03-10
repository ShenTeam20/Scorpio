package auto;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.REVDigitBoard;

public class AutoModeChooser extends Scorpio {

	REVDigitBoard revDB = new REVDigitBoard();
	double revDispNum = 00.00;

	public AutoModeChooser() {

	}

	public void RevBoardHandler() {
		if (revDB.getButtonA()) {
			revDispNum += .01;
		}
		if (revDB.getButtonB()) {
			revDispNum -= .01;
		}
	}

	public double getDispNum() {
		return revDispNum;
	}

}
