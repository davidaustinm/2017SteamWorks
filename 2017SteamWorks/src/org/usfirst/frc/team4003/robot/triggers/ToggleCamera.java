package org.usfirst.frc.team4003.robot.triggers;

import org.usfirst.frc.team4003.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class ToggleCamera extends Trigger {

    public boolean get() {
        return Robot.oi.driver.getXButton();
    }
}
