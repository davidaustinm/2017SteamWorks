package org.usfirst.frc.team4003.robot.subsystems;

import org.usfirst.frc.team4003.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Sensors extends Subsystem {

	Encoder leftDrive, rightDrive;
	AHRS navX;
	double gyroOffset = 0;
	double leftEncoderOffset = 0;
	double rightEncoderOffset = 0;
	double XCoordinate = 0;
	double YCoordinate = 0;
	double lastLeftEncoder = 0, lastRightEncoder = 0;
	double EncoderTicPerInch = 6;		//tested at 600 tics over 100 inches
	
	public static final int BLUE = 1;
	public static final int RED = 2;
	public int allianceColor = BLUE;
	
	public void setAllianceColor(int color){
		allianceColor = color;
	}
	public int getAllianceColor(){
		return allianceColor;
	}
	
	public boolean isAllianceColorRed(){
		return allianceColor == RED;
	}
	
	public Sensors (){
		leftDrive = new Encoder(RobotMap.LEFTDRIVEENCODERA, RobotMap.LEFTDRIVEENCODERB);
		leftDrive.setReverseDirection(false);
		rightDrive = new Encoder(RobotMap.RIGHTDRIVEENCODERA, RobotMap.RIGHTDRIVEENCODERB);
		rightDrive.setReverseDirection(true);
		navX = new AHRS(SerialPort.Port.kMXP);
	}
	
	public double getLeftDriveEncoder(){
		return leftDrive.get() - leftEncoderOffset;
	}
	public double getRightDriveEncoder(){
		return rightDrive.get() - rightEncoderOffset;
	}
	public double getYaw(){
		return -(navX.getYaw()-gyroOffset);
	}
	public void resetYaw(){
		gyroOffset = navX.getYaw();
	}
	public void resetEncoder(){
		leftEncoderOffset = leftDrive.get();
		rightEncoderOffset = rightDrive.get();
		lastLeftEncoder = 0;
		lastRightEncoder = 0;
	}
	public void updatePosition(){
		double currentLeftEncoder = getLeftDriveEncoder();
		double currentRightEncoder = getRightDriveEncoder();
		double changeInLeftEncoder = currentLeftEncoder - lastLeftEncoder;
		double changeInRightEncoder = currentRightEncoder - lastRightEncoder;
		double encoderDistance = (changeInLeftEncoder + changeInRightEncoder)/2;
		double heading = Math.toRadians(getYaw());
		double changeInX = encoderDistance * Math.cos(heading);
		double changeInY = encoderDistance * Math.sin(heading);
		XCoordinate += changeInX;
		YCoordinate += changeInY;
		lastLeftEncoder = currentLeftEncoder;
		lastRightEncoder = currentRightEncoder;
	}
	public double getXCoordinate(){
		return XCoordinate/EncoderTicPerInch;
	}
	public double getYCoordinate(){
		return YCoordinate/EncoderTicPerInch;
	}
	public void resetPosition(){
		XCoordinate = 0;
		YCoordinate = 0;
	}
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

