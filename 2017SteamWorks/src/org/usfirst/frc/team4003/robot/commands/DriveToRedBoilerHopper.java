package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.utilities.Acceleration;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToRedBoilerHopper extends CommandGroup {

    public DriveToRedBoilerHopper() {
    	addSequential(new DriveToPoint(70,0, new Acceleration(.3,.75,0.02),false,10));
    	addSequential(new RotateToHeading(-85,.50,0));
    	addSequential(new DriveForwardForDistance(12,.5));
    	addSequential(new WaitForTime(1500));
    	addSequential(new SwitchDirection());
    	addSequential(new RotateToHeading(-45,0,0.5));
    	addSequential(new DriveForwardForDistance(50,.8));
    	addSequential(new RotateToHeading(45,0,0.5));
    	addSequential(new DriveForwardForDistance(10,.6));
    	addSequential(new SwitchDirection());
    	//addSequential(new DriveToPoint(95,-30, new Acceleration(.25,.25,0)));
    }
}