package org.usfirst.frc.team4003.robot.subsystems;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.RobotMap;
import org.usfirst.frc.team4003.robot.commands.IntakeValueCommand;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeValves extends Subsystem {
	
	Timer feedTimer;
	
	long feedTime;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Solenoid flipper;
	boolean flipperState = false;
	public IntakeValueCommand intakeCommand;
	//Solenoid reentry;
	public IntakeValves() {
		feedTimer = new Timer();
		feedTime = System.currentTimeMillis();
		flipper = new Solenoid(RobotMap.INTAKEFLIPPER);
		//reentry = new Solenoid(RobotMap.INTAKEREENTRY);
	}
	
	public void setFlipper(boolean on) {
		on = !on;
		if(flipperState = true && !on){
//			feedTime = System.currentTimeMillis() + 500;
		}
		flipperState = on;
		flipper.set(on);
	}
	public boolean isLowBoilerFeedOn() {
//		System.out.println(flipperState + " | Time: " + feedTimer.get());
		return flipperState && feedTimer.get() >= 0.5;
	}
	/*public void setReentry(boolean on) {
		reentry.set(on);
	}*/

	public void resetTimer() {
		feedTimer.start();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new IntakeValueCommand());
    	feedTimer.start();
    	
    	intakeCommand = new IntakeValueCommand();
    	setDefaultCommand(intakeCommand);
    }
}

