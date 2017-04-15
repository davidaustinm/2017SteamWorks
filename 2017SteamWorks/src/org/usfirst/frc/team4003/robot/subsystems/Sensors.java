package org.usfirst.frc.team4003.robot.subsystems;

import org.usfirst.frc.team4003.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Sensors extends Subsystem {

	Encoder leftDrive, rightDrive;
	AHRS navX;
	DigitalInput horizontalDrumSwitch;
	DigitalInput verticalDrumSwitch;
	double gyroOffset = 0;
	double pitchOffset = 0;
	double rollOffset = 0;
	double leftEncoderOffset = 0;
	double rightEncoderOffset = 0;
	double XCoordinate = 0;
	double YCoordinate = 0;
	double lastLeftEncoder = 0, lastRightEncoder = 0;
	// change this! (done)
	double EncoderTicPerInch = 6;		//tested at 600 tics over 100 inches
	//double EncoderTicPerInch = 33;
	double bumperOffset = 3;
	
	public void setBumperOffset(double bo) {
		bumperOffset = bo;
	}
	
	public static final int BLUE = 1;
	public static final int RED = 2;
	public int allianceColor = RED;
	
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
		leftDrive.setReverseDirection(true);
		//leftDrive.setReverseDirection(false);
		rightDrive = new Encoder(RobotMap.RIGHTDRIVEENCODERA, RobotMap.RIGHTDRIVEENCODERB);
		rightDrive.setReverseDirection(false);
		//rightDrive.setReverseDirection(true);
		navX = new AHRS(SerialPort.Port.kMXP);
		horizontalDrumSwitch = new DigitalInput(RobotMap.HORIZONTALDRUMSWITCH);
		verticalDrumSwitch = new DigitalInput(RobotMap.VERTICALDRUMSWITCH);
	}
	
	public boolean getHorizontalDrumSwitch(){
		return !horizontalDrumSwitch.get();
	}
	public boolean getVerticalDrumSwitch(){
		return !verticalDrumSwitch.get();
	}
	public double getLeftDriveEncoder(){
		return leftDrive.get() - leftEncoderOffset;
	}
	public double getRightDriveEncoder(){
		return rightDrive.get() - rightEncoderOffset;
	}
	public double getYaw(){
		double yaw = -(navX.getYaw()-gyroOffset);
		while(yaw > 180){
			yaw -= 360;
		}
		while(yaw < -180){
			yaw += 360;
		}
		return yaw;
	}
	public double getPitch(){
		return navX.getPitch() - pitchOffset;
	}
	public double getRoll(){
		return navX.getRoll() - rollOffset;
	}
	public void resetYaw(){
		gyroOffset = navX.getYaw();
		pitchOffset = navX.getPitch();
		rollOffset = navX.getRoll();
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
		return XCoordinate/EncoderTicPerInch + bumperOffset;
		//return XCoordinate/EncoderTicPerInch;
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

