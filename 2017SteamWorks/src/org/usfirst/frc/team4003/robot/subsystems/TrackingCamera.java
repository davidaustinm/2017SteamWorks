package org.usfirst.frc.team4003.robot.subsystems;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team4003.robot.Robot;
import org.usfirst.frc.team4003.robot.RobotMap;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
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
	//UsbCamera backCam;
	UsbCamera trackingCam;

	boolean frontCamera = true;
	public static double NOTFOUND = -10000;
	double targetX = NOTFOUND;
	double targetY = NOTFOUND;
	public static Hashtable<String, Integer> cameraHash;
	

	protected static final String INTAKE_CAM = "C920";
	protected static final String GEAR_CAM = "046d:0825";
	protected static final String TRACKING_CAM = "Tracking";

	protected NetworkTable rdt = NetworkTable.getTable("robotData");

	public static void loadKeys() {
		//TODO: Remove magic strings and make constants out of them.
		cameraHash = new Hashtable<String, Integer>();
        cameraHash.put(GEAR_CAM, new Integer(-1));
        cameraHash.put(INTAKE_CAM, new Integer(-1));
		/* The 'Tracking' camera isn't identified by a string in the name but rather USB port position.
		 * but we stick the value of it here to keep things tidy.
		 */
        cameraHash.put("Tracking", new Integer(-1));
	}
	
	public TrackingCamera() {         
         source = new Mat();
         hsv = new Mat();
         mask = new Mat();
         hierarchy = new Mat();
         lowerHSV = new Scalar(60,120,150);
         upperHSV = new Scalar(100,255,255);
         tracking = new Thread(this);
            
         // Set exposure with v4l2-ctl
         // example: /usr/bin/v4l2-ctl --set-ctrl exposure_absolute=3
         try {
        	if (cameraHash.get(TRACKING_CAM).intValue() > 0){
        		Runtime.getRuntime().exec("/usr/bin/v4l2-ctl -d /dev/video" + cameraHash.get("Tracking").toString() + " --set-ctrl exposure_auto=1");
        		Runtime.getRuntime().exec("/usr/bin/v4l2-ctl -d /dev/video" + cameraHash.get("Tracking").toString() + "  --set-ctrl exposure_absolute=2047");
        		trackingCam = new UsbCamera("track", cameraHash.get(TRACKING_CAM));
        	}
        	Runtime.getRuntime().exec("/usr/bin/v4l2-ctl -d /dev/video" + cameraHash.get(GEAR_CAM).toString() + " --set-ctrl exposure_auto=1");
        	Runtime.getRuntime().exec("/usr/bin/v4l2-ctl -d /dev/video" + cameraHash.get(INTAKE_CAM).toString() + "  --set-ctrl exposure_auto=1");
			Runtime.getRuntime().exec("/usr/bin/v4l2-ctl -d /dev/video" + cameraHash.get(GEAR_CAM).toString() + "  --set-ctrl exposure_absolute=400");
			Runtime.getRuntime().exec("/usr/bin/v4l2-ctl -d /dev/video" + cameraHash.get(INTAKE_CAM).toString() + "  --set-ctrl exposure_absolute=30");
			// TODO: Set tracking camera exposures IF we have one.
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
        /*
        frontCam = new UsbCamera("front", RobotMap.VIEWCAMERA);
        if (RobotMap.trackingLocal == false) {
        	frontCam.setResolution(320,240);
    		frontCam.setFPS(15);
    		CameraServer.getInstance().startAutomaticCapture(frontCam);
        } else {
        	trackingCam = new UsbCamera("track", RobotMap.TRACKINGCAMERA);
        }
        */
        //backCam = new UsbCamera("back", cameraHash.get(INTAKE_CAM));  
                 
	}
	public void toggleCamera() {
		synchronized(visionLock) {
			//frontCamera = !frontCamera;
		}
	}

	public void trackingOn(boolean on) {
		synchronized(visionLock) {
			trackingOn = on;
			if(on == false) {
				targetX = NOTFOUND;
				targetY = NOTFOUND;
			}
		}
	}

	public boolean isTrackingOn() {
		synchronized(visionLock) {
			return trackingOn;
		}
	}

	public void startCamera() {
		//if (RobotMap.trackingLocal) tracking.start();
		//tracking.start();
	}
	
	public void run() {
		//UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
        //camera.setResolution(320, 240);
        //camera.setFPS(15);
        
        CvSink cvSink = CameraServer.getInstance().getVideo();
        CvSource outputStream = CameraServer.getInstance().putVideo("Camera", 640, 480);
        
        Mat source = new Mat();
        Mat output = new Mat();
        
        int margin = 50;
        Point bl = new Point(50, 240);
        Point tl = new Point(50, 0);
        Point br = new Point(320-margin, 240);
        Point tr = new Point(320-margin, 0);
        Scalar green = new Scalar(0,255,0);
        /*
        Scalar red = new Scalar(0,0,255);
        Scalar magenta = new Scalar(255,0,255);
        */
        while(!Thread.interrupted()) {
            cvSink.grabFrame(source);
            Imgproc.line(source, tl, bl, green, 3);
            Imgproc.line(source, tr, br, green, 3);
            //Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
            outputStream.putFrame(source);
        }	
	}
	
	double focalLength = 500; // measured camera focal length
	double cameraOffset = 4.75; // 5
	public double[] getTargetPosition() {
		if (RobotMap.trackingLocal == false) {
			double[] targetInfo = Robot.udpServer.getTargetInfo();
			if (targetInfo[1] == -1) return new double[] {NOTFOUND, NOTFOUND};
			double pegX;
			if (targetInfo[1] == -1) pegX = (double) targetInfo[0];
			else pegX = (targetInfo[0] + targetInfo[1])/2.0;
			double rightWidth = (double) targetInfo[2];
			
			double distanceToTarget = 2.0* focalLength / rightWidth;
			//System.out.println(distanceToTarget);
			double distanceToCenterPix = 159.5 - pegX;
			double distanceToCenterInches = 2.0 * distanceToCenterPix / rightWidth;
			distanceToCenterInches += cameraOffset;
			double horizontalDistance  = Math.sqrt(Math.pow(distanceToTarget,2) - 
					Math.pow(distanceToCenterInches,2));
			        
			double xCoord = Robot.sensors.getXCoordinate();
			double yCoord = Robot.sensors.getYCoordinate();
			double yaw = Math.toRadians(Robot.sensors.getYaw());
			double yawp90 = yaw + Math.PI/2;
			        
			double targetX = xCoord + horizontalDistance * Math.cos(yaw);
			double targetY = yCoord + horizontalDistance * Math.sin(yaw);
			targetX += distanceToCenterInches * Math.cos(yawp90);
			targetY += distanceToCenterInches * Math.sin(yawp90);
			
			return new double[] {targetX, targetY};	
		}
		return new double[] {NOTFOUND, NOTFOUND};
	}

	public void oldrun() {
		CvSink trackingSink = null;
		if (isTrackingLocal()) {
	        trackingCam.setResolution(320, 240);
	        trackingCam.setFPS(20);
	        trackingCam.setExposureManual(3);
	        CameraServer.getInstance().addCamera(trackingCam);
	        trackingSink = CameraServer.getInstance().getVideo(trackingCam);
	        trackingSink.grabFrame(source);
		}

		frontCam.setResolution(320,240);
        //backCam.setResolution(320,240);

        frontCam.setFPS(20);
        //backCam.setFPS(20);

        CameraServer.getInstance().addCamera(frontCam);
        //CameraServer.getInstance().addCamera(backCam);

        CvSink frontSink = CameraServer.getInstance().getVideo(frontCam);
        //CvSink backSink = CameraServer.getInstance().getVideo(backCam);

        CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 320,240);

		/* Grab the initial frame immediately.  There's a 1-2 second delay on getting that frame
		 * so get it out of the way before we need anything.
		 */

        frontSink.grabFrame(source);
        //backSink.grabFrame(source);


        boolean localFrontCamera = true;
        while(true) {
        	synchronized(visionLock) {
        		localFrontCamera = frontCamera;
        	}
        	double poseX = Robot.sensors.getXCoordinate();
        	double poseY = Robot.sensors.getYCoordinate();
        	double poseYaw = Robot.sensors.getYaw();
        	if (isTrackingOn() && isTrackingLocal()) {
        		trackingSink.grabFrame(source);
        	} else {
        		if (localFrontCamera) {	
            		frontSink.grabFrame(source);
            	} else {
            		//backSink.grabFrame(source);
            	}
        	}
            if (isTrackingOn() && isTrackingLocal()) {
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
              	analyzeFrontContours(contours, poseX, poseY, poseYaw);
            }
            outputStream.putFrame(source);
        }
	}
	
	protected boolean isTrackingLocal() {
		//return cameraHash.get(TRACKING_CAM).intValue() > 0;
		return RobotMap.trackingLocal;
	}

	public void setTarget(double x, double y) {
		synchronized(visionLock) {
			targetX = x;
			targetY = y;
			rdt.putNumber("targetX", x);
			rdt.putNumber("targetY", y);
		}
	}

	public double[] getTarget() {
		synchronized(visionLock) {
			if (RobotMap.trackingLocal) return new double[] {targetX, targetY};
			targetX = rdt.getNumber("targetX", NOTFOUND);
			targetY = rdt.getNumber("targetY", NOTFOUND);
			return new double[]{targetX, targetY};
		}
	}
	public void analyzeFrontContours(ArrayList<MatOfPoint> contours, double poseX, double poseY, double poseYaw) {
		if(contours.size() < 2) {
			setTarget(NOTFOUND, NOTFOUND);
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
		double theta = Math.toRadians(poseYaw + 90);
		targetX += cameraOffset * Math.cos(theta);
		targetY += cameraOffset * Math.sin(theta);
		setTarget(targetX, targetY);
		/*
		SmartDashboard.putNumber("angle", alpha);
		SmartDashboard.putNumber("distance", distanceToTarget);
		SmartDashboard.putNumber("poseX", poseX);
		SmartDashboard.putNumber("poseY", poseY);
		SmartDashboard.putNumber("poseYaw", poseYaw);
		*/
	}

	public void analyzeBackContours(ArrayList<MatOfPoint> contours, double poseX, double poseY, double poseYaw) {
	}	

	public MatOfPoint getBestContour(ArrayList<MatOfPoint> contours, double aspectRatio) {
		double bestError = Math.abs(getAspectRatio(contours.get(0)) - aspectRatio);
		MatOfPoint bestContour = contours.get(0);
		for(int i = 1; i < contours.size(); i++) {
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
		return bbox.width / (double)bbox.height;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

