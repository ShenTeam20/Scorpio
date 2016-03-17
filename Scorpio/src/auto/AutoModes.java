package auto;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Node;
import org.usfirst.frc.team20.robot.Team20Libraries.T20ParallelNode;
import org.usfirst.frc.team20.robot.Team20Libraries.T20SeriesNode;

import autoCommands.T20AutoAutoTarget;
import autoCommands.T20AutoCommandArcTurnToAngle;
import autoCommands.T20AutoCommandDoNothing;
import autoCommands.T20AutoCommandDriveStraightEncoder;
import autoCommands.T20AutoCommandDriveStraightTime;
import autoCommands.T20AutoCommandFlywheelToSpeed;
import autoCommands.T20AutoCommandHomeHood;
import autoCommands.T20AutoCommandHoodToLowPosition;
import autoCommands.T20AutoCommandHoodToOuterworksPosition;
import autoCommands.T20AutoCommandHoodToSafePosition;
import autoCommands.T20AutoCommandLanceDown;
import autoCommands.T20AutoCommandLanceUp;
import autoCommands.T20AutoCommandToggleLance;
import autoCommands.T20AutoCommandTomahawksDown;
import autoCommands.T20AutoCommandTomahawksUp;
import autoCommands.T20AutoCommandTurnToAngle;

public class AutoModes extends Scorpio {

	private T20Node systemCheckTree;

	/**
	 * Puts the robot through a system check<br>
	 * <br>
	 * 
	 * @return a system check node
	 */

	public void createSystemCheck() {
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

	public void exeuteSystemCheck() {
		systemCheckTree.execute();
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
		rollOutTree.addChild(new T20AutoCommandHomeHood());
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
		lowBarHighGoalTree.addChild(new T20AutoCommandDriveStraightEncoder(1, -37000));
		lowBarHighGoalTree.addChild(new T20AutoCommandArcTurnToAngle(.5, 7));
		lowBarHighGoalTree.addChild(new T20AutoCommandArcTurnToAngle(.3, 38));
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

	private T20Node crossBAndD;

	/**
	 * Drives the robot over a B or D defense <br>
	 * <br>
	 * 
	 * @return a cross B and D node
	 */

	public void createCrossBAndD(double speed, double time, double angle) {
		crossBAndD = new T20SeriesNode();
		crossBAndD.addChild(new T20AutoCommandDriveStraightTime(speed, time));
		crossBAndD.addChild(new T20AutoCommandTurnToAngle(angle));
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

	public void createMainAutoMode() {
		mainAutoNode = new T20SeriesNode();
		switch (autoModeChooser.temp) {
		case "0.00":
			mainAutoNode.addChild(new T20AutoCommandDoNothing());
			break;
		case "0.01":
			break;
		case "0.02":
			break;
		case "0.03":
			break;
		case "0.04":
			break;
		case "0.05":
			break;
		case "0.06":
			break;
		case "0.07":
			createLowBarHighGoal();
			mainAutoNode.addChild(lowBarHighGoalTree);
			break;
		case "0.08":
			break;
		case "0.09":
			break;
		case "0.10":
			break;
		case "0.11":
			break;
		case "0.12":
			break;
		case "0.20":
			createSystemCheck();
			mainAutoNode.addChild(systemCheckTree);
			break;
		default:
			break;
		}
	}

	public void executeMainAutoMode() {
		mainAutoNode.execute();
	}

}
