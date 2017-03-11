package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LowBoilerOnForTime extends Command {
	int time;
	long stopTime;
    public LowBoilerOnForTime(int time) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.time = time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	stopTime = System.currentTimeMillis() + time;
    	Robot.lowBoilerState.setOn(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() >= stopTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lowBoilerState.setOn(false);
    	Robot.agitator.agitatorCommand.setOn(false);
    	Robot.intakeFeed.feedCommand.setState(IntakeFeedCommand.IDLE);
    	Robot.intakeValves.intakeCommand.setFlipperState(true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
