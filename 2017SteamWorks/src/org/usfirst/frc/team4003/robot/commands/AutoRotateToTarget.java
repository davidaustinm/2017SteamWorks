package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.utilities.PID;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoRotateToTarget extends Command {
	public static int LEFT = 0;
	public static int RIGHT = 1;
	int direction;
	double angle;
	PID rotatePID;
	double speed = 0.5;
	long stopTime;
	double pegX = 0;
    public AutoRotateToTarget(int direction, double angle) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        this.direction = direction;
        this.angle = angle;
        rotatePID = new PID(0.01,0,0);
        rotatePID.setTarget(159.5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	stopTime = System.currentTimeMillis() + 2000;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double[] target = Robot.udpServer.getTargetInfo();
    	if (target[1] < 0) {
    		if (direction == LEFT) Robot.driveTrain.setPower(-speed, speed);
    		else Robot.driveTrain.setPower(speed, -speed);
    		pegX = 0;
    		return;
    	}
    	pegX = (target[0] + target[1])/2.0;
    	System.out.println(pegX);
    	double correction = rotatePID.getCorrection(pegX);
    	if (correction > 1) correction = 1;
    	else if (correction < -1) correction = -1;
    	double currentSpeed = correction * speed;
    	Robot.driveTrain.setPower(currentSpeed, -currentSpeed);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    double tolerance = 5;
    protected boolean isFinished() {
    	if (System.currentTimeMillis() >= stopTime) return true;
    	if (direction == LEFT && Robot.sensors.getYaw() > angle) return true;
    	if (direction == RIGHT && Robot.sensors.getYaw() < angle) return true;
    	if (direction == LEFT && pegX < 159.5 + tolerance && pegX > 0) return true;
    	if (direction == RIGHT && pegX > 159.5 - tolerance) return true;
        return false; 
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
