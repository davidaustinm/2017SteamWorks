package org.usfirst.frc.team4003.robot.subsystems;

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
	
	public Sensors (){
		leftDrive = new Encoder(0,1);
		leftDrive.setReverseDirection(true);
		rightDrive = new Encoder(2,3);
		navX = new AHRS(SerialPort.Port.kMXP);
	}
	
	public double getLeftDriveEncoder(){
		return leftDrive.get();
	}
	public double getRightDriveEncoder(){
		return rightDrive.get();
	}
	public double getYaw(){
		return -(navX.getYaw()-gyroOffset);
	}
	public void resetYaw(){
		gyroOffset = navX.getYaw();
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

