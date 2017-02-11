package org.usfirst.frc.team4003.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pneunamatics extends Subsystem {
	public DoubleSolenoid exampleDouble = new DoubleSolenoid(0, 1);

    public void initDefaultCommand() {
        
    }
}

