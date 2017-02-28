package org.usfirst.frc.team4003.robot.commands;

import org.usfirst.frc.team4003.robot.utilities.Acceleration;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToBlueBoilerHopper extends CommandGroup {

    public DriveToBlueBoilerHopper() {
    	addSequential(new DriveToPoint(70,0, new Acceleration(.3,.75,0.02),false,10, 2000));
    	addSequential(new RotateToHeading(85,.50,0));
    	addSequential(new DriveForwardForDistance(12,.5));
    	addSequential(new WaitForTime(1500));
    	addSequential(new SwitchDirection());
    	addSequential(new RotateToHeading(45,0,0.5));
    	addSequential(new DriveForwardForDistance(50,.8));
    	addSequential(new RotateToHeading(-45,0,0.5));
    	addSequential(new DriveForwardForDistance(10,.6));
    	addSequential(new SwitchDirection());
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
