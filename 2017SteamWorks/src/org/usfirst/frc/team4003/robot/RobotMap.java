package org.usfirst.frc.team4003.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	// PWM
	public static final int BEATERS = 0;
	public static final int REENTRYFEED = 1;
	public static final int CLIMBDRUM = 2;
	
	// pneumatics
	public static final int SHIFTER = 1;
	public static final int INTAKEFLIPPER = 2;
	public static final int INTAKEREENTRY = 3;
	public static final int GEARRELEASE = 4;
}
