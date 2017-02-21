package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeFeedCommand extends Command {

    public IntakeFeedCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.intakeFeed);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double power = 0;
    	if(Robot.shooter.isAtSpeed()){
    		power = -1;
    	}
    	else if(Robot.intakeValves.isLowBoilerFeedOn()){
    		power = 1;
    	}
    	Robot.intakeFeed.setPower(power);
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
