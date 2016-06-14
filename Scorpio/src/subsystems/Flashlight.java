package subsystems;

import org.usfirst.frc.team20.robot.Constants;

import edu.wpi.first.wpilibj.Relay;

public class Flashlight {

	Relay flashlightSpike = new Relay(Constants.FLASHLIGHT_SPIKE_PORT);

	public Flashlight() {

	}

	public void flashlightOn() {
		flashlightSpike.set(Relay.Value.kForward);
	}

	public void flashlightOff() {
		flashlightSpike.set(Relay.Value.kOff);
	}
}
