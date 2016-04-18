package subsystems;

import org.usfirst.frc.team20.robot.Constants;
import org.usfirst.frc.team20.robot.Scorpio;

import edu.wpi.first.wpilibj.Talon;

public class SystematicAcension_by_LiftingTechnology extends Scorpio {

	private Talon saltTalonOne = new Talon(Constants.SALT_ONE_PORT);
	private Talon saltTalonTwo = new Talon(Constants.SALT_TWO_PORT);

	public SystematicAcension_by_LiftingTechnology() {

	}

	public void addWater() {
		saltTalonOne.set(0);
		saltTalonTwo.set(0);
	}

	public void addSalt() {
		saltTalonOne.set(.5);
		saltTalonTwo.set(.5);
	}

}
