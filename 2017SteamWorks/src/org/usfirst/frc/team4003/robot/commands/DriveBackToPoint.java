package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.utilities.Acceleration;

public class DriveBackToPoint extends DriveToPoint {
	public DriveBackToPoint(double x, double y, Acceleration accelerate, int timeout) {
		super(x, y, accelerate, timeout);
		addToYaw = 180;
	}
    
    public DriveBackToPoint(double x, double y, Acceleration accelerate, 
    		boolean coast, double slowDistance, int timeout) {
    	super(x,y,accelerate,coast, slowDistance, timeout);
    	addToYaw = 180;
    }
}
