package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

import edu.wpi.first.wpilibj.DriverStation;

public class T20AutoCommandRobotShutdown extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;

	public T20AutoCommandRobotShutdown() {
		this.isFinished = false;
		this.isStarted = false;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Drivetrain Stopping: " + ">");
			drivetrain.setRobotCentric();
			isStarted = !isStarted;
		}
		if (DriverStation.getInstance().getMatchTime() < .1) {
			drivetrain.driveTrainStopMethod();
			indexer.stopIndexer();
			lance.stopIntake();
			flywheel.flywheelToSpeed(flywheel.FLYSPEED_STOP);

			System.out.println("</Drivetrain Stopping: " + ">");
			this.isFinished = true;
		}
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoCommandRobotShutdown();
	}

}
