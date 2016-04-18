package subsystems;

import org.usfirst.frc.team20.robot.Constants;
import org.usfirst.frc.team20.robot.Team20Libraries.GeneralPIDExecutor;
import org.usfirst.frc.team20.robot.Team20Libraries.T20CANTalon;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class Hood {

	// 560783
	// 537000
	public final double HOOD_POS_OUTERWORKS = 555000, HOOD_POS_BATTER = 646000, HOOD_POS_SAFE = 252346,
			HOOD_POS_THE_6 = 620000;
	public boolean hoodIsActuallyHomed = false;
	private double hoodCurrent = 0;
	private double hoodOperationCurrentLimit = 10;
	private double hoodHomingCurrentLimit = 2;

	private T20CANTalon hoodTalon = new T20CANTalon(Constants.HOOD_MOTOR_PORT);

	private GeneralPIDExecutor autoTargetHoodPID = new GeneralPIDExecutor(-.2, .2, -125, 120, false);

	public Hood() {
		hoodTalon.subsystemName = "Hood";
		hoodTalon.changeControlMode(TalonControlMode.Position);
		hoodTalon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		hoodTalon.setPosition(0);
		hoodTalon.setPID(.05, 0, 0);
		autoTargetHoodPID.start();
		hoodTalon.homed = true;
		hoodTalon.currentLimit = this.hoodOperationCurrentLimit;
		hoodTalon.enableControl();
		hoodTalon.run();
	}

	public void setAutoTargetHoodPID(double p, double i, double d) {
		autoTargetHoodPID.setPID(p, i, d);
	}

	public void setAutoTargetHood_PV_SP(double pv, double sp) {
		autoTargetHoodPID.setPV(pv);
		autoTargetHoodPID.setSP(sp);
	}

	public double getAutoTargetHood_OP() {
		return autoTargetHoodPID.getOP();
	}

	public void moveHoodPVbus(double speed) {
		hoodTalon.changeControlMode(TalonControlMode.PercentVbus);
		hoodTalon.set(speed);
	}

	public void moveHoodPositon(double positon) {
		hoodTalon.changeControlMode(TalonControlMode.Position);
		hoodTalon.set(positon);
	}

	public double getHoodEnc() {
		return hoodTalon.getEncPosition();
	}

	public boolean getHoodHomeState() {
		return hoodTalon.homed;
	}

	public void enableHoodControl() {
		hoodTalon.enableControl();
	}

	public void homeHood() {
		hoodTalon.homed = false;
		hoodTalon.changeControlMode(TalonControlMode.Position);
	}

	public void hoodHomeWatchdog() {
		hoodCurrent = (hoodCurrent * .99) + (hoodTalon.getOutputCurrent() * .01);
		if (!hoodTalon.homed) {
			hoodTalon.setPID(.05, 0, 0);
			hoodTalon.setPosition(0);
			hoodTalon.set(-11000);

			if (hoodCurrent > hoodHomingCurrentLimit) {
				hoodTalon.setPosition(0);
				hoodTalon.set(4000);
				hoodTalon.homed = true;
			}
		}
	}

}