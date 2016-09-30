package auto;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Node;
import org.usfirst.frc.team20.robot.Team20Libraries.T20ParallelNode;
import org.usfirst.frc.team20.robot.Team20Libraries.T20SeriesNode;

import autoCommands.*;

public class AutoModes extends Scorpio {

	private double initialHeading = 0;

	private T20Node systemCheckTree;

	/**
	 * Puts the robot through a system check<br>
	 * <br>
	 * 
	 * @return a system check node
	 */

	public void createSystemCheck() {
		systemCheckTree = new T20SeriesNode();
		createAutoBotsTransformRollOut();
		createAutoBotsTransformConceal();
		systemCheckTree.addChild(rollOutTree);
		systemCheckTree.addChild(new T20AutoCommandHoodToOuterworksPosition());
		systemCheckTree.addChild(new T20AutoCommandFlywheelToSpeed(1000));
		systemCheckTree.addChild(new T20AutoCommandFlywheelToSpeed(flywheel.FLYSPEED_STOP));
		systemCheckTree.addChild(new T20AutoCommandDriveStraightTime(.4, 3));
		systemCheckTree.addChild(new T20AutoCommandDriveStraightTime(-.4, 3));
		systemCheckTree.addChild(transformConcealTree);

	}

	/**
	 * Puts the robot through a system check<br>
	 * <br>
	 * 
	 * @return a system check node
	 */

	public void executeSystemCheck() {
		systemCheckTree.execute();
	}

	private T20Node specialRollOutTree;

	/**
	 * Sets up robot to go under the low bar or portcullis<br>
	 * <br>
	 * 
	 * @return a rollOut node
	 */

	public void createAutoBotsTransformSpecialRollOut() {
		specialRollOutTree = new T20SeriesNode();
		specialRollOutTree.addChild(new T20AutoCommandTomahawksDown());
		specialRollOutTree.addChild(new T20AutoCommandToggleLance());
		T20Node specialSecondaryRollOut = new T20ParallelNode();
		specialSecondaryRollOut.addChild(new T20AutoCommandLanceDown());
		specialRollOutTree.addChild(specialSecondaryRollOut);
	}

	/**
	 * Sets up robot to go under the low bar or portcullis<br>
	 * <br>
	 * 
	 * @return a rollOut node
	 */

	public void executeAutoBotsTransformSpecialRollOut() {
		rollOutTree.execute();
	}

	private T20Node rollOutTree;

	/**
	 * Sets up robot to go under the low bar or portcullis<br>
	 * <br>
	 * 
	 * @return a rollOut node
	 */

