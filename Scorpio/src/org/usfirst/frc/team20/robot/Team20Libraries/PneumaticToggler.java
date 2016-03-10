package org.usfirst.frc.team20.robot.Team20Libraries;

public class PneumaticToggler {

	public boolean extend = true, retract;
	public boolean limitSwitch;

	public PneumaticToggler() {
	}

	public void toggle() {
		//
		if (!limitSwitch)
			extend = true;
		retract = !extend;
	}

	public void toggleToggle() {
		extend = false;
	}

}
