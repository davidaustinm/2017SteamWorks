package org.usfirst.frc.team4003.robot.subsystems;

import org.usfirst.frc.team4003.robot.commands.ShooterCommand;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {
	CANTalon master = new CANTalon(3);
	CANTalon slave = new CANTalon(5);
	double SPEED = 2000;
	public ShooterCommand shooterCommand;
	public ShooterSubsystem() {
		master.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		master.changeControlMode(CANTalon.TalonControlMode.Speed);
		master.configNominalOutputVoltage(0, 0);
        master.configPeakOutputVoltage(12, -12);
        master.configEncoderCodesPerRev(420);
		master.setF(3.6535);
		master.setP(3);
		slave.changeControlMode(CANTalon.TalonControlMode.Follower);
		slave.set(master.getDeviceID());
	}
	public void set(boolean on){
		if(on) master.set(SPEED);
		else master.set(0);
	}
	public void setPower(double power) {
		//master.set(60);
		SmartDashboard.putNumber("shooterposition", master.getPosition());
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new ShooterCommand());
    	shooterCommand = new ShooterCommand();
    	setDefaultCommand(shooterCommand);
        
    }
    public double getSpeed() {
    	return master.getSpeed();
    }
    public int getClosedLoopError() {
    	return master.getClosedLoopError();
    }
}

