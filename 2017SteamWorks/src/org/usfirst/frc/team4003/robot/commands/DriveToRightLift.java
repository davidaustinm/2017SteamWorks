package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.utilities.Acceleration;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToRightLift extends CommandGroup {

    public DriveToRightLift() {
    	addSequential(new DriveToPoint(70, 0, new Acceleration(.2, .5, 0.02), true, 15));
    	addSequential(new RotateToHeading(50, 0, .4));
    	addSequential(new DriveToTarget(new Acceleration(.3, .3, 0), 5));
    	addSequential(new WaitForTime(1000));
    	addSequential(new SwitchDirection());
    	addSequential(new DriveForwardForDistance(10, .2));
    	addSequential(new RotateToHeading(120, 0, .4));
    	addSequential(new DriveBackToPoint(170, -30, new Acceleration(.2, .5, 0.02), true, 20));
    	addSequential(new RotateToHeading(90, 0.3, 0));
    	addSequential(new DriveForwardForDistance(10, 0.3));
    	addSequential(new SwitchDirection());
    }
}
