package org.usfirst.frc.team4003.robot.subsystems;

import org.usfirst.frc.team4003.robot.RobotMap;
import org.usfirst.frc.team4003.robot.commands.GearShiftCommand;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShifterSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Solenoid shifterValve;
	public ShifterSubsystem() {
		shifterValve = new Solenoid(RobotMap.SHIFTER);
	}
	
	public void set(boolean on) {
		shifterValve.set(on);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new GearShiftCommand());
    }
}

