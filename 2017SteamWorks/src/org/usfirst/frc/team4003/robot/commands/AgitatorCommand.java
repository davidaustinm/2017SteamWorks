package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AgitatorCommand extends Command {
	boolean on = false;
	public void setOn(boolean on) {
		this.on = on;
	}

    public AgitatorCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.agitator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double power = 0;
    	if (on) power = 1.0;
    	if (Robot.inAuton && Robot.sensors.isAllianceColorRed() == false && on) power = 0.5;
    	Robot.agitator.setPower(power);
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
