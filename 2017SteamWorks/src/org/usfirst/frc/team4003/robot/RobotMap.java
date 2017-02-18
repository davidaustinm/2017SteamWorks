package org.usfirst.frc.team4003.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// CAN
	public static final int LEFTFRONT = 1;
	public static final int LEFTBACK = 2;
	public static final int RIGHTFRONT = 3;
	public static final int RIGHTTBACK = 4;
	
	// DIO
	public static final int LEFTDRIVEENCODERA = 0;
	public static final int LEFTDRIVEENCODERB = 1;
	public static final int RIGHTDRIVEENCODERA = 2;
	public static final int RIGHTDRIVEENCODERB = 3;
	public static final int HORIZONTALDRUMSWITCH = 4;
	public static final int VERTICALDRUMSWITCH = 5;
	
	// PWM
	public static final int BEATERS = 0;
	public static final int REENTRYFEED = 1;
	public static final int CLIMBDRUM = 2;
	public static final int AGITATOR = 3;
	public static final int SHOOTERFEED = 4;
	
	// pneumatics
	public static final int SHIFTER = 1;
	public static final int INTAKEFLIPPER = 2;
	public static final int INTAKEREENTRY = 3;
	public static final int GEARRELEASE = 4;
}