	public void createAutoBotsTransformRollOut() {
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
	 * @return a rollOut node
	 */

	public void executeAutoBotsTransformRollOut() {
		rollOutTree.execute();
	}

	private T20Node transformConcealTree;

	/**
	 * Sets up robot to go over B and D defenses<br>
	 * <br>
	 * 
	 * @return a conceal node
	 */

	public void createAutoBotsTransformConceal() {
		transformConcealTree = new T20SeriesNode();
		transformConcealTree.addChild(new T20AutoCommandHoodToSafePosition());
		transformConcealTree.addChild(new T20AutoCommandToggleLance());
		T20Node secondaryTranformConceal = new T20ParallelNode();
		secondaryTranformConceal.addChild(new T20AutoCommandLanceUp());
		secondaryTranformConceal.addChild(new T20AutoCommandTomahawksUp());
		transformConcealTree.addChild(secondaryTranformConceal);
	}

	/**
	 * Sets up robot to go over B and D defenses<br>
	 * <br>
	 * 
	 * @return a conceal node
	 */

	public void executeAutoBotsTransformConceal() {
		transformConcealTree.execute();
	}

	private T20Node lowBarTree;

	/**
	 * Drives the robot through the low bar and high goals<br>
	 * <br>
	 * 
	 * @return a low bar high goal auto
	 */

	public void createLowBar() {
		createAutoBotsTransformRollOut();
		lowBarTree = new T20SeriesNode();
		lowBarTree.addChild(rollOutTree);
		lowBarTree.addChild(new T20AutoCommandDriveStraightTime(1, 2));

	}

	/**
	 * Drives the robot through the low bar and high goals<br>
	 * <br>
	 * 
	 * @return a low bar high goal auto
	 */
	public void executeLowBar() {
		lowBarTree.execute();
	}

	private T20Node batterAutoTree;

	/**
	 * Drives the robot through the batter and high goals<br>
	 * <br>
	 * 
	 * @return a batter high goal auto
	 */

	public void createPosThreeBatterAuto() {
		batterAutoTree = new T20SeriesNode();
		batterAutoTree.addChild(new T20AutoCommandLanceWatchDog());
		batterAutoTree.addChild(new T20AutoCommandDriveStraightFeetLeft(.5, 11));
		batterAutoTree.addChild(new T20AutoCommandTurnToAngle(30));
		batterAutoTree.addChild(new T20AutoCommandDriveStraightFeetLeft(.3, 2));
		batterAutoTree.addChild(new T20AutoCommandTurnToAngle(-30));
		batterAutoTree.addChild(new T20AutoCommandDriveStraightFeetLeft(.3, 5.7));
		T20Node startShot = new T20ParallelNode();
		createAutoBotsTransformRollOut();
		startShot.addChild(rollOutTree);
		startShot.addChild(new T20AutoCommandFlywheelToSpeed(flywheel.FLYSPEED_OUTERWORKS));
		batterAutoTree.addChild(startShot);
		batterAutoTree.addChild(new T20AutoCommandHoodTo610Position());
		batterAutoTree.addChild(new T20AutoCommandIntakeIntake());
	}

	/**
	 * Drives the robot through the low bar and high goals<br>
	 * <br>
	 * 
	 * @return a batter high goal auto
	 */

	public void executePosThreeBatterAuto() {
		batterAutoTree.execute();
	}

	private T20Node lowBarHighGoalTree;

	/**
	 * Drives the robot through the low bar and high goals<br>
	 * <br>
	 * 
	 * @return a low bar high goal auto
	 */

	public void createLowBarHighGoal() {
		createAutoBotsTransformRollOut();
		lowBarHighGoalTree = new T20SeriesNode();
		lowBarHighGoalTree.addChild(rollOutTree);
		lowBarHighGoalTree.addChild(new T20AutoCommandDriveStraightTime(1, 1.7));
		lowBarHighGoalTree.addChild(new T20AutoCommandArcTurnToAngle(.5, 7));
		lowBarHighGoalTree.addChild(new T20AutoCommandArcTurnToAngle(0, 33));
		lowBarHighGoalTree.addChild(new T20AutoCommandHoodToOuterworksPosition());
		lowBarHighGoalTree.addChild(new T20AutoAutoTarget());
	}

	/**
	 * Drives the robot through the low bar and high goals<br>
	 * <br>
	 * 
	 * @return a low bar high goal auto
	 */

	public void executeLowBarHighGoal() {
		lowBarHighGoalTree.execute();
	}

	private T20Node finishWithAHighGoalTree;

	/**
	 * Drives the robot through a defense and high goals<br>
	 * <br>
	 * 
	 * @return a defense high goal auto
	 */

	public void createfinishWithAHighGoal(boolean hasRolledOut, double turnAngle, int josNum) {
		createJostleTree(josNum);
		createAutoBotsTransformSpecialRollOut();
		finishWithAHighGoalTree = new T20SeriesNode();
		T20Node turnRollOut = new T20ParallelNode();
		if (!hasRolledOut)
			turnRollOut.addChild(specialRollOutTree);
		turnRollOut.addChild(new T20AutoCommandFlywheelToSpeed(2000));
		turnRollOut.addChild(new T20AutoCommandHoodToOuterworksPosition());
		finishWithAHighGoalTree.addChild(turnRollOut);
	}

	/**
	 * Drives the robot through a defense and high goals<br>
	 * <br>
	 * 
	 * @return a defense high goal auto
	 */

	public void executefinishWithAHighGoal() {
		finishWithAHighGoalTree.execute();
	}

	private T20Node testTree;

	/**
	 * Drives the robot through the low bar and high goals<br>
	 * <br>
	 * 
	 * @return a low bar high goal auto
	 */

	public void createTestTree() {
		testTree = new T20SeriesNode();
		testTree.addChild(new T20AutoCommandTurnToAngle(180));

	}

	/**
	 * Drives the robot through the low bar and high goals<br>
	 * <br>
	 * 
	 * @return a low bar high goal auto
	 */

	public void executeTestTree() {
		testTree.execute();
	}

	private T20Node jostleTree;

	/**
	 * Drives the robot through the low bar and high goals<br>
	 * <br>
	 * 
	 * @return a low bar high goal auto
	 */

	public void createJostleTree(int numTimes) {
		jostleTree = new T20SeriesNode();
		for (int x = 0; x < numTimes; x++) {
			jostleTree.addChild(new T20AutoCommandDriveJostleTime(.7, .3));
			jostleTree.addChild(new T20AutoCommandDriveJostleTime(-.7, .3));
		}
	}

	/**
	 * Drives the robot through the low bar and high goals<br>
	 * <br>
	 * 
	 * @return a low bar high goal auto
	 */

	public void executeJostleTree() {
		jostleTree.execute();
	}

	private T20Node crossBAndD;

	/**
	 * Drives the robot over a B or D defense <br>
	 * <br>
	 * 
	 * @return a cross B and D node
	 */

	public void createCrossBAndD(double speed, double time, double angle) {
		crossBAndD = new T20SeriesNode();
		crossBAndD.addChild(new T20AutoCommandLanceWatchDog());
		crossBAndD.addChild(new T20AutoCommandDriveStraightTime(speed, time));
	}

	/**
	 * Drives the robot over a B or D defense <br>
	 * <br>
	 * 
	 * @return a cross B and D node
	 */

	public void executeCrossBAndD() {
		crossBAndD.execute();
	}

	private T20Node mainAutoNode;

	public void createMainAutoMode(int pos, boolean doExtend, boolean shouldHighGoal) {
		this.initialHeading = ahrs.ahrs.getAngle();
		boolean rollOutSender = false;
		createAutoBotsTransformConceal();
		createAutoBotsTransformRollOut();
		createPosThreeBatterAuto();
		createCrossBAndD(1, 3, ahrs.ahrs.getAngle());
		createLowBar();
		createLowBarHighGoal();
		createSystemCheck();
		createTestTree();
		mainAutoNode = new T20SeriesNode();
		switch (pos) {
		case 1:
			if (shouldHighGoal) {
				mainAutoNode.addChild(lowBarHighGoalTree);
			} else {
				mainAutoNode.addChild(lowBarTree);
			}
			break;
		case 2:
			if (doExtend) {
				mainAutoNode.addChild(lowBarTree);
				rollOutSender = true;
			} else {
				mainAutoNode.addChild(crossBAndD);

			}
			if (shouldHighGoal) {
				mainAutoNode.addChild(new T20AutoCommandTurnToAngle(this.initialHeading));
				createfinishWithAHighGoal(rollOutSender, 30, 8);
				mainAutoNode.addChild(finishWithAHighGoalTree);
			}
			break;
		case 3:
			if (doExtend) {
				mainAutoNode.addChild(lowBarTree);
				rollOutSender = true;
			} else {
				// mainAutoNode.addChild(crossBAndD);
				mainAutoNode.addChild(new T20AutoCommandDriveStraightFeetLeft(.5, 11));
				mainAutoNode.addChild(new T20AutoCommandDriveStraightTime(0, .2));
			}
			if (shouldHighGoal) {
				// mainAutoNode.addChild(new
				// T20AutoCommandTurnToAngle(this.initialHeading));
				// mainAutoNode.addChild(new
				// T20AutoCommandDriveStraightTime(-1,
				// .8));
				mainAutoNode.addChild(new T20AutoCommandDriveStraightFeetLeft(-.2, -2));
				mainAutoNode.addChild(new T20AutoCommandDriveStraightTime(0, .2));
				mainAutoNode.addChild(new T20AutoCommandTurnToAngle(6));
				createfinishWithAHighGoal(rollOutSender, 6, 0);
				mainAutoNode.addChild(finishWithAHighGoalTree);

			}
			break;
		case 4:
			if (doExtend) {
				mainAutoNode.addChild(lowBarTree);
				rollOutSender = true;
			} else {
				mainAutoNode.addChild(crossBAndD);

			}
			if (shouldHighGoal) {
				mainAutoNode.addChild(new T20AutoCommandTurnToAngle(this.initialHeading));
				mainAutoNode.addChild(new T20AutoCommandDriveStraightTime(-1, .5));
				createfinishWithAHighGoal(rollOutSender, 0, 4);
				mainAutoNode.addChild(finishWithAHighGoalTree);
			}
			break;
		case 5:
			if (doExtend) {
				mainAutoNode.addChild(lowBarTree);
				rollOutSender = true;
			} else {
				mainAutoNode.addChild(crossBAndD);

			}
			if (shouldHighGoal) {
				mainAutoNode.addChild(new T20AutoCommandTurnToAngle(this.initialHeading));
				createfinishWithAHighGoal(rollOutSender, -15, 6);
				mainAutoNode.addChild(finishWithAHighGoalTree);
			}
			break;
		case 15:
			mainAutoNode.addChild(testTree);

			break;
		case 18:
			mainAutoNode.addChild(batterAutoTree);
			break;
		case 20:
			mainAutoNode.addChild(systemCheckTree);
			break;
		default:
			mainAutoNode.addChild(new T20AutoCommandDoNothing());
			break;
		}
		mainAutoNode.addChild(new T20AutoCommandRobotShutdown());
	}

	public void executeMainAutoMode() {
		mainAutoNode.execute();
	}

}
