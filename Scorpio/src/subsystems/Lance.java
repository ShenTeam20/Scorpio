package subsystems;

import org.usfirst.frc.team20.robot.Constants;
import org.usfirst.frc.team20.robot.Team20Libraries.PneumaticToggler;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class Lance {

	private PneumaticToggler leftToggle = new PneumaticToggler();
	private PneumaticToggler rightToggle = new PneumaticToggler();

	private Talon collectorMotor = new Talon(Constants.COLLECTOR_MOTOR_PORT);
	private Talon collectorMotor2 = new Talon(Constants.SECOND_COLLECTOR_MOTOR_PORT);

	private Solenoid leftExtend = new Solenoid(Constants.LANCE_LEFT_EXTEND_SOLENOID_PORT);
	private Solenoid leftRetract = new Solenoid(Constants.LANCE_LEFT_RETRACT_SOLENOID_PORT);
	private Solenoid rightExtend = new Solenoid(Constants.LANCE_RIGHT_EXTEND_SOLENOID_PORT);
	private Solenoid rightRetract = new Solenoid(Constants.LANCE_RIGHT_RETRACT_SOLENOID_PORT);
	private DoubleSolenoid lockingPistons = new DoubleSolenoid(Constants.LANCE_LOCKING_PISTONS_FIRST_SOLENOID_PORT,
			Constants.LANCE_LOCKING_PISTONS_SECOND_SOLENOID_PORT);

	private DigitalInput magSwitchLeft = new DigitalInput(Constants.MAG_SWITCH_LEFT_PORT);
	private DigitalInput magSwitchRight = new DigitalInput(Constants.MAG_SWITCH_RIGHT_PORT);
	private DigitalInput magSwitchIsExtened = new DigitalInput(Constants.MAG_SWITCH_IS_EXTENED_PORT);

	public Lance() {

	}

	public boolean getMagSwitchIsExtened() {
		return magSwitchIsExtened.get();
	}

	public void lockIt() {
		lockingPistons.set(DoubleSolenoid.Value.kForward);
	}

	public void unlockIt() {
		lockingPistons.set(DoubleSolenoid.Value.kReverse);
	}

	public void lanceMovementWatchDog() {
		leftToggle.limitSwitch = magSwitchLeft.get();
		rightToggle.limitSwitch = magSwitchRight.get();
		leftToggle.toggle();
		leftExtend.set(leftToggle.extend);
		leftRetract.set(leftToggle.retract);
		rightToggle.toggle();
		rightExtend.set(rightToggle.extend && magSwitchRight.get());
		rightRetract.set(rightToggle.retract);
		if (!magSwitchIsExtened.get() && leftToggle.extend && rightToggle.extend) {
			lockIt();
		} else {
			unlockIt();
		}
	}

	public void toggleLance() {
		leftToggle.toggleToggle();
		rightToggle.toggleToggle();
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
