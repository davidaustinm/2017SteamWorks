package org.usfirst.frc.team4003.robot.commands;

import edu.wpi.first.wpilibj.command.*;

/**
 *
 */
public class AutoLowBoiler extends CommandGroup {

    public AutoLowBoiler() {
        addSequential(new FeedLowBoiler());
        addParallel(new AutonRunBeater(5000));
    }

}
