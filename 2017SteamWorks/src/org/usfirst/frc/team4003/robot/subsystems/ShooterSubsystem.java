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
	CANTalon shooter0 = new CANTalon(3);
	CANTalon shooter1 = new CANTalon(5);
	public ShooterSubsystem() {
		shooter0.setFeedbackDevice(FeedbackDevice.QuadEncoder);
	}
	public void setPower(double power) {
		shooter0.set(power);
		SmartDashboard.putNumber("shooterposition", shooter0.getPosition());
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ShooterCommand());
        
    }
    public double getSpeed() {
    	return shooter0.getSpeed();
    }
}

