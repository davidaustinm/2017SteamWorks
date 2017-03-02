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
	public GearReleaseCommand gearReleaseCommand;
	Solenoid gearRelease;
	boolean state = false;
	public GearReleaseSubsystem() {
		gearRelease = new Solenoid(RobotMap.GEARRELEASE);
		
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public void set() {
		//System.out.println(state);
		gearRelease.set(state);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	gearReleaseCommand = new GearReleaseCommand();
        setDefaultCommand(gearReleaseCommand);
    }
}

