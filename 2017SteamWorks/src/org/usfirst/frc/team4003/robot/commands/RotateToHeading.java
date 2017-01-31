package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.utilities.PID;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotateToHeading extends Command {
	double degrees;
	double initalYaw;
	PID headingPID = new PID(0.1,0,0.105);
	double leftSpeed, rightSpeed;
	boolean coast = false;
    public RotateToHeading(double degrees, double leftSpeed, double rightSpeed) {
    	this.degrees = degrees;
    	headingPID.setTarget(degrees);
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
    }
    public RotateToHeading(double degrees, double leftSpeed, double rightSpeed, boolean coast) {
    	this(degrees, leftSpeed, rightSpeed);
    	this.coast = coast;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double correction = headingPID.getCorrection(Robot.sensors.getYaw());
    	if (correction > 1) correction = 1;
    	if (correction < -1) correction = -1;
    	
    	Robot.driveTrain.setPower(-leftSpeed * correction, rightSpeed * correction);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Math.abs(Robot.sensors.getYaw() - degrees) < 3;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if (!coast) Robot.driveTrain.setPower(0, 0);
    }

}
