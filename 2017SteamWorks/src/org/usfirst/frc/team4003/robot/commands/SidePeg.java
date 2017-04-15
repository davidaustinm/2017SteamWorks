package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.utilities.Acceleration;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SidePeg extends CommandGroup {
	public static int LEFT = 0;
	public static int RIGHT = 1;
    public SidePeg(int side) {
    	System.out.println("In side peg");
    	//addSequential(new DriveToPoint(70, 0, new Acceleration(.1, .55, 0.02), false, 10, 3000));
    	addSequential(new DriveToPoint(59, 0, new Acceleration(.1, .55, 0.02), false, 10, 3000));
    	addSequential(new WaitForTime(100));
    	if(side == RIGHT) {
    		addSequential(new RotateToHeading(50, .5, .5));
    	} else {
    		addSequential(new RotateToHeading(-50, .5, .5));
    	}
    	//if (side == RIGHT) addSequential(new AutoRotateToTarget(AutoRotateToTarget.LEFT, 55));
    	//else addSequential(new AutoRotateToTarget(AutoRotateToTarget.RIGHT, -55));
    	
    	addSequential(new AutonDriveToTarget(3000));
    	addSequential(new GearReleaseToggle(true));
    	addSequential(new WaitForTime(1000));
    	addSequential(new SwitchDirection());
    	addSequential(new DriveForwardForDistance(7, .4));
    	
    	if (side == LEFT && Robot.sensors.isAllianceColorRed()) {
    		addSequential(new RotateToHeading(-170, 0.5, 0));
        	addSequential(new DriveBackToPoint(360, -20, new Acceleration(0.2, 0.5, 0.02), true, 20, 7000));
    	}
    	if (side == RIGHT && Robot.sensors.isAllianceColorRed() == false) {
    		addSequential(new RotateToHeading(170, 0, 0.5));
        	addSequential(new DriveBackToPoint(360, 20, new Acceleration(0.2, 0.5, 0.02), true, 20, 7000));
    	}
    	if (side == RIGHT && Robot.sensors.isAllianceColorRed()){
    		addSequential(new RotateToHeading(170,0,0.6,false));
    		addSequential(new DriveBackToPoint(170, 20, new Acceleration(.3,0.5,0.02),false,20,10000));
    		addSequential(new DriveBackToPoint(380, 160, new Acceleration(.3,0.5,0.02),true,20,10000));
    	}
    	if (side == LEFT && Robot.sensors.isAllianceColorRed() == false) {
    		addSequential(new RotateToHeading(-170,0.6,0,false));
    		addSequential(new DriveBackToPoint(170, -20, new Acceleration(.3,0.5,0.02),false,20,10000));
    		addSequential(new DriveBackToPoint(380, -160, new Acceleration(.3,0.5,0.02),true,20,10000));
    	}
    	
    	
    	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
