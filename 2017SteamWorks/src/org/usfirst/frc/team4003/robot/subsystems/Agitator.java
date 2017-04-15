package org.usfirst.frc.team4003.robot.subsystems;

import org.usfirst.frc.team4003.robot.RobotMap;
import org.usfirst.frc.team4003.robot.commands.AgitatorCommand;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Agitator extends Subsystem {
	public AgitatorCommand agitatorCommand;
	Spark agitator;
	public Agitator() {
		agitator = new Spark(RobotMap.AGITATOR);
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public void setPower(double power) {
		agitator.set(power);
	}
	
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	agitatorCommand = new AgitatorCommand();
        setDefaultCommand(agitatorCommand);
    }
}

