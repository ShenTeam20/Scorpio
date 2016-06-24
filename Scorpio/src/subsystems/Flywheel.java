package subsystems;

import org.usfirst.frc.team20.robot.Constants;
import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20CANTalon;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class Flywheel extends Scorpio {

	// 3200 batter prev
	//2150
	public final double FLYSPEED_OUTERWORKS = 3750, FLYSPEED_BATTER = 2100, FLYSPEED_STOP = 0;

	private T20CANTalon flywheelTalon = new T20CANTalon(Constants.FLYWHEEL_MOTOR_PORT);

	public Flywheel() {
		flywheelTalon.subsystemName = "Flywheel";
		flywheelTalon.changeControlMode(TalonControlMode.Speed);
		flywheelTalon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		flywheelTalon.reverseSensor(true);
		flywheelTalon.configNominalOutputVoltage(+0.0f, -0.0f);
		flywheelTalon.configPeakOutputVoltage(+12.0f, -12.0f);
		flywheelTalon.setPosition(0);
		flywheelTalon.configEncoderCodesPerRev(25);
		flywheelTalon.setPID(5.0, 0.00001, 0.00001);
		flywheelTalon.enableControl();
	}

	public void flywheelToSpeed(double speed) {
		if (speed <= 200) {
			flywheelTalon.disableControl();
		} else {
			flywheelTalon.enableControl();
			flywheelTalon.set(speed);
		}
	}

	public void fire() {
		indexer.intakeIndexer(false);
		lance.intakeLance();
	}

	public double getSpeed() {
		System.out.println("Flywheel RPM: " + (flywheelTalon.getSpeed() * 72) / 22);
		return (flywheelTalon.getSpeed() * 72) / 22;
	}

	public double getFlyEnc() {
		return flywheelTalon.getEncPosition();
	}

}
