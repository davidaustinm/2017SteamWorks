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
    		System.out.println("Drive to middle red");
    		//addSequential(new DriveToPoint(78,0, new Acceleration(.2,.5,.04),false, 20));
    		addSequential(new DriveToPoint(79,0, new Acceleration(.2,.45,.04),false, 20,3000));
        	//addSequential(new DriveToTarget(new Acceleration(.2,.3,0), 10));
        	addSequential(new GearReleaseToggle(true));
        	addSequential(new WaitForTime(1500));
        	addSequential(new SwitchDirection());
        	addSequential(new DriveForwardForDistance(10, 0.2));
        	if (Robot.autonSelector.getEndingPosition() == "H") return;
        	addSequential(new CircleDrive(45, 260, 100, 0.30));
        	/*
        	addSequential(new DriveForwardForDistance(65,.4));
        	addSequential(new SwitchDirection());
        	addSequential(new RotateToHeading(-75,.4,0));
        	addSequential(new DriveForwardForDistance(34,.4));
        	*/
        	/*
        	addSequential(new DriveBackToPoint(144,112, new Acceleration(.1,.45,.04),true,5, 3000));
        	addSequential(new RotateToHeading(-90,0,.5));
        	addSequential(new DriveForwardForDistance(5,.4));
        	*/
        	addSequential(new DriveBackToPoint(380, 100, new Acceleration(.1, 0.5, 0.04), true, 5, 7000));
        	//addSequential(new DriveBackToPoint(360, -60, new Acceleration(0.5, 0.5, 0.04), true, 20, 10000));
        	
    	} else {
    		System.out.println("drive to middle blue");
    		//addSequential(new DriveToPoint(78,0, new Acceleration(.2,.5,.04),false, 20));
    		addSequential(new DriveToPoint(79,0, new Acceleration(.2,.6,.04),false, 20,3000));
        	//addSequential(new DriveToTarget(new Acceleration(.2,.3,0), 10));
        	addSequential(new GearReleaseToggle(true));
        	addSequential(new WaitForTime(1500));
        	addSequential(new SwitchDirection());
        	addSequential(new DriveForwardForDistance(10, 0.2));
        	if (Robot.autonSelector.getEndingPosition() == "H") return;
        	addSequential(new CircleDrive(45, 90, 260, 0.35));
        	/*
        	addSequential(new DriveForwardForDistance(65,.4));
        	addSequential(new SwitchDirection());
        	addSequential(new RotateToHeading(-75,.4,0));
        	addSequential(new DriveForwardForDistance(34,.4));
        	*/
        	/*
        	addSequential(new DriveBackToPoint(144,-112, new Acceleration(.1,.45,.04),true,5, 3000));
        	addSequential(new RotateToHeading(90,.5,0));
        	addSequential(new DriveForwardForDistance(5,.4));
        	*/
        	addSequential(new DriveBackToPoint(380,-100, new Acceleration(.1,.45,.04),true,5, 7000));
        	
    	}
    	
    }
}
