package subsystems;

import org.usfirst.frc.team20.robot.Constants;

import edu.wpi.first.wpilibj.Talon;

public class Lance {

	private Talon collectorMotor = new Talon(Constants.COLLECTOR_MOTOR_PORT);
	private Talon collectorMotor2 = new Talon(Constants.SECOND_COLLECTOR_MOTOR_PORT);

	public Lance() {

	}

	public void intakeLance() {
		collectorMotor.set(1);
		collectorMotor2.set(1);
	}

	public void backDrive() {
		collectorMotor.set(-1);
		collectorMotor2.set(-1);
	}

	public void stopIntake() {
		collectorMotor.set(0);
		collectorMotor2.set(0);
	}
}
