package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class T20AutoAutoTarget extends Scorpio implements T20Command {
	private boolean isFinished, isStarted, fireTimerStarted;
	private Timer fireTimer = new Timer();
	private long sysTime = 0;

	public T20AutoAutoTarget() {
		this.isFinished = false;
		this.isStarted = false;
		this.fireTimerStarted = false;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}
		if (!isStarted) {
			System.out.println("<Auto Targeting" + ">");
			drivetrain.setCameraTargetMode();
			isStarted = !isStarted;
		}
		drivetrain.drive(0, ahrs.ahrs.getAngle());
		System.out
				.println("                                           headingoffset:  " + drivetrain.getHeadingOffSet());

		if (!(drivetrain.getHeadingOffSet() > 0 && drivetrain.getHeadingOffSet() < 1.5)) {
			sysTime = System.currentTimeMillis();
		}

		if (drivetrain.getHeadingOffSet() > 0 && drivetrain.getHeadingOffSet() < 1.5
				&& System.currentTimeMillis() > sysTime + 500) {
			System.out.println("</Auto Targeting" + ">");
			flywheel.flywheelToSpeed(flywheel.FLYSPEED_OUTERWORKS);
		}

		if (flywheel.getSpeed() > 8000) {
			flywheel.fire();
			if (!fireTimerStarted) {
				fireTimer.start();
				fireTimerStarted = true;
			}

		}
		if (DriverStation.getInstance().getMatchTime() < .5) {
			System.out.println("</Auto Firing>");
			lance.stopIntake();
			indexer.stopIndexer();
			flywheel.flywheelToSpeed(flywheel.FLYSPEED_STOP);
			drivetrain.setFieldCentric();
			this.isFinished = true;
		}
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoAutoTarget();
	}

}
