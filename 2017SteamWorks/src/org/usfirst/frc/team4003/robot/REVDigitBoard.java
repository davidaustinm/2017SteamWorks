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
        charRegistry.put("B", new byte[]{ (byte)0b10001111, (byte)0b00010010 });
        charRegistry.put("C", new byte[]{ (byte)0b00111001, (byte)0b00000000 });
        charRegistry.put("D", new byte[]{ (byte)0b00001111, (byte)0b00010010 });
        charRegistry.put("E", new byte[]{ (byte)0b11111001, (byte)0b00000000 });
        charRegistry.put("F", new byte[]{ (byte)0b11110001, (byte)0b00000000 });
        charRegistry.put("G", new byte[]{ (byte)0b10111101, (byte)0b00000000 });
        charRegistry.put("H", new byte[]{ (byte)0b11110110, (byte)0b00000000 });
        charRegistry.put("I", new byte[]{ (byte)0b00001001, (byte)0b00010010 });
        charRegistry.put("J", new byte[]{ (byte)0b00011110, (byte)0b00000000 });
        charRegistry.put("K", new byte[]{ (byte)0b01110000, (byte)0b00001100 });
        charRegistry.put("L", new byte[]{ (byte)0b00111000, (byte)0b00000000 });
        charRegistry.put("M", new byte[]{ (byte)0b00110110, (byte)0b00000101 });
        charRegistry.put("N", new byte[]{ (byte)0b00110110, (byte)0b00001001 });
        charRegistry.put("O", new byte[]{ (byte)0b00111111, (byte)0b00000000 });
        charRegistry.put("P", new byte[]{ (byte)0b11110011, (byte)0b00000000 });
        charRegistry.put("Q", new byte[]{ (byte)0b00111111, (byte)0b00001000 });
        charRegistry.put("R", new byte[]{ (byte)0b11110011, (byte)0b00001000 });
        charRegistry.put("S", new byte[]{ (byte)0b10001101, (byte)0b00000001 });
        charRegistry.put("T", new byte[]{ (byte)0b00000001, (byte)0b00010010 });
        charRegistry.put("U", new byte[]{ (byte)0b00111110, (byte)0b00000000 });
        charRegistry.put("V", new byte[]{ (byte)0b00110000, (byte)0b00100100 });
        charRegistry.put("W", new byte[]{ (byte)0b00110110, (byte)0b00101000 });
        charRegistry.put("X", new byte[]{ (byte)0b00000000, (byte)0b00101101 });
        charRegistry.put("Y", new byte[]{ (byte)0b00000000, (byte)0b00010101 });
        charRegistry.put("Z", new byte[]{ (byte)0b00001001, (byte)0b00100100 });
        charRegistry.put(" ", new byte[]{ (byte)0b00000000, (byte)0b00000000 });
    	
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
