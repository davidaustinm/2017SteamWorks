package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShortTurn extends Command {
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	int time, direction;
	double speed;
	long stopTime;

    public ShortTurn(int direction, int time, double speed) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        this.direction = direction;
        this.time = time;
        this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	stopTime = System.currentTimeMillis() + time;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (direction == LEFT) Robot.driveTrain.setPower(speed, 0);
    	else Robot.driveTrain.setPower(0, speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() >= stopTime;
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
