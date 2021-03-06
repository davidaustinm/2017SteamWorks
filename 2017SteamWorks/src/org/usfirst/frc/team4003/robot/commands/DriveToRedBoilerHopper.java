package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.utilities.Acceleration;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToRedBoilerHopper extends CommandGroup {

    public DriveToRedBoilerHopper() {
    	if(Robot.sensors.isAllianceColorRed()){
    		addSequential(new SwitchDirection());
    		addSequential(new DriveBackToPoint(-46,0, new Acceleration(.3,.5,0.02),false,10, 2000));
        	addSequential(new RotateToHeading(-85,.60,0));
        	//addSequential(new DriveForwardForDistance(2,.5));
        	addSequential(new WaitForTime(1500));
        	addSequential(new SwitchDirection());
        	addSequential(new RotateToHeading(-45,0,0.56));
        	addSequential(new DriveForwardForDistance(55,.7));
        	addSequential(new RotateToHeading(-135,0,0.5));
        	addSequential(new SwitchDirection());
        	addSequential(new DriveForwardForDistance(10,.4));
        	addSequential(new ShootHighForTime(7000));
        	//addSequential(new SwitchDirection());
        	
    	} else{
    		addSequential(new SwitchDirection());
    		addSequential(new DriveBackToPoint(-46,0, new Acceleration(.3,.5,0.02),false,10, 2000));
        	addSequential(new RotateToHeading(85,0,.50));
        	addSequential(new DriveForwardForDistance(2,.5));
        	addSequential(new WaitForTime(1500));
        	addSequential(new SwitchDirection());
        	addSequential(new RotateToHeading(45,0.6,0));
        	addSequential(new DriveForwardForDistance(59,.7));
        	addSequential(new RotateToHeading(130,0.5,0));
        	addSequential(new SwitchDirection());
        	addSequential(new DriveForwardForDistance(12,.4));
        	addSequential(new ShootHighForTime(7000));
        	//addSequential(new SwitchDirection());
    		/*
    		
    		addSequential(new DriveToPoint(70,0, new Acceleration(.3,.70,0.02),false,10));
        	addSequential(new RotateToHeading(85,0,0.4));
        	addSequential(new DriveForwardForDistance(15,.5));
        	addSequential(new WaitForTime(1500));
        	addSequential(new SwitchDirection());
        	addSequential(new RotateToHeading(45,0.5,0));
        	addSequential(new DriveForwardForDistance(34,.7));
        	addSequential(new RotateToHeading(-45,0.35,0));
        	addSequential(new DriveForwardForDistance(22,.4));
        	addSequential(new SwitchDirection());
        	*/
    	}
    	
    }
}