package org.usfirst.frc.team4003.robot.subsystems;

import org.usfirst.frc.team4003.robot.commands.TankDrive;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TalonDriveTrain extends Subsystem {
	Talon left1, left2, right1, right2;
	public TalonDriveTrain() {
		left1 = new Talon(0);
		left2 = new Talon(1);
		right1 = new Talon(2);
		right1.setInverted(true);
		right2 = new Talon(3);
		right2.setInverted(true);
	}
	
	public void setPower(double left, double right) {
		left1.set(left);
		left2.set(left);
		right1.set(right);
		right2.set(right);
	}
	
	public void switchDirection(){
		Talon temp = left1;
		left1 = right1;
		right1 = temp;
		temp = left2;
		left2 = right2;
		right2 = temp;
		left1.setInverted(!left1.getInverted());
		left2.setInverted(!left2.getInverted());
		right1.setInverted(!right1.getInverted());
		right2.setInverted(!right2.getInverted());
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new TankDrive());
    	
    }
}

