package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.utilities.Acceleration;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShootThenGear extends CommandGroup {

    public ShootThenGear() {
    	if(Robot.sensors.isAllianceColorRed()){
    		addSequential(new DriveToPoint(100,-33,new Acceleration(.2,.5,.02),true, 15, 5000));
    		addSequential(new RotateToHeading(7,0,.5));
    		addSequential(new DriveToPoint(160, -20, new Acceleration(0.4, 0.4, 0), false, 15, 3000));
    		addSequential(new SwitchDirection());
    		
    		//addSequential(new PlaceGear());
    		
    		addSequential(new WaitForTime(1000));
    		addSequential(new DriveForwardForDistance(7,0.4));
    		addSequential(new RotateToHeading(75,0,0.6,false));
    		addSequential(new DriveBackToPoint(300, -200, new Acceleration(.3,0.5,0.02),true,20,10000));
    		
    		
    	} else {
    		addSequential(new RotateToHeading(80, 0, 0.6), 2000);
    		addSequential(new DriveToPoint(20, 105, new Acceleration(0.4, 0.5, 0.01), false, 15, 2000));
    		addSequential(new RotateToHeading(30, 0.6, 0));
    		addSequential(new DriveToPoint(76, 133, new Acceleration(0.4, 0.4, 0), false, 15, 3000));
    		addSequential(new SwitchDirection());
    		
    		//addSequential(new PlaceGear());
    		
    		addSequential(new WaitForTime(1000));
    		addSequential(new DriveForwardForDistance(7,0.4));
    		addSequential(new RotateToHeading(-75,0.6,0,false));
    		addSequential(new DriveBackToPoint(0, 380, new Acceleration(.3,0.5,0.02),true,20,10000));
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
