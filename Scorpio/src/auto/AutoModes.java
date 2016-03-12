package auto;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Node;
import org.usfirst.frc.team20.robot.Team20Libraries.T20ParallelNode;
import org.usfirst.frc.team20.robot.Team20Libraries.T20SeriesNode;

import autoCommands.*;

public class AutoModes extends Scorpio {

	private T20Node rollOutTree;

	public void createTransformersRollOut() {
		rollOutTree = new T20SeriesNode();
		rollOutTree.addChild(new T20AutoCommandTomahawksDown());
		rollOutTree.addChild(new T20AutoCommandHomeHood());
		rollOutTree.addChild(new T20AutoCommandToggleLance());
		T20Node secondaryRollOut = new T20ParallelNode();
		secondaryRollOut.addChild(new T20AutoCommandLanceDown());
		secondaryRollOut.addChild(new T20AutoCommandHoodToLowPosition());
		rollOutTree.addChild(secondaryRollOut);
	}

	public void executeTransformersRollOut() {
		rollOutTree.execute();
	}

	private T20Node transformTree;

	public void createTransformersTransform() {
		transformTree = new T20SeriesNode();
		transformTree.addChild(new T20AutoCommandHoodToSafePosition());
		transformTree.addChild(new T20AutoCommandHomeHood());
		transformTree.addChild(new T20AutoCommandToggleLance());
		T20Node secondaryTranform = new T20ParallelNode();
		secondaryTranform.addChild(new T20AutoCommandLanceUp());
		secondaryTranform.addChild(new T20AutoCommandHoodToSafePosition());
		secondaryTranform.addChild(new T20AutoCommandTomahawksUp());
		transformTree.addChild(secondaryTranform);
	}

	public void executeTransformersTransform() {
		transformTree.execute();
	}

	private T20Node driveStraightTimeTree;

	public void createDriveStraightTime(double speed, double time) {
		driveStraightTimeTree = new T20SeriesNode();
		driveStraightTimeTree.addChild(new T20AutoCommandDriveStraightTime(1, 2));
	}

	public void executeDriveStraightTime() {
		driveStraightTimeTree.execute();
	}

	private T20Node lowBarTree;

	public void createLowBar() {
		createTransformersRollOut();
		lowBarTree = new T20SeriesNode();
		lowBarTree.addChild(rollOutTree);
		lowBarTree.addChild(new T20AutoCommandDriveStraightTime(1, 1.9));
		lowBarTree.addChild(new T20AutoCommandTurnToAngle(45));
	}

	public void executeLowBar() {
		lowBarTree.execute();
	}

}
