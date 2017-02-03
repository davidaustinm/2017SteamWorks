package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.utilities.Acceleration;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveToTarget extends DriveToPoint {
	double[] target;
    public DriveToTarget(Acceleration accelerate, double slowDistance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super(0, 0, accelerate, false, slowDistance);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	target = Robot.trackingCamera.getTarget();
    	SmartDashboard.putNumber("targetX", target[0]);
    	SmartDashboard.putNumber("targetY", target[1]);
    	if(Double.isNaN(target[0])){
    		return;
    	}
    	targetX = target[0];
    	targetY = target[1];
    	super.execute();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double changeInX = targetX - Robot.sensors.getXCoordinate();
    	double changeInY = targetY - Robot.sensors.getYCoordinate();
    	double distance = Math.sqrt(Math.pow(changeInX, 2) + Math.pow(changeInY, 2));
        return Double.isNaN(target[0]) || distance < 2;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
