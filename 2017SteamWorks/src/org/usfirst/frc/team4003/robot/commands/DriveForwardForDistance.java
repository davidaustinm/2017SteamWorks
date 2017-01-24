package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.utilities.PID;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForwardForDistance extends Command {
	double distance, encoderTarget;
	PID headingPID = new PID(0.02,0.0005,0);
    public DriveForwardForDistance(double distance, double heading) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        this.distance = distance;
        headingPID.setTarget(heading);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	encoderTarget = Robot.sensors.getLeftDriveEncoder() + distance;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = .5;
    	double heading = Robot.sensors.getYaw();
    	double correction = headingPID.getCorrection(heading);
    	Robot.driveTrain.setPower(speed - correction, speed + correction);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.sensors.getLeftDriveEncoder() >= encoderTarget;
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
