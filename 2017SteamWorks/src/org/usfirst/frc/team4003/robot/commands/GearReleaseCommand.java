package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearReleaseCommand extends Command {
	boolean on = false;
    public GearReleaseCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.gearRelease);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//boolean on = Math.abs(Robot.oi.operator.getTriggerAxis(Hand.kRight)) > .5;
    	Robot.gearRelease.set(on);
    }
    public void set(boolean on) {
    	this.on = on;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
