package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeValueCommand extends Command {
	boolean flipperState = true;
	boolean reentryState = false;
    public IntakeValueCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.intakeValves);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intakeValves.setFlipper(flipperState);
    	//Robot.intakeValves.setReentry(reentryState);
    }
    
    public void setFlipperState(boolean state) {
    	flipperState = state;
    }
    public void setReentryState(boolean state) {
    	reentryState = state;
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
