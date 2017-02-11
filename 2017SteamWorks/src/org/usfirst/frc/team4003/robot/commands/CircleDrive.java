package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.utilities.PID;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class CircleDrive extends Command {
	public static final int CCW = 0;
	public static final int CW = 1;
	int direction;
	double radius;
	double wheelbase = 24;
	PID radiusPID, headingPID;
	double initAngle, finalAngle;
	double centerX, centerY;
	double cutpoint;
	double speed;
	double currentAngle;
	boolean coast = false;
	
	public CircleDrive(double radius, double initAngle, double finalAngle, double speed, boolean coast) {
		this(radius, initAngle, finalAngle, speed);
		this.coast = coast;
	}
	
    public CircleDrive(double radius, double initAngle, double finalAngle, double speed) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        this.radius = radius;
        this.initAngle = initAngle;
        this.finalAngle = finalAngle;
        this.speed = speed;
        radiusPID = new PID(0.02, 0.0, 0);
        radiusPID.setTarget(radius);
        headingPID = new PID(0.02, 0.0, 0);
        headingPID.setTarget(0);
        if (finalAngle > initAngle) {
        	direction = CCW;
        	cutpoint = initAngle - 90;
        } else {
        	direction = CW;
        	cutpoint = finalAngle - 90;
        }
    }
    
    public double normalizeAngle(double angle, double cut) {
    	while (angle > cut + 360) angle -= 360;
    	while (angle < cut) angle += 360;
    	return angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double currentX = Robot.sensors.getXCoordinate();
    	double currentY = Robot.sensors.getYCoordinate();
    	double angle = Math.toRadians(initAngle + 180);
    	centerX = currentX + radius * Math.cos(angle);
    	centerY = currentY + radius * Math.sin(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double currentX = Robot.sensors.getXCoordinate();
    	double currentY = Robot.sensors.getYCoordinate();
    	double changeInX = currentX - centerX;
    	double changeInY = currentY - centerY;
    	SmartDashboard.putNumber("centerX", centerX);
    	SmartDashboard.putNumber("centerY", centerY);
    	
    	double currentRadius = Math.sqrt(Math.pow(changeInX, 2) + Math.pow(changeInY, 2));
    	double heading = Robot.sensors.getYaw() + Robot.driveTrain.getSwitchCount() * 180;
    	currentAngle = normalizeAngle(Math.toDegrees(Math.atan2(changeInY, changeInX)), cutpoint);
    	
    	
    	double targetHeading;
    	if (direction == CCW) targetHeading = currentAngle + 90;
    	else targetHeading = currentAngle - 90;
    	SmartDashboard.putNumber("targetHeading", targetHeading);
    	SmartDashboard.putNumber("heading", heading);
    	double angleError = normalizeAngle(targetHeading - heading, -180);
    	double correction = -headingPID.getCorrection(angleError);
    	
    	if (direction == CCW) correction -= radiusPID.getCorrection(currentRadius);
    	else correction += radiusPID.getCorrection(currentRadius);
    	
    	double leftPower = (radius - wheelbase/2.0)/radius * speed;
    	double rightPower = (radius + wheelbase/2.0)/radius * speed;
    	if (direction == CW) {
    		double tmp = rightPower;
    		rightPower = leftPower;
    		leftPower = tmp;
    	}
    	
    	leftPower -= correction;
    	leftPower = clip(leftPower, 0, 1);
    	rightPower += correction;
    	rightPower = clip(rightPower, 0, 1);
    	
    	Robot.driveTrain.setPower(leftPower, rightPower);
    }
    
    public double clip(double x, double min, double max) {
    	if (x > max) return max;
    	if (x < min) return min;
    	return x;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (direction == CCW) return currentAngle > finalAngle;
        return currentAngle < finalAngle;
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
