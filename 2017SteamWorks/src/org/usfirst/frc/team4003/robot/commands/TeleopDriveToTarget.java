package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.utilities.Acceleration;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TeleopDriveToTarget extends CommandGroup {

    public TeleopDriveToTarget() {
    	addSequential(new ToggleTracking(true));
    	addSequential(new WaitForGearTarget());
    	addSequential(new DriveToTarget(new Acceleration(.3, .3, 0), 5));
    }
    protected void end(){
    	Robot.trackingCamera.trackingOn(false);
    }
}
