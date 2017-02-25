package org.usfirst.frc.team4003.robot;

import org.usfirst.frc.team4003.robot.utilities.AutonInterface;

public class AutonSelector implements AutonInterface{
	static final int COLOR = 0;
	static final int STARTPOS = 1;
	static final int ENDPOS = 2;
	int state = COLOR;
	
	boolean lastA = true;
	boolean lastB = true;
	
	int colorStateRed = 0;
	int colorStateBlue = 1;
	int colorState = colorStateRed;
	
	int startPosLeft = 0;
	int startPosCenter = 1;
	int startPosRight = 2;
	int startPosHopper = 3;
	int startPosBaseline = 4;
	int startNothing = 5;
	int startPosState = startPosLeft;
	
	int endPosBoiler = 0;
	int endPosLow = 1;
	int endPosHopper = 2;
	int endPosGear = 3;
	int endPosState = endPosBoiler;
	
	String[] colorStates;
	String[] startPosStates;
	String[] endPosStates;
	String[] stateString = {"C", "S", "E"};

	REVDigitBoard digitBoard = new REVDigitBoard();
	
	public AutonSelector() {
		colorStates = new String[2];
		colorStates[colorStateRed] = "R";
		colorStates[colorStateBlue] = "B";
		
		startPosStates = new String[6];
		startPosStates[startPosLeft] = "L";
		startPosStates[startPosCenter] = "C";
		startPosStates[startPosRight] = "R";
		startPosStates[startPosHopper] = "H";
		startPosStates[startPosBaseline] = "B";
		startPosStates[startNothing] = "N";
		
		endPosStates = new String[4];
		endPosStates[endPosBoiler] = "B";
		endPosStates[endPosLow] = "L";
		endPosStates[endPosHopper] = "H";
		endPosStates[endPosGear] = "G";
	}
	
	public void update() {
		boolean currentA = digitBoard.getAButton();
		boolean currentB = digitBoard.getBButton();
		
		if (!lastA && currentA) {
			state++;
			if (state == stateString.length) state = 0;
		}
		if (!lastB && currentB) {
			switch(state){
				case COLOR:
					colorState++;
					if (colorState == colorStates.length) {
						colorState = 0;
					}
					break;
				case STARTPOS:
					startPosState++;
					if (startPosState == startPosStates.length) startPosState = 0;
					break;
				case ENDPOS:
					endPosState++;
					if (endPosState == endPosStates.length) endPosState = 0;
					break;
			}
		}
		String displayString = stateString[state] + colorStates[colorState] + startPosStates[startPosState]
				+ endPosStates[endPosState];
		digitBoard.display(displayString);
//		System.out.println("state: " + String.valueOf(state));
		lastA = currentA;
		lastB = currentB;
	}
	
	public String getAllianceColor() {
		return colorStates[colorState];
		//return "R";
	}
	public String getStartingPosition() {
		return startPosStates[startPosState];
		//return "L";
	}
	public String getEndingPosition() {
		return endPosStates[endPosState];
		//return "G";
	}
}
