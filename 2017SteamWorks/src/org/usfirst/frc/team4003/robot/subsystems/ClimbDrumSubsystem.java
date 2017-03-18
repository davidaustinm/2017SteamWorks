package org.usfirst.frc.team4003.robot.subsystems;

import org.usfirst.frc.team4003.robot.RobotMap;
import org.usfirst.frc.team4003.robot.commands.ClimbDrumCommand;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ClimbDrumSubsystem extends Subsystem {

	Spark drum;
	public ClimbDrumSubsystem() {
		drum = new Spark(RobotMap.CLIMBDRUM);
		drum.setInverted(false);
	}
	
	public void setPower(double power) {
		//if (power < 0) power = 0;
		drum.set(power);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ClimbDrumCommand());
    }
}

