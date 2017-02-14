package org.usfirst.frc.team4003.robot.subsystems;

import org.usfirst.frc.team4003.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeValves extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Solenoid flipper;
	Solenoid reentry;
	public IntakeValves() {
		flipper = new Solenoid(RobotMap.INTAKEFLIPPER);
		reentry = new Solenoid(RobotMap.INTAKEREENTRY);
	}
	
	public void setFlipper(boolean on) {
		flipper.set(on);
	}
	public void setReentry(boolean on) {
		reentry.set(on);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new IntakeValueCommand());
    }
}

