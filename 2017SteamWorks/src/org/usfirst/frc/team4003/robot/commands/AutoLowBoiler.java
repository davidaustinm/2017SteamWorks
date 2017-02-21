package org.usfirst.frc.team4003.robot.commands;

import edu.wpi.first.wpilibj.command.*;

/**
 *
 */
public class AutoLowBoiler extends CommandGroup {

    public AutoLowBoiler() {
    	addParallel(new AutonRunBeater(8000));
        addSequential(new FeedLowBoiler());
    }

}
