package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.utilities.Acceleration;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToMiddleLift extends CommandGroup {

    public DriveToMiddleLift() {
    	addSequential(new DriveToPoint(70,0, new Acceleration(.2,.4,.04),true, 20));
    	addSequential(new DriveToTarget(new Acceleration(.3,.3,0), 10));
    	//addSequential(new )
    }
}
