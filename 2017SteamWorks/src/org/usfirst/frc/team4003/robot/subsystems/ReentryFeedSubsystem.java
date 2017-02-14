package org.usfirst.frc.team4003.robot.subsystems;

import org.usfirst.frc.team4003.robot.RobotMap;
import org.usfirst.frc.team4003.robot.commands.IntakeFeedCommand;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ReentryFeedSubsystem extends Subsystem {
	Spark feed;
	public ReentryFeedSubsystem() {
		feed = new Spark(RobotMap.REENTRYFEED);
	}
	
	public void setPower(double power) {
		feed.set(power);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new IntakeFeedCommand());
    }
}

