package subsystems;

import org.usfirst.frc.team20.robot.Constants;
import org.usfirst.frc.team20.robot.Scorpio;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Indexer extends Scorpio {

	private Talon indexerTalon = new Talon(Constants.INDEXER_MOTOR_PORT);
	private DigitalInput indexerBumpSwitch = new DigitalInput(Constants.INDEXER_BUMP_SWITCH_PORT);
	private boolean isIntaking = false;

	public Indexer() {
	}

	public void intakeIndexer(boolean shouldStop) {
		indexerTalon.set(-.7);
		if (shouldStop) {
			isIntaking = true;
			SmartDashboard.putBoolean(" Collector", true);
		} else {
			isIntaking = false;
		}
	}

	public void backdriveIndexer() {
		indexerTalon.set(1);
		isIntaking = false;
		SmartDashboard.putBoolean(" Collector", true);
	}

	public void stopIndexer() {
		indexerTalon.set(0);
		isIntaking = false;
		SmartDashboard.putBoolean(" Collector", false);
	}

	public void indexerBumpSwitchWatchDog() {
		if (!indexerBumpSwitch.get() && isIntaking) {
			stopIndexer();
			lance.stopIntake();
		}
	}

	public boolean getIndexerBumpSwitch() {
		return !indexerBumpSwitch.get();
	}
}
