package org.usfirst.frc.team4003.robot.subsystems;

import java.io.IOException;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team4003.robot.Robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TrackingCamera extends Subsystem implements Runnable {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Mat source, hsv, mask, hierarchy;
	Scalar lowerHSV, upperHSV;
	Thread tracking;
	Object visionLock = new Object();
	boolean trackingOn = true;
	UsbCamera frontCam;
	UsbCamera backCam;
	boolean frontCamera = true;
	double targetX = Double.NaN;
	double targetY = Double.NaN;
	public TrackingCamera() {         
         source = new Mat();
         hsv = new Mat();
         mask = new Mat();
         hierarchy = new Mat();
         lowerHSV = new Scalar(0,108,94);
         upperHSV = new Scalar(32,206,255);
         tracking = new Thread(this);
         
         // Set exposure with v4l2-ctl
         // /usr/bin/v4l2-ctl --set-ctrl exposure_absolute=3
         try {
        	Runtime.getRuntime().exec("/usr/bin/v4l2-ctl -d /dev/video0 --set-ctrl exposure_auto=1");
        	Runtime.getRuntime().exec("/usr/bin/v4l2-ctl -d /dev/video1 --set-ctrl exposure_auto=1");
			Runtime.getRuntime().exec("/usr/bin/v4l2-ctl -d /dev/video0 --set-ctrl exposure_absolute=30");
			Runtime.getRuntime().exec("/usr/bin/v4l2-ctl -d /dev/video1 --set-ctrl exposure_absolute=5");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         frontCam = new UsbCamera("front", 1);
         backCam = new UsbCamera("back", 0);  
         
	}
	public void toggleCamera() {
		synchronized(visionLock){		
			frontCamera = !frontCamera;
		}
	}
	public void trackingOn(boolean on) {
		synchronized(visionLock){
			trackingOn = on;
			if(on == false) {
				targetX = Double.NaN;
				targetY = Double.NaN;
			}
		}
	}
	public boolean isTrackingOn() {
		synchronized(visionLock){
			return trackingOn;
		}
	}
	public void startCamera() {
		tracking.start();
	}
	public void run() {
		//camera = CameraServer.getInstance().startAutomaticCapture("Camera", 0);
		
        frontCam.setResolution(320,240);
        backCam.setResolution(320,240);
        frontCam.setFPS(20);
        backCam.setFPS(20);
        //camera.setExposureManual(3);
        CameraServer.getInstance().addCamera(frontCam);
        CameraServer.getInstance().addCamera(backCam);
        CvSink frontSink = CameraServer.getInstance().getVideo(frontCam);
        CvSink backSink = CameraServer.getInstance().getVideo(backCam);
        CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 320,240);
        frontSink.grabFrame(source);
        backSink.grabFrame(source);
        boolean localFrontCamera = true;
        while(true) {
        	synchronized(visionLock){
        		localFrontCamera = frontCamera;
        	}
        	double poseX = Robot.sensors.getXCoordinate();
        	double poseY = Robot.sensors.getYCoordinate();
        	double poseYaw = Robot.sensors.getYaw();
        	
        	if(localFrontCamera){
        		frontSink.grabFrame(source);
        	}else {
        		backSink.grabFrame(source);
        	}
            if (isTrackingOn()) {
            	Imgproc.cvtColor(source, hsv, Imgproc.COLOR_BGR2HSV);
                Core.inRange(hsv, lowerHSV, upperHSV, mask);
                ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
                Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, 
                		Imgproc.CHAIN_APPROX_SIMPLE);
                ArrayList<MatOfPoint> bigContours = new ArrayList<MatOfPoint>();
                for (int i = 0; i < contours.size(); i++) {
                	if (Imgproc.contourArea(contours.get(i)) > 50) {
                		bigContours.add(contours.get(i));
                	}
                }
                contours = bigContours;
                for (int i = 0; i < contours.size(); i++) {
                    Rect bbox = Imgproc.boundingRect(contours.get(i));
                    Imgproc.rectangle(source, bbox.tl(), bbox.br(), new Scalar(0,255,0),2);
                    //Imgproc.drawContours(source, contours, i, new Scalar(255,0,0),3);
                    Imgproc.contourArea(contours.get(i));
                }
                if(localFrontCamera) {
                	analyzeFrontContours(contours, poseX, poseY, poseYaw);
                }else {
                	analyzeBackContours(contours, poseX, poseY, poseYaw);
                }
            }
            outputStream.putFrame(source);
            
        }
        
        /*
        CameraServer.getInstance().removeCamera("Camera");
        CameraServer.getInstance().removeServer("Blur");
        */
	}
	public void setTarget(double x, double y){
		synchronized(visionLock){
			targetX = x;
			targetY = y;
		}
	}
	public double[] getTarget(){
		synchronized(visionLock){
			return new double[]{targetX, targetY};
		}
	}
	public void analyzeFrontContours(ArrayList<MatOfPoint> contours, double poseX, double poseY, double poseYaw){
		if(contours.size() < 2){
			SmartDashboard.putNumber("angle", Double.NaN);
			SmartDashboard.putNumber("distance", Double.NaN);
			setTarget(Double.NaN, Double.NaN);
			return;
		}
		MatOfPoint  bestOne = getBestContour(contours, .4);
		contours.remove(bestOne);
		MatOfPoint bestTwo = getBestContour(contours, .4);
		Rect rectangleOne = Imgproc.boundingRect(bestOne);
		Rect rectangleTwo = Imgproc.boundingRect(bestTwo);
		double x1 = rectangleOne.x + rectangleOne.width/2.0;
		double x2 = rectangleTwo.x + rectangleTwo.width/2.0;
		double pegX = (x1 + x2)/2.0;
		double distanceToCenter = pegX - 159.5;
		double distanceToTargetPix = 160 / Math.tan(Math.toRadians(35.29)); // 32.93 for 920?
		double alpha = -Math.toDegrees(Math.atan(distanceToCenter/distanceToTargetPix));
		double inchesPerPixel = 2 / (double) rectangleTwo.width;
		double distanceToTarget = inchesPerPixel * distanceToTargetPix;
		double phi = Math.toRadians(alpha + poseYaw);
		double targetX = poseX + distanceToTarget * Math.cos(phi);
		double targetY = poseY + distanceToTarget * Math.sin(phi);
		setTarget(targetX, targetY);
		SmartDashboard.putNumber("angle", alpha);
		SmartDashboard.putNumber("distance", distanceToTarget);
		SmartDashboard.putNumber("poseX", poseX);
		SmartDashboard.putNumber("poseY", poseY);
		SmartDashboard.putNumber("poseYaw", poseYaw);
	}
	public void analyzeBackContours(ArrayList<MatOfPoint> contours, double poseX, double poseY, double poseYaw){
		
	}	
	public MatOfPoint getBestContour(ArrayList<MatOfPoint> contours, double aspectRatio) {
		double bestError = Math.abs(getAspectRatio(contours.get(0)) - aspectRatio);
		MatOfPoint bestContour = contours.get(0);
		for(int i = 1; i < contours.size(); i++){
			double currentError = Math.abs(getAspectRatio(contours.get(i)) - aspectRatio);
			if(currentError < bestError){
				bestError = currentError;
				bestContour = contours.get(i);
			}
		}
		return bestContour;
	}
	public double getAspectRatio(MatOfPoint contour) {
		Rect bbox = Imgproc.boundingRect(contour);
		return bbox.width/(double)bbox.height;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

