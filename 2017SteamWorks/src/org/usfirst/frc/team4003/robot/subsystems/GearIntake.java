package org.usfirst.frc.team4003.robot.subsystems;

import org.usfirst.frc.team4003.robot.RobotMap;
import org.usfirst.frc.team4003.robot.commands.GearIntakeCommand;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearIntake extends Subsystem {
	Solenoid gearIntake = new Solenoid(RobotMap.GEARINTAKE);
	boolean on = false;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public void setState(boolean on) {
		this.on = on;
	}
	
	public void set() {
		gearIntake.set(on);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new GearIntakeCommand());
    }
}

