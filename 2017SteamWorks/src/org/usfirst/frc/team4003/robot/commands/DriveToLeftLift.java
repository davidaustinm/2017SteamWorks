package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.utilities.Acceleration;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToLeftLift extends CommandGroup {

    public DriveToLeftLift() {
    	if(Robot.sensors.isAllianceColorRed()){
    		addSequential(new DriveToPoint(85,0, new Acceleration(.2,.6,.04),true, 20));
        	addSequential(new RotateToHeading(55,0,0.3,false));
        	addSequential(new WaitForTime(100));
        	//addSequential(new DriveToTarget(new Acceleration(.3,.3,0), 10));
        	addSequential(new DriveToPoint(112,24, new Acceleration(.2,.3,.04),false,10));
        	addSequential(new WaitForTime(1000));
        	addSequential(new SwitchDirection());
        	addSequential(new DriveForwardForDistance(7, .4));
        	addSequential(new DriveBackToPoint(15,-62, new Acceleration(.3,.5,0.02),false,15));
        	addSequential(new SwitchDirection());
        	
    	}else{
    		addSequential(new DriveToPoint(86,0, new Acceleration(.2,.6,.04),true, 20));
        	addSequential(new RotateToHeading(-55,.4,0,false));
        	addSequential(new WaitForTime(100));
        	//addSequential(new DriveToTarget(new Acceleration(.3,.3,0), 10));
        	addSequential(new DriveToPoint(110,-23, new Acceleration(.2,.3,.04),false,10));
        	addSequential(new WaitForTime(1000));
        	addSequential(new SwitchDirection());
        	addSequential(new DriveForwardForDistance(7, .4));
        	addSequential(new DriveBackToPoint(26,72, new Acceleration(.3,.5,0.02),false,15));
        	addSequential(new SwitchDirection());
        	
    	}
    	
    	
    	//addSequential(new RotateToHeading(20,.3,0,true));
    	/*
    	addSequential(new SwitchDirection());
    	addSequential(new DriveForwardForDistance(7,.2));
  		addSequential(new DriveBackToPoint(40, 8, new Acceleration(.2,.4,.01), true, 5));
    	addSequential(new DriveBackToPoint(22,24, new Acceleration(.3,.3,0), false, 5));
    	addSequential(new SwitchDirection());
    	*/
    	//addSequential(new DriveBackToPoint(-60,-28,.3));
    
    }
}
