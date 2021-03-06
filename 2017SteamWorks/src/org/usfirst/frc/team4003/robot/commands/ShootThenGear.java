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
    		addParallel(new ShootHighForTime(5000));
    		CommandGroup group = new CommandGroup();
    		group.addSequential(new WaitForTime(5000));
    		group.addSequential(new DriveToPoint(90,-15,new Acceleration(.2,.4,.02),true, 15, 5000));
    		//group.addSequential(new DriveToPoint(90,-18,new Acceleration(.2,.4,.02),true, 15, 5000));
    		group.addSequential(new RotateToHeading(6,0,.6));
    		group.addSequential(new AutonDriveToTarget(2500));
    		// leave out group.addSequential(new DriveToPoint(160, -20, new Acceleration(0.4, 0.4, 0), false, 15, 3000));
    		addSequential(group);
    		
    		boolean shoot = false;
    		if (shoot) {
	    		addSequential(new GearReleaseToggle(true));
	    		
	    		addSequential(new SwitchDirection());
	    		addSequential(new WaitForTime(1000));
	    		addSequential(new DriveForwardForDistance(7,0.4));
	    		addSequential(new RotateToHeading(75,0,0.6,false));
	    		addSequential(new DriveBackToPoint(300, -200, new Acceleration(.3,0.5,0.02),true,20,10000));
    		}
    		
    	} else {
    		Robot.shooter.setBlueSpeed();
    		addParallel(new ShootHighForTime(5000));
    		CommandGroup group = new CommandGroup();
    		group.addSequential(new WaitForTime(5000));
    		group.addSequential(new RotateToHeading(40, 0.65, 0, 300));
    		group.addSequential(new DriveToPoint(20,20, new Acceleration(0.3, 0.4, 0.02), false, 1, 2000));
    		group.addSequential(new RotateToHeading(70, 0, 0.7), 1500);
    		group.addSequential(new DriveToPoint(30, 68, new Acceleration(0.4, 0.4, 0.01), false, 15, 2000));
    		group.addSequential(new RotateToHeading(35, 0.6, 0));
    		group.addSequential(new AutonDriveToTarget());
    		//group.addSequential(new DriveToPoint(76, 133, new Acceleration(0.4, 0.4, 0), false, 15, 3000));
    		addSequential(group);
    		addSequential(new GearReleaseToggle(true));
    		
    		addSequential(new SwitchDirection());
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
