package org.usfirst.frc.team4003.robot;

public class AutonSelector {
	static final int COLOR = 0;
	static final int STARTPOS = 1;
	static final int ENDPOS = 2;
	int state = COLOR;
	
	boolean lastA = false;
	boolean lastB = false;
	
	int colorStateRed = 0;
	int colorStateBlue = 1;
	int colorState = colorStateRed;
	
	int startPosLeft = 0;
	int startPosCenter = 1;
	int startPosRight = 2;
	int startPosHopper = 3;
	int startPosState = startPosLeft;
	
	int endPosBoiler = 0;
	int endPosHopper = 1;
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
		
		startPosStates = new String[4];
		startPosStates[startPosLeft] = "L";
		startPosStates[startPosCenter] = "C";
		startPosStates[startPosRight] = "R";
		startPosStates[startPosHopper] = "H";
		
		endPosStates = new String[2];
		endPosStates[endPosBoiler] = "B";
		endPosStates[endPosHopper] = "H";
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
		String displayString = String.valueOf(state)+String.valueOf(colorState)+
				String.valueOf(startPosState)+String.valueOf(endPosState);
		digitBoard.display(displayString);
		System.out.println("state: " + String.valueOf(state));
		lastA = currentA;
		lastB = currentB;
	}
	
	public String getAllianceColor() {
		return colorStates[colorState];
	}
	public String getStartingPosition() {
		return startPosStates[startPosState];
	}
	public String getEndingPosition() {
		return endPosStates[endPosState];
	}
}
