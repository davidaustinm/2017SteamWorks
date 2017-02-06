package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.utilities.Acceleration;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CrossLineAuton extends CommandGroup {

	//private final int BLUE = 1;
	//private final int RED = 2;
	
	public static int LEFT = 1;
	public static int RIGHT = 2;
	
    public CrossLineAuton(int position, int direction) {
    	addSequential(new WaitForTime(1000));
    	
    	if(position == 1 || position == 3){
    		addSequential(new DriveToPoint(100, 0, new Acceleration(.2,.5,.04),false, 0));
    	}
    	if(position == 2){
    		if(direction == LEFT){
    			addSequential(new RotateToHeading(45,0,.5,true));
    			addSequential(new DriveToPoint(100,100, new Acceleration(.2,.5,04)));
    		}
    		if(direction == RIGHT){
    			addSequential(new RotateToHeading(-45,.5,0,true));
    			addSequential(new DriveToPoint(100,-100, new Acceleration(.2,.5,04)));
    		}
    	}
    }
}