package auto;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Node;
import org.usfirst.frc.team20.robot.Team20Libraries.T20ParallelNode;
import org.usfirst.frc.team20.robot.Team20Libraries.T20SeriesNode;

import autoCommands.T20AutoCommandDelaySeconds;
import autoCommands.T20AutoCommandDoNothing;
import autoCommands.T20AutoCommandDriveStraightFeetLeft;
import autoCommands.T20AutoCommandDriveStraightFeetRight;
import autoCommands.T20AutoCommandFlywheelToSpeed;
import autoCommands.T20AutoCommandHoodTo610Position;
import autoCommands.T20AutoCommandHoodToLowPosition;
import autoCommands.T20AutoCommandHoodToOuterworksPosition;
import autoCommands.T20AutoCommandHoodToSafePosition;
import autoCommands.T20AutoCommandIntakeBackDrive;
import autoCommands.T20AutoCommandIntakeIntake;
import autoCommands.T20AutoCommandIntakeStop;
import autoCommands.T20AutoCommandIntakeWaitForBall;
import autoCommands.T20AutoCommandLanceDown;
import autoCommands.T20AutoCommandLanceUp;
import autoCommands.T20AutoCommandLanceWatchDog;
import autoCommands.T20AutoCommandRobotShutdown;
import autoCommands.T20AutoCommandToggleLance;
import autoCommands.T20AutoCommandTomahawksDown;
import autoCommands.T20AutoCommandTomahawksUp;
import autoCommands.T20AutoCommandTurnToAngle;
import edu.wpi.first.wpilibj.DriverStation;

public class AutoModesV2 extends Scorpio {

	private T20Node doNothingNode;

	/**
	 * Does Nothing<br>
	 * <br>
	 * 
	 * @return Seriously Does Nothing
	 */

	private void createNothing() {
		doNothingNode = new T20SeriesNode();
		doNothingNode.addChild(new T20AutoCommandDoNothing());
	}

	/**
	 * Does Nothing<br>
	 * <br>
	 * 
	 * @return Seriously Does Nothing
	 */

	private void executeNothing() {
		doNothingNode.execute();
	}

	private T20Node rollOutTree;

	/**
	 * Sets up robot to go under the low bar or portcullis<br>
	 * <br>
	 * 
	 * @return a rollOut node
	 */

	private void createAutoBotsTransformRollOut() {
		rollOutTree = new T20SeriesNode();
		rollOutTree.addChild(new T20AutoCommandTomahawksDown());
		rollOutTree.addChild(new T20AutoCommandHoodToLowPosition());
		rollOutTree.addChild(new T20AutoCommandToggleLance());
		T20Node secondaryRollOut = new T20ParallelNode();
		secondaryRollOut.addChild(new T20AutoCommandLanceDown());
		secondaryRollOut.addChild(new T20AutoCommandHoodToLowPosition());
		rollOutTree.addChild(secondaryRollOut);
	}

	/**
	 * Sets up robot to go under the low bar or portcullis<br>
	 * <br>
	 * 
	 * @return runs a rollOut node
	 */

	private void executeAutoBotsTransformRollOut() {
		rollOutTree.execute();
	}

	private T20Node concealTree;

	/**
	 * Sets up robot to go over B and D defenses<br>
	 * <br>
	 * 
	 * @return a conceal node
	 */

	private void createAutoBotsTransformConceal() {
		concealTree = new T20SeriesNode();
		concealTree.addChild(new T20AutoCommandHoodToSafePosition());
		concealTree.addChild(new T20AutoCommandToggleLance());
		T20Node secondaryTranformConceal = new T20ParallelNode();
		secondaryTranformConceal.addChild(new T20AutoCommandLanceUp());
		secondaryTranformConceal.addChild(new T20AutoCommandTomahawksUp());
		concealTree.addChild(secondaryTranformConceal);
	}

	/**
	 * Sets up robot to go over B and D defenses<br>
	 * <br>
	 * 
	 * @return runs a conceal node
	 */

	private void executeAutoBotsTransformConceal() {
		concealTree.execute();
	}

	private T20Node systemCheckTree;

	/**
	 * Puts the robot through a system check<br>
	 * <br>
	 * 
	 * @return a system check node
	 */

	private void createSystemCheck() {
		systemCheckTree = new T20SeriesNode();
		createAutoBotsTransformRollOut();
		createAutoBotsTransformConceal();
		systemCheckTree.addChild(rollOutTree);
		systemCheckTree.addChild(new T20AutoCommandHoodToOuterworksPosition());
		systemCheckTree.addChild(new T20AutoCommandFlywheelToSpeed(1000));
		systemCheckTree.addChild(new T20AutoCommandFlywheelToSpeed(flywheel.FLYSPEED_STOP));
		systemCheckTree.addChild(new T20AutoCommandDriveStraightFeetLeft(.5, .5));
		systemCheckTree.addChild(new T20AutoCommandDriveStraightFeetRight(.5, .5));
		systemCheckTree.addChild(concealTree);

	}

	/**
	 * Puts the robot through a system check<br>
	 * <br>
	 * 
	 * @return runs a system check node
	 */

	private void executeSystemCheck() {
		systemCheckTree.execute();
	}

	private T20Node batterAutoPos3Node;

