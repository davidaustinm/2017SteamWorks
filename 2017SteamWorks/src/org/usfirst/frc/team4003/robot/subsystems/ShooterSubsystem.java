package org.usfirst.frc.team4003.robot.subsystems;

import org.usfirst.frc.team4003.robot.RobotMap;
import org.usfirst.frc.team4003.robot.commands.ShooterCommand;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {
	CANTalon master = new CANTalon(RobotMap.SHOOTERMASTER);
	CANTalon slave = new CANTalon(RobotMap.SHOOTERSLAVE);
	double SPEED = 3500;
	double BLUESPEED = 4000;
	double speed = SPEED;
	public ShooterCommand shooterCommand;
	public ShooterSubsystem() {        
        master.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
    	master.configNominalOutputVoltage(0, 0);
        master.configPeakOutputVoltage(12, -12);
        master.setProfile(0);
        master.setF(0.0361);
        master.setI(0);
        master.setP(0.05);
        master.setD(0);
        master.changeControlMode(TalonControlMode.Speed);
        
		slave.changeControlMode(CANTalon.TalonControlMode.Follower);
		slave.set(master.getDeviceID());
	}
	
	public void setBlueSpeed() {
		speed = BLUESPEED;
	}
	
	public void resetSpeed() {
		speed = SPEED;
	}
	
	public void set(boolean on){
		if(on) master.set(SPEED);
		else master.set(0);
	}
	
	 
	public void setPower(double power) {
		//SmartDashboard.putNumber("shooterposition", master.getPosition());
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new ShooterCommand());
    	shooterCommand = new ShooterCommand();
    	//setDefaultCommand(shooterCommand);
        
    }
    public double getSpeed() {
    	return master.getSpeed();
    }
    public boolean isAtSpeed() {
    	return master.getSpeed() > 0.8*SPEED;
    }
    public int getClosedLoopError() {
    	return master.getClosedLoopError();
    }
}

