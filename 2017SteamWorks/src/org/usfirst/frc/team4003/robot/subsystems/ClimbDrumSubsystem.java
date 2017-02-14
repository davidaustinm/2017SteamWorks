package org.usfirst.frc.team4003.robot.subsystems;

import org.usfirst.frc.team4003.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ClimbDrumSubsystem extends Subsystem {

	Spark drum;
	public ClimbDrumSubsystem() {
		drum = new Spark(RobotMap.CLIMBDRUM);
	}
	
	public void setPower(double power) {
		drum.set(power);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ClimbDrumCommand());
    }
}
