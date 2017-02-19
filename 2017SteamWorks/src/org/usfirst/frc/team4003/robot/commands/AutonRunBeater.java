package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonRunBeater extends Command {
	int time;
	long stopTime;
    public AutonRunBeater(int time) {
    	this.time = time;
        requires(Robot.beaters);
        requires(Robot.intakeFeed);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	stopTime = System.currentTimeMillis() + time;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.beaters.setPower(1);
    	Robot.intakeFeed.setPower(1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() >= stopTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.beaters.setPower(0);
    	Robot.intakeFeed.setPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
