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
    		System.out.println("RightLiftRed");
    		//addSequential(new DriveToPoint(50, 0, new Acceleration(.2, .55, 0.02), true, 15));
    		addSequential(new DriveToPoint(44, 0, new Acceleration(.2, .55, 0.02), true, 15, 2000));
        	addSequential(new RotateToHeading(-55, 0.5, 0));
        	//addSequential(new DriveToTarget(new Acceleration(.3, .3, 0), 5));
        	//addSequential( new DriveToPoint(105,-49, new Acceleration(.2, .4, 0.02),true, 10, 2000));
        	addSequential( new DriveToPoint(99,-49, new Acceleration(.2, .4, 0.02),true, 10, 2000));
        	addSequential(new GearReleaseToggle(true));
        	addSequential(new WaitForTime(1000));
        	addSequential(new SwitchDirection());
        	addSequential(new DriveForwardForDistance(10, .3));
        	addSequential(new RotateToHeading(-150, 0.5, 0));
        	if (Robot.autonSelector.getEndingPosition() == "H") {
        		addSequential(new DriveBackToPoint(155, 25, new Acceleration(.2, .5, 0.02), true, 20, 3000));
        		addSequential(new RotateToHeading(-90, 0, 0.4));
        	} else {
        		addSequential(new DriveBackToPoint(360, 25, new Acceleration(0.2, 0.5, 0.02), true, 20, 7000));
        	}
        	//addSequential(new DriveForwardForDistance(10, 0.3));
        	//addSequential(new SwitchDirection());
    	} else {
    		System.out.println("RightLiftBlue");
        	//addSequential(new DriveToPoint(53, 0, new Acceleration(.2, .55, 0.02), true, 15));
    		addSequential(new DriveToPoint(47, 0, new Acceleration(.2, .55, 0.02), true, 15, 2000));
        	addSequential(new RotateToHeading(55, 0, .5));
        	//addSequential(new DriveToTarget(new Acceleration(.3, .3, 0), 5));
        	//addSequential( new DriveToPoint(106,47, new Acceleration(.2, .4, 0.02),true, 15));
        	addSequential( new DriveToPoint(103,47, new Acceleration(.2, .4, 0.02),true, 15, 2000));
        	addSequential(new GearReleaseToggle(true));
        	addSequential(new WaitForTime(1000));
        	addSequential(new SwitchDirection());
        	addSequential(new DriveForwardForDistance(10, .3));
        	addSequential(new RotateToHeading(150, 0, .6));
        	if (Robot.autonSelector.getEndingPosition() == "H") {
        		addSequential(new DriveBackToPoint(155, -25, new Acceleration(.2, .55, 0.02), true, 20, 3000));
            	addSequential(new RotateToHeading(90, 0.4, 0));
        	} else {
        		addSequential(new DriveBackToPoint(360, -25, new Acceleration(0.2, 0.5, 0.02), true, 20, 7000));
        	}
        	
        	//addSequential(new DriveForwardForDistance(10, 0.3));
        	//addSequential(new SwitchDirection());
    	}
    }
}
