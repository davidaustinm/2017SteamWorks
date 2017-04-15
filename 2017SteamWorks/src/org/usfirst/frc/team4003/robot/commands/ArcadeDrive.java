package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArcadeDrive extends Command {
	boolean didJerk;
	double lastYPower;

    public ArcadeDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	didJerk = false;
    }

    // Called repeatedly when this Command is scheduled to run
    double alpha = 0.5; //0.74;
    double turnAlpha = .8;
    double lastTurn = 0;
    double turnAlpham1 = 1-turnAlpha;
    double alpham1 = 1-alpha;
    protected void execute() {
    	double yValues = Robot.oi.driver.getY(Hand.kLeft);
    	double xValues = Robot.oi.driver.getX(Hand.kRight);
    	if (Math.abs(yValues) < 0.1) yValues = 0;
    	if (Math.abs(xValues) < 0.1) xValues = 0;
    	
    	double power = (alpha * yValues) + (alpham1 * lastYPower);
    	double turn = (turnAlpha * xValues) + turnAlpham1 * lastTurn;
    	
    	if (Math.abs(yValues) <= 0.15) {
    		power = (1 * yValues) + (0 * lastYPower);   
    		turn = xValues;
    	}
    	
    	//System.out.println(power + " " + xValues);
    	//Robot.driveTrain.arcadeDrive(-power, -xValues, true);
    	Robot.driveTrain.arcadeDrive(-power, -turn, true);
    	lastYPower = power;
    	lastTurn = xValues;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
