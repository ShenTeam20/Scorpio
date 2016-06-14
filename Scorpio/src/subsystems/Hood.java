package subsystems;

import org.usfirst.frc.team20.robot.Constants;
import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.GeneralPIDExecutor;
import org.usfirst.frc.team20.robot.Team20Libraries.T20CANTalon;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;

public class Hood extends Scorpio {

	private T20CANTalon hoodTalon = new T20CANTalon(Constants.HOOD_MOTOR_PORT);
	private DigitalInput hoodHomeSensor = new DigitalInput(Constants.HOOD_PROXIMITY_HOMING_PORT);

	private GeneralPIDExecutor autoTargetHoodPID = new GeneralPIDExecutor(-.2, .2, -125, 120, false);

	public final double HOOD_POS_OUTERWORKS = -250000, HOOD_POS_BATTER = -310000, HOOD_POS_SAFE = -90000,
			HOOD_POS_THE_6 = -290000, HOOD_POS_OFFSET_AUTO = 0, HOOD_POS_HOME = 1500;

	public boolean hoodIsActuallyHomed = false;
	private double hoodOperationCurrentLimit = 10;

	public Hood() {
		hoodTalon.subsystemName = "Hood";
		hoodTalon.changeControlMode(TalonControlMode.Position);
		hoodTalon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		hoodTalon.setPosition(0);
		hoodTalon.setPID(.01, 0, 0);
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

	public void setHoodHomeState(boolean bool) {
		hoodTalon.homed = bool;
	}

	public void enableHoodControl() {
		hoodTalon.enableControl();
	}

	public void hoodHomeWatchDog() {
		if (!hoodIsActuallyHomed) {
			hoodTalon.setPID(.5, 0, 0);
			hoodTalon.setPosition(0);
			hoodTalon.set(3000);
		}
		if (!hoodHomeSensor.get() && !hoodIsActuallyHomed) {
			setHoodHomeState(true);
			hoodTalon.setPID(.01, 0, 0);
			hoodTalon.setPosition(0);
			operator.hoodPositonHolder = 0;
			hoodIsActuallyHomed = true;
		}
	}

}