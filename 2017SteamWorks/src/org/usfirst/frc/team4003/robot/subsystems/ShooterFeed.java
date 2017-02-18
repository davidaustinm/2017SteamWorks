package org.usfirst.frc.team4003.robot.subsystems;

import org.usfirst.frc.team4003.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterFeed extends Subsystem {
	Spark shooterFeed;
	public ShooterFeed() {
		shooterFeed = new Spark(RobotMap.SHOOTERFEED);
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public void setPower(double power) {
		shooterFeed.set(power);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

