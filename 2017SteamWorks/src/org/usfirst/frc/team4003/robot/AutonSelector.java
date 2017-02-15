package org.usfirst.frc.team4003.robot;

public class AutonSelector {
	static final int COLOR = 0;
	static final int STARTPOS = 1;
	static final int ENDPOS = 2;
	int state = COLOR;
	boolean lastA = false;
	boolean lastB = false;
	int colorState = 0;
	int startPosState = 0;
	int endPosState = 0;
	String[] colorStates = {"0", "1"};
	String[] startPosStates = {"0", "1", "2"};
	String[] endPosStates = {"0", "1"};
	String[] stateString = {"C", "S", "E"};
	REVDigitBoard digitBoard = new REVDigitBoard();
	public void update() {
		boolean currentA = digitBoard.getAButton();
		boolean currentB = digitBoard.getBButton();
		
		if (!lastA && currentA) {
			state++;
			if (state == 3) state = 0;
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
}
