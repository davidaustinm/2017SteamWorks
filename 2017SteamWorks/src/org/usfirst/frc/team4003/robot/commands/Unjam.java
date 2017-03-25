package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Unjam extends Command {
	long stopTime = -1;
    public Unjam() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.agitator);
    
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.shooterState.isIdle() && stopTime == -1){
    		stopTime = System.currentTimeMillis() + 500;
    	}
    	if(Robot.shooterState.isIdle() && stopTime != -1){
    		Robot.agitator.setPower(-0.4);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //return stopTime != -1 && System.currentTimeMillis() >= stopTime;
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.agitator.setPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
