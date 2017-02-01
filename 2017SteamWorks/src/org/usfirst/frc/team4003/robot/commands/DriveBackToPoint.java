package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.utilities.Acceleration;

public class DriveBackToPoint extends DriveToPoint {
	public DriveBackToPoint(double x, double y, Acceleration accelerate) {
		super(x, y, accelerate);
		addToYaw = 180;
	}
    
    public DriveBackToPoint(double x, double y, Acceleration accelerate, boolean coast) {
    	super(x,y,accelerate,coast);
    	addToYaw = 180;
    }
}
