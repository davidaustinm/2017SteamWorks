package org.usfirst.frc.team4003.robot.subsystems;

import org.usfirst.frc.team4003.robot.utilities.AutonInterface;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DashboardAutonSwitch extends Subsystem implements AutonInterface {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public String getAllianceColor() {
		return SmartDashboard.getString("DB/String 0", "");
		//return "R";
	}
	public String getStartingPosition() {
		return SmartDashboard.getString("DB/String 1", "N");
		//return "L";
	}
	public String getEndingPosition() {
		return SmartDashboard.getString("DB/String 2", "B");
		//return "G";
	}
}

