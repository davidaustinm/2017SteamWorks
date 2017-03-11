package org.usfirst.frc.team4003.robot.subsystems;

import org.usfirst.frc.team4003.robot.RobotMap;
import org.usfirst.frc.team4003.robot.commands.BeaterCommand;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BeaterSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Spark beaters;
	public BeaterSubsystem() {
		beaters = new Spark(RobotMap.BEATERS);
	}
	
	public void setPower(double power) {
		//System.out.println("    " + power);
		beaters.set(power);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new BeaterCommand());
    }
}

