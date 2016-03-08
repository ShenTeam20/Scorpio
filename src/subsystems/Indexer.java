package subsystems;

import org.usfirst.frc.team20.robot.Constants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

public class Indexer {

	private Talon indexer = new Talon(Constants.INDEXER_MOTOR_PORT);
	private DigitalInput indexerBumpSwitch = new DigitalInput(Constants.INDEXER_BUMP_SWITCH_PORT);

	public Indexer() {
	}

	public void intakeIndexer() {
		indexer.set(-1);
	}

	public void backdriveIndexer() {
		indexer.set(1);
	}

	public void stopIndexer() {
		indexer.set(0);
	}

	public void watchIndexerBumpSwitch() {
		if (!indexerBumpSwitch.get()) {
			stopIndexer();
		}
	}
}
