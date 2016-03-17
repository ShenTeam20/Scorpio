package auto;

/**
 * @author Team 20
 */
import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.REVDigitBoard;

public class AutoModeChooser extends Scorpio {

	private REVDigitBoard revDB = new REVDigitBoard();
	private double revDispNum = 00.00;
	private boolean shouldChange = true;
	public String temp = "";

	public AutoModeChooser() {

	}

	public void displayRevBoardNum() {
		revDB.display(revDispNum);
	}

	public void displayRevBoardName() {
		temp = Double.toString(revDispNum);
		if (revDispNum < .01) {
			temp = "0.00";
		} else {
			temp = temp.substring(0, 4);
		}
		System.out.println("                           " + temp);

		switch (temp) {
		case "0.00":
			revDB.display("NTHN");
			break;
		case "0.01":
			revDB.display("CREX");
			break;
		case "0.02":
			revDB.display("CRRE");
			break;
		case "0.03":
			revDB.display("REH2");
			break;
		case "0.04":
			revDB.display("REH3");
			break;
		case "0.05":
			revDB.display("REH4");
			break;
		case "0.06":
			revDB.display("REH5");
			break;
		case "0.07":
			revDB.display("EXH1");
			break;
		case "0.08":
			revDB.display("EXH2");
			break;
		case "0.09":
			revDB.display("EXH3");
			break;
		case "0.10":
			revDB.display("EXH4");
			break;
		case "0.11":
			revDB.display("EXH5");
			break;
		case "0.12":
			revDB.display("REACH");
			break;
		case "0.20":
			revDB.display(" HSB");
			break;

		default:
			break;
		}
	}

	public void clearRevBoard() {
		revDB.clear();
	}

	public void revBoardHandler() {
		if (revDB.getButtonA() && shouldChange) {
			if (revDispNum == .13) {
				revDispNum = .14;
			} else {
				revDispNum += .01;
			}
		}
		if (revDB.getButtonB() && revDispNum > .00 && shouldChange) {
			if (revDispNum == .03) {
				revDispNum = .02;
			} else if (revDispNum == .15) {
				revDispNum = .14;
			} else {
				revDispNum -= .01;
			}
		}
		if (revDB.getButtonB() && revDB.getButtonA() && shouldChange) {
			revDispNum = .00;
			clearRevBoard();
		}
		if (getPotVolt() > .28) {
			displayRevBoardName();
			shouldChange = false;
		} else {
			displayRevBoardNum();
			shouldChange = true;
		}
	}

	public double getDispNum() {
		return revDispNum;
	}

	public double getPotVolt() {
		return revDB.getPot();
	}

}