	/**
	 * Drives the robot t0 the batter and high goals<br>
	 * <br>
	 * 
	 * @return a batter high goal auto
	 */

	public void createPosThreeBatterAuto() {
		batterAutoPos3Node = new T20SeriesNode();
		batterAutoPos3Node.addChild(new T20AutoCommandLanceWatchDog());
		batterAutoPos3Node.addChild(new T20AutoCommandDriveStraightFeetLeft(.5, 11));
		batterAutoPos3Node.addChild(new T20AutoCommandTurnToAngle(30));
		batterAutoPos3Node.addChild(new T20AutoCommandDriveStraightFeetLeft(.3, 2));
		batterAutoPos3Node.addChild(new T20AutoCommandTurnToAngle(-30));
		batterAutoPos3Node.addChild(new T20AutoCommandDriveStraightFeetLeft(.3, 5.7));
		T20Node startShot = new T20ParallelNode();
		createAutoBotsTransformRollOut();
		startShot.addChild(rollOutTree);
		startShot.addChild(new T20AutoCommandFlywheelToSpeed(flywheel.FLYSPEED_OUTERWORKS));
		batterAutoPos3Node.addChild(startShot);
		batterAutoPos3Node.addChild(new T20AutoCommandHoodTo610Position());
		batterAutoPos3Node.addChild(new T20AutoCommandIntakeIntake());
	}

	/**
	 * Drives the robot to the batter and high goals<br>
	 * <br>
	 * 
	 * @return Nothing
	 */

	public void executePosThreeBatterAuto() {
		batterAutoPos3Node.execute();
	}

	private T20Node mainAuto;

	/**
	 * Creates the auto mode based on the smartdashboard choice<br>
	 * <br>
	 * 
	 * @return Nothing
	 */
	public void createAuto(String mode) {
		mainAuto = new T20SeriesNode();
		mainAuto.addChild(new T20AutoCommandLanceWatchDog());
		switch (mode) {
		case "nothing":
			createNothing();
			mainAuto.addChild(new T20AutoCommandDriveStraightFeetLeft(-.4, 2));
			break;
		case "pos1cross":
			createAutoBotsTransformRollOut();
			mainAuto.addChild(rollOutTree);
			mainAuto.addChild(new T20AutoCommandDriveStraightFeetLeft(.5, 12));
			break;
		case "pos2to5cross":
			mainAuto.addChild(new T20AutoCommandDriveStraightFeetLeft(1, 14));
			break;
		case "pos2to5crossreturn":
			mainAuto.addChild(new T20AutoCommandDriveStraightFeetLeft(.5, 13));
			mainAuto.addChild(new T20AutoCommandTurnToAngle(-15));
			mainAuto.addChild(new T20AutoCommandDelaySeconds(.1));
			mainAuto.addChild(new T20AutoCommandIntakeBackDrive());
			mainAuto.addChild(new T20AutoCommandDelaySeconds(.5));
			mainAuto.addChild(new T20AutoCommandIntakeStop());
			mainAuto.addChild(new T20AutoCommandDelaySeconds(.1));
			mainAuto.addChild(new T20AutoCommandTurnToAngle(15));
			mainAuto.addChild(new T20AutoCommandDelaySeconds(.2));
			mainAuto.addChild(new T20AutoCommandToggleLance());
			mainAuto.addChild(new T20AutoCommandLanceDown());
			mainAuto.addChild(new T20AutoCommandDriveStraightFeetLeft(-.5, 14));
			mainAuto.addChild(new T20AutoCommandIntakeWaitForBall());
			if (DriverStation.getInstance().getMatchTime() > 5 && indexer.getIndexerBumpSwitch()) {
				mainAuto.addChild(new T20AutoCommandDriveStraightFeetLeft(1, 14));
			}
			break;
		case "pos2battershot":

			break;
		case "pos3battershot":
			createPosThreeBatterAuto();
			mainAuto.addChild(batterAutoPos3Node);
			break;
		case "pos4battershot":

			break;
		case "pos5battershot":
			mainAuto.addChild(new T20AutoCommandDriveStraightFeetLeft(.8, 11));
			mainAuto.addChild(new T20AutoCommandDriveStraightFeetLeft(.5, 10));
			mainAuto.addChild(new T20AutoCommandTurnToAngle(-25));
			mainAuto.addChild(new T20AutoCommandDriveStraightFeetLeft(.4, .5));
			T20Node startShot = new T20ParallelNode();
			createAutoBotsTransformRollOut();
			startShot.addChild(rollOutTree);
			startShot.addChild(new T20AutoCommandFlywheelToSpeed(flywheel.FLYSPEED_OUTERWORKS));
			mainAuto.addChild(startShot);
			mainAuto.addChild(new T20AutoCommandHoodTo610Position());
			mainAuto.addChild(new T20AutoCommandIntakeIntake());
			break;
		case "pos3outerworksshot":

			break;
		case "pos4outerworksshot":

			break;
		case "pos5steal":

			break;

		default:
			createNothing();
			mainAuto.addChild(doNothingNode);
			break;
		}
		mainAuto.addChild(new T20AutoCommandRobotShutdown());
	}

	/**
	 * Executes the auto mode based on the smartdashboard choice<br>
	 * <br>
	 * 
	 * @return Nothing
	 */
	public void executeMainAuto() {
		mainAuto.execute();
	}
}
