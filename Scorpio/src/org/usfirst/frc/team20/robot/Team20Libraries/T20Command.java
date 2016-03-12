package org.usfirst.frc.team20.robot.Team20Libraries;

public interface T20Command {
	
	void execute();
	
	boolean isFinished();
	
	T20Command copy();
}