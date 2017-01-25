package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveToPoint extends Command {
	double speed = 0.4;
	double targetX, targetY;
	double distance;
	double lastDistance = 1000;
    public DriveToPoint(double x, double y, double speed) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        targetX = x;
        targetY = y;
        this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double currentX = Robot.sensors.getXCoordinate(); 
    	double currentY = Robot.sensors.getYCoordinate();
    	double changeInY = targetY-currentY;
    	double changeInX = targetX- currentX;
    	distance = Math.sqrt(Math.pow(changeInX, 2) + Math.pow(changeInY, 2));
    	double alpha = Math.atan2(changeInY, changeInX);
    	double beta = Robot.sensors.getYaw()- Math.toDegrees(alpha);
    	SmartDashboard.putNumber("x", currentX);
    	SmartDashboard.putNumber("y", currentY);
    	SmartDashboard.putNumber("distance", distance);
    	double correction = 0.02 * beta;
    	if (correction>0.1) correction = 0.1;
    	if (correction<-0.1) correction = -0.1;
    	Robot.driveTrain.setPower(speed+correction, speed-correction); 
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean finished = distance>lastDistance;
    	lastDistance = distance;
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.setPower(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
