package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BackupLoadGear extends Command {
	double initialX, initialY;
	double DISTANCE = 2.5;

    public BackupLoadGear() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	initialX = Robot.sensors.getXCoordinate();
    	initialY = Robot.sensors.getYCoordinate();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.setPower(.3, .3);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double changeInX, changeInY;
    	changeInX = Robot.sensors.getXCoordinate() - initialX;
    	changeInY = Robot.sensors.getYCoordinate() - initialY;
    	
        return Math.sqrt(Math.pow(changeInX, 2) + Math.pow(changeInY, 2)) >= DISTANCE;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.setPower(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
