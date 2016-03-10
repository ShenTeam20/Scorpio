package subsystems;

import org.usfirst.frc.team20.robot.Constants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

public class Indexer {

	private Talon indexerTalon = new Talon(Constants.INDEXER_MOTOR_PORT);
	private DigitalInput indexerBumpSwitch = new DigitalInput(Constants.INDEXER_BUMP_SWITCH_PORT);

	public Indexer() {
	}

	public void intakeIndexer() {
		indexerTalon.set(-1);
	}

	public void backdriveIndexer() {
		indexerTalon.set(1);
	}

	public void stopIndexer() {
		indexerTalon.set(0);
	}

	public void indexerBumpSwitchWatchDog() {
		if (!indexerBumpSwitch.get()) {
			stopIndexer();
		}
	}
}
