package auto;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Node;
import org.usfirst.frc.team20.robot.Team20Libraries.T20ParallelNode;
import org.usfirst.frc.team20.robot.Team20Libraries.T20SeriesNode;

import autoCommands.*;

public class AutoModes extends Scorpio {

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
		transformConcealTree.addChild(new T20AutoCommandHomeHood());
		transformConcealTree.addChild(new T20AutoCommandToggleLance());
		T20Node secondaryTranformConceal = new T20ParallelNode();
		secondaryTranformConceal.addChild(new T20AutoCommandLanceUp());
		secondaryTranformConceal.addChild(new T20AutoCommandHoodToSafePosition());
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

}
