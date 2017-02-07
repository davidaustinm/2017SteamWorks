package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.utilities.Acceleration;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToRedBoilerHopper extends CommandGroup {

    public DriveToRedBoilerHopper() {
    	addSequential(new DriveToPoint(80,0, new Acceleration(.2,.6,0.02),true,20));
    	addSequential(new RotateToHeading(-90,.5,0));
    	addSequential(new DriveToPoint(90,-30, new Acceleration(.3,.3,0)));
    }
}