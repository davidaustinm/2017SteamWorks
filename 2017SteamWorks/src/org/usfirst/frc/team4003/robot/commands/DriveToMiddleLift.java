package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.utilities.Acceleration;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToMiddleLift extends CommandGroup {

    public DriveToMiddleLift() {
    	if(Robot.sensors.isAllianceColorRed()){
    		
    		addSequential(new DriveToPoint(81,0, new Acceleration(.2,.5,.04),false, 20));
        	//addSequential(new DriveToTarget(new Acceleration(.2,.3,0), 10));
        	addSequential(new WaitForTime(1500));
        	addSequential(new SwitchDirection());
        	addSequential(new DriveForwardForDistance(10, 0.2));
        	addSequential(new CircleDrive(45, 260, 100, 0.30));
        	/*
        	addSequential(new DriveForwardForDistance(65,.4));
        	addSequential(new SwitchDirection());
        	addSequential(new RotateToHeading(-75,.4,0));
        	addSequential(new DriveForwardForDistance(34,.4));
        	*/
        	addSequential(new DriveBackToPoint(159,112, new Acceleration(.1,.45,.04),true,5));
        	addSequential(new RotateToHeading(-90,0,.4));
        	addSequential(new DriveForwardForDistance(5,.4));
        	
    	} else {
    		addSequential(new DriveToPoint(81,0, new Acceleration(.2,.5,.04),false, 20));
        	//addSequential(new DriveToTarget(new Acceleration(.2,.3,0), 10));
        	addSequential(new WaitForTime(1500));
        	addSequential(new SwitchDirection());
        	addSequential(new DriveForwardForDistance(10, 0.2));
        	addSequential(new CircleDrive(45, 90, 260, 0.35));
        	/*
        	addSequential(new DriveForwardForDistance(65,.4));
        	addSequential(new SwitchDirection());
        	addSequential(new RotateToHeading(-75,.4,0));
        	addSequential(new DriveForwardForDistance(34,.4));
        	*/
        	addSequential(new DriveBackToPoint(160,-112, new Acceleration(.1,.45,.04),true,5));
        	addSequential(new RotateToHeading(90,.4,0));
        	addSequential(new DriveForwardForDistance(5,.4));
        	
    	}
    	
    }
}
