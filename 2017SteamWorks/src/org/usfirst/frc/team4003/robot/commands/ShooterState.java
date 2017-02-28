package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterState extends Command {
	static final int IDLE = 0;
	static final int SHOOTERON = 1;
	static final int FEEDON = 2;
	static final int AGITATORON = 3;
	static final int AGITATOROFF = 4;
	static final int FEEDOFF = 5;
	static final int SHOOTEROFF = 6;
	int state = IDLE;
	boolean on = false;
	public void setOn(boolean on) {
		this.on = on;
		if (on) {
			Robot.lowBoilerState.setOn(false);
		}
	}
	
	public boolean isIdle() {
		return state == IDLE;
	}

    public ShooterState() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }
    
   // Called repeatedly when this Command is scheduled to run
    long transitionTime = System.currentTimeMillis();
    protected void execute() {
    	switch (state) {
    	case IDLE:
    		if (on && Robot.lowBoilerState.isIdle()) {
    			Robot.shooter.set(true);
    			state = SHOOTERON;
    		}
    		break;
    	case SHOOTERON:
    		if (on == false) {
    			Robot.shooter.set(false);
    			state = IDLE;
    		}
    		else if (Robot.shooter.isAtSpeed()) {
    			state = FEEDON;
    			Robot.intakeFeed.feedCommand.setState(IntakeFeedCommand.FEEDSHOOTER);
    			transitionTime = System.currentTimeMillis() + 300;
    		}
    		break;
    	case FEEDON:
    		if (System.currentTimeMillis() >= transitionTime) {
    			state = AGITATORON;
    			Robot.agitator.agitatorCommand.setOn(true);
    		}
    		break;
    	case AGITATORON:
    		if (on == false) {
    			state = AGITATOROFF;
    			Robot.agitator.agitatorCommand.setOn(false);
    			transitionTime = System.currentTimeMillis() + 300;
    		}
    		break;
    	case AGITATOROFF:
    		if (System.currentTimeMillis() >= transitionTime) {
    			state = FEEDOFF;
    			Robot.intakeFeed.feedCommand.setState(IntakeFeedCommand.IDLE);
    			transitionTime= System.currentTimeMillis() + 300;
    		}
    		break;
    	case SHOOTEROFF:
    		if (System.currentTimeMillis() >= transitionTime) {
    			Robot.shooter.set(false);
    			state = IDLE;
    		}
    		break;
    		
    	}
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
