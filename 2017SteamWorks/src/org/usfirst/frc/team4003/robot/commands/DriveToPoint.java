package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.utilities.Acceleration;
import org.usfirst.frc.team4003.robot.utilities.PID;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveToPoint extends Command {
	Acceleration accelerate;
	double targetX, targetY;
	double distance;
	double lastDistance = 1000;
	boolean coast = false;
	double addToYaw = 0;
	PID headingPID = new PID(0.015, 0, 0.005);
	PID distancePID = null;
    public DriveToPoint(double x, double y, Acceleration accelerate) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        targetX = x;
        targetY = y;
        this.accelerate = accelerate;
        headingPID.setTarget(0);
    }
    public DriveToPoint(double x, double y, Acceleration accelerate, boolean coast, double slowDistance) {
    	this(x,y,accelerate);
    	this.coast = coast;	
    	distancePID = new PID(1/slowDistance, 0,0);
    	distancePID.setTarget(0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    public double normalizeAngle(double angle){
    	while(angle > 180){
    		angle -= 360;
    	}
    	while(angle < -180){
    		angle += 360;
    	}
    	return angle;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double currentX = Robot.sensors.getXCoordinate(); 
    	double currentY = Robot.sensors.getYCoordinate();
    	double changeInY = targetY-currentY;
    	double changeInX = targetX- currentX;
    	distance = Math.sqrt(Math.pow(changeInX, 2) + Math.pow(changeInY, 2));
    	double alpha = Math.atan2(changeInY, changeInX);
    	double beta = Robot.sensors.getYaw() + addToYaw - Math.toDegrees(alpha);
    	
    	beta = normalizeAngle(beta);
    	SmartDashboard.putNumber("Beta", beta);
    	SmartDashboard.putNumber("x", currentX);
    	SmartDashboard.putNumber("y", currentY);
    	SmartDashboard.putNumber("distance", distance);
    	
    	double correction = headingPID.getCorrection(beta);
    	if (correction>0.1) correction = 0.1;
    	if (correction<-0.1) correction = -0.1;
    	double speed = accelerate.getSpeed();
    	double speedFactor = 1;
    	if (distancePID != null) {
    		speedFactor = distancePID.getCorrection(-distance);
    		if (Math.abs(speedFactor) > 1) speedFactor = 1;
    	}
    	double leftPower = (speed - correction) * speedFactor;
    	double rightPower = (speed + correction) * speedFactor;
    	Robot.driveTrain.setPower(leftPower, rightPower); 
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean finished = distance>lastDistance;
    	finished = distance < 2;
    	lastDistance = distance;
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if (!coast) Robot.driveTrain.setPower(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
