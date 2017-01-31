package org.usfirst.frc.team4003.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToLeftLift extends CommandGroup {

    public DriveToLeftLift() {
    	
    	addSequential(new DriveToPoint(60,0,.3,true));
    	addSequential(new RotateToHeading(-60,.5,0,true));
    	addSequential(new DriveToTarget(.3));
    	//addSequential(new RotateToHeading(20,.3,0,true));
    	addSequential(new SwitchDirection());
    	addSequential(new DriveToPoint(20,-28,.3));
    	
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
