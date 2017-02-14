package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeValveToggle extends Command {
	int valve;
	boolean on;
    public IntakeValveToggle(int valve, boolean on) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.valve = valve;
    	this.on = on;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (valve == RobotMap.INTAKEFLIPPER) {
    		Robot.intakeValveCommand.setFlipperState(on);
    		Robot.intakeValveCommand.setReentryState(!on);
    	}
    	if (valve == RobotMap.INTAKEREENTRY) {
    		Robot.intakeValveCommand.setReentryState(on);
    		Robot.intakeValveCommand.setFlipperState(!on);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
