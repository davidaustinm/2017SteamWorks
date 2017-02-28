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

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Solenoid flipper;
	boolean flipperState = false;
	public IntakeValueCommand intakeCommand;
	public IntakeValves() {
		flipper = new Solenoid(RobotMap.INTAKEFLIPPER);
	}
	
	public void setFlipper(boolean on) {
		on = !on;
		flipperState = on;
		flipper.set(on);
	}
	
    public void initDefaultCommand() {
    	intakeCommand = new IntakeValueCommand();
    	setDefaultCommand(intakeCommand);
    }
}

