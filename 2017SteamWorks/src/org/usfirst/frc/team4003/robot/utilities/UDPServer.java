package org.usfirst.frc.team4003.robot.utilities;

import java.io.*;
import java.net.*;

public class UDPServer implements Runnable {
	private final Object lock = new Object();
	double[] targetInfo = new double[] {-1,-1, -1};
    public UDPServer() {
	    Thread thread = new Thread(this);
	    thread.start();
    }
    
    public void setTargetInfo(double left, double right, double width) {
    	synchronized(lock) {
    		targetInfo = new double[] {left, right, width};
    	}
    }
    
    public double[] getTargetInfo() {
    	synchronized(lock) {
    		return targetInfo;
    	}
    }

    public void run() {
	    DatagramSocket serverSocket = null;
	    
	    try {
	        serverSocket = new DatagramSocket(5802);
	    } catch(SocketException ex) {
	        System.out.println("can't open socket");
	        return;
	    }
	    byte[] receiveData = new byte[1024];
	 
	    while(!Thread.interrupted()) {
	    	
	        DatagramPacket receivePacket =
	        		new DatagramPacket(receiveData, receiveData.length);
	        try {
	        	serverSocket.receive(receivePacket);
	        } catch(IOException ex) {
	        	System.out.println("Error reading packet");
	        	continue;
	        }
	        String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
	        //System.out.println("Raw sentence: " + sentence);
	        String[] tokens = sentence.split(" ");
	        //System.out.println("RECEIVED: " + tokens[0] + " " + tokens[1] + " " + tokens[2]);
	        double left = Double.valueOf(tokens[0]);
	        double right = Double.valueOf(tokens[1]);
	        double width = Double.valueOf(tokens[2]);
	        setTargetInfo(left, right, width);
	        
	    }
	    serverSocket.close();
    }
}
