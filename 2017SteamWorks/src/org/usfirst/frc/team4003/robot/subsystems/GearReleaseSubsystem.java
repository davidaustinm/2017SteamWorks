package org.usfirst.frc.team4003.robot.subsystems;

import org.usfirst.frc.team4003.robot.RobotMap;
import org.usfirst.frc.team4003.robot.commands.GearReleaseCommand;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearReleaseSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Solenoid gearRelease;
	public GearReleaseSubsystem() {
		gearRelease = new Solenoid(RobotMap.GEARRELEASE);
	}
	public void set(boolean on) {
		gearRelease.set(on);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new GearReleaseCommand());
    }
}

