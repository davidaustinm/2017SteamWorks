package org.usfirst.frc.team4003.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C.Port;

public class REVDigitBoard {
	
	private I2C i2c;
		
	private DigitalInput buttonA;
	private DigitalInput buttonB;
			
	private AnalogInput pot;
	
	private HashMap<String, byte[]> charRegistry;
	
	private byte[] chars;
	
	public REVDigitBoard() {
		i2c = new I2C(Port.kMXP, 0x70);
		
		buttonA = new DigitalInput(10);
		buttonB = new DigitalInput(11);
		
		pot = new AnalogInput(0);
		
		byte[] osc = new byte[1];
    	byte[] blink = new byte[1];
    	byte[] bright = new byte[1];
    	
    	osc[0] = (byte)0x21;
    	blink[0] = (byte)0x81;
    	bright[0] = (byte)0xEF;

    	i2c.writeBulk(osc);
    	Timer.delay(.01);
    	i2c.writeBulk(bright);
    	Timer.delay(.01);
    	i2c.writeBulk(blink);
    	Timer.delay(.01);
    	
    	charRegistry = new HashMap<>();

    	charRegistry.put("1", new byte[]{ (byte)0b00000110, (byte)0b00000000 });
    	charRegistry.put("2", new byte[]{ (byte)0b11011011, (byte)0b00000000 });
    	charRegistry.put("3", new byte[]{ (byte)0b11001111, (byte)0b00000000 });
    	charRegistry.put("4", new byte[]{ (byte)0b11100110, (byte)0b00000000 });
    	charRegistry.put("5", new byte[]{ (byte)0b11101101, (byte)0b00000000 });
    	charRegistry.put("6", new byte[]{ (byte)0b11111101, (byte)0b00000000 });
    	charRegistry.put("7", new byte[]{ (byte)0b00000111, (byte)0b00000000 });
    	charRegistry.put("8", new byte[]{ (byte)0b11111111, (byte)0b00000000 });
    	charRegistry.put("9", new byte[]{ (byte)0b11101111, (byte)0b00000000 });
    	charRegistry.put("0", new byte[]{ (byte)0b00111111, (byte)0b00000000 });
    	charRegistry.put("A", new byte[]{ (byte)0b11110111, (byte)0b00000000 });
    	/*
        charreg[11][0] = (byte)0b10001111; charreg[11][1] = (byte)0b00010010; //B
        charmap.put('B',11);
        charreg[12][0] = (byte)0b00111001; charreg[12][1] = (byte)0b00000000; //C
        charmap.put('C',12);
        charreg[13][0] = (byte)0b00001111; charreg[13][1] = (byte)0b00010010; //D
        charmap.put('D',13);
        charreg[14][0] = (byte)0b11111001; charreg[14][1] = (byte)0b00000000; //E
        charmap.put('E',14);
        charreg[15][0] = (byte)0b11110001; charreg[15][1] = (byte)0b00000000; //F
        charmap.put('F',15);
        charreg[16][0] = (byte)0b10111101; charreg[16][1] = (byte)0b00000000; //G
        charmap.put('G',16);
        charreg[17][0] = (byte)0b11110110; charreg[17][1] = (byte)0b00000000; //H
        charmap.put('H',17);
        charreg[18][0] = (byte)0b00001001; charreg[18][1] = (byte)0b00010010; //I
        charmap.put('I',18);
        charreg[19][0] = (byte)0b00011110; charreg[19][1] = (byte)0b00000000; //J
        charmap.put('J',19);
        charreg[20][0] = (byte)0b01110000; charreg[20][1] = (byte)0b00001100; //K
        charmap.put('K',20);
        charreg[21][0] = (byte)0b00111000; charreg[21][1] = (byte)0b00000000; //L
        charmap.put('L',21);
        charreg[22][0] = (byte)0b00110110; charreg[22][1] = (byte)0b00000101; //M
        charmap.put('M',22);
        charreg[23][0] = (byte)0b00110110; charreg[23][1] = (byte)0b00001001; //N
        charmap.put('N',23);
        charreg[24][0] = (byte)0b00111111; charreg[24][1] = (byte)0b00000000; //O
        charmap.put('O',24);
        charreg[25][0] = (byte)0b11110011; charreg[25][1] = (byte)0b00000000; //P
        charmap.put('P',25);
        charreg[26][0] = (byte)0b00111111; charreg[26][1] = (byte)0b00001000; //Q
        charmap.put('Q',26);
        charreg[27][0] = (byte)0b11110011; charreg[27][1] = (byte)0b00001000; //R
        charmap.put('R',27);
        charreg[28][0] = (byte)0b10001101; charreg[28][1] = (byte)0b00000001; //S
        charmap.put('S',28);
        charreg[29][0] = (byte)0b00000001; charreg[29][1] = (byte)0b00010010; //T
        charmap.put('T',29);
        charreg[30][0] = (byte)0b00111110; charreg[30][1] = (byte)0b00000000; //U
        charmap.put('U',30);
        charreg[31][0] = (byte)0b00110000; charreg[31][1] = (byte)0b00100100; //V
        charmap.put('V',31);
        charreg[32][0] = (byte)0b00110110; charreg[32][1] = (byte)0b00101000; //W
        charmap.put('W',32);
        charreg[33][0] = (byte)0b00000000; charreg[33][1] = (byte)0b00101101; //X
        charmap.put('X',33);
        charreg[34][0] = (byte)0b00000000; charreg[34][1] = (byte)0b00010101; //Y
        charmap.put('Y',34);
        charreg[35][0] = (byte)0b00001001; charreg[35][1] = (byte)0b00100100; //Z
        charmap.put('Z',35);
        charreg[36][0] = (byte)0b00000000; charreg[36][1] = (byte)0b00000000; //space
        */
    	
    	chars = new byte[10];
	}
	
	public void display(String text) {
		clear();
		
		chars[0] = (byte)(0b0000111100001111);
		for (int i=0; i<8; i+=2) {
			
			//System.out.println(i);
			String subChar = text.charAt(i/2) + "";
			//System.out.println("Character " + (i/2) + ": " + subChar);
			byte[] charAt = charRegistry.get(subChar);
			chars[8-(2*(i/2))] = charAt[0];
			chars[9-(2*(i/2))] = charAt[1];
		}
		i2c.writeBulk(chars);
		Timer.delay(.1);
	}
	
	public void clear() {
		for(int c = 0; c < 10; c++) {
    		chars[c] = (byte)(0b00000000) & 0xFF;
    	}
	}
	
	public boolean getAButton() {
		return buttonA.get();
	}
	
	public boolean getBButton() {
		return buttonB.get();
	}
	
	public double getPotValue() {
		return pot.getVoltage();
	}
	
}
