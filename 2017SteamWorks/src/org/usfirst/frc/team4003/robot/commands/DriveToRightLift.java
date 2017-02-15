package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.utilities.Acceleration;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToRightLift extends CommandGroup {

    public DriveToRightLift() {
    	if(Robot.sensors.isAllianceColorRed()){
    		addSequential(new DriveToPoint(70, 0, new Acceleration(.2, .5, 0.02), true, 15));
        	addSequential(new RotateToHeading(-50, 0.4, 0));
        	//addSequential(new DriveToTarget(new Acceleration(.3, .3, 0), 5));
        	addSequential( new DriveToPoint(110,-48, new Acceleration(.2, .3, 0.02),true, 10));
        	addSequential(new WaitForTime(1000));
        	addSequential(new SwitchDirection());
        	addSequential(new DriveForwardForDistance(10, .2));
        	addSequential(new RotateToHeading(-120, 0.4, 0));
        	addSequential(new DriveBackToPoint(170, 30, new Acceleration(.2, .5, 0.02), true, 20));
        	addSequential(new RotateToHeading(-90, 0, 0.3));
        	addSequential(new DriveForwardForDistance(10, 0.3));
        	addSequential(new SwitchDirection());
    	} else {
        	addSequential(new DriveToPoint(74, 0, new Acceleration(.2, .55, 0.02), true, 15));
        	addSequential(new RotateToHeading(50, 0, .4));
        	//addSequential(new DriveToTarget(new Acceleration(.3, .3, 0), 5));
        	addSequential( new DriveToPoint(110,48, new Acceleration(.2, .3, 0.02),true, 10));
        	addSequential(new WaitForTime(1000));
        	addSequential(new SwitchDirection());
        	addSequential(new DriveForwardForDistance(10, .3));
        	addSequential(new RotateToHeading(120, 0, .4));
        	addSequential(new DriveBackToPoint(170, -30, new Acceleration(.2, .55, 0.02), true, 20));
        	addSequential(new RotateToHeading(90, 0.4, 0));
        	addSequential(new DriveForwardForDistance(10, 0.3));
        	addSequential(new SwitchDirection());
    	}
    }
}
