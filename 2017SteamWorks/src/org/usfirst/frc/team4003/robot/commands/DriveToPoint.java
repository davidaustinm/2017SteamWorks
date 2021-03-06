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
	int timeout = 10000;
	long stopTime;
	double maxCorrection;
    public DriveToPoint(double x, double y, Acceleration accelerate, int timeout) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        this.timeout = timeout;
        targetX = x;
        targetY = y;
        this.accelerate = accelerate;
        headingPID.setTarget(0);
        maxCorrection = 0.1;
    }
    public DriveToPoint(double x, double y, Acceleration accelerate, boolean coast, double slowDistance,int timeout) {
    	this(x,y,accelerate, timeout);
    	this.coast = coast;	
    	distancePID = new PID(1/slowDistance, 0,0);
    	distancePID.setTarget(0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	stopTime  = System.currentTimeMillis() + timeout;
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
    	if (Double.isNaN(targetX)) return;
    	double currentX = Robot.sensors.getXCoordinate(); 
    	double currentY = Robot.sensors.getYCoordinate();
    	double changeInY = targetY-currentY;
    	double changeInX = targetX- currentX;
    	distance = Math.sqrt(Math.pow(changeInX, 2) + Math.pow(changeInY, 2));
    	double alpha = Math.atan2(changeInY, changeInX);
    	double beta = Robot.sensors.getYaw() + addToYaw - Math.toDegrees(alpha);
    	
    	beta = normalizeAngle(beta);
    	//SmartDashboard.putNumber("Beta", beta);
    	//SmartDashboard.putNumber("x", currentX);
    	//SmartDashboard.putNumber("y", currentY);
    	//SmartDashboard.putNumber("distance", distance);
    	if (Double.isNaN(targetX) == false) SmartDashboard.putNumber("PiTargetX", targetX);
		if (Double.isNaN(targetY) == false) SmartDashboard.putNumber("PiTargetY", targetY);
		//System.out.println(targetX + " " + targetY);
    	
    	double correction = headingPID.getCorrection(beta);
    	if (correction>maxCorrection) correction = maxCorrection;
    	if (correction<-maxCorrection) correction = -maxCorrection;
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
    	if (System.currentTimeMillis() >= stopTime) return true;
    	boolean finished = distance>lastDistance;
    	//finished = distance < 2;
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
