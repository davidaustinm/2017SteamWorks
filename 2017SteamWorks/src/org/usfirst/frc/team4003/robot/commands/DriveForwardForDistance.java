package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.utilities.PID;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForwardForDistance extends Command {
	double distance, speed, initialX, initialY;
	
    public DriveForwardForDistance(double distance, double speed) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        this.distance = distance;
        this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	initialX = Robot.sensors.getXCoordinate();
    	initialY = Robot.sensors.getYCoordinate();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.driveTrain.setPower(speed, speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double currentX = Robot.sensors.getXCoordinate();
    	double currentY = Robot.sensors.getYCoordinate();
    	double distanceTraveled = Math.sqrt(Math.pow(currentX - initialX, 2)+Math.pow(currentY - initialY, 2));
        return distanceTraveled >= distance;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Robot.driveTrain.setPower(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
