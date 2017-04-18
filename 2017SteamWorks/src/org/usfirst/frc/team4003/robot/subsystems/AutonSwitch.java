package org.usfirst.frc.team4003.robot.subsystems;

import org.usfirst.frc.team4003.robot.utilities.AutonInterface;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class AutonSwitch extends Subsystem implements AutonInterface{
	DigitalInput[] switches;
	public AutonSwitch() {
		
		switches = new DigitalInput[] {
			new DigitalInput(23), // switch 6 -- 0
			new DigitalInput(13), // switch 5 -- 1
			new DigitalInput(19), // switch 4 -- 2
			new DigitalInput(20), // switch 3 -- 3
			new DigitalInput(21), // switch 2 -- 4
			new DigitalInput(22)  // switch 1 -- 5
		};
		/*
		switches = new DigitalInput[] {
				new DigitalInput(4), // switch 6 -- 0
				new DigitalInput(5), // switch 5 -- 1
				new DigitalInput(6), // switch 4 -- 2
				new DigitalInput(7), // switch 3 -- 3
				new DigitalInput(8), // switch 2 -- 4
				new DigitalInput(9)  // switch 1 -- 5
			};
		*/
	}
	
	public boolean[] getSwitches() {
		boolean[] result = new boolean[6];
		for (int i = 0; i < switches.length; i++) {
			result[i] = switches[i].get();
		}
		return result;
	}
	
	public boolean getSwitch(int i) {
		return !switches[6-i].get();
	}
	
	public void printSwitches() {
		String s = "";
		for (int i = 0; i < switches.length; i++) {
			String r = String.valueOf(switches[i].get());
			while (r.length() < 6) r = " " + r;
			s = s + r;
		}
		System.out.println(s);
	}

	String[] colorStates = new String[] {"R", "B"};
	String[] startPosStates = new String[] {"L", "C", "R", "H", "B", "N"};
	String[] endPosStates = new String[] {"B", "L", "H", "G"};
	
	public int getInt(int[] s) {
		int result = 0;
		for (int i = 0; i < s.length; i++) {
			result = 2*result;
			if (getSwitch(s[i])) result ++;
		}
		return result;
	}
	
	public String getAllianceColor() {
		int value = getInt(new int[]{1});
		return colorStates[value];
		//return "R";
	}
	public String getStartingPosition() {
		int value = getInt(new int[]{2,3,4});
		if (value >= 5) value = 5;
		return startPosStates[value];
		//return "L";
	}
	public String getEndingPosition() {
		int value = getInt(new int[]{5,6});
		return endPosStates[value];
		//return "G";
	}
	
	public String getAutonString() {
		String string = getAllianceColor() + " ";
		string = string + getStartingPosition() + " ";
		string = string + getEndingPosition();
		return string;
	}
	
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

