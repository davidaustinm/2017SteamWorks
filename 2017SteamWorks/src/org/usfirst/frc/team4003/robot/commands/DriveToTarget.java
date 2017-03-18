package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.subsystems.TrackingCamera;
import org.usfirst.frc.team4003.robot.utilities.Acceleration;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveToTarget extends DriveToPoint {
	double[] target;
	long stopTime;
	int timeOut;
    public DriveToTarget(Acceleration accelerate, double slowDistance, int timeOut) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super(0, 0, accelerate, false, slowDistance, 3000);
    	this.timeOut = timeOut;
    	maxCorrection = 0.2;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	target = Robot.trackingCamera.getTarget();
        targetX = target[0];
        targetY = target[1];
    	stopTime = System.currentTimeMillis() + timeOut;
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	
    	target = Robot.trackingCamera.getTargetPosition();
    	
    	SmartDashboard.putNumber("targetX", target[0]);
    	SmartDashboard.putNumber("targetY", target[1]);
    	
    	if(target[0] == TrackingCamera.NOTFOUND || lastDistance < 28){
    		super.execute();
    		return;
    	}
    	
    	targetX = target[0];
    	targetY = target[1];
    	
    	super.execute();
    	System.out.println(distance);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double changeInX = targetX - Robot.sensors.getXCoordinate();
    	double changeInY = targetY - Robot.sensors.getYCoordinate();
    	double distance = Math.sqrt(Math.pow(changeInX, 2) + Math.pow(changeInY, 2));
        return distance < 5 || System.currentTimeMillis() >= stopTime;
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
