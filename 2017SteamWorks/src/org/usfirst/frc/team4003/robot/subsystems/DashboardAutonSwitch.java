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
    	String string = SmartDashboard.getString("DB/String 3", "");
    	if (string.length() == 0) return "";
    	string = string.substring(0, 1);
    	return "B";
		//return string;
		//return "R";
	}
	public String getStartingPosition() {
		String string = SmartDashboard.getString("DB/String 1", "N");
		if (string.length() == 0) return "";
		string = string.substring(0, 1);
		return string;
		//return "L";
	}
	public String getEndingPosition() {
		String string = SmartDashboard.getString("DB/String 2", "B");
		if (string.length() == 0) return "";
		string = string.substring(0, 1);
		return string;
		//return "G";
	}
}

