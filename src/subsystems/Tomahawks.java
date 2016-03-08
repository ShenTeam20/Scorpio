package subsystems;

import org.usfirst.frc.team20.robot.Constants;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Tomahawks {

	private DoubleSolenoid tomahawkPistons = new DoubleSolenoid(Constants.TOMAHAWK_FIRST_SOLENOID_PORT,
			Constants.TOMAHAWK_SECOND_SOLENOID_PORT);

	public Tomahawks() {
	}

	public void retractTomahawks() {
		tomahawkPistons.set(DoubleSolenoid.Value.kReverse);
	}

	public void actuateTomahawks() {
		tomahawkPistons.set(DoubleSolenoid.Value.kForward);
	}

	public void neturalTomahawks() {
		tomahawkPistons.set(DoubleSolenoid.Value.kOff);
	}
}
