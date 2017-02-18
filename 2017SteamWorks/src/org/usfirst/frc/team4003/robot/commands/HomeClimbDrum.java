package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HomeClimbDrum extends Command {
	public static int HORIZONTAL = 0;
	public static int VERTICAL = 1;
	int direction;

    public HomeClimbDrum(int direction) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.climbDrum);
        this.direction = direction;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.climbDrum.setPower(.3);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(direction == HORIZONTAL){
        	return Robot.sensors.getHorizontalDrumSwitch();
        } else {
        	return Robot.sensors.getVerticalDrumSwitch();
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
