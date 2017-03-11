package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LowBoilerState extends Command {
	static final int IDLE = 0;
	static final int FEEDON = 2;
	static final int AGITATORON = 3;
	static final int AGITATOROFF = 4;
	static final int FEEDOFF = 5;
	static final int SHOOTEROFF = 6;
	int state = IDLE;
	boolean on = false;
	public void setOn(boolean on) {
		this.on = on;
		if (on) Robot.shooterState.setOn(false);
	}
	public boolean isIdle() {
		return state == IDLE;
	}
    public LowBoilerState() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    RunBeater runBeater;
    long transitionTime = System.currentTimeMillis();
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//System.out.println(state);
    	switch(state) {
    	case IDLE:
    		if (on && Robot.shooterState.isIdle()) {
    			runBeater = new RunBeater();
    			runBeater.start();
    			Robot.intakeFeed.feedCommand.setState(IntakeFeedCommand.FEEDLOW);
    			Robot.intakeValves.intakeCommand.setFlipperState(false);
    			state = FEEDON;
    			transitionTime = System.currentTimeMillis() + 500;
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
    			Robot.agitator.agitatorCommand.setOn(false);
    			state = FEEDOFF;
    			transitionTime = System.currentTimeMillis() + 500;
    		}
    		break;
    	case FEEDOFF:
    		if (System.currentTimeMillis() >= transitionTime) {
    			Robot.intakeFeed.feedCommand.setState(IntakeFeedCommand.IDLE);
    			transitionTime = System.currentTimeMillis() + 300;
    			state = SHOOTEROFF;
    		}
    		break;
    	case SHOOTEROFF:
    		if (System.currentTimeMillis() >= transitionTime) {
    			Robot.intakeValves.intakeCommand.setFlipperState(true);
    			runBeater.cancel();
    			state = IDLE;
    		}
    		break;
    	}
    }
    
    public void setIdle() {
    	Robot.agitator.agitatorCommand.setOn(false);
    	Robot.intakeFeed.feedCommand.setState(IntakeFeedCommand.IDLE);
    	Robot.intakeValves.intakeCommand.setFlipperState(true);
    	state = IDLE;
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
